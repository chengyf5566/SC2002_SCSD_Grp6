package User;

import Utility.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PatientMenu implements UserRoleMenu {
    private Patient patient;
    private CsvReaderPatient csvReaderPatient;
    
    // Constructor to initialize Patient and CsvReaderPatient
    public PatientMenu(Patient patient) {
        this.patient = patient;
        this.csvReaderPatient = new CsvReaderPatient(); 

    }
    
   
    @Override
    public void displayMenu(Scanner scanner) {
    	
        

        boolean exit = false;
        while (!exit) {
        	// Initialize csv
            patient.readAndInitializePatient();
            patient.readAndInitializeAppointments();
        	
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

            int input = -1; 
            while (input < 1 || input > 9) {  
                System.out.print("Select Option: ");
                if (scanner.hasNextInt()) {  
                    input = scanner.nextInt();
                    scanner.nextLine(); 
                    if (input < 1 || input > 9) {
                        System.out.println("Invalid option. Please select a number between 1 and 9.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a numeric value.");
                    scanner.nextLine(); 
                }
            }

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

////////////////////////////view medical record////////////////////////////  
    private void viewMedicalRecord() {
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

////////////////////////////update personal information (contact no. and email)////////////////////////////  
    private void updatePersonalInformation(Scanner scanner) {
        String newContactNumber = "";
        while (newContactNumber.isEmpty()) {
            System.out.print("Enter new contact number: ");
            newContactNumber = scanner.nextLine().trim();  
            if (newContactNumber.isEmpty()) {
                System.out.println("Contact number cannot be empty. Please try again.");
            }
        }


        String newEmail = "";
        while (newEmail.isEmpty()) {
            System.out.print("Enter new email address: ");
            newEmail = scanner.nextLine().trim(); 
            if (newEmail.isEmpty()) {
                System.out.println("Email address cannot be empty. Please try again.");
            }
        }

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


////////////////////////////change password//////////////////////////// 
    private void changePassword(Scanner scanner) {
        String newPassword = "";
        while (newPassword.isEmpty()) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine().trim();  
            if (newPassword.isEmpty()) {
                System.out.println("Password cannot be empty. Please try again.");
            }
        }

        newPassword = PasswordHashing.hashPassword(newPassword);
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

    
////////////////////////////schedule appointment//////////////////////////// 
    private void scheduleAppointment(Scanner scanner) {
        String date = "";
        String time = "";

        // Handle date input (must be in DD MM YYYY format)
        while (date.isEmpty() || !date.matches("\\d{2} \\d{2} \\d{4}")) {
            System.out.print("Enter the desired appointment date (DD MM YYYY): ");
            date = scanner.nextLine().trim();  
            if (date.isEmpty()) {
                System.out.println("Date cannot be empty. Please try again.");
            } else if (!date.matches("\\d{2} \\d{2} \\d{4}")) {
                System.out.println("Invalid date format. Please enter in DD MM YYYY format.");
            }
        }

        // Handle time input (must be in HHMM format)
        while (time.isEmpty() || !time.matches("\\d{4}")) {
            System.out.print("Enter the desired appointment time (HHMM): ");
            time = scanner.nextLine().trim();  
            if (time.isEmpty()) {
                System.out.println("Time cannot be empty. Please try again.");
            } else if (!time.matches("\\d{4}")) {
                System.out.println("Invalid time format. Please enter in HHMM format.");
            } else {
                int hour = Integer.parseInt(time.substring(0, 2));
                int minute = Integer.parseInt(time.substring(2, 4));
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
                    System.out.println("Invalid time. Please enter a valid time (HHMM).");
                    time = "";  
                } else {
                    // Validate that the time is within 08:00 and 21:00 , doctor working hours
                    LocalTime parsedTime = LocalTime.parse(time, DateTimeFormatter.ofPattern("HHmm"));
                    LocalTime startBoundary = LocalTime.of(8, 0);
                    LocalTime endBoundary = LocalTime.of(21, 0);

                    //each appointment is 30 minutes
                    LocalTime endTime = parsedTime.plusMinutes(30);

                    if (parsedTime.isBefore(startBoundary) || endTime.isAfter(endBoundary)) {
                        System.out.println("The selected time must be between 08:00 and 21:00, and the appointment must end by 21:00.");
                        time = "";  
                    }
                }
            }
        }

        boolean isScheduled = patient.scheduleAppointment(date, time);
        if (isScheduled) {
            System.out.println("Appointment successfully scheduled for " + date + " at " + time + ".");
        } else {
            System.out.println("Failed to schedule appointment.");
        }
    }


////////////////////////////view schedule appointment//////////////////////////// 
    private void viewScheduledAppointments() {
        System.out.println("Scheduled Appointments:");
        System.out.println("\n====================");
        patient.viewScheduledAppointments().forEach(System.out::println);
    }

////////////////////////////re-schedule appointment//////////////////////////// 
    private void rescheduleAppointment(Scanner scanner) {
        boolean isRescheduled = patient.rescheduleAppointment(scanner);
        if (isRescheduled) {
            System.out.println("Appointment successfully rescheduled.");
        } else {
            System.out.println("Failed to reschedule appointment. Please try again.");
        }

    }

////////////////////////////cancel appointment//////////////////////////// 
    private void cancelAppointment(Scanner scanner) {

        boolean isCancelled = patient.cancelAppointment(scanner);
        if (isCancelled) {
            System.out.println("Appointment successfully canceled.");
        } else {
            System.out.println("Failed to cancel appointment. Please try again.");
        }

    }

////////////////////////////view past appointment//////////////////////////// 
    private void viewPastAppointmentOutcomeRecords() {
        List<String> pastRecords = patient.viewPastAppointmentRecords();

        if (pastRecords.isEmpty()) {
            System.out.println("No past appointment outcome records found.");
        } else {
            pastRecords.forEach(System.out::println);
        }
    }
}
