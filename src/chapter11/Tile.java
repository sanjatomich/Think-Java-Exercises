package chapter11;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Tile {
    private char letter;
    private int value;

    public Tile(){
        this.letter = 0;
        this.value = 0;
    }

    public Tile(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    public char getLetter() {
        return this.letter;
    }

    public int getValue() {
        return this.value;
    }

    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static void printTile(Tile tile) {   // can't use this inside a static method
        System.out.println("Letter: " + tile.letter);
        System.out.println("Value: " + tile.value);
    }

    @Override
    public String toString() {
        return String.format("Tile[letter=%c, value=%d]", this.letter, this.value);
    }

    public boolean equals1(Object obj) { // to avoid crashing with a ClassCastException
        if (obj instanceof Tile) {  // (object) instanceof (Type); returns boolean (true or false)
            Tile that = (Tile) obj; // casting
            return this.letter == that.letter && this.value == that.value;
        }
        return false;
    }

    // Java 16+
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tile that) {
            return this.letter == that.letter && this.value == that.value;
        }
        return false;
    }

    public static void main(String[] args) {
        Tile tile1 = new Tile('Z', 10);
        printTile(tile1);
        Tile t = new Tile('a', 2);
        System.out.println(tile1.toString());
        System.out.println("Are they equal? " + tile1.equals(t));

        // Empty constructor + Scanner
        Scanner in = new Scanner(System.in);
        Tile myTile = new Tile();

        /*
        try {
            System.out.print("Enter tile letter: ");
            char letterInput = in.next().charAt(0);
            myTile.setLetter(letterInput);
            System.out.print("Enter tile value: ");
            int valueInput = in.nextInt();
            myTile.setValue(valueInput);

            System.out.println("Successfully created: ");
            System.out.println(myTile.toString());
        } catch (InputMismatchException e) {
            System.err.println("Error: Enter the whole number for the value!");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
        } finally {
            in.close();
        }
         */

        // Try-with-Resources (Java 7)
        try (Scanner input = new Scanner(System.in)) {
            System.out.print("Enter tile letter: ");
            char letterInput = in.next().charAt(0);
            myTile.setLetter(letterInput);

            System.out.print("Enter tile value: ");
            int valueInput = in.nextInt();
            myTile.setValue(valueInput);

            System.out.println("Successfully created: \n" + myTile); // String + object calls .toString() ("String" + myTile.toString())
        } catch (InputMismatchException e) {
            System.err.println("Error: Enter a whole number for the value!");
        } catch (Exception e) {
            System.err.println("An unexpected error occurred.");
        }
    }
}