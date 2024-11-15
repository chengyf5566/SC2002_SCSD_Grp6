package Utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DateAndTimeTest {

    public void bookAppointment(Scanner scanner) {
        // Create SimpleDateFormat for input and output formatting
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy HHmm");

        // Prompt patient for appointment date (DD MM YYYY)
        System.out.print("Enter appointment date (DD MM YYYY): ");
        String dateStr = scanner.nextLine();

        // Prompt patient for appointment time (HHmm)
        System.out.print("Enter appointment time (HHmm): ");
        String timeStr = scanner.nextLine();

        // Combine the date and time strings into one string (for parsing)
        String startTimeStr = dateStr + " " + timeStr;

        try {
            // Parse the input date and time into a Date object
            Date startDate = dateFormat.parse(startTimeStr);

            // Add 30 minutes to the start time to calculate the end time
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            calendar.add(Calendar.MINUTE, 30);  // Add 30 minutes to start time

            // Get the new end time
            Date endDate = calendar.getTime();

            // Print the appointment details
            System.out.println("Appointment Date: " + dateStr);
            System.out.println("Appointment Start Time: " + timeStr);
            System.out.println("Appointment End Time: " + new SimpleDateFormat("HHmm").format(endDate));

        } catch (Exception e) {
            System.out.println("Invalid date or time format. Please enter the date in DD MM YYYY and time in HHmm format.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        DateAndTimeTest appointmentBooking = new DateAndTimeTest();
        appointmentBooking.bookAppointment(scanner);

        scanner.close();
    }
}
