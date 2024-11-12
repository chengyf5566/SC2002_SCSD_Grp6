package hospitalManagementSystem;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Medication {
    private String name;
    private int quantity;
    private int lowStockAlert;
    private static List<Medication> inventory = new ArrayList<>();
    private static final String CSV_FILE_PATH = System.getProperty("user.dir") + "/Medicine_List.csv";




    public Medication(String name, int quantity, int lowStockAlert) {
        this.name = name;
        this.quantity = quantity;
        this.lowStockAlert = lowStockAlert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public void setLowStockAlert(int lowStockAlert) {
        this.lowStockAlert = lowStockAlert;
    }

    public static void loadInventoryFromFile() {
        inventory.clear(); // Clear any existing data
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            br.readLine(); // Skip header
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 3) {
                    String name = values[0].trim();
                    int initialStock = Integer.parseInt(values[1].trim());
                    int lowStockAlert = Integer.parseInt(values[2].trim());

                    Medication medication = new Medication(name, initialStock, lowStockAlert);
                    inventory.add(medication);
                }
            }
            System.out.println("Inventory loaded successfully from " + CSV_FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error reading the inventory file: " + e.getMessage());
        }
    }

    // Static method to save inventory back to CSV file
    public static void saveInventoryToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE_PATH))) {
            bw.write("Name,Initial Stock,Low Stock Alert\n"); // Write header
            for (Medication med : inventory) {
                bw.write(med.getName() + "," + med.getQuantity() + "," + med.getLowStockAlert() + "\n");
            }
            System.out.println("Inventory saved successfully to " + CSV_FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error writing to the inventory file: " + e.getMessage());
        }
    }

    // Static method to get the inventory list
    public static List<Medication> getInventory() {
        return inventory;
    }

    // Method to update the quantity of a medication
    public static void updateMedicationQuantity(String name, int newQuantity) {
        for (Medication med : inventory) {
            if (med.getName().equalsIgnoreCase(name)) {
                med.setQuantity(newQuantity);
                System.out.println("Updated quantity for " + name + " to " + newQuantity);
                saveInventoryToFile(); // Save changes to CSV
                return;
            }
        }
        System.out.println("Medication " + name + " not found in inventory.");
    }

    // Override equals method to compare medications by name
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Medication that = (Medication) obj;
        return name.equals(that.name);
    }

    // Override hashCode method
    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
