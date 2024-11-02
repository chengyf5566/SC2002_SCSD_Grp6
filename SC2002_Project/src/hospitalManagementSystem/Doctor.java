package hospitalManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Doctor extends User {
    private String specialty;
    private List<AppointmentSlot> availableSlots;

    public Doctor(String userID, String password, String specialty) {
        super(userID, password, "Doctor"); // Assuming "Doctor" is the role
        this.specialty = specialty;
        this.availableSlots = new ArrayList<>(); // Initialize the list of available slots
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public List<AppointmentSlot> getAvailableSlots() {
        return availableSlots;
    }

    public void addAvailableSlot(AppointmentSlot slot) {
        availableSlots.add(slot);
    }

    // Additional methods related to the Doctor's functionality
}