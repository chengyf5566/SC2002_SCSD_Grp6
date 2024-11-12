package hospitalManagementSystem;

public class Medication {
    private String medicineName;
    private int initialStock;
    private int lowStockLevelAlert;
    private String replenishRequest;  // Changed to String

    // Constructor to initialize the medication
    public Medication(String medicineName, int initialStock, int lowStockLevelAlert, String replenishRequest) {
        this.medicineName = medicineName;
        this.initialStock = initialStock;
        this.lowStockLevelAlert = lowStockLevelAlert;
        this.replenishRequest = replenishRequest != null ? replenishRequest : "false";  // Default to "false"
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

    @Override
    public String toString() {
        return "Medication{" +
               "medicineName='" + medicineName + '\'' +
               ", initialStock=" + initialStock +
               ", lowStockLevelAlert=" + lowStockLevelAlert +
               ", replenishRequest='" + replenishRequest + '\'' +
               '}';
    }
}
