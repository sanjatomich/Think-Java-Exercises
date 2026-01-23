package chapter05;

import chapter03.ConversionTheory;
import java.time.temporal.ValueRange;
import java.util.Random;
import java.util.Scanner;

public class ConditionalsLogicExercise {

    public static void checkNumberSign(int x) {
        if (x > 0 && x < 10) {
            System.out.println("Positive single digit number.");
        } else {
            System.out.println("Negative single digit number.");
        }
    }

    public static void checkRange(int number) {
        ValueRange range = ValueRange.of(0,101);
        if(range.isValidIntValue(number)) {
            System.out.println(number + " is in the range");
        }
    }

    // 5.4
    public static void guessMyNumberModified (Scanner in) {
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1; // 0 <= value < bound

        System.out.println("I'm thinking of a number between 1 and 100 (including both). Can you guess what it is?");
        int counter = 0;

        do {
            int number = ConversionTheory.getValidInt(in, "Take a guess: ");
            counter++;

            if (number == randomNumber) {
                System.out.println("You guessed it right! 🏆");
                return; // exit early
            } else if (number > randomNumber) {
                System.out.println("Too high!");
            } else {
                    System.out.println("Too low!");
                }
            if (counter < 3) {
                System.out.println("Try again!");
            }
        } while (counter < 3);

        System.out.println("Game over! The number I was thinking of is: " + randomNumber);

    }

    //5.3
    public static void fermat(int a, int b, int c, int n) {

        if ((Math.pow(a, n) + Math.pow(b, n) == Math.pow(c, n)) && n > 2) {
            System.out.println("Holy smokes, Fermat was wrong!");
        }
        else {
            System.out.println("No, that doesn't work.");
        }
    }

    // 5.4
    boolean yes = true;
    boolean no = false;
    int loVal = -999;
    int hiVal = 999;
    double grade = 87.5;
    double amount = 50.0;
    String hello = "world";

   /* 5.4 Table
    Expression                              Result
    yes == no || grade > amount             false || true -> true
    amount == 40.0 || 50.0                  false -> error; incompatible types: int cannot be converted to boolean
    hiVal != loVal || loVal < 0             true || true -> true
    True || hello.length() > 0              true || true -> true
    hello.isEmpty() & & yes                 false && true -> false
    grade <= 100 & & !false                 true && true -> true
    !yes || no                              false || false -> false
    grade > 75 > amount                     true > 50.0 -> error; can't compare a boolean to a number
    amount <= hiVal & & amount >= loVal     true && true -> true
    no & & !no || yes & & !yes              false || false -> false

    Correct: "hello".isEmpty() && "yes".isEmpty()
    Incorrect: "hello".isEmpty() && "yes"
    */

    // 5.5
    public static boolean isHoopy(int x) {
        boolean hoopyFlag;
        if (x % 2 == 0) {
            hoopyFlag = true;
        } else {
            hoopyFlag = false;
        }
        return hoopyFlag;
    }
    public static boolean isFrabjuous(int x) {
        boolean frabjuousFlag;
        if (x > 0) {
            frabjuousFlag = true;
        } else {
            frabjuousFlag = false;
        }
        return frabjuousFlag;
    }

    /*
    main: flag1 = isHoppy(202) RETURN to main 2+, goto isHoppy(202)
    isHoppy(x = 202), hoopyFlag = true
    main: flag1 = true; flag2 = isFrabjuous(202) RETURN main 3+ goto isFrabjuous(202)
    is Frabjuous(x = 202), frabjuousFlag = true;
    main: flag2 = truel, sout(flag1 = true) -> "true", sout(flag2 = true) -> "true", "ping", "pong"
    */

    // 5.6
    public static void quadratic (int a, int b, int c) {
        if (a == 0) {
            System.out.println("A cannot be 0!");
            return;
        }

        double d = (double) (b * b) - (4 * a * c);

        if (d < 0) {
            System.out.println("No real solutions!");
        } else {
            double root1 = (-b + Math.sqrt(d)) / (2.0 * a); // not integer division!
            double root2 = (-b - Math.sqrt(d)) / (2.0 * a);

            if (d > 0) {
                System.out.printf("There are two roots: %.3f and %.3f\n", root1, root2);
            } else { // d = 0
                System.out.printf("There is only one root: %3f\n", root1);
            }
        }
    }

    // if the method signature changes to public static boolean -> return an array

    // 5.7

    public static void triangle(int a, int b, int c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            System.out.println("Error! The lengths can't be negative or zero.");
            return;
        }

        // A triangle is valid ONLY is the sum of ANY two sides is greater than the third; all three conditions must be true
        if((a + b > c) && (a + c > b) && (b + c > a)) {
            System.out.println("You can form a triangle.");
        }
        else {
            System.out.println("You cannot form a triangle!");
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        guessMyNumberModified(in);

        boolean flag1 = isHoopy(202);
        boolean flag2 = isFrabjuous(202);
        System.out.println(flag1);
        System.out.println(flag2);
        if (flag1 && flag2) {
            System.out.println("ping!");
        }
        if (flag1 || flag2) {
            System.out.println("pong!");
        }
    }
}
