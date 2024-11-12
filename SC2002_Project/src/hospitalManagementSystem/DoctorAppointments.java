package hospitalManagementSystem;

import java.util.*;

public class DoctorAppointments implements AppointmentManager{
	
	// local variables
	private List<Appointment> appointments; // list of appointments assigned to the doctor
	private final Doctor doctor;
		
	// constructor
	public DoctorAppointments(Doctor doctor) 
	{
		this.doctor = doctor;
	
	}
	
	// *********** APPOINTMENTS  *********** //
	
	// GETTERS
	
	// get all appointments
	public List<Appointment> getAllAppointments(){
		return appointments;
	}
	
	// get specific appointment based on Appointment ID from my own appointment list
	public Appointment getSpecificAppointment(String appointmentID) {			
		// loop through until required appointment based on AppointmentID is found
		for (Appointment appointment : appointments) {
			// Check if the AppointmetnID in the appointment matches the provided ID
		        if (appointment.getAppointmentID().equals(appointmentID)) {
		            return appointment;
		        }
		 }
		return null;
	}
	
	// get appointments past current time --> upcoming appointments
	public List<Appointment> getUpcomingApppointments(){
		List<Appointment> upcomingAppointments = new ArrayList<>();
		
		Date now = DateUtil.getCurrentDate();
        
        for (Appointment appointment : appointments) {
            if (appointment.getDate().after(now) ) {
                upcomingAppointments.add(appointment);
            }
        }
        return upcomingAppointments;

	}
	
	// get appointments past current time --> upcoming appointments that are confirmed
		public List<Appointment> getUpcomingApppointmentsConfirmed(){
			List<Appointment> upcomingAppointmentsConfirmed = new ArrayList<>();
			
			Date now = DateUtil.getCurrentDate();
	        
	        for (Appointment appointment : appointments) {
	            if (appointment.getDate().after(now) && appointment.getStatus() == "confirmed" ) {
	                upcomingAppointmentsConfirmed.add(appointment);
	            }
	        }
	        return upcomingAppointmentsConfirmed;
		}

	// SETTERS
	
	// add appointment to appointments list --> to be used by the patient when they create a new appointment
	public void setAppointment(Appointment appointment){
		this.appointments.add(appointment);
	} // done
	
	// remove appointment from appointments list --> to be used by doctor for cleaning 
	public void setRemoveAppointment(String appointmentID){ // removes based on the index --> find the index based on the appointment number
		int i=0;
		for (Appointment appointment : appointments) {
			if (appointment.getAppointmentID().equals(appointmentID)) {
				appointments.remove(i);
			}
			i++;
		}
	}
	
	// update/accept appointment from appointments list
	public boolean setAppointmentAccept(Appointment appointment) {
		// get the list of availability slots
		List<AppointmentSlot> availablitySlots = new ArrayList<>();
		availablitySlots = doctor.getAvailability().getAllAvailableSlots();
		if (availablitySlots.stream().anyMatch(slot -> 
		slot.getDate().equals(appointment.getDate()) && // check if the slot in doctors' date is the same appointment date
        slot.getTimeSlot().equals(appointment.getTimeSlot()) && // check if the slot in doctors' time is the same appointment slot time
        slot.getIsAvailable())) // check if the slot in doctors' is isAvailable set to `true`
		{
	        //appointments.add(appointment);
	        //appointment.updateStatus("confirmed"); // confirm on patient and doctors side
	        setAppointmentStatus(appointment, new StatusAccepted());
	        
	        doctor.getAvailability().setSlotAvailability(appointment.getDate(), appointment.getTimeSlot(), false); // the slot is no more free on docs side
	        System.out.println("appointment confirmed successfully");
	        return true;
	    }
		
		System.out.println("appointment cannot be confirmed. Conflict in availability of doctor");
	    return false;
	}

	// set/update appointment status
	public void setAppointmentStatus(Appointment appointment, AppointmentStatus status) {
		status.updateStatus(appointment);
	}
	// usage --> setAppointmentStatus(appointment, new StatusAccepted());
	// usage --> setAppointmentStatus(appointment, new StatusDeclined());
	// usage --> setAppointmentStatus(appointment, new StatusCompleted());
	
	// HELPER/TESTING
	
	
	// get patient details for upcoming confirmed appointments
	public void getPatientDetails() {
		List<Appointment> confirmedUpcoming = new ArrayList<>();
		confirmedUpcoming = getUpcomingApppointmentsConfirmed();
		
		for(Appointment appointment : confirmedUpcoming) {
			// appointment details
			//System.out.println("Here are the Appointment details for the patient " + appointment.getPatient());
			System.out.println("Appointment ID " + appointment.getAppointmentID());
			System.out.println("Appointment Status " + appointment.getStatus());
			System.out.println("Appointment Date " + appointment.getDate());
			System.out.println("Appointment Time Slot " + appointment.getTimeSlot());
			System.out.println(" ");
			
			// patient details
			Patient patient = appointment.getPatient();
			System.out.println("Here are the patient details for the appointment " + appointment.getAppointmentID());
			System.out.println("Patients Name " + patient.getName());
			System.out.println("Patients Email Address " + patient.getEmailAddress());
			System.out.println("Patients DOB " + patient.getDateOfBirth());
			System.out.println("Patients Blood Type " + patient.getBloodType());
			System.out.println("Patients Contact Number " + patient.getContactNum());
			System.out.println(" ");
		}
	}
	
	
	
}

