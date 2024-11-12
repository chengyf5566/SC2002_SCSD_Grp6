package hospitalManagementSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderInventory {

    private String filePath = "Medicine_List.csv"; // Path to the medication CSV file

    public CsvReaderInventory(String filePath) {
        this.filePath = filePath;
    }

    private List<Medication> medicationList = new ArrayList<>();

    // Method to read data from CSV and create Medication objects
    public void readAndInitializeInventory(String filePath) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Check if the record contains all expected columns
                if (values.length < 4) {  // 4 columns expected now
                    System.out.println("Incomplete record: " + line);
                    continue;
                }

                // Extract and trim the values from the CSV
                String name = values[0].trim();
                int initialStock;
                int lowStockLevelAlert;
                String replenishRequest = values[3].trim();  // Expecting a string ("true"/"false")

                // Try parsing the initial stock and low stock alert levels
                try {
                    initialStock = Integer.parseInt(values[1].trim());
                    lowStockLevelAlert = Integer.parseInt(values[2].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock data for medication: " + name);
                    continue;
                }

                // Create a Medication object with the data
                Medication medication = new Medication(name, initialStock, lowStockLevelAlert, replenishRequest);

                // Add the created medication object to the medication list
                medicationList.add(medication);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for the medication list
    public List<Medication> getMedicationList() {
        return medicationList;
    }

    // Method to write medication data to CSV
    public void writeInventoryToCSV(String filePath) {
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath);
            return; // Do not attempt to create new directories or files
        }

        try (FileWriter writer = new FileWriter(file)) {
            // Write the header
            writer.write("Medicine Name,Initial Stock,Low Stock Level Alert,Replenish Request\n");

            // Write each medication's data
            for (Medication med : medicationList) {
                writer.write(med.getMedicineName() + ","
                        + med.getInitialStock() + ","
                        + med.getLowStockLevelAlert() + ","
                        + med.getReplenishRequest() + "\n");
            }

            System.out.println("Inventory list updated in CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
