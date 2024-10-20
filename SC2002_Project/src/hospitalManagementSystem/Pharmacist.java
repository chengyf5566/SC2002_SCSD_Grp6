package hospitalManagementSystem;

import java.util.List;

public class Pharmacist extends Staff {

    // Constructor for the Pharmacist class
    public Pharmacist(String name, String userID, String password, char gender, String role, int age) {
        super(name, userID, password, gender, role, age);
    }

    // Method to view all prescriptions from appointment outcomes
    public void viewAppointmentOutcomeRecords(List<AppointmentOutcome> outcomes) {
    }

    // Method to update the status of a specific prescription by ID
    public void updatePrescriptionStatus(String prescriptionID, String newStatus, List<AppointmentOutcome> outcomes) {
        boolean updated = false; // Flag to track if the prescription was updated

        // Iterate through each AppointmentOutcome
        for (AppointmentOutcome outcome : outcomes) {
            // Iterate through prescriptions within the current AppointmentOutcome
            for (Prescription prescription : outcome.getPrescriptions()) {
                // Check if the prescription ID matches the given ID
                if (prescription.getPrescriptionID().equals(prescriptionID)) {
                    // Update the prescription's status
                    prescription.setPrescriptionStatus(newStatus);
                    System.out.println("Updated Prescription ID " + prescriptionID + " to status: " + newStatus);
                    updated = true; // Set updated flag to true
                    break; // Exit inner loop
                }
            }
            if (updated) 
                break; // Exit outer loop if the prescription was updated
        }
        // If no prescription was updated, notify the user
        if (!updated) {
            System.out.println("Prescription ID " + prescriptionID + " not found.");
        }
    }

    // Method to view the current medication inventory
    public void viewInventory(List<Medication> inventory) {
        System.out.println("Medication Inventory:");
        // Iterate through each medication in the inventory
        for (Medication med : inventory) {
            // Display the medication name and available stock
            System.out.println("Medication Name: " + med.getName() + ", Stock: " + med.getQuantity());
        }
    }

    // Method to request replenishment of a specific medicine
    public void submitReplenishmentRequest(String medicineName, int quantity, List<Medication> inventory) {
        boolean found = false; // Flag to track if the medication was found in inventory
        
        // Iterate through the medication inventory
        for (Medication med : inventory) {
            // Check for a case-insensitive match with the medication name
            if (med.getName().equalsIgnoreCase(medicineName)) {
                // Medication found, create a replenishment request
                ReplenishmentRequest request = new ReplenishmentRequest(med, quantity);
                ReplenishmentRequest.addRequest(request); // Submit the replenishment request
                System.out.println("Replenishment for " + quantity + " units of " + medicineName + " submitted.");
                found = true; // Set found flag to true
                break; // Exit the loop after the request is submitted
            }
        }
        // Notify if the medication was not found in the inventory
        if (!found) {
            System.out.println("Medication " + medicineName + " not found in the inventory.");
        }
    }

    // Method to view all replenishment requests
    public void viewReplenishmentRequests(List<ReplenishmentRequest> requestList) {
        System.out.println("Listing all replenishment requests:");
        // Iterate through each replenishment request in the request list
        for (ReplenishmentRequest request : requestList) {
            // Display details about each replenishment request
            System.out.println("Medication: " + request.getMedication().getName() 
                + ", Quantity: " + request.getQuantityRequested() 
                + ", Date: " + request.getRequestDate() 
                + ", Status: " + request.getStatus());
        }
    }
}
