package chapter13;

import chapter12.Card;

public class Deck {              // Level 1: the Deck (Master Object)
    // Instance Variable
    private Card[] cards;        // Level 2: Card[] array Object (Variable inside Deck)
                                 // Level 3: Card Object (Item inside the array)

    public Deck(int n) { // the size of a deck, 52/10/...
        // Set this object's cards variable to a new array of Card objects with n slots
        this.cards = new Card[n]; // Create n null slots in memory
    }

    public Card[] getCards() {
        return this.cards; // an array of cards
    }

    public Deck() {
        this.cards = new Card[52];
        int index = 0;
        for (int suit = 0; suit <= 3; suit++) {
            for (int rank = 1; rank <= 13; rank++) {
                this.cards[index] = new Card(rank, suit);
                index ++;
            }
        }
    }

    // Instance Method (doesn't need any parameters; it lives inside the Deck)
    public void print() { // Call: myDeck.print()
        for (Card card : this.cards) {
            System.out.println(card);
        }
    }

    // Pseudocode (top-down design)
    public void shuffle() {
        // for each index i {
        // choose a random number between i and length - 1
        // swap the ith card and the randomly-chosen card
        for (int i = 0; i < cards.length - 1; i++) {
            int j = randomInt(i, cards.length - 1);
            swapCards(i, j);
        }
    }

    // Helper Method (often private)
    // Class method (static) - general purpose tool; does not know anything about cards
    private static int randomInt(int low, int high) {
        // return a random number between low and high,
        // including both

        // 1. Calculate how many possible numbers are there
        int range = high - low + 1;
        // 2. Get a random decimal (0.0 - 0.9999...)
        double fract = Math.random();
        // 3. Scale and shift it
        return (int) (fract * range) + low;
    }

    private static int randomInt2 (int low, int high) {
        java.util.Random generator = new java.util.Random();
        return generator.nextInt(high - low + 1) + low;
    }

    // Instance method (non-static) - specific action that changes the internal state of one deck
    // It MUST know about a specific Deck (reach into this.cards)
    // "Hey myDeck, swap your own card #5 with #10"
    private void swapCards(int i, int j) {
        // swap the ith and the jth cards in the array
        Card temp = this.cards[i]; // Put card i in your pocket
        this.cards[i] = this.cards[j]; // Move card j to spot i
        this.cards[j] = temp; // Take the card i from your pocket and put it in spot j
    }

    // O(n^2)
    public void selectionSort() {
        // for each index i {
        // find the lowest card at or to the right of i
        // swap the ith card and the lowest card found

        for (int i = 0; i < this.cards.length; i++) { // n steps
           // 1. Find the lowest card from the current position i to the end
            int j = indexLowest(i, this.cards.length - 1); // n steps

            // 2. Swap the card at i with the lowest card we just found at j
            swapCards(i, j);
        }
    }

    private int indexLowest(int low, int high) {
        // Assume the first card in the range is the smallest for now
        int lowIndex = low;

        // Start checking from the next card (low + 1) to the end (high)
        for (int i = low + 1; i <= high; i ++) {
            // Compare current card i with our current winner (lowIndex)
            // a negative number means card i (the first card) is smaller; zero - the same
            if (this.cards[i].compareTo(this.cards[lowIndex]) < 0) { // objects in Java
                lowIndex = i;
            }
        }
        return lowIndex;
    }

    // O (n log2n)
    public static Deck merge1(Deck d1, Deck d2) {
        // 1. Create a new deck big enough for both (Level 1: The Master Object)
        int totalSize = d1.cards.length + d2.cards.length;
        Deck result = new Deck(totalSize);

        // 2. "Three fingers" (Indices)
        int i = 0; // Finger for deck 1
        int j = 0; // Finger for deck 2

        // 3. Loop through every slot in the new result deck
        for ( int k = 0; k < totalSize; k++) {

            // CASE A: Deck 1 is empty, just take from Deck 2
            if ( i >= d1.cards.length) {
                result.cards[k] = d2.cards[j];
                j++;
            }
            // CASE B: Deck 2 is empty, just take from Deck 1
            if ( j >= d2.cards.length) {
                result.cards[k] = d1.cards[i];
                i++;
            }
            // CASE C: Both have cards, COMPARE them!
            else if (d1.cards[i].compareTo(d2.cards[j]) <= 0) {
                result.cards[k] = d1.cards[i]; // Deck 1 card is smaller/equal
                i++;
            } else {
                result.cards[k] = d2.cards[j]; // Deck 2 card is smaller
            }
        }
        return result;
    }

