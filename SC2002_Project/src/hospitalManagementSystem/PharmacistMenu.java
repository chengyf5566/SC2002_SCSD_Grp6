package hospitalManagementSystem;

import java.util.Scanner;

public class PharmacistMenu implements UserRoleMenu {

    private Pharmacist pharmacist;

    // Constructor to initialize the PharmacistMenu with a Pharmacist object
    public PharmacistMenu(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    @Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        Medication.loadInventoryFromFile();

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
                case 1:
                    pharmacist.viewAppointmentOutcomeRecords(null); // Pass the list when calling
                    break;
                case 2:
                    System.out.print("Enter Appointment ID: ");
                    String appointmentID = scanner.nextLine();
                    System.out.print("Enter Prescription ID: ");
                    String prescriptionID = scanner.nextLine();
                    System.out.print("Enter New Status: ");
                    String newStatus = scanner.nextLine();
                    pharmacist.updatePrescriptionStatus(appointmentID, prescriptionID, newStatus, null); // Pass the list when calling
                    break;
                case 3:
                pharmacist.viewInventory(Medication.getInventory()); // Pass the list when calling
                    break;
                case 4:
                    System.out.print("Enter Medicine Name: ");
                    String medicineName = scanner.nextLine();
                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();
                    pharmacist.submitReplenishmentRequest(medicineName, quantity, Medication.getInventory()); // Pass the list when calling
                    break;
                case 5:
                    pharmacist.viewReplenishmentRequests(null); // Pass the list when calling
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
