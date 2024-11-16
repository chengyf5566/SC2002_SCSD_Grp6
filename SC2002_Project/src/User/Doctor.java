package User;

import Appointment.Appointment;
import Inventory.Medication;
import Utility.*;
import java.util.*;

public class Doctor extends Staff {

    private List<Patient> patientList = new ArrayList<>();
    private CsvReaderPatient csvReaderPatient;
    //private final String patientFilePath = "Patient_List.csv";

    private List<Appointment> appointmentList;
    private CsvReaderAppointment csvReaderAppointment;
    //private final String appointmentFilePath = "Appointment_Outcome.csv";
    
    private List<Staff> staffList;
    private CsvReaderStaff csvReader;
    
    private List<Medication> medicationList;
    private CsvReaderInventory csvReaderInventory;

    public Doctor(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }

    // Method to initialize patient from CSV
    public void readAndInitializePatient() {
        this.csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readCsv();
        this.patientList = csvReaderPatient.getPatientList();  // Assign the read patient list
    }

    // Method to initialize appointment outcome from CSV
    public void readAndInitializeAppointments() {
        this.csvReaderAppointment = new CsvReaderAppointment();
        csvReaderAppointment.readCsv();
        this.appointmentList = csvReaderAppointment.getAppointmentList();

        
        // Debugging: Print out the appointments after initialization
        System.out.println("Appointments loaded: " + appointmentList.size());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment); // Assuming the toString method in Appointment prints useful details
        } 
    }
    
    // Method to initialize staff list  from CSV
    public void initializeStaffFromCSV() {        
        this.csvReader = new CsvReaderStaff(); // Initialize the class-level csvReader with the given file path        
        csvReader.readCsv(); // Read and initialize staff from the CSV
        this.staffList = csvReader.getStaffList();  // Assign the read staff list
    }
    
    public void initializeInventoryFromCSV() {
        this.csvReaderInventory = new CsvReaderInventory();
        csvReaderInventory.readCsv();
        this.medicationList = csvReaderInventory.getMedicationList();
    }

