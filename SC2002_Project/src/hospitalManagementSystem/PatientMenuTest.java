package hospitalManagementSystem;

import java.util.Scanner;

public class PatientMenuTest {

    public static void main(String[] args) {
        

        // Specify the patient ID to load
        String patientID = "P1001";

        // Load the patient data using CsvReaderPatient
        CsvReaderPatient csvReader = new CsvReaderPatient();
        csvReader.readAndInitializePatient();

        // Retrieve the specific patient based on patient ID
        Patient patient = findPatientByID(patientID, csvReader);

        if (patient == null) {
            System.out.println("Patient with ID " + patientID + " not found.");
            return;
        }

        // Initialize the PatientMenu with the patient, appointment file path, and patient list file path
        PatientMenu patientMenu = new PatientMenu(patient);

        // Create a scanner for user input
        Scanner scanner = new Scanner(System.in);

        // Run the menu
        patientMenu.displayMenu(scanner);

        // Close the scanner
        scanner.close();
    }

    // Helper method to find a patient by ID in the patient list
    private static Patient findPatientByID(String patientID, CsvReaderPatient csvReader) {
        for (Patient patient : csvReader.getPatientList()) {
            if (patient.getPatientID().equals(patientID)) {
                return patient;
            }
        }
        return null;
    }
}
