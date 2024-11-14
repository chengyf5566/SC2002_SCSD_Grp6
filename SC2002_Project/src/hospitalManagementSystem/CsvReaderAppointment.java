package hospitalManagementSystem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderAppointment {
    private final String filePath_Appointment = "Appointment_Outcome.csv"; // Path to the Appointment_Outcome_Record CSV file
    private List<Appointment> appointmentList = new ArrayList<>();

    public CsvReaderAppointment() {
    	readAndInitializeAppointments();
    }

    // Method to read and initialize appointments from CSV
    public void readAndInitializeAppointments() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath_Appointment), StandardCharsets.UTF_8))) {
            // Skip BOM if present
            if (br.ready()) {
                br.mark(3); // Mark the first 3 bytes
                if (br.read() != 0xEF || br.read() != 0xBB || br.read() != 0xBF) {
                    br.reset(); // No BOM, reset to the start
                }
            }

            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                line = line.trim();  // Remove any extra spaces or newlines
                if (isHeader) {
                    isHeader = false;  // Skip the header row
                    continue;
                }

                String[] data = line.split(",");
                if (data.length == 13) {  // Ensure there are 13 columns (doctorId, patientId, etc.)
                    Appointment appointment = new Appointment(
                        cleanString(data[0]),  // doctorId
                        cleanString(data[1]),  // doctorName
                        cleanString(data[2]),  // patientId
                        cleanString(data[3]),  // patientName
                        cleanString(data[4]),  // appointmentDate
                        cleanString(data[5]),  // appointmentStartTime
                        cleanString(data[6]),  // appointmentEndTime
                        cleanString(data[7]),  // appointmentStatus
                        cleanString(data[8]),  // typeOfService
                        cleanString(data[9]),  // prescribedMedications
                        cleanString(data[10]), // prescribedMedicationsStatus
                        cleanString(data[11]), // diagnosis
                        cleanString(data[12])  // consultationNotes
                    );
                    appointmentList.add(appointment);
                } else {
                    System.out.println("Skipping invalid line: " + line);  // Debugging
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to clean strings (strip unnecessary spaces or quotes)
    private String cleanString(String input) {
        return input.replaceAll("\"", "").trim();  // Remove any quotes and trim whitespace
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }

    // Method to update the appointment file after changes
    public void writeAppointmentFile() {
        File file = new File(filePath_Appointment);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath_Appointment);
            return; // Do not attempt to create new directories or files
        }

        // Proceed with writing to the existing file
        try (FileWriter writer = new FileWriter(file)) {
            // Write the header with the new columns
            writer.write("Doctor ID,Doctor Name,Patient ID,Patient Name,Appointment Date,Appointment Start Time,Appointment End Time,Appointment Status,Type of Service,Prescribed Medications,Prescribed Medications Status,Diagnosis,Consultation Notes\n");

            // Write each appointment's data
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
