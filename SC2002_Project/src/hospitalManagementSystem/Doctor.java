package hospitalManagementSystem;

import java.util.*;

public class Doctor extends Staff {
	
	private DoctorAvailability availability; 
	private DoctorAppointments appointments; 
	
    public Doctor(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }						
	
	public DoctorAvailability getAvailability() {
        return availability;
    }
	
	public DoctorAppointments getAppointments() {
        return appointments;
    }

	
	// get doctor details
	public void getAllDetails() {
		String userID = getUserID();
		String role = getRole();
		String gender = getGender();
		String name = getName();
		int age = getAge();
		
		System.out.println("Here is the information about user " + userID);
		System.out.println("Role: " + role);
		System.out.println("Name: " + name);
		System.out.println("Age: " + age);
		System.out.println("Gender: " + gender + "\n");
	}

}