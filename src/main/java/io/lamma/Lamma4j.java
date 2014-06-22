package io.lamma;

import static io.lamma.LammaConversion.*;

import scala.collection.immutable.List;
import scala.collection.JavaConverters;

public class Lamma4j {

    @Deprecated
    public static java.util.List<Date> sequence(Date from, Date to) {
        DateRange range = new DateRange(from, to, DateRange$.MODULE$.apply$default$3(), DateRange$.MODULE$.apply$default$4(), DateRange$.MODULE$.apply$default$5(), DateRange$.MODULE$.apply$default$6());
        return range.javaList();
    }

    @Deprecated
    public static java.util.List<Date> sequence(Date from, Date to, Pattern pattern) {
        DateRange range = new DateRange(from, to, pattern, DateRange$.MODULE$.apply$default$4(), DateRange$.MODULE$.apply$default$5(), DateRange$.MODULE$.apply$default$6());
        return range.javaList();
    }

    @Deprecated
    public static java.util.List<Date> sequence(Date from, Date to, Pattern pattern, Shifter shifter) {
        DateRange range = new DateRange(from, to, pattern, DateRange$.MODULE$.apply$default$4(), list(shifter), DateRange$.MODULE$.apply$default$6());
        return range.javaList();
    }

    @Deprecated
    public static java.util.List<Date> sequence(Date from, Date to, Pattern pattern, Shifter shifter, Selector selector) {
        DateRange range = new DateRange(from, to, pattern, DateRange$.MODULE$.apply$default$4(), list(shifter), list(selector));
        return range.javaList();
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, Schedule$.MODULE$.apply$default$4(), Schedule$.MODULE$.apply$default$5(), Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern, java.util.List<DateDef> dateDefs) {
        return schedule(start, end, pattern, Schedule$.MODULE$.apply$default$4(), JavaConverters.asScalaBufferConverter(dateDefs).asScala().toList(), Schedule$.MODULE$.apply$default$6());
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern, List<DateDef> dateDefs) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, Schedule$.MODULE$.apply$default$4(), dateDefs, Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, periodBuilder, Schedule$.MODULE$.apply$default$5(), Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, java.util.List<DateDef> dateDefs) {
        return schedule(start, end, pattern, periodBuilder, JavaConverters.asScalaBufferConverter(dateDefs).asScala().toList(), Schedule$.MODULE$.apply$default$6());
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, List<DateDef> dateDefs) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, periodBuilder, dateDefs, Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, java.util.List<DateDef> dateDefs, Direction direction) {
        return schedule(start, end, pattern, periodBuilder, JavaConverters.asScalaBufferConverter(dateDefs).asScala().toList(), direction);
    }

    @Deprecated
    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, List<DateDef> dateDefs, Direction direction) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, periodBuilder, dateDefs, direction);
        return new Schedule4j(schedule);
    }
}
