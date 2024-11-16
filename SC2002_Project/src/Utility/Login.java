package Utility;

import User.Patient;
import User.Staff;
import java.util.List;

public class Login {

    // Method to authenticate a user by checking both the Staff and Patient lists
    public static String authenticate(String userID, String password, List<Staff> staffList, List<Patient> patientList) {
        for (Staff staff : staffList) {
            if (staff.getUserID().equals(userID)) {          	
                if (staff.getPassword().equals(password)) {
                    return "staff"; 
                }
            }
        }


        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(userID)) {
                if (patient.getPatientPassword().equals(password)) {
                    return "patient"; 
                }
            }
        }

  
        return "invalid";  
    }
}
