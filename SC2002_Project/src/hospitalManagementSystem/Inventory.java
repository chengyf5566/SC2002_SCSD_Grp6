package hospitalManagementSystem;

import java.util.HashMap;
import java.util.Map;

public class Inventory{
    private Map<String, Integer> medicines; // Medicine name and its quantity

    public Inventory() {
        medicines = new HashMap<>();
        // Initialize with some medicines
        medicines.put("A", 10);
        medicines.put("B", 5);
    }

    // Method to add or update a medicine in the inventory
    public void addMedicine(String medication, int quantity) {
        medicines.put(medication, medicines.getOrDefault(medication, 0) + quantity);
    }

    // Method to display all medicines
    public void displayMedicines() {
        for (Map.Entry<String, Integer> entry : medicines.entrySet()) {
            System.out.println(entry.getKey() + " + " + entry.getValue());
        }
    }
}
