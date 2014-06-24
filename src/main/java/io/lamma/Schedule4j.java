package io.lamma;

import static io.lamma.LammaJavaImports.javaList;
import static io.lamma.LammaJavaImports.scalaList;

import java.util.List;

public class Schedule4j {

    public static Schedule4j schedule(Date start, Date end, Pattern pattern) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, Schedule$.MODULE$.apply$default$4(), Schedule$.MODULE$.apply$default$5(), Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    public static Schedule4j schedule(Date start, Date end, Pattern pattern, java.util.List<DateDef> dateDefs) {
        return schedule(start, end, pattern, Schedule$.MODULE$.apply$default$4(), scalaList(dateDefs), Schedule$.MODULE$.apply$default$6());
    }

    public static Schedule4j schedule(Date start, Date end, Pattern pattern, scala.collection.immutable.List<DateDef> dateDefs) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, Schedule$.MODULE$.apply$default$4(), dateDefs, Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, periodBuilder, Schedule$.MODULE$.apply$default$5(), Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, java.util.List<DateDef> dateDefs) {
        return schedule(start, end, pattern, periodBuilder, scalaList(dateDefs), Schedule$.MODULE$.apply$default$6());
    }

    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, scala.collection.immutable.List<DateDef> dateDefs) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, periodBuilder, dateDefs, Schedule$.MODULE$.apply$default$6());
        return new Schedule4j(schedule);
    }

    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, java.util.List<DateDef> dateDefs, Direction direction) {
        return schedule(start, end, pattern, periodBuilder, scalaList(dateDefs), direction);
    }

    public static Schedule4j schedule(Date start, Date end, Pattern pattern, PeriodBuilder periodBuilder, scala.collection.immutable.List<DateDef> dateDefs, Direction direction) {
        Schedule schedule = Schedule$.MODULE$.apply(start, end, pattern, periodBuilder, dateDefs, direction);
        return new Schedule4j(schedule);
    }

    private final Schedule schedule;

    public Schedule4j(Schedule schedule) {
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public List<Period> getPeriods() {
        return javaList(schedule.periods());
    }

    public String toPrintableString() {
        return schedule.printableString();
    }

    public List<Date> get(String dateDefName) {
        return javaList(schedule.apply(dateDefName));
    }

    @Override
    public String toString() {
        return "Schedule4j{" +
                "schedule=" + schedule +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Schedule4j that = (Schedule4j) o;

        if (schedule != null ? !schedule.equals(that.schedule) : that.schedule != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return schedule != null ? schedule.hashCode() : 0;
    }
}
