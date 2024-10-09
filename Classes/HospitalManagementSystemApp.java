import java.util.Scanner;

public class HospitalManagementSystemApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("Welcome to Hospital Management System");
            System.out.println("1. Patient");
            System.out.println("2. Doctor");
            System.out.println("3. Pharmacist");
            System.out.println("4. Administrator");
            System.out.println("5. Logout");

            System.out.print("Select your role: ");
            int role = scanner.nextInt();
            scanner.nextLine();  // consume newline

            switch (role) {
                case 1:
                    patientMenu(scanner);
                    break;
                case 2:
                    doctorMenu(scanner);
                    break;
                case 3:
                    pharmacistMenu(scanner);
                    break;
                case 4:
                    adminMenu(scanner);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        scanner.close();
    }
}

    