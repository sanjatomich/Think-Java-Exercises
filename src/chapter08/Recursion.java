package chapter08;

import java.util.Arrays;

public class Recursion {

    public static void countdown(int n) {
        if (n == 0) {
            System.out.println("Blastoff!");
        } else {
            System.out.println(n);
            countdown(n - 1);
        }
    }

    public static void nLines(int n) {
        if (n > 0) {
            System.out.println();
            nLines(n - 1);
        }
    }

    public static int factorial(int n) {
        if (n == 0) return 1;

        int recurse = factorial(n - 1);
        int result = n * recurse;

        return result;
    }

    public static int factorial1(int n) {
        if (n == 0) return 1;

        return n * factorial(n - 1);
    }

    public static int fibonacci(int n) {
        if (n == 1 || n == 2) return 1;

        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void displayBinary(int value) {
        if (value > 0) {
            displayBinary(value / 2);
            System.out.println(value % 2);
        }
    }

    public static String noX(String s) {
        if (s.isEmpty()) return "";
        if (s.charAt(0) == 'x') {
            return noX(s.substring(1));
        } else {
            return s.charAt(0) + noX(s.substring(1));
        }
    }

    public static int array11(int[] array, int index) {     // if int index = 0; is inside the method -> index would reset to 0 and we'd be stuck in an infinite loop

        if (index >= array.length) return 0;

        if (array[index] == 11) {
            return 1 + array11(array, index + 1);
        } else {
            return array11(array, index + 1);
        }
    }

    // Additional examples
    // Palindrome
    private static boolean checkPalindrome(String s, int low, int high) {
        if (low >= high) return true;
        if (s.charAt(low) != s.charAt(high)) return false;
        return checkPalindrome(s, low + 1, high - 1); // otherwise: s.substring(1, s.length-1)
    }

    public static boolean isPalindrome(String s) {
        return checkPalindrome(s, 0, s.length() - 1);
    }

    // Max
    private static int maxHelper(int[] array, int index) { // pasing an int (index), not using Arrays.copyOfRange to create new sub-arrays
        if (index == array.length - 1) return array[index];

        int maxOfRest = maxHelper(array, index + 1); // shrinking the problem: dive all the way to the end, and find the winner on the way back up

        if (array[index] > maxOfRest) {
            return array[index];
        } else {
            return maxOfRest;
        }
    }

    public static int findMax(int[] array) {
        return maxHelper(array, 0);
    }

    // Assume the first is max - Accumulator
    private static int maxHelperAccumulator(int[] array, int index, int currentMax) {
        if (index == array.length) return currentMax;

        int newMax = Math.max(currentMax, array[index]);

        return maxHelperAccumulator(array, index + 1, newMax);
    }

    // Divide and Conquer
    private static int helper(int[] array, int low, int high) {
        if (low == high) return array[low]; // Base case: one element

        // 1. Divide
        int mid = (low + high) / 2;
        // 2. Conquer
        int leftMax = helper(array, low, mid);
        int rightMax = helper(array, mid + 1, high);
        // 3. Combine
        return Math.max(leftMax, rightMax);
    }

    public static int findMax2(int[] array) {
        if (array.length == 0) return -1;
        return helper(array, 0, array.length - 1);
    }

    private static int countCharHelper(String s, char target, int index) {
        if (index == s.length()) return 0;

        int found = (s.charAt(index) == target) ? 1 : 0;

        return found + countCharHelper(s, target, index + 1);
    }

    // Chapter08 Exercises

    // 8.1
    private static void recursiveLyrics(int n) {
        if (n == 0) {
            System.out.println("No bottles of beer on the wall,\n" +
                    "no bottles of beer,\n" +
                    "ya’ can’t take one down, ya’ can’t pass it around,\n" +
                    "’cause there are no more bottles of beer on the wall!");
        } else {
            System.out.println(n + " bottles of beer on the wall,\n" + n + " bottles of beer,\n" + "ya' take one down, ya' pass it around,");
            recursiveLyrics(n-1);

        }
    }

    // 8.2
    public static int oddSum(int n) {
        if (n == 1) return 1;
        return n + oddSum(n - 2);
    }

    // 8.3
    public static int prod(int m, int n) {
        if (m == n) {
            return n;
        } else {
            int recurse = prod(m, n - 1);
            int result = n * recurse;
            return result;
        }
    }
    // main : prod(1,4)
    // prod(m=1, n=4) - > recurse = prod(1, 3); res = 4*prod(1,3) RETURN    4 * 6 = 24  DONE
    // prod(1,3)        -> recurse = prod(1, 2); res = 3*prod(1,2) RETURN   3 * 2 = 6 DONE
    // prod(1,2)        -> recurse = prod (1, 1); res = 2*prod(1,1) RETURN 2 * 1 = 2 DONE
    // prod(1,1)        -> return 1; DONE
    // calculates the products of all integers from n to n inclusive

    public static int prod1(int m, int n) {
        if (m == n) {
            return n;
        } else {
            return n * prod(m, n - 1);
        }
    }

    public static void mystery1(int n) {
        if (n == 0) {
            return;
        }
        System.out.println(n);
        mystery1(n - 1);
    }
    // counts from n to 1

    public static int mystery2(int n) {
        if (n == 1) {
            return 1;
        }
        return n + mystery2(n - 1);
    }
    // calculates the sum of all positive integers from n to 1

    public static int mystery3(int n) {
        if (n < 10) {
            return 1; // one digit remains
        }
        return 1 + mystery3(n / 10);
    }
    // counts the number of digits in a positive integer

    public static void mysteryA(String s) {
        if (s.length() == 0) {
            return;
        }
        mysteryA(s.substring(1));
        System.out.print(s.charAt(0));
    }
    // reversing the word (bottom-up)

    public static int mysteryB(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n % 2 != 0) {
            return 1 + mysteryB(n - 1);
        } else {
            return mysteryB(n - 1);
        }
    }
    // count of off numbers 1-n

