package io.lamma;

import scala.collection.JavaConverters;

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
        return new SimpleHolidayRule(LammaJavaImports.iterable(holidays).<Date>toSet());
    }

    public static HolidayRule newSimpleHolidayRule(Set<Date> holidays) {
        scala.collection.immutable.Set<Date> scalaSet = JavaConverters.asScalaSetConverter(holidays).asScala().toSet();
        return new SimpleHolidayRule(scalaSet);
    }

    public static HolidayRule newCompositeHolidayRule(HolidayRule... rules) {
        return CompositeHolidayRule$.MODULE$.apply(LammaJavaImports.iterable(rules).<HolidayRule>toSeq());
    }
}
