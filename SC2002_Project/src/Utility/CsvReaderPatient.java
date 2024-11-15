package Utility;
import User.Patient;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReaderPatient implements CsvReader {  // Missing class declaration

    private final String filePath = "Patient_List.csv"; 
    private List<Patient> patientList = new ArrayList<>();
    private boolean isInitialized = false;

    public CsvReaderPatient() {
    	readCsv();  // Load patient data at initialization
    }

    public void readCsv() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            // Skip BOM if present
            if (br.ready()) {
                br.mark(3); // Mark the first 3 bytes
                if (br.read() != 0xEF || br.read() != 0xBB || br.read() != 0xBF) {
                    br.reset(); // No BOM, reset to the start
                }
            }

            String line;
            
            // Clear the existing patient list to avoid duplicates
            patientList.clear();
            
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

                // Split by commas, but skip commas inside quotes, allowing empty fields
                String[] values = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); 

                // Ensure the record has the expected number of columns (14 in this case)
                // Adjust to allow for missing values in optional columns (e.g., pastDiagnoses, prescribedMedicines)
                String[] fixedValues = new String[14];
                System.arraycopy(values, 0, fixedValues, 0, values.length); 

                // Fill missing values with empty strings if not enough columns
                for (int i = values.length; i < 14; i++) {
                    fixedValues[i] = "";
                }

                // Extract and clean the values
                String patientID = cleanString(fixedValues[0]);
                String password = cleanString(fixedValues[1]);
                String name = cleanString(fixedValues[2]);
                String dobString = cleanString(fixedValues[3]); // Date as string in format dd/MM/yyyy
                String gender = cleanString(fixedValues[4]);
                String bloodType = cleanString(fixedValues[5]);
                String contactNumber = cleanString(fixedValues[6]);
                String email = cleanString(fixedValues[7]);
                String assignedDoctorID = cleanString(fixedValues[8]);
                String assignedDoctorName = cleanString(fixedValues[9]);

                // Handle lists (split by space or comma as needed), defaulting to empty lists if data is missing
                List<String> pastDiagnoses = new ArrayList<>(Arrays.asList(cleanString(fixedValues[10]).split(" ")));
                List<String> prescribedMedicines = new ArrayList<>(Arrays.asList(cleanString(fixedValues[11]).split(" ")));
                List<String> consultationNotes = new ArrayList<>(Arrays.asList(cleanString(fixedValues[12]).split(" ")));
                List<String> typeOfService = new ArrayList<>(Arrays.asList(cleanString(fixedValues[13]).split(" ")));


                // Create a Patient object with the data
                Patient patient = new Patient(patientID, password, name, dobString, gender, bloodType,
                        contactNumber, email, assignedDoctorID, assignedDoctorName,
                        pastDiagnoses, prescribedMedicines, consultationNotes, typeOfService);

                // Add patient to the patientList
                patientList.add(patient);
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

    public void writeCSV() {
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
