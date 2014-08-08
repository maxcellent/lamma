package io.lamma;

/**
 * utility class to construct Selector with Java<br>
 *
 * The original Scala version io.lamma.Selector is not quite Java friendly.
 */
public class Selectors {

    /**
     * select current date
     */
    public static final Selector SAME_DAY_SELECTOR = Selector.SameDay$.MODULE$;

    /**
     * new forward selector with HolidayRules.WEEKEND
     */
    public static Selector newForwardSelector() {
        return new Selector.Forward(Selector.Forward$.MODULE$.apply$default$1());
    }

    /**
     * new forward selector with specified holiday rule
     */
    public static Selector newForwardSelector(HolidayRule rule) {
        return new Selector.Forward(rule);
    }

    /**
     * new backward selector with HolidayRules.WEEKEND
     */
    public static Selector newBackwardSelector() {
        return new Selector.Backward(Selector.Backward$.MODULE$.apply$default$1());
    }

    /**
     * new backward selector with specified holiday rule
     */
    public static Selector newBackwardSelector(HolidayRule rule) {
        return new Selector.Backward(rule);
    }

    /**
     * new modified following selector with HolidayRules.WEEKEND
     */
    public static Selector newModifiedFollowingSelector() {
        return new Selector.ModifiedFollowing(Selector.ModifiedFollowing$.MODULE$.apply$default$1());
    }

    /**
     * new modified following selector with specified holiday rule
     */
    public static Selector newModifiedFollowingSelector(HolidayRule rule) {
        return new Selector.ModifiedFollowing(rule);
    }

    /**
     * new modified preceding selector with HolidayRules.WEEKEND
     */
    public static Selector newModifiedPrecedingSelector() {
        return new Selector.ModifiedPreceding(Selector.ModifiedPreceding$.MODULE$.apply$default$1());
    }

    /**
     * new modified preceding selector with specified holiday rule
     */
    public static Selector newModifiedPrecedingSelector(HolidayRule rule) {
        return new Selector.ModifiedPreceding(rule);
    }

}
