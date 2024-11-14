package hospitalManagementSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderStaff {
    
    // File path declared within the class
    private String filePath = "Staff_List.csv";
    

    public CsvReaderStaff() {
    	readAndInitializeStaff();
    }

    // List to store staff members
    private List<Staff> staffList = new ArrayList<>();

    // Method to read data from CSV and create staff objects
    public void readAndInitializeStaff() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Check if the record contains all 6 expected columns
                if (values.length < 6) {
                    System.out.println("Incomplete record: " + line);
                    continue;
                }

                // Extract and trim the values from the CSV
                String userID = values[0].trim();
                String password = values[1].trim();
                String name = values[2].trim();
                String role = values[3].trim();
                String gender = values[4].trim();
                int age;

                // Try parsing the age and handle invalid data
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
                        staff = new Doctor(userID, password, role, gender, name, age);
                        break;
                    case "pharmacist":
                        staff = new Pharmacist(userID, password, role, gender, name, age);
                        break;
                    case "administrator":
                        staff = new Administrator(userID, password, role, gender, name, age);
                        break;
                    default:
                        System.out.println("Unknown role: " + role);
                        continue;
                }

                // Add the created staff member to the staff list
                if (staff != null) {
                    staffList.add(staff);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for the staff list
    public List<Staff> getStaffList() {
        return staffList;
    }
    
    // Method to write staff to CSV
    public void writeStaffToCSV() {
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath);
            return; // Do not attempt to create new directories or files
        }

        // Proceed with writing to the existing file
        try (FileWriter writer = new FileWriter(file)) {
            // Write the header
            writer.write("UserID,Password,Name,Role,Gender,Age\n");

            // Write each staff member's data
            for (Staff staff : staffList) {
                writer.write(staff.getUserID() + ","
                        + staff.getPassword() + ","
                        + staff.getName() + ","
                        + staff.getRole() + ","
                        + staff.getGender() + ","
                        + staff.getAge() + "\n");
            }

            System.out.println("Staff list updated in CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
