 import java.util.*;
class Booking
{
        int numSingleRooms = 10;
        int numDoubleRooms = 10;
        int numTripleRooms = 10;
        int extrabed=15;
        static  int bookedSingleRooms = 0;
        static int bookedDoubleRooms = 0;
        static int bookedTripleRooms = 0;
        static int bookedextrabed=0;
        void catalog()
        {
            System.out.println("----------------------------");
            System.out.println(" welcome to Our PG !");
    	    System.out.println();
    	    System.out.println(" Single Bed Room : 5000/- ");
    	    System.out.println(" Double Bed Room : 10000/- ");
    	    System.out.println(" Triple Bed Room : 14000/- ");
    	    System.out.println(" Extra Bed : 1000/- ");
            System.out.println("----------------------------");
        }
        void Booking()
        {
        	Scanner sc = new Scanner(System.in);
        	int choice;
        do
        {
            System.out.println("------------------------");
            System.out.println("1. Book a single room  ");
            System.out.println("2. Book a double room");
            System.out.println("3. Book a triple room");
            System.out.println("4. Book a Extra bed");
            System.out.println("5. Cancel  booking");
            System.out.println("6. Exit");
            System.out.println("------------------------");
            System.out.print("Choose an option: ");
            choice = sc.nextInt();
            switch (choice)
             {
                case 1:
                	{
                	    System.out.print("Enter Number Of Single Room : ");
                	    int n_single =sc.nextInt();
                	    bookedSingleRooms = bookedSingleRooms + n_single;
        	   	        if (numSingleRooms > bookedSingleRooms)
                        {
                            System.out.println("Single room booked successfully");
                        } 
                        else 
                        {
                            bookedSingleRooms =bookedSingleRooms - n_single;
                            System.out.println("Sorry, no single rooms available");
                        }
                  }
                  break;
                case 2:
                     {
                         System.out.print("Enter Number Of Double Room : ");
                	      int n_double =sc.nextInt();
                	      bookedDoubleRooms =bookedDoubleRooms + n_double;
        	              if (numDoubleRooms > bookedDoubleRooms)
                          {
                              System.out.println("Double room booked successfully");
                          } 
                          else 
                          {
                              bookedDoubleRooms = bookedDoubleRooms - n_double;
                              System.out.println("Sorry, no double rooms available");
                          }
                    }
                    break;
                case 3:
                	 {
                	     System.out.print("Enter Number Of triple rooms Room : ");
                	     int n_tripleroom =sc.nextInt();
                	     bookedTripleRooms =bookedTripleRooms + n_tripleroom;
        	             if (numTripleRooms > bookedTripleRooms)
                         {
                             System.out.println("triple room booked successfully");
                         } 
                         else 
                         {
                             bookedTripleRooms=bookedTripleRooms - n_tripleroom;
                             System.out.println("Sorry, no triple rooms available");
                         }
                    }
                    break;
                case 4:
                	 {
                        if(bookedSingleRooms>0 || bookedDoubleRooms>0 || bookedTripleRooms>0)
						 {
                             System.out.print("Enter Number Of Extra Bed : ");
                             int n_bed =sc.nextInt();
                             bookedextrabed =bookedextrabed + n_bed;
                             if(extrabed >bookedextrabed)
                             {
                                 System.out.println("Extra bed booked successfully ");
                             }
                             else
                             {
                                 bookedextrabed =bookedextrabed - n_bed;
                                 System.out.println("sorry , not available ");
                             }
						 }
						else
						 {
							 System.out.println("Hello , Sir Please Book Any Rooms");
						 }
                     }
                     break;
                  case 5:
                  {
                      System.out.println("------------------------");
                      System.out.println("1. Single room");
                      System.out.println("2. Double room");
                      System.out.println("3. triple room");
                      System.out.println("4. extra bed");
                      System.out.println("5. Exit");
                      System.out.println("------------------------");
                      System.out.print("Enter the type of room to cancel booking for : ");
                      int cancelChoice = sc.nextInt();
                      switch (cancelChoice) 
                      {
                          case 1:
                             System.out.print("Enter Number Of Cancel Single Room : ");
                             int nc_single =sc.nextInt();
                             if (bookedSingleRooms >=nc_single)
                             {
                                 bookedSingleRooms=bookedSingleRooms - nc_single;
								 if(bookedSingleRooms!=0)
								 {
									System.out.println("Single room booking cancelled successfully");
								 }
								 else
								 {
									
									System.out.println("Please At Least Booked one Room");
								 }
                             }
                              else
                             {
								 bookedextrabed=0;
                                 System.out.println("No single room booking found");
                             }
                            break;
                        case 2:
                            System.out.print("Enter Number Of Cancel Double Room : ");
                            int nc_double =sc.nextInt();
                            if (bookedDoubleRooms >=nc_double)
                            {
                                bookedDoubleRooms=bookedDoubleRooms - nc_double;
                               if(bookedDoubleRooms>0)
								 {
									System.out.println("Double room booking cancelled successfully");
								 }
								 else
								 {
									 bookedextrabed=0;
									System.out.println("Please At Least Booked one Room");
								 }
                            } 
                            else
                            {
                                System.out.println("No double room booking found");
                            }
                            break;
                        case 3:
                            System.out.print("Enter Number Of Cancel triple room : ");
                            int nc_tripleroom =sc.nextInt();
                            if (bookedTripleRooms >=nc_tripleroom)
                            {
                                bookedTripleRooms=bookedTripleRooms - nc_tripleroom;
                                if(bookedTripleRooms>0)
								 {
									System.out.println("Triple room booking cancelled successfully");
								 }
								 else
								 {
									 bookedextrabed=0;
									System.out.println("Please At Least Booked one Room");
								 }
                            } else {
                                System.out.println("No triple room booking found");
                            }
                            break;
							case 4:
								System.out.print("Enter Number Of Cancel extrabed : ");
								int nc_extrabed =sc.nextInt();
								if (bookedextrabed >=nc_extrabed)
								{
									bookedextrabed=bookedextrabed - nc_extrabed;
									System.out.println("Extra Bed booking cancelled successfully");
								} 
								else 
								{
									System.out.println("No Extra Bed booking found");
								}
								break;
							case 5:
								System.out.println("Back to main Page ");
								System.out.println("----------------------------");
							break;
                        default:
                            System.out.println("Invalid choice");
                            break;
                    }
                    break;
                }
                case 6:
                    System.out.println("Back to main Page ");
                    System.out.println("----------------------------");
                    break;
            
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        } 
        while (choice != 6);
        } 
        void show()
        {
            System.out.println(" Your Booking Status : ");
            System.out.println("---------------------------------");
            if(bookedSingleRooms > 0)
            {
                System.out.println(" Single Bed Room :"+bookedSingleRooms);
            }
            if(bookedDoubleRooms > 0)
            {
                System.out.println(" Double Bed Room :"+bookedDoubleRooms);
            }
            if(bookedTripleRooms > 0)
            {
                 System.out.println(" triple room Room :"+bookedTripleRooms);
            }
              if(bookedextrabed > 0)
            {
                 System.out.println(" Extra Bed :"+bookedextrabed);
            }
            System.out.println("---------------------------------");
        }
        void bill()
        {
            System.out.println("-----------Your Bill Payment-----------");
            System.out.println();
            if(bookedSingleRooms > 0)
            {
                System.out.println(" Single Bed Room="+bookedSingleRooms+"----->"+(bookedSingleRooms*5000)+"/-" );
            }
            if(bookedDoubleRooms > 0)
            {
                System.out.println(" Double Bed Room="+bookedDoubleRooms+"----->"+(bookedDoubleRooms*10000)+"/-" );
            }
            if(bookedTripleRooms > 0)
            {
                 System.out.println(" triple rooms Room="+bookedTripleRooms+"----->"+(bookedTripleRooms*14000)+"/-" );
            }
            if(bookedextrabed > 0)
            {
                 System.out.println(" extra bed="+bookedextrabed+"----->"+(bookedextrabed*1000)+"/-" );
            }
            if(bookedSingleRooms > 0 || bookedDoubleRooms > 0 || bookedTripleRooms > 0 || bookedextrabed>0)
            {
                System.out.println("---------------------------------------" );
                int total_a=(bookedSingleRooms*5000) + (bookedDoubleRooms*10000) + (bookedTripleRooms*14000)+(bookedextrabed*1000);
                System.out.println(" Total Amount -------------"+total_a+"/-" );
            }
        }
        void feedback()
        {
            Scanner sc =new Scanner(System.in);
            System.out.print("Please feedback point out of 5 :");
            int n = sc.nextInt();
            if(n<3)
            {
                System.out.print(" Enter feedback requirements : ");
                String x=sc.next();   
            }
			else
			{
				System.out.print(" Your most welcome : ");
			}
        }
}
class PG
{
    public static void main(String [] arg)
    {
            Scanner sc = new Scanner(System.in);
        	Booking B= new Booking();
        	boolean x=true;
        	while(x)
        	{
                System.out.println("1. view catalog  ");
                System.out.println("2. Booking Room ");
                System.out.println("3. Show your Booking Status ");
                System.out.println("4. your bill ");
                System.out.println("5. Give your feedback ");
                System.out.println("6. Exit");
                System.out.print("Choose an option : ");
                int n =sc.nextInt();
                switch(n)
                {
                    case 1:
                	    B.catalog();
                	    break;
                    case 2:
                        B.Booking();
                        break;
                    case 3:
                	    B.show();
                        break;
                    case 4:
                	    B.bill();
                        break;
                    case 5:
                         B.feedback();
                    case 6:
                        System.out.println("Thank you !");
                        x=false;
                    break;
                    default:
                        System.out.println("Invalid choice");
                    break;
                }
            } 
    }
}