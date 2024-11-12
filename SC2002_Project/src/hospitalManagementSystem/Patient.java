package hospitalManagementSystem;
import java.util.*;

public class Patient extends User {

	private String emailAddress;
	private String contactNum;
	private String dateOfBirth;
	private String bloodType;
    private String gender;
	private List<Treatment> treatmentList; //list of treatment objects
	private List<Diagnosis> diagnosisList; //list of diagnosis objects
	private List<Appointment> appointmentList; // list of appointment objects
	private List<AppointmentSlot> appointmentSlotList; //list of appointmentSlots objects for cancelAppointment()
	private List<Prescription> prescriptionList; // list of prescriptions
	private List<AppointmentOutcome> appointmentOutcomeList; //list of appointmentOutcomes
	
	public Patient(String name, String userId,String password, String gender, String emailAddress, String contactNum, String dateOfBirth, String bloodType) {
		super(name, userId,password); //need to add password?
        this.gender = gender;
		this.emailAddress = emailAddress;
		this.contactNum = contactNum;
		this.dateOfBirth = dateOfBirth;
		this.bloodType = bloodType;
		this.diagnosisList = new ArrayList<>();
		this.treatmentList = new ArrayList<>();
		this.appointmentList = new ArrayList<>();
		this.appointmentSlotList = new ArrayList<>();
		this.prescriptionList = new ArrayList<>();
		this.appointmentOutcomeList = new ArrayList<>(); //
		
	}
	
	public void setEmailAddress(String emailAddress) {this.emailAddress = emailAddress;}
	public void setContactNum(String contactNum) {this.contactNum = contactNum;}
	
	// Method to add a treatment, used in doctor class: patient.addTreatment(treatment)
	public void addTreatment(Treatment treatment) {
	    this.treatmentList.add(treatment);
	}

	// Method to add a diagnosis, used in doctor class: patient.addDiagnosis(diagnosis);
	public void addDiagnosis(Diagnosis diagnosis) {
	    this.diagnosisList.add(diagnosis);
	}
	
	public void addPrescription(Prescription prescription) { //used in doctor class
	        this.prescriptionList.add(prescription);
	}
	
	public void addAppointmentOutcome(AppointmentOutcome outcome) { //used in doctor class
        this.appointmentOutcomeList.add(outcome);
    }
	
    public String getEmailAddress() {
        return emailAddress;
    }

    public String getContactNum() {
        return contactNum;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getBloodType() {
        return bloodType;
    }

    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public List<Diagnosis> getDiagnosisList() {
        return diagnosisList;
    }
    
    public List<Prescription> getPrescriptionList() {
        return prescriptionList;
    }
    
    public List<AppointmentOutcome> getAppointmentOutcomes() {
        return appointmentOutcomeList;
    }
    
    //========APPOINTMENT============
   
    public void viewAvailableSlots(Doctor doctor) {
        List<AppointmentSlot> availableSlots = doctor.getAvailableSlots();
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots.");
        } else {
            System.out.println("Available Slots:");
            for (AppointmentSlot slot : availableSlots) {
                System.out.println("Date: " + slot.getDate() + " | Time: " + slot.getTimeSlot());
            }
        }
    }
    
    public Appointment scheduleAppointment(Doctor doctor, AppointmentSlot slot) {
        if (slot == null || !slot.getIsAvailable()) {
            System.out.println("Selected slot is unavailable.");
            return null;
        }
        

        Date selectedDate = slot.getDate();
        String selectedTimeSlot = slot.getTimeSlot();
        String appointmentID = "Appointment" + (appointmentList.size() + 1); 

        // Create new appointment object
        Appointment appointment = new Appointment(appointmentID, doctor, this, selectedDate, selectedTimeSlot, "pending");
        appointmentList.add(appointment);
        appointmentSlotList.add(slot); //add to appointmentSlotList (maybe idk)

        // Set slot as unavailable
        slot.setIsAvailable(false);

        System.out.println("==Appointment scheduled!==");
        System.out.println("Doctor: "+doctor);
        System.out.println("Date: "+selectedDate);
        System.out.println("Time: "+selectedTimeSlot);
        return appointment;
    }
    
    public List<Appointment> viewScheduledAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("No scheduled appointments.");
            return new ArrayList<>();
        }

        System.out.println("==Scheduled Appointments:==");
        for (Appointment appointment : appointmentList) {
            System.out.println("Appointment ID: " + appointment.getAppointmentID());
            System.out.println("Doctor: "+appointment.getDoctor().getName() );
            System.out.println("Date: "+ appointment.getDate());
            System.out.println("Time: "+ appointment.getTimeSlot());
            System.out.println("Status: "+ appointment.getStatus()); 
        }
        return appointmentList;
    }
    
    public Boolean cancelAppointment(Appointment appointment) {
        if (appointment == null) {
            return false;
        }

        //set AppointmentSlot as available
        //SRY IDK HOW TO CONNECT PATIENT TO THE APPOINTMENTSLOT. I NEED TO USE THE appointmentSlot.setIsAvailable(false);
        //i'm just gonna make a appointmentSlot list for the patient
        AppointmentSlot slot = null;
        for (AppointmentSlot s : appointmentSlotList) {
            if (s.getDate().equals(appointment.getDate()) && s.getTimeSlot().equals(appointment.getTimeSlot())) {
                slot = s;
                break;
            }
        }
        
        slot.setIsAvailable(true); 
        appointment.setStatus("canceled");

        // remove Appointment object from the patients appointmentList and remove slot obj from slotList
        appointmentList.remove(appointment);
        appointmentSlotList.remove(slot);
        return true;
    }
    
    public boolean rescheduleAppointment(Appointment appointment, AppointmentSlot newSlot) {
        // Check if the new slot is available
        if (newSlot == null || !newSlot.getIsAvailable()) {
            System.out.println("New slot is unavailable.");
            return false;
        }

        // Cancel the current appointment
        if (!cancelAppointment(appointment)) {
            System.out.println("Failed to cancel the existing appointment.");
            return false;
        }

        // Schedule a new appointment using scheduleAppointment
        Doctor doctor = appointment.getDoctor();
        Appointment newAppointment = scheduleAppointment(doctor, newSlot);

        if (newAppointment != null) {
            System.out.println("Appointment rescheduled");
            return true;
        } else {
            System.out.println("Failed to reschedule appointment.");
            return false;
        }
    }

    public List<AppointmentOutcome> viewPastAppointments() {
        if (appointmentOutcomeList.isEmpty()) {
            System.out.println("No past appointments found.");
            return new ArrayList<>();
        }

        System.out.println("==Appointment Outcome Records==");
        for (AppointmentOutcome outcome : appointmentOutcomeList) {
            System.out.println("Appointment ID: " + outcome.getAppointment().getAppointmentID());
            System.out.println("Date: " + outcome.getDate());
            System.out.println("Service Type: " + outcome.getServiceType());
            System.out.println("Consultation Notes: " + outcome.getConsultationNotes());
            System.out.println("Outcome: " + outcome.getOutcome());
            System.out.println();
        }

        return appointmentOutcomeList;
    }
 
}


    

