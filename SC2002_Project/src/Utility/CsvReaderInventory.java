package Utility;
import Inventory.Medication;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderInventory implements CsvReader {

    private final String filePath = "Medicine_List.csv"; // Path to the medication CSV file
    private List<Medication> medicationList = new ArrayList<>();


    // Method to read data from CSV and create Medication objects
    public void readCsv() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Skip the header row
            br.readLine();

            // Read each line from the CSV file
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Check if the record contains all expected columns
                if (values.length < 6) {  // 6 columns expected now
                    System.out.println("Incomplete record: " + line);
                    continue;
                }

                // Extract and trim the values from the CSV
                String name = values[0].trim();
                int initialStock;
                int currentStock;
                int lowStockLevelAlert;
                String replenishRequest = values[4].trim();
                int replenishRequestAmount;

                // Try parsing the initial stock, current stock, low stock alert levels, and replenish amount
                try {
                    initialStock = Integer.parseInt(values[1].trim());
                    currentStock = Integer.parseInt(values[2].trim());
                    lowStockLevelAlert = Integer.parseInt(values[3].trim());
                    replenishRequestAmount = Integer.parseInt(values[5].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock data for medication: " + name);
                    continue;
                }

                // Create a Medication object with the data
                Medication medication = new Medication(name, initialStock, currentStock, lowStockLevelAlert, replenishRequest, replenishRequestAmount);

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
    public void writeCSV() {
        File file = new File(filePath);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath);
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            // Write the header
            writer.write("Medicine Name,Initial Stock,Current Stock,Low Stock Level Alert,Replenish Request,Replenish Request Amount\n");

            // Write each medication's data
            for (Medication med : medicationList) {
                writer.write(med.getMedicineName() + ","
                        + med.getInitialStock() + ","
                        + med.getCurrentStock() + ","
                        + med.getLowStockLevelAlert() + ","
                        + med.getReplenishRequest() + ","
                        + med.getReplenishRequestAmount() + "\n");
            }

            System.out.println("Inventory list updated in CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
