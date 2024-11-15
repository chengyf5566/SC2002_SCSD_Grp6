package hospitalManagementSystem;

import java.util.Arrays;
import java.util.*;
import java.util.stream.Collectors;
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
    
    
    private List<Appointment> appointmentList;

    private List<Patient> patientList;
    private CsvReaderPatient csvReaderPatient;

    
    // Method to initialize appointment outcome from CSV
    public void readAndInitializeAppointments() {
        this.csvReaderAppointment = new CsvReaderAppointment();
        csvReaderAppointment.readCsv();
        this.appointmentList = csvReaderAppointment.getAppointmentList();
        
        // Debugging: Print out the appointments after initialization
        System.out.println("Appointments loaded: " + appointmentList.size());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment); // Assuming the toString method in Appointment prints useful details
        } 
    }
  
    // Method to initialize patient list from CSV
    public void readAndInitializePatient() {
        this.csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readCsv();
        this.patientList = csvReaderPatient.getPatientList();
    }
   

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

            // Validate that the time is within 08:00 and 21:00
            LocalTime startBoundary = LocalTime.of(8, 0);
            LocalTime endBoundary = LocalTime.of(21, 0);

            if (parsedTime.isBefore(startBoundary) || endTime.isAfter(endBoundary)) {
                System.out.println("The selected time must be between 08:00 and 21:00.");
                return false;
            }

            String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
            String formattedStartTime = parsedTime.format(DateTimeFormatter.ofPattern("HHmm"));
            String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("HHmm"));

            if (csvReaderAppointment.checkAvailability(assignedDoctorID, formattedDate, formattedStartTime)) {
                boolean isAdded = csvReaderAppointment.addAppointmentRecord(
                        assignedDoctorID, assignedDoctorName, patientID, name, formattedDate, formattedStartTime, formattedEndTime, "Pending");

                if (isAdded) {
                    // Print the appointment details in the desired format
                    System.out.println("Doctor Name = " + assignedDoctorName + 
                                       ", Patient Name = " + name + 
                                       ", Date of Appointment = " + formattedDate + 
                                       ", Start Time = " + formattedStartTime + 
                                       ", End Time = " + formattedEndTime + 
                                       ", Status = Pending");
                    return true;
                } else {
                    System.out.println("Failed to add the appointment record.");
                    return false;
                }
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
        List<String> formattedAppointments = new ArrayList<>();

        if (appointments.isEmpty()) {
            System.out.println("No pending or confirmed appointments found for Patient: " + getName());
        } else {
            for (String appointmentStr : appointments) {
                // Parse the appointment string to extract the relevant fields
                String[] parts = appointmentStr.split(", ");
                String doctorName = parts[1].split("=")[1];
                String patientName = parts[3].split("=")[1];
                String dateOfAppointment = parts[4].split("=")[1];
                String appointmentStartTime = parts[5].split("=")[1];
                String appointmentEndTime = parts[6].split("=")[1];
                String appointmentStatus = parts[7].split("=")[1];

                // Format the extracted details
                String formattedAppointment = "Doctor Name = " + doctorName +
                                              ", Patient Name = " + patientName +
                                              ", Date of Appointment = " + dateOfAppointment +
                                              ", Start Time = " + appointmentStartTime +
                                              ", End Time = " + appointmentEndTime +
                                              ", Status = " + appointmentStatus;

                formattedAppointments.add(formattedAppointment);
            }
        }

        return formattedAppointments;
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
        scanner.nextLine(); // Consume newline

        if (choice < 1 || choice > appointments.size()) {
            System.out.println("Invalid selection.");
            return false;
        }

        String selectedAppointment = appointments.get(choice - 1);
        String oldDate = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "Date");
        String oldStartTime = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "StartTime");
        String oldEndTime = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "EndTime");

        System.out.print("Enter the new appointment date (DD MM YYYY): ");
        String newDate = scanner.nextLine();
        System.out.print("Enter the new appointment time (HHMM): ");
        String newTime = scanner.nextLine();

        // Validate the new time is within the boundary of 08:00 to 21:00
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        try {
            LocalTime newStartTime = LocalTime.parse(newTime, timeFormatter);
            LocalTime newEndTime = newStartTime.plusMinutes(30);

            LocalTime startBoundary = LocalTime.of(8, 0);
            LocalTime endBoundary = LocalTime.of(21, 0);

            if (newStartTime.isBefore(startBoundary) || newEndTime.isAfter(endBoundary)) {
                System.out.println("The selected time must be between 08:00 and 21:00.");
                return false;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HHMM format.");
            return false;
        }

        boolean success = csvReaderAppointment.replaceAppointmentRecord(
            patientID, oldDate, oldStartTime, oldEndTime, newDate, newTime, "Pending", "Pending"
        );

        if (success) {
            System.out.println("Appointment successfully rescheduled.");
        } else {
            System.out.println("Failed to reschedule appointment. The selected time might conflict with another appointment.");
        }

        return success;
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
        String oldStartTime = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "StartTime");
        String oldEndTime = csvReaderAppointment.extractAppointmentDetail(selectedAppointment, "EndTime");

        // Find the matching appointment to cancel
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientId().equals(patientID) &&
                appointment.getDateOfAppointment().equals(oldDate) &&
                appointment.getAppointmentStartTime().equals(oldStartTime) &&
                appointment.getAppointmentEndTime().equals(oldEndTime)) {

                // Set the appointment fields to empty for start and end time and change the status to "Cancelled"
                appointment.setAppointmentStartTime("");
                appointment.setAppointmentEndTime("");
                appointment.setAppointmentStatus("Cancelled");

                // Persist the updated appointment list to the CSV
                csvReaderAppointment.writeCSV();

                System.out.println("Appointment canceled successfully.");
                return true;
            }
        }

        System.out.println("Appointment not found.");
        return false;
    }

    // Method to view past appointment records
    public List<String> viewPastAppointmentRecords() {
        List<String> pastAppointments = csvReaderAppointment.getAppointmentsByPatientID(patientID, "Completed", "Canceled");
        List<String> formattedAppointments = new ArrayList<>();

        if (pastAppointments.isEmpty()) {
            System.out.println("No past appointment records found for Patient: " + getName());
        } else {
            for (String appointmentStr : pastAppointments) {
                // Parse the appointment string to extract the relevant fields
                String[] parts = appointmentStr.split(", ");
                String doctorName = parts[1].split("=")[1];
                String patientName = parts[3].split("=")[1];
                String dateOfAppointment = parts[4].split("=")[1];
                String appointmentStartTime = parts[5].split("=")[1];
                String appointmentEndTime = parts[6].split("=")[1];
                String appointmentStatus = parts[7].split("=")[1];
                String typeOfService = parts[8].split("=")[1];
                String prescribedMedications = parts[9].split("=")[1];
                String diagnosis = parts[11].split("=")[1];

                // Format the extracted details with each piece of information on a new line
                String formattedAppointment = "Doctor Name: " + doctorName + "\n" +
                                              "Patient Name: " + patientName + "\n" +
                                              "Date of Appointment: " + dateOfAppointment + "\n" +
                                              "Start Time: " + appointmentStartTime + "\n" +
                                              "End Time: " + appointmentEndTime + "\n" +
                                              "Status: " + appointmentStatus + "\n" +
                                              "Type of Service: " + typeOfService + "\n" +
                                              "Prescribed Medications: " + prescribedMedications + "\n" +
                                              "Diagnosis: " + diagnosis + "\n";

                formattedAppointments.add(formattedAppointment);
            }
        }

        return formattedAppointments;
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
