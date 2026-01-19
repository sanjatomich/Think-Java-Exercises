package chapter02;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeExercise {

    // public static int secondsOfTheDay (int hour, int minute) {
    //     if (hour >= 0 && hour <= 23 && minute >= 0 && minute <= 59) {
    //         return (hour * 3600) + (minute * 60);
    //     } else {
    //         System.out.println("Error: Invalid time range.");
    //     }
    //     return 0;
    // }

    public static String getTimeOfDay(int hour) {
        if (hour >= 5 && hour < 12) return "Morning";
        if (hour >= 12 && hour < 18) return "Afternoon";
        if (hour >= 18 & hour < 22) return "Evening";
        return "Night";
    }

    public static int secondsOfTheDay(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            return -1;
        }
        return (hour * 3600) + (minute * 60);
    }

    public static int secondsOfTheDayWithLibrary(int hour, int minute) {
        if (hour < 0 || hour > 23 || minute < 0 || minute > 59) {
            System.err.println("Invalid input: " + hour + ":" + minute);
            return -1;
        }
            return LocalTime.of(hour, minute).toSecondOfDay();
    }

    public static void main(String[] args) {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = time.format(formatter);

        int hour = 16;
        int minute = 53;

        int secondPassed = secondsOfTheDay(hour, minute);

        if (secondPassed == -1) {
            System.out.println("Error: " + hour + ":" + minute + " is not a valid time.");
        } else {
            int totalSecondsInDay = 86400;
            System.out.println("Testing time is: " + hour + ":" + minute);
            System.out.println("Seconds since midnight: " + secondPassed);
            System.out.println("Seconds remaining: " + (totalSecondsInDay - secondPassed));

            double percentage = (secondPassed * 100.0) / totalSecondsInDay;
            System.out.printf("Percentage of the day passed: %.2f%%\n", percentage);
        }

        System.out.println("Current time: " + formattedTime);
    }


    // int systemHour = time.getHour();
    // int systemMinute = time.getMinute();
    //
    // int secondPassed = secondsOfTheDay(systemHour, systemMinute);
    //
    // if (secondPassed != -1) {
    //     System.out.println("Time Category: " + getTimeOfDay(systemHour));
    //     System.out.printf("Percentage: %.2f%%\n", (secondPassed * 100.0) / 86400);
    // } else {
    //     System.err.println("System clock error!");
    // }
}




