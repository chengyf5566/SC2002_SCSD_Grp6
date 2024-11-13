package hospitalManagementSystem;

import java.util.Scanner;

public class PharmacistInventoryTest {
    public static void main(String[] args) {
        // Set up test data for the Pharmacist
        String testUserID = "P123";
        String testPassword = "password123";
        String testRole = "Pharmacist";
        String testGender = "Female";
        String testName = "Alice";
        int testAge = 30;

        // Create an instance of Pharmacist
        Pharmacist pharmacist = new Pharmacist(testUserID, testPassword, testRole, testGender, testName, testAge);

        // Define the path to the CSV file (adjust as needed)
        String inventoryFilePath = "Medicine_List.csv";

        // Initialize the medication inventory from the CSV file
        pharmacist.initializeInventoryFromCSV(inventoryFilePath);

        // Create a scanner instance for user input
        Scanner scanner = new Scanner(System.in);

        // Start managing inventory stock
        pharmacist.manageInventoryStock(scanner);

        // Close the scanner after use
        scanner.close();
    }
}
