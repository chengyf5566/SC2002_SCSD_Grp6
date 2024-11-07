package hospitalManagementSystem;
import java.util.Date;

public class Treatment {
    private String treatmentId;
    private String description;
    private Date date;

    public Treatment(String treatmentId, String description, Date date) {
        this.treatmentId = treatmentId;
        this.description = description;
        this.date = date;
    }

    public String getTreatmentId() {  // Add this method
        return treatmentId;
    }

    public void setTreatmentId(String treatmentId) {  // Optional setter
        this.treatmentId = treatmentId;
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