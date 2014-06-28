package io.lamma;

public enum Month {

    JANUARY,

    FEBRUARY,

    MARCH,

    APRIL,

    MAY,

    JUNE,

    JULY,

    AUGUST,

    SEPTEMBER,

    OCTOBER,

    NOVEMBER,

    DECEMBER;

    public int n() {
        return this.ordinal() + 1;
    }

    public static Month of(int i) {
        return values()[i - 1];
    }

}
