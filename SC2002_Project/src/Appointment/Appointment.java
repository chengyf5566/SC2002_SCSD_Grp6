package Appointment;

public class Appointment {
    private String doctorId;
    private String doctorName;
    private String patientId;
    private String patientName;
    private String dateOfAppointment;
    private String appointmentStartTime;
    private String appointmentEndTime;
    private String appointmentStatus;
    private String typeOfService;
    private String prescribedMedications;
    private String prescribedMedicationsQuantity;
    private String prescribedMedicationsStatus;
    private String diagnosis;
    private String consultationNotes;

    public Appointment(String doctorId, String doctorName, String patientId, String patientName,
                       String dateOfAppointment, String appointmentStartTime, String appointmentEndTime,
                       String appointmentStatus, String typeOfService, String prescribedMedications,
                       String prescribedMedicationsQuantity, String prescribedMedicationsStatus,
                       String diagnosis, String consultationNotes) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.patientId = patientId;
        this.patientName = patientName;
        this.dateOfAppointment = dateOfAppointment;
        this.appointmentStartTime = appointmentStartTime;
        this.appointmentEndTime = appointmentEndTime;
        this.appointmentStatus = appointmentStatus;
        this.typeOfService = typeOfService;
        this.prescribedMedications = prescribedMedications;
        this.prescribedMedicationsQuantity = prescribedMedicationsQuantity;
        this.prescribedMedicationsStatus = prescribedMedicationsStatus;
        this.diagnosis = diagnosis;
        this.consultationNotes = consultationNotes;
    }

    // Getter methods for each field
    public String getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDateOfAppointment() {
        return dateOfAppointment;
    }

    public String getAppointmentStartTime() {
        return appointmentStartTime;
    }

    public String getAppointmentEndTime() {
        return appointmentEndTime;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public String getPrescribedMedications() {
        return prescribedMedications;
    }

    public String getPrescribedMedicationsQuantity() {
        return prescribedMedicationsQuantity;
    }
    
    public String getPrescribedMedicationsStatus() {
        return prescribedMedicationsStatus;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }

    // Setter methods for each field
    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDateOfAppointment(String dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public void setAppointmentStartTime(String appointmentStartTime) {
        this.appointmentStartTime = appointmentStartTime;
    }

    public void setAppointmentEndTime(String appointmentEndTime) {
        this.appointmentEndTime = appointmentEndTime;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public void setPrescribedMedications(String prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    public void setPrescribedMedicationsQuantity(String prescribedMedicationsQuantity) {
        this.prescribedMedicationsQuantity = prescribedMedicationsQuantity; 
    }

    public void setPrescribedMedicationsStatus(String prescribedMedicationsStatus) {
        this.prescribedMedicationsStatus = prescribedMedicationsStatus;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", dateOfAppointment='" + dateOfAppointment + '\'' +
                ", appointmentStartTime='" + appointmentStartTime + '\'' +
                ", appointmentEndTime='" + appointmentEndTime + '\'' +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                ", typeOfService='" + typeOfService + '\'' +
                ", prescribedMedications='" + prescribedMedications + '\'' +
                ", prescribedMedicationsQuantity=" + prescribedMedicationsQuantity + // Include new field
                ", prescribedMedicationsStatus='" + prescribedMedicationsStatus + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", consultationNotes='" + consultationNotes + '\'' +
                '}';
    }
}
