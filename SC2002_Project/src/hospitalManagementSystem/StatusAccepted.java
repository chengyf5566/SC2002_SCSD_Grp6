package hospitalManagementSystem;

public class StatusAccepted implements AppointmentStatus {
	
	@Override
	public void updateStatus(Appointment appointment) {
        appointment.setStatus("confirmed");
        // Additional logic for confirming an appointment
    }

}
