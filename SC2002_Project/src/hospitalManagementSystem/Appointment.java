package hospitalManagementSystem;

public class Appointment {
    private String doctorId;
    private String patientId;
    private String dateOfAppointment;
    private String appointmentStatus;
    private String typeOfService;
    private String prescribedMedications;
    private String prescribedMedicationsStatus;
    private String consultationNotes;

    public Appointment(String doctorId, String patientId, String dateOfAppointment, String appointmentStatus,
                       String typeOfService, String prescribedMedications, String prescribedMedicationsStatus, 
                       String consultationNotes) {
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.dateOfAppointment = dateOfAppointment;
        this.appointmentStatus = appointmentStatus;
        this.typeOfService = typeOfService;
        this.prescribedMedications = prescribedMedications;
        this.prescribedMedicationsStatus = prescribedMedicationsStatus;
        this.consultationNotes = consultationNotes;
    }

    // Getter methods for each field
    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDateOfAppointment() {
        return dateOfAppointment;
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

    public String getPrescribedMedicationsStatus() {
        return prescribedMedicationsStatus;
    }

    public String getConsultationNotes() {
        return consultationNotes;
    }


    @Override
    public String toString() {
        return "Appointment{" +
                "doctorId='" + doctorId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", dateTime='" + dateOfAppointment + '\'' +
                ", appointmentStatus='" + appointmentStatus + '\'' +
                ", typeOfService='" + typeOfService + '\'' +
                ", prescribedMedications='" + prescribedMedications + '\'' +
                ", prescribedMedicationsStatus='" + prescribedMedicationsStatus + '\'' +
                ", consultationNotes='" + consultationNotes + '\'' +
                '}';
    }
}
