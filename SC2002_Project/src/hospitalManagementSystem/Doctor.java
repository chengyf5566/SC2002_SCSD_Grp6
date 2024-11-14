package hospitalManagementSystem;

import java.util.*;

public class Doctor extends Staff {

    private List<Patient> patientList = new ArrayList<>();
    private CsvReaderPatient csvReaderPatient;
    private final String patientFilePath = "Patient_List.csv";

    private List<Appointment> appointmentList;
    private CsvReaderAppointment csvReaderAppointment;
    private final String appointmentFilePath = "Appointment_Outcome.csv";

    public Doctor(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }

    // Method to initialize patient from CSV
    public void readAndInitializePatient() {
        this.csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readAndInitializePatient();
        this.patientList = csvReaderPatient.getPatientList();  // Assign the read patient list
    }

    // Method to initialize appointment outcome from CSV
    public void readAndInitializeAppointments() {
        this.csvReaderAppointment = new CsvReaderAppointment();
        csvReaderAppointment.readAndInitializeAppointments();
        this.appointmentList = csvReaderAppointment.getAppointmentList();

        // Debugging: Print out the appointments after initialization
        System.out.println("Appointments loaded: " + appointmentList.size());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment); // Assuming the toString method in Appointment prints useful details
        }
    }

    // Method to view a specific patient's medical records based on patient ID
    public void viewPatientMedicalRecords() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Patient ID to view medical records: ");
        String patientId = scanner.nextLine().trim();

        // Find the patient by ID
        boolean found = false;
        for (Patient patient : patientList) {
            if (patient.getPatientID().equalsIgnoreCase(patientId)) {
                System.out.println("\n--- Medical Record for Patient ID: " + patientId + " ---");
                System.out.println("Name: " + patient.getName());
                System.out.println("Date of Birth: " + patient.getDateOfBirth());
                System.out.println("Gender: " + patient.getGender());
                System.out.println("Blood Type: " + patient.getBloodType());
                System.out.println("Contact Number: " + patient.getContactNum());
                System.out.println("Email: " + patient.getEmail());
                System.out.println("Assigned Doctor: " + patient.getAssignedDoctorID() + " - " + patient.getAssignedDoctorName());
                System.out.println("Past Diagnoses: " + patient.getPastDiagnoses());
                System.out.println("Prescribed Medicines: " + patient.getPrescribedMedicines());
                System.out.println("Consultation Notes: " + patient.getConsultationNotes());
                System.out.println("Type of Service: " + patient.getTypeOfService());
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Patient with ID " + patientId + " not found.");
        }
    }

    // Method to view appointment outcomes for this doctor based on doctor ID
    public void viewDoctorAppointmentRecord() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Doctor ID to view appointment outcomes: ");
        String doctorId = scanner.nextLine().trim();

        boolean found = false;
        System.out.println("\n--- Appointment Outcomes for Doctor ID: " + doctorId + " ---");

        // Display only appointments for the specified doctor ID with status "Confirmed"
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId) && appointment.getAppointmentStatus().equalsIgnoreCase("Confirmed")) {
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
            System.out.println("No confirmed appointment outcomes found for Doctor ID " + doctorId);
        }
    }

    public void acceptOrDeclineAppointmentSchedule() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Doctor ID to view and manage appointments: ");
        String doctorId = scanner.nextLine().trim();

        boolean foundPendingAppointments = false;

        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId) && appointment.getAppointmentStatus().equalsIgnoreCase("Pending")) {
                foundPendingAppointments = true;

                System.out.println("\n--- Pending Appointments for Doctor ID: " + doctorId + " ---");
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

                System.out.print("Do you want to confirm this appointment? (yes/no): ");
                String decision = scanner.nextLine().trim().toLowerCase();

                if (decision.equals("yes")) {
                    appointment.setAppointmentStatus("Confirmed");
                    System.out.println("Appointment with Patient " + appointment.getPatientName() + " has been confirmed.");
                } else if (decision.equals("no")) {
                    appointment.setAppointmentStatus("Cancelled");
                    System.out.println("Appointment with Patient " + appointment.getPatientName() + " has been cancelled.");
                } else {
                    System.out.println("Invalid choice. Appointment not confirmed or declined.");
                }
            }
        }

        if (!foundPendingAppointments) {
            System.out.println("No pending appointments found for Doctor ID " + doctorId);
        }

        // After updating the status, write the changes back to the CSV

        csvReaderAppointment.writeAppointmentFile();
    }

    public void recordAppointmentOutcome() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Doctor ID to view appointments: ");
        String doctorId = scanner.nextLine().trim();

        List<Appointment> confirmedAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase("Confirmed") &&
                appointment.getDoctorId().equalsIgnoreCase(doctorId)) {
                confirmedAppointments.add(appointment);
            }
        }

        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments available to update for Doctor ID " + doctorId + ".");
            return;
        }

        System.out.println("\n--- Confirmed Appointments ---");
        for (int i = 0; i < confirmedAppointments.size(); i++) {
            System.out.println((i + 1) + ". " + confirmedAppointments.get(i));
        }

        System.out.print("\nChoose the number of the appointment to record the outcome for: ");
        int appointmentChoice = scanner.nextInt();
        scanner.nextLine();

        if (appointmentChoice < 1 || appointmentChoice > confirmedAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Appointment appointmentToUpdate = confirmedAppointments.get(appointmentChoice - 1);

        String patientId = appointmentToUpdate.getPatientId();
        Patient patientToUpdate = null;

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

        System.out.print("Enter type of service: ");
        String typeOfService = scanner.nextLine().trim();

        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine().trim();

        System.out.print("Enter prescribed medications: ");
        String prescribedMedications = scanner.nextLine().trim();

        System.out.print("Enter consultation notes: ");
        String consultationNotes = scanner.nextLine().trim();

        appointmentToUpdate.setAppointmentStatus("Completed");
        appointmentToUpdate.setTypeOfService(typeOfService);
        appointmentToUpdate.setPrescribedMedications(prescribedMedications);
        appointmentToUpdate.setPrescribedMedicationsStatus("Pending");
        appointmentToUpdate.setDiagnosis(diagnosis);
        appointmentToUpdate.setConsultationNotes(consultationNotes);

        patientToUpdate.getPastDiagnoses().add(diagnosis);
        patientToUpdate.getPrescribedMedicines().add(prescribedMedications);
        patientToUpdate.getConsultationNotes().add(consultationNotes);
        patientToUpdate.getTypeOfService().add(typeOfService);

        csvReaderAppointment.writeAppointmentFile();
        csvReaderPatient.writePatientDataToCSV();

        System.out.println("Appointment outcome recorded successfully for Patient ID " + patientId);
    }
}
