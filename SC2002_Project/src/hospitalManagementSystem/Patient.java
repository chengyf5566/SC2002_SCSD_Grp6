package hospitalManagementSystem;

import java.io.*;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.FileWriter;
import java.io.IOException;

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
    
    public void deleteAppointmentRecord(String appointmentRecord, String appointmentsFilePath) {
        File inputFile = new File(appointmentsFilePath);
        File tempFile = new File("temp_appointments.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                // Write all lines except the one matching the appointmentRecord to the temp file
                if (!line.trim().equals(appointmentRecord.trim())) {
                    writer.write(line + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace original file with the updated temp file
        if (!inputFile.delete() || !tempFile.renameTo(inputFile)) {
            System.out.println("Failed to update the appointments file.");
        }
    }
    
    public List<String> viewScheduledAppointments(String appointmentsFilePath) {
        List<String> appointments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(appointmentsFilePath))) {
            String line;
            boolean hasAppointments = false;

            // Skip header if present
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");

                // Ensure the line has sufficient columns, matches the patient ID, and has the right status
                if (details.length >= 8 && details[2].trim().equals(patientID)) {
                    String doctorName = details[1].trim();       // Doctor name
                    String appointmentDate = details[4].trim();  // Appointment date
                    String startTime = details[5].trim();        // Start time
                    String status = details[7].trim();           // Appointment status

                    // Only add appointments with "Pending" or "Confirmed" status for viewing
                    if (status.equalsIgnoreCase("Pending") || status.equalsIgnoreCase("Confirmed")) {
                        String formattedAppointment = "Name: " + getName() +
                                                     ", Doctor: " + doctorName +
                                                     ", Date: " + appointmentDate +
                                                     ", Time: " + startTime +
                                                     ", Status: " + status;
                        appointments.add(formattedAppointment);
                        hasAppointments = true;
                    }
                }
            }

            if (!hasAppointments) {
                System.out.println("No pending or confirmed appointments found for Patient: " + getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return appointments;
    }
    
    public boolean rescheduleAppointment(String appointmentsFilePath, Scanner scanner) {
        // View and list the patient's current scheduled appointments
        List<String> appointments = viewScheduledAppointments(appointmentsFilePath);
        if (appointments.isEmpty()) {
            System.out.println("No appointments to reschedule.");
            return false;
        }

        System.out.println("\nCurrent Scheduled Appointments:");
        for (int i = 0; i < appointments.size(); i++) {
            System.out.println((i + 1) + ". " + appointments.get(i));
        }

        // Select the appointment to reschedule
        System.out.print("Select the appointment number to reschedule: ");
        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (choice < 1 || choice > appointments.size()) {
            System.out.println("Invalid selection.");
            return false;
        }

        // Extract details from the selected appointment
        String selectedAppointment = appointments.get(choice - 1);
        String oldDate = null;
        String oldTime = null;

        try {
            // Extract date and time from selected appointment line
            String[] parts = selectedAppointment.split(", ");
            for (String part : parts) {
                if (part.startsWith("Date: ")) {
                    oldDate = part.split(": ")[1];
                } else if (part.startsWith("Time: ")) {
                    oldTime = part.split(": ")[1];
                }
            }

            // Ensure date and time were found
            if (oldDate == null || oldTime == null) {
                System.out.println("Error: Could not find date or time in the selected appointment.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error processing appointment details: " + e.getMessage());
            return false;
        }

        // Prompt for new date and time
        System.out.print("Enter the new appointment date (DD MM YYYY): ");
        String newDate = scanner.nextLine();
        System.out.print("Enter the new appointment time (HHMM): ");
        String newTime = scanner.nextLine();

        // Attempt to replace the selected appointment with the new date, time, and a status of "Pending"
        boolean isReplaced = replaceAppointmentRecord(patientID, oldDate, oldTime, newDate, newTime, appointmentsFilePath, "Pending", "Pending");
        return isReplaced;
    }
    
    public boolean replaceAppointmentRecord(String patientID, String oldDate, String oldTime, String newDate, String newTime, String appointmentsFilePath, String newStatus, String oldStatus) {
        List<String> lines = new ArrayList<>();
        boolean isReplaced = false;

        // Read all lines, modifying only the line that matches patientID, old date, old time, and old status
        try (BufferedReader reader = new BufferedReader(new FileReader(appointmentsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");

                // Check if the line matches patient ID, old date, old time, and old status
                if (details.length >= 8 &&
                    details[2].trim().equals(patientID) &&
                    details[4].trim().equals(oldDate) &&
                    details[5].trim().equals(oldTime) &&
                    details[7].trim().equalsIgnoreCase(oldStatus)) {
                    
                    // Update the date, time, and status in the details array
                    details[4] = newDate;    // Update date column
                    details[5] = newTime;    // Update start time column
                    details[6] = LocalTime.parse(newTime, DateTimeFormatter.ofPattern("HHmm")).plusMinutes(30).format(DateTimeFormatter.ofPattern("HHmm")); // Update end time
                    details[7] = newStatus;  // Update status to the new status provided

                    // Reconstruct the line with the updated date, time, and status
                    String newRecord = String.join(",", details);
                    lines.add(newRecord);
                    isReplaced = true;
                } else {
                    // Keep other lines as they are
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        // Overwrite the original file with modified content
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(appointmentsFilePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return isReplaced;
    }

    
 // Method to cancel an appointment by changing its status to "Canceled"
    public boolean cancelAppointment(String appointmentsFilePath, Scanner scanner) {
        List<String> appointments = viewScheduledAppointments(appointmentsFilePath);
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
        scanner.nextLine();  // Consume newline

        if (choice < 1 || choice > appointments.size()) {
            System.out.println("Invalid selection.");
            return false;
        }

        // Extract the appointment details to be canceled
        String selectedAppointment = appointments.get(choice - 1);
        String oldDate = null;
        String oldTime = null;

        try {
            // Extract date and time from selected appointment line
            String[] parts = selectedAppointment.split(", ");
            for (String part : parts) {
                if (part.startsWith("Date: ")) {
                    oldDate = part.split(": ")[1];
                } else if (part.startsWith("Time: ")) {
                    oldTime = part.split(": ")[1];
                }
            }

            // Ensure date and time were found
            if (oldDate == null || oldTime == null) {
                System.out.println("Error: Could not find date or time in the selected appointment.");
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error processing appointment details: " + e.getMessage());
            return false;
        }

        // Update the appointment status to "Canceled" without deleting the record
        boolean isCanceled = replaceAppointmentRecord(patientID, oldDate, oldTime, oldDate, oldTime, appointmentsFilePath, "Pending", "Pending");
        return isCanceled;
    }
    
    public List<String> viewPastAppointmentRecords(String appointmentsFilePath) {
        List<String> pastAppointments = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(appointmentsFilePath))) {
            String line;
            br.readLine(); // Skip header

            while ((line = br.readLine()) != null) {
                String[] details = line.split(",");

                // Ensure the line has enough columns and matches the patient ID
                if (details.length >= 8 && details[2].trim().equals(patientID)) {
                    String status = details[7].trim();  // Appointment Status

                    // Only add records with a status of "Completed" or "Canceled"
                    if (status.equalsIgnoreCase("Completed") || status.equalsIgnoreCase("Canceled")) {
                        String doctorName = details[1].trim();
                        String appointmentDate = details[4].trim();
                        String diagnosis = details.length > 10 ? details[10].trim() : "N/A";
                        String medications = details.length > 9 ? details[9].trim() : "N/A";
                        String notes = details.length > 11 ? details[11].trim() : "N/A";

                        // Format the record
                        String formattedRecord = "Date: " + appointmentDate +
                                                 ", Doctor: " + doctorName +
                                                 ", Status: " + status +
                                                 ", Diagnosis: " + diagnosis +
                                                 ", Medications: " + medications +
                                                 ", Notes: " + notes;
                        pastAppointments.add(formattedRecord);
                    }
                }
            }

            if (pastAppointments.isEmpty()) {
                System.out.println("No past appointment records found for Patient: " + getName());
            }

        } catch (IOException e) {
            e.printStackTrace();
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
    public void setContactNum(String contactNum) { this.contactNum = contactNum;}
    public void setEmail(String email) {this.email = email;}

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