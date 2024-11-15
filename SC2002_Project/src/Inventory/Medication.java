package Inventory;

public class Medication {
    private String medicineName;
    private int initialStock;
    private int currentStock;
    private int lowStockLevelAlert;
    private String replenishRequest;
    private int replenishRequestAmount;

    // Constructor to initialize the medication
    public Medication(String medicineName, int initialStock, int currentStock, int lowStockLevelAlert, String replenishRequest, int replenishRequestAmount) {
        this.medicineName = medicineName;
        this.initialStock = initialStock;
        this.currentStock = currentStock;
        this.lowStockLevelAlert = lowStockLevelAlert;
        this.replenishRequest = replenishRequest != null ? replenishRequest : "No";
        this.replenishRequestAmount = replenishRequestAmount;
    }

    // Getters and Setters
    public String getMedicineName() {
        return medicineName;
    }

    public int getInitialStock() {
        return initialStock;
    }

    public void setInitialStock(int initialStock) {
        this.initialStock = initialStock;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getLowStockLevelAlert() {
        return lowStockLevelAlert;
    }

    public void setLowStockLevelAlert(int lowStockLevelAlert) {
        this.lowStockLevelAlert = lowStockLevelAlert;
    }

    public String getReplenishRequest() {
        return replenishRequest;
    }

    public void setReplenishRequest(String replenishRequest) {
        this.replenishRequest = replenishRequest;
    }

    public int getReplenishRequestAmount() {
        return replenishRequestAmount;
    }

    public void setReplenishRequestAmount(int replenishRequestAmount) {
        this.replenishRequestAmount = replenishRequestAmount;
    }

    @Override
    public String toString() {
        return "Medication{" +
               "medicineName='" + medicineName + '\'' +
               ", initialStock=" + initialStock +
               ", currentStock=" + currentStock +
               ", lowStockLevelAlert=" + lowStockLevelAlert +
               ", replenishRequest='" + replenishRequest + '\'' +
               ", replenishRequestAmount=" + replenishRequestAmount +
               '}';
    }
}
