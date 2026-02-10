package chapter11;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Rational {
    private int numerator;
    private int denominator;

    public Rational() {
        this.numerator = 0;
        this.denominator = 1;
    }

    public Rational(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public void setDenominator(int denominator) {
        this.denominator = denominator;
    }

    public static void printRational(Rational r) {
        System.out.printf("Rational[numerator=%d, denominator=%d]\n", r.numerator, r.denominator);
    }

    @Override
    public String toString() {
        return String.format("%d/%d", this.numerator, this.denominator);
    }

    public static boolean isWholeNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidRational(int numerator, int denominator) {
        return denominator != 0;
    }

    // Modifier Method; it changes the internal state of the object
    public void negate() {
        this.numerator = this.numerator * -1;
    }

    // Modifier Method
    public void invert() {
        if (this.numerator == 0) {
            throw new ArithmeticException("Cannot invert: New denominator would be zero!");
          //  System.err.println("Error: Cannot invert a fraction with a 0 numerator");
          //  return;
        }
        // values cannot be swapped; they are copied
        int temp = this.numerator; // doesn't matter which value goes into the temporary variable
        this.numerator = this.denominator;
        this.denominator = temp; // this.denominator = this.numerator; without the temp doesn't work
    }

    // Pure Method
    public double toDouble() {
        return (double) this.numerator / this.denominator;
    }

    private int gdc(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b; // remainder of a divided by b
            a = temp;
        }
        return a;
    }

    public void reduce() {
        int common = gdc(this.numerator, this.denominator);
        this.numerator = this.numerator / common;
        this.denominator = this.denominator / common;
    }

    // Pure Method
    public Rational add(Rational that) { // a/b + c/d = (a*d)+(b*c) / b*d
       int newN = (this.numerator * that.denominator) + (that.numerator + this.denominator);
       int newD = this.denominator * that.denominator;

       Rational result = new Rational(newN, newD); // only the temporary object is modified before being sent back to the user

       result.reduce();

       return result;
    }


    public static void main(String[] args) {
        Rational rat = new Rational();
        rat.setNumerator(3);
        rat.setDenominator(4);
        System.out.println(rat);
        rat.negate();
        System.out.println("Negated: " + rat);
        rat.invert();
        System.out.println("Inverted: " + rat);
        double db = rat.toDouble();
        System.out.println(db);


        try (Scanner in = new Scanner(System.in)) {
            in.useDelimiter("[ /\\n\\r]+");
            Rational r = null;
            boolean isDone = false;


            while (!isDone) {
                try {
                    System.out.print("Enter rational (Numerator Denominator): ");
                    int n = in.nextInt();
                    int d = in.nextInt();

                    if (isValidRational(n, d)) {
                        r = new Rational(n, d);
                        isDone = true;
                        System.out.println("Success!");
                    } else {
                        System.err.println("Error: Denominator can't be zero!");
                    }
                } catch (InputMismatchException e) {
                    System.err.println("Please enter whole numbers only.");
                    in.next();
                }
            }
            System.out.println("You entered: " + r);
        }
    }
}