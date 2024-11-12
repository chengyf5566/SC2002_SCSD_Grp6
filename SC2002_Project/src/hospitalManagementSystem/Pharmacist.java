package hospitalManagementSystem;

import java.util.List;

public class Pharmacist extends Staff {

    // Constructor for the Pharmacist class
    public Pharmacist(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }


    // Method to view all prescriptions from appointment outcomes
    public void viewAppointmentOutcomeRecords(List<AppointmentOutcome> outcomes) {
    }

    // Method to update the status of a specific prescription by ID
    public void updatePrescriptionStatus(String appointmentID, String prescriptionID, String newStatus, List<AppointmentOutcome> outcomes) {
        boolean appointmentFound = false;
        boolean found = false; 
    
        // Iterate through each AppointmentOutcome
        for (AppointmentOutcome outcome : outcomes) {
            // Check if the appointment ID matches the given ID
            if (outcome.getAppointment().getAppointmentID().equals(appointmentID)) {
                appointmentFound = true; // Appointment ID found
                // Iterate through prescriptions within the current AppointmentOutcome
                for (Prescription prescription : outcome.getPrescriptions()) {
                    // Check if the prescription ID matches the given ID
                    if (prescription.getPrescriptionID().equals(prescriptionID)) {
                        // Update the prescription's status
                        prescription.setPrescriptionStatus(newStatus);
                        System.out.println("Updated Prescription ID " + prescriptionID + " in Appointment ID " + appointmentID + " to status: " + newStatus);
                        found = true;
                        break;
                    }
                }
                if (found) 
                    break;
            }
        }
    
        // Error message if appointment ID was not found
        if (!appointmentFound) {
            System.out.println("Appointment ID " + appointmentID + " not found.");
        } else if (!found) {
            // Error message if the appointment was found but the prescription ID was not updated
            System.out.println("Prescription ID " + prescriptionID + " not found in Appointment ID " + appointmentID + ".");
        }
    }

    // Method to view the current medication inventory
    public void viewInventory(List<Medication> inventory) {
        System.out.println("Medication Inventory:");
        if (inventory.isEmpty()) {
            System.out.println("No medications found in inventory.");
        } else {
            for (Medication med : inventory) {
                System.out.println("Medication Name: " + med.getName() + ", Stock: " + med.getQuantity() +
                                   ", Low Stock Alert: " + med.getLowStockAlert());
            }
        }
    }

    // Method to request replenishment of a specific medicine
    public void submitReplenishmentRequest(String medicineName, int quantity, List<Medication> inventory) {
        boolean found = false;
        
        // Iterate through the medication inventory
        for (Medication med : inventory) {
            // Check for a case-insensitive match with the medication name
            if (med.getName().equalsIgnoreCase(medicineName)) {
                // Medication found, create a replenishment request
                ReplenishmentRequest request = new ReplenishmentRequest(med, quantity);
                ReplenishmentRequest.addRequest(request); // Submit the replenishment request
                System.out.println("Replenishment for " + quantity + " units of " + medicineName + " submitted.");
                found = true;
                break;
            }
        }
        // Error messageif the medication was not found in the inventory
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