    public Deck subdeck(int low, int high) {
        Deck sub = new Deck(high - low + 1); // ! forgetting + 1 leads to off-by-one errors
        for (int i = 0; i < sub.cards.length; i ++) {
            sub.cards[i] = this.cards[low + i];
        }
        return sub;
    }

    private static Deck merge(Deck d1, Deck d2) {
        // create a new deck, d3, big enough for all the cards
        // use the index i to keep track of where we are at in
        // the first deck, and the index j for the second deck
        int totalSize = d1.cards.length + d2.cards.length;
        Deck d3 = new Deck(totalSize);
        int i = 0;
        int j = 0;

        // fill every slot k in the new big deck
        for (int k = 0; k < totalSize; k++) {
            // if d1 is empty, use top card from d2
            if (i >= d1.cards.length) {
                d3.cards[k] = d2.cards[j];
                j++;

            // if d2 is empty, use top card from d1
                if (j >= d2.cards.length) {
                    d3.cards[k] = d1.cards[i];
                    i++;
                }

            // no one is empty, compare the top two cards
                if (d1.cards[i].compareTo(d2.cards[j]) <= 0) {
                    d3.cards[k] = d1.cards[i]; // card 1 is smaller/equal
                    i++;
                } else {
                    d3.cards[k] = d2.cards[j];
                    j++;
                }
            // add lowest card to the new deck at k
            // increment i or j (depending on card)
            }
        }
        // return the new deck
        return d3;
    }

    public Deck almostMergeSort() {
       // 1. Find the midpoint
       int mid = (this.cards.length - 1) / 2;

       // 2. Divide the deck into two subdecks (use the subdeck method)
        Deck d1 = subdeck(0, mid);
        Deck d2 = subdeck(mid + 1, this.cards.length - 1);

        // 3. Sort the subdecks using selectionSort
        d1.selectionSort();
        d2.selectionSort();

        // 4. Merge the sorted subdecks and return the result
        return merge (d1, d2);
    }

    // recursive merge O(nlogn)
    public Deck mergeSort() {
        // 1. BASE CASE: If the deck has 0 or 1 cards, it is already sorted!
        if (this.cards.length <= 1) {
            return this;
        }

        // 2. DIVIDE: Find the midpoint
        int mid = (this.cards.length - 1) / 2;
        Deck d1 = subdeck(0, mid);
        Deck d2 = subdeck(mid + 1, this.cards.length - 1);

        // 3. RECURSION Sort the subdecks by calling mergeSort AGAIN
        d1 = d1.mergeSort();
        d2 = d2.mergeSort();

        // 4. MERGE: Zip the two sorted halves and return the result
        return merge(d2, d2);
    }

    // 13.2
    @Override
    public String toString() {
        // 1. Create the Banner
        StringBuilder sb = new StringBuilder();
        // 2. Loop through every card in the tray
        for (Card card : this.cards) {
            // 3/ Add the card's strong plus a new line to the banner
            sb.append(card);
            sb.append("\n"); // Move to the next line for the next card
        }
        // 4. Covert the banner into a single string
        return sb.toString();
    }
    // Deck - file/class name
    // this - Object
    // .cards - private array inside this

    // 13.3 Utility method (only Deck uses it internally)
    private int indexLowest1(int low, int high) {
        int winner = low; // assume the first is the smallest (!just storing the address/index, not the variable)

        for (int i = low + 1; i <= high; i ++) { // look at all the cards from i + 1 to the end
            if (this.cards[i].compareTo(this.cards[winner]) < 0) { // use compareTo, if it's a negative number, card[i] (the 1st) is smaller
                winner = i;
            }
        }
        return winner; // return the index of the winner

    }

    public void selectionSort1() {
        for (int i = 0; i < this.cards.length; i++) {
            int j = indexLowest(i, this.cards.length - 1);
            swapCards(i, j);
        }
    }





    public static void main(String[] args) {

       // Deck myDeck = new Deck();
       // System.out.println("Shuffling");
       // myDeck.shuffle();
       // myDeck.print();
       // System.out.println("Sorting");
       // myDeck.selectionSort();
       // myDeck.print();


        // 1. Build and shuffle a deck
        Deck deck1 = new Deck();
        deck1.shuffle(); // instance method (the object shuffles itself)

        // 2. Use subdeck to form two small halves
        Deck d1 = deck1.subdeck(0, 4);
        Deck d2 = deck1.subdeck(5, 9);

        // 3. Sort the small halves
        d1.selectionSort();
        d2.selectionSort();

        // 4. Pass the two halves to merge
        Deck result = Deck.merge(d1, d2); // static method (The class is like a factory creating a third deck)

        // 5. Print
        System.out.println(result);

    }


}
