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


    private List<Staff> staffList = new ArrayList<>();

    // Method to read data from CSV
    public void readCsv() {
        String line;
        

        staffList.clear();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();

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

        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath);
            return;  
        }

        try (FileWriter writer = new FileWriter(file, false)) {  

            writer.write("UserID,Password,Name,Role,Gender,Age\n");

  
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
