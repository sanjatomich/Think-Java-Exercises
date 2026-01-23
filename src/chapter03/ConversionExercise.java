package chapter03;
import java.time.Duration;
import java.util.Scanner;
import java.util.Random;

public class ConversionExercise {

    public static double getValidDouble(Scanner in, String prompt) {
        System.out.print(prompt);
        while (!in.hasNextDouble()) {
            String wrongInput = in.next(); // "eat" bad Input
            System.out.println("Error! '" + wrongInput + "' is not a valid decimal.");
            System.out.print(prompt);
        }
        return in.nextDouble();
    }


    public static void convertToFahrenheit (Scanner in) {
        double temperatureInC = getValidDouble(in, "Enter a temperature in Celsius: ");
        double temperatureInF = (temperatureInC * 9.0 / 5.0) + 32;

        System.out.printf("%.1f C = %.1f F", temperatureInC, temperatureInF);
    }

    public static void convertSeconds (Scanner in) {
        int totalSeconds = ConversionTheory.getValidInt(in, "Enter a number: ");

        Duration duration = Duration.ofSeconds(totalSeconds);
        long totalMinutes = duration.toMinutes();
        long hours = duration.toHours();

        long minutesPart = totalMinutes % 60;
        long secondsPart = totalSeconds % 60;

        System.out.println("Total seconds: " + totalSeconds);
        System.out.println("Formatted time: " + hours + " hours, " + minutesPart + " minutes, " + secondsPart + " seconds.");
    }

    public static void guessMyNumber (Scanner in){
        System.out.println("I'm thinking of a number between 1 and 100 (including both). Can you guess what it is?");
        int number = ConversionTheory.getValidInt(in, "Type a number: ");

        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1; // 0 <= value < bound

        int offset = Math.abs(number - randomNumber);

        System.out.println("Your guess is: " + number);
        System.out.printf("The number I was thinking of is: %d\nYou were off by: %d\n", randomNumber, offset);
    }

    public static void main(String[] args) {
    try (Scanner in = new Scanner(System.in)){

        guessMyNumber(in);
        in.nextLine();

        convertSeconds(in);
        in.nextLine();

        convertToFahrenheit(in);


    } catch (Exception e){
        System.err.print("An unexpected error occurred: " + e.getMessage());
    }

    }
}