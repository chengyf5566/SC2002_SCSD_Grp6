package Main;
import Utility.*;
import User.*;
import java.util.List;
import java.util.Scanner;

public class App {
    private List<Staff> staffList;
    private List<Patient> patientList;

    public static void main(String[] args) {
        App app = new App();
        app.initializeData();  // Initialize both staff and patient data
        app.run();  // Run the main program loop

    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        LoginMenu loginMenu = new LoginMenu(staffList, patientList); // Create the LoginMenu instance

        // Display login menu and authenticate the user
        loginMenu.displayMenu(scanner);
        scanner.close();
    }
  
    // Method to initialize staff and patient data
    public void initializeData() {
        staffList = new CsvReaderStaff().getStaffList();
        patientList = new CsvReaderPatient().getPatientList();

    }
}
