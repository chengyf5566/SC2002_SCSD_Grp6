package hospitalManagementSystem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class CsvReaderPatient {

    private String filePath = "Patient_List.csv";
    private List<Patient> patientList = new ArrayList<>();
    private boolean isInitialized = false;

    public CsvReaderPatient() {
        readAndInitializePatient();  // Load patient data at initialization
    }

    public void readAndInitializePatient() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
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
                if (line.isEmpty()) {
                    continue;  // Skip empty lines
                }

                if (isHeader) {
                    isHeader = false;  // Skip the header row
                    continue;
                }

                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by comma, but skip commas inside quotes

                // Ensure the record has the expected number of columns (14 in this case)
                if (values.length == 14) {
                    // Extract and clean the values
                    String patientID = cleanString(values[0]);
                    String password = cleanString(values[1]);
                    String name = cleanString(values[2]);
                    String dobString = cleanString(values[3]); // Date as string in format dd/MM/yyyy
                    String gender = cleanString(values[4]);
                    String bloodType = cleanString(values[5]);
                    String contactNumber = cleanString(values[6]);
                    String email = cleanString(values[7]);
                    String assignedDoctorID = cleanString(values[8]);
                    String assignedDoctorName = cleanString(values[9]);

                    // Handle lists (split by space or comma as needed)
                    List<String> pastDiagnoses = new ArrayList<>(Arrays.asList(cleanString(values[10]).split(" ")));
                    List<String> prescribedMedicines = new ArrayList<>(Arrays.asList(cleanString(values[11]).split(" ")));
                    List<String> consultationNotes = new ArrayList<>(Arrays.asList(cleanString(values[12]).split(" ")));
                    List<String> typeOfService = new ArrayList<>(Arrays.asList(cleanString(values[13]).split(" ")));

                    // Create a Patient object with the data
                    Patient patient = new Patient(patientID, password, name, dobString, gender, bloodType,
                            contactNumber, email, assignedDoctorID, assignedDoctorName,
                            pastDiagnoses, prescribedMedicines, consultationNotes, typeOfService);

                    // Add patient to the patientList
                    patientList.add(patient);
                } else {
                    System.out.println("Skipping invalid line: " + line);  // Debugging
                }
            }
            isInitialized = true; // Mark as initialized to avoid re-reading
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Method to get a patient by ID
    public Patient getPatientByID(String patientID) {
        for (Patient patient : patientList) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        System.out.println("No patient found with ID: " + patientID);
        return null;
    }

    // Helper method to clean strings (strip unnecessary spaces or quotes)
    private String cleanString(String input) {
        return input.replaceAll("\"", "").trim();  // Remove any quotes and trim whitespace
    }

    // Getter for the patient list
    public List<Patient> getPatientList() {
        return patientList;
    }

    // Method to write patient data to CSV without needing to pass a filePath parameter
    public void writePatientDataToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write the header row
            writer.write("Patient ID,Password,Name,Date of Birth,Gender,Blood Type,Contact Number,Email,Assigned Doctor ID," +
                         "Assigned Doctor Name,Past Diagnoses,Prescribed Medicine,Consultation Notes,Type of Service\n");

            // Write each patient's data
            for (Patient patient : patientList) {
                writer.write(patient.getPatientID() + ","
                        + patient.getPatientPassword() + "," // Include Password if needed
                        + patient.getName() + ","
                        + patient.getDateOfBirth() + ","
                        + patient.getGender() + ","
                        + patient.getBloodType() + ","
                        + patient.getContactNum() + ","
                        + patient.getEmail() + ","
                        + patient.getAssignedDoctorID() + ","
                        + patient.getAssignedDoctorName() + ","
                        + String.join(" ", patient.getPastDiagnoses()) + ","
                        + String.join(" ", patient.getPrescribedMedicines()) + ","
                        + String.join(" ", patient.getConsultationNotes()) + ","
                        + String.join(" ", patient.getTypeOfService()) + "\n");
            }

            System.out.println("Patient data successfully written to CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
		
	
	
