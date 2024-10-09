import java.util.Scanner;

public class HospitalManagementSystemApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to Healthcare Management System");
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
                    adminMenu(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }

    public static void patientMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
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
            
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // consume newline
            
            switch (option) {
                case 1:
                    // Add logic for viewing medical records
                    System.out.println("Viewing Medical Record...");
                    break;
                case 2:
                    // Add logic for updating personal information
                    System.out.println("Updating Personal Information...");
                    break;
                case 3:
                    // Add logic for viewing available appointment slots
                    System.out.println("Viewing Available Appointment Slots...");
                    break;
                case 4:
                    // Add logic for scheduling an appointment
                    System.out.println("Scheduling an Appointment...");
                    break;
                case 5:
                    // Add logic for rescheduling an appointment
                    System.out.println("Rescheduling an Appointment...");
                    break;
                case 6:
                    // Add logic for canceling an appointment
                    System.out.println("Canceling an Appointment...");
                    break;
                case 7:
                    // Add logic for viewing scheduled appointments
                    System.out.println("Viewing Scheduled Appointments...");
                    break;
                case 8:
                    // Add logic for viewing past appointment outcome records
                    System.out.println("Viewing Past Appointment Outcome Records...");
                    break;
                case 9:
                    System.out.println("Logging out...");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void doctorMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. Record Appointment Outcome");
            System.out.println("8. Logout");

            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (option) {
                case 1:
                    // Add logic for viewing patient medical records
                    System.out.println("Viewing Patient Medical Records...");
                    break;
                case 2:
                    // Add logic for updating patient medical records
                    System.out.println("Updating Patient Medical Records...");
                    break;
                case 3:
                    // Add logic for viewing personal schedule
                    System.out.println("Viewing Personal Schedule...");
                    break;
                case 4:
                    // Add logic for setting availability
                    System.out.println("Setting Availability for Appointments...");
                    break;
                case 5:
                    // Add logic for accepting or declining appointments
                    System.out.println("Accepting or Declining Appointment Requests...");
                    break;
                case 6:
                    // Add logic for viewing upcoming appointments
                    System.out.println("Viewing Upcoming Appointments...");
                    break;
                case 7:
                    // Add logic for recording appointment outcome
                    System.out.println("Recording Appointment Outcome...");
                    break;
                case 8:
                    System.out.println("Logging out...");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void pharmacistMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");

            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (option) {
                case 1:
                    // Add logic for viewing appointment outcome record
                    System.out.println("Viewing Appointment Outcome Record...");
                    break;
                case 2:
                    // Add logic for updating prescription status
                    System.out.println("Updating Prescription Status...");
                    break;
                case 3:
                    // Add logic for viewing medication inventory
                    System.out.println("Viewing Medication Inventory...");
                    break;
                case 4:
                    // Add logic for submitting replenishment request
                    System.out.println("Submitting Replenishment Request...");
                    break;
                case 5:
                    System.out.println("Logging out...");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    public static void adminMenu(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments Details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");

            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (option) {
                case 1:
                    // Add logic for viewing and managing hospital staff
                    System.out.println("Viewing and Managing Hospital Staff...");
                    break;
                case 2:
                    // Add logic for viewing appointments details
                    System.out.println("Viewing Appointments Details...");
                    break;
                case 3:
                    // Add logic for managing medication inventory
                    System.out.println("Managing Medication Inventory...");
                    break;
                case 4:
                    // Add logic for approving replenishment requests
                    System.out.println("Approving Replenishment Requests...");
                    break;
                case 5:
                    System.out.println("Logging out...");
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
