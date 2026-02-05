package chapter09;

import java.util.ArrayList;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class ImmutableObjects {

    public static boolean isCapitalized(String s) {
        if (s == null || s.isEmpty()) return false;
        return Character.isUpperCase(s.charAt(0));
    }

    // BigInteger
    public static void bigIntMethods(BigInteger bigInt) {
        BigInteger num1 = new BigInteger("12345");
        BigInteger num2 = BigInteger.valueOf(100);

        BigInteger sum = num1.add(num2);
        BigInteger product = num1.multiply(num2);
        BigInteger quotient = num1.divide(num2);

        System.out.println("Number 1: " + num1);
        System.out.println("Number 2: " + num2);
        System.out.println("Sum: " + sum);
        System.out.println("Product: " + product);
        System.out.println("Quotient: " + quotient);
    }

    // Incremental Design
    public static void printRow0() {     // step 2: encapsulate the code; wrap
        for (int i = 1; i <= 6; i++) {
            System.out.printf("%4d", 2 * i); // step 1: use a literal value (2)
        }
        System.out.println();
    }

    public static void printRow1(int n) {
        for (int i = 1; i <= 6; i++) {
            System.out.printf("%4d", n * i);    // step 3: generalize n
        }
        System.out.println();
    }

    public static void printTable1() {   // step 6: the outer loop is encapsulated in a method, too
        for (int i = 1; i <= 6; i++) {  // step 4: write the loop
            printRow1(i);   //  step 5: then encapsulate the inner loop in the printRow method
        }
    }

    public static void printTable2(int rows) {
        for (int i = 1; i <= rows; i++) {   // step 7: generalize the number of rows
            printRow1(i);
        }
    }

    public static void printRow2(int n, int cols) {
        for (int i = 1; i <= cols; i++) {   // step 8: generalized col
            System.out.printf("%4d", n * i);
        }
        System.out.println();
    }

    public static void printTable3(int rows) {
        for (int i = 1; i <= rows; i++) {
            printRow2(i, rows); // step 9; two parameters
        }
    }

    public void printTable4(int rows) {
        for (int i = 1; i <= rows; i++) {
            printRow2(i, i);    // step 10: ab=ba; print half the table with i for both n and cols
        }
    }

    public static void charPrint(char c) { // if i pass c = 's', s (115), t (116), u (117), result: s t u
        for (int j = 0; j <= 2; j++) {
            char ch = c++; // c = c++ means assign the current value of c to ch, then increment
            // ch = ++c // result: t u v
            System.out.println(ch);
        }
    }

    // 9.2
    // Iterative method (using a for loop; time complexity: O(n); space complexity: O(1) - uses a single variable to store result)
    public static long factorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Number must be non-negative!");

        long factorial = 1; // after 20!, long can't hold it anymore; use java.math.BigInteger!
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }

    // Recursive method (time complexity: O(n); space complexity: O(n) - new stack frame added each time recursion happens)
    public static long recFactorial(int n) {
        if (n < 0) throw new IllegalArgumentException("Number must be non-negative!");

        // Base case (0! = 1, 1! = 1)
        if (n <= 1) {
            return 1;
        } else {
            return n * recFactorial(n - 1);
        }
    }

    // Big Integer Factorial (for over 20!)
    public static BigInteger factorialBig(int n) {
        BigInteger factorial = BigInteger.ONE; // .valueOf(1)
        for (int i = 2; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        return factorial;
    }

    public static void factTable() {    // after 21! Integer Overflow
        System.out.printf("%-10s %-35s%n", "Integer", "Factorial");
        System.out.printf("%-10s %-35s%n", "-------", "-------");

        for (int i = 0; i <= 30; i++) {
            System.out.printf("%-10d %-35d%n", i, factorialBig(i));
        }
    }

    // 9.3
    // Integer Exponentiation (O(n))
    public static double pow(double x, int n) {
        if (n == 0) return 1.0;

        double t = pow(x, n / 2);

        if (n % 2 == 0) {
            return t * t;
        } else {
            return t * t * x;
        }
    }

    // BigInteger Exponentiation (O(log n))
    public static BigInteger powBig(int x, int n) {
        if (n == 0) return BigInteger.ONE;

        BigInteger t = powBig(x, n / 2);

        if (n % 2 == 0) {
            return t.multiply(t);
        } else {
            return t.multiply(t).multiply(BigInteger.valueOf(x));
        }

    }

    // 9.4
    public static double myExp(double x, int n) {
        double e = 0.0;
        for (int i = 0; i < n; i++) {
            // e = e + (pow(x, i) / (double) factorial(i));
            double term = pow(x, i) / factorial(i); // we have to use floating-point math before the division; otherwise the result will be truncated (like 0.5 to 0)!
            e = e + term;
        }
        return e;
    }

    public static double myExp2(double x, int n) {
        double e = 1.0;
        double term = 1.0;

        for (int i = 1; i < n; i++) {
            // The current term is the previous term * (x / i)
            term = term * (x / i);
            e += term;
        }
        return e;
    }

    public static void check(double x) {
        int n = 5;
        System.out.printf("%.1f\t%.15f\t%.15f\n", x, myExp(x, n), Math.exp(x));
        // System.out.println(x + ".0\t" + myExp(x, n) + "\t" + Math.exp(x));
    }

    // 9.5 Encapsulation and generalization
    public static double[] powArray(double[] a) { // enhanced for loop is for reading, not for keeping track of the index i
        double[] squared = new double[a.length];

        for (int i = 0; i < a.length; i++) {
            squared[i] = a[i] * a[i];
        }
        return squared;
    }

    public static double[] powArrayGen(double[] a, int n) {
        double[] b = new double[a.length];

        for (int i = 0; i < a.length; i++) {
            b[i] = pow(b[i], n); // Math.pow(b[i], n);
        }
        return b;
    }

    public static int[] histogram(int[] scores) {
        int[] counts = new int[100];

        for (int i = 0; i < scores.length; i++) {
            int index = scores[i];
            counts[index]++;
        }
        // for-each loop
        // for (int score : scores) {
        //   counts[score]++;

        return counts;
    }

    public static int[] histogramGen(int[] scores, int numCounters) {
        int[] counts = new int[numCounters]; // numCounters - how many different possible scores can we track

        for (int score : scores) { // score - the index where the count is stored
            if (score >= 0 && score < numCounters) { // if someone passes 105 "buckets", but we have 100 counters, the program will crash with an ArrayIndexOutOfBoundsException
                counts[score]++;
            }
        }
        return counts;
    }

    // 9.6
    public static int parenthesesCount(String s, char open, char close) { // can count (), {}, [], <>
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == open) {
                count++;
            } else if (c == close) {
                count--;
            }
        }
        return count;
    }

    public static boolean isBalanced(String s) {
        int count = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
            }
            // Safety check: negative count means we have a ')' without a '('
            if  (count < 0) {
                return false;
            }
        }
        // Final check: if count is 0, it is balanced; if not, it's still open
        return count == 0;
    }


    public static void main(String[] args) {

        System.out.println(Arrays.toString(args)); // System.out.println(args) would print a memory address

        if(args.length == 0) {
            System.err.println("Usage: java ImmutableObjects <numbers>");
            return;

        }
        int max = Integer.MIN_VALUE;
        for (String arg : args) {// enhanced for loop; for-each
            int value = Integer.parseInt(arg);
            if (value > max) {
                max = value;
            }
        }


        // 1. Primitive Types
        // Logical - false
        boolean is = true;

        // Integral - 0
        byte b = 10; // 8 bits (1 byte); -128 to 127, inclusive
        short s = 1000; // 16 bits (2 bytes); -32,768 to 32, 767
        int i = 50000;  // 32 bits (4 bytes); -2^31 to 2^31-1
        long l = 1000000L;  // 64 bits (8 bytes);
        // Unsigned integer: char '\0000' (null character)
        char c = 'a'; // 16 bites Unicode; A = 65, a = 97

        // Floating-point - 0.0
        float f = 123.4f; // 32 bits (4 bytes)
        double d = 123.456; // 64 bits (8 bytes)

        // 2. Reference Types (null)
        // String
        String str = "Java";
        String str2 = new String("Java");

        // Arrays
        int[] arr1 = new int[5];
        int[] arr2 = {1, 2, 3};
        String[] arrS = {"Red"};

        // Class
        ImmutableObjects myClass = new ImmutableObjects(); // can't call static methods; just instance methods inherited from java.lang.Object
        BigInteger myInt1 = BigInteger.valueOf(10); // class/complex object

        // Wrapper Classes (Byte, Short, Integer, Long, Float, Double, Boolean, Character)
        Integer myInt2 = 10; // autoboxing

        // Interfaces (initialize the class that implements it)
        List<String> list = new ArrayList<>();

        // Enum (fixed set of constants)
        enum Level { LOW, MEDIUM, HIGH } // initialize
        Level myLevel = Level.MEDIUM; // use



        // check
        System.out.println("Positive values: ");
        double[] positives = {0.1, 1.0, 10.0, 100.0};
        for (double x : positives) {
            check(x);
        }

        System.out.println("Negative values: ");
        double[] negatives = {-0.1, -1.0, -10.0, -100.0};
        for (double x : negatives) {
            check(x);
        }
        // for small x (0.1, 1.0): high accuracy
        // for large x (100.0): low accuracy (x^n grows much faster than n!)
        // As x increases, the accuracy decreases because n (the number of terms) can't represent the sum
        // FOr getaive values, the accuracy is even words (alternating signs of terms) which leads to rounding errors in floating-point math


    }
}