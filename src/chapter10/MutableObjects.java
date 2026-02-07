package chapter10;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class MutableObjects {

    public static void printPoint(Point p) {
        System.out.println("(" + p.x + ", " + p.y + ")");
    }

    public static double distance(Point p1, Point p2) { // passing Objects as parameters
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static Point findCenter(Rectangle box) { // pass objects as arguments; methods can return new objects
        int x = box.x + box.width / 2;
        int y = box.y + box.height / 2;
        return new Point(x, y); // create a new Point object and return a reference to it
    }

    // Modifies the original object
    public static void moveRect(Rectangle box, int dx, int dy) {
        box.x = box.x + 50;
        box.y = box.y + 50;
    }

    /**
     * Creates a new Rectangle that is moved a specified distance from the original.
     *
     * @param rect the original Rectangle object
     * @param dx the distance to move along the x-axis
     * @param dy the distance to move along the y-axis
     * @return a new Rectangle object that has been moved
     */
    // Method that modifies an object copy SAFELY (leaves the original alone)
    public static Rectangle movedCopy(Rectangle rect, int dx, int dy) {                
        // 1. Create a new object that looks like the old one
        Rectangle newRect = new Rectangle(rect); // Copy Constructor                                                                                
        // 2. Modify the copy
        newRect.translate(dx, dy);
        // 3. Send the copy back to the user
        return newRect;  
    }    

    // The Reassignment Trap
    public static void tryToReplace(Rectangle rect) {
        // 1. House A passed from main
        // 2. 'new' creates House B in a different part of memory
        // 3. '=' tells 'rect' to point to House B instead
        rect = new Rectangle(500, 500, 10, 10);
        // 4. 'rect' is now looking at House B
        // 5. but r2 in main is still looking at House A
    }
    
    // Return the new object (cannot replace)
    public static Rectangle createNewRec() {    // method to "replace" an object - return the new object
        return new Rectangle(500, 500, 10, 10);
    }

    // Mutable vs Immutable
    public static void suprise() {
        String s1 = "Hi, Mom!";
        String s2 = "Hi, " + "Mom!";
        if (s1 == s2) { // true; same memory address; compiler creates only one String and make both variables refer to the same object
            System.out.println("s1 and s2 are the same!");
        }
    }

    public static void main(String[] args) {
        // Point
        Point blank = new Point(3, 4);
        int x = blank.x;

        System.out.println(blank.x + ", " + blank.y);
        int sum = blank.x * blank.x + blank.y * blank.y;

        printPoint(blank);

        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);
        double dist = p1.distance(p2);
        System.out.println(blank);  // prints the type of object and values of the attributes; automatically calls toString

        // Rectangle
        Rectangle box = new Rectangle(0, 0, 100, 200);
        System.out.println(box);
        moveRect(box, 50, 100);
        box.translate(50, 100); // invoke the 'translate' method on the object that box refers to; modifies the original object

        // Aliasing
        Rectangle box1 = new Rectangle(0, 0, 100, 200);
        Rectangle box2 = box1; // box2 is another name for box1, not a new box
        System.out.println("Before change, box1: " + box1);
        box2.translate(50, 50);
        System.out.println("After change, box1: " + box1);
        if (box1 == box2) {
            System.out.println("box1 and box2 are the exact same object.");
        }

        // Create a REAL second object with the same values as the original; all the changes happen on the new object
        Rectangle box3 = new Rectangle(box1.x, box1.y, box1.width, box1.height);
        Rectangle box3A = new Rectangle(box1);  // The Copy Constructor; passing an existing object into a new Rectangle(), it copies all the values
        Point p = box1.getLocation(); // Point object representing the location of the box1

        // Passing an object as an argument (modifying) into a method (passing the MEMORY ADDRESS)
        Rectangle r1 = new Rectangle(0, 0, 100, 200);
        // 1. Mutable Style (changes the original)
        System.out.println("Before moving: " + r1);     // r1: x = 0, y = 0  (OG)
        moveRect(r1, 50, 50);
        System.out.println("After moving: " + r1);      // r1: x = 50, y = 50 (OG)
        // 2. Immutable Style
        Rectangle nextbox = movedCopy(r1, 50, 50); // nextBox: x = 0, y = 0 (copy); r1: x = 0, y = 0 (OG still intact)

        // ! The Reassignment Trap (can't replace the whole object inside a method)
        Rectangle r2 = new Rectangle(0, 0, 10, 10);
        tryToReplace(r2);
        System.out.println(r2.x);
        // To modify the object inside a method - reach inside it (cannot change identity)
        // 1. change a field: rect.x = 500; YES
        // 2. call a mover: rect.translate(10, 10); YES
        // 3. ! Reassign rect = new Rectangle(...); NO
        // Cannot change the REFERENCE; can change the STATE of an object (x, y, width, ...)

        // Garbage Collector
        Rectangle first = new Rectangle(0, 0, 100, 200); // Object A created: first points to A
        Rectangle second = new Rectangle(50, 50, 50, 50); // Object B created: second points to B
        first = second; // Object A is "garbage"; no variable points to it anymore
        // at the ned of main, both variables go out of scope, even object B

        // String Builder
        StringBuilder sb = new StringBuilder();
        sb.ensureCapacity(50);
        for (int i = 1; i <= 5; i++) {
            sb.append("Item ").append(i);   // method chaining; NO extra temporary objects created (no + sign)
            if ( i < 5) {   // Trailing Separator, to avoid Item 1, Item 2, Item 3, Item 4, Item 5,
                sb.append(", ");
            }
        }
        String result = sb.toString(); // creates an immutable String
        System.out.println(result);
        // Java 8+ StringJoiner/String.join()
        StringJoiner sj = new StringJoiner(", ");
        for (int i = 1; i <=5; i++) {
            sj.add("Item " + i);    // the + sign creates a temporary String in every iteration of the loop
        }
        System.out.println(sj.toString());
        // Streams
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        String resultStream = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
        System.out.println(result);
        // String that looks like  an HTML list (<ul><li>1</li><li>2</li>...</ul>)
        // Using the StringBuilder (more efficient; no new Strings)
        StringBuilder sb1 = new StringBuilder("<ul>"); // start with prefix
        for (Integer n: numbers) {
            sb1.append("<li>").append(n).append("</li>");
        }
        sb1.append("</ul>"); // end with a suffix
        String result2 = sb1.toString();
        System.out.println(result2);
        // Using the Stream
        String result3 = numbers.stream()
                .map(n -> "<li>" + n + "</li>") // wrap each item; transforms 1 into <li>1</li>; creates a new string for every <li> tag (less efficient than StringBuilder)
                .collect(Collectors.joining("", "<ul>", "</ul>"));
        System.out.println(result3);
        // String.join (static method on the String class for a list of Strings)
        // do not allow prefixes or suffixes
        List<String> list = List.of("A", "B", "C");
        String resultJoin = String.join("-", list); // A-B-C

        //
        List<String> names = List.of("anna", "bob", "charles");
        String resultNames = names.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining("-", "{", "}"));
    }
}