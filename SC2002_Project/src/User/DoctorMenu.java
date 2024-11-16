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
        doctor.initializeInventoryFromCSV();
        
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

            int choice = 0;

            // Ensure valid input for the menu choice
            boolean validInput = false;
            while (!validInput) {
                System.out.print("Select an option (1-6): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character left by nextInt()
                    if (choice >= 1 && choice <= 6) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid choice, please select a number between 1 and 6.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Consume the invalid input
                }
            }

            // Handle the selected option
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
                    exit = true;
                    break;
                case 6:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    // This block won't be reached due to the validInput check above
                    break;
            }
        }
    }

}
