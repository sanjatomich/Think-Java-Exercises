package chapter12;

import java.util.Arrays;

public class Card {
    // Class variables: STATIC
    // Without static, creating an array of 1 million cards, the computer could run out of memory (trying to create 1m copies of the word "Spades")
    // With static, the array is created once, and it stays in one spot
    public static final String[] RANKS = {null, "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    public static final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};


    // Instance variables
    // final means no modifier can be added later
    // encoding: using numbers (int) to encode things we want to represent
    private final int rank;
    private final int suit;

    // No setters (and any modifier methods) -> Immutable Objects (less error prone)
    public int getRank() {
        return this.rank;
    }

    public int getSuit() {
        return this.suit;
    }

    public Card(int rank, int suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return RANKS[this.rank] + " of " + SUITS[this.suit];
    }

    public boolean equals(Object obj) { // Java 16+; handles null automatically
        if (obj instanceof Card that) {
            return this.rank == that.rank && this.suit == that.suit;
        }
        return false;
    }

    public int compareTo(Card that) { // the 1st is smaller (lower) - negative number
        // Suits are more important than ranks
        if (this.suit < that.suit) {
            return -1;
        }
        if (this.suit > that.suit) {
            return 1;
        }

        // Ranks: Ace Check
        // Equal (Ace vs Ace)
        if (this.rank == that.rank) {
            return 0;
        }
        // // If I am an Ace, and 'that' is not, I am bigger
        if (this.rank == 1) {
            return 1;
        }
        // If I am not an Ace, and 'that' is an Ace, I am smaller
        if (that.rank == 1) {
            return -1;
        }

        // Ranks: The Rest Check (2-13)
        if (this.rank < that.rank) {
            return -1;
        }
        if (this.rank > that.rank) {
            return 1;
        }
        return 0;
    }

    public int compareTo2(Card that) {
        // 1. Compare suits
        if (this.suit != that.suit) {
            return this.suit - that.suit; // 0, 1, 2, 3; e.g., 0-3=-3
        }

        // Ternary Operator (condition) ? trueValue : falseValue
        // rank1 and rank2 - we need a temporary number to do the math for the comparison
        // Set the rank1 to 14 if it's an Ace, otherwise keep it as it is
        int rank1 = (this.rank == 1) ? 14 : this.rank;
        int rank2 = (that.rank == 1) ? 14 : that.rank;

        return rank1 - rank2;
        // 14 - 13 = 1 (Positive: Ace is bigger)
        // 14 - 14 = 0 (Zero: Equal)
        // 5 - 14 = -9 (Negative: Five is smaller)
    }

    public static void printDeck(Card[] cards) {
        for (Card card : cards) {
            System.out.println(card);
        }
    }

    // Sequential Search (Linear Search): O(n), inefficient; pile of clothes
    // 1m items: 52 steps
    public static int search(Card[] cards, Card target) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    // Binary Search: O(log n); dictionary
    // Base-2 Logarithm - how many times can we cut the number in half until we reach 1
    // Formula: n items -> number of steps is x (2^x = n)
    // 1m items: 20 steps
    public static int binarySearch(Card[] cards, Card target) {
        int low = 0;
        int high = cards.length - 1;
        while (low <= high) {
            System.out.println(low + ", " + high);
            int mid = (low + high) / 2;                 // step 1
            int comp = cards[mid].compareTo(target);

            if (comp == 0) {                            // step 2
                return mid;
            } else if (comp < 0) {                      // step 3
                low = mid + 1;
            } else {                                    // step 4
                high = mid - 1;
            }
        }
        return -1;
    }

    // Recursive Binary Search
    // Helper
    private static int recursiveHelper(Card[] cards, Card target, int low, int high) {
        if (low > high) {
            return -1;
        }

        int mid = (low + high) / 2;
        int comp = cards[mid].compareTo(target);

        if (comp == 0) {
            return mid;
        } else if (comp < 0) { // cards[mid] is smaller than target, so look RIGHT
            return recursiveHelper(cards, target, mid + 1, high);
        } else {
            return recursiveHelper(cards, target, low, mid - 1);
        }
    }

    public static int binarySearchRecursive(Card[] cards, Card target) {
        return recursiveHelper(cards, target, 0, cards.length - 1);
    }

    // 11.2
    public static Card[] makeDeck() {
        // Create the empty slots/"parking lots"
        Card[] cards = new Card[52];

        int index = 0; // 0 - 51
        for (int suit = 0; suit <=3; suit++) { // column
            for (int rank = 1; rank <= 13; rank++) { // row
                cards[index] = new Card(rank, suit); // Create the card and put it in the slot
                index++;
            }
        }
        return cards;
    }

    // 12.3 Decomposition
    public static int[] suitHist(Card[] cards) {
        // Create 4 buckets
        int[] counts = new int[4];

        // Check teh array once
        for (Card card : cards) {
            if (card != null) { // check for null values; NullPointerException
                // Look at the card's suit
                int suitValue = card.getSuit();
                // Add 1 to that bucket
                counts[suitValue]++;
            }
        }
        return counts;
    }

    // Flush
    public static boolean hasFlush(Card[] cards) {
        int[] counts = suitHist(cards);

        // check each bucket in the histogram
        for (int count : counts) {
            if (count >= 5) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasRoyal(Card[] cards) {
        // Must be a flush
        if(!hasFlush(cards)) {
            return false;
        }

        // Histogram of the RANKS (1-13, so 14 slots)
        int[] rankCounts = new int[14];
        for (Card card : cards) {
            if (card != null) {
                rankCounts[card.getRank()]++;
            }
        }

        // Check if Royal (1 is Ace, 10-13 are 10, Jack, Queen, King)
        return rankCounts[1] >= 1 &&
                rankCounts[10] >= 1 &&
                rankCounts[11] >= 1 &&
                rankCounts[12] >= 1 &&
                rankCounts[13] >= 1;
    }


    public static void main(String[] args) {
        // The array contains references* to the Card objects; initially, all are null
        Card[] cards = new Card[52];

        if (cards[0] == null) {
            System.out.println("No card yet!");
        }

        // NullPointerException (non-existing Card object)
        // System.out.println(cards[0].rank);

        System.out.println(Arrays.toString(cards));

        Card card = new Card(11, 0);
        System.out.println(binarySearch(cards, card));
    }
}