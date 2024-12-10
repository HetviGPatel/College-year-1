import java.util.*;

class Trip 
{
    String destination;
    double cost;

    public Trip(String destination, double cost) 
	{
        this.destination = destination;
        this.cost = cost;
    }

    public String getDestination() 
	{
        return destination;
    }

    public double getCost() 
	{
        return cost;
    }
}

class TravelAgency1 {
	Scanner sc=new Scanner(System.in);
    int MAX_TRIPS = 5;
    Trip[] availableTrips;
    Trip[] bookedTrips;
    int bookedCount;

    TravelAgency1() 
	{
        availableTrips = new Trip[MAX_TRIPS];
        bookedTrips = new Trip[MAX_TRIPS];
        initializeTrips();
    }

    void initializeTrips() 
	{
        availableTrips[0] = new Trip("Paris", 1500.0);
        availableTrips[1] = new Trip("New York", 2000.0);
        availableTrips[2] = new Trip("Tokyo", 2500.0);
    }

    public void displayAvailableTrips() 
	{
        System.out.println("Available Trips:");
        for (int i = 0; i < MAX_TRIPS; i++) 
		{
            if (availableTrips[i] != null) 
			{
                System.out.println((i + 1) + ". " + availableTrips[i].getDestination() + " - $" + availableTrips[i].getCost());
            }
        }
    }

    void bookTrip(int tickets) 
	{
        System.out.println("Enter the trip index to book:");
        int tripIndex = sc.nextInt();
        if (tripIndex >= 1 && tripIndex <= MAX_TRIPS && availableTrips[tripIndex - 1] != null) 
		{
            double totalCost = tickets * availableTrips[tripIndex - 1].getCost();
            bookedTrips[bookedCount++] = availableTrips[tripIndex - 1];
            System.out.println("Trip to " + availableTrips[tripIndex - 1].getDestination() + " booked successfully!");
            availableTrips[tripIndex - 1] = null;
            System.out.println("Total Cost: $" + totalCost);
        } 
		else 
		{
            System.out.println("Invalid trip index or trip already booked.");
        }
    }

    void displayBookedTrips() 
	{
        System.out.println("Booked Trips:");
        for (int i = 0; i < bookedCount; i++) 
		{
            System.out.println((i + 1) + ". " + bookedTrips[i].getDestination() + " - $" + bookedTrips[i].getCost());
        }
    }
}

class Main {
    public static void main(String[] arg) 
	{
        Scanner sc = new Scanner(System.in);
        TravelAgency1 travelAgency = new TravelAgency1();
        boolean b = true;
        while (b) 
		{
            System.out.println("1. Display available trips");
            System.out.println("2. Book a trip");
            System.out.println("3. Display booked trips");
            System.out.println("4. Exit");
            System.out.print("Choose an option : ");
            int n = sc.nextInt();
            switch (n) 
			{
                case 1:
					System.out.println("--------------------------------------------------");
                    travelAgency.displayAvailableTrips();
					System.out.println("--------------------------------------------------");
                    break;
                case 2:
					System.out.println("--------------------------------------------------");
                    System.out.print("Enter the no of tickets you want to book: ");
                    int tickets = sc.nextInt();
                    travelAgency.bookTrip(tickets);
					System.out.println("--------------------------------------------------");
                    break;
                case 3:
					System.out.println("--------------------------------------------------");
                    travelAgency.displayBookedTrips();
					System.out.println("--------------------------------------------------");
                    break;
                case 4:
					System.out.println("--------------------------------------------------");
                    System.out.println("Exiting the travel agency system. Thank you!");
					System.out.println("--------------------------------------------------");
                    b = false;
                    break;
                default:
					System.out.println("--------------------------------------------------");
                    System.out.println("Invalid choice. Please enter a valid option.");
					System.out.println("--------------------------------------------------");
                    break;
            }
        }
    }
}