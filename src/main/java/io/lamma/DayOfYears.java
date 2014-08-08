package io.lamma;

/**
 * utility class to construct DayOfYear with Java<br>
 *
 * The original Scala version io.lamma.DayOfYear is not quite Java friendly.
 */
public class DayOfYears {

    public static DayOfYear firstDay() {
        return DayOfYear$.MODULE$.FirstDayOfYear();
    }

    public static DayOfYear secondDay() {
        return DayOfYear$.MODULE$.SecondDayOfYear();
    }

    public static DayOfYear nthDay(int n) {
        return new DayOfYear.NthDayOfYear(n);
    }

    public static DayOfYear lastDay() {
        return DayOfYear.LastDayOfYear$.MODULE$;
    }

    public static DayOfYear firstWeekDay(DayOfWeek dow) {
        return DayOfYear$.MODULE$.FirstWeekDayOfYear(dow);
    }

    public static DayOfYear nthWeekday(int n, DayOfWeek dow) {
        return new DayOfYear.NthWeekdayOfYear(n, dow);
    }

    public static DayOfYear lastWeekday(DayOfWeek dow) {
        return DayOfYear$.MODULE$.LastWeekdayOfYear(dow);
    }

    public static DayOfYear firstMonth(DayOfMonth dom) {
        return DayOfYear$.MODULE$.FirstMonthOfYear(dom);
    }

    public static DayOfYear nthMonth(Month m, DayOfMonth dom) {
        return new DayOfYear.NthMonthOfYear(m, dom);
    }

    public static DayOfYear lastMonth(DayOfMonth dom) {
        return DayOfYear$.MODULE$.LastMonthOfYear(dom);
    }

}
