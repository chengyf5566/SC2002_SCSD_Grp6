package hospitalManagementSystem;

import java.util.Scanner;

public class App {
    private static final String UserID = "admin";
    private static final String PASSWORD = "pw123";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            System.out.println("==Please enter Login Details==");

            System.out.print("Enter User ID: ");
            String inputUsername = scanner.nextLine();

            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine();

            if (authenticate(inputUsername, inputPassword)) {
                System.out.println("Login successful! Welcome, " + inputUsername + "!");
                System.out.println("\nWelcome to Hospital Management System");
                System.out.println("=====================================");
                System.out.println("1. Patient");
                System.out.println("2. Doctor");
                System.out.println("3. Pharmacist");
                System.out.println("4. Administrator");
                System.out.println("5. Logout");
                System.out.print("Select your role: ");
                int role = scanner.nextInt();
                scanner.nextLine();

                UserRoleMenu menu = null;
                switch (role) {
                    case 1: menu = new PatientMenu(); break;
                    case 2: menu = new DoctorMenu(); break;
                    case 3: menu = new PharmacistMenu(); break;
                    case 4: menu = new AdministratorMenu(); break;
                    case 5: System.out.println("Logging out..."); exit = true; break;
                    default: System.out.println("Invalid option. Try again.");
                }

                if (menu != null) {
                    menu.displayMenu(scanner);
                }
            } else {
                System.out.println("Authentication failed. Please try again.\n");
            }
        }

        scanner.close();
    }

    private static boolean authenticate(String username, String password) {
        return UserID.equals(username) && PASSWORD.equals(password);
    }
}