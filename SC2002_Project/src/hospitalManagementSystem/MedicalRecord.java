package hospitalManagementSystem;

import java.util.*;

public class MedicalRecord {
	private List<Patient> patientList; //list of all patients
	
	public MedicalRecord() {
		this.patientList = new ArrayList<>();
	}
	
	public void addPatient(Patient patient) {
		patientList.add(patient);
	}
	
	public Patient getPatient(String userId) {
		for (Patient patient:patientList) {
			if (patient.getUserId().equals(userId)) {
				return patient;
			}
		}
		return null;
	}
	public void getRecordDetails(String userId) {
		Patient patient = getPatient(userId);
		if (patient == null) {
			System.out.println( "Invalid Patient.");
		}
		
		System.out.println("=== Patient Information ===");
		System.out.println("Patient ID: "+ patient.getUserId());
		System.out.println("Name: "+ patient.getName());
		System.out.println("Date of Birth: " + patient.getGender());
		System.out.println("Phone Number: " + patient.getContactNum());
		System.out.println("Email Address: " + patient.getEmailAddress());
		System.out.println("Blood Type: ");
		
		System.out.println("=== Diagnoses ===");
		List<Diagnosis> diagnosisList = patient.getDiagnosisList();
		if (diagnosisList.isEmpty()) {
			System.out.println("*NO PAST DIAGNOSES*");
		}
		else {
			for (Diagnosis diagnosis : patient.getDiagnosisList()) {
				System.out.println("1. " + diagnosis.getDiagnosisId());
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
				System.out.println("1. " + treatment.getTreatmentId());
				System.out.println("Desription: " + treatment.getDescription());
				System.out.println("Date "+ treatment.getDate());
			}
		}
		
	}
	
	
}
