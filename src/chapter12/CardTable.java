package chapter12;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class CardTable extends Canvas {

    private Image[][] images;
    private int cardWidth, cardHeight;

    /**
     * Creates a CardTable.
     * cardset is the name of the folder that contains the card images.
     */
    public CardTable(String cardset) {
        setBackground(new Color(0x088A4B));

        // create a 2-D array of card images
        images = new Image[14][4];
        String suits = "cdhs";

        for (int suit = 0; suit <= 3; suit++) {
            char c = suits.charAt(suit);

            for (int rank = 1; rank <= 13; rank++) {
                String s = String.format("%s/%02d%c.gif",
                        cardset, rank, c);
                images[rank][suit] = new ImageIcon(s).getImage();
            }
        }

        // get the width and height of the cards and set the size of
        // the frame accordingly
        cardWidth = images[1][1].getWidth(null);
        cardHeight = images[1][1].getHeight(null);

        // set the size temporarily to get the insets
        setTableSize(14, 4);
    }

    /**
     * Sets the table size.
     * x and y are in units of card width/height.
     */
    public void setTableSize(double x, double y) {
        setSize((int) (x * cardWidth),
                (int) (y * cardHeight));
    }

    /**
     * Draws a card at the given coordinates.
     * x and y are in units of card width/height.
     */
    public void drawCard(Graphics g, int rank, int suit, double x, double y) {
        Image image = images[rank][suit];
        g.drawImage(image,
                (int) (x * cardWidth),
                (int) (y * cardHeight),
                null);
    }

    /**
     * Special method invoked when the Frame needs to be drawn.
     */
    public void paint(Graphics g) {
        // 1. Draw the "Stock" pile (the back of a card) in the top left
        // We use rank 0 or a special filename for the back
        drawCard(g, 1, 0, 0, 0); // Temporary: just drawing a card to show where it goes

        // 2. The Klondike Tableau (The 7 columns)
        for (int col = 0; col < 7; col++) {
            for (int row = 0; row <= col; row++) {
                // x position is based on the column
                // y position is slightly shifted for each row to show overlapping
                double x = col + 1;
                double y = row * 0.2 + 1.5;

                // For now, we draw the same card, but in a real game,
                // you'd pull from your Card[] deck.
                drawCard(g, row + 1, col % 4, x, y);
            }
        }
    }

    /*
    public void paint(Graphics g) {
        for (int rank = 1; rank <= 13; rank++) {
            for (int suit = 0; suit <= 3; suit++) {
                double x = rank - 1 + suit / 5.0;
                double y = suit / 2.0;
                drawCard(g, rank, suit, x, y);
            }
        }
    }
     */

    public static void main(String[] args) {
        // make the frame
        JFrame frame = new JFrame("Card Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // add the CardTable
        String cardset = "cardset-oxymoron";
        Canvas canvas = new CardTable(cardset);
        frame.getContentPane().add(canvas);

        // show the frame
        frame.pack();
        frame.setVisible(true);
    }

}
