package hospitalManagementSystem;

import java.util.List;
import java.util.Scanner;

public class App {
    private List<Staff> staffList;
    private int role = 0;
    boolean authenticated = false;
    String userID = "";
    String password = "";

    public static void main(String[] args) {
        App app = new App();
        app.initializeStaff();  // Initialize staff from the CSV file at the start
        app.run();  // Run the main program loop
    }

    // Main program loop
    public void run() {
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();  // Create an instance of Login to handle authentication

        // Authentication loop
        while (!authenticated) {
            System.out.print("Enter User ID: ");
            userID = scanner.nextLine();
            System.out.print("Enter Password: ");
            password = scanner.nextLine();

            // Authenticate user and get the role
            role = login.authenticate(staffList, userID, password);

            if (role != 0) {
                System.out.println("Authentication successful. Role: " + role);
                authenticated = true;
            } else {
                System.out.println("Authentication failed. Please try again.");
            }
        }

        // Main menu loop
        boolean exit = false;
        
        while (!exit) {

            UserRoleMenu menu = null;
            // Determine the appropriate menu based on the user's role
            switch (role) {
                case 1:
                    menu = new PatientMenu();
                    break;
                case 2:
                    menu = new DoctorMenu();
                    break;
                case 3:
                    Pharmacist pharmacist = findPharmacist(userID);
                    if (pharmacist != null) {
                        menu = new PharmacistMenu(pharmacist);
                    } else {
                        System.out.println("Pharmacist not found.");
                        exit = true;
                    }
                    exit = true;
                    break;
                case 4:
                    menu = new AdministratorMenu();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid role. Exiting.");
                    exit = true;
                    break;
            }

            // Display the role-specific menu if applicable
            if (menu != null) {
                menu.displayMenu(scanner);
            }
        }

        scanner.close();
    }

    // Method to initialize staff objects from the CSV file
    public void initializeStaff() {
        CsvReaderStaff csvReaderStaff = new CsvReaderStaff();
        csvReaderStaff.readAndInitializeStaff("C:\\Users\\User\\OneDrive\\Documents\\GitHub\\SC2002_SCSD_Grp6\\SC2002_Project\\src\\data\\Staff_List 1.csv");
        staffList = csvReaderStaff.getStaffList();
    }

    // Method to find and return the Pharmacist by userID
    public Pharmacist findPharmacist(String userID) {
        for (Staff staff : staffList) {
            if (staff instanceof Pharmacist && staff.getUserId().equals(userID)) {
                return (Pharmacist) staff;
            }
        }
        return null;  // Return null if pharmacist is not found
    }
}
