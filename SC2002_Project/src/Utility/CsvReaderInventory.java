package Utility;
import Inventory.Medication;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderInventory implements CsvReader {

    private final String filePath = "Medicine_List.csv"; 
    private List<Medication> medicationList = new ArrayList<>();


    // Method to read data from CSV
    public void readCsv() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");


                if (values.length < 6) {  
                    System.out.println("Incomplete record: " + line);
                    continue;
                }


                String name = values[0].trim();
                int initialStock;
                int currentStock;
                int lowStockLevelAlert;
                String replenishRequest = values[4].trim();
                int replenishRequestAmount;


                try {
                    initialStock = Integer.parseInt(values[1].trim());
                    currentStock = Integer.parseInt(values[2].trim());
                    lowStockLevelAlert = Integer.parseInt(values[3].trim());
                    replenishRequestAmount = Integer.parseInt(values[5].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid stock data for medication: " + name);
                    continue;
                }


                Medication medication = new Medication(name, initialStock, currentStock, lowStockLevelAlert, replenishRequest, replenishRequestAmount);


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

        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath);
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Medicine Name,Initial Stock,Current Stock,Low Stock Level Alert,Replenish Request,Replenish Request Amount\n");

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
