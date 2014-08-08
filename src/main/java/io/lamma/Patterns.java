package io.lamma;

/**
 * utility class to construct Recurrence Patterns with Java<br>
 *
 * The original Scala version io.lamma.Pattern is not quite Java friendly.
 */
public class Patterns {

    // =========== Daily Patterns ===========
    /**
     * alias of newDailyPattern(1)
     */
    public static final Daily EVERY_DAY = newDailyPattern(1);

    /**
     * alias of newDailyPattern(2)
     */
    public static final Daily EVERY_OTHER_DAY = newDailyPattern(2);

    public static Daily newDailyPattern(int days) {
        return new Daily(days);
    }

    public static Daily newDailyBackwardPattern(int days) {
        return newDailyPattern(- days);
    }

    // =========== Weekly Patterns ===========
    /**
     * alias of weeks(1)
     */
    public static final Weekly EVERY_WEEK = newWeeklyPattern(1);

    /**
     * alias of weeks(2)
     */
    public static final Weekly EVERY_OTHER_WEEK = newWeeklyPattern(2);

    public static Weekly newWeeklyPattern(int weeks) {
        return Weekly$.MODULE$.apply(weeks, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly newWeeklyPattern(DayOfWeek dow) {
        return Weekly$.MODULE$.apply(1, dow);
    }

    public static Weekly newWeeklyPattern(int weeks, DayOfWeek dow) {
        return Weekly$.MODULE$.apply(weeks, dow);
    }

    public static Weekly newWeeklyBackwardPattern(int weeks) {
        return Weekly$.MODULE$.apply(- weeks, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly newWeeklyBackwardPattern(DayOfWeek dow) {
        return Weekly$.MODULE$.apply(-1, dow);
    }

    public static Weekly newWeeklyBackwardPattern(int weeks, DayOfWeek dow) {
        return Weekly$.MODULE$.apply(- weeks, dow);
    }

    // =========== Monthly Patterns ===========
    /**
     * alias of newMonthlyPattern(1)
     */
    public static final Monthly EVERY_MONTH = newMonthlyPattern(1);

    /**
     * alias of newMonthlyPattern(2)
     */
    public static final Monthly EVERY_OTHER_MONTH = newMonthlyPattern(2);

    public static Monthly newMonthlyPattern(int n) {
        return Monthly$.MODULE$.apply(n, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly newMonthlyPattern(DayOfMonth dom) {
        return Monthly$.MODULE$.apply(1, dom);
    }

    public static Monthly newMonthlyPattern(int n, DayOfMonth dom) {
        return Monthly$.MODULE$.apply(n, dom);
    }

    public static Monthly newMonthlyBackwardPattern(int n) {
        return Monthly$.MODULE$.apply(-n, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly newMonthlyBackwardPattern(DayOfMonth dom) {
        return Monthly$.MODULE$.apply(-1, dom);
    }

    public static Monthly newMonthlyBackwardPattern(int n, DayOfMonth dom) {
        return Monthly$.MODULE$.apply(-n, dom);
    }

    // =========== Yearly Patterns ===========
    /**
     * alias of newYearlyPattern(1)
     */
    public static final Yearly EVERY_YEAR = newYearlyPattern(1);

    /**
     * alias of newYearlyPattern(2)
     */
    public static final Yearly EVERY_OTHER_YEAR = newYearlyPattern(2);

    public static Yearly newYearlyPattern(int n) {
        return Yearly$.MODULE$.apply(n, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly newYearlyPattern(DayOfYear doy) {
        return Yearly$.MODULE$.apply(1, doy);
    }

    public static Yearly newYearlyPattern(int n, DayOfYear doy) {
        return Yearly$.MODULE$.apply(n, doy);
    }

    public static Yearly newYearlyBackwardPattern(int n) {
        return Yearly$.MODULE$.apply(-n, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly newYearlyBackwardPattern(DayOfYear doy) {
        return Yearly$.MODULE$.apply(-1, doy);
    }

    public static Yearly newYearlyBackwardPattern(int n, DayOfYear doy) {
        return Yearly$.MODULE$.apply(-n, doy);
    }

}
