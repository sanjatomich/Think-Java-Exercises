package chapter04;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class MethodsTesting {

    private static final DateTimeFormatter AMERICAN_FORMATTER = DateTimeFormatter.ofPattern("EEEE, MM dd yyyy 'at' hh:mm a", Locale.US); //why nto allowed in teh methid?
    private static final DateTimeFormatter EUROPEAN_FORMATTER = DateTimeFormatter.ofPattern("EEEE, yyyy MM dd 'at' HH:mm", Locale.UK);

    double root = Math.sqrt(17.0);
    double degrees = 90;
    double angle = degrees / 180.0 * Math.PI;
    double height = Math.sin(angle);
    double radians = Math.toRadians(280.0);
    double degrees1 = Math.toDegrees(Math.PI);
    long x = Math.round(Math.PI * 20);
    double y = Math.cos(angle + Math.PI / 2.0);
    double z = Math.pow(2.0, 10.0);

    public static double calculateArea(double radius) {
        return Math.PI * radius * radius;
    }

    public static double distance(double x1, double x2, double y1, double y2) {
        double dx = Math.pow((x2 - x1), 2);
        double dy = Math.pow((y2 - y1), 2);
        return Math.sqrt(dx + dy);
    }

    public static double multadd(double a, double b, double c) {
        return a * b + c;
    }

    public static double expSum(double a) {
        // a = x, b = ^(-x), c = sqrt(1-e^(-x))
        return multadd(a, Math.exp(-a), Math.sqrt(1 - Math.exp(-a)));
    }

    public static void printAmerican() {
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        String enumDayName = dayOfWeek.name();
        String formattedDayName = enumDayName.substring(0, 1).toUpperCase() + enumDayName.substring(1).toLowerCase();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM dd yyyy").withLocale(Locale.US);
        String customOutput = now.format(formatter);

        System.out.println(formattedDayName + ", " + customOutput);
    }

    public static void printAmericanOptimized() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(AMERICAN_FORMATTER));
    }

    public static void printEuropeanOptimized() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.format(EUROPEAN_FORMATTER));
    }

    //Exercise 4.2
    public static void zippo(String quince, int flag) {
        if (flag < 0) {
            System.out.println(quince + " zoop");
        } else {
            System.out.println("ik");
            baffle(quince);
            System.out.println("boo-wa-ha-ha");
        }
    }

    public static void baffle(String blimp) {
        System.out.println(blimp);
        zippo("ping", -5);
    }

    // 4.2 Logic Flow
    /*
    zippo(quice=rattle, flag=13)
    13>0 -> else "ik" 1, baffle(rattle)
    baffle(rattle)
    baffle(blimp=rattle)
    "rattle" 2
    zippo(quince=ping, flag=-5)
    "ping zoop" 3
    back to baffle(rattle) -> "boo-wha-ha-ha" 4
    q3 blimp=rattle when baffle gets invoked
    q4 ik rattle ping zoop boo-wa-ha-ha
    */

    // Exercise 4.3
    public static void zoop() {
        baffle();
        System.out.println("You wugga ");
        baffle();
    }

    public static void baffle() {
        System.out.println("wug");
        ping();
    }

    public static void ping() {
        System.out.println(".");
    }

    // 4.3 Logic Flow
    /*
    main -> "No, I " goto zoop (main 3,4 left) RETURN HERE LATER DONE
    zoop goto baffle (zoop 2,3 left) RETURN HERE LATER DONE
    baffle -> "wug" goto ping DONE
    ping -> "." DONE
    zoop 2 -> "You wugga " goto baffle DONE
    baffle -> "wug" goto ping DONE
    ping -> "." DONE
    main -> "I" goto baffle DONE
    baffle -> "wug" goto ping DONE
    ping -> "." DONE
    stack when ping is invoked 1st time: No, I / wug
    */

    // Exercise 4.5
    public static void zoop(String fred, int bob) {
        System.out.println(fred);
        if (bob == 5) {
            ping("not ");
        } else {
            System.out.println("!");
        }
    }

    public static void clink(int fork) {
        System.out.println("It's ");
        zoop("breakfast ", fork);
    }

    public static void ping(String strangStrung) {
        System.out.println("any " + strangStrung + "more ");
    }

    // 4.5 Logic Flow
    /*
    4.5 main-> bizz=5, buzz=2, goto zoop("just for", 5) RETURN main 4 left DONE
    zoop(fred="just for",bob=5) -> sout "just for", boob==5 true goto ping(" not ") DONE
    ping(strangStrung="not ") -> sout "any not more " DONE
    main: clink(2 * 2 = 4 = fork) -> sout "It's " goto zoop("breakfast ", 4) DONE
    zoop("breakfast "=fred, 4=bob) -> sout "breakfast ", bob=4 else -> sout "!" DONE
    second time zoop is invoked -> bizz=5, buzz=2, fred="just for",bob=5;
    */

    public static void main(String[] args) {
        // Exercise 4.6 Results
        double result1 = multadd(1, Math.sin(Math.PI / 4.0), Math.cos(Math.PI / 4.0) / 2.0);
        System.out.println("Result 1: " + result1);

        // Exercise 4.4
        int bizz = 5;
        int buzz = 2;
        zoop("just for", bizz);
        clink(2 * buzz);

        // Exercise 4.3
        System.out.println("No, I ");
        zoop();
        System.out.println("I ");
        baffle();

        // Exercise 4.2
        zippo("rattle", 13);

        printAmerican();
        printAmericanOptimized();
        printEuropeanOptimized();
        System.out.println(distance(1, 2, 2, 4));
        System.out.println(calculateArea(2.0));
    }
}