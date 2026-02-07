package chapter10;
import java.awt.*;

public class MutableObjectsExercises {

    // 10.1
    public static int riddle(int x, Point p) { // p is a copy of the reference (the second arrow pointing to the same object)
        x = x + 7;  // local copy of x; the original is unaffected
        // if we say p.x = x + 7; Aliasing: this would update the Point object to {x:12; y:2) in the main, because they point to the same object
        return x + p.x + p.y;
    }

    // 10.2
    public static double distance(Point p1, Point p2) { // (2,4), (5,8)
        int dx = p2.x - p1.x; // dx = 5 - 2 = 3
        int dy = p2.y - p1.y; // dy = 9 - 4 = 4
        return Math.sqrt(dx * dx + dy * dy); // 3 * 3 + 4 * 4 = 9 + 16 = 25
    }

    public static Point findCenter(Rectangle box) { // 10.2 (0, 2, 4, 4)    // 10.3 (2, 4, 7, 9),   (1, 3, 9, 11)
        int x = box.x + box.width / 2; // x = 0 + 4 / 2 = 2     // x = 2 + 7 / 2 = 2 + 3 = 5,   x = 1 + 9 / 2 = 1 + 4 = 5
        int y = box.y + box.height / 2; // y = 2 + 4 / 2 = 4    // y = 4 + 9 / 2 = 4 + 4 = 9,   y = 3 + 11 / 2 = 3 + 5 = 8
        return new Point(x, y); // (2, 4)   // (5, 8),  (5, 8)
    }

    // 10.3
    public static void printPoint(Point p) {
        System.out.println("(" + p.x + ", " + p.y + ")"); // (5, 8)
    }


    public static void main(String[] args) {
        // 10.1
       /* int x = 5; // stays same forever because primitives are copied; it's on the stack
        Point blank = new Point(1, 2); // reference to an object on the Heap
        System.out.println(riddle(x, blank));
        System.out.println(x);
        System.out.println(blank.x);
        System.out.println(blank.y);
        */

        // main: x, blank*Point(1,2), riddle(x, blank) RETURN END
        // riddle(x, blank): x=5+7=12, riddle=12+1+2=15; END
        // main: 15 (riddle(x), blank)
        // 5 (x)
        // 1 (blank.x)
        // 2 (blank.y)

        // main frame (stack): x=5, blank*
        // riddle frame (stack): x=12; p*
        // Point Object (Heap): x:1,y:2

        // 10.2
        Point blank = new Point(5, 8);
        Rectangle rect = new Rectangle(0, 2, 4, 4);
        Point center = findCenter(rect);
        double dist = distance(center, blank);
        System.out.println(dist);

        // main: blank*(5,8), rect*(0,2,4,4), center*=findCenter() RETURN center*(2,4) END
        // findCenter(rec): (2,4) END
        // main: dist=distance() RETURN END
        // distance(center, blank): 25 END
        // main: sqrt(25) = 5

        // 10.3 Aliasing (aliases are two variables that refer to the same object)
        Rectangle box1 = new Rectangle(2, 4, 7, 9);
        Point p1 = findCenter(box1);
        printPoint(p1);
        box1.grow(1, 1);
        Point p2 = findCenter(box1);
        printPoint(p2);

        // main: box1*(2,4,7,9), p1*=findCenter() RETURN p1*(5,8) END
        // findCenter(box1): (5, 8) END
        // main: printPoint() RETURN p1*(5, 8) END
        // printPoint(p1): (5, 8) END -
        // main: box1.grow(1, 1): x-1, y-1, w+2, h+2 - box1*(1, 3, 9, 11) END
        // p2*=findCenter() RETURN p2*(5, 8) END
        // findCenter(box1): (5, 8) END
        // main: printPoint() RETURN
        // printPoint(p2): (5, 8) END -
        // p1 and p2 are not the same object; not aliases
        if (p1 == p2) { // = checks the memory addresses; .equals() checks if the data inside the boxes is the same
            System.out.println("Aliases");
        } else {
            System.out.println("Not Aliases");
        }
    }
}