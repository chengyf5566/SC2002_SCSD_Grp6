package hospitalManagementSystem;

import java.util.*;

public class Doctor extends Staff {
	
    public Doctor(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }			
    
	private List<Patient> patientList = new ArrayList<>();
	private String filePath_Patient = "Patient_List.csv";
	private CsvReaderPatient csvReaderPatient;
	
	private List<Appointment> appointmentList;
    private CsvReaderAppointment csvReaderAppointment;
    private String filePath_Appointment = "Appointment_Outcome.csv"; 
	
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
                // Patient found, display the details
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
                break;  // Exit loop once the patient is found
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
                // Printing full details of the confirmed appointment
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
        
        // Get doctor ID
        System.out.print("Enter Doctor ID to view and manage appointments: ");
        String doctorId = scanner.nextLine().trim();

        // Flag to check if any pending appointment exists
        boolean foundPendingAppointments = false;
        
        // Loop through all appointments and display the pending ones for the specified doctor
        //System.out.println("\n--- Pending Appointments for Doctor ID: " + doctorId + " ---");
        for (Appointment appointment : appointmentList) {
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId) && appointment.getAppointmentStatus().equalsIgnoreCase("Pending")) {
                foundPendingAppointments = true;
                
                // Display the pending appointment details
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

                // Ask the doctor if they want to accept or decline the appointment
                System.out.print("Do you want to confirm this appointment? (yes/no): ");
                String decision = scanner.nextLine().trim().toLowerCase();

                if (decision.equals("yes")) {
                    // Change status to "Confirmed"
                    appointment.setAppointmentStatus("Confirmed");
                    System.out.println("Appointment with Patient " + appointment.getPatientName() + " has been confirmed.");
                } else if (decision.equals("no")) {
                    // Change status to "Declined"
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
        
        //get doctor ID
        System.out.print("Enter Doctor ID to view appointments: ");
        String doctorId = scanner.nextLine().trim();

        //Display confirmed appointments for the specified doctor
        List<Appointment> confirmedAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase("Confirmed") &&
                appointment.getDoctorId().equalsIgnoreCase(doctorId)) {
                confirmedAppointments.add(appointment);
            }
        }

        // Check if there are any confirmed appointments for the doctor
        if (confirmedAppointments.isEmpty()) {
            System.out.println("No confirmed appointments available to update for Doctor ID " + doctorId + ".");
            return;
        }

        // Display confirmed appointments
        System.out.println("\n--- Confirmed Appointments ---");
        for (int i = 0; i < confirmedAppointments.size(); i++) {
            System.out.println((i + 1) + ". " + confirmedAppointments.get(i));
        }

        System.out.print("\nChoose the number of the appointment to record the outcome for: ");
        int appointmentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (appointmentChoice < 1 || appointmentChoice > confirmedAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        // Select the chosen appointment
        Appointment appointmentToUpdate = confirmedAppointments.get(appointmentChoice - 1);

        // Find the patient associated with the appointment
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

        // Step 2: Collect information from the doctor
        System.out.print("Enter type of service: ");
        String typeOfService = scanner.nextLine().trim();

        System.out.print("Enter diagnosis: ");
        String diagnosis = scanner.nextLine().trim();

        System.out.print("Enter prescribed medications: ");
        String prescribedMedications = scanner.nextLine().trim();

        System.out.print("Enter consultation notes: ");
        String consultationNotes = scanner.nextLine().trim();

        // Step 3: Update the appointment details
        appointmentToUpdate.setAppointmentStatus("Completed");
        appointmentToUpdate.setTypeOfService(typeOfService);
        appointmentToUpdate.setPrescribedMedications(prescribedMedications);
        appointmentToUpdate.setPrescribedMedicationsStatus("Pending");
        appointmentToUpdate.setDiagnosis(diagnosis);
        appointmentToUpdate.setConsultationNotes(consultationNotes);

        // Step 4: Update patient's records
        List<String> updatedPastDiagnoses = new ArrayList<>(patientToUpdate.getPastDiagnoses());
        updatedPastDiagnoses.add(diagnosis);
        patientToUpdate.setPastDiagnoses(updatedPastDiagnoses);

        List<String> updatedPrescribedMedicines = new ArrayList<>(patientToUpdate.getPrescribedMedicines());
        updatedPrescribedMedicines.add(prescribedMedications);
        patientToUpdate.setPrescribedMedicines(updatedPrescribedMedicines);

        List<String> updatedConsultationNotes = new ArrayList<>(patientToUpdate.getConsultationNotes());
        updatedConsultationNotes.add(consultationNotes);
        patientToUpdate.setConsultationNotes(updatedConsultationNotes);

        List<String> updatedTypeOfService = new ArrayList<>(patientToUpdate.getTypeOfService());
        updatedTypeOfService.add(typeOfService);
        patientToUpdate.setTypeOfService(updatedTypeOfService);

        // Step 5: Write changes back to CSV files
        csvReaderAppointment.writeAppointmentFile();
        csvReaderPatient.writePatientDataToCSV();

        System.out.println("Appointment outcome recorded successfully for Patient ID " + patientId);
    }



    public static void main(String[] args) {
        // Create a Doctor object
        Doctor doctor = new Doctor("D001", "password123", "Doctor", "Male", "Dr. Smith", 45);

        // Initialize patient and appointment data from CSV files
        doctor.readAndInitializePatient();  // Load patient data
        doctor.readAndInitializeAppointments();  // Load appointment data

        // Use the Doctor's methods to view records
        Scanner scanner = new Scanner(System.in);
        
        // Example: Viewing a patient's medical records
        //System.out.println("Viewing a patient's medical records:");
        //doctor.viewPatientMedicalRecords();
        
        // Example: Viewing a doctor's appointment outcomes
        //System.out.println("\nViewing doctor's appointment outcomes:");
        //doctor.viewDoctorAppointmentRecord();
        
        // Example: confirm or cancel patient's appointment
        //System.out.println("\nConfirmed/Cancelled Patient's Appointment");
        //doctor.acceptOrDeclineAppointmentSchedule();
        
        //Example: Patient appointment outcome
        System.out.println("\nPatient's Appointment Outcome");
        doctor.recordAppointmentOutcome();
        
        
        scanner.close();
    }
}





	
    /*
	public DoctorAvailability getAvailability() {
        return availability;
    }
	
	public DoctorAppointments getAppointments() {
        return appointments;
    }

	
	// get doctor details
	public void getAllDetails() {
		String userID = getUserID();
		String role = getRole();
		String gender = getGender();
		String name = getName();
		int age = getAge();
		
		System.out.println("Here is the information about user " + userID);
		System.out.println("Role: " + role);
		System.out.println("Name: " + name);
		System.out.println("Age: " + age);
		System.out.println("Gender: " + gender + "\n");
	}
	
	*/