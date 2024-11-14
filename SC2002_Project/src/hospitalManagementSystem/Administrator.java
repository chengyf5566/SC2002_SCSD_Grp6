package hospitalManagementSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Administrator extends Staff {

    private List<Staff> staffList; //staff list for staff management
    private CsvReaderStaff csvReader; //Staff csv file reader
    
    private List<Medication> medicationList; // Medication list for inventory management
    private CsvReaderInventory csvReaderInventory; // Inventory csv file reader
    
    private List<Appointment> appointmentList;
    private CsvReaderAppointment csvReaderAppointment;
    
    private List<Patient> patientList = new ArrayList<>();
	private CsvReaderPatient csvReaderPatient;
    
    public Administrator(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }


    // Method to initialize staff list  from CSV
    public void initializeStaffFromCSV() {        
        this.csvReader = new CsvReaderStaff(); // Initialize the class-level csvReader with the given file path        
        csvReader.readAndInitializeStaff(); // Read and initialize staff from the CSV
        this.staffList = csvReader.getStaffList();  // Assign the read staff list
    }
    
    // Method to initialize medication inventory from CSV
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
        
        // Debugging: Print out the appointments after initialization
        System.out.println("Appointments loaded: " + appointmentList.size());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment); // Assuming the toString method in Appointment prints useful details
        }
    }
    
    // Method to initialize patient list from CSV
    public void readAndInitializePatient() {
        this.csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readAndInitializePatient();
        this.patientList = csvReaderPatient.getPatientList();
    }
    
    
    
    //manageHospitalStaff 
    // Method to display and manage staff menu
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
                case 1:
                    addStaff(scanner);
                    break;
                case 2:
                    removeStaff(scanner);
                    break;
                case 3:
                    updateStaff(scanner);
                    break;
                case 4:
                    viewCurrentStaff();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }

    private void addStaff(Scanner scanner) {
        System.out.print("Enter User ID: ");
        String userID = scanner.nextLine().trim();  // Trim input to avoid whitespace issues
        
        // Check if staff with this User ID already exists
        boolean userExists = staffList.stream()
                                      .anyMatch(staff -> staff.getUserID().equalsIgnoreCase(userID));
        
        if (userExists) {
            System.out.println("A staff member with this User ID already exists. Please try again with a different ID.");
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
        scanner.nextLine();  // Consume the newline character

        // Create the new staff object based on role
        Staff newStaff = switch (role.toLowerCase()) {
            case "doctor" -> new Doctor(userID, password, role, gender, name, age);
            case "pharmacist" -> new Pharmacist(userID, password, role, gender, name, age);
            case "administrator" -> new Administrator(userID, password, role, gender, name, age);
            default -> {
                System.out.println("Invalid role. Staff not added.");
                yield null;
            }
        };

        // Add the new staff to the list and update the CSV
        if (newStaff != null) {
            staffList.add(newStaff);
            System.out.println("Staff member added successfully.");

            // Save the updated staff list to the CSV file
            csvReader.writeStaffToCSV();
        }
    }

    private void removeStaff(Scanner scanner) {
        System.out.print("Enter Staff ID to remove: ");
        String staffID = scanner.nextLine().trim();  // Trim input to avoid whitespace issues
        
        // Perform removal (case-insensitive)
        boolean removed = staffList.removeIf(staff -> staff.getUserID().equalsIgnoreCase(staffID));
        
        // If a staff member was removed, update the CSV
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
        String staffID = scanner.nextLine().trim();  // Trim input to avoid whitespace issues
        
        // Check if the staff member exists
        Staff staffToUpdate = staffList.stream()
                                      .filter(staff -> staff.getUserID().equalsIgnoreCase(staffID))
                                      .findFirst()
                                      .orElse(null);

        if (staffToUpdate == null) {
            System.out.println("No staff member found with ID: " + staffID);
            return;
        }

        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter New Gender: ");
        String gender = scanner.nextLine();
        System.out.print("Enter New Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Update the staff member details
        staffToUpdate.setName(name);
        staffToUpdate.setGender(gender);
        staffToUpdate.setAge(age);
        System.out.println("Staff member updated successfully.");

        // After updating, write the updated list to CSV
        csvReader.writeStaffToCSV();
    }

    private void viewCurrentStaff() {
    	
        if (staffList.isEmpty()) {
            System.out.println("No staff available in the list.");
        } else {
            System.out.println("Current Staff List:");
            System.out.println("=====================================");
            // Iterate through the staff list and print each staff's details
            for (Staff staff : staffList) {
                System.out.println("UserID: " + staff.getUserID());
                System.out.println("Name: " + staff.getName());
                System.out.println("Role: " + staff.getRole());
                System.out.println("Gender: " + staff.getGender());
                System.out.println("Age: " + staff.getAge());
                System.out.println("=====================================");
            }
        }
    }


/////////////////////////////////////////////////////////////////////////////////////////////    
//manageMedicationInventory 

	// Method to display and manage medication inventory menu
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
	            case 1:
	                viewMedicationInventory();
	                break;
	            case 2:
	                addMedication(scanner);
	                break;
	            case 3:
	                removeMedication(scanner);
	                break;
	            case 4:
	                updateStockLevels(scanner);
	                break;
	            case 5:
	                updateLowStockAlertLevel(scanner);
	                break;
	            case 6:
	                approveReplenishmentRequest();
	                break;
	            case 7:
	                back = true;
	                break;
	            default:
	                System.out.println("Invalid option. Try again.");
	        }
	    }
	}
	
	private void viewMedicationInventory() {
	    System.out.println("\nMedication Inventory:");
	    for (Medication medication : medicationList) {
	        System.out.println(medication); // Assuming the Medication class has a properly overridden toString method
	    }
	}
	
	private void addMedication(Scanner scanner) {
	    System.out.print("Enter Medicine Name: ");
	    String name = scanner.nextLine().trim();
	    System.out.print("Enter Initial Stock: ");
	    int initialStock = scanner.nextInt();
	    System.out.print("Enter Current Stock: ");
	    int currentStock = scanner.nextInt();
	    System.out.print("Enter Low Stock Alert Level: ");
	    int lowStockAlert = scanner.nextInt();
	    scanner.nextLine(); // Consume the newline character

	    // Default replenishRequest to "false"
	    String replenishRequest = "No";  // Default value is "false"
	    
	 // Default replenishRequest to "false"
	   int replenishRequestAmount = 0;  // Default value is 0

	    // Check if medication with this name already exists
	    boolean exists = medicationList.stream().anyMatch(med -> med.getMedicineName().equalsIgnoreCase(name));
	    if (exists) {
	        System.out.println("Medication with this name already exists. Please update the stock if needed.");
	        return;
	    }

	    // Create new medication with replenishRequest set to "false"
	    Medication newMedication = new Medication(name, initialStock, currentStock, lowStockAlert, replenishRequest, replenishRequestAmount);

	    // Add new medication to the list
	    medicationList.add(newMedication);
	    System.out.println("Medication added successfully.");

	    // Write the updated inventory to CSV
	    csvReaderInventory.writeInventoryToCSV();
	}
	
	private void removeMedication(Scanner scanner) {
	    System.out.print("Enter Medicine Name to remove: ");
	    String name = scanner.nextLine().trim();
	
	    boolean removed = medicationList.removeIf(med -> med.getMedicineName().equalsIgnoreCase(name));
	    if (removed) {
	        System.out.println("Medication removed successfully.");
	        csvReaderInventory.writeInventoryToCSV(); // Write the updated inventory to CSV
	    } else {
	        System.out.println("No medication found with this name.");
	    }
	}
	
	private void updateStockLevels(Scanner scanner) {
	    System.out.print("Enter Medicine Name to update stock levels: ");
	    String name = scanner.nextLine().trim();
	
	    Medication medicationToUpdate = medicationList.stream()
	            .filter(med -> med.getMedicineName().equalsIgnoreCase(name))
	            .findFirst()
	            .orElse(null);
	
	    if (medicationToUpdate != null) {
	        System.out.print("Enter new stock level: ");
	        int newStock = scanner.nextInt();
	        medicationToUpdate.setInitialStock(newStock);
	        System.out.println("Stock level updated successfully.");
	
	        // Write the updated inventory to CSV
	        csvReaderInventory.writeInventoryToCSV();
	    } else {
	        System.out.println("No medication found with this name.");
	    }
	}
	
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
	            
	            // Set replenishRequestAmount to 0" as it has been restock
	            medication.setReplenishRequestAmount(0);

	            System.out.println("Replenishment approved and stock updated for: " + medication.getMedicineName());
	        }
	    });

	    // Write the updated inventory to CSV
	    csvReaderInventory.writeInventoryToCSV();
	}

	
