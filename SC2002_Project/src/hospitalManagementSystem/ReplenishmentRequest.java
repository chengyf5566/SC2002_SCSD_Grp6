package hospitalManagementSystem;

import java.time.LocalDate;

public class ReplenishmentRequest{

    private Medication medication;
    private int quantityRequested;
    private LocalDate requestDate;
    private String status; // e.g., "Pending", "Approved", "Rejected"

    // Constructor
    public ReplenishmentRequest(Medication medication, int quantityRequested) {
        this.medication = medication;
        this.quantityRequested = quantityRequested;
        this.requestDate = LocalDate.now();  // Sets the request date to today
        this.status = "Pending";  // Default status is pending
    }

    // Getter for medication
    public Medication getMedication() {
        return medication;
    }

    // Setter for medication
    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    // Getter for quantityRequested
    public int getQuantityRequested() {
        return quantityRequested;
    }

    // Setter for quantityRequested
    public void setQuantityRequested(int quantityRequested) {
        this.quantityRequested = quantityRequested;
    }

    // Getter for requestDate
    public LocalDate getRequestDate() {
        return requestDate;
    }

    // Getter for status
    public String getStatus() {
        return status;
    }

    // Setter for status
    public void setStatus(String status) {
        this.status = status;
    }

    // Method to approve the request
    public void approveRequest() {
        this.status = "Approved";
    }

    // Method to reject the request
    public void rejectRequest() {
        this.status = "Rejected";
    }

}
