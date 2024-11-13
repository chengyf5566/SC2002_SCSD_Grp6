package hospitalManagementSystem;

import java.util.List;
import java.util.Scanner;

public class Administrator extends Staff {

    private List<Staff> staffList; //staff list for staff management
    private CsvReaderStaff csvReader; //Staff csv file reader
    
    private List<Medication> medicationList; // Medication list for inventory management
    private CsvReaderInventory csvReaderInventory; // Inventory csv file reader
    
    private List<Appointment> appointmentList;
    private CsvReaderAppointment csvReaderAppointment;
    
    
    // Define the file paths for CSV files
    private String staffFilePath = "Staff_List.csv";
    private String inventoryFilePath = "Medicine_List.csv";
    private String filePath_Appointment = "Appointment_Outcome.csv"; 
    
    public Administrator(String userID, String password, String role, String gender, String name, int age) {
        super(userID, password, role, gender, name, age);  // Pass data to Staff constructor
    }


    // Method to initialize staff from CSV
    public void initializeStaffFromCSV(String filePath) {
        // Initialize the class-level csvReader with the given file path
        this.csvReader = new CsvReaderStaff(filePath);
        
        // Read and initialize staff from the CSV
        csvReader.readAndInitializeStaff(filePath);
       
        // Assign the read staff list
        this.staffList = csvReader.getStaffList();  // Assign the read staff list
        
    }
    
    // Method to initialize medication inventory from CSV
    public void initializeInventoryFromCSV(String inventoryFilePath) {
        this.csvReaderInventory = new CsvReaderInventory(inventoryFilePath);
        csvReaderInventory.readAndInitializeInventory(inventoryFilePath);
        this.medicationList = csvReaderInventory.getMedicationList();
    }
    
 // Method to initialize appointment outcome from CSV
    public void readAndInitializeAppointments(String filePath_Appointment) {
        this.csvReaderAppointment = new CsvReaderAppointment(filePath_Appointment);
        csvReaderAppointment.readAndInitializeAppointments();
        this.appointmentList = csvReaderAppointment.getAppointmentList();
        
        // Debugging: Print out the appointments after initialization
        System.out.println("Appointments loaded: " + appointmentList.size());
        for (Appointment appointment : appointmentList) {
            System.out.println(appointment); // Assuming the toString method in Appointment prints useful details
        }
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
            csvReader.writeStaffToCSV(staffFilePath);
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
            csvReader.writeStaffToCSV(staffFilePath);
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
        csvReader.writeStaffToCSV(staffFilePath);
    }

    // Method to display the current CSV file content (staff list)
    private void viewCurrentStaff() {
        System.out.println("UserID/Password/Name/Role/Gender/Age");
        for (Staff staff : staffList) {
            System.out.println(staff);  // This will use the overridden toString method
        }
    }

    
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
	    csvReaderInventory.writeInventoryToCSV(inventoryFilePath);
	}
	
	private void removeMedication(Scanner scanner) {
	    System.out.print("Enter Medicine Name to remove: ");
	    String name = scanner.nextLine().trim();
	
	    boolean removed = medicationList.removeIf(med -> med.getMedicineName().equalsIgnoreCase(name));
	    if (removed) {
	        System.out.println("Medication removed successfully.");
	        csvReaderInventory.writeInventoryToCSV(inventoryFilePath); // Write the updated inventory to CSV
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
	        csvReaderInventory.writeInventoryToCSV(inventoryFilePath);
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
	        csvReaderInventory.writeInventoryToCSV(inventoryFilePath);
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
	    csvReaderInventory.writeInventoryToCSV(inventoryFilePath);
	}


    // Method to view all appointments
    public void viewAppointments() {
        System.out.println("\nAppointments:");
        for (Appointment appointment : csvReaderAppointment.getAppointmentList()) {
            System.out.println(appointment);
        }
    }

}

