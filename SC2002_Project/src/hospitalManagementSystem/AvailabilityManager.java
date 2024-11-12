package hospitalManagementSystem;

import java.util.Date;
import java.util.List;

public interface AvailabilityManager {
	List<AppointmentSlot> getAllAvailableSlots();
	void setAvailabileSlot(Date date, String timeSlot); // create a new slot
	void setSlotAvailability(Date date, String timeSlot, boolean isAvailable); //update a slot
}
