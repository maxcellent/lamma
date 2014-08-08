package io.lamma;

/**
 * utility class to construct DayOfMonth with Java<br>
 *
 * The original Scala version io.lamma.DayOfMonth is not quite Java friendly.
 */
public class DayOfMonths {

    /**
     * @return first day of month
     */
    public static DayOfMonth firstDay() {
        return DayOfMonth$.MODULE$.FirstDayOfMonth();
    }

    /**
     * @return nth day of month
     */
    public static DayOfMonth nthDay(int n) {
        return new DayOfMonth.NthDayOfMonth(n);
    }

    public static DayOfMonth lastDay() {
        return DayOfMonth.LastDayOfMonth$.MODULE$;
    }

    public static DayOfMonth firstWeekday(DayOfWeek dow) {
        return DayOfMonth$.MODULE$.FirstWeekdayOfMonth(dow);
    }

    public static DayOfMonth nthWeekday(int n, DayOfWeek dow) {
        return new DayOfMonth.NthWeekdayOfMonth(n, dow);
    }

    public static DayOfMonth lastWeekday(DayOfWeek dow) {
        return new DayOfMonth.LastWeekdayOfMonth(dow);
    }

}
