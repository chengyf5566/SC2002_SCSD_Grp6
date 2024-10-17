package hospitalManagementSystem;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private List<Medication> medications;

    public Inventory(){
        medications = new ArrayList<>();
    }

    public boolean addMedication(Medication med){
        if(!medications.contains(med)){
            medications.add(med);
            return true;
        }
        return false;
    }

    public boolean removeMedication(Medication med){
        return medications.remove(med);
    }


    public boolean updateStock(Medication med, int quantity){
        for(Medication m: medications){
            if(m.equals(med)){
                m.setQuantity(quantity);
                return true;
            }
        }
        return false;
    }

    public List<Medication> getMedications(){
        return medications;
    }
}
