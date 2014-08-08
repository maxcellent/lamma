package io.lamma;

/**
 * utility class to construct DayOfYear with Java<br>
 *
 * The original Scala version io.lamma.DayOfYear is not quite Java friendly.
 */
public class DayOfYears {

    public static DayOfYear firstDayOfYear() {
        return DayOfYear$.MODULE$.FirstDayOfYear();
    }

    public static DayOfYear secondDayOfYear() {
        return DayOfYear$.MODULE$.SecondDayOfYear();
    }

    public static DayOfYear nthDayOfYear(int n) {
        return new DayOfYear.NthDayOfYear(n);
    }

    public static DayOfYear lastDayOfYear() {
        return DayOfYear.LastDayOfYear$.MODULE$;
    }

    public static DayOfYear firstWeekDayOfYear(DayOfWeek dow) {
        return DayOfYear$.MODULE$.FirstWeekDayOfYear(dow);
    }

    public static DayOfYear nthWeekdayOfYear(int n, DayOfWeek dow) {
        return new DayOfYear.NthWeekdayOfYear(n, dow);
    }

    public static DayOfYear lastWeekdayOfYear(DayOfWeek dow) {
        return DayOfYear$.MODULE$.LastWeekdayOfYear(dow);
    }

    public static DayOfYear firstMonthOfYear(DayOfMonth dom) {
        return DayOfYear$.MODULE$.FirstMonthOfYear(dom);
    }

    public static DayOfYear nthMonthOfYear(Month m, DayOfMonth dom) {
        return new DayOfYear.NthMonthOfYear(m, dom);
    }

    public static DayOfYear lastMonthOfYear(DayOfMonth dom) {
        return DayOfYear$.MODULE$.LastMonthOfYear(dom);
    }

}
