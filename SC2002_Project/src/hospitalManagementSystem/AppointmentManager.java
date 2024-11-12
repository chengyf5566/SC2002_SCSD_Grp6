package hospitalManagementSystem;

import java.util.List;

public interface AppointmentManager {
	
	List<Appointment> getAllAppointments();
	void setAppointment(Appointment appointment);
	void setRemoveAppointment(String appointmentID);
	void setAppointmentStatus(Appointment appointment, AppointmentStatus status);

}
