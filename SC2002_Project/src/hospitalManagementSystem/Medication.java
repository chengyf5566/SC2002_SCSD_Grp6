package hospitalManagementSystem;

public class Medication {
    private String name;
    private int quantity;

    public Medication(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
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
