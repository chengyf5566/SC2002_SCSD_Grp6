package hospitalManagementSystem;

import java.util.Scanner;

public class DoctorMenu implements UserRoleMenu {

    @Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("6. View Upcoming Appointments");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. Record Appointment Outcome");
            System.out.println("9. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1: System.out.println("View Patient Medical Records"); break;
                case 2: System.out.println("Update Patient Medical Records"); break;
                case 3: System.out.println("View Personal Schedule"); break;
                case 4: System.out.println("Set Availability for Appointments"); break;
                case 5: System.out.println("Accept or Decline Appointment Requests"); break;
                case 6: System.out.println("View Upcoming Appointments"); break;
                case 7: System.out.println("View Scheduled Appointments"); break;
                case 8: System.out.println("Record Appointment Outcome"); break;
                case 9: System.out.println("Logout\n"); exit = true; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }
}
