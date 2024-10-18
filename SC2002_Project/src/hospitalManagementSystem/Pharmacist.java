package hospitalManagementSystem;

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
    }
}
    