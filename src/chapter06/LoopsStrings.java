package chapter06;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

public class LoopsStrings {

    // int age = getValidInput(in, "Enter age: ", Integer::parseInt);
    // double price = getValidInput(in, "Enter price: ", Double::parseDouble);

    public static <T> T getValidInput(Scanner in, String prompt, Function<String, T> parser) {
        while (true) { // never stop on your own; exit: when you hit the return inside the try block
            System.out.println(prompt);
            String input = in.next();
            try {
                return parser.apply(input); // exit here
            } catch (Exception e) {
                System.out.println("Invalid Input. Try again.");
            }
        }
    }


    public static String reverse(String s) {
        String r = "";
        for (int i = s.length() - 1; i >= 0; i--) {
            r += s.charAt(i);
        }
        return r;
    }

    // 6.1
    public static void loop(int n) {
        int i = n;

        while (i > 1) {
            System.out.println(i);
            if (i % 2 == 0) {
                i = i / 2;
            } else {
                i = i + 1;
            }
        }
    }


    /*
    6.1.1.
    i   |   n
    10      10
    5       10
    6       10
    3       10
    4       10
    2       10
    1       10 end

    6.1.2 10, 5, 6, 3, 4, 2,

    6.1.3 Yes, this loop terminates for any positive value of n, because it's getting smaller and smaller with i/2
     */

    //6.2 Convergent Loop pattern
    public static double squareRoot(double a) {
        double x0 = a / 2;
        double x1 = (x0 + a / x0) / 2.0; // Prime-the-Pump

        while (Math.abs(x1 - x0) >= 0.0001) {
            x0 = x1; // holds the "old" value; "handoff"
            x1 = (x0 + a / x0) / 2.0; // holds the "new value"; The order matters! If x1 = ... goes before x0 = x1, x1 and x0 would be teh same and the Math.abs would be 0 -> condition false -> EXIT the loop
        }

        return x1;

        /* do-while; no PRIMING

        double x0 = a / 2.0;
        double x1;

        do {
               x0 = x1;
                x1 = (x0 + a / x0) / 2.0;
        } while (Math.abs(x1 - x0) >= 0.0001;

            return x1;
         */

    }

    // 6.3
    public static double gauss(int x, int n) { // n = 6
        double sign = 1;
        double sum = 0; // double prevents the Integer Division
        double factorial = 1; // // double prevents the Integer Division
        double term = 1;
        double squared = (double) x * x;

        for (int i = 0; i <= n; i++) {

            if (i > 0) {
                factorial *= i;
            }

            double formula = sign * term / factorial;
            sum += formula;

            term *= squared;
            sign += -1;
        }

        return sum;
    }

    // The most optimized method
    public static double gauss(double x, int n) {
        if (n <= 0) return 0;

        double sum = 1.0;    // This is the first term (i = 0)
        double term = 1.0;   // Tracks the value of the individual term
        double xSquared = x * x;

        // Loop starts at 1 because we already initialized sum with the i=0 term
        for (int i = 1; i < n; i++) {
            // Apply the ratio: multiply by -x^2 and divide by i
            term = term * (-xSquared / i);
            sum += term;
        }

        return sum;
    }

    // 6.4
    public static boolean isAbecedarian(String word) {

        for (int i = 0; i < word.length() - 1; i++) { // ! if you don't use - 1, StringIndexOutOfBoundsException
            String letter = String.valueOf(word.charAt(i));
            String nextLetter = String.valueOf(word.charAt(i + 1));

            if (letter.compareToIgnoreCase(nextLetter) > 0) {
                return false;
            }
        }

        return true;
    }

    // 6.4 comparing chars as primitives (hold numerical value)
    public static boolean isAbecedarian2(String word) {
        for (int i = 0; i < word.length() - 1; i++) {
            // Direct char comparison is simpler than String.valueOf and compareTo
            char current = Character.toLowerCase(word.charAt(i));
            char next = Character.toLowerCase(word.charAt(i + 1));

            if (current > next) {
                return false;
            }
        }

        return true;
    }

