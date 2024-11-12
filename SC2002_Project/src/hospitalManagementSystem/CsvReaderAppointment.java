package hospitalManagementSystem;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderAppointment {
    private String filePath_Appointment = "Appointment_Outcome.csv"; // Path to the Appointment_Outcome_Record CSV file
    private List<Appointment> appointmentList = new ArrayList<>();

    public CsvReaderAppointment(String filePath_Appointment) {
        this.filePath_Appointment = filePath_Appointment;
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
                if (data.length == 8) {  // Ensure there are 8 columns (doctorId, patientId, etc.)
                    Appointment appointment = new Appointment(
                        cleanString(data[0]),  // doctorId
                        cleanString(data[1]),  // patientId
                        cleanString(data[2]),  // dateOfAppointment
                        cleanString(data[3]),  // appointmentStatus
                        cleanString(data[4]),  // typeOfService
                        cleanString(data[5]),  // prescribedMedications
                        cleanString(data[6]),  // prescribedMedicationsStatus
                        cleanString(data[7])   // consultationNotes
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
    public void writeAppointmentFile(String filePath_Appointment) {
        File file = new File(filePath_Appointment);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist at path: " + filePath_Appointment);
            return; // Do not attempt to create new directories or files
        }

        // Proceed with writing to the existing file
        try (FileWriter writer = new FileWriter(file)) {
            // Write the header
            writer.write("Doctor ID,Patient ID,Date of appointment,Appointment Status,Type of Service,Prescribed Medications,Prescribed Medications Status,Consultation Notes\n");

            // Write each appointment's data
            for (Appointment appointment : appointmentList) {
                writer.write(appointment.getDoctorId() + ","
                        + appointment.getPatientId() + ","
                        + appointment.getDateOfAppointment() + ","
                        + appointment.getAppointmentStatus() + "," // Write appointment status
                        + appointment.getTypeOfService() + ","
                        + appointment.getPrescribedMedications() + ","
                        + appointment.getPrescribedMedicationsStatus() + ","
                        + appointment.getConsultationNotes() + "\n");
            }

            System.out.println("Appointment list updated in CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
