package hospitalManagementSystem;

import java.util.Date;

public class AppointmentSlot {

    private Date date;            
    private String timeSlot;     // eg. 1200 as 12pm , 2400 as 12am
    private Boolean isAvailable;   

    // Constructor
    public AppointmentSlot(Date date, String timeSlot, Boolean isAvailable) {
        this.date = date;
        this.timeSlot = timeSlot;
        this.isAvailable = isAvailable;
    }

    // Getters and Setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
}
