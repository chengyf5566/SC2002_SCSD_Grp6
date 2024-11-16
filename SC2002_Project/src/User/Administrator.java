package User;

import Appointment.Appointment;
import java.util.InputMismatchException;
import Inventory.Medication;
import Utility.*;
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
        this.csvReader = new CsvReaderStaff();   
        csvReader.readCsv(); 
        this.staffList = csvReader.getStaffList();  
    }
    
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
  
    // Method to initialize patient list from CSV
    public void readAndInitializePatient() {
        this.csvReaderPatient = new CsvReaderPatient();
        csvReaderPatient.readCsv();
        this.patientList = csvReaderPatient.getPatientList();
    }

//////////////////////////// Staff management menu////////////////////////////
    public void manageHospitalStaff(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Hospital Staff:");
            System.out.println("1. Add Staff");
            System.out.println("2. Remove Staff");
            System.out.println("3. Update Staff");
            System.out.println("4. View Current Staff List");
            System.out.println("5. Back to Main Menu");
            
            int choice = -1;
            boolean validChoice = false;


            while (!validChoice) {
                System.out.print("Select Option: ");
                try {
                    choice = scanner.nextInt();
                    scanner.nextLine(); 
                    if (choice < 1 || choice > 5) {
                        System.out.println("Invalid option. Please select a number between 1 and 5.");
                    } else {
                        validChoice = true; 
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    scanner.nextLine(); 
                }
            }

            // Process valid input
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


////////////////////////////add staff////////////////////////////
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
        password = PasswordHashing.hashPassword(password);


        String role = "";
        boolean validRole = false;
        while (!validRole) {
            System.out.print("Enter Role (Doctor/Pharmacist/Administrator): ");
            role = scanner.nextLine().trim().toLowerCase();
            if (role.equals("doctor") || role.equals("pharmacist") || role.equals("administrator")) {
                validRole = true;
            } else {
                System.out.println("Invalid role. Please enter one of the following: Doctor, Pharmacist, Administrator.");
            }
        }

   
        String gender = "";
        boolean validGender = false;
        while (!validGender) {
            System.out.print("Enter Gender (Male/Female): ");
            gender = scanner.nextLine().trim().toLowerCase();
            if (gender.equals("male") || gender.equals("female")) {
                validGender = true;
            } else {
                System.out.println("Invalid gender. Please enter Male, Female.");
            }
        }

 
        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter Name: ");
            name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        }

    
        int age = -1;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter Age: ");
            try {
                age = scanner.nextInt();
                scanner.nextLine(); 
                if (age <= 0) {
                    System.out.println("Age must be a positive number.");
                } else {
                    validAge = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input for age. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }

        Staff newStaff = null;
        switch (role) {
            case "doctor":
                newStaff = new Doctor(userID, password, role, gender, name, age);
                break;
            case "pharmacist":
                newStaff = new Pharmacist(userID, password, role, gender, name, age);
                break;
            case "administrator":
                newStaff = new Administrator(userID, password, role, gender, name, age);
                break;
            default:
                System.out.println("Invalid role. Staff not added.");
                break;
        }

        if (newStaff != null) {
            staffList.add(newStaff);
            System.out.println("Staff member added successfully.");
            csvReader.writeCSV();
        }
    }

////////////////////////////remove staff////////////////////////////
    private void removeStaff(Scanner scanner) {
        // Display the list of staff members with their ID and Name
        System.out.println("\n--- Current Staff List ---");
        for (int i = 0; i < staffList.size(); i++) {
            Staff staff = staffList.get(i);
            System.out.println((i + 1) + ". " + staff.getUserID() + " - " + staff.getName());
        }

        boolean validInput = false;
        int choice = -1;

        // Ask the user to choose a staff member to remove
        while (!validInput) {
            System.out.print("\nChoose which staff member to remove: ");
            
       
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 

                
                if (choice >= 1 && choice <= staffList.size()) {
                    validInput = true;
                } else {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + staffList.size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        // Get the staff member to remove based on the user's choice
        Staff selectedStaff = staffList.get(choice - 1);
        String staffID = selectedStaff.getUserID();

        // Remove the staff member
        boolean removed = staffList.removeIf(staff -> staff.getUserID().equalsIgnoreCase(staffID));
        if (removed) {    
            csvReader.writeCSV();
            System.out.println("Staff member with ID " + staffID + " removed and list updated in CSV.");
        } else {
            System.out.println("No staff member found with ID: " + staffID);
        }
    }

////////////////////////////update staff////////////////////////////
    private void updateStaff(Scanner scanner) {
        // Display the list of staff members with their ID and Name
        System.out.println("\n--- Current Staff List ---");
        for (int i = 0; i < staffList.size(); i++) {
            Staff staff = staffList.get(i);
            System.out.println((i + 1) + ". " + staff.getUserID() + " - " + staff.getName());
        }

        boolean validInput = false;
        int choice = -1;

        // Ask the user to choose a staff member to update
        while (!validInput) {
            System.out.print("\nChoose which staff member to update: ");
            
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 


                if (choice >= 1 && choice <= staffList.size()) {
                    validInput = true;
                } else {
                    System.out.println("Invalid selection. Please choose a number between 1 and " + staffList.size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }

        Staff staffToUpdate = staffList.get(choice - 1);

        // Ask for updated details
        System.out.print("Enter New Name: ");
        String newName = scanner.nextLine().trim();
        if (newName.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }
        staffToUpdate.setName(newName);

        System.out.print("Enter New Gender: ");
        String newGender = scanner.nextLine().trim();
        if (newGender.isEmpty()) {
            System.out.println("Gender cannot be empty.");
            return;
        }
        staffToUpdate.setGender(newGender);

        int newAge = -1;
        boolean validAge = false;
        while (!validAge) {
            System.out.print("Enter New Age: ");
            if (scanner.hasNextInt()) {
                newAge = scanner.nextInt();
                scanner.nextLine();
                if (newAge > 0) {
                    validAge = true;
                } else {
                    System.out.println("Age must be a positive integer.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid age.");
                scanner.nextLine(); 
            }
        }
        staffToUpdate.setAge(newAge);
        System.out.println("Staff member updated successfully.");     
        csvReader.writeCSV();
    }

////////////////////////////view current staff////////////////////////////
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

    
////////////////////////////Medication inventory management menu////////////////////////////
    public void manageMedicationInventory(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Medication Inventory:");
            System.out.println("1. View Medication Inventory");
            System.out.println("2. Add Medication");
            System.out.println("3. Remove Medication");
            System.out.println("4. Update Current Stock Amount");
            System.out.println("5. Update Low Stock Alert Level");
            System.out.println("6. Approve Replenishment Request");
            System.out.println("7. Back to Main Menu");
            
            int choice = -1;  
            boolean validInput = false;

     
            while (!validInput) {
                System.out.print("Select Option: ");
                
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();  

                    if (choice >= 1 && choice <= 7) {
                        validInput = true;  
                    } else {
                        System.out.println("Invalid option. Please select a number between 1 and 7.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number.");
                    scanner.nextLine();  
                }
            }

            // Process the valid choice
            switch (choice) {
                case 1 -> viewMedicationInventory();
                case 2 -> addMedication(scanner);
                case 3 -> removeMedication(scanner);
                case 4 -> updateCurrentStock(scanner);
                case 5 -> updateLowStockAlertLevel(scanner);
                case 6 -> approveReplenishmentRequest();
                case 7 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

////////////////////////////viewMedicationInventory////////////////////////////
    private void viewMedicationInventory() {
        if (medicationList.isEmpty()) {
            System.out.println("No medication in inventory.");
        } else {
            System.out.println("\n--- Medication Inventory ---");
            medicationList.forEach(medication -> {
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
            });
        }
    }


    
////////////////////////////add medication////////////////////////////    
    private void addMedication(Scanner scanner) {
        System.out.print("Enter Medicine Name: ");
        String name = scanner.nextLine().trim();
        
        // Validate that the name is not empty
        if (name.isEmpty()) {
            System.out.println("Medicine name cannot be empty. Try again.");
            return;
        }

        // Check if the medication already exists
        boolean exists = medicationList.stream().anyMatch(med -> med.getMedicineName().equalsIgnoreCase(name));
        if (exists) {
            System.out.println("Medication with this name already exists.");
            return;
        }


        int initialStock = -1;
        int currentStock = -1;
        int lowStockAlert = -1;


        while (initialStock < 0) {
            System.out.print("Enter Initial Stock (must be a positive integer): ");
            if (scanner.hasNextInt()) {
                initialStock = scanner.nextInt();
                if (initialStock < 0) {
                    System.out.println("Stock cannot be negative. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }
  
        while (currentStock < 0) {
            System.out.print("Enter Current Stock (must be a positive integer): ");
            if (scanner.hasNextInt()) {
                currentStock = scanner.nextInt();
                if (currentStock < 0) {
                    System.out.println("Stock cannot be negative. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }

        while (lowStockAlert < 0) {
            System.out.print("Enter Low Stock Alert Level (must be a positive integer): ");
            if (scanner.hasNextInt()) {
                lowStockAlert = scanner.nextInt();
                if (lowStockAlert < 0) {
                    System.out.println("Stock alert level cannot be negative. Try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            }
        }


        Medication newMedication = new Medication(name, initialStock, currentStock, lowStockAlert, "No", 0);
        medicationList.add(newMedication);
        System.out.println("Medication added successfully.");
        
        csvReaderInventory.writeCSV();
    }

////////////////////////////remove medication////////////////////////////
    private void removeMedication(Scanner scanner) {
        // Display the list of available medications
        if (medicationList.isEmpty()) {
            System.out.println("No medications available in the inventory.");
            return;
        }

        System.out.println("\nAvailable Medications:");
        for (int i = 0; i < medicationList.size(); i++) {
            System.out.println((i + 1) + ". " + medicationList.get(i).getMedicineName());
        }

        // Ask the user to choose a medication by number
        int choice = -1;
        while (choice < 1 || choice > medicationList.size()) {
            System.out.print("Enter the number of the medication to remove: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > medicationList.size()) {
                    System.out.println("Invalid choice. Please choose a valid medication number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }

        Medication selectedMedication = medicationList.get(choice - 1);
        medicationList.remove(selectedMedication);

        csvReaderInventory.writeCSV();
        System.out.println("Medication removed successfully: " + selectedMedication.getMedicineName());
    }


////////////////////////////update current stock////////////////////////////
    private void updateCurrentStock(Scanner scanner) {
        // Display the list of available medications
        if (medicationList.isEmpty()) {
            System.out.println("No medications available in the inventory.");
            return;
        }

        System.out.println("\nAvailable Medications:");
        for (int i = 0; i < medicationList.size(); i++) {
            System.out.println((i + 1) + ". " + medicationList.get(i).getMedicineName());
        }

        // Ask the user to choose a medication by number
        int choice = -1;
        while (choice < 1 || choice > medicationList.size()) {
            System.out.print("Enter the number of the medication to update stock levels: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 1 || choice > medicationList.size()) {
                    System.out.println("Invalid choice. Please choose a valid medication number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }

        // Get the selected medication
        Medication selectedMedication = medicationList.get(choice - 1);

        // Show the current and initial stock values before updating
        System.out.println("\nCurrent Stock: " + selectedMedication.getCurrentStock());
        System.out.println("Initial Stock: " + selectedMedication.getInitialStock());

        // Ask for the amount to update the stock levels
        int amountToUpdate = -1;
        while (amountToUpdate <= 0) {
            System.out.print("Enter the amount to update the stock (positive number): ");
            if (scanner.hasNextInt()) {
                amountToUpdate = scanner.nextInt();
                scanner.nextLine(); 
                if (amountToUpdate <= 0) {
                    System.out.println("Amount must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a positive number.");
                scanner.nextLine(); 
            }
        }

        selectedMedication.setCurrentStock(selectedMedication.getCurrentStock() + amountToUpdate);
        selectedMedication.setInitialStock(selectedMedication.getInitialStock() + amountToUpdate);

        csvReaderInventory.writeCSV();
        System.out.println("Stock updated successfully. New Current Stock: " + selectedMedication.getCurrentStock());
    }


////////////////////////////update low stock level////////////////////////////
    private void updateLowStockAlertLevel(Scanner scanner) {
        // Display the list of available medications
        if (medicationList.isEmpty()) {
            System.out.println("No medications available in the inventory.");
            return;
        }

        System.out.println("\nAvailable Medications:");
        for (int i = 0; i < medicationList.size(); i++) {
            System.out.println((i + 1) + ". " + medicationList.get(i).getMedicineName());
        }

        // Ask the user to choose a medication by number
        int choice = -1;
        while (choice < 1 || choice > medicationList.size()) {
            System.out.print("Enter the number of the medication to update low stock alert level: ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine(); 
                if (choice < 1 || choice > medicationList.size()) {
                    System.out.println("Invalid choice. Please choose a valid medication number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        // Get the selected medication
        Medication selectedMedication = medicationList.get(choice - 1);

        // Display the current low stock alert level before updating
        System.out.println("\nCurrent Low Stock Alert Level: " + selectedMedication.getLowStockLevelAlert());

        // Ask for the new low stock alert level
        int newAlertLevel = -1;
        while (newAlertLevel <= 0) {
            System.out.print("Enter new low stock alert level (positive number): ");
            if (scanner.hasNextInt()) {
                newAlertLevel = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
                if (newAlertLevel <= 0) {
                    System.out.println("The alert level must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid positive number.");
                scanner.nextLine(); 
            }
        }

        selectedMedication.setLowStockLevelAlert(newAlertLevel);

        csvReaderInventory.writeCSV();
        System.out.println("Low stock alert level updated successfully.");
    }


////////////////////////////approve replenish request////////////////////////////
	private void approveReplenishmentRequest() {
	    // Loop through all medications to find those with replenishRequest set to "Yes"
	    medicationList.forEach(medication -> {
	        if ("Yes".equalsIgnoreCase(medication.getReplenishRequest())) {
	            int newCurrentStock = medication.getCurrentStock() + medication.getReplenishRequestAmount();
	            medication.setCurrentStock(newCurrentStock);
	            medication.setInitialStock(newCurrentStock);
	            medication.setReplenishRequest("No");
	            medication.setReplenishRequestAmount(0);

	            System.out.println("Replenishment approved and stock updated for: " + medication.getMedicineName());
	        }
	    });
	    csvReaderInventory.writeCSV();
	}

	
////////////////////////////view appointments////////////////////////////
	public void viewAppointments() {
	    System.out.println("\nAppointments:");
	    for (Appointment appointment : csvReaderAppointment.getAppointmentList()) {
	        try {
	            String formattedAppointment = String.format(
	                "\nDoctor Name: '%s'\n" +
	                "Patient Name: '%s'\n" +
	                "Date of Appointment: '%s'\n" +
	                "Start Time: '%s'\n" +
	                "End Time: '%s'\n" +
	                "Status: '%s'\n" +
	                "Type of Service: '%s'\n" +
	                "Prescribed Medications: '%s'\n" +
	                "Medication Quantity: '%s'\n" +  // Changed from %d to %s
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
	                String.valueOf(appointment.getPrescribedMedicationsQuantity()), // Ensure it's a string
	                appointment.getPrescribedMedicationsStatus(),
	                appointment.getDiagnosis(),
	                appointment.getConsultationNotes()
	            );
	            System.out.println(formattedAppointment);
	        } catch (Exception e) {
	            System.err.println("Error formatting appointment: " + e.getMessage());
	        }
	    }
	}


////////////////////////////patient management menu////////////////////////////
    public void manageHospitalPatient(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\nManage Hospital Patient:");
            System.out.println("1. Add Patient");
            System.out.println("2. Remove Patient");
            System.out.println("3. View Current Patient List");
            System.out.println("4. Back to Main Menu");
            System.out.print("Select Option: ");

            int choice = -1;
            
            // Input handling for option choice
            while (choice < 1 || choice > 4) {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine(); 
                    if (choice < 1 || choice > 4) {
                        System.out.println("Invalid choice. Please select a valid option between 1 and 4.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter a number between 1 and 4.");
                    scanner.nextLine(); 
                }
            }

            switch (choice) {
                case 1 -> addPatient(scanner);
                case 2 -> removePatient(scanner);
                case 3 -> viewAllCurrentPatient();
                case 4 -> back = true;
                default -> System.out.println("Invalid option. Try again.");
            }
        }
    }

////////////////////////////add patient////////////////////////////
    private void addPatient(Scanner scanner) {    	
    	String patientID;

        while (true) {
            System.out.print("Enter new Patient ID: ");
            patientID = scanner.nextLine().trim();
            
            // Check if the Patient ID is empty
            if (patientID.isEmpty()) {
                System.out.println("Patient ID cannot be empty.");
                continue; 
            }

            // Check if the patient already exists
            boolean patientExists = false;
            for (Patient patient : patientList) {
                if (patient.getPatientID().equalsIgnoreCase(patientID)) {
                    patientExists = true;
                    break; 
                }
            }

            if (patientExists) {
                System.out.println("A patient with this Patient ID already exists. Try a different ID.");
                continue; 
            }

            break; 
        }

    	// Ask for password and hash it
        String password = "";
        while (password.isEmpty()) {
            System.out.print("Enter Password: ");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty. Please enter a valid password.");
            }
        }
        password = PasswordHashing.hashPassword(password);

        String name = "";
        while (name.isEmpty()) {
            System.out.print("Enter Patient Name: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                System.out.println("Patient Name cannot be empty. Please enter a valid name.");
            }
        }

        String dateOfBirth = "";
        while (dateOfBirth.isEmpty()) {
            System.out.print("Enter Patient's Date Of Birth (DD MM YYYY): ");
            dateOfBirth = scanner.nextLine();
            if (dateOfBirth.isEmpty()) {
                System.out.println("Date of Birth cannot be empty. Please enter a valid date.");
            }
        }

        String gender = "";
        while (gender.isEmpty()) {
            System.out.print("Enter Gender: ");
            gender = scanner.nextLine();
            if (gender.isEmpty()) {
                System.out.println("Gender cannot be empty. Please enter a valid gender.");
            }
        }

        String bloodType = "";
        while (bloodType.isEmpty()) {
            System.out.print("Enter Patient Blood Type: ");
            bloodType = scanner.nextLine();
            if (bloodType.isEmpty()) {
                System.out.println("Blood Type cannot be empty. Please enter a valid blood type.");
            }
        }

        String contactNum = "";
        while (contactNum.isEmpty()) {
            System.out.print("Enter Patient Contact Number: ");
            contactNum = scanner.nextLine();
            if (contactNum.isEmpty()) {
                System.out.println("Contact Number cannot be empty. Please enter a valid contact number.");
            }
        }

        String email = "";
        while (email.isEmpty()) {
            System.out.print("Enter Patient Email: ");
            email = scanner.nextLine();
            if (email.isEmpty()) {
                System.out.println("Email cannot be empty. Please enter a valid email address.");
            }
        }


        System.out.println("\nAvailable Doctors:");
        List<Staff> doctors = new ArrayList<>();
        for (Staff staffMember : staffList) {
            if (staffMember.getUserID().startsWith("D")) {  
                doctors.add(staffMember);
                System.out.println(doctors.size() + ". " + staffMember.getName());
            }
        }

        if (doctors.isEmpty()) {
            System.out.println("No doctors found in the system.");
            return;
        }

        int doctorChoice = -1;
        while (doctorChoice < 1 || doctorChoice > doctors.size()) {
            System.out.print("Select Doctor by number: ");
            if (scanner.hasNextInt()) {
                doctorChoice = scanner.nextInt();
                scanner.nextLine(); 
                if (doctorChoice < 1 || doctorChoice > doctors.size()) {
                    System.out.println("Invalid choice. Please select a valid doctor number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }


        Staff selectedDoctor = doctors.get(doctorChoice - 1);
        String assignedDoctorID = selectedDoctor.getUserID();
        String assignedDoctorName = selectedDoctor.getName();

        Patient newPatient = new Patient(patientID, password, name, gender, dateOfBirth, bloodType, 
                                         contactNum, email, assignedDoctorID, assignedDoctorName, 
                                         new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

        if (newPatient != null) {
            patientList.add(newPatient);  
            System.out.println("New Patient added successfully.");
            csvReaderPatient.writeCSV();
        }
    }

////////////////////////////remove patient////////////////////////////
    private void removePatient(Scanner scanner) {
        // Display the list of patients with their IDs
        if (patientList.isEmpty()) {
            System.out.println("No patients available to remove.");
            return;
        }

        System.out.println("Select a patient to remove:");
        for (int i = 0; i < patientList.size(); i++) {
            Patient patient = patientList.get(i);
            System.out.println((i + 1) + ". " + patient.getPatientID() + " - " + patient.getName());
        }

        // Prompt user to select a patient by number
        int patientChoice = -1;
        while (patientChoice < 1 || patientChoice > patientList.size()) {
            System.out.print("Enter the number corresponding to the patient to remove: ");
            

            if (scanner.hasNextInt()) {
                patientChoice = scanner.nextInt();
                scanner.nextLine(); 
                if (patientChoice < 1 || patientChoice > patientList.size()) {
                    System.out.println("Invalid choice. Please select a valid patient number.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }

        String selectedPatientID = patientList.get(patientChoice - 1).getPatientID();

        boolean removed = patientList.removeIf(patient -> patient.getPatientID().equalsIgnoreCase(selectedPatientID));
        if (removed) {
            csvReaderPatient.writeCSV();
            System.out.println("Patient with ID " + selectedPatientID + " removed and list updated in CSV.");
        } else {
            System.out.println("Failed to remove the patient with ID: " + selectedPatientID);
        }
    }

////////////////////////////view all current patient////////////////////////////
    private void viewAllCurrentPatient() {
        if (patientList.isEmpty()) {
            System.out.println("No patients available in the list.");
        } else {
            System.out.println("Current Patient List:");
            System.out.println("========================");
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
    
////////////////////////////change password////////////////////////////
    public void changePassword(Scanner scanner) {
        String newPassword = "";
        
        // Ensure password is not empty
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
