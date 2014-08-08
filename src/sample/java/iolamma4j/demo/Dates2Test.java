package iolamma4j.demo;

import com.google.common.collect.Lists;
import io.lamma.*;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static io.lamma.DayOfWeek.*;
import static io.lamma.Month.*;

/**
 * this class covers all java code used in Tutorial 2: Advanced Date Generation
 *      it was Tutorial 2: Advanced Sequence Generation in 1.x
 */
public class Dates2Test {

    @Test
    public void testWeekday() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014, 5, 13),
                Dates.newDate(2014, 6, 3),
                Dates.newDate(2014, 6, 24)
        );
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 7, 1).byWeeks(3).on(TUESDAY).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testPositionOfMonth() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014, 5, 10),
                Dates.newDate(2014, 6, 10),
                Dates.newDate(2014, 7, 10)
        );
        List<Date> actual = Dates.from(2014, 5, 1).to(2014, 7, 30).byMonth().on(Locators.nthDay(10)).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testAWeekdayWithInAMonth() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014, 5, 9),
                Dates.newDate(2014, 7, 11),
                Dates.newDate(2014, 9, 12)
        );
        List<Date> actual = Dates.from(2014, 5, 1).to(2014, 9, 30).byMonths(2).on(Locators.nth(2, FRIDAY)).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testPositionOfYear() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014, 12, 30),
                Dates.newDate(2015, 12, 29),
                Dates.newDate(2016, 12, 27)
        );
        List<Date> actual = Dates.from(2014, 1, 1).to(2016, 12, 31).byYear().on(Locators.last(TUESDAY)).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testComplicatedExample() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2010,2,19),
                Dates.newDate(2013,2,15),
                Dates.newDate(2016,2,19),
                Dates.newDate(2019,2,15)
        );
        List<Date> actual = Dates.from(2010, 1, 1).to(2019, 12, 31).byYears(3).on(Locators.nth(3, FRIDAY).of(FEBRUARY)).build();
        assertThat(actual, is(expected));
    }

    /**
     * match first day in Feb, 3rd day for other months
     */
    static class MyPositionOfMonth implements DayOfMonth {

        @Override
        public boolean isValidDOM(Date d) {
            return d.month() == FEBRUARY ? d.dd() == 1 : d.dd() == 3;
        }
    }

    @Test
    public void testCustomPosition() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014, 1, 3),
                Dates.newDate(2014, 2, 1),
                Dates.newDate(2014, 3, 3));
        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 3, 31).byMonth().on(new MyPositionOfMonth()).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testShiftor() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014,1,29),
                Dates.newDate(2014,2,26),
                Dates.newDate(2014,3,29)
        );
        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 3, 31).byMonth().on(Locators.lastDay()).shift(-2).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testShiftByWorkingDays() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014,1,29),
                Dates.newDate(2014,2,26),
                Dates.newDate(2014,3,27));
        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 3, 31).byMonth().on(Locators.lastDay()).shift(-2, HolidayRules.weekends()).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testSelector() {
        // further select result date after the date is shifted
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2014,1,29),
                Dates.newDate(2014,2,26),
                Dates.newDate(2014,3,31));   // last date is different
        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 3, 31).byMonth().on(Locators.lastDay()).shift(-2).forward(HolidayRules.weekends()).build();
        assertThat(actual, is(expected));
    }

    // ========= edge cases ======
    @Test
    public void testRecurrencePatternTooLongForward() {
        List<Date> expected = Lists.newArrayList(Dates.newDate(2014, 1, 1));
        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 3, 31).byMonths(6).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testRecurrencePatternTooLongBackward() {
        List<Date> expected = Lists.newArrayList(Dates.newDate(2014, 3, 31));
        List<Date> actual = Dates.from(2014, 3, 31).to(2014, 1, 1).byMonths(-6).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testSameFromAndToDate() {
        List<Date> expected = Lists.newArrayList(Dates.newDate(2014, 1, 1));
        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 1, 1).byMonth().build();
        assertThat(actual, is(expected));
    }
}
