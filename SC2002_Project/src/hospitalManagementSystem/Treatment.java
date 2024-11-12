package hospitalManagementSystem;

import java.util.Date;

public class Treatment {
    private String treatmentID;
    private String description;
    private Date localDate;

    // Constructor
    public Treatment(String treatmentID, String description, Date localDate) {
        this.treatmentID = treatmentID;
        this.description = description;
        this.localDate = localDate;
    }

    // Getter and Setter for treatmentID
    public String getTreatmentID() {
        return treatmentID;
    }

    public void setTreatmentID(String treatmentID) {
        this.treatmentID = treatmentID;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for localDate
    public Date getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date localDate) {
        this.localDate = localDate;
    }
}