package hospitalManagementSystem;
import java.util.*;

public class Patient extends User {
    private String patientID;
    private String dateOfBirth;
    private String bloodType;
    private String contactNum;
    private String email;
    private String assignedDoctorID;
    private String assignedDoctorName;
    private List<String> pastDiagnoses;
    private List<String> prescribedMedicines;
    private String consultationNotes;
    private String typeOfService;

    public Patient(String userID, String password, String name, String gender, String patientID, String dateOfBirth,
                   String bloodType, String contactNum, String email, String assignedDoctorID, String assignedDoctorName,
                   List<String> pastDiagnoses, List<String> prescribedMedicines, String consultationNotes, String typeOfService) {
        super(userID, password, "patient", gender, name);
        this.patientID = patientID;
        this.dateOfBirth = dateOfBirth;
        this.bloodType = bloodType;
        this.contactNum = contactNum;
        this.email = email;
        this.assignedDoctorID = assignedDoctorID;
        this.assignedDoctorName = assignedDoctorName;
        this.pastDiagnoses = new ArrayList<>(pastDiagnoses);
        this.prescribedMedicines = new ArrayList<>(prescribedMedicines);
        this.consultationNotes = consultationNotes;
        this.typeOfService = typeOfService;
    }

    // Getters and setters
    public String getPatientID() { return patientID; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getBloodType() { return bloodType; }
    public String getContactNum() { return contactNum; }
    public String getEmail() { return email; }
    public String getAssignedDoctorID() { return assignedDoctorID; }
    public String getAssignedDoctorName() { return assignedDoctorName; }
    public List<String> getPastDiagnoses() { return pastDiagnoses; }
    public List<String> getPrescribedMedicines() { return prescribedMedicines; }
    public String getConsultationNotes() { return consultationNotes; }
    public String getTypeOfService() { return typeOfService; }
    
    @Override
    public String toString() {
        return "Patient ID: " + patientID + ", Name: " + getName() + ", Date of Birth: " + dateOfBirth + ", Gender: " + getGender() +
               ", Blood Type: " + bloodType + ", Contact Number: " + contactNum + ", Email: " + email +
               ", Assigned Doctor: " + assignedDoctorID + " - " + assignedDoctorName + 
               ", Past Diagnoses: " + pastDiagnoses + ", Prescribed Medicines: " + prescribedMedicines +
               ", Consultation Notes: " + consultationNotes + ", Type of Service: " + typeOfService;
    }
}