    public static void mysteryC(int n) {
        if (n <= 0) {
            return;
        }
        System.out.print(n % 2);
        mysteryC(n / 2);
    }
    // binary number

    public static int mysteryBoss(int a, int b) {
        if (b == 0) {
            return 0;
        }
        return a + mysteryBoss(a, b - 1);
    }
    // multiplication, adds a to itself

    // 8. 4
    public static int ack(int m, int n) {
        if (m < 0 || n < 0) return 1;

        if (m == 0) {
            return n + 1;
        } else if (n == 0) {
            return ack(m - 1, 1);
        } else {
            return ack(m - 1, ack(m, n - 1));
        }
    }

    // 8.5
    public static double power(double x, int n) {
        if (n == 0) return 1;

        if (n < 0) {
            return power(1 / x, -n); // x^-n is the same as (1/x)^n
        }

        return x * power(x, n - 1);
    }

    // 8.6
    public static int maxInRangeHelper(int[] array, int low, int high) { // O(n)
        if (high == low) return array[low]; // one element!

        int mid = (low + high) / 2;

        int leftMax = maxInRangeHelper(array, low, mid);
        int rightMax = maxInRangeHelper(array, mid + 1, high);

        return Math.max(leftMax, rightMax);

    }

    public static int maxInRange(int[] array, int low, int high) {
        if (array.length == 0) return -1;
        return maxInRangeHelper(array, low, high);
    }

    public static int sumArrayHelper(int[] array, int low, int high) { // bottom-up recursion
        if (low == high) return array[low];

        int mid = (low + high) / 2;

        int leftSum = sumArrayHelper(array, low, mid);
        int rightSum = sumArrayHelper(array, mid + 1, high);

        return leftSum + rightSum;
    }

    public static int sumArray(int[] array, int low, int high) {
        if (array.length == 0) return -1;
        return sumArrayHelper(array, low, high);
    }

    // 8.7
    public static char first(String s) {
        return s.charAt(0);
    }

    public static String rest(String s) {
        return s.substring(1);
    }

    public static String middle(String s) {
        return s.substring(1, s.length() - 1);
    }

    public static int length(String s) {
        return s.length();
    }

    public static void printString(String s) {
        if (s.isEmpty()) return;

        System.out.println(first(s));
        printString(rest(s));
    }

    public static void printBackward(String s) { // top-down
        if (s.isEmpty()) return;
        System.out.println(s.charAt(s.length() - 1));

        printBackward(s.substring(0, length(s) - 1));
    }

    public static void printBackward2(String s) { // bottom-up
        if (length(s) == 0) return;

        printBackward2(rest(s));

        System.out.println(first(s));
    }

    public static String reverseString(String s) {
        if (s.isEmpty()) return s;
        return reverseString(s.substring(1)) + s.charAt(0);
    }


        public static void main(String[] args) {

        // 8.2
        int n = 5;
        int sum = oddSum(n);
        System.out.println("Total sum of odd integers from 1 to " + n + " is: " + sum);

        // 8.3
        System.out.println(prod(1, 4));

        String s = "Sanja";
        char c = first(s);
        String test = middle(s);
        int length = length(s);
        System.out.println(length);

        printString(s);
        printBackward(s);
        System.out.println(Arrays.toString(args));

    }
}