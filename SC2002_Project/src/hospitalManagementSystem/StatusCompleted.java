package hospitalManagementSystem;

public class StatusCompleted implements AppointmentStatus{
	
	@Override
	public void updateStatus(Appointment appointment) {
        appointment.setStatus("completed");
    }

}
