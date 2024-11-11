package hospitalManagementSystem;

import java.util.List;

public class Login {

    // Method to authenticate a user and return role-specific values
    public static int authenticate(List<Staff> staffList, String userID, String password) {
        for (Staff staff : staffList) {
            // Check if userID and password match using inherited methods
            if (staff.getUserId().equals(userID) && staff.getPassword().equals(password)) {
                if (staff.getRole().equalsIgnoreCase("Doctor")) {
                    return 2;
                } else if (staff.getRole().equalsIgnoreCase("Pharmacist")) {
                    return 3;
                } else if (staff.getRole().equalsIgnoreCase("Administrator")) {
                    return 4;
                }
            }
        }
        return 0;
    }
}
