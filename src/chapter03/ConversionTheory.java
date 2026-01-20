package chapter03;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ConversionTheory {

    public static int getValidInt(Scanner in, String prompt) {
        System.out.print(prompt);

        while (!in.hasNextInt()) {
            String wrongInput = in.next();
            System.out.println("Error: '" + wrongInput + "' is not a number.");
            System.out.print(prompt);
        }
        return in.nextInt();
    }

    public static void convertInchesToCm(Scanner in) {
        final double CM_PER_INCH = 2.54;

        int totalInches = getValidInt(in, "Inch: ");

        double cm = totalInches * CM_PER_INCH;

        System.out.printf("%d in = %.2f cm\n", totalInches, cm);
    }

    public static void convertInchesToFeet(Scanner in){
        final int IN_PER_FOOT = 12;

        int totalInches = getValidInt(in, "Inch: ");

        int feet = totalInches / IN_PER_FOOT;
        int remainder = totalInches % IN_PER_FOOT;

        System.out.printf("%d inch = %d ft, %d in\n", totalInches, feet, remainder);

    }

    public static int getValidInt2(Scanner in, String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return in.nextInt();
            } catch (InputMismatchException e) {
                String junk = in.next();
                System.out.println("Error: '" + junk + "' is not a valid number");
            }
        }
    }

    public static void processInput(Scanner inputScanner) {
        System.out.print("Enter a number: ");
        int value = inputScanner.nextInt();
        System.out.println("Input value: " + value);
    }

    public static void printUserProfile(Scanner in) {
        System.out.print("Age: ");
        int age = in.nextInt();
        in.nextLine();

        System.out.print("Name: ");
        String name = in.nextLine();

        System.out.printf("Hello, %s! Your age is: %d\n", name, age);
    }

    public static void studyFormatting (int inch, int cm, String style){
        // using a Formatter; it's too 'clunky' for printing to the screen; stick to shortcuts
        // try (Formatter formatter = new Formatter()) {
        //   formatter.format("Inches: %d, cm %d", inch, cm);
        //   System.out.println(formatter);
        // }

        if(style.equals("printf")) {
            System.out.printf("Direct path: %d in, %d cm\n", inch, cm);
        } else if (style.equals("string")){
            String formatted = String.format("Saved String: %d in, %d cm", inch, cm);
            System.out.println(formatted);
        }
    }

    public static void main(String[] args) {

        try (Scanner in = new Scanner(System.in)) {

            studyFormatting(2, 4, "printf");

            convertInchesToFeet(in);
            in.nextLine(); // !clean the buffer after nextInt() inside the method

            convertInchesToCm(in);
            in.nextLine();

            printUserProfile(in);

        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }

    }
}