package io.lamma;

import static io.lamma.LammaConversion.*;

import scala.collection.immutable.List;
import scala.collection.JavaConverters;

public class Lamma4j {

    public static java.util.List<Date> sequence(Date from, Date to) {
        List<Date> result = Lamma.sequence(from, to, Lamma.sequence$default$3(), Lamma.sequence$default$4(), Lamma.sequence$default$5(), Lamma.sequence$default$6());
        return javaList(result);
    }

    public static java.util.List<Date> sequence(Date from, Date to, Recurrence pattern) {
        List<Date> result = Lamma.sequence(from, to, pattern, Lamma.sequence$default$4(), Lamma.sequence$default$5(), Lamma.sequence$default$6());
        return javaList(result);
    }

    public static java.util.List<Date> sequence(Date from, Date to, Recurrence pattern, Shifter shifter) {
        List<Date> result = Lamma.sequence(from, to, pattern, shifter, Lamma.sequence$default$5(), Lamma.sequence$default$6());
        return javaList(result);
    }

    public static java.util.List<Date> sequence(Date from, Date to, Recurrence pattern, Shifter shifter, Selector selector) {
        List<Date> result = Lamma.sequence(from, to, pattern, shifter, selector, Lamma.sequence$default$6());
        return javaList(result);
    }

    public static java.util.List<Date> sequence(Date from, Date to, Recurrence pattern, Shifter shifter, Selector selector, Calendar calendar) {
        List<Date> result = Lamma.sequence(from, to, pattern, shifter, selector, calendar);
        return javaList(result);
    }

    public static Schedule4j schedule(Date start, Date end, Recurrence pattern) {
        Schedule schedule = Lamma.schedule(start, end, pattern, Lamma.schedule$default$4(), Lamma.schedule$default$5());
        return new Schedule4j(schedule);
    }

    public static Schedule4j schedule(Date start, Date end, Recurrence pattern, java.util.List<DateDef> dateDefs) {
        return schedule(start, end, pattern, Lamma.schedule$default$4(), JavaConverters.asScalaIterableConverter(dateDefs).asScala().toList());
    }

    public static Schedule4j schedule(Date start, Date end, Recurrence pattern, List<DateDef> dateDefs) {
        Schedule schedule = Lamma.schedule(start, end, pattern, Lamma.schedule$default$4(), dateDefs);
        return new Schedule4j(schedule);
    }

    public static Schedule4j schedule(Date start, Date end, Recurrence pattern, PeriodBuilder periodBuilder) {
        Schedule schedule = Lamma.schedule(start, end, pattern, periodBuilder, Lamma.schedule$default$5());
        return new Schedule4j(schedule);
    }

    public static Schedule4j schedule(Date start, Date end, Recurrence pattern, PeriodBuilder periodBuilder, java.util.List<DateDef> dateDefs) {
        return schedule(start, end, pattern, periodBuilder, JavaConverters.asScalaIterableConverter(dateDefs).asScala().toList());
    }

    public static Schedule4j schedule(Date start, Date end, Recurrence pattern, PeriodBuilder periodBuilder, List<DateDef> dateDefs) {
        Schedule schedule = Lamma.schedule(start, end, pattern, periodBuilder, dateDefs);
        return new Schedule4j(schedule);
    }
}
