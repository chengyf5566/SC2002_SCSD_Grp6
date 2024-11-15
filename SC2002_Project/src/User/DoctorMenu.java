package User;

import java.util.Scanner;
import java.util.*;
import java.util.Date;

public class DoctorMenu implements UserRoleMenu {
	private Doctor doctor; // Assuming doctor object is passed to this class
	
	// Constructor to initialize the administrator object
    public DoctorMenu(Doctor doctor) {
        this.doctor = doctor;
    }
    
    @Override
    public void displayMenu(Scanner scanner) {
    	
    	// Initialize the doctor with patient and appointment data (assuming you have CSV files loaded)
        doctor.readAndInitializePatient();
        doctor.readAndInitializeAppointments();
        doctor.initializeStaffFromCSV();
        
    	boolean exit = false;

        while (!exit) {
            // Display the menu
            System.out.println("\n--- Doctor Menu ---");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. View Scheduled Appointments (Doctor)");
            System.out.println("3. Confirm or Cancel Appointment Requests From Patient");
            System.out.println("4. Record Patient Appointment Outcome");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");
            System.out.print("Select an option (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character left by nextInt()

            switch (choice) {
                case 1:
                	doctor.viewPatientMedicalRecords();
                    break;
                case 2:
                	doctor.viewDoctorAppointmentRecord();
                    break;
                case 3:
                	doctor.acceptOrDeclineAppointmentSchedule();
                    break;
                case 4:
                	doctor.recordAppointmentOutcome();
                    break;
                case 5:
                	doctor.changePassword(scanner);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice, please select between 1 and 5.");
                    break;
            }
        }
    }
    
    //test usage
    public static void main(String[] args) {
        // Initialize necessary objects
        Scanner scanner = new Scanner(System.in);

        // Create a sample Doctor object (you may need to load data from CSV files here)
        Doctor doctor = new Doctor("D001", "password123", "Doctor", "Male", "Dr. Smith", 40);

        // Initialize the doctor with patient and appointment data (assuming you have CSV files loaded)
        doctor.readAndInitializePatient();
        doctor.readAndInitializeAppointments();

        // Now we create the DoctorMenu object and pass the doctor object to it
        DoctorMenu doctorMenu = new DoctorMenu(doctor);

        // Display the doctor menu and allow the doctor to interact
        doctorMenu.displayMenu(scanner);
        
        // Close scanner after use
        scanner.close();
    }
   
}
