package io.lamma;

import java.util.Set;

/**
 * utility class to construct holiday rules with Java<br>
 *
 * The original Scala version io.lamma.HolidayRule is not quite Java friendly.
 */
public class HolidayRules {

    public static final HolidayRule NO_HOLIDAY = NoHoliday$.MODULE$;

    public static final HolidayRule WEEKENDS = Weekends$.MODULE$;

    public static HolidayRule newSimpleHolidayRule(Date... holidays) {
        scala.collection.immutable.Set<Date> scalaSet = JavaCollectionUtil.asScalaSeq(holidays).toSet();
        return new SimpleHolidayRule(scalaSet);
    }

    public static HolidayRule newSimpleHolidayRule(Set<Date> holidays) {
        scala.collection.immutable.Set<Date> scalaSet = JavaCollectionUtil.asScala(holidays);
        return new SimpleHolidayRule(scalaSet);
    }

    public static HolidayRule newCompositeHolidayRule(HolidayRule... rules) {
        scala.collection.Seq<HolidayRule> scalaSeq = JavaCollectionUtil.asScalaSeq(rules);
        return CompositeHolidayRule$.MODULE$.apply(scalaSeq);
    }
}
