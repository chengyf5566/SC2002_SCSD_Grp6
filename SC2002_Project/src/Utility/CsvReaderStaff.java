package Utility;
import User.Administrator;
import User.Doctor;
import User.Pharmacist;
import User.Staff;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderStaff implements CsvReader {
    

    private String filePath = "Staff_List.csv";

    // Constructor to initialize and read the staff list
    public CsvReaderStaff() {
        readCsv();
    }

    // List to store staff members
    private List<Staff> staffList = new ArrayList<>();

    // Method to read data from CSV and create staff objects
    public void readCsv() {
        String line;
        
        // Clear the existing staff list to avoid duplicates
        staffList.clear();
        
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

                // Extract the details
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

                // Create a Staff object based on the role
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

    // Method to write staff to CSV
    public void writeCSV() {
        File file = new File(filePath);

        // Ensure that the file exists or handle the case when it's missing
        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath);
            return;  // Exit if file is missing
        }

        try (FileWriter writer = new FileWriter(file, false)) {  // 'false' ensures overwriting, not appending
            // Write header
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
            System.out.println("Staff data updated in CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
