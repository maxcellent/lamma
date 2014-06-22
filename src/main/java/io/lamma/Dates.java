package io.lamma;

import scala.Option;

import static io.lamma.LammaConversion.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// TODO: test
public class Dates {

    private static Date date(String isoRepr) {
        if (isoRepr == null) {
            throw new IllegalArgumentException("From date must not be null. Expected: yyyy-MM-dd");
        }

        return Date$.MODULE$.apply(isoRepr);
    }

    private static Date date(Integer yyyy, Integer mm, Integer dd) {
        if (yyyy == null) {
            throw new IllegalArgumentException("yyyy should not be null");
        }
        if (mm == null) {
            throw new IllegalArgumentException("mm must not be null");
        }
        if (dd == null) {
            throw new IllegalArgumentException("dd must not be null");
        }

        return new Date(yyyy, mm, dd);
    }

    public static DatesFrom from(Date from) {
        if (from == null) {
            throw new IllegalArgumentException("From date must not be null");
        }

        return new DatesFrom(from);
    }

    public static DatesFrom from(Integer yyyy, Integer mm, Integer dd) {
        return from(date(yyyy, mm, dd));
    }

    public static DatesFrom from(String isoRepr) {
        return from(date(isoRepr));
    }

    public static class DatesFrom {

        private final Date from;

        private DatesFrom(Date from) {
            if (from == null) {
                throw new IllegalArgumentException("From date must not be null.");
            }

            this.from = from;
        }

        public Dates to(Date to) {
            return new Dates(from, to);
        }

        public Dates to(Integer yyyy, Integer mm, Integer dd) {
            return to(date(yyyy, mm, dd));
        }

        public Dates to(String isoRepr) {
            return to(date(isoRepr));
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DatesFrom datesFrom = (DatesFrom) o;

            if (!from.equals(datesFrom.from)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return from.hashCode();
        }

        @Override
        public String toString() {
            return "DatesFrom{" +
                    "from=" + from +
                    '}';
        }
    }

    private final Date from;

    private final Date to;

    private Duration duration = DateRangeBuilder$.MODULE$.apply$default$3();

    private HolidayRule holiday = DateRangeBuilder$.MODULE$.apply$default$4();

    private Locator loc;

    private List<Shifter> shifters = new LinkedList<Shifter>();

    private List<Selector> selectors = new LinkedList<Selector>();

    private DayOfMonth customDayOfMonth;

    private DayOfYear customDayOfYear;

    public Dates(Date from, Date to) {
        if (from == null) {
            throw new IllegalArgumentException("From date must not be null.");
        }
        if (to == null) {
            throw new IllegalArgumentException("To date must not be null.");
        }

        this.from = from;
        this.to = to;
    }

    public List<Date> build() {
        DateRangeBuilder builder = new DateRangeBuilder(from, to, duration, holiday, Option.apply(loc),
                scalaList(shifters), scalaList(selectors), Option.apply(customDayOfMonth), Option.apply(customDayOfYear));
        return builder.javaList();
    }

    public Dates by(Integer days) {
        return byDays(days);
    }

    public Dates byDay() {
        return byDays(1);
    }

    public Dates byDays(Integer days) {
        if (days == null) {
            throw new IllegalArgumentException("days must not be null.");
        }
        return byDuration(new DayDuration(days));
    }

    public Dates byWeek() {
        return byWeeks(1);
    }

    public Dates byWeeks(Integer weeks) {
        if (weeks == null) {
            throw new IllegalArgumentException("weeks must not be null.");
        }
        return byDuration(new WeekDuration(weeks));
    }

    public Dates byMonth() {
        return byMonths(1);
    }

    public Dates byMonths(Integer months) {
        if (months == null) {
            throw new IllegalArgumentException("months must not be null.");
        }
        return byDuration(new MonthDuration(months));
    }

    public Dates byYear() {
        return byYears(1);
    }

    public Dates byYears(Integer years) {
        if (years == null) {
            throw new IllegalArgumentException("years must not be null.");
        }
        return byDuration(new YearDuration(years));
    }

    private Dates byDuration(Duration newDuration) {
        this.duration = newDuration;
        return this;
    }

    public Dates except(HolidayRule holiday) {
        if (holiday == null) {
            throw new IllegalArgumentException("holiday must not be null.");
        }

        this.holiday = this.holiday.and(holiday);
        return this;
    }

    public Dates on(DayOfWeek dayOfWeek) {
        if (dayOfWeek == null) {
            throw new IllegalArgumentException("dayOfWeek must not be null.");
        }

        this.loc = DateRangeBuilder$.MODULE$.dow2Loc(dayOfWeek);
        return this;
    }

