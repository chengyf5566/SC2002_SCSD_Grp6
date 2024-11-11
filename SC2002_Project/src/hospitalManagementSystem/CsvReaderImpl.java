package hospitalManagementSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderImpl implements CsvReader {

    private List<Staff> staffList = new ArrayList<>();

    @Override
    public void readCsv(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Assuming the CSV columns are in the order: Staff ID, Password, Name, Role, Gender, Age
                String userID = values[0].trim();
                String password = values[1].trim();
                String name = values[2].trim();
                String role = values[3].trim();
                String gender = values[4].trim();
                int age = Integer.parseInt(values[5].trim());

                // Create a new Staff object and add it to the list
                Staff staff = new Staff(userID, password, role, gender, name, age);
                staffList.add(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Staff> getStaffList() {
        return staffList;
    }
}
