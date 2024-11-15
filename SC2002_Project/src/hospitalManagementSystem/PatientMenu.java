package hospitalManagementSystem;

import java.util.Scanner;
import java.util.List;

public class PatientMenu implements UserRoleMenu {
    private Patient patient;
    private CsvReaderPatient csvReaderPatient;
    private String appointmentFilePath;

    // Constructor to initialize Patient and CsvReaderPatient
    public PatientMenu(Patient patient) {
        this.patient = patient;
        this.csvReaderPatient = new CsvReaderPatient(); //this can remove? or will break

    }
    
   
    @Override
    public void displayMenu(Scanner scanner) {
    	// Initialize csv files
    	patient.readAndInitializePatient();
    	patient.readAndInitializeAppointments();

        boolean exit = false;
        while (!exit) {
            System.out.println("\nPatient Menu:");
            System.out.println("1. View Medical Record");
            System.out.println("2. Update Personal Information");
            System.out.println("3. Change Password");
            System.out.println("4. Schedule an Appointment");
            System.out.println("5. Reschedule an Appointment");
            System.out.println("6. Cancel an Appointment");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. View Past Appointment Outcome Records");
            System.out.println("9. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1 -> viewMedicalRecord();
                case 2 -> updatePersonalInformation(scanner);
                case 3 -> {
                	changePassword(scanner);
                	 exit = true;
                }
                case 4 -> scheduleAppointment(scanner);
                case 5 -> rescheduleAppointment(scanner);
                case 6 -> cancelAppointment(scanner);
                case 7 -> viewScheduledAppointments();
                case 8 -> viewPastAppointmentOutcomeRecords();
                case 9 -> {
                    System.out.println("Logout\n");
                    exit = true;
                }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewMedicalRecord() {
    	//System.out.println(patient.getPatientID());
        Patient patientDetails = csvReaderPatient.getPatientByID(patient.getPatientID());

        if (patientDetails != null) {
        	System.out.println("=====================================");
            System.out.println("Medical Record:");
            System.out.println("Name: " + patientDetails.getName());
            System.out.println("Date of Birth: " + patientDetails.getDateOfBirth());
            System.out.println("Gender: " + patientDetails.getGender());
            System.out.println("Blood Type: " + patientDetails.getBloodType());
            System.out.println("Contact Number: " + patientDetails.getContactNum());
            System.out.println("Email: " + patientDetails.getEmail());
            System.out.println("Assigned Doctor Name: " + patientDetails.getAssignedDoctorName());
            System.out.println("Past Diagnoses: " + (patientDetails.getPastDiagnoses().isEmpty() ? "None" : String.join(", ", patientDetails.getPastDiagnoses())));
            System.out.println("Prescribed Medicines: " + (patientDetails.getPrescribedMedicines().isEmpty() ? "None" : String.join(", ", patientDetails.getPrescribedMedicines())));
            System.out.println("Consultation Notes: " + (patientDetails.getConsultationNotes().isEmpty() ? "None" : String.join(", ", patientDetails.getConsultationNotes())));
            System.out.println("Type of Service: " + (patientDetails.getTypeOfService().isEmpty() ? "None" : String.join(", ", patientDetails.getTypeOfService())));
            System.out.println("=====================================");
        } else {
            System.out.println("Patient details not found.");
        }
    }

    private void updatePersonalInformation(Scanner scanner) {
        System.out.print("Enter new contact number: ");
        String newContactNumber = scanner.nextLine();

        System.out.print("Enter new email address: ");
        String newEmail = scanner.nextLine();

        patient.setContactNum(newContactNumber);
        patient.setEmail(newEmail);

        List<Patient> patients = csvReaderPatient.getPatientList();
        for (Patient p : patients) {
            if (p.getPatientID().equals(patient.getPatientID())) {
                p.setContactNum(newContactNumber);
                p.setEmail(newEmail);
                break;
            }
        }

        csvReaderPatient.writeCSV();
        System.out.println("Personal information updated successfully.");
    }


    private void changePassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

        patient.setPatientPassword(newPassword);

        List<Patient> patients = csvReaderPatient.getPatientList();
        for (Patient p : patients) {
            if (p.getPatientID().equals(patient.getPatientID())) {
                p.setPatientPassword(newPassword);
                break;
            }
        }

        csvReaderPatient.writeCSV();
        System.out.println("Password updated successfully.");
    }

    private void scheduleAppointment(Scanner scanner) {
        System.out.print("Enter the desired appointment date (DD MM YYYY): ");
        String date = scanner.nextLine();
        System.out.print("Enter the desired appointment time (HHMM): ");
        String time = scanner.nextLine();

        boolean isScheduled = patient.scheduleAppointment(date, time);
        if (isScheduled) {
            System.out.println("Appointment successfully scheduled for " + date + " at " + time + ".");
        } else {
            System.out.println("Failed to schedule appointment.");
        }

    }

    private void viewScheduledAppointments() {
        System.out.println("Scheduled Appointments:");
        patient.viewScheduledAppointments().forEach(System.out::println);
    }

    private void rescheduleAppointment(Scanner scanner) {
        boolean isRescheduled = patient.rescheduleAppointment(scanner);
        if (isRescheduled) {
            System.out.println("Appointment successfully rescheduled.");
        } else {
            System.out.println("Failed to reschedule appointment. Please try again.");
        }

    }

    private void cancelAppointment(Scanner scanner) {

        boolean isCancelled = patient.cancelAppointment(scanner);
        if (isCancelled) {
            System.out.println("Appointment successfully canceled.");
        } else {
            System.out.println("Failed to cancel appointment. Please try again.");
        }

    }

    private void viewPastAppointmentOutcomeRecords() {
        System.out.println("Past Appointment Outcome Records:");
        List<String> pastRecords = patient.viewPastAppointmentRecords();

        if (pastRecords.isEmpty()) {
            System.out.println("No past appointment outcome records found.");
        } else {
            pastRecords.forEach(System.out::println);
        }
    }
}
