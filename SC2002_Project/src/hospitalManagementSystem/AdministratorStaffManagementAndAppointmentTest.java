package hospitalManagementSystem;

import java.util.Scanner;

public class AdministratorStaffManagementAndAppointmentTest {
    public static void main(String[] args) {
    	
    	// Define the file path for the CSV files
        String staffFilePath = "Staff_List.csv";
        String appointmentFilePath = "Appointment_Outcome.csv";

        Scanner scanner = new Scanner(System.in);

        // Create a sample administrator
        Administrator admin = new Administrator("admin1", "password123", "administrator", "Male", "John Doe", 40);

        // Initialize staff from the CSV file (ensure this file is in the correct path)
        //admin.initializeStaffFromCSV(staffFilePath);
        //System.out.println("Staff initialized.");
        
        // Initialize appointments from the CSV file (ensure this file exists and is in the correct path)
        admin.readAndInitializeAppointments(appointmentFilePath);
        System.out.println("Appointments initialized.");
        
        // Test viewAppointments method
        //System.out.println("\nTesting viewAppointments method:");
        admin.viewAppointments();  // This will print the appointments

        // Optionally, you can simulate the "manageHospitalStaff" method as well
        //admin.manageHospitalStaff(scanner);
        
        scanner.close();
    }
}
