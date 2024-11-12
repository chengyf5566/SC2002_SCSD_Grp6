package hospitalManagementSystem;

import java.util.Scanner;

public class AdministratorInvetoryManagementTest {
	 public static void main(String[] args) {
		  	
	 	String inventoryFilePath = "Medicine_List.csv";
	 	
	 	Scanner scanner = new Scanner(System.in);
	 	
        // Create an Administrator object (you can modify these details as needed)
        Administrator admin = new Administrator("admin123", "password", "administrator", "Male", "Admin", 35);

        // Initialize the medication inventory (assuming a test CSV path)
        admin.initializeInventoryFromCSV(inventoryFilePath);
        System.out.println("Current working directory: " + System.getProperty("user.dir"));
        
        // Test: Manage Medication Inventory
        admin.manageMedicationInventory(scanner);
    }
}
