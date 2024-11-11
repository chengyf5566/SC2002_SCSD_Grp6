package hospitalManagementSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderStaff {
    // List to store staff members
    private List<Staff> staffList = new ArrayList<>();

    // Method to read data from CSV and create staff objects
    public void readAndInitializeStaff(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length < 6) {
                    System.out.println("Incomplete record: " + line);
                    continue;
                }

                String userID = values[0].trim();
                String password = values[1].trim();
                String name = values[2].trim();
                String role = values[3].trim();
                String gender = values[4].trim();
                int age;

                try {
                    age = Integer.parseInt(values[5].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid age for userID " + userID + ": " + values[5]);
                    continue;
                }

                // Create specific Staff objects based on role
                Staff staff = null;
                switch (role.toLowerCase()) {
                    case "doctor":
                        //staff = new Doctor(userID, password, role, gender, name, age);
                        break;
                    case "pharmacist":
                        staff = new Pharmacist(userID, password, role, gender, name, age);
                        break;
                    case "administrator":
                        //staff = new Administrator(userID, password, role, gender, name, age);
                        break;
                    default:
                        System.out.println("Unknown role: " + role);
                        continue;
                }

                // Add the created staff member to the list
                staffList.add(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for the staff list
    public List<Staff> getStaffList() {
        return staffList;
    }
}
