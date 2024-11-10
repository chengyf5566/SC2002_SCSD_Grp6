package hospitalManagementSystem;

import java.util.Date;

public class Diagnosis {
    private String diagnosisID;
    private String description;
    private Date date;

    // Constructor
    public Diagnosis(String diagnosisID, String description, Date date) {
        this.diagnosisID = diagnosisID;
        this.description = description;
        this.date = date;
    }

    // Getter and Setter for diagnosisID
    public String getDiagnosisID() {
        return diagnosisID;
    }

    public void setDiagnosisID(String diagnosisID) {
        this.diagnosisID = diagnosisID;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for date
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
