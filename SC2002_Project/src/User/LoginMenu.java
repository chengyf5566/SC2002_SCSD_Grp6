package User;

import Utility.*;
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
    
    

    @Override
    public void displayMenu(Scanner scanner) {
        while (true) {  
            authenticated = false;

            while (!authenticated) {
                System.out.println("--- Welcome to Hospital Management App ---");
                System.out.println("--- Login Menu ---");

                System.out.print("Please Enter User ID: ");
                userID = scanner.nextLine().trim();
                while (userID.isEmpty()) {
                    System.out.println("User ID cannot be empty. Please try again.");
                    System.out.print("Please Enter User ID: ");
                    userID = scanner.nextLine().trim();
                }

                System.out.print("Please Enter Password: ");
                password = scanner.nextLine().trim();
                while (password.isEmpty()) {
                    System.out.println("Password cannot be empty. Please try again.");
                    System.out.print("Please Enter Password: ");
                    password = scanner.nextLine().trim();
                }

                // Hash the password
                password = PasswordHashing.hashPassword(password);

                Login login = new Login();
                String userType = login.authenticate(userID, password, staffList, patientList);

                if (!userType.equals("invalid")) {
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

////////////////////////////handleRoleMenu////////////////////////////  
    private void handleRoleMenu(Scanner scanner, String userType, String userID) {
        UserRoleMenu menu = null;

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

        if (menu != null) {
            menu.displayMenu(scanner);
        }
    }

////////////////////////////find patient////////////////////////////  
    private Patient findPatient(String userID) {
        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(userID)) {
                return patient;
            }
        }
        return null;
    }

////////////////////////////find staff////////////////////////////  
    private Staff findStaff(String userID) {
        for (Staff staff : staffList) {
            if (staff.getUserID().equals(userID)) {
                return staff;
            }
        }
        return null;
    }

////////////////////////////get user name////////////////////////////  
    private String getUserName(String userID, String userType) {
        if (userType.equals("staff")) {
            Staff staff = findStaff(userID);
            if (staff != null) {
                return staff.getName(); 
            }
        } else if (userType.equals("patient")) {
            Patient patient = findPatient(userID);
            if (patient != null) {
                return patient.getName();
            }
        }
        return "Unknown User"; 
    }
}
