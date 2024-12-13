import java.io.*;
import java.sql.*;
import java.util.*;

public class Metro {

    static String DB_URL = "jdbc:mysql://localhost:3306/mms";
    static String DB_USERNAME = "root";
    static String DB_PASSWORD = "";
    

    DoublyLinkedList customerList = new DoublyLinkedList(); // Doubly linked list for customers
    static String adpass = "123";
    static Scanner sc = new Scanner(System.in);


    public static void main(String[] args)  throws Exception{
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection dbConnection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            Metro mms = new Metro();

            // Start the thread to display the next train schedule
            Thread updateThread = new Thread(() -> {
                while (true) {
                    try {
                        mms.displayNextTrain(dbConnection);
                        Thread.sleep(60000 * 5); // Sleep for 5 minutes
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            updateThread.start();

            System.out.println("---------Welcome to Metro Management System---------");

            while (true) {
                System.out.println("Are you a:");
                System.out.println("1. Customer");
                System.out.println("2. Administrator");
                System.out.println("3. Exit");

                int u = sc.nextInt();
                sc.nextLine();

                if (u == 1) {
                    try{
                        String insertQuery = "INSERT INTO login(name, email, contact_no) VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
                System.out.println("enter name : ");
                String name = sc.next();
                System.out.println("enter email : ");
                String email = sc.next();
                System.out.println("enter contact : ");
                int contact_no = sc.nextInt();
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setInt(3, contact_no);

                int rowsAffected = preparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Registration successful!");
                } else {
                    System.out.println("Registration failed.");
                }
                    }
                    catch(SQLException s){
                        System.out.println("u have already login");
                    }
                    mms.customerMenu(dbConnection);
                } else if (u == 2) {
                    System.out.print("Enter Administrator Password: ");
                    String password = sc.nextLine();
                    if (password.equals(adpass)) {
                        mms.adminMenu(dbConnection);
                    } else {
                        System.out.println("Incorrect password. Access denied.");
                    }
                } else if (u == 3) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else {
                    System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (Exception e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

   //
   private void customerMenu(Connection dbConnection) throws SQLException {
    while (true) {
        System.out.println("Customer Menu:");
        System.out.println("1. Book Ticket");
        System.out.println("2. Cancel Ticket");
        System.out.println("3. View Schedules");
        System.out.println("4. Fetch Ticket");
        System.out.println("5. Exit to Main Menu");

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            bookTicket(dbConnection);
        } else if (choice == 2) {
            cancelTicket(dbConnection);
        } else if (choice == 3) {
            displaySchedules(dbConnection);
        } else if (choice == 4) {
            fetchTicket(dbConnection);
        } else if (choice == 5) {
            return;
        } else {
            System.out.println("Invalid choice. Please try again.");
        }
    }
}

   //

    private void adminMenu(Connection dbConnection) throws SQLException {
        loadCustomers(dbConnection); // Load customers into the doubly linked list
    
        while (true) {
            System.out.println("Administrator Menu:");
            System.out.println("1. Add Station");
            System.out.println("2. Add Schedule");
            System.out.println("3. Update Schedule");
            System.out.println("4. Display Stations");
            System.out.println("5. Display Schedules");
            System.out.println("6. Display Customers");
            System.out.println("7. Display Bookings");
            //System.out.println("8. Search Customer by Booking ID");
            System.out.println("9. Search Customer by ID");
            System.out.println("10. Search Customer by Name");
            System.out.println("11. Exit to Main Menu");
    
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    addStation(dbConnection);
                    break;
                case 2:
                    addSchedule(dbConnection);
                    break;
                case 3:
                    updateSchedule(dbConnection);
                    break;
                case 4:
                    displayStations(dbConnection);
                    break;
                case 5:
                    displaySchedules(dbConnection);
                    break;
                case 6:
                    displayCustomers();
                    break;
                case 7:
                    displayBookings(dbConnection);
                    break;
                case 9:
                    searchCustomerById(dbConnection);
                    break;
                case 10:
                    searchCustomerByName();
                    break;
                case 11:
                    System.out.println("Exiting admin page...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    

    private void loadCustomers(Connection dbConnection) throws SQLException {
        String selectCustomersSQL = "SELECT * FROM Customers";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(selectCustomersSQL)) {
            while (rs.next()) {
                int id = rs.getInt("customer_id");
                String name = rs.getString("customer_name");
                String email = rs.getString("email");
                String contactNumber = rs.getString("contact_number");

                customerList.addCustomer(id, name, contactNumber,email);
            }
            System.out.println("Customers loaded successfully.");
        }
    }

    private void addStation(Connection dbConnection) throws SQLException {
        System.out.print("Enter Station Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Station ID: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        String insertStationSQL = "INSERT INTO Stations (station_id, name) VALUES (?, ?)";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertStationSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
            System.out.println("Station added successfully.");
        }
    }

    private void addSchedule(Connection dbConnection) throws SQLException {
        System.out.print("Enter Station ID: ");
        int stationId = sc.nextInt();
        System.out.print("Enter Arrival Time (HH:MM:SS): ");
        String arrivalTime = sc.next();
        System.out.print("Enter Departure Time (HH:MM:SS): ");
        String departureTime = sc.next();
        sc.nextLine(); // Consume newline

        String insertScheduleSQL = "INSERT INTO Schedules (station_id, arrival_time, departure_time) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertScheduleSQL)) {
            preparedStatement.setInt(1, stationId);
            preparedStatement.setTime(2, Time.valueOf(arrivalTime));
            preparedStatement.setTime(3, Time.valueOf(departureTime));
            preparedStatement.executeUpdate();
            System.out.println("Schedule added successfully.");
        }
    }

    private void bookTicket(Connection dbConnection) throws SQLException {
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Customer Contact Number: ");
        String contactNumber = sc.nextLine();

        System.out.print("Enter Departure Station Name: ");
        String departureStationName = sc.nextLine();
        System.out.print("Enter Arrival Station Name: ");
        String arrivalStationName = sc.nextLine();

        // Fetch the station IDs for departure and arrival
        int departureStationId = getStationIdByName(dbConnection, departureStationName);
        int arrivalStationId = getStationIdByName(dbConnection, arrivalStationName);

        if (departureStationId == -1 || arrivalStationId == -1) {
            System.out.println("Invalid departure or arrival station.");
            return;
        }

        // Check if there's a schedule for the given departure station
        String getScheduleSQL = "SELECT schedule_id, arrival_time, departure_time, price " +
                "FROM Schedules " +
                "WHERE station_id = ? " +
                "ORDER BY departure_time LIMIT 1"; // Get the next schedule

        int scheduleId = -1;
        double pricePerTicket = 0;

        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(getScheduleSQL)) {
            preparedStatement.setInt(1, departureStationId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    scheduleId = rs.getInt("schedule_id");
                    pricePerTicket = rs.getDouble("price");
                } else {
                    System.out.println("No schedule available for the selected departure station.");
                    return;
                }
            }
        }

        System.out.print("Enter Number of Tickets: ");
        int numberOfTickets = sc.nextInt();
        sc.nextLine(); // Consume newline
        try{
            if(numberOfTickets<=0){
                throw new IllegalArgumentException("not valid tickets (not zero or negative should be inserted) ...");
            }
        }
        catch(Exception e){
            System.out.println("error came " + e.getMessage());
        }
        double totalPrice = numberOfTickets * pricePerTicket;

        // Insert into Customers table
        String insertCustomerSQL = "INSERT INTO Customers (customer_name, email, contact_number) VALUES (?, ?, ?)";
        int customerId;
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, contactNumber);
            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    customerId = rs.getInt(1);
                } else {
                    throw new SQLException("Failed to retrieve customer ID.");
                }
            }
        }

        // Insert into Bookings table
        String insertBookingSQL = "INSERT INTO Bookings (customer_id, schedule_id, no_of_tickets, total_price) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(insertBookingSQL)) {
            preparedStatement.setInt(1, customerId);
            preparedStatement.setInt(2, scheduleId);
            preparedStatement.setInt(3, numberOfTickets);
            preparedStatement.setDouble(4, totalPrice);
            preparedStatement.executeUpdate();
            System.out.println("Ticket booked successfully.");
        }
    }

