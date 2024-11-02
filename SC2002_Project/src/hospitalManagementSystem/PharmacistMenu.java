package hospitalManagementSystem;

import java.util.Scanner;

public class PharmacistMenu implements UserRoleMenu {

    @Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medical Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. View Replenishment Request");
            System.out.println("6. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1: System.out.println("View Appointment Outcome Record"); break;
                case 2: System.out.println("Update Prescription Status"); break;
                case 3: System.out.println("View Medical Inventory"); break;
                case 4: System.out.println("Submit Replenishment Request"); break;
                case 5: System.out.println("View Replenishment Request"); break;
                case 6: System.out.println("Logout\n"); exit = true; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }
}
