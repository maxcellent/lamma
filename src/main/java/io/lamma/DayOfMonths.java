package io.lamma;

/**
 * utility class to construct DayOfMonth with Java<br>
 *
 * The original Scala version io.lamma.DayOfMonth is not quite Java friendly.
 */
public class DayOfMonths {

    public static DayOfMonth firstDayOfMonth() {
        return DayOfMonth$.MODULE$.FirstDayOfMonth();
    }

    public static DayOfMonth nthDayOfMonth(int n) {
        return new DayOfMonth.NthDayOfMonth(n);
    }

    public static DayOfMonth lastDayOfMonth() {
        return DayOfMonth.LastDayOfMonth$.MODULE$;
    }

    public static DayOfMonth firstWeekdayOfMonth(DayOfWeek dow) {
        return DayOfMonth$.MODULE$.FirstWeekdayOfMonth(dow);
    }

    public static DayOfMonth nthWeekdayOfMonth(int n, DayOfWeek dow) {
        return new DayOfMonth.NthWeekdayOfMonth(n, dow);
    }

    public static DayOfMonth lastWeekdayOfMonth(DayOfWeek dow) {
        return new DayOfMonth.LastWeekdayOfMonth(dow);
    }

}
