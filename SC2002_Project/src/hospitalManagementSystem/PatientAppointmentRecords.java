package hospitalManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class PatientAppointmentRecords {
    private List<AppointmentOutcome> outcomes; 

    // Constructor
    public PatientAppointmentRecords() {
        this.outcomes = new ArrayList<>(); 
    }

    // Method to add an appointment outcome
    public void addOutcome(AppointmentOutcome outcome) {
        outcomes.add(outcome);
    }

    // Get list of appointment outcomes
    public List<AppointmentOutcome> getOutcomes() {
        return outcomes;
    }

    // Get specific appointment outcome 
    public AppointmentOutcome getOutcome(int index) {
        if (index >= 0 && index < outcomes.size()) {
            return outcomes.get(index);
        }
        return null; 
    }
}
