package io.lamma;

/**
 * utility class to construct Shifter with Java<br>
 *
 * The original Scala version io.lamma.Shifter is not quite Java friendly.
 */
public class Shifters {

    public static final Shifter NO_SHIFT = Shifter.NoShift$.MODULE$;

    public static Shifter newCalendarDaysShifter(int days) {
        return new Shifter.ShiftCalendarDays(days);
    }

    public static Shifter newWorkingDaysShifter(int days) {
        return new Shifter.ShiftWorkingDays(days, Shifter.ShiftWorkingDays$.MODULE$.apply$default$2());
    }

    public static Shifter newWorkingDaysShifter(int days, HolidayRule rule) {
        return new Shifter.ShiftWorkingDays(days, rule);
    }
}
