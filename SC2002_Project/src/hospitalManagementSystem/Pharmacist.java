package hospitalManagementSystem;

<<<<<<< Updated upstream
import java.util.List;

public class Pharmacist extends Staff {
    // Constructor
    public Pharmacist(String name, String userID, String password, char gender, String role, int age) {
        super(name, userID, password, gender, role, age);
    }

    // Method to submit a replenishment request
    public void submitReplenishmentRequest(String medication, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return;
        }

        // Add medicine to the inventory
        inventory.addMedicine(medication, quantity);
        
        // Print successful
        System.out.println("Replenishment request submitted for " + medication + " with quantity " + quantity);
    }
    
    // Method to update prescription status
    public boolean updatePrescriptionStatus(String prescription, String status) {
        return true;
    }

    // Method to view appointment outcomes
    public List<String> viewAppointmentOutcomes() {
    }

    // Method to view medication inventory
    public void viewMedicationInventory() {
        inventory.displayMedicines(); 
=======
public class Pharmacist extends Staff{
    
    public Pharmacist(String name, String userID, String password, char gender, String role, int age) {
        super(name, userID, password, gender, role, age);
    }

    // Method to view all prescriptions
    public void viewPrescriptions() {
    }

    // Method to update the status of a specific prescription
    public void updatePrescriptionStatus(String prescriptionID, String newStatus) {
    }

    // Method to view the current medication inventory
    public void viewInventory() {
    }

    // Method to request replenishment of a specific medicine
    public void submitReplenishmentRequest(String medicineName, int quantity) {
>>>>>>> Stashed changes
    }
}
