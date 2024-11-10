package hospitalManagementSystem;

import java.util.Scanner;
import java.util.*;
import java.util.Date;

public class DoctorMenu implements UserRoleMenu {

    @Override
    public void displayMenu(Scanner scanner) {
        boolean exit = false;
        
        // ************* DOC OC ********************//
        
        // create a doctor object for testing
        Doctor doctor = new Doctor("D001", "password123", "Doctor", 'M', "Dr. John Doe", 45);
        
        // doctor empty slots
        Calendar calendar = Calendar.getInstance();
        // date is 15 dec 2024 at 12pm
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER); // Note: Months are 0-based in Calendar (December is 11)
        calendar.set(Calendar.DAY_OF_MONTH, 15);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // Set hour to 0
        calendar.set(Calendar.MINUTE, 0);       // Set minute to 0
        calendar.set(Calendar.SECOND, 0);       // Set second to 0
        calendar.set(Calendar.MILLISECOND, 0);
        Date temp1 = calendar.getTime();       
        
        // date is 23 nov 2024 at 1am
        calendar.set(Calendar.YEAR, 2024);
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER); // Note: Months are 0-based in Calendar (December is 11)
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  // Set hour to 0
        calendar.set(Calendar.MINUTE, 0);       // Set minute to 0
        calendar.set(Calendar.SECOND, 0);       // Set second to 0
        calendar.set(Calendar.MILLISECOND, 0);
        Date temp2 = calendar.getTime();
        
        doctor.setAvailability(temp1, "1200");
        doctor.setAvailability(temp2, "0100");
       
        
        // ************* PATIENT ********************//
        
        // create a patient object for testing
    	Patient patient = new Patient("Jane", "PA001" ,"Patient", 23, "pasd", "female", "jane@example.com", "8877 0092", "09-09-99", "O+");
    	Patient patient1 = new Patient("Howard", "PA002" ,"Patient", 23, "pasd", "male", "howard@example.com", "8877 0092", "09-09-99", "O+");
    	Patient patient2 = new Patient("Domingo", "PA003" ,"Patient", 23, "pasd", "female", "jenny@example.com", "8877 0092", "09-09-99", "O+");

    	// create new appointment slot for doc
    	AppointmentSlot patientAppointmentSlotTest = new AppointmentSlot(temp1, "1200", true);
    	AppointmentSlot patientAppointmentSlotTest1 = new AppointmentSlot(temp1, "1400", true); // doc not free
    	AppointmentSlot patientAppointmentSlotTest2 = new AppointmentSlot(temp2, "0100", true);
    	// create appointment for patient and assign our doc and the slot doc is free
    	Appointment forDoc = patient.scheduleAppointment(doctor, patientAppointmentSlotTest);
    	Appointment forDoc1 = patient1.scheduleAppointment(doctor, patientAppointmentSlotTest1); // doc not free
    	Appointment forDoc2 = patient2.scheduleAppointment(doctor, patientAppointmentSlotTest2);

        // get appointments from patient related to the doctor
    	// problem --> need to ask every patient for this before initialising
    	// if a new patient is added, will not show up here HELP
        List<Appointment> appointmentCheck = patient.getAppointmentListByDoctor(doctor);
        List<Appointment> appointmentCheck1 = patient1.getAppointmentListByDoctor(doctor); // not freee
        List<Appointment> appointmentCheck2 = patient2.getAppointmentListByDoctor(doctor);
        
        doctor.addAppointments(appointmentCheck);
        doctor.addAppointments(appointmentCheck1); // not free
        doctor.addAppointments(appointmentCheck2);
        
        
        // from the doctors side, accept the patients appointment
        //boolean checkTest = doctor.setAppointmentAccept(forDoc); //
        //System.out.println(checkTest);
        
        List<Appointment> schedule = doctor.getPersonalSchedule();
        List<Appointment> scheduleUpcoming = doctor.getAvailableSlots();
        
        
        while (!exit) {
            System.out.println("\nDoctor Menu:");
            System.out.println("1. View Patient Medical Records");
            System.out.println("2. Update Patient Medical Records");
            System.out.println("3. View Personal Schedule"); // initally all true
            System.out.println("4. Set Availability for Appointments"); // set the ones he doesnt want wiht false
            System.out.println("5. Accept or Decline Appointment Requests");
            System.out.println("7. View Scheduled Appointments");
            System.out.println("8. Record Appointment Outcome");
            System.out.println("9. Logout");

            System.out.print("Select Option: ");
            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1: System.out.println("View Patient Medical Records"); break;
                case 2: System.out.println("Update Patient Medical Records"); break;
                
                
                case 3: 
                	System.out.println("View Doctor Schedule");
                	if (schedule.isEmpty()) {
                	    System.out.println("No appointments scheduled for this doctor.");
                	} else {
                	    System.out.println("Doctor's Schedule:");
                	    for (Appointment appointment : schedule) {
                	        System.out.println("Appointment ID: " + appointment.getAppointmentID());
                	        System.out.println("Patient Name: " + appointment.getPatient().getName());
                	        System.out.println("Date: " + appointment.getDate());
                	        System.out.println("Time: " + appointment.getTimeSlot());
                	        System.out.println("Status: " + appointment.getStatus());
                	        System.out.println(" ");
                	    }
                	}
                	
                	// need to show the available time slots
                	
                	
                	break;
                	
                case 4: System.out.println("Set Availability for Appointments"); break;
                // need to change Appointment class to have "isDocAvailable"
                // change teh avalibility in AppointmentSlot
                // autochange in appointments --> by default should be available
                
                case 5: 
                	System.out.println("Accept or Decline Appointment Requests"); 
                	// test for accept
                	// get the specific appointment
                	Appointment acceptAppointment = doctor.getSpecificAppointment(schedule, "AP002");
                	// pass to accept
                	boolean success = doctor.setAppointmentAccept(acceptAppointment);
                	System.out.println(success + " appointment booking acceptance");
                	
                	/*
                	// test for decline
                	Appointment declineAppointment = doctor.getSpecificAppointment(schedule, "AP001");
                	// pass to accept
                	boolean succ = doctor.setAppointmentDecline(declineAppointment);
                	System.out.println(succ + " appointment booking cancellation");
                	
                	*/
                	
                break;
          
                case 7: 
                	System.out.println("View Scheduled Appointments");
                	for (Appointment appointment : scheduleUpcoming) {
                		if (appointment.getStatus() == "confirmed") {
                			
                			System.out.println("Appointment ID: " + appointment.getAppointmentID());
                	        System.out.println("Patient Name: " + appointment.getPatient().getName());
                	        System.out.println("Date: " + appointment.getDate());
                	        System.out.println("Time: " + appointment.getTimeSlot());
                	        System.out.println("Status: " + appointment.getStatus());
                	        System.out.println(" ");
                			
                		
                		}
            	        
            	    }
                break;
                case 8: System.out.println("Record Appointment Outcome"); break;
                case 9: System.out.println("Logout\n"); exit = true; break;
                default: System.out.println("Invalid option. Try again.");
            }
        }
    }
}
