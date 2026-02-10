package chapter11;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Date {
    private int year;
    private String month;
    private int day;

    public Date() {
        this.year = 0;
        this.month = null;
        this.day = 0;
    }

    public Date(int year, String month, int day) {
        this.year = year;
        this.month = month.substring(0,1).toUpperCase() + month.substring(1).toLowerCase();
        this.day = day;
    }

    // Getters
    public int getYear() { return this.year; }
    public String getMonth() { return this.month; }
    public int getDay() { return this.day; }

    // Setters
    public void setYear(int year) { this.year = year; }
    public void setMonth(String month) { this.month = month; }
    public void setDay(int day) { this.day = day; }

    @Override
    public String toString() {
        return String.format("%s %d %d", this.month, this.day, this.year);
    }

    // Decomposition (separate method for validity)
    public static boolean isValidMonth(String month) {
        String[] validMonths = { "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December" };

        for (String m : validMonths) {
            if (m.equalsIgnoreCase(month)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidDay(String month, int day) {
        if (day < 1 || day > 31) return false;

        if (month.equalsIgnoreCase("February")) {
            return day <= 29; // Ignoring leap year
        }

        if (month.equalsIgnoreCase("April") || month.equalsIgnoreCase("June") ||
        month.equalsIgnoreCase("September") || month.equalsIgnoreCase("November")) {
            return day <= 30;
        }
        return true;
    }

    public static boolean isLeapYear(int year) {
        // divisible by 4 AND not 100   OR  divisible by 400
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // ENUM
    public static boolean isValidDay(int year, String monthName, int day) {
        try {
            // convert October string to Month.OCTOBER enum
            Month month = Month.valueOf(monthName.toUpperCase());

            // length() method handles 30/31 days
            int maxDays = month.length(isLeapYear(year)); // .lengths(true) returns 29; false returns 28

            return day >= 1 && day <= maxDays;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        // Empty constructors + Setters - when we don't have the data yet (waiting for user input/many  optional fields)
        Date birthday1 = new Date();

        birthday1.setDay(23);
        birthday1.setMonth("October");
        birthday1.setYear(1996);

        System.out.println(birthday1);

        /*
        // Empty constructor + Scanner
        Scanner in = new Scanner(System.in);
        System.out.println("Enter birthday (MM/DD/YYYY): ");
        String inputLine = in.nextLine();
        String[] parts = inputLine.split("[/-]");

        if (parts.length == 3) {
            int month = Integer.parseInt(parts[0].trim());
            int day = Integer.parseInt(parts[1].trim());
            int year = Integer.parseInt(parts[2].trim());

            Date date = new Date(year, month, day);
        } else {
            System.err.println("Error: Please use the format MM/DD/YYYY");
        }


        // DateTimeFormatter
        Scanner in = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        try {
            System.out.print("Enter birthday (M/D/YYYY): ");
            String input = in.nextLine();

            LocalDate date = LocalDate.parse(input, formatter);

            int month = date.getMonthValue();
            int day = date.getDayOfMonth();
            int year = date.getYear();

            System.out.println("Parsed: " + month + "/" + day + "/" + year);
        } catch (DateTimeParseException e) {
            System.err.println("Invalid format! Use Month/Day/Year.");
        }
         */

        // Using the delimiter "[ /-]"
        try (Scanner in = new Scanner(System.in)) {

            in.useDelimiter("[ /\\n\\r]+"); // Regex (Regular Expression)

            Date birthday = null;
            boolean isDone = false; // the flag to control the loop; a switch

            while (!isDone) { // While NOT done...
                try {
                    System.out.println("Enter birthday (Year Month Day): ");

                    int year = in.nextInt();
                    String month = in.next();
                    int day = in.nextInt();

                    if (!isValidMonth(month)) {
                        System.out.println("Invalid month name.");
                    } else if (!isValidDay(month, day)){
                        System.out.println("Error: " + month + " does not have " + day + " days.");
                    } else {
                        // both are valid, month and day
                        birthday = new Date(year, month, day);
                        isDone = true;
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Invalid format! Use numbers for year/day");
                    in.next(); // clear buffer from the scanner; trash can
                }
            }
            System.out.println("Your birthday: " + birthday);
        } // no need for in.close() because try() handles it (try-with-resources)


        // Constructors with parameters - best when we know all the data at the moment of creation
        // Date myBirthday = new Date(1996, "October", 23);
        // System.out.println(myBirthday);
    }
}
