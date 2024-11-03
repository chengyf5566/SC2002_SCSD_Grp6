package hospitalManagementSystem;

import java.util.Scanner;

public class AdministratorMenu implements UserRoleMenu {
	@Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments details");
            System.out.println("3. View Personal Schedule");
            System.out.println("4. Set Availability for Appointments");
            System.out.println("5. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1: System.out.println("View and Manage Hospital Staff"); break;
                case 2: System.out.println("View Appointments details"); break;
                case 3: System.out.println("View and Manage Medication Inventory"); break;
                case 4: System.out.println("Approve Replenishment Requests"); break;
                case 5: System.out.println("Logout\n"); exit = true; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }

}


