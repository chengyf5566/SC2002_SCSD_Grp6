package hospitalManagementSystem;

import java.util.Date;
import java.util.List;

public class AppointmentOutcome {
    // Attributes
    private Appointment appointment; // Reference to the associated appointment
    private Date date;               // Date of the appointment
    private String serviceType;      // Type of service provided (e.g., consultation, X-ray, blood test etc).
    private List<Prescription> prescriptions; // List of prescriptions //get from medical records
    private String consultationNotes; // Consultation notes
    private String outcome;          // Outcome of the appointment (e.g., "confirmed", "canceled", "completed")

    // Constructor
    public AppointmentOutcome(Appointment appointment, Date date, String serviceType,
                              List<Prescription> prescriptions, String consultationNotes, String outcome) {
        this.appointment = appointment;
        this.date = date;
        this.serviceType = serviceType;
        this.prescriptions = prescriptions;
        this.consultationNotes = consultationNotes;
        this.outcome = outcome;
    }

    // Getters
    public Appointment getAppointment() {
        return appointment;
    }
    
    public Date getDate() {
        return date;
    }
    
    public String getServiceType() {
        return serviceType;
    }
    
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
    
    public String getConsultationNotes() {
        return consultationNotes;
    }
    
    public String getOutcome() {
        return outcome;
    }
    
    //setters
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
}
