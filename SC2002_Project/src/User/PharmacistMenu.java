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
    	// Initialize the doctor with patient and appointment data (assuming you have CSV files loaded)
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

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

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
                    System.out.println("Logout\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    //example usage
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Example pharmacist creation
        Pharmacist pharmacist = new Pharmacist("P001", "securePassword", "Pharmacist", "Female", "Jane Doe", 35);

        // Initialize pharmacist's inventory and appointments from CSV files
        pharmacist.initializeInventoryFromCSV();
        pharmacist.readAndInitializeAppointments();

        // Create the PharmacistMenu and pass the pharmacist instance
        PharmacistMenu pharmacistMenu = new PharmacistMenu(pharmacist);

        // Display the pharmacist's menu
        pharmacistMenu.displayMenu(scanner);

        scanner.close();
    }
}
