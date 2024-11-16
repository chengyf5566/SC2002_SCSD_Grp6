package Utility;
import Appointment.Appointment;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CsvReaderAppointment implements CsvReader{
    public String filePath_Appointment = "Appointment_Outcome.csv";
    public List<Appointment> appointmentList = new ArrayList<>();


    // Read and initialize appointments from CSV
    public void readCsv() {
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

                String[] data = line.split(",", -1);
                if (data.length < 8) {
                    System.out.println("Skipping invalid line due to insufficient fields: " + line);
                    continue;
                }

                String[] fullData = new String[14];
                for (int i = 0; i < fullData.length; i++) {
                    fullData[i] = (i < data.length) ? cleanString(data[i]) : "";
                }

                Appointment appointment = new Appointment(
                    fullData[0], fullData[1], fullData[2], fullData[3],
                    fullData[4], fullData[5], fullData[6], fullData[7],
                    fullData[8], fullData[9], fullData[10], fullData[11], fullData[12], fullData[13]
                );
                appointmentList.add(appointment);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Write appointment list to CSV file
    public void writeCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath_Appointment))) {
        	writer.write("Doctor ID,Doctor Name,Patient ID,Patient Name,Appointment Date,Appointment Start Time,"
                    + "Appointment End Time,Appointment Status,Type of Service,Prescribed Medications,Prescribed Medications Quantity,"
                    + "Prescribed Medications Status,Diagnosis,Consultation Notes\n");
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
                        + appointment.getPrescribedMedicationsQuantity() + ","
                        + appointment.getPrescribedMedicationsStatus() + ","
                        + appointment.getDiagnosis() + ","
                        + appointment.getConsultationNotes() + "\n");
            }
            System.out.println("Appointment list updated in CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Helper method to clean strings
    private String cleanString(String input) {
        return input.replaceAll("\"", "").trim();
    }

    public List<Appointment> getAppointmentList() {
        return appointmentList;
    }
    
////////////////////////////Check if a specific appointment slot is available////////////////////////////  
    public boolean checkAvailability(String doctorID, String date, String time) {
        LocalTime requestedStartTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));
        LocalTime requestedEndTime = requestedStartTime.plusMinutes(30); // Assuming a 30-minute slot

        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equals(doctorID) &&
                appointment.getDateOfAppointment().equals(date) &&
                !appointment.getAppointmentStatus().equalsIgnoreCase("Canceled")) {

                LocalTime appointmentStartTime = LocalTime.parse(appointment.getAppointmentStartTime(), DateTimeFormatter.ofPattern("HHmm"));
                LocalTime appointmentEndTime = LocalTime.parse(appointment.getAppointmentEndTime(), DateTimeFormatter.ofPattern("HHmm"));

                // Check for overlap
                if (requestedStartTime.isBefore(appointmentEndTime) && requestedEndTime.isAfter(appointmentStartTime)) {
                    return false; // Slot overlaps with an existing appointment
                }
            }
        }
        return true; // No conflicts found
    }



////////////////////////////add appointment////////////////////////////  
    public boolean addAppointmentRecord(String doctorID, String doctorName, String patientID, String patientName,
                                     String date, String startTime, String endTime, String status) {
        Appointment newAppointment = new Appointment(doctorID, doctorName, patientID, patientName, date, startTime, endTime, status, "", "", "", "", "", "");
        appointmentList.add(newAppointment);
        writeCSV();
        return true;
    }

    
////////////////////////////Delete an appointment record by exact match////////////////////////////  
    public boolean deleteAppointmentRecord(String appointmentRecord) {
        boolean isRemoved = appointmentList.removeIf(app -> app.toString().trim().equals(appointmentRecord.trim()));
        if (isRemoved) {
        	writeCSV();
        	return true;
        } else {
            System.out.println("Appointment record not found or could not be removed.");
            return false;
        }
    }

    
////////////////////////////Get a list of appointments by patient ID with specific statuses////////////////////////////  
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

    
//////////////////////////// Helper to extract details from appointment string////////////////////////////  
    public String extractAppointmentDetail(String appointmentRecord, String field) {
        String[] fields = appointmentRecord.split(", ");

        if (fields.length <= 5) {
            System.out.println("Invalid appointment record: Not enough fields - " + appointmentRecord);
            return "";
        }

        try {
            switch (field) {
                case "Date":
                    if (fields[2].contains("=")) {
                        return fields[2].split("=")[1].trim().replace("'", "");
                    } else {
                        System.out.println("Invalid Date format in record: " + appointmentRecord);
                        return "";
                    }
                case "StartTime":
                    if (fields[3].contains("=")) {
                        return fields[3].split("=")[1].trim().replace("'", "");
                    } else {
                        System.out.println("Invalid StartTime format in record: " + appointmentRecord);
                        return "";
                    }
                case "EndTime":
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
////////////////////////////replace appointment record//////////////////////////// 
    public boolean replaceAppointmentRecord(String patientID, String oldDate, String oldStartTime, String oldEndTime, String newDate, String newStartTime, String newStatus, String oldStatus) {
        // Validate oldStatus
        if (!oldStatus.equalsIgnoreCase("Confirmed") && !oldStatus.equalsIgnoreCase("Pending")) {
            System.out.println("Invalid oldStatus provided: " + oldStatus);
            return false;
        }

        LocalTime newStart = LocalTime.parse(newStartTime, DateTimeFormatter.ofPattern("HHmm"));
        LocalTime newEnd = newStart.plusMinutes(30);

        // Find the appointment to replace
        for (Appointment appointment : appointmentList) {
            if (appointment.getPatientId().trim().equals(patientID.trim()) &&
                appointment.getDateOfAppointment().trim().equals(oldDate.trim()) &&
                appointment.getAppointmentStartTime().trim().equals(oldStartTime.trim())) {

                //System.out.println("Expected Status: '" + oldStatus + "', Found Status: '" + appointment.getAppointmentStatus() + "'");

                // Match only 'Confirmed' or 'Pending'
                if (!appointment.getAppointmentStatus().equalsIgnoreCase("Confirmed") &&
                    !appointment.getAppointmentStatus().equalsIgnoreCase("Pending")) {
                    System.out.println("Invalid status for replacement: " + appointment.getAppointmentStatus());
                    return false;
                }

                // Update appointment details
                appointment.setDateOfAppointment(newDate.trim());
                appointment.setAppointmentStartTime(newStartTime.trim());
                appointment.setAppointmentEndTime(newEnd.format(DateTimeFormatter.ofPattern("HHmm")));
                appointment.setAppointmentStatus(newStatus.trim());

                writeCSV();
                //System.out.println("Appointment successfully rescheduled.");
                return true;
            }
        }

        // No matching appointment found
        System.out.println("No matching appointment found to replace.");
        return false;
    }
}