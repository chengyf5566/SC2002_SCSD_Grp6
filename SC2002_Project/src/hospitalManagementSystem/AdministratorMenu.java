package hospitalManagementSystem;

import java.util.Scanner;

public class AdministratorMenu implements UserRoleMenu {
    private Administrator administrator; // Assuming administrator object is passed to this class

    // Constructor to initialize the administrator object
    public AdministratorMenu(Administrator administrator) {
        this.administrator = administrator;
    }
    
	@Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View Appointments details");
            System.out.println("3. View and Manage Medication Inventory");
            System.out.println("4. Approve Replenishment Requests");
            System.out.println("5. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1: 
                	administrator.manageHospitalStaff(scanner); //method for managing hospital staff
                	break;
                case 2: 
                	administrator.viewAppointments();
                	break;
                case 3: 
                	administrator.manageMedicationInventory(scanner);
                	break;
                case 4: 
                	administrator.manageMedicationInventory(scanner);
                	break;
                case 5: 
                	System.out.println("Logout\n"); 
                	exit = true; 
                	break;
                default: 
                	System.out.println("Invalid option. Try again.");
            }
        }
    }

}


