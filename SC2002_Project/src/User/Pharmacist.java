package User;
import Appointment.Appointment;
import Inventory.Medication;
import Utility.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends Staff {

    private List<Medication> medicationList; 
    private CsvReaderInventory csvReaderInventory; 
    
    private List<Appointment> appointmentList;
    private CsvReaderAppointment csvReaderAppointment;
    
    private List<Staff> staffList;
    private CsvReaderStaff csvReader;
    
    // Method to initialize medication inventory from CSV
    public void initializeInventoryFromCSV() {
        this.csvReaderInventory = new CsvReaderInventory();
        csvReaderInventory.readCsv();
        this.medicationList = csvReaderInventory.getMedicationList();
    }
    
    // Method to initialize appointment outcome from CSV
    public void readAndInitializeAppointments() {
        this.csvReaderAppointment = new CsvReaderAppointment();
        csvReaderAppointment.readCsv();
        this.appointmentList = csvReaderAppointment.getAppointmentList();
    }
    
    // Method to initialize staff list  from CSV
    public void initializeStaffFromCSV() {        
        this.csvReader = new CsvReaderStaff();       
        csvReader.readCsv(); 
        this.staffList = csvReader.getStaffList();  
    }
    
    // Constructor for the Pharmacist class
    public Pharmacist(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  
    }
    
    //manage inventory menu 
    public void manageInventoryStock(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Manage Inventory Stock ---");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Set Replenish Request");
            System.out.println("3. Return to Menu");

            int choice = 0;
            boolean validInput = false;

            while (!validInput) {
                System.out.print("Select an option: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); 
                    if (choice >= 1 && choice <= 3) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid option. Please select a number between 1 and 3.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); 
                }
            }

            switch (choice) {
                case 1:
                    viewMedicationInventory();
                    break;
                case 2:
                    setReplenishRequest(scanner);
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting inventory management.");
                    break;
                default:
                    break;
            }
        }
    }
    
////////////////////////////view medication inventory//////////////////////////// 
    public void viewMedicationInventory() {
        System.out.println("\n--- Medication Inventory ---");
        for (Medication medication : medicationList) {
            String formattedMedication = String.format(
                "\nMedicine Name: '%s'\n" +
                "Initial Stock: '%d'\n" +
                "Current Stock: '%d'\n" +
                "Low Stock Level Alert: '%d'\n" +
                "Replenish Request: '%s'\n" +
                "Replenish Request Amount: '%d'\n" +
                "=========================",
                medication.getMedicineName(),
                medication.getInitialStock(),
                medication.getCurrentStock(),
                medication.getLowStockLevelAlert(),
                medication.getReplenishRequest(),
                medication.getReplenishRequestAmount()
            );
            System.out.println(formattedMedication);
        }
    }

    
    
