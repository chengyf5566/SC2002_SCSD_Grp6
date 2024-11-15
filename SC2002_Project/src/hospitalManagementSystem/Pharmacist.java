package hospitalManagementSystem;

import java.util.List;
import java.util.ArrayList;
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
        
        /*
        // Debugging: Print out the appointments after initialization
        System.out.println("Appointments loaded: " + appointmentList.size());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment); // Assuming the toString method in Appointment prints useful details
        } */
    }
    
    // Method to initialize staff list  from CSV
    public void initializeStaffFromCSV() {        
        this.csvReader = new CsvReaderStaff(); // Initialize the class-level csvReader with the given file path        
        csvReader.readCsv(); // Read and initialize staff from the CSV
        this.staffList = csvReader.getStaffList();  // Assign the read staff list
    }
    
    public void manageInventoryStock(Scanner scanner) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n--- Manage Inventory Stock ---");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Set Replenish Request");
            System.out.println("3. Return to Menu");

            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

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
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
    
    public void viewMedicationInventory() {
        System.out.println("\nMedication Inventory:");
        for (Medication medication : medicationList) {
            System.out.println(medication); // Assuming the Medication class has a properly overridden toString method
        }
    }
    
    public void setReplenishRequest(Scanner scanner) {
        System.out.println("\n--- Medication Inventory ---");
        for (int i = 0; i < medicationList.size(); i++) {
            System.out.println((i + 1) + ". " + medicationList.get(i).getMedicineName());
        }

        System.out.print("Enter the number of the medication to set replenish request: ");
        int medicationChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        // Validate the user's choice
        if (medicationChoice < 1 || medicationChoice > medicationList.size()) {
            System.out.println("Invalid selection. Please try again.");
            return;
        }

        // Get the selected medication based on user choice
        Medication medicationToUpdate = medicationList.get(medicationChoice - 1);

        // Set the replenish request to "Yes"
        medicationToUpdate.setReplenishRequest("Yes");

        // Ask for the replenish amount
        System.out.print("Enter Replenish Request Amount: ");
        int replenishAmount = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        // Set the replenish request amount in the medication
        medicationToUpdate.setReplenishRequestAmount(replenishAmount);

        System.out.println("Replenish request updated for " + medicationToUpdate.getMedicineName());

        // Write the updated inventory to CSV
        csvReaderInventory.writeCSV();
    }

    
    // Constructor for the Pharmacist class
    public Pharmacist(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }

    // Method to view all appointments
    public void viewAppointments() {
        System.out.println("\nAppointments:");
        for (Appointment appointment : csvReaderAppointment.getAppointmentList()) {
            System.out.println(appointment);
        }
    }
    
    public void dispenseMedication(Scanner scanner) {
        // Step 1: Display appointments with "Completed" status and "Pending" medication status
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

        // Show pending appointments to pharmacist
        System.out.println("\n--- Pending Appointments ---");
        for (int i = 0; i < pendingAppointments.size(); i++) {
            System.out.println((i + 1) + ". " + pendingAppointments.get(i));
        }

        System.out.print("\nChoose which appointment to dispense medication for: ");
        int appointmentChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (appointmentChoice < 1 || appointmentChoice > pendingAppointments.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Appointment selectedAppointment = pendingAppointments.get(appointmentChoice - 1);

        // Step 2: Choose Medication
        System.out.println("\n--- Medication Inventory ---");
        for (int i = 0; i < medicationList.size(); i++) {
            System.out.println((i + 1) + ". " + medicationList.get(i).getMedicineName());
        }

        System.out.print("\nEnter the number of the medication to dispense: ");
        int medicationChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (medicationChoice < 1 || medicationChoice > medicationList.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Medication selectedMedication = medicationList.get(medicationChoice - 1);

        // Step 3: Choose Medication Amount
        System.out.print("Enter the amount of " + selectedMedication.getMedicineName() + " to dispense: ");
        int dispenseAmount = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (dispenseAmount > selectedMedication.getCurrentStock()) {
            System.out.println("Insufficient stock to dispense this amount.");
            return;
        }

        // Step 4: Update Appointment Status
        selectedAppointment.setPrescribedMedicationsStatus("Dispensed");

        // Update the appointment CSV with the new status
        csvReaderAppointment.writeCSV();

        // Step 5: Decrease Stock Level in Inventory
        selectedMedication.setCurrentStock(selectedMedication.getCurrentStock() - dispenseAmount);

        // Write updated inventory back to CSV
        csvReaderInventory.writeCSV();

        System.out.println("Medication dispensed successfully for Patient ID: " + selectedAppointment.getPatientId());
        System.out.println("Updated stock for " + selectedMedication.getMedicineName() + ": " + selectedMedication.getCurrentStock());
    }
    
    //change password method
    public void changePassword(Scanner scanner) {
        System.out.print("Enter new password: ");
        String newPassword = scanner.nextLine();

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