////////////////////////////view patient medical records////////////////////////////
    public void viewPatientMedicalRecords() {
        Scanner scanner = new Scanner(System.in);
        String doctorId = this.getUserID(); // Use the logged-in doctor's ID

        // Display the list of patients assigned to the doctor
        System.out.println("\n--- Patients under Doctor ID: " + doctorId + " ---");
        List<Patient> patientsUnderDoctor = new ArrayList<>();
        for (Patient patient : patientList) {
            if (patient.getAssignedDoctorID().equalsIgnoreCase(doctorId)) {
                patientsUnderDoctor.add(patient);
            }
        }

        // If no patients found for the doctor
        if (patientsUnderDoctor.isEmpty()) {
            System.out.println("No patients assigned under Doctor ID: " + doctorId);
            return;
        }

        // Show the list of patients assigned to the doctor
        for (int i = 0; i < patientsUnderDoctor.size(); i++) {
            Patient patient = patientsUnderDoctor.get(i);
            System.out.println((i + 1) + ". Patient ID: " + patient.getPatientID() + " | Name: " + patient.getName());
        }

        int patientChoice = -1;
        boolean validChoice = false;

        // Prompt the user until a valid patient number is selected
        while (!validChoice) {
            System.out.print("\nEnter the number of the patient to view their medical records: ");
            
            if (scanner.hasNextInt()) {
                patientChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                // Validate the patient's choice
                if (patientChoice >= 1 && patientChoice <= patientsUnderDoctor.size()) {
                    validChoice = true;  // Valid input
                } else {
                    System.out.println("Invalid selection. Please enter a number between 1 and " + patientsUnderDoctor.size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        // Get the selected patient
        Patient selectedPatient = patientsUnderDoctor.get(patientChoice - 1);

        // Display the selected patient's medical records
        System.out.println("=====================================");
        System.out.println("--- Medical Record for Patient ID: " + selectedPatient.getPatientID() + " ---");
        System.out.println("Name: " + selectedPatient.getName());
        System.out.println("Date of Birth: " + selectedPatient.getDateOfBirth());
        System.out.println("Gender: " + selectedPatient.getGender());
        System.out.println("Blood Type: " + selectedPatient.getBloodType());
        System.out.println("Contact Number: " + selectedPatient.getContactNum());
        System.out.println("Email: " + selectedPatient.getEmail());
        System.out.println("Assigned Doctor Name: " + selectedPatient.getAssignedDoctorName());
        System.out.println("Past Diagnoses: " + (selectedPatient.getPastDiagnoses().isEmpty() ? "None" : String.join(", ", selectedPatient.getPastDiagnoses())));
        System.out.println("Prescribed Medicines: " + (selectedPatient.getPrescribedMedicines().isEmpty() ? "None" : String.join(", ", selectedPatient.getPrescribedMedicines())));
        System.out.println("Consultation Notes: " + (selectedPatient.getConsultationNotes().isEmpty() ? "None" : String.join(", ", selectedPatient.getConsultationNotes())));
        System.out.println("Type of Service: " + (selectedPatient.getTypeOfService().isEmpty() ? "None" : String.join(", ", selectedPatient.getTypeOfService())));
        System.out.println("=====================================");
    }

    
////////////////////////////view doctor own appointment schedule////////////////////////////  
    public void viewDoctorAppointmentRecord() {
        boolean found = false;
        System.out.println("\n--- Appointment Outcomes for Doctor ID: " + this.getUserID() + " ---");

        // Display only appointments for the logged-in doctor ID with status "Confirmed"
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equalsIgnoreCase(this.getUserID()) && 
                appointment.getAppointmentStatus().equalsIgnoreCase("Confirmed")) {
                System.out.println("--------------------------------------------------");
                System.out.println("Doctor ID: " + appointment.getDoctorId());
                System.out.println("Doctor Name: " + appointment.getDoctorName());
                System.out.println("Patient ID: " + appointment.getPatientId());
                System.out.println("Patient Name: " + appointment.getPatientName());
                System.out.println("Appointment Date: " + appointment.getDateOfAppointment());
                System.out.println("Appointment Start Time: " + appointment.getAppointmentStartTime());
                System.out.println("Appointment End Time: " + appointment.getAppointmentEndTime());
                System.out.println("Appointment Status: " + appointment.getAppointmentStatus());
                System.out.println("Type of Service: " + appointment.getTypeOfService());
                System.out.println("Prescribed Medications: " + appointment.getPrescribedMedications());
                System.out.println("Prescribed Medications Status: " + appointment.getPrescribedMedicationsStatus());
                System.out.println("Diagnosis: " + appointment.getDiagnosis());
                System.out.println("Consultation Notes: " + appointment.getConsultationNotes());
                System.out.println("--------------------------------------------------");

                found = true;
            }
        }

        if (!found) {
            System.out.println("No confirmed appointment outcomes found for Doctor ID " + this.getUserID());
        }
    }

    
////////////////////////////accept Or decline AppointmentSchedule////////////////////////////  
    public void acceptOrDeclineAppointmentSchedule() {
        Scanner scanner = new Scanner(System.in);
        String doctorId = this.getUserID(); // Use the logged-in doctor's ID

        boolean foundPendingAppointments = false;

        System.out.println("\n--- Pending Appointments for Doctor ID: " + doctorId + " ---");

        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId) && 
                appointment.getAppointmentStatus().equalsIgnoreCase("Pending")) {
                foundPendingAppointments = true;

                System.out.println("--------------------------------------------------");
                System.out.println("Doctor ID: " + appointment.getDoctorId());
                System.out.println("Doctor Name: " + appointment.getDoctorName());
                System.out.println("Patient ID: " + appointment.getPatientId());
                System.out.println("Patient Name: " + appointment.getPatientName());
                System.out.println("Appointment Date: " + appointment.getDateOfAppointment());
                System.out.println("Appointment Start Time: " + appointment.getAppointmentStartTime());
                System.out.println("Appointment End Time: " + appointment.getAppointmentEndTime());
                System.out.println("Appointment Status: " + appointment.getAppointmentStatus());
                System.out.println("Type of Service: " + appointment.getTypeOfService());
                System.out.println("Prescribed Medications: " + appointment.getPrescribedMedications());
                System.out.println("Prescribed Medications Status: " + appointment.getPrescribedMedicationsStatus());
                System.out.println("Diagnosis: " + appointment.getDiagnosis());
                System.out.println("Consultation Notes: " + appointment.getConsultationNotes());
                System.out.println("--------------------------------------------------");

                // Input handling for "yes/no"
                String decision = "";
                while (true) {
                    System.out.print("Do you want to confirm this appointment? (yes/no): ");
                    decision = scanner.nextLine().trim().toLowerCase();

                    if (decision.equals("yes")) {
                        appointment.setAppointmentStatus("Confirmed");
                        System.out.println("\nAppointment with Patient " + appointment.getPatientName() + " has been confirmed.");
                        break; // Exit the loop if input is valid
                    } else if (decision.equals("no")) {
                        appointment.setAppointmentStatus("Canceled");
                        System.out.println("\nAppointment with Patient " + appointment.getPatientName() + " has been cancelled.");
                        break; // Exit the loop if input is valid
                    } else {
                        System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                    }
                }
            }
        }

        if (!foundPendingAppointments) {
            System.out.println("No pending appointments found for Doctor ID " + doctorId);
        }

        // After updating the status, write the changes back to the CSV
        csvReaderAppointment.writeCSV();
    }