    // 6.5 O(n^2)
    public static boolean isDoubloon(String word) {

        for (int i = 0; i < word.length(); i++) {
            char letter = Character.toLowerCase(word.charAt(i));
            int counter = 0;

            for (int y = 0; y < word.length(); y++) {
                char duplicate = Character.toLowerCase(word.charAt(y));

                if (letter == duplicate) {
                    counter++;
                }

                if (counter != 2) return false;

            }
        }

        return true;
    }


    // more efficient O(n)
    public static boolean isDoubloon2(String word) {
        String lower = word.toLowerCase(); // Convert once to save time

        for (int i = 0; i < lower.length(); i++) {
            char letter = lower.charAt(i);
            int counter = 0;

            for (int y = 0; y < lower.length(); y++) {
                if (letter == lower.charAt(y)) {
                    counter++;
                }
            }

            // If any letter count is not exactly 2, it's not a doubloon
            if (counter != 2) {
                return false;
            }
        }
        return true;
    }

    // 6.6
    public static boolean canSpell(String tiles, String word) {
        String tilesNew = tiles;

        for (int i = 0; i < word.length(); i++) {
            char c1 = word.charAt(i);

            int index = tilesNew.indexOf(c1);

            if (index == -1) {
                return false; // Letter not found in tiles, or we already used all of that specific letter

            } else {
                tilesNew = tilesNew.substring(0, index) + tilesNew.substring(index + 1);
            }
        }

        return true;
    }

    public static void main(String[] args) {
        // 6.1
        loop(10);

        String fruit = "Apple";
        for (int i = 0; i < fruit.length(); i++) {
            char letter = fruit.charAt(i);
            System.out.println(letter);
        }

        int length = fruit.length();
        // char last = fruit.charAt(length); // can't get charAt(length)!
        int index = fruit.indexOf('a', 2);
        String sub = fruit.substring(0, 2);

        // Stream
        List<String> names = List.of("Alice", "Bob", "Claire");
        names.stream().filter(n -> n.startsWith("S")).forEach(System.out::println);

        for (char c = 'A'; c <= 'Z'; c++) {
            System.out.print(c);
        }

        int x = 1;
        String name = "Sanja";
        int index1 = 0;

        while (x <= name.length()) {
            int y = 0;

            while (y < name.length()) {
                System.out.println(name.charAt(y++));

            }
            System.out.println();
            break;
        }

        while (index1 < name.length()) {
            System.out.println(name.charAt(index1));
            index1++;
        }

        for (int i = 0; i < name.length(); i++) {
            System.out.println(name.charAt(i));
        }

        for (char letter : name.toCharArray()) {
            System.out.println(letter);
        }

        /*
        int x = 1;
        while (x <= 10)    {
            int y = 1;

            while (y <= 10) {
                System.out.printf("%4d", x * y);
                y++;
            }
            System.out.println();
            x++;
        }
        */

        // Tables
        System.out.printf("%-5s | %-10s | %-5s%n", "ID", "Name", "Score");
        System.out.printf("------+------------+------%n");
        System.out.printf("%-5d | %-10s | %-5d%n", 1, "Alice", 85);
        System.out.printf("%-5d | %-10s | %-5d%n", 2, "Bob", 90);
        System.out.printf("%-5d | %-10s | %-5d%n", 3, "Charlie", 78);

        System.out.println();
        System.out.printf("%-5s | %-10s%n", "ID", "name");
        System.out.print("------+----------%n");
        System.out.printf("%-5d | %-10s%n", 1, "Alice");
        System.out.printf("%-5d | %-10s%n", 2, "Bob");

    /*
        for (int x = 1; x <= 10; x++) {
            for (int y = 1; y <= 10; y++) {
                System.out.printf("%3d", x * y);
            }
            System.out.println();
        }

    int n;
    for (n = 3; n > 0; n--) {
        System.out.println(n);
    }
    System.out.println("n is now: " + n);

    */

    }
}