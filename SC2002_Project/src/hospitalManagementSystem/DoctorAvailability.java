package hospitalManagementSystem;

import java.util.*;

public class DoctorAvailability implements AvailabilityManager{
	
	private List<AppointmentSlot> availablitySlots; // list of slots doctor is free at
	private final Doctor doctor;
	
	public DoctorAvailability(Doctor doctor)
	{
		this.doctor = doctor;
	}
	
	// *********** APPOINTMENT SLOTS  *********** // 
	
		// GETTERS
		
		// get all slots on the doctors side
	
		public List<AppointmentSlot> getAllAvailableSlots(){
			return availablitySlots;
		}
		
		// get all slots that are FREE --> for doctor
		public List<AppointmentSlot> getAllFreeAvailableSlots(){
			List<AppointmentSlot> freeSlots = new ArrayList<>(); 
			for(AppointmentSlot slot : availablitySlots) {
				if (slot.getIsAvailable() == true) {
					freeSlots.add(slot);
				}
			}
			return freeSlots;
		}
		
		// get all available slots after current time --> for patient & doctor
		public List<AppointmentSlot> getAllUpcomingAvailableSlots(){
			List<AppointmentSlot> upcomingSlots = new ArrayList<>(); 
			Date now = DateUtil.getCurrentDate();
			for(AppointmentSlot slot : availablitySlots) {
				if (slot.getIsAvailable() == true && slot.getDate().after(now)) {
					upcomingSlots.add(slot);
				}
			}
			return upcomingSlots;
		}
		
		// is the slot available

		// SETTERS
		
		// set/create a new slot and add it to avaliblitySlot <<private>>
		public void setAvailabileSlot(Date date, String timeSlot) {
			availablitySlots.add(new AppointmentSlot(date, timeSlot, true));
		}
		
		// set unavailability of a slot
		public void setUnavailability(AppointmentSlot slot) {
			slot.setIsAvailable(false);
		}
		
		// set availablity of a slot
		public void setAvailability(AppointmentSlot slot) {
				slot.setIsAvailable(true);
		}
		
		// set availability for a specific slot in the availability slot list <<private>>
		public void setSlotAvailability(Date date, String timeSlot, boolean isAvailable) {
	        for (AppointmentSlot slot : availablitySlots) {
	            if (slot.getDate().equals(date) && slot.getTimeSlot().equals(timeSlot)) {
	                slot.setIsAvailable(isAvailable);
	                break;
	            }
	        }
	    }
		
		// HELPERS/TESTING
		// get the current date
		

}
