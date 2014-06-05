package io.lamma;

import static io.lamma.LammaConversion.javaList;

import java.util.List;

public class Schedule4j {

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
