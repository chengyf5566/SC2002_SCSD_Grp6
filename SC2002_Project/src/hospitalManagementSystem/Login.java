package hospitalManagementSystem;

import java.util.List;

public class Login {

    // Method to authenticate a user by checking both the Staff and Patient lists
    public static String authenticate(String userID, String password, List<Staff> staffList, List<Patient> patientList) {
        // Check if the userID exists in staffList
        for (Staff staff : staffList) {
            if (staff.getUserID().equals(userID)) {
                // If a match is found, check the password
                if (staff.getPassword().equals(password)) {
                    return "staff"; // Return "staff" if valid staff
                }
            }
        }

        // Check if the userID exists in patientList
        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(userID)) {
                // If a match is found, check the password
                if (patient.getPatientPassword().equals(password)) {
                    return "patient"; // Return "patient" if valid patient
                }
            }
        }

        // If no match is found
        return "invalid";  // Return "invalid" if no match
    }
}
