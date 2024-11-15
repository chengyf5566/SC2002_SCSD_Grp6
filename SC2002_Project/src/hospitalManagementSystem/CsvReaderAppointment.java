package hospitalManagementSystem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderAppointment {
    private String filePath_Appointment = "Appointment_Outcome.csv";
    private List<Appointment> appointmentList = new ArrayList<>();

    public CsvReaderAppointment() {
        readAndInitializeAppointments();

    }

    // Read and initialize appointments from CSV
    public void readAndInitializeAppointments() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath_Appointment), StandardCharsets.UTF_8))) {
            if (br.ready()) {
                br.mark(3);
                if (br.read() != 0xEF || br.read() != 0xBB || br.read() != 0xBF) {
                    br.reset();
                }
            }

            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",", -1); // Use -1 to keep trailing empty fields
                if (data.length < 8) {
                    System.out.println("Skipping invalid line due to insufficient fields: " + line);
                    continue;
                }

                // Ensure we have at least 13 fields, filling missing ones with empty strings if necessary
                String[] fullData = new String[13];
                for (int i = 0; i < fullData.length; i++) {
                    fullData[i] = (i < data.length) ? cleanString(data[i]) : "";
                }

                // Create an appointment with all fields (empty ones will default to "")
                Appointment appointment = new Appointment(
                    fullData[0], fullData[1], fullData[2], fullData[3],
                    fullData[4], fullData[5], fullData[6], fullData[7],
                    fullData[8], fullData[9], fullData[10], fullData[11], fullData[12]
                );
                appointmentList.add(appointment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if a specific appointment slot is available
    public boolean checkAvailability(String doctorID, String date, String time) {
        LocalTime requestedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));

        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equals(doctorID) &&
                appointment.getDateOfAppointment().equals(date) &&
                !appointment.getAppointmentStatus().equalsIgnoreCase("Canceled")) {

                LocalTime appointmentStartTime = LocalTime.parse(appointment.getAppointmentStartTime(), DateTimeFormatter.ofPattern("HHmm"));
                LocalTime appointmentEndTime = LocalTime.parse(appointment.getAppointmentEndTime(), DateTimeFormatter.ofPattern("HHmm"));

                // Check if the requested time overlaps with any existing appointment
                if (!requestedTime.isBefore(appointmentStartTime) && !requestedTime.isAfter(appointmentEndTime)) {
                    return false;
                }
            }
        }
        return true;
    }


    // Add an appointment record
    public boolean addAppointmentRecord(String doctorID, String doctorName, String patientID, String patientName,
                                        String date, String startTime, String endTime, String status) {
    	Appointment newAppointment = new Appointment(doctorID, doctorName, patientID, patientName, date, startTime, endTime, status, "", "", "", "", "");
        appointmentList.add(newAppointment);
        return writeAppointmentFile();
    }


    // Delete an appointment record by exact match
    public boolean deleteAppointmentRecord(String appointmentRecord) {
        boolean isRemoved = appointmentList.removeIf(app -> app.toString().trim().equals(appointmentRecord.trim()));
        return isRemoved && writeAppointmentFile();
    }

    // Get a list of appointments by patient ID with specific statuses
    public List<String> getAppointmentsByPatientID(String patientID, String... statuses) {
        List<String> result = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            for (String status : statuses) {
                if (appointment.getPatientId().equals(patientID) && appointment.getAppointmentStatus().equalsIgnoreCase(status)) {
                    result.add(appointment.toString());
                    break;
                }
            }
        }
        return result;
    }

    // Helper to extract details from appointment string
    public String extractAppointmentDetail(String appointmentRecord, String field) {
        // Split the record into fields
        String[] fields = appointmentRecord.split(", ");

        // Validate the number of fields
        if (fields.length <= 5) {
            System.out.println("Invalid appointment record: Not enough fields - " + appointmentRecord);
            return "";
        }

        try {
            switch (field) {
                case "Date":
                    // Check if the field contains "=" and split it
                    if (fields[2].contains("=")) {
                        return fields[2].split("=")[1].trim().replace("'", "");
                    } else {
                        System.out.println("Invalid Date format in record: " + appointmentRecord);
                        return "";
                    }
                case "StartTime":
                    // Check if the field contains "=" and split it
                    if (fields[3].contains("=")) {
                        return fields[3].split("=")[1].trim().replace("'", "");
                    } else {
                        System.out.println("Invalid StartTime format in record: " + appointmentRecord);
                        return "";
                    }
                case "EndTime":
                    // Check if the field contains "=" and split it
                    if (fields[4].contains("=")) {
                        return fields[4].split("=")[1].trim().replace("'", "");
                    } else {
                        System.out.println("Invalid EndTime format in record: " + appointmentRecord);
                        return "";
                    }
                default:
                    System.out.println("Unknown field requested: " + field);
                    return "";
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error processing record: " + appointmentRecord + " - " + e.getMessage());
            return "";
        }
    }


    // Replace appointment record
    public boolean replaceAppointmentRecord(String patientID, String oldDate, String oldStartTime, String oldEndTime, String newDate, String newStartTime, String newStatus, String oldStatus) {
        LocalTime newStart = LocalTime.parse(newStartTime, DateTimeFormatter.ofPattern("HHmm"));
        LocalTime newEnd = newStart.plusMinutes(30); 

        // Check for conflicts with other appointments
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientId().equals(patientID) &&
                appointment.getDateOfAppointment().equals(newDate) &&
                !appointment.getAppointmentStatus().equalsIgnoreCase("Canceled")) {

                LocalTime existingStart = LocalTime.parse(appointment.getAppointmentStartTime(), DateTimeFormatter.ofPattern("HHmm"));
                LocalTime existingEnd = LocalTime.parse(appointment.getAppointmentEndTime(), DateTimeFormatter.ofPattern("HHmm"));

                // Check if the new time overlaps with an existing appointment
                if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingStart)) {
                    System.out.println("The selected time conflicts with an existing appointment: " +
                                       "Start - " + appointment.getAppointmentStartTime() +
                                       ", End - " + appointment.getAppointmentEndTime());
                    return false;
                }
            }
        }

        // Update the matching appointment
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientId().equals(patientID) &&
                appointment.getDateOfAppointment().equals(oldDate) &&
                appointment.getAppointmentStartTime().equals(oldStartTime) &&
                appointment.getAppointmentStatus().equalsIgnoreCase(oldStatus)) {

                // Update the appointment details
                appointment.setDateOfAppointment(newDate);
                appointment.setAppointmentStartTime(newStartTime);
                appointment.setAppointmentEndTime(newEnd.format(DateTimeFormatter.ofPattern("HHmm")));
                appointment.setAppointmentStatus(newStatus);

                return writeAppointmentFile();
            }
        }

        return false;
    }





    // Write appointment list to CSV file
    public boolean writeAppointmentFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath_Appointment))) {
            writer.write("Doctor ID,Doctor Name,Patient ID,Patient Name,Appointment Date,Appointment Start Time,Appointment End Time,Appointment Status,Type of Service,Prescribed Medications,Prescribed Medications Status,Diagnosis,Consultation Notes\n");
            for (Appointment appointment : appointmentList) {
                writer.write(appointment.getDoctorId() + ","
                        + appointment.getDoctorName() + ","
                        + appointment.getPatientId() + ","
                        + appointment.getPatientName() + ","
                        + appointment.getDateOfAppointment() + ","
                        + appointment.getAppointmentStartTime() + ","
                        + appointment.getAppointmentEndTime() + ","
                        + appointment.getAppointmentStatus() + ","
                        + appointment.getTypeOfService() + ","
                        + appointment.getPrescribedMedications() + ","
                        + appointment.getPrescribedMedicationsStatus() + ","
                        + appointment.getDiagnosis() + ","
                        + appointment.getConsultationNotes() + "\n");
            }
            System.out.println("Appointment list updated in CSV.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Helper method to clean strings
    private String cleanString(String input) {
        return input.replaceAll("\"", "").trim();
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }
}
