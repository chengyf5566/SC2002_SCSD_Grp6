package hospitalManagementSystem;

import java.util.List;
import java.util.Scanner;

public class App {
    private List<Staff> staffList;
    private List<Patient> patientList; // Separate list for patients
    private boolean authenticated = false;
    private int role = 0;
    private String userID;
    private String password;

    // Define file paths
    private String FilePath_Staff = "Staff_List.csv";
    private String FilePath_Patient = "Patient_List.csv";
    
    public static void main(String[] args) {
        App app = new App();
        app.initializeStaff();     // Initialize staff from CSV
        app.initializePatients();  // Initialize patients from CSV
        app.run();                 // Run the main program loop
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

            // Check if the user exists in patient list
            Patient patient = findPatient(userID);
            if (patient != null && patient.getPassword().equals(password)) {
                role = 1;
                authenticated = true;
                System.out.println("Authentication successful. Role: Patient");
            } else {
                // Check if the user exists in staff list
                Staff staff = findStaff(userID);
                if (staff != null && staff.getPassword().equals(password)) {
                    role = assignRole(staff);
                    authenticated = true;
                    System.out.println("Authentication successful. Role: " + staff.getRole());
                } else {
                    System.out.println("Authentication failed. Please try again.");
                }
            }
        }

        // Main menu loop
        boolean exit = false;
        while (!exit) {
            UserRoleMenu menu = null;

            // Determine the appropriate menu based on the user's role
            switch (role) {
                case 1:
                    Patient patient = findPatient(userID);
                    if (patient != null) {
                        menu = new PatientMenu(patient);
                    } else {
                        System.out.println("Patient not found.");
                        exit = true;
                    }
                    break;
                case 2:
                    Doctor doctor = (Doctor) findStaff(userID);
                    if (doctor != null) {
                        menu = new DoctorMenu(doctor);
                    } else {
                        System.out.println("Doctor not found.");
                        exit = true;
                    }
                    break;
                case 3:
                    Pharmacist pharmacist = (Pharmacist) findStaff(userID);
                    if (pharmacist != null) {
                        menu = new PharmacistMenu(pharmacist);
                    } else {
                        System.out.println("Pharmacist not found.");
                        exit = true;
                    }
                    break;
                case 4:
                    Administrator admin = (Administrator) findStaff(userID);
                    if (admin != null) {
                        menu = new AdministratorMenu(admin);
                    } else {
                        System.out.println("Administrator not found.");
                        exit = true;
                    }
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
        csvReaderStaff.readAndInitializeStaff();
        staffList = csvReaderStaff.getStaffList();
    }

    // Method to initialize patient objects from the CSV file
    public void initializePatients() {
        CsvReaderPatient csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readAndInitializePatient();
        patientList = csvReaderPatient.getPatientList();
    }

    // Method to find and return the Patient by userID
    public Patient findPatient(String userID) {
        for (Patient patient : patientList) {
            if (patient.getUserID().equals(userID)) {
                return patient;
            }
        }
        return null;  // Return null if patient is not found
    }

    // Method to find and return the Staff by userID
    public Staff findStaff(String userID) {
        for (Staff staff : staffList) {
            if (staff.getUserID().equals(userID)) {
                return staff;
            }
        }
        return null;  // Return null if staff is not found
    }

    // Method to assign role based on staff type
    private int assignRole(Staff staff) {
        if (staff instanceof Doctor) return 2;
        if (staff instanceof Pharmacist) return 3;
        if (staff instanceof Administrator) return 4;
        return 0;
    }
}
