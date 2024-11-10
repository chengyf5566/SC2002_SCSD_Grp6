package hospitalManagementSystem;

import java.util.*;

public class DoctorPatientManager {
	
	private final Doctor doctor;
	
	public DoctorPatientManager(Doctor doctor) {
		this.doctor = doctor;
	}
	
	// *********** PATIENT  *********** //
	
		// GETTERS
		
		// view patients under their care --> check through appointments
		public List<Patient> getAllPatients() {
			List<Patient> patients = new ArrayList<>();
			
			List<Appointment> appointments = new ArrayList<>();
			
			appointments = doctor.getAppointments().getAllAppointments();
			
			for(Appointment appointment : appointments) {
				Patient tempPatient = appointment.getPatient();
				patients.add(tempPatient);
			}
			
			return patients;	
		}
		
		public Patient getSpecificPatient(String patientID) {
			List<Patient> tempPatientList = getAllPatients();
			for(Patient tempPatient : tempPatientList) {
				if(tempPatient.getUserID() == patientID) {
					return tempPatient;
				}
			}
			return null;	
		}

		// view patient medical records based for all patients
		public void getPatientMedicalRecords() {
			List<Patient> patientsRecords = new ArrayList<>();
			patientsRecords = getAllPatients(); 
			
			for(Patient patient : patientsRecords) {
				System.out.println("Here are the medical records for your patients: ");
				System.out.println("Patient Name: " + patient.getName());
				
				System.out.println("=== Diagnoses ===");
				List<Diagnosis> diagnosisList = patient.getDiagnosisList();
				if (diagnosisList.isEmpty()) {
					System.out.println("*NO PAST DIAGNOSES*");
				}
				else {
					for (Diagnosis diagnosis : patient.getDiagnosisList()) {
						System.out.println("1. " + diagnosis.getDiagnosisID());
						System.out.println("Desription: " + diagnosis.getDescription());
						System.out.println("Date "+ diagnosis.getDate());
					}
				}
				
				System.out.println("=== Treatments ===");
				List<Treatment> treatmentList = patient.getTreatmentList();
				if (treatmentList.isEmpty()) {
					System.out.println("*NO PAST TREATMENTS*");
				}
				else {
					for (Treatment treatment : patient.getTreatmentList()) {
						System.out.println("1. " + treatment.getTreatmentID());
						System.out.println("Desription: " + treatment.getDescription());
						System.out.println("Date "+ treatment.getLocalDate());
					}
				}
			}
		}
		
		// SETTERS

		// add diagnosis based on patient id --> check if it belongs to doc
		public void setAddDiagnosisToPatient(String id, String description, String patientID){
			Patient patient = getSpecificPatient(patientID);
			Date now = getCurrentDate();
			Diagnosis diagnosis = new Diagnosis(id, description, now);
			patient.addDiagnosis(diagnosis);
		}

		// add treatment plan based on patient id --> check if it belongs to doc
		public void setAddTreatmentToPatient(String id, String description, String patientID){
			Patient patient = getSpecificPatient(patientID);
			Date now = getCurrentDate();
			Treatment treatment = new Treatment(id, description, now);
			patient.addTreatment(treatment);
		}
		
		// set a new outcome 
		public void setAppointmentOutcome(String appointmentID, String patientID, String serviceType, String consultationNotes){
			Appointment appointment = doctor.getAppointments().getSpecificAppointment(appointmentID);
			Date now = getCurrentDate();
			Patient patient = getSpecificPatient(patientID);
			List<Prescription> prescriptions = new ArrayList<>();
			prescriptions = patient.getPrescriptionList();

			AppointmentOutcome outcom = new AppointmentOutcome(appointment, now, serviceType, prescriptions, consultationNotes, "completed");
			
			patient.addAppointmentOutcome(outcom);
			
			// update appointment as completed
			 doctor.getAppointments().setAppointmentStatus(appointment, new StatusCompleted());
		}
		
		// HELPERS/TESTING
		// get the current date
		public Date getCurrentDate() {
					
			Calendar calendar = Calendar.getInstance();
		    int year = calendar.get(Calendar.YEAR);
		    int month = calendar.get(Calendar.MONTH) + 1; 
		    int day = calendar.get(Calendar.DAY_OF_MONTH);
		    calendar.set(Calendar.HOUR_OF_DAY, 0);  // Set hour to 0
		    calendar.set(Calendar.MINUTE, 0);       // Set minute to 0
		    calendar.set(Calendar.SECOND, 0);       // Set second to 0
		    calendar.set(Calendar.MILLISECOND, 0);
			        
		    Date now = calendar.getTime();
		       
		    return now;
		}
	
	

}
