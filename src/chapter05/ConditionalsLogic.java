package chapter05;

import chapter03.ConversionTheory;

import java.util.Scanner;

public class ConditionalsLogic {

  /*
  switch(number){
    case 1:
    word ="one";
        break;
    case 2:
    word ="two";
        break;
    case 3:
    word ="three";
        break;
    default:
    word ="unknown";
        break;
}

switch (food) {
        case: "apple";
        case: "banana";
            System.out.println("Fruit!");
            break;
        case "broccoli";
        case "carrot";
            System.out.println("Vegetable!");
            break;
    }
   */

    public static boolean isSingleDigit(int x) {
        return x > -10 && x < 10;
    }

    public static boolean isSingleDigit2(int x) { // slow I/O operation; avoid inside utility functions
        if (Math.abs(x) < 10) {
            System.out.println(x + " is a single digit.");
            return true;
        } else {
            return false;
        }
    }

    // char
    public static boolean isSingleDigitChar(char ch) {
        return Character.isDigit(ch);
    }

    public static boolean isSingleLetter(char ch) {
        return Character.isLetter(ch);
    }

    // String; check if the input is not the digit 0
    public static boolean isOneCharacter(String input) {
        return input != null && input.length() == 1 && !input.equals("0");
    }

    public static void legalAgeEntry (Scanner in) {
        int age;
        do {
            age = ConversionTheory.getValidInt(in, "Enter your age: ");
            if (age < 18) {
                System.out.println("Sorry, you must be over 18!");
            }
        } while (age < 18);

        System.out.println("Welcome inside!");
    }


    public static void main(String[] args) {
        // System.out.println(isSingleDigit(22));

        try {
            Scanner in = new Scanner(System.in);
            int number;

            do {
                System.out.print("Enter a positive number: ");

                if (in.hasNextInt()) {
                    number = in.nextInt();
                    if (number <= 0) {
                        System.out.println("Error! The number must be positive!");
                    }

                } else {
                    System.out.println("Error: Invalid Input.");
                    in.next();
                    number = -1;
                }

            } while (number <= 0);
            System.out.print("You entered: " + number);
            in.close();


        } catch (Exception e) {
            System.err.print("An unexpected error occurred: " + e.getMessage());
        }

    }

}


