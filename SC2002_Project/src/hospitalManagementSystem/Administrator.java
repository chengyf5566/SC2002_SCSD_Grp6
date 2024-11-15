package hospitalManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator extends Staff {

    private List<Staff> staffList;
    private CsvReaderStaff csvReader;

    private List<Medication> medicationList;
    private CsvReaderInventory csvReaderInventory;

    private List<Appointment> appointmentList;
    private CsvReaderAppointment csvReaderAppointment;

    private List<Patient> patientList;
    private CsvReaderPatient csvReaderPatient;


    public Administrator(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);
        this.staffList = new ArrayList<>();
        this.medicationList = new ArrayList<>();
        this.appointmentList = new ArrayList<>();
        this.patientList = new ArrayList<>();
    }

    // Method to initialize staff list  from CSV
    public void initializeStaffFromCSV() {        
        this.csvReader = new CsvReaderStaff(); // Initialize the class-level csvReader with the given file path        
        csvReader.readAndInitializeStaff(); // Read and initialize staff from the CSV
        this.staffList = csvReader.getStaffList();  // Assign the read staff list
    }
    
    public void initializeInventoryFromCSV() {
        this.csvReaderInventory = new CsvReaderInventory();
        csvReaderInventory.readAndInitializeInventory();
        this.medicationList = csvReaderInventory.getMedicationList();
    }
    
    // Method to initialize appointment outcome from CSV
    public void readAndInitializeAppointments() {
        this.csvReaderAppointment = new CsvReaderAppointment();
        csvReaderAppointment.readAndInitializeAppointments();
        this.appointmentList = csvReaderAppointment.getAppointmentList();
    }
  
    // Method to initialize patient list from CSV
    public void readAndInitializePatient() {
        this.csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readAndInitializePatient();
        this.patientList = csvReaderPatient.getPatientList();
    }

    // Staff management menu
    public void manageHospitalStaff(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Hospital Staff:");
            System.out.println("1. Add Staff");
            System.out.println("2. Remove Staff");
            System.out.println("3. Update Staff");
            System.out.println("4. View Current Staff List");
            System.out.println("5. Back to Main Menu");
            System.out.print("Select Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addStaff(scanner);
                case 2 -> removeStaff(scanner);
                case 3 -> updateStaff(scanner);
                case 4 -> viewCurrentStaff();
                case 5 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addStaff(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine().trim();
        
        boolean userExists = staffList.stream().anyMatch(staff -> staff.getUserID().equalsIgnoreCase(userID));
        if (userExists) {
            System.out.println("A staff member with this User ID already exists. Try a different ID.");
            return;
        }
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role (Doctor/Pharmacist/Administrator): ");
        String role = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();

        Staff newStaff = switch (role.toLowerCase()) {
            case "doctor" -> new Doctor(userID, password, role, gender, name, age);
            case "pharmacist" -> new Pharmacist(userID, password, role, gender, name, age);
            case "administrator" -> new Administrator(userID, password, role, gender, name, age);
            default -> {
                System.out.println("Invalid role. Staff not added.");
                yield null;
            }
        };

        if (newStaff != null) {
            staffList.add(newStaff);
            System.out.println("Staff member added successfully.");
            // Save the updated staff list to the CSV file
            csvReader.writeStaffToCSV();
        }
    }

    private void removeStaff(Scanner scanner) {
        System.out.print("Enter Staff ID to remove: ");
        String staffID = scanner.nextLine().trim();
        
        boolean removed = staffList.removeIf(staff -> staff.getUserID().equalsIgnoreCase(staffID));
        if (removed) {
            // Save the updated staff list to the CSV file
            csvReader.writeStaffToCSV();
            System.out.println("Staff member removed and list updated in CSV.");
        } else {
            System.out.println("No staff member found with ID: " + staffID);
        }
    }

    private void updateStaff(Scanner scanner) {
        System.out.print("Enter Staff ID to update: ");
        String staffID = scanner.nextLine().trim();
        
        Staff staffToUpdate = staffList.stream()
                                      .filter(staff -> staff.getUserID().equalsIgnoreCase(staffID))
                                      .findFirst()
                                      .orElse(null);

        if (staffToUpdate == null) {
            System.out.println("No staff member found with ID: " + staffID);
            return;
        }

        System.out.print("Enter New Name: ");
        staffToUpdate.setName(scanner.nextLine());
        System.out.print("Enter New Gender: ");
        staffToUpdate.setGender(scanner.nextLine());
        System.out.print("Enter New Age: ");
        staffToUpdate.setAge(scanner.nextInt());
        scanner.nextLine();

        System.out.println("Staff member updated successfully.");
        // After updating, write the updated list to CSV
        csvReader.writeStaffToCSV();
    }

    private void viewCurrentStaff() {
        if (staffList.isEmpty()) {
            System.out.println("No staff available in the list.");
        } else {
            System.out.println("Current Staff List:");
            staffList.forEach(staff -> System.out.println("UserID: " + staff.getUserID() +
                    "\nName: " + staff.getName() +
                    "\nRole: " + staff.getRole() +
                    "\nGender: " + staff.getGender() +
                    "\nAge: " + staff.getAge() + "\n" + "=".repeat(30)));
        }
    }

    // Medication inventory management menu
    public void manageMedicationInventory(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Medication Inventory:");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Add Medication");
            System.out.println("3. Remove Medication");
            System.out.println("4. Update Stock Levels");
            System.out.println("5. Update Low Stock Alert Level");
            System.out.println("6. Approve Replenishment Request");
            System.out.println("7. Back to Main Menu");
            System.out.print("Select Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewMedicationInventory();
                case 2 -> addMedication(scanner);
                case 3 -> removeMedication(scanner);
                case 4 -> updateStockLevels(scanner);
                case 5 -> updateLowStockAlertLevel(scanner);
                case 6 -> approveReplenishmentRequest();
                case 7 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void viewMedicationInventory() {
        if (medicationList.isEmpty()) {
            System.out.println("No medication in inventory.");
        } else {
            System.out.println("\nMedication Inventory:");
            medicationList.forEach(System.out::println);
        }
    }

    private void addMedication(Scanner scanner) {
        System.out.print("Enter Medicine Name: ");
        String name = scanner.nextLine().trim();

        boolean exists = medicationList.stream().anyMatch(med -> med.getMedicineName().equalsIgnoreCase(name));
        if (exists) {
            System.out.println("Medication with this name already exists.");
            return;
        }

        System.out.print("Enter Initial Stock: ");
        int initialStock = scanner.nextInt();
        System.out.print("Enter Current Stock: ");
        int currentStock = scanner.nextInt();
        System.out.print("Enter Low Stock Alert Level: ");
        int lowStockAlert = scanner.nextInt();
        scanner.nextLine();

        Medication newMedication = new Medication(name, initialStock, currentStock, lowStockAlert, "No", 0);
        medicationList.add(newMedication);
        System.out.println("Medication added successfully.");
        csvReaderInventory.writeInventoryToCSV();
    }

    private void removeMedication(Scanner scanner) {
        System.out.print("Enter Medicine Name to remove: ");
        String name = scanner.nextLine().trim();

        boolean removed = medicationList.removeIf(med -> med.getMedicineName().equalsIgnoreCase(name));
        if (removed) {
            csvReaderInventory.writeInventoryToCSV();
            System.out.println("Medication removed successfully.");
        } else {
            System.out.println("No medication found with this name.");
        }
    }

    private void updateStockLevels(Scanner scanner) {
        System.out.print("Enter Medicine Name to update stock levels: ");
        String name = scanner.nextLine().trim();
    }
      
	// Method to display and manage medication inventory menu
	
	
	private void updateLowStockAlertLevel(Scanner scanner) {
	    System.out.print("Enter Medicine Name to update low stock alert level: ");
	    String name = scanner.nextLine().trim();
	
	    Medication medicationToUpdate = medicationList.stream()
	            .filter(med -> med.getMedicineName().equalsIgnoreCase(name))
	            .findFirst()
	            .orElse(null);
	
	    if (medicationToUpdate != null) {
	        System.out.print("Enter new low stock alert level: ");
	        int newAlertLevel = scanner.nextInt();
	        medicationToUpdate.setLowStockLevelAlert(newAlertLevel);
	        System.out.println("Low stock alert level updated successfully.");
	
	        // Write the updated inventory to CSV
	        csvReaderInventory.writeInventoryToCSV();
	    } else {
	        System.out.println("No medication found with this name.");
	    }
	}
	
	private void approveReplenishmentRequest() {
	    // Loop through all medications to find those with replenishRequest set to "Yes"
	    medicationList.forEach(medication -> {
	        if ("Yes".equalsIgnoreCase(medication.getReplenishRequest())) {
	            // Add replenish amount to current stock
	            int newCurrentStock = medication.getCurrentStock() + medication.getReplenishRequestAmount();
	            medication.setCurrentStock(newCurrentStock);

	            // Set initial stock to the new current stock
	            medication.setInitialStock(newCurrentStock);

	            // Set replenishRequest to "No" as it has been approved
	            medication.setReplenishRequest("No");

	            // Set replenishRequestAmount to 0 as it has been restocked
	            medication.setReplenishRequestAmount(0);

	            System.out.println("Replenishment approved and stock updated for: " + medication.getMedicineName());
	        }
	    });



	    // Write the updated inventory to CSV
	    csvReaderInventory.writeInventoryToCSV();
	}
      
    public void viewAppointments() {
        if (appointmentList.isEmpty()) {
            System.out.println("No appointments available.");
        } else {
            System.out.println("\nAppointments:");
            appointmentList.forEach(System.out::println);
        }
    }

    // Patient management menu
    public void manageHospitalPatient(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Hospital Patient:");
            System.out.println("1. Add Patient");
            System.out.println("2. Remove Patient");
            System.out.println("3. View Current Patient List");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addPatient(scanner);
                case 2 -> removePatient(scanner);
                case 3 -> viewAllCurrentPatient();
                case 4 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addPatient(Scanner scanner) {
        System.out.print("Enter new Patient ID: ");
        String patientID = scanner.nextLine().trim();
        if (patientID.isEmpty()) {
            System.out.println("Patient ID cannot be empty.");
            return;
        }

        boolean patientExists = patientList.stream()
                .anyMatch(patient -> patient.getPatientID().equalsIgnoreCase(patientID));

        if (patientExists) {
            System.out.println("A patient with this Patient ID already exists. Try a different ID.");
            return;
        }

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Patient's Date Of Birth (DD MM YYYY): ");
        String dateOfBirth = scanner.nextLine();
        System.out.print("Enter Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter Patient Blood Type: ");
        String bloodType = scanner.nextLine();
        System.out.print("Enter Patient Contact Number: ");
        String contactNum = scanner.nextLine();
        if (contactNum.trim().equalsIgnoreCase("null")) contactNum = null;
        System.out.print("Enter Patient Email: ");
        String email = scanner.nextLine();
        if (email.trim().equalsIgnoreCase("null")) email = null;
        System.out.print("Enter Patient's Assigned Doctor ID: ");
        String assignedDoctorID = scanner.nextLine();
        System.out.print("Enter Patient's Assigned Doctor: ");
        String assignedDoctorName = scanner.nextLine();

        Patient newPatient = new Patient(patientID, password, name, gender, dateOfBirth, bloodType, 
                                         contactNum, email, assignedDoctorID, assignedDoctorName, 
                                         new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        // Add the new patient to the list and update the CSV
        if (newPatient != null) {
            patientList.add(newPatient);  // Add new patient to the list
            System.out.println("New Patient added successfully.");

            // Save the updated patient list to the CSV file
            csvReaderPatient.writePatientDataToCSV();
        }
    }

    private void removePatient(Scanner scanner) {
        System.out.print("Enter Patient ID to remove: ");
        String patientID = scanner.nextLine().trim();

        boolean removed = patientList.removeIf(patient -> patient.getPatientID().equalsIgnoreCase(patientID));
        if (removed) {

            // Save the updated patient list to the CSV file
            csvReaderPatient.writePatientDataToCSV();
            System.out.println("Patient removed and list updated in CSV.");
        } else {
            System.out.println("No patient found with ID: " + patientID);
        }
    }

    private void viewAllCurrentPatient() {
        if (patientList.isEmpty()) {
            System.out.println("No patients available in the list.");
        } else {
            System.out.println("Current Patient List:");
            patientList.forEach(patient -> System.out.println("Patient ID: " + patient.getPatientID() +
                    "\nName: " + patient.getName() +
                    "\nGender: " + patient.getGender() +
                    "\nDate of Birth: " + patient.getDateOfBirth() +
                    "\nBlood Type: " + patient.getBloodType() +
                    "\nContact Number: " + (patient.getContactNum() != null ? patient.getContactNum() : "Not Provided") +
                    "\nEmail: " + (patient.getEmail() != null ? patient.getEmail() : "Not Provided") +
                    "\nAssigned Doctor ID: " + patient.getAssignedDoctorID() +
                    "\nAssigned Doctor Name: " + patient.getAssignedDoctorName() +
                    "\nPast Diagnoses: " + (patient.getPastDiagnoses().isEmpty() ? "None" : String.join(", ", patient.getPastDiagnoses())) +
                    "\nPrescribed Medicines: " + (patient.getPrescribedMedicines().isEmpty() ? "None" : String.join(", ", patient.getPrescribedMedicines())) +
                    "\nConsultation Notes: " + (patient.getConsultationNotes().isEmpty() ? "None" : String.join(", ", patient.getConsultationNotes())) +
                    "\nType of Service: " + (patient.getTypeOfService().isEmpty() ? "None" : String.join(", ", patient.getTypeOfService())) +
                    "\n" + "=".repeat(30)));
        }
    }
    
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
        csvReader.writeStaffToCSV();
        System.out.println("Password updated successfully.");
    }
    


}
