import java.util.Collection;
import java.util.List;
import java.util.OptionalInt;


public class Arrays {

    // 7.1
    public static int banana(int[] a) {
        int kiwi = 1;
        int i = 0;
        while (i < a.length) {
            kiwi = kiwi * a[i];
            i++;
        }
        return kiwi;
    }
    // This method is aimed at calculating the variable kiwi which grows in every step, there are as many steps as the number of elements in the array. Multiplicative Accumulation. So we basically find a product as a result of multiplying all the elements.

    public static int grapefruit(int[] a, int grape) {
        for (int i = 0; i < a.length; i++) {
            if (a[i] == grape) {
                return i;
            }
        }
        return -1;
    }
    // We check whether there is an int value in the array of elements. If there is, we return it; if not, we return -1.

    public static int pineapple(int[] a, int apple) {
        int pear = 0;
        for (int pine : a) {
            if (pine == apple) {
                pear++;
            }
        }
        return pear;
    }
    // We are looking for and counting the elements in the array that equal to the int value. So we take each element from the array, compare it with the int value. If we found such elements, we increase the counter. The method will return the number of elements that satisfy the given criteria.

    // 7.2
    public static int[] make(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        return a;
    }

    public static void dub(int[] jub) {
        for (int i = 0; i < jub.length; i++) {
            jub[i] *= 2;
        }
    }

    public static int mus(int[] zoo) {
        int fus = 0;
        for (int i = 0; i < zoo.length; i++) {
            fus += zoo[i];
        }
        return fus;
    }

    // Stack: main -> int[] bob; make(n=5) RETURN .
    // make(n=5) -> n; int[] a; int i = 0; {1, 2, 3, 4, 5} .
    // main -> int[] bob[] = {1, 2, 3, 4, 5}; dub(int[] jub=bob) RETURN .
    // dub(bob) -> int[] jub = {2, 4, 6, 8, 10}; int i=0; . // both jub and bob arrays refer to the same object in memory!
    // main -> mus(bob) RETURN "30" .
    // mus(int[] zoo = bob); int fus = 0; int i = 0; fus<=0+2=2; fus<=2+4=6; fus<=6+6=12; fus<=12+8=20; fus=20=10=30 - 30 .
    // The output is 30.

    // 7.3.1 Using a loop
    public static int indexOfMax(int[] array) {
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
}

    /* 7.3.2 Using Collections.max() (array->list)
    Integer[] array = {1, 2, 3, 4};
    List<Integer> list = Arrays.asList(array);
    int max = Collection.max(list); */

    /* 7.3.3 Using Java 8 Streams; max()
    int array[] = {1, 2, 3};
    OptionalInt maxOpt = Arrays.stream(array).max();
    if (maxOpt.isPresent()) {
        int max = maxOpt.getAsInt();
    } */

    // 7.3.3.1 The One-Liner
    // int max = Arrays.stream(array).max().orElse(0); // works for positive numbers; for negative use Integer.MIN_VALUE


    /*
   List<Integer> nums = Arrays.asList(10, 45, 22, 8); // convert into Stream<Integer>

    int result = nums.stream()           // 1. Source
                 .filter(n -> n < 40)    // 2. Intermediate (remove 45)
                 .max(Integer::compare)  // 3. Terminal (find max of remaining); Integer::compare is a Method Reference
                 .orElse(0);             // Handle "Optional" if empty; The "Unwrapper", safety net

    System.out.println(result); // Result: 22
    */

    /* 7.3.4 Using Math.max() in a loop
    int array[] = {1, 2, 3};
    int max = array[0];
    for (int i = 0; i < array.length; i++) {
        max = Math.max(max, arr[i]);
    } */

    // 7.4 The Sieve of Eratosthenes
    public static boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n];

        for (int i = 0; i < n; i++) isPrime[i] = true; // assume all are prime except 0 and 1
        if (n > 0) isPrime[0] = false; // the condition; there must be at least one number in the array
        if (n > 1) isPrime[1] = false;

        for (int i = 2; i * i < n; i++) {
            if (isPrime[i]) { // only kill the multiples of the actual prime numbers
                for (int j = i * i; j < n; j += i) { // kill multiples of i; start at the first "new" non-prime; jump by i
                    isPrime[j] = false;
                }
            }
        }
        return isPrime;
    }

    // 7.5
    public static boolean areFactors(int n, int[] array) {
        // 2,   [2,4, 4]
        if (array == null || array.length == 0) return false;


        for (int i = 0; i < array.length; i++) {

            // if (array[i] == 0 || n % array[i] != 0) return false;
            if (array[i] == 0) return false;

            if (n % array[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // 7.6
    public static boolean arePrimeFactors(int n, int[] array) {
        if (array == null || array.length == 0) return false;
        long product = 1;

        for (int i = 0; i < array.length; i++) {
            int num = array[i];

            if (num <= 1) return false; // 0 and 1 are not prime
            if (num > 2 && num % 2 == 0) return false; // even numbers > 2 are not prime


            for (int j = 3; j * j <= num; j += 2) {
            if (num % j == 0) return false;

        }
            product *= num;
        }
        return product == n;
    }

    // 7.7
    public static int[] letterHist(String s) {
        int[] histogram = new int[26];
        String lower = s.toLowerCase();

        for (int i = 0; i < lower.length(); i++) {
            char c = lower.charAt(i);

            if (c >= 'a' && c <= 'z') {
                int index = c - 'a';
                histogram[index]++;
            }

            // for (char c : lower.toCharArray()) { // convert to an array of characters; doesn't work with strings
            //    if (c >= 'a' && c <= 'z') {
            //      int index = c - 'a';
            //      histogram[index]++;
            //     }
            // }

            }
      return histogram;
    }

    // 7.8.1
    public static boolean isAnagram(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        int[] hist1 = letterHist(s1);
        int[] hist2 = letterHist(s2);

        // Compare the two results
        for (int i = 0; i < 26; i++) {
            if (hist1[i] != hist2[i]) return false;
        }
        return true;
    }

    // 7.8.2
    public static boolean isAnagram1(String s1, String s2) {
        if (s1.length() != s2.length()) return false;

        int[] counts = new int[26];
        String lower1 = s1.toLowerCase();
        String lower2 = s2.toLowerCase();

        for (int i = 0; i < s1.length(); i++) {
            int index1 = lower1.charAt(i) - 'a';
            counts[index1]++;

            int index2 = lower2.charAt(i) - 'a';
            counts[index2]--;
        }

        for (int i = 0; i < 26; i++) {
            if (counts[i] != 0) {
                return false;
            }
        }
        return true;
    }

    // 7.8.2
    public static boolean isAnagram2(String s1, String s2) {
        // 1. If lengths are different, they can't be anagrams
        if (s1.length() != s2.length()) return false;

        int[] counts = new int[26]; // Our frequency "ledger"

        // 2. Process both strings in one loop
        for (int i = 0; i < s1.length(); i++) {
            // Count up for string 1
            counts[s1.toLowerCase().charAt(i) - 'a']++;

            // Count down for string 2
            counts[s2.toLowerCase().charAt(i) - 'a']--;
        }

        // 3. If it's an anagram, every single slot must be 0
        for (int count : counts) {
            if (count != 0) return false;
        }

        return true;
    }






    public static void main(String[] args) {

        int[] bob = make(5);
        dub(bob);
        System.out.println(mus(bob));


    }
}