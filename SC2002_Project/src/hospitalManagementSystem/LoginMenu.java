package hospitalManagementSystem;

import java.util.List;
import java.util.Scanner;

public class LoginMenu implements UserRoleMenu {


    private boolean authenticated = false;
    private String userID;
    private String password;
    
    
    private List<Staff> staffList;
    private List<Patient> patientList;
    // Constructor to initialize the LoginMenu with staff and patient lists
    public LoginMenu(List<Staff> staffList, List<Patient> patientList) {
        this.staffList = staffList;
        this.patientList = patientList;
    }
    
    /*
    // Constructor to initialize the LoginMenu with staff and patient lists
    public LoginMenu() {
        // Initialize staffList and patientList by reading data from CSV files
        this.staffList = new ArrayList<>();
        this.patientList = new ArrayList<>();
        
        // Initialize staff and patient data from CSV
        initializeStaffFromCSV();
        readAndInitializePatient();
    }
    
    // Method to initialize patient list from CSV
    private List<Patient> patientList;
    private CsvReaderPatient csvReaderPatient;
    public void readAndInitializePatient() {
        this.csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readCsv();
        this.patientList = csvReaderPatient.getPatientList();
    }
    
    // Method to initialize staff list from CSV
    private List<Staff> staffList;
    private CsvReaderStaff csvReader;
    public void initializeStaffFromCSV() {        
        this.csvReader = new CsvReaderStaff(); // Initialize the class-level csvReader with the given file path        
        csvReader.readAndInitializeStaff(); // Read and initialize staff from the CSV
        this.staffList = csvReader.getStaffList();  // Assign the read staff list
    }
    */
    
    // Method to display the login menu and handle the login process
    @Override
    public void displayMenu(Scanner scanner) {
        while (true) {  // Loop indefinitely to handle re-login after logout
            authenticated = false;

            // Authentication loop
            while (!authenticated) {
            	System.out.println("--- Welcome to Hospital Management App---");
                System.out.println("--- Login Menu ---");
                System.out.print("Please Enter User ID: ");
                userID = scanner.nextLine();
                System.out.print("Please Enter Password: ");
                password = scanner.nextLine();

                // Authenticate the user and get the role
                Login login = new Login();
                String userType = login.authenticate(userID, password, staffList, patientList);

                // Check the result of authentication
                if (!userType.equals("invalid")) {
                    // Fetch the user's name based on the role and display the greeting
                    String userName = getUserName(userID, userType);
                    System.out.println("Authentication successful. Welcome, " + userName + "!");
                    authenticated = true;
                    handleRoleMenu(scanner, userType, userID);
                } else {
                    System.out.println("Authentication failed. Please try again.");
                }
            }
        }
    }

    // Method to handle user role-based navigation after successful login
    private void handleRoleMenu(Scanner scanner, String userType, String userID) {
        UserRoleMenu menu = null;

        // Determine the role and provide the corresponding menu
        switch (userType.toLowerCase()) {
            case "patient":
                Patient patient = findPatient(userID);
                menu = new PatientMenu(patient);
                break;
            case "staff":
                Staff staff = findStaff(userID);
                if (staff != null) {
                    String role = staff.getRole().toLowerCase();
                    switch (role) {
                        case "doctor":
                            menu = new DoctorMenu((Doctor) staff);
                            break;
                        case "pharmacist":
                            menu = new PharmacistMenu((Pharmacist) staff);
                            break;
                        case "administrator":
                            menu = new AdministratorMenu((Administrator) staff);
                            break;
                        default:
                            System.out.println("Unknown staff role.");
                            break;
                    }
                }
                break;
            default:
                System.out.println("Invalid user type. Exiting.");
                return;
        }

        // Display the role-specific menu if applicable
        if (menu != null) {
            menu.displayMenu(scanner);
        }
    }

    // Method to find and return the Patient by userID
    private Patient findPatient(String userID) {
        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(userID)) {
                return patient;
            }
        }
        return null;
    }

    // Method to find and return the Staff member (if it is a Doctor, Pharmacist, etc.)
    private Staff findStaff(String userID) {
        for (Staff staff : staffList) {
            if (staff.getUserID().equals(userID)) {
                return staff;
            }
        }
        return null;
    }

    // Method to fetch the user's name based on their userID and role
    private String getUserName(String userID, String userType) {
        if (userType.equals("staff")) {
            // Look for staff in staffList
            Staff staff = findStaff(userID);
            if (staff != null) {
                return staff.getName(); // Return staff's name
            }
        } else if (userType.equals("patient")) {
            // Look for patient in patientList
            Patient patient = findPatient(userID);
            if (patient != null) {
                return patient.getName(); // Return patient's name
            }
        }
        return "Unknown User"; // In case something goes wrong
    }
}