/////////////////////////////////////////////////////////////////////////////////////////////  
    // Method to view all appointments
    public void viewAppointments() {
        System.out.println("\nAppointments:");
        for (Appointment appointment : csvReaderAppointment.getAppointmentList()) {
            System.out.println(appointment);
        }
    }
    
    
/////////////////////////////////////////////////////////////////////////////////////////////  
//manageHospitalStaff 
    // Method to display and manage staff menu
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
                case 1:
                    addPatient(scanner);
                    break;
                case 2:
                    removePatient(scanner);
                    break;
                case 3:
                	viewAllCurrentPatient();
                    break;
                case 4:
                	back = true;
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
    
    private void addPatient(Scanner scanner) {
        System.out.print("Enter new Patient ID: ");
        String patientID = scanner.nextLine().trim();
        if (patientID.isEmpty()) {
            System.out.println("Patient ID cannot be empty");
            return;  // Prevent creation of a patient with no ID
        }

        // Check if a patient with this Patient ID already exists
        boolean patientExists = patientList.stream()
                                          .anyMatch(patient -> patient.getPatientID().equalsIgnoreCase(patientID));

        if (patientExists) {
            System.out.println("A patient with this Patient ID already exists. Please try again with a different ID.");
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
        if (contactNum.trim().equalsIgnoreCase("null")) contactNum = null;  // Set to null if user inputs 'null'
        System.out.print("Enter Patient Email: ");
        String email = scanner.nextLine();
        if (email.trim().equalsIgnoreCase("null")) email = null;  // Set to null if user inputs 'null'
        System.out.print("Enter Patient's Assigned Doctor ID: ");
        String assignedDoctorID = scanner.nextLine();
        System.out.print("Enter Patient's Assigned Doctor: ");
        String assignedDoctorName = scanner.nextLine();

        // Initialize the lists as empty, ready to be populated later
        List<String> pastDiagnoses = new ArrayList<>();
        List<String> prescribedMedicines = new ArrayList<>();
        List<String> consultationNotes = new ArrayList<>();
        List<String> typeOfService = new ArrayList<>();

        // Create a new Patient object
        Patient newPatient = new Patient(patientID, password, name, gender, dateOfBirth, bloodType, 
                                         contactNum, email, assignedDoctorID, assignedDoctorName, 
                                         pastDiagnoses, prescribedMedicines, consultationNotes, typeOfService);

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
        String patientID = scanner.nextLine().trim();  // Trim input to avoid whitespace issues

        // Perform removal (case-insensitive)
        boolean removed = patientList.removeIf(patient -> patient.getPatientID().equalsIgnoreCase(patientID));

        // If a patient was removed, update the CSV
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
            // Iterate through the patient list and print each patient's details
            for (Patient patient : patientList) {
                System.out.println("Patient ID: " + patient.getPatientID());
                System.out.println("Name: " + patient.getName());
                System.out.println("Gender: " + patient.getGender());
                System.out.println("Date of Birth: " + patient.getDateOfBirth());
                System.out.println("Blood Type: " + patient.getBloodType());
                System.out.println("Contact Number: " + (patient.getContactNum() != null ? patient.getContactNum() : "Not Provided"));
                System.out.println("Email: " + (patient.getEmail() != null ? patient.getEmail() : "Not Provided"));
                System.out.println("Assigned Doctor ID: " + patient.getAssignedDoctorID());
                System.out.println("Assigned Doctor Name: " + patient.getAssignedDoctorName());
                
                // Print lists with null or empty checks
                System.out.println("Past Diagnoses: " + (patient.getPastDiagnoses().isEmpty() ? "None" : String.join(", ", patient.getPastDiagnoses())));
                System.out.println("Prescribed Medicines: " + (patient.getPrescribedMedicines().isEmpty() ? "None" : String.join(", ", patient.getPrescribedMedicines())));
                System.out.println("Consultation Notes: " + (patient.getConsultationNotes().isEmpty() ? "None" : String.join(", ", patient.getConsultationNotes())));
                System.out.println("Type of Service: " + (patient.getTypeOfService().isEmpty() ? "None" : String.join(", ", patient.getTypeOfService())));
                
                System.out.println("=====================================");
            }
        }
    }


    
    


}

