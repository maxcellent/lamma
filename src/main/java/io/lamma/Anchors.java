package io.lamma;

/**
 * utility class to construct Anchor with Java<br>
 *
 * The original Scala version io.lamma.Anchor is not quite Java friendly.
 */
public class Anchors {

    /**
     * @return Anchor relative to period end date
     */
    public static Anchor periodEnd() {
        return Anchor.PeriodEnd$.MODULE$;
    }

    /**
     * @return Anchor relative to period start date
     */
    public static Anchor periodStart() {
        return Anchor.PeriodStart$.MODULE$;
    }

    /**
     * @return Anchor relative to other date
     */
    public static Anchor otherDate(String name) {
        return new Anchor.OtherDate(name);
    }
}
