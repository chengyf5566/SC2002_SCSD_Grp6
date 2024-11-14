package hospitalManagementSystem;
import java.util.*;

public class Patient {
    private String patientID;
    private String patientPassword;
    private String name;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private String contactNum;
    private String email;
    private String assignedDoctorID;
    private String assignedDoctorName;
    private List<String> pastDiagnoses;
    private List<String> prescribedMedicines;
    private List<String> consultationNotes;
    private List<String> typeOfService;

    public Patient(String patientID, String patientPassword, String name, String dateOfBirth, String gender, 
                   String bloodType, String contactNum, String email, String assignedDoctor, 
                   String assignedDoctorName, List<String> pastDiagnoses, List<String> prescribedMedicines, 
                   List<String> consultationNotes, List<String> typeOfService) {
        this.patientID = patientID;
        this.patientPassword = patientPassword;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.contactNum = contactNum;
        this.email = email;
        this.assignedDoctorID = assignedDoctor;
        this.assignedDoctorName = assignedDoctorName;
        this.pastDiagnoses = pastDiagnoses != null ? new ArrayList<>(pastDiagnoses) : new ArrayList<>();
        this.prescribedMedicines = prescribedMedicines != null ? new ArrayList<>(prescribedMedicines) : new ArrayList<>();
        this.consultationNotes = consultationNotes != null ? new ArrayList<>(consultationNotes) : new ArrayList<>();
        this.typeOfService = typeOfService != null ? new ArrayList<>(typeOfService) : new ArrayList<>();
    }

    // Getters
    public String getPatientID() { return patientID; }
    public String getPatientPassword() { return patientPassword; }
    public String getName() { return name; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getBloodType() { return bloodType; }
    public String getContactNum() { return contactNum; }
    public String getEmail() { return email; }
    public String getAssignedDoctorID() { return assignedDoctorID; }
    public String getAssignedDoctorName() { return assignedDoctorName; }
    public List<String> getPastDiagnoses() { return pastDiagnoses; }
    public List<String> getPrescribedMedicines() { return prescribedMedicines; }
    public List<String> getConsultationNotes() { return consultationNotes; }
    public List<String> getTypeOfService() { return typeOfService; }

    // Setters
    public void setPatientID(String patientID) { this.patientID = patientID; }
    public void setPatientPassword(String patientPassword) { this.patientPassword = patientPassword; }
    public void setName(String name) { this.name = name; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public void setContactNum(String contactNum) { this.contactNum = contactNum; }
    public void setEmail(String email) { this.email = email; }
    public void setAssignedDoctorID(String assignedDoctor) { this.assignedDoctorID = assignedDoctor; }
    public void setAssignedDoctorName(String assignedDoctorName) { this.assignedDoctorName = assignedDoctorName; }

    public void setPastDiagnoses(List<String> pastDiagnoses) {
        this.pastDiagnoses = pastDiagnoses != null ? new ArrayList<>(pastDiagnoses) : new ArrayList<>();
    }
    public void setPrescribedMedicines(List<String> prescribedMedicines) {
        this.prescribedMedicines = prescribedMedicines != null ? new ArrayList<>(prescribedMedicines) : new ArrayList<>();
    }
    public void setConsultationNotes(List<String> consultationNotes) {
        this.consultationNotes = consultationNotes != null ? new ArrayList<>(consultationNotes) : new ArrayList<>();
    }
    public void setTypeOfService(List<String> typeOfService) {
        this.typeOfService = typeOfService != null ? new ArrayList<>(typeOfService) : new ArrayList<>();
    }

    @Override
    public String toString() {
        // Improved readability by formatting pastDiagnoses, prescribedMedicines, consultationNotes, and typeOfService
        String diagnoses = String.join(", ", pastDiagnoses);
        String medicines = String.join(", ", prescribedMedicines);
        String notes = String.join(", ", consultationNotes);
        String services = String.join(", ", typeOfService);

        return "Patient ID: " + patientID + "\n" +
               "Patient Password: " + patientPassword + "\n" +
               "Name: " + name + "\n" +
               "Date of Birth: " + dateOfBirth + "\n" +
               "Gender: " + gender + "\n" +
               "Blood Type: " + bloodType + "\n" +
               "Contact Number: " + contactNum + "\n" +
               "Email: " + email + "\n" +
               "Assigned Doctor: " + assignedDoctorID + "\n" +
               "Assigned Doctor Name: " + assignedDoctorName + "\n" +
               "Past Diagnoses: " + (diagnoses.isEmpty() ? "None" : diagnoses) + "\n" +
               "Prescribed Medicines: " + (medicines.isEmpty() ? "None" : medicines) + "\n" +
               "Consultation Notes: " + (notes.isEmpty() ? "None" : notes) + "\n" +
               "Type of Service: " + (services.isEmpty() ? "None" : services);
    }
}
