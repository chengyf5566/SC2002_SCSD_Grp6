package hospitalManagementSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReaderPatient {

    private String filePath = "Patient_List.csv";;
    private List<Patient> patientList = new ArrayList<>();

    public CsvReaderPatient(String filePath) {
        this.filePath = filePath;
    }

    // Method to read data from CSV and create Patient objects
    public void readAndInitializePatient() {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            br.readLine(); // Skip the header row

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                // Ensure the record has the expected number of columns (13 in this case)
                if (values.length < 13) {
                    System.out.println("Incomplete record: " + line);
                    continue;
                }

                // Extract and trim the values from the CSV
                String patientID = values[0].trim();
                String name = values[1].trim();
                String dateOfBirth = values[2].trim();
                String gender = values[3].trim();
                String bloodType = values[4].trim();
                String contactNumber = values[5].trim();
                String email = values[6].trim();
                String assignedDoctorID = values[7].trim();
                String assignedDoctorName = values[8].trim();
                List<String> pastDiagnoses = Arrays.asList(values[9].trim().split(" "));
                List<String> prescribedMedicines = Arrays.asList(values[10].trim().split(" "));
                String consultationNotes = values[11].trim();
                String typeOfService = values[12].trim();

                // Create a Patient object
                Patient patient = new Patient(patientID, "", name, gender, patientID, dateOfBirth, bloodType,
                        contactNumber, email, assignedDoctorID, assignedDoctorName,
                        pastDiagnoses, prescribedMedicines, consultationNotes, typeOfService);

                patientList.add(patient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter for the patient list
    public List<Patient> getPatientList() {
        return patientList;
    }

    // Method to write patient data back to CSV
    public void writePatientDataToCSV() {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Write the header row
            writer.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Number,Email,Assigned Doctor," +
                         "Assigned Doctor Name,Past Diagnoses,Prescribed Medicine,Consultation Notes,Type of Service\n");

            // Write each patient's data
            for (Patient patient : patientList) {
                writer.write(patient.getPatientID() + ","
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
                        + patient.getConsultationNotes() + ","
                        + patient.getTypeOfService() + "\n");
            }

            System.out.println("Patient data successfully written to CSV.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
