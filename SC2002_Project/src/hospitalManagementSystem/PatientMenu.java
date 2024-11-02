package hospitalManagementSystem;

import java.util.Scanner;

public class PatientMenu implements UserRoleMenu {

    @Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
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

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1: System.out.println("View Medical Record"); break;
                case 2: System.out.println("Update Personal Information"); break;
                case 3: System.out.println("View Available Appointment Slots"); break;
                case 4: System.out.println("Schedule an Appointment"); break;
                case 5: System.out.println("Reschedule an Appointment"); break;
                case 6: System.out.println("Cancel an Appointment"); break;
                case 7: System.out.println("View Scheduled Appointments"); break;
                case 8: System.out.println("View Past Appointment Outcome Records"); break;
                case 9: System.out.println("Logout\n"); exit = true; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }
}
