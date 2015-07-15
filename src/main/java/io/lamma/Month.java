package io.lamma;

public enum Month {

    JANUARY(Quarter.Q1),

    FEBRUARY(Quarter.Q1),

    MARCH(Quarter.Q1),

    APRIL(Quarter.Q2),

    MAY(Quarter.Q2),

    JUNE(Quarter.Q2),

    JULY(Quarter.Q3),

    AUGUST(Quarter.Q3),

    SEPTEMBER(Quarter.Q3),

    OCTOBER(Quarter.Q4),

    NOVEMBER(Quarter.Q4),

    DECEMBER(Quarter.Q4);

    private final Quarter q;

    Month(Quarter q) {
        this.q = q;
    }

    public Quarter quarter() {
        return this.q;
    }

    public int n() {
        return this.ordinal() + 1;
    }

    public static Month of(int i) {
        return values()[i - 1];
    }

}
