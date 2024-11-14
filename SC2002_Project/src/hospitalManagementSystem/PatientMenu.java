package hospitalManagementSystem;

import java.util.Scanner;
import java.util.List;

public class PatientMenu implements UserRoleMenu {
    private Patient patient;
    private String appointmentFilePath;
    private String patientListFilePath;
    private CsvReaderPatient csvReaderPatient;

    // Constructor to initialize Patient, file path, and CsvReaderPatient
    public PatientMenu(Patient patient, String appointmentFilePath, String patientFilePath) {
        this.patient = patient;
        this.appointmentFilePath = appointmentFilePath;
        this.csvReaderPatient = new CsvReaderPatient(patientFilePath); // Initialize CsvReaderPatient with the file path
        this.csvReaderPatient.readAndInitializePatient(); // Load patient data
    }

    @Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nPatient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. Schedule an Appointment");
            System.out.println("4. Reschedule an Appointment");
            System.out.println("5. Cancel an Appointment");
            System.out.println("6. View Scheduled Appointments");
            System.out.println("7. View Past Appointment Outcome Records");
            System.out.println("8. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1:
                    viewMedicalRecord();
                    break;
                case 2:
                    updatePersonalInformation(scanner);
                    break;
                case 3:
                    scheduleAppointment(scanner);
                    break;
                case 4:
                    rescheduleAppointment(scanner);
                    break;
                case 5:
                    cancelAppointment(scanner);
                    break;
                case 6:
                    viewScheduledAppointments();
                    break;
                case 7:
                    viewPastAppointmentOutcomeRecords();
                    break;
                case 8:
                    System.out.println("Logout\n");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewMedicalRecord() {
        // Use CsvReaderPatient to get the patient details by patientID
        Patient patientDetails = csvReaderPatient.getPatientByID(patient.getPatientID());
        
        if (patientDetails != null) {
            // Display relevant patient information, excluding PatientID and DoctorID
            System.out.println("Name: " + patientDetails.getName());
            System.out.println("Date of Birth: " + patientDetails.getDateOfBirth());
            System.out.println("Gender: " + patientDetails.getGender());
            System.out.println("Blood Type: " + patientDetails.getBloodType());
            System.out.println("Contact Number: " + patientDetails.getContactNum());
            System.out.println("Email: " + patientDetails.getEmail());
            System.out.println("Assigned Doctor Name: " + patientDetails.getAssignedDoctorName());
            System.out.println("Past Diagnoses: " + patientDetails.getPastDiagnoses());
            System.out.println("Prescribed Medicines: " + patientDetails.getPrescribedMedicines());
            System.out.println("Consultation Notes: " + patientDetails.getConsultationNotes());
            System.out.println("Type of Service: " + patientDetails.getTypeOfService());
        } else {
            System.out.println("Patient details not found.");
        }
    }
    
    // Method to handle updating personal information (email and contact number)
    private void updatePersonalInformation(Scanner scanner) {
        System.out.print("Enter new contact number: ");
        String newContactNumber = scanner.nextLine();
        
        System.out.print("Enter new email address: ");
        String newEmail = scanner.nextLine();

        // Update the patient's contact number and email in the current patient object
        patient.setContactNum(newContactNumber);
        patient.setEmail(newEmail);

        // Update the patient in the CsvReaderPatient list
        List<Patient> patients = csvReaderPatient.getPatientList();
        for (Patient p : patients) {
            if (p.getPatientID().equals(patient.getPatientID())) {
                p.setContactNum(newContactNumber);
                p.setEmail(newEmail);
                break;
            }
        }

        // Write the updated patient list back to the CSV
        csvReaderPatient.writePatientDataToCSV();

        System.out.println("Personal information updated successfully.");
    }

    // Method to handle scheduling an appointment
    private void scheduleAppointment(Scanner scanner) {
        System.out.print("Enter the desired appointment date (DD MM YYYY): ");
        String date = scanner.nextLine();
        System.out.print("Enter the desired appointment time (HHMM): ");
        String time = scanner.nextLine();

        // Attempt to schedule the appointment
        boolean isScheduled = patient.scheduleAppointment(date, time, appointmentFilePath);
        if (isScheduled) {
            System.out.println("Appointment successfully scheduled for " + date + " at " + time + ".");
        } else {
            System.out.println("Failed to schedule appointment. The slot might be unavailable.");
        }
    }

    // Method to view scheduled appointments by calling the method in the Patient class
    private void viewScheduledAppointments() {
        System.out.println("Scheduled Appointments:");
        patient.viewScheduledAppointments(appointmentFilePath).forEach(System.out::println);
    }

    // Method to handle rescheduling an appointment
    private void rescheduleAppointment(Scanner scanner) {
        boolean isRescheduled = patient.rescheduleAppointment(appointmentFilePath, scanner);
        if (isRescheduled) {
            System.out.println("Appointment successfully rescheduled.");
        } else {
            System.out.println("Failed to reschedule appointment. Please try again.");
        }
    }
    
    private void cancelAppointment(Scanner scanner) {
        boolean isCancelled = patient.cancelAppointment(appointmentFilePath, scanner);
        if (isCancelled) {
            System.out.println("Appointment successfully canceled.");
        } else {
            System.out.println("Failed to cancel appointment. Please try again.");
        }
    }
    
    private void viewPastAppointmentOutcomeRecords() {
        System.out.println("Past Appointment Outcome Records:");
        List<String> pastRecords = patient.viewPastAppointmentRecords(appointmentFilePath);

        if (pastRecords.isEmpty()) {
            System.out.println("No past appointment outcome records found.");
        } else {
            pastRecords.forEach(System.out::println);
        }
    }
}
