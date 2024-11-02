package hospitalManagementSystem;

public class Prescription {
	private String prescriptionID;
	private Medication medication;
	private String prescriptionstatus; //(e.g., pending to dispensed)
	
	//constructor
	public Prescription(String prescriptionID, Medication medication , String prescriptionstatus ) {
		this.prescriptionID = prescriptionID;
		this.medication  = medication;
		this.prescriptionstatus = prescriptionstatus;
	}
	
	//getters
	public String getPrescriptionID() {
		return prescriptionID;
	}
	
	public Medication getMedication() {
		return medication;
	}
	
	public String getPrescriptionStatus() {
		return prescriptionstatus;
	}
	
	//setters
	public void setPrescriptionID(String prescriptionID) {
		this.prescriptionID = prescriptionID;
	}
	
	public void setMedication(Medication medication) {
		this.medication = medication;
	}
	
	public void setPrescriptionStatus(String prescriptionstatus) {
		this.prescriptionstatus = prescriptionstatus;
	}
	
	public boolean updatePrescriptionStatus(String newStatus) {
		if(newStatus != null && !newStatus.equals(this.prescriptionstatus)) {
			this.prescriptionstatus = newStatus;
			return true;
		}
		else {
			return false;
		}
	}
	
	//override //for debug
    public String toString() {
        return "Prescription ID: " + prescriptionID +
               ", Medication: " + medication +
               ", Status: " + prescriptionstatus;
    }
	
}
