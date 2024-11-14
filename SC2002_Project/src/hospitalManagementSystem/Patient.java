package hospitalManagementSystem;

import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    private CsvReaderAppointment csvReaderAppointment = new CsvReaderAppointment(); // For appointment handling

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

    // Method to schedule an appointment
    public boolean scheduleAppointment(String date, String time) {
        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

            LocalDate parsedDate = LocalDate.parse(date, dateFormatter);
            LocalTime parsedTime = LocalTime.parse(time, timeFormatter);
            LocalTime endTime = parsedTime.plusMinutes(30);

            String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
            String formattedStartTime = parsedTime.format(DateTimeFormatter.ofPattern("HHmm"));
            String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("HHmm"));

            if (csvReaderAppointment.checkAvailability(assignedDoctorID, formattedDate, formattedStartTime)) {
                return csvReaderAppointment.addAppointmentRecord(
                        assignedDoctorID, assignedDoctorName, patientID, name, formattedDate, formattedStartTime, formattedEndTime, "Pending");
            } else {
                System.out.println("The selected appointment slot is not available.");
                return false;
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete an appointment record
    public void deleteAppointmentRecord(String appointmentRecord) {
        boolean isDeleted = csvReaderAppointment.deleteAppointmentRecord(appointmentRecord);
        if (!isDeleted) {
            System.out.println("Failed to update the appointments file.");
        }
    }

    // Method to view scheduled appointments
    public List<String> viewScheduledAppointments() {
        List<String> appointments = csvReaderAppointment.getAppointmentsByPatientID(patientID, "Pending", "Confirmed");

        if (appointments.isEmpty()) {
            System.out.println("No pending or confirmed appointments found for Patient: " + getName());
        }
        return appointments;
    }

    // Method to reschedule an appointment
    public boolean rescheduleAppointment(Scanner scanner) {
        List<String> appointments = viewScheduledAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments to reschedule.");
            return false;
        }

        System.out.println("\nCurrent Scheduled Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ". " + appointments.get(i));
        }

        System.out.print("Select the appointment number to reschedule: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > appointments.size()) {
            System.out.println("Invalid selection.");
            return false;
        }

        String selectedAppointment = appointments.get(choice - 1);
        String oldDate = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "Date");
        String oldTime = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "Time");

        System.out.print("Enter the new appointment date (DD MM YYYY): ");
        String newDate = scanner.nextLine();
        System.out.print("Enter the new appointment time (HHMM): ");
        String newTime = scanner.nextLine();

        return csvReaderAppointment.replaceAppointmentRecord(patientID, oldDate, oldTime, newDate, newTime, "Pending", "Pending");
    }

    // Method to cancel an appointment
    public boolean cancelAppointment(Scanner scanner) {
        List<String> appointments = viewScheduledAppointments();
        if (appointments.isEmpty()) {
            System.out.println("No appointments to cancel.");
            return false;
        }

        System.out.println("\nCurrent Scheduled Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ". " + appointments.get(i));
        }

        System.out.print("Select the appointment number to cancel: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice < 1 || choice > appointments.size()) {
            System.out.println("Invalid selection.");
            return false;
        }

        String selectedAppointment = appointments.get(choice - 1);
        String oldDate = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "Date");
        String oldTime = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "Time");

        return csvReaderAppointment.replaceAppointmentRecord(patientID, oldDate, oldTime, oldDate, oldTime, "Canceled", "Pending");
    }

    // Method to view past appointment records
    public List<String> viewPastAppointmentRecords() {
        List<String> pastAppointments = csvReaderAppointment.getAppointmentsByPatientID(patientID, "Completed", "Canceled");

        if (pastAppointments.isEmpty()) {
            System.out.println("No past appointment records found for Patient: " + getName());
        }
        return pastAppointments;
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
