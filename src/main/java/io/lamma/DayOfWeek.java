package io.lamma;

public enum DayOfWeek {

    MONDAY,

    TUESDAY,

    WEDNESDAY,

    THURSDAY,

    FRIDAY,

    SATURDAY,

    SUNDAY;

    /**
     * nth day of a week, start from MONDAY. <br/>
     *  MONDAY => 1
     *  TUESDAY => 2
     *  WEDNESDAY => 2
     *  THURSDAY => 2
     *  FRIDAY => 2
     *  SATURDAY => 2
     *  SUNDAY => 2
     */
    public int n() {
        return this.ordinal() + 1;
    }

    public static DayOfWeek of(int i) {
        return values()[i - 1];
    }
}