    private void cancelTicket(Connection dbConnection) throws SQLException {
        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = sc.nextInt();
        sc.nextLine(); // 

        String deleteBookingSQL = "DELETE FROM Bookings WHERE booking_id = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(deleteBookingSQL)) {
            preparedStatement.setInt(1, bookingId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Booking cancelled successfully.");
            } else {
                System.out.println("No booking found with the given ID.");
            }
        }
    }

    private void displaySchedules(Connection dbConnection) throws SQLException {
        String selectSchedulesSQL = "SELECT * FROM Schedules";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(selectSchedulesSQL)) {
            while (rs.next()) {
                int scheduleId = rs.getInt("schedule_id");
                int stationId = rs.getInt("station_id");
                Time arrivalTime = rs.getTime("arrival_time");
                Time departureTime = rs.getTime("departure_time");
                String arp = rs.getString("arrival_name");
                String drp = rs.getString("departure_name");
                double price = rs.getDouble("price");

                System.out.println("Schedule ID: " + scheduleId + ", Station ID: " + stationId +
                        ", Arrival name: " + arp + ", Departure name: " + drp +
                        ", Arrival Time: " + arrivalTime + ", Departure Time: " + departureTime +
                        ", Price: " + price);
            }
        }
    }
    //
    private void fetchTicket(Connection dbConnection) throws SQLException {
    
        // Ask for both email and contact number
        System.out.print("Enter your Email: ");
        String email = sc.nextLine();
        System.out.print("Enter your Contact Number: ");
        String contactNumber = sc.nextLine();
    
        // Fetch customer details based on email and contact number
        String getCustomerSQL = "SELECT customer_id FROM Customers WHERE email = ? AND contact_number = ?";
        int customerId = -1;
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(getCustomerSQL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, contactNumber);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    customerId = rs.getInt("customer_id");
                } else {
                    System.out.println("No customer found with the given email and contact number.");
                    return;
                }
            }
        }
    
        // Fetch ticket details based on customer ID
        String getTicketSQL = "SELECT b.booking_id, s.arrival_time, s.departure_time, b.no_of_tickets, b.total_price " +
                              "FROM Bookings b " +
                              "JOIN Schedules s ON b.schedule_id = s.schedule_id " +
                              "WHERE b.customer_id = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(getTicketSQL)) {
            preparedStatement.setInt(1, customerId);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Create or open the file for writing
                File file = new File("ticket_details.txt");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    if (rs.next()) {
                        int bookingId = rs.getInt("booking_id");
                        Time arrivalTime = rs.getTime("arrival_time");
                        Time departureTime = rs.getTime("departure_time");
                        int numberOfTickets = rs.getInt("no_of_tickets");
                        double totalPrice = rs.getDouble("total_price");
    
                        // Write ticket details to the file
                        writer.write("Ticket Details:");
                        writer.newLine();
                        writer.write("Booking ID: " + bookingId);
                        writer.newLine();
                        writer.write("Arrival Time: " + arrivalTime);
                        writer.newLine();
                        writer.write("Departure Time: " + departureTime);
                        writer.newLine();
                        writer.write("Number of Tickets: " + numberOfTickets);
                        writer.newLine();
                        writer.write("Total Price: " + totalPrice);
                        writer.newLine();
    
                        System.out.println("Ticket details have been written to 'ticket_details.txt'.");
                    } else {
                        writer.write("No tickets found for the given customer.");
                        writer.newLine();
                        System.out.println("No tickets found for the given customer.");
                    }
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }
        }
    }
    //


    private void displayStations(Connection dbConnection) throws SQLException {
        String selectStationsSQL = "SELECT * FROM Stations";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(selectStationsSQL)) {
            while (rs.next()) {
                int stationId = rs.getInt("station_id");
                String name = rs.getString("name");

                System.out.println("Station ID: " + stationId + ", Name: " + name);
            }
        }
    }

    private void displayCustomers() {
        customerList.displayCustomers();
    }

    private void displayBookings(Connection dbConnection) throws SQLException {
        String selectBookingsSQL = "SELECT * FROM Bookings";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(selectBookingsSQL)) {
            while (rs.next()) {
                int bookingId = rs.getInt("booking_id");
                int customerId = rs.getInt("customer_id");
                int scheduleId = rs.getInt("schedule_id");
                int numberOfTickets = rs.getInt("no_of_tickets");
                double totalPrice = rs.getDouble("total_price");

                System.out.println("Booking ID: " + bookingId + ", Customer ID: " + customerId +
                        ", Schedule ID: " + scheduleId + ", Number of Tickets: " + numberOfTickets +
                        ", Total Price: " + totalPrice);
            }
        }
    }

    

    private void searchCustomerById(Connection dbConnection) throws SQLException {
        System.out.print("Enter Customer ID to search: ");
        int customerId = sc.nextInt();
        sc.nextLine(); 
    
        Customer customer = customerList.searchCustomerById(customerId);
    
        if (customer != null) {
            System.out.println("Customer Details:");
            System.out.println(customer);
    
            // Create a file 
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Customer_" + customerId + ".txt"))) {
                writer.write("Customer Details:");
                writer.newLine();
                writer.write("ID: " + customer.getId());
                writer.newLine();
                writer.write("Name: " + customer.getName());
                writer.newLine();
                writer.write("Contact Number: " + customer.getContactNumber());
                System.out.println("Customer information written to file: Customer_" + customerId + ".txt");
            } catch (IOException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("No customer found with the given ID.");
        }
    }
    
    //name
    private void searchCustomerByName() {
        System.out.print("Enter Customer Name to search: ");
        String name = sc.nextLine();
    
        List<Customer> customers = customerList.searchCustomerByName(name);
    
        if (!customers.isEmpty()) {
            System.out.println("Customers found:");
            int count = 1;
            for (Customer customer : customers) {
                System.out.println(customer);
    
                // Create a file 
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("Customer_" + name + "_" + count + ".txt"))) {
                    writer.write("Customer Details:");
                    writer.newLine();
                    writer.write("ID: " + customer.getId());
                    writer.newLine();
                    writer.write("Name: " + customer.getName());
                    writer.newLine();
                    writer.write("Contact Number: " + customer.getContactNumber());
                    System.out.println("Customer information written to file: Customer_" + name + "_" + count + ".txt");
                    count++;
                } catch (IOException e) {
                    System.out.println("Error writing to file: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No customers found with the given name.");
        }
    }
    
    

    private int getStationIdByName(Connection dbConnection, String stationName) throws SQLException {
        String getStationIdSQL = "SELECT station_id FROM Stations WHERE name = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(getStationIdSQL)) {
            preparedStatement.setString(1, stationName);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("station_id");
                } else {
                    return -1; // Station not found
                }
            }
        }
    }

    private void updateSchedule(Connection dbConnection) throws SQLException {
        System.out.print("Enter Schedule ID to update: ");
        int scheduleId = sc.nextInt();
        sc.nextLine(); // Consume newline

        System.out.print("Enter New Arrival Time (HH:MM:SS): ");
        String newArrivalTime = sc.next();
        System.out.print("Enter New Departure Time (HH:MM:SS): ");
        String newDepartureTime = sc.next();
        sc.nextLine(); // Consume newline

        String updateScheduleSQL = "UPDATE Schedules SET arrival_time = ?, departure_time = ? WHERE schedule_id = ?";
        try (PreparedStatement preparedStatement = dbConnection.prepareStatement(updateScheduleSQL)) {
            preparedStatement.setTime(1, Time.valueOf(newArrivalTime));
            preparedStatement.setTime(2, Time.valueOf(newDepartureTime));
            preparedStatement.setInt(3, scheduleId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Schedule updated successfully.");
            } else {
                System.out.println("No schedule found with the given ID.");
            }
        }
    }

    private void displayNextTrain(Connection dbConnection) throws SQLException {
        String getNextTrainSQL = "SELECT s.name, sc.arrival_time, sc.departure_time " +
                "FROM Schedules sc " +
                "JOIN Stations s ON sc.station_id = s.station_id " +
                "WHERE sc.departure_time > NOW() " +
                "ORDER BY sc.departure_time LIMIT 1";
        try (Statement stmt = dbConnection.createStatement();
             ResultSet rs = stmt.executeQuery(getNextTrainSQL)) {
            if (rs.next()) {
                String stationName = rs.getString("name");
                Time arrivalTime = rs.getTime("arrival_time");
                Time departureTime = rs.getTime("departure_time");

                System.out.println("Next Train Information:");
                System.out.println("Station: " + stationName);
                System.out.println("Arrival Time: " + arrivalTime);
                System.out.println("Departure Time: " + departureTime);
            } else {
                System.out.println("No upcoming trains.");
            }
        }
    }
}
//

 class Customer {
    private int id;
    private String name;
    private String contactNumber;
     String email;

    // Constructor
    public Customer(int id, String name, String contactNumber, String email) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
    }

    // Getter for contact number
    public String getContactNumber() {
        return contactNumber;
    }

    // Getter for other fields
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    // Setter for contact number
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    // Setter for other fields
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id + ", Name: " + name + ", Contact Number: " + contactNumber + ", Email: " + email;
    }
}

//

class DoublyLinkedList {
     Node head;
     Node tail;

    private class Node {
         int id;
         String name;
         String email;
         String contactNumber;
         Node next;
         Node prev;

        Node(int id, String name, String contactNumber,String email) {
            this.id = id;
            this.name = name;
            this.contactNumber = contactNumber;
            this.email=email;
        }
    }

    public void addCustomer(int id, String name, String contactNumber,String email) {
        Node newNode = new Node(id, name, contactNumber,email);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void displayCustomers() {
        Node current = head;
        while (current != null) {
            System.out.println("ID: " + current.id + ", Name: " + current.name + ", Contact Number: " + current.contactNumber);
            current = current.next;
        }
    }

    public Customer searchCustomerById(int id) {
        Node current = head;
        while (current != null) {
            if (current.id == id) {
                return new Customer(current.id, current.name, current.contactNumber,current.email );
            }
            current = current.next;
        }
        return null; // Customer not found
    }

    public List<Customer> searchCustomerByName(String name) {
        List<Customer> customers = new ArrayList<>();
        Node current = head;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                customers.add(new Customer(current.id, current.name, current.contactNumber,current.email));
            }
            current = current.next;
        }
        return customers;
    }
}