package User;

import java.util.List;
import java.util.Scanner;

public class AdministratorMenu implements UserRoleMenu {
    private Administrator administrator; // Assuming administrator object is passed to this class

    // Constructor to initialize the administrator object
    public AdministratorMenu(Administrator administrator) {
        this.administrator = administrator;
    }
    
	@Override
    public void displayMenu(Scanner scanner) {
		
		// Initialize csv files
		administrator.readAndInitializePatient();
		administrator.readAndInitializeAppointments();
		administrator.initializeStaffFromCSV();
		administrator.initializeInventoryFromCSV();
		
		
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAdministrator Menu:");
            System.out.println("1. View and Manage Hospital Staff");
            System.out.println("2. View and Manage Hospital Patients");
            System.out.println("3. View Appointments details");
            System.out.println("4. View and Manage Medication Inventory");
            System.out.println("5. Change Password");
            System.out.println("6. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1: 
                	administrator.manageHospitalStaff(scanner); //method for managing hospital staff
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
	

	
	//example usage
	public static void main(String[] args) {
        // Example Administrator object with dummy values
		Administrator admin = new Administrator("admin01", "adminPassword", "Administrator", "Male", "John Doe", 35);
		
		// Initialize csv files
		admin.readAndInitializePatient();
		admin.readAndInitializeAppointments();
		admin.initializeStaffFromCSV();
		admin.initializeInventoryFromCSV();
		
        // Creating an instance of the AdministratorMenu class and passing the admin object
        AdministratorMenu adminMenu = new AdministratorMenu(admin);
        
        // Passing Scanner object to the displayMenu method for user input
        Scanner scanner = new Scanner(System.in);
        
        // Calling the displayMenu method to show the menu to the administrator
        adminMenu.displayMenu(scanner);
        
        scanner.close();
    }

}


