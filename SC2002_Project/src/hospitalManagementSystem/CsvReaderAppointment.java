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

                String[] data = line.split(",");
                if (data.length == 13) {
                    Appointment appointment = new Appointment(
                        cleanString(data[0]), cleanString(data[1]), cleanString(data[2]), cleanString(data[3]),
                        cleanString(data[4]), cleanString(data[5]), cleanString(data[6]), cleanString(data[7]),
                        cleanString(data[8]), cleanString(data[9]), cleanString(data[10]), cleanString(data[11]),
                        cleanString(data[12])
                    );
                    appointmentList.add(appointment);
                } else {
                    System.out.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if a specific appointment slot is available
    public boolean checkAvailability(String doctorID, String date, String time) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equals(doctorID) &&
                appointment.getDateOfAppointment().equals(date) &&
                appointment.getAppointmentStartTime().equals(time) &&
                !appointment.getAppointmentStatus().equalsIgnoreCase("Canceled")) {
                return false;
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
        String[] fields = appointmentRecord.split(", ");
        switch (field) {
            case "Date": return fields[4].split(": ")[1];
            case "Time": return fields[5].split(": ")[1];
            default: return "";
        }
    }

    // Replace appointment record
    public boolean replaceAppointmentRecord(String patientID, String oldDate, String oldTime, String newDate, String newTime, String newStatus, String oldStatus) {
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientId().equals(patientID) &&
                appointment.getDateOfAppointment().equals(oldDate) &&
                appointment.getAppointmentStartTime().equals(oldTime) &&
                appointment.getAppointmentStatus().equalsIgnoreCase(oldStatus)) {

                appointment.setDateOfAppointment(newDate);
                appointment.setAppointmentStartTime(newTime);
                appointment.setAppointmentEndTime(LocalTime.parse(newTime, DateTimeFormatter.ofPattern("HHmm")).plusMinutes(30).format(DateTimeFormatter.ofPattern("HHmm")));
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
