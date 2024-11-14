package hospitalManagementSystem;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.FileWriter;
import java.io.IOException;

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

    // New method to schedule an appointment
    public boolean scheduleAppointment(String date, String time, String appointmentsFilePath) {
        try {
            // Parse date and time to standardize formatting if necessary
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MM yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

            LocalDate parsedDate = LocalDate.parse(date, dateFormatter);
            LocalTime parsedTime = LocalTime.parse(time, timeFormatter);
            
            // Calculate the end time as 30 minutes after the start time
            LocalTime endTime = parsedTime.plusMinutes(30);

            // Format date and time for consistency in the CSV
            String formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
            String formattedStartTime = parsedTime.format(DateTimeFormatter.ofPattern("HHmm"));
            String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("HHmm"));

            // Check if the appointment slot is available
            if (checkAvailability(formattedDate, formattedStartTime, appointmentsFilePath)) {
                // Add appointment to CSV with all necessary columns
                try (FileWriter writer = new FileWriter(appointmentsFilePath, true)) {
                    writer.write(
                        assignedDoctorID + "," +       // Doctor ID
                        assignedDoctorName + "," +     // Doctor Name
                        patientID + "," +              // Patient ID
                        getName() + "," +              // Patient Name
                        formattedDate + "," +          // Appointment Date
                        formattedStartTime + "," +     // Appointment Start Time
                        formattedEndTime + "," +       // Appointment End Time
                        "Pending," +                 // Appointment Status
                        "," +          				// Type of Service
                        "," +                       // Prescribed Medications (default value if not available)
                        "," +                       // Prescribed Medications Status (default value if not available)
                        "," +                       // Diagnosis (default value if not available)
                        "\n"                        // Consultation Notes (default value if not available)
                    );
                    System.out.println("Appointment scheduled successfully.");
                    return true;
                }
            } else {
                System.out.println("The selected appointment slot is not available.");
                return false;
            }
        } catch (IOException | DateTimeParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to check appointment availability
    private boolean checkAvailability(String date, String time, String appointmentsFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentsFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");

                // Ensure the line has at least 5 elements to avoid ArrayIndexOutOfBoundsException
                if (details.length < 5) {
                    continue; // Skip any improperly formatted lines
                }

                String doctorID = details[0].trim();          // Doctor ID
                String appointmentDate = details[4].trim();   // Appointment Date
                String appointmentTime = details[5].trim();   // Appointment Start Time
                String status = details[7].trim();            // Appointment Status

                // Check if an appointment exists for the same doctor, date, and time
                if (doctorID.equals(assignedDoctorID) && appointmentDate.equals(date) && appointmentTime.equals(time)) {
                    // Return true only if the existing appointment is canceled
                    return status.equalsIgnoreCase("canceled");
                }
            }
            // No conflicting appointment found, so the slot is available
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
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