package chapter13;

import chapter12.Card;

import java.util.ArrayList;

public class Pile {
    private ArrayList<Card> cards; // array list of Card objects

    public Pile() {
        this.cards = new ArrayList<Card>();
    }

    public Deck toDeck() {
        Deck deck = new Deck(this.cards.size());
        for (int i = 0; i < this.cards.size(); i++) {
            deck.getCards()[i] = this.cards.get(i);
        }
        return deck;
    }

    public ArrayList<Card> getCards() {
        return this.cards;
    }

    // wrapper methods
    public Card popCard() {
        return this.cards.remove(0); // from the top of the pile; the list shifts everyone up one spot (no null hole as with the regular array)
    }

    public void addCard(Card card) {
        this.cards.add(card); // to the bottom of the pile
    }

    // wrapper method
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

    public void addDeck(Deck deck) {
        for (Card card : deck.getCards()) { // cards are immutable objects in memory; Deck and Pile are pointers
            this.cards.add(card);
        }
    }


    public static void main(String[] args) {
        Deck deck = new Deck();
        deck.shuffle();

        Pile p1 = new Pile();
        p1.addDeck(deck.subdeck(0, 25));

        Pile p2 = new Pile();
        p2.addDeck(deck.subdeck(26, 51));

        while (!p1.isEmpty() && !p2.isEmpty()) {
            // pop a card from each pile
            Card c1 = p1.popCard();
            Card c2 = p2.popCard();

            // compare the cards
            // we could also use int diff = c1.comparetTo(c2); from Chapter 12
            int diff = c1.getRank() - c2.getRank();


            if (diff > 0) {
                p1.addCard(c1);
                p1.addCard(c2);
            } else if (diff < 0) {
                p2.addCard(c1);
                p2.addCard(c2);
            } else {
                // it's a TIE - start the "War" Vattle
                Pile mid = new Pile();
                mid.addCard(c1);
                mid.addCard(c2);

                boolean tie = true;
                while (tie) {
                    // check if players have enough cards
                    if (p1.getCards().size() < 4 || p2.getCards().size() < 4) {
                        // not enough cards to continue the war, break out
                        tie = false;
                    } else {
                        // draw 3 cards each (face down)
                        for (int i = 0; i < 3; i++) {
                            mid.addCard(p1.popCard());
                            mid.addCard(p2.popCard());
                        }

                        // Draw the 4th card (face up) to break the tie
                        Card c3 = p1.popCard();
                        Card c4 = p2.popCard();
                        mid.addCard(c3);
                        mid.addCard(c4);

                        // Compare the tie-breaker cards
                        int diff2 = c3.getRank() - c4.getRank();
                        if (diff2 > 0) {
                            // Player 1 wins the war!
                            p1.addDeck(mid.toDeck());
                            tie = false;
                        } else if (diff2 < 0) {
                            // Player 2 wins the war!
                            p2.addDeck(mid.toDeck());
                            tie = false;
                        }
                        // If diff2 == 0, the 'while(tie)' continues for another round of War
                    }
                }
            }
        }

        // 4. Declare the Final Winner
        if (p2.isEmpty()) {
            System.out.println("Player 1 wins the game!");
        } else {
            System.out.println("Player 2 wins the game!");
        }
    }

}

