package hospitalManagementSystem;

import java.util.Date;

public class Diagnosis {
    private String diagnosisId;
    private String description;
    private Date date;

    public Diagnosis(String diagnosisId, String description, Date date) {
        this.diagnosisId = diagnosisId;
        this.description = description;
        this.date = date;
    }

    public String getDiagnosisId() {  // Add this method
        return diagnosisId;
    }

    public void setDiagnosisId(String diagnosisId) {  // Optional setter
        this.diagnosisId = diagnosisId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}