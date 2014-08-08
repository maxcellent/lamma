package io.lamma;

import java.util.Set;

/**
 * utility class to construct holiday rules with Java<br>
 *
 * The original Scala version io.lamma.HolidayRule is not quite Java friendly.
 */
public class HolidayRules {

    public static HolidayRule noHoliday() {
        return NoHoliday$.MODULE$;
    }

    public static HolidayRule weekends() {
        return Weekends$.MODULE$;
    }

    public static HolidayRule simpleRule(Date... holidays) {
        scala.collection.immutable.Set<Date> scalaSet = JavaCollectionUtil.asScalaSeq(holidays).toSet();
        return new SimpleHolidayRule(scalaSet);
    }

    public static HolidayRule simpleRule(Set<Date> holidays) {
        scala.collection.immutable.Set<Date> scalaSet = JavaCollectionUtil.asScala(holidays);
        return new SimpleHolidayRule(scalaSet);
    }

    public static HolidayRule compositeRule(HolidayRule... rules) {
        scala.collection.Seq<HolidayRule> scalaSeq = JavaCollectionUtil.asScalaSeq(rules);
        return CompositeHolidayRule$.MODULE$.apply(scalaSeq);
    }
}
