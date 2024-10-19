package hospitalManagementSystem;

import java.util.Date;

public class Appointment{
	private String appointmentID;
	private Doctor doctor;
	private Patient patient;
	private Date date;
	private String timeslot; // eg. 1200 as 12pm , 2400 as 12am
	private String status;
	
	// Constructor
    public Appointment(String appointmentID, Doctor doctor, Patient patient, Date date, String timeSlot, String status) {
        this.appointmentID = appointmentID;
        this.doctor = doctor;
        this.patient = patient;
        this.date = date;
        this.timeslot = timeSlot;
        this.status = status;
    }

    // Method to update the appointment status
    public boolean updateStatus(String newStatus) {
        if (newStatus == null) {
            return false; 
        }
        
        String statusToUpdate = newStatus.toLowerCase();

        // Check if the new status is valid
        switch (statusToUpdate) {
            case "confirmed":
            	this.status = newStatus; 
            	return true; 
            case "canceled":
            	this.status = newStatus; 
            	return true; 
            case "completed":
            	this.status = newStatus; 
                return true; 
            default:
                return false; // Invalid
        }
    }

    // Getter
    public String getStatus() {
        return status;
    }

    public String getAppointmentID() {
        return appointmentID;
    }

    public Date getDate() {
        return date;
    }

    public String getTimeSlot() {
        return timeslot;
    }
}
