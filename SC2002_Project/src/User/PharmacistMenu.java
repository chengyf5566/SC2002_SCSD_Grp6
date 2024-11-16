package User;

import java.util.Scanner;

public class PharmacistMenu implements UserRoleMenu {

    private Pharmacist pharmacist;

    // Constructor to initialize the PharmacistMenu with a Pharmacist object
    public PharmacistMenu(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    @Override
    public void displayMenu(Scanner scanner) {
        // Initialize the pharmacist with inventory, appointments, and staff data
        pharmacist.initializeInventoryFromCSV();
        pharmacist.readAndInitializeAppointments();
        pharmacist.initializeStaffFromCSV();
        
        boolean exit = false;

        while (!exit) {
            System.out.println("\nPharmacist Menu:");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Dispense Medication");
            System.out.println("3. Manage Inventory Stock");
            System.out.println("4. Change Password");
            System.out.println("5. Logout");

            int input = -1;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Select Option: ");
                
                if (scanner.hasNextInt()) {
                    input = scanner.nextInt();
                    scanner.nextLine();  
                    
                    if (input >= 1 && input <= 5) {
                        validInput = true; 
                    } else {
                        System.out.println("Invalid option. Please select a number between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); 
                }
            }

            switch (input) {
                case 1:
                    pharmacist.viewAppointments(); 
                    break;
                case 2:
                    pharmacist.dispenseMedication(scanner); 
                    break;
                case 3:
                    pharmacist.manageInventoryStock(scanner); 
                    break;
                case 4:
                    pharmacist.changePassword(scanner); 
                    exit = true; 
                    break;
                case 5:
                    System.out.println("Logging out...\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
                    break;
            }
        }
    }
}
