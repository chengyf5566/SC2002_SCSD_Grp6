package hospitalManagementSystem;

import java.util.Scanner;

public class App {
    private static final String UserID = "admin";
    private static final String PASSWORD = "pw123";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            
            //simple login for now 
            System.out.println("==Please enter Login Details==");

            System.out.print("Enter User ID: ");
            String inputUsername = scanner.nextLine();

            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine();

            if (authenticate(inputUsername, inputPassword)) {
                System.out.println("Login successful! Welcome, " + inputUsername + "!");
                System.out.println("");
                System.out.println("Welcome to Hospital Management System");
                System.out.println("=====================================");
                System.out.println("1. Patient");
                System.out.println("2. Doctor");
                System.out.println("3. Pharmacist");
                System.out.println("4. Administrator");
                System.out.println("5. Logout");
                System.out.print("Select your role: ");
                int role = scanner.nextInt();
                scanner.nextLine();  // consume newline

                switch (role) {
                    case 1:
                        patientMenu(scanner);
                        break;
                    case 2:
                        doctorMenu(scanner);
                        break;
                    case 3:
                        pharmacistMenu(scanner);
                        break;
                    case 4:
                        administratorMenu(scanner);
                        break;
                    case 5:
                        System.out.println("Logging out...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
            else {
                System.out.println("Authentication failed. Please try again.\n");
            }
            
        }
        
        scanner.close();
    }

    private static boolean authenticate(String username, String password) {
        return UserID.equals(username) && PASSWORD.equals(password);
    }

    public static void patientMenu(Scanner scanner) {
        boolean exit = false;
        while(!exit){
            System.out.println("\nPatient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. View Available Appointment Slots");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");

            System.out.println("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 1: 
                    System.out.println("View Medical Record");
                    break;
                case 2: 
                    System.out.println("Update Personal Information");
                    break;
                case 3: 
                    System.out.println("View Available Appointment Slots");
                    break;
                case 4: 
                    System.out.println("Schedule an Appointment");
                    break;
                case 5: 
                    System.out.println("Reschedule an Appointment");
                    break;
                case 6: 
                    System.out.println("Cancel an Appointment");
                    break;
                case 7: 
                    System.out.println("View Scheduled Appointments");
                    break;
                case 8: 
                    System.out.println("View Past Appointment Outcome Records");
                    break;
                case 9: 
                    System.out.println("Logout");
                    System.out.println("");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void doctorMenu(Scanner scanner) {
        boolean exit = false;
        while(!exit){
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. Record Appointment Outcome");
            System.out.println("9. Logout");

            System.out.println("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 1: 
                    System.out.println("View Patient Medical Records");
                    break;
                case 2: 
                    System.out.println("Update Patient Medical Records");
                    break;
                case 3: 
                    System.out.println("View Personal Schedule");
                    break;
                case 4: 
                    System.out.println("Set Availability for Appointments");
                    break;
                case 5: 
                    System.out.println("Accept or Decline Appointment Requests");
                    break;
                case 6: 
                    System.out.println("View Upcoming Appointments");
                    break;
                case 7: 
                    System.out.println("View Scheduled Appointments");
                    break;
                case 8: 
                    System.out.println("View Scheduled Appointments");
                    break;
                case 9: 
                    System.out.println("Logout");
                    System.out.println("");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void pharmacistMenu(Scanner scanner) {
        boolean exit = false;
        while(!exit){
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medical inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. View Replenishment Request");
            System.out.println("6. Logout");

            System.out.println("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();
            
            switch(input){
                case 1: 
                    System.out.println("View Appointment Outcome Record");
                    break;
                case 2: 
                    System.out.println("Update Prescription Status");
                    //pharmacist.updatePrescriptionStatus("RX001", "Dispensed", Appointment.getAppointment());
                    break;
                case 3: 
                    System.out.println("View Medication Inventory");
                    //pharmacist.viewInventory(inventory.getMedications());
                    break;
                case 4: 
                    System.out.println("Submit Replenishment Request");
                    System.out.println("Enter medicine");
                    String name = scanner.next();
                    scanner.nextLine();
                    System.out.println("Enter medicine quantity");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    //pharmacist.submitReplenishmentRequest(name, quantity, inventory.getMedications());
                    break;
                case 5: 
                    System.out.println("View ReplenishmentRequests");
                    //pharmacist.viewReplenishmentRequests(ReplenishmentRequest.getRequestList());
                    break;    
                case 6: 
                    System.out.println("Logout");
                    System.out.println("");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void administratorMenu(Scanner scanner) {
        boolean exit = false;
        while(!exit){
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments details");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Logout");

            System.out.println("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch(input){
                case 1: 
                    System.out.println("View and Manage Hospital Staff");
                    break;
                case 2: 
                    System.out.println("View Appointments details");
                    break;
                case 3: 
                    System.out.println("View and Manage Medication Inventory");
                    break;
                case 4: 
                    System.out.println("Approve Replenishment Requests");
                    break;
                case 5: 
                    System.out.println("Logout");
                    System.out.println("");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    
}



    