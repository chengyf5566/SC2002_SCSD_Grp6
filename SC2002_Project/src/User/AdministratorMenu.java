package User;

import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

public class AdministratorMenu implements UserRoleMenu {
    private Administrator administrator; 

    // Constructor to initialize the administrator object
    public AdministratorMenu(Administrator administrator) {
        this.administrator = administrator;
    }
    
	@Override
    public void displayMenu(Scanner scanner) {

        

        boolean exit = false;
        while (!exit) {
        	// Initialize csv files
            administrator.readAndInitializePatient();
            administrator.readAndInitializeAppointments();
            administrator.initializeStaffFromCSV();
            administrator.initializeInventoryFromCSV();
        	
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View and Manage Hospital Patients");
            System.out.println("3. View Appointments details");
            System.out.println("4. View and Manage Medication Inventory");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");

            int input = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Select Option: ");
                try {
                    input = scanner.nextInt();
                    scanner.nextLine(); 
                    if (input < 1 || input > 6) {
                        System.out.println("Invalid option. Please select a number between 1 and 6.");
                    } else {
                        validInput = true; 
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine(); 
                }
            }

            switch (input) {
                case 1: 
                    administrator.manageHospitalStaff(scanner); 
                    break;
                case 2: 
                    administrator.manageHospitalPatient(scanner);
                    break;
                case 3: 
                    administrator.viewAppointments();
                    break;
                case 4: 
                    administrator.manageMedicationInventory(scanner);
                    break;
                case 5: 
                    administrator.changePassword(scanner);
                    exit = true; 
                    break;
                case 6: 
                    System.out.println("Logout\n"); 
                    exit = true; 
                    break;
                default: 
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}