////////////////////////////record appointment outcome////////////////////////////  
    public void recordAppointmentOutcome() {
        Scanner scanner = new Scanner(System.in);

        // Get Doctor ID (assuming this is the logged-in doctor's ID)
        String doctorId = this.getUserID(); // Use the logged-in doctor's ID

        // List all confirmed appointments for the doctor
        List<Appointment> confirmedAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase("Confirmed") &&
                appointment.getDoctorId().equalsIgnoreCase(doctorId)) {
                confirmedAppointments.add(appointment);
            }
        }

        // If no confirmed appointments are found, print a message and exit
        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments available to update for Doctor ID " + doctorId + ".");
            return;
        }

        System.out.println("\n--- Confirmed Appointments ---");
        for (int i = 0; i < confirmedAppointments.size(); i++) {
            System.out.println((i + 1) + ". " + confirmedAppointments.get(i));
        }

        // Repeated prompt until valid selection is made
        int appointmentChoice = -1;
        while (appointmentChoice < 1 || appointmentChoice > confirmedAppointments.size()) {
            System.out.print("\nChoose which appointment to record the outcome for: ");
            // Ensure the input is valid and not empty
            if (scanner.hasNextInt()) {
                appointmentChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (appointmentChoice < 1 || appointmentChoice > confirmedAppointments.size()) {
                    System.out.println("Invalid selection. Please select a valid appointment.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        // Get the selected appointment
        Appointment appointmentToUpdate = confirmedAppointments.get(appointmentChoice - 1);

        String patientId = appointmentToUpdate.getPatientId();
        Patient patientToUpdate = null;

        // Find the patient for the selected appointment
        for (Patient patient : patientList) {
            if (patient.getPatientID().equalsIgnoreCase(patientId)) {
                patientToUpdate = patient;
                break;
            }
        }

        if (patientToUpdate == null) {
            System.out.println("Patient with ID " + patientId + " not found.");
            return;
        }

        // Ask for type of service
        System.out.print("Enter type of service: ");
        String typeOfService = scanner.nextLine().trim();
        while (typeOfService.isEmpty()) {
            System.out.println("Input cannot be empty. Please enter a valid type of service.");
            System.out.print("Enter type of service: ");
            typeOfService = scanner.nextLine().trim();
        }

        // Ask for diagnosis
        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine().trim();
        while (diagnosis.isEmpty()) {
            System.out.println("Input cannot be empty. Please enter a valid diagnosis.");
            System.out.print("Enter diagnosis: ");
            diagnosis = scanner.nextLine().trim();
        }
        
        // Ask for consultation notes
        System.out.print("Enter consultation notes: ");
        String consultationNotes = scanner.nextLine().trim();  // Changed from "diagnosis" to "consultationNotes"
        while (consultationNotes.isEmpty()) {
            System.out.println("Input cannot be empty. Please enter a valid consultation note.");
            System.out.print("Enter consultation notes: ");
            consultationNotes = scanner.nextLine().trim();  // Re-prompt for consultation notes
        }

        // Display available medications
        System.out.println("\nAvailable Medications:");
        for (int i = 0; i < medicationList.size(); i++) {
            Medication medication = medicationList.get(i);
            System.out.println((i + 1) + ". " + medication.getMedicineName() + " (Available: " + medication.getCurrentStock() + ")");
        }

        // Repeated prompt for medication choice until valid input
        int medicationChoice = -1;
        while (medicationChoice < 1 || medicationChoice > medicationList.size()) {
            System.out.print("\nChoose a medication to prescribe (enter number): ");
            if (scanner.hasNextInt()) {
                medicationChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (medicationChoice < 1 || medicationChoice > medicationList.size()) {
                    System.out.println("Invalid medication choice. Please select a valid medication.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid medication number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        Medication selectedMedication = medicationList.get(medicationChoice - 1);

        // Repeated prompt for quantity until valid input
        int prescribedQuantity = -1;
        while (prescribedQuantity <= 0 || prescribedQuantity > selectedMedication.getCurrentStock()) {
            System.out.print("Enter quantity to prescribe: ");
            if (scanner.hasNextInt()) {
                prescribedQuantity = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (prescribedQuantity <= 0 || prescribedQuantity > selectedMedication.getCurrentStock()) {
                    System.out.println("Invalid quantity. Not enough stock or invalid input.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid quantity.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        // Update the appointment with prescribed medications and quantity
        appointmentToUpdate.setAppointmentStatus("Completed");
        appointmentToUpdate.setTypeOfService(typeOfService);
        appointmentToUpdate.setPrescribedMedications(selectedMedication.getMedicineName());
        appointmentToUpdate.setPrescribedMedicationsQuantity(Integer.toString(prescribedQuantity));
        appointmentToUpdate.setPrescribedMedicationsStatus("Pending");
        appointmentToUpdate.setDiagnosis(diagnosis);
        appointmentToUpdate.setConsultationNotes(consultationNotes);

        // Update patient records
        patientToUpdate.getPastDiagnoses().add(diagnosis);
        patientToUpdate.getPrescribedMedicines().add(selectedMedication.getMedicineName());
        patientToUpdate.getConsultationNotes().add(appointmentToUpdate.getConsultationNotes());
        patientToUpdate.getTypeOfService().add(typeOfService);

        // Write the updated appointment and patient records to CSV
        csvReaderAppointment.writeCSV();
        csvReaderPatient.writeCSV();

        System.out.println("Appointment outcome recorded successfully for Patient ID " + patientId);
    }


////////////////////////////change password////////////////////////////  
    public void changePassword(Scanner scanner) {
        String newPassword = "";
        
        // Ensure password is not empty
        while (newPassword.isEmpty()) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine().trim(); // Remove leading/trailing spaces
            
            if (newPassword.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter a valid password.");
            }
        }

        // Set the new password for the current administrator
        this.setPassword(newPassword);

        // Update the password in the staff list and save to CSV
        for (Staff staff : staffList) {
            if (staff.getUserID().equals(this.getUserID())) { // Assuming getUserID() is accessible
                staff.setPassword(newPassword); // Assuming setPassword() is accessible
                break;
            }
        }

        // Write updated staff data to CSV
        csvReader.writeCSV();
        System.out.println("Password updated successfully.");
    }

}