    public Dates on(Locator loc) {
        if (loc == null) {
            throw new IllegalArgumentException("Locator must not be null.");
        }

        this.loc = loc;
        return this;
    }

    public Dates on(Locators.Builder locBuilder) {
        if (locBuilder == null) {
            throw new IllegalArgumentException("LocatorBuilder must not be null.");
        }

        this.loc = locBuilder.build();
        return this;
    }

    public Dates on(DayOfMonth customDayOfMonth) {
        if (customDayOfMonth == null) {
            throw new IllegalArgumentException("DayOfMonth must not be null.");
        }

        this.customDayOfMonth = customDayOfMonth;
        return this;
    }

    public Dates on(DayOfYear customDayOfYear) {
        if (customDayOfYear == null) {
            throw new IllegalArgumentException("DayOfYear must not be null.");
        }

        this.customDayOfYear = customDayOfYear;
        return this;
    }

    public Dates shift(Integer n) {
        if (n == null) {
            throw new IllegalArgumentException("n must not be null.");
        }
        return shift(Shifter$.MODULE$.apply(n));
    }

    public Dates shift(Integer n, HolidayRule holiday) {
        if (n == null) {
            throw new IllegalArgumentException("n must not be null.");
        }
        if (holiday == null) {
            throw new IllegalArgumentException("Holiday must not be null.");
        }

        return shift(Shifter$.MODULE$.apply(n, holiday));
    }

    public Dates shift(Shifter shifter) {
        if (shifter == null) {
            throw new IllegalArgumentException("Shifter must not be null.");
        }

        this.shifters.add(shifter);
        return this;
    }

    public Dates forward(HolidayRule holiday) {
        if (holiday == null) {
            throw new IllegalArgumentException("holiday must not be null.");
        }
        return select(Selector.Forward$.MODULE$.apply(holiday));
    }

    public Dates backward(HolidayRule holiday) {
        if (holiday == null) {
            throw new IllegalArgumentException("holiday must not be null.");
        }
        return select(Selector.Backward$.MODULE$.apply(holiday));
    }

    public Dates modifiedFollowing(HolidayRule holiday) {
        if (holiday == null) {
            throw new IllegalArgumentException("holiday must not be null.");
        }
        return select(Selector.ModifiedFollowing$.MODULE$.apply(holiday));
    }

    public Dates modifiedPreceding(HolidayRule holiday) {
        if (holiday == null) {
            throw new IllegalArgumentException("holiday must not be null.");
        }
        return select(Selector.ModifiedPreceding$.MODULE$.apply(holiday));
    }

    public Dates select(Selector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("Selector must not be null.");
        }

        this.selectors.add(selector);
        return this;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public Duration getDuration() {
        return duration;
    }

    public HolidayRule getHoliday() {
        return holiday;
    }

    public Locator getLoc() {
        return loc;
    }

    public List<Shifter> getShifters() {
        return new ArrayList<Shifter>(shifters);
    }

    public List<Selector> getSelectors() {
        return new ArrayList<Selector>(selectors);
    }

    public DayOfMonth getCustomDayOfMonth() {
        return customDayOfMonth;
    }

    public DayOfYear getCustomDayOfYear() {
        return customDayOfYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dates dates = (Dates) o;

        if (customDayOfMonth != null ? !customDayOfMonth.equals(dates.customDayOfMonth) : dates.customDayOfMonth != null)
            return false;
        if (customDayOfYear != null ? !customDayOfYear.equals(dates.customDayOfYear) : dates.customDayOfYear != null)
            return false;
        if (!duration.equals(dates.duration)) return false;
        if (!from.equals(dates.from)) return false;
        if (!holiday.equals(dates.holiday)) return false;
        if (loc != null ? !loc.equals(dates.loc) : dates.loc != null) return false;
        if (!selectors.equals(dates.selectors)) return false;
        if (!shifters.equals(dates.shifters)) return false;
        if (!to.equals(dates.to)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + holiday.hashCode();
        result = 31 * result + (loc != null ? loc.hashCode() : 0);
        result = 31 * result + shifters.hashCode();
        result = 31 * result + selectors.hashCode();
        result = 31 * result + (customDayOfMonth != null ? customDayOfMonth.hashCode() : 0);
        result = 31 * result + (customDayOfYear != null ? customDayOfYear.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Dates{" +
                "from=" + from +
                ", to=" + to +
                ", duration=" + duration +
                ", holiday=" + holiday +
                ", loc=" + loc +
                ", shifters=" + shifters +
                ", selectors=" + selectors +
                ", customDayOfMonth=" + customDayOfMonth +
                ", customDayOfYear=" + customDayOfYear +
                '}';
    }
}
