package hospitalManagementSystem;

public class StatusDeclined implements AppointmentStatus {
	
	@Override
	public void updateStatus(Appointment appointment) {
        appointment.setStatus("cancelled");
    }

}
