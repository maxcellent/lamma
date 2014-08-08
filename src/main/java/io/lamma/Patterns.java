package io.lamma;

/**
 * utility class to construct Recurrence Patterns with Java<br>
 *
 * The original Scala version io.lamma.Pattern is not quite Java friendly.
 */
public class Patterns {

    // =========== Daily Patterns ===========
    /**
     * alias of daily(1)
     */
    public static Daily everyDay() {
        return daily(1);
    }

    /**
     * alias of daily(2)
     */
    public static final Daily everyOtherDay() {
        return daily(2);
    }

    public static Daily daily(int days) {
        return new Daily(days);
    }

    public static Daily dailyBackward(int days) {
        return daily(-days);
    }

    // =========== Weekly Patterns ===========
    /**
     * alias of weeks(1)
     */
    public static Weekly everyWeek() {
        return weekly(1);
    }

    /**
     * alias of weeks(2)
     */
    public static Weekly everyOtherWeek() {
        return weekly(2);
    }

    public static Weekly weekly(int weeks) {
        return Weekly$.MODULE$.apply(weeks, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly weekly(DayOfWeek dow) {
        return Weekly$.MODULE$.apply(1, dow);
    }

    public static Weekly weekly(int weeks, DayOfWeek dow) {
        return Weekly$.MODULE$.apply(weeks, dow);
    }

    public static Weekly weeklyBackward(int weeks) {
        return Weekly$.MODULE$.apply(- weeks, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly weeklyBackward(DayOfWeek dow) {
        return Weekly$.MODULE$.apply(-1, dow);
    }

    public static Weekly weeklyBackward(int weeks, DayOfWeek dow) {
        return Weekly$.MODULE$.apply(- weeks, dow);
    }

    // =========== Monthly Patterns ===========
    /**
     * alias of monthly(1)
     */
    public static Monthly everyMonth() {
        return monthly(1);
    }

    /**
     * alias of monthly(2)
     */
    public static Monthly everyOtherMonth() {
        return monthly(2);
    }

    public static Monthly monthly(int n) {
        return Monthly$.MODULE$.apply(n, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly monthly(DayOfMonth dom) {
        return Monthly$.MODULE$.apply(1, dom);
    }

    public static Monthly monthly(int n, DayOfMonth dom) {
        return Monthly$.MODULE$.apply(n, dom);
    }

    public static Monthly monthlyBackward(int n) {
        return Monthly$.MODULE$.apply(-n, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly monthlyBackward(DayOfMonth dom) {
        return Monthly$.MODULE$.apply(-1, dom);
    }

    public static Monthly monthlyBackward(int n, DayOfMonth dom) {
        return Monthly$.MODULE$.apply(-n, dom);
    }

    // =========== Yearly Patterns ===========
    /**
     * alias of yearly(1)
     */
    public static Yearly everyYear() {
        return yearly(1);
    }

    /**
     * alias of yearly(2)
     */
    public static Yearly everyOtherYear() {
        return yearly(2);
    }

    public static Yearly yearly(int n) {
        return Yearly$.MODULE$.apply(n, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly yearly(DayOfYear doy) {
        return Yearly$.MODULE$.apply(1, doy);
    }

    public static Yearly yearly(int n, DayOfYear doy) {
        return Yearly$.MODULE$.apply(n, doy);
    }

    public static Yearly yearlyBackward(int n) {
        return Yearly$.MODULE$.apply(-n, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly yearlyBackward(DayOfYear doy) {
        return Yearly$.MODULE$.apply(-1, doy);
    }

    public static Yearly yearlyBackward(int n, DayOfYear doy) {
        return Yearly$.MODULE$.apply(-n, doy);
    }

}