////////////////////////////set replenish request//////////////////////////// 
    public void setReplenishRequest(Scanner scanner) {
        System.out.println("\n--- Medication Inventory ---");
        for (int i = 0; i < medicationList.size(); i++) {
            System.out.println((i + 1) + ". " + medicationList.get(i).getMedicineName());
        }

        int medicationChoice = -1;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.print("Choose which medication to set replenish request: ");
            
            if (scanner.hasNextInt()) {
                medicationChoice = scanner.nextInt();
                scanner.nextLine(); 

                if (medicationChoice >= 1 && medicationChoice <= medicationList.size()) {
                    validChoice = true;  
                } else {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + medicationList.size());
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }

        Medication medicationToUpdate = medicationList.get(medicationChoice - 1);

        System.out.println("\nCurrent Stock for " + medicationToUpdate.getMedicineName() + ": " + medicationToUpdate.getCurrentStock());
        System.out.println("Initial Stock for " + medicationToUpdate.getMedicineName() + ": " + medicationToUpdate.getInitialStock());

        medicationToUpdate.setReplenishRequest("Yes");

        int replenishAmount = -1;
        boolean validReplenishAmount = false;

        while (!validReplenishAmount) {
            System.out.print("Enter Replenish Request Amount: ");
            if (scanner.hasNextInt()) {
                replenishAmount = scanner.nextInt();
                scanner.nextLine(); 
                if (replenishAmount >= 0) {
                    validReplenishAmount = true; 
                } else {
                    System.out.println("Replenish amount cannot be negative. Please enter a valid amount.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for replenish amount.");
                scanner.nextLine(); 
            }
        }

        medicationToUpdate.setReplenishRequestAmount(replenishAmount);

        System.out.println("Replenish request updated for " + medicationToUpdate.getMedicineName());

        csvReaderInventory.writeCSV();
    }


    
////////////////////////////view all appointments//////////////////////////// 
    public void viewAppointments() {
        System.out.println("\nAppointments with Pending Medications:");
        boolean foundPending = false; 

        for (Appointment appointment : csvReaderAppointment.getAppointmentList()) {
            if ("Pending".equalsIgnoreCase(appointment.getPrescribedMedicationsStatus())) {
                foundPending = true;

                String formattedAppointment = String.format(
                    "\nDoctor Name: '%s'\n" +
                    "Patient Name: '%s'\n" +
                    "Date of Appointment: '%s'\n" +
                    "Start Time: '%s'\n" +
                    "End Time: '%s'\n" +
                    "Status: '%s'\n" +
                    "Type of Service: '%s'\n" +
                    "Prescribed Medications: '%s'\n" +
                    "Medication Quantity: '%s'\n" +
                    "Medication Status: '%s'\n" +
                    "Diagnosis: '%s'\n" +
                    "Consultation Notes: '%s'\n" +
                    "=========================",
                    appointment.getDoctorName(),
                    appointment.getPatientName(),
                    appointment.getDateOfAppointment(),
                    appointment.getAppointmentStartTime(),
                    appointment.getAppointmentEndTime(),
                    appointment.getAppointmentStatus(),
                    appointment.getTypeOfService(),
                    appointment.getPrescribedMedications(),
                    String.valueOf(appointment.getPrescribedMedicationsQuantity()),
                    appointment.getPrescribedMedicationsStatus(),
                    appointment.getDiagnosis(),
                    appointment.getConsultationNotes()
                );

                System.out.println(formattedAppointment);
            }
        }

        if (!foundPending) {
            System.out.println("No appointments with pending medications found.");
        }
    }
    
////////////////////////////dispense medication/////////////////////////// 
    public void dispenseMedication(Scanner scanner) {
        List<Appointment> pendingAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase("Completed") &&
                appointment.getPrescribedMedicationsStatus().equalsIgnoreCase("Pending")) {
                pendingAppointments.add(appointment);
            }
        }

        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointments with completed status found.");
            return;
        }

        System.out.println("\n--- Pending Order ---");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            Appointment appointment = pendingAppointments.get(i);
            String formattedAppointment = String.format(
                "\nAppointment #%d\n" +
                "Doctor Name: '%s'\n" +
                "Patient Name: '%s'\n" +
                "Date of Appointment: '%s'\n" +
                "Start Time: '%s'\n" +
                "End Time: '%s'\n" +
                "Status: '%s'\n" +
                "Type of Service: '%s'\n" +
                "Prescribed Medications: '%s'\n" +
                "Medication Quantity: '%s'\n" +
                "Medication Status: '%s'\n" +
                "Diagnosis: '%s'\n" +
                "Consultation Notes: '%s'\n" +
                "=========================",
                i + 1,  // Appointment number
                appointment.getDoctorName(),
                appointment.getPatientName(),
                appointment.getDateOfAppointment(),
                appointment.getAppointmentStartTime(),
                appointment.getAppointmentEndTime(),
                appointment.getAppointmentStatus(),
                appointment.getTypeOfService(),
                appointment.getPrescribedMedications(),
                String.valueOf(appointment.getPrescribedMedicationsQuantity()),
                appointment.getPrescribedMedicationsStatus(),
                appointment.getDiagnosis(),
                appointment.getConsultationNotes()
            );
            System.out.println(formattedAppointment);
        }


        int appointmentChoice = -1;
        boolean validChoice = false;

        while (!validChoice) {
            System.out.print("\nChoose which appointment to dispense medication for: ");
            
            if (scanner.hasNextInt()) {
                appointmentChoice = scanner.nextInt();
                scanner.nextLine(); 

                if (appointmentChoice >= 1 && appointmentChoice <= pendingAppointments.size()) {
                    validChoice = true; 
                } else {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + pendingAppointments.size());
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }

        Appointment selectedAppointment = pendingAppointments.get(appointmentChoice - 1);

        String prescribedMedicationName = selectedAppointment.getPrescribedMedications();
        int prescribedQuantity = Integer.parseInt(selectedAppointment.getPrescribedMedicationsQuantity());

        Medication selectedMedication = null;
        for (Medication medication : medicationList) {
            if (medication.getMedicineName().equalsIgnoreCase(prescribedMedicationName)) {
                selectedMedication = medication;
                break;
            }
        }

        if (selectedMedication == null) {
            System.out.println("Medication " + prescribedMedicationName + " not found in inventory.");
            return;
        }

        if (prescribedQuantity > selectedMedication.getCurrentStock()) {
            System.out.println("Insufficient stock to dispense the prescribed amount.");
            return;
        }

        selectedAppointment.setPrescribedMedicationsStatus("Dispensed");

        csvReaderAppointment.writeCSV();

        selectedMedication.setCurrentStock(selectedMedication.getCurrentStock() - prescribedQuantity);

        csvReaderInventory.writeCSV();

        System.out.println("Medication dispensed successfully for Patient ID: " + selectedAppointment.getPatientId());
        System.out.println("Updated stock for " + prescribedMedicationName + ": " + selectedMedication.getCurrentStock());
    }

    
////////////////////////////change password//////////////////////////// 
    public void changePassword(Scanner scanner) {
        String newPassword = "";
        
        while (newPassword.isEmpty()) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine().trim(); 
            
            if (newPassword.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter a valid password.");
            }
        }

        this.setPassword(newPassword);

        for (Staff staff : staffList) {
            if (staff.getUserID().equals(this.getUserID())) { 
                staff.setPassword(newPassword); 
                break;
            }
        }

        csvReader.writeCSV();
        System.out.println("Password updated successfully.");
    }

}
