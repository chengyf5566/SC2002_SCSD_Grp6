package User;
import Appointment.Appointment;
import Inventory.Medication;
import Utility.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pharmacist extends Staff {

    private List<Medication> medicationList; // Medication list for inventory management
    private CsvReaderInventory csvReaderInventory; // Inventory csv file reader
    
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
        this.csvReader = new CsvReaderStaff(); // Initialize the class-level csvReader with the given file path        
        csvReader.readCsv(); // Read and initialize staff from the CSV
        this.staffList = csvReader.getStaffList();  // Assign the read staff list
    }
    
    // Constructor for the Pharmacist class
    public Pharmacist(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
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

            // Ensure valid input for the menu choice
            while (!validInput) {
                System.out.print("Select an option: ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character left by nextInt()
                    if (choice >= 1 && choice <= 3) {
                        validInput = true;
                    } else {
                        System.out.println("Invalid option. Please select a number between 1 and 3.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); // Consume the invalid input
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
                    // This block won't be reached due to the validInput check above
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

        // Prompt until a valid choice is entered
        while (!validChoice) {
            System.out.print("Choose which medication to set replenish request: ");
            
            // Check if the input is a valid integer
            if (scanner.hasNextInt()) {
                medicationChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                // Check if the choice is within the valid range
                if (medicationChoice >= 1 && medicationChoice <= medicationList.size()) {
                    validChoice = true;  // Valid input
                } else {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + medicationList.size());
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        // Get the selected medication based on user choice
        Medication medicationToUpdate = medicationList.get(medicationChoice - 1);

        // Display the current stock and initial stock
        System.out.println("\nCurrent Stock for " + medicationToUpdate.getMedicineName() + ": " + medicationToUpdate.getCurrentStock());
        System.out.println("Initial Stock for " + medicationToUpdate.getMedicineName() + ": " + medicationToUpdate.getInitialStock());

        // Set the replenish request to "Yes"
        medicationToUpdate.setReplenishRequest("Yes");

        // Ask for the replenish amount
        int replenishAmount = -1;
        boolean validReplenishAmount = false;

        // Ensure the replenish amount is valid (non-negative and non-empty)
        while (!validReplenishAmount) {
            System.out.print("Enter Replenish Request Amount: ");
            if (scanner.hasNextInt()) {
                replenishAmount = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character
                if (replenishAmount >= 0) {
                    validReplenishAmount = true; // Valid input
                } else {
                    System.out.println("Replenish amount cannot be negative. Please enter a valid amount.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for replenish amount.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        // Set the replenish request amount in the medication
        medicationToUpdate.setReplenishRequestAmount(replenishAmount);

        System.out.println("Replenish request updated for " + medicationToUpdate.getMedicineName());

        // Write the updated inventory to CSV
        csvReaderInventory.writeCSV();
    }


    
////////////////////////////view all appointments//////////////////////////// 
    public void viewAppointments() {
        System.out.println("\nAppointments with Pending Medications:");
        boolean foundPending = false; // Flag to check if any pending records exist

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
        // Step 1: Collect appointments with "Completed" status and "Pending" medication status
        List<Appointment> pendingAppointments = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentStatus().equalsIgnoreCase("Completed") &&
                appointment.getPrescribedMedicationsStatus().equalsIgnoreCase("Pending")) {
                pendingAppointments.add(appointment);
            }
        }

        // Check if there are any pending appointments that meet the criteria
        if (pendingAppointments.isEmpty()) {
            System.out.println("No pending appointments with completed status found.");
            return;
        }

        // Show pending appointments to pharmacist in the desired format
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

        // Prompt until a valid choice is entered
        while (!validChoice) {
            System.out.print("\nChoose which appointment to dispense medication for: ");
            
            // Check if the input is a valid integer
            if (scanner.hasNextInt()) {
                appointmentChoice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                // Check if the choice is within the valid range
                if (appointmentChoice >= 1 && appointmentChoice <= pendingAppointments.size()) {
                    validChoice = true;  // Valid input
                } else {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + pendingAppointments.size());
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }

        Appointment selectedAppointment = pendingAppointments.get(appointmentChoice - 1);

        // Extract Prescribed Medication and Quantity from the Appointment
        String prescribedMedicationName = selectedAppointment.getPrescribedMedications();
        int prescribedQuantity = Integer.parseInt(selectedAppointment.getPrescribedMedicationsQuantity());

        // Find the Medication in the Inventory
        Medication selectedMedication = null;
        for (Medication medication : medicationList) {
            if (medication.getMedicineName().equalsIgnoreCase(prescribedMedicationName)) {
                selectedMedication = medication;
                break;
            }
        }

        // Check if the medication is available in the inventory
        if (selectedMedication == null) {
            System.out.println("Medication " + prescribedMedicationName + " not found in inventory.");
            return;
        }

        //Check if sufficient stock is available
        if (prescribedQuantity > selectedMedication.getCurrentStock()) {
            System.out.println("Insufficient stock to dispense the prescribed amount.");
            return;
        }

        //Update Appointment Status and Dispense Medication
        selectedAppointment.setPrescribedMedicationsStatus("Dispensed");

        // Write updated appointment data back to CSV
        csvReaderAppointment.writeCSV();

        //Decrease Stock Level in Inventory
        selectedMedication.setCurrentStock(selectedMedication.getCurrentStock() - prescribedQuantity);

        // Write updated inventory back to CSV
        csvReaderInventory.writeCSV();

        // Output successful dispense message
        System.out.println("Medication dispensed successfully for Patient ID: " + selectedAppointment.getPatientId());
        System.out.println("Updated stock for " + prescribedMedicationName + ": " + selectedMedication.getCurrentStock());
    }

    
////////////////////////////change password//////////////////////////// 
    public void changePassword(Scanner scanner) {
        String newPassword = "";
        
        // Ensure password is not empty
        while (newPassword.isEmpty()) {
            System.out.print("Enter new password: ");
            newPassword = scanner.nextLine().trim(); // Remove leading/trailing spaces
            
            if (newPassword.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter a valid password.");
            }
        }

        // Set the new password for the current administrator
        this.setPassword(newPassword);

        // Update the password in the staff list and save to CSV
        for (Staff staff : staffList) {
            if (staff.getUserID().equals(this.getUserID())) { // Assuming getUserID() is accessible
                staff.setPassword(newPassword); // Assuming setPassword() is accessible
                break;
            }
        }

        // Write updated staff data to CSV
        csvReader.writeCSV();
        System.out.println("Password updated successfully.");
    }

}
