package chapter11;

import java.time.LocalTime;

public class Time {
    // instance variables
    private int hour;   // private - class only
    private int minute;
    private double second;

    public Time() {
        // new Time(); // StackOverflowError; infinite recursion
        this.hour = 0;  // the field
        this.minute = 0;
        this.second = 0;
    }

    public Time(int hour, int minute, double second) {
        this.hour = hour;   // shadowing: this.hour is the field; hour is the parameter
        this.minute = minute;
        this.second = second;
    }

    // getters (let clients "read" the instance variables)
    public int getHour() {
        return this.hour;
    }

    public int getMinute() {
        return this.minute;
    }

    public double getSecond() {
        return this.second;
    }

    // setters (clients can modify the instance variables; "write")
    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setSecond(double second) {
        this.second = second;
    }

    public static void printTime(Time t) {
        System.out.println(t.hour);
        System.out.println(":");
        System.out.println(t.minute);
        System.out.println(":");
        System.out.println(t.second);
    }

    public static void PrintTimeF(Time t) {
        System.out.printf("%02d:%02d:%04.1f\n", t.hour, t.minute, t.second);
    }

    // Instance method (used on an object/instance)
    public String toString() {  // not static; it's an instance method
        return String.format("%02d:%02d:%04.1f\n", this.hour, this.minute, this.second);
    }

    // Instance method
    public boolean equals(Time that) {
        final double DELTA = 0.001; // don't compare floating-point numbers with == because of rounding errors -> check whether the diff. is smaller than a threshold
        return this.hour == that.hour && this.minute == that.minute && Math.abs(this.second - that.second) < DELTA;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Check for null or diff types
        if (!(obj instanceof Time)) return false;

        // 2. Casting (Java 16+)
        Time that = (Time) obj;

        // 3. Comparison with DELTA
        final double DELTA = 0.001;
        return this.hour == that.hour &&
                this.minute == that.minute &&
                Math.abs(this.second - that.second) < DELTA;
    }

    // Add times
    // Method 1: Static method with 2 Objects as parameters
    public static Time add(Time t1, Time t2) {
        Time sum = new Time();
        sum.hour = t1.hour + t2.hour;
        sum.minute = t1.minute + t2.minute;
        sum.second = t1.second + t2.second;
        return sum;
    }

    // Method 2: Instance method that gets invoked on one Object, and has the other as a parameter
    // Immutable method; returns a new Time object (safer)
    public Time add(Time t2) {
        Time sum = new Time();
        sum.hour = this.hour + t2.hour; // implicit parameter
        sum.minute = this.minute + t2.minute;
        sum.second = this.second + t2.second;

        if (sum.second >= 60) {
            sum.second -= 60;
            sum.minute += 1;
        }
        if (sum.minute >= 60) {
            sum.minute -= 60;
            sum.hour += 1;
        }
        if (sum.hour >= 24) {
            sum.hour -= 24;
        }
        return sum;
    }

    // 11.1 Mutable method; changes 'this' instance
    // % (remainder) tells us what small units stay behind in the current unit; the seconds that didn't make it into a full minute
    // / (division) tells us what big units move forward to the next unit; how many full minutes
    public void increment(double seconds) { // O(1); e.g., 01:59:50, add 20s
        double totalSeconds = this.second + seconds;    // 50.0 + 20.0 = 70.0; totalSeconds = 70.0

        this.second = totalSeconds % 60;    // 70 % 60 = 1 (10); this.second = 10.0

        int extraMinutes = (int) (totalSeconds / 60.0);     // (int)(70 / 60) = 1; extraMinutes = 1 -> because totalSeconds is a double, and we want whole minutes
        int totalMinutes = this.minute + extraMinutes;      // 59 + 1 = 60; totalMinutes = 60

        this.minute = totalMinutes % 60;    // 60 % 60 = 0; this.minute = 0

        int extraHours = totalMinutes / 60;     // 60 / 60 = 1; extraHours = 1
        this.hour = this.hour + extraHours;     // 1 + 1 = 2; this.hour = 2;
        //02:00:10
    }


    public static void main(String[] args) {
        Time time0 = new Time(); // create a Time object, use the keyword new
        Time time = new Time(11, 59, 59.9);
        System.out.println(time); // Method overriding: if no toString() method; this would print chapter11.Time@36baf30c
        String s = time.toString();
        System.out.println(time);

        Time time1 = new Time(9, 30, 0.0); // time1*(9,30,0.0)
        Time time2 = time1; // time2*time1*(9,30,0.0); time1 == time 2 is true (identical)
        Time time3 = new Time(9, 30, 0.0); // time3*(9,30,0.0); time 1 == time 3 is false

        // two objects are equal if their instance variables are equal
        System.out.println(time1.equals(time3));

        Time startTime = new Time(18, 50, 0.0);
        Time runningTime = new Time(2, 16, 0.0);
        Time endTime = Time.add(startTime, runningTime); // static method
        Time endTime2 = startTime.add(runningTime);

        // 11.1 (Java 8)
        LocalTime timeNow = LocalTime.of(23, 59, 50);
        LocalTime newTime = timeNow.plusSeconds(20);
    }
}
