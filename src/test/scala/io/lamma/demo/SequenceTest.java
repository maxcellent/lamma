package io.lamma.demo;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Lamma4j;
import io.lamma.PositionOfMonth;
import org.junit.Test;

import java.util.List;

// always import these two lines when using Java, this will make our life a lot easier
import static io.lamma.LammaConversion.*;
import static io.lamma.LammaConst.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SequenceTest {

    @Test
    public void testGenerateDateSequence() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 5, 11), date(2014, 5, 12));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 5, 12));
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateSequenceByWeek() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 5, 17), date(2014, 5, 24));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 5, 24), everyWeek());
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateSequenceByMonth() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 6, 10), date(2014, 7, 10));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 7, 10), everyMonth());
        assertThat(actual, is(expected));
    }

    @Test
    public void testMonthEndHandling() {
        List<Date> expected = Lists.newArrayList(date(2014, 1, 31), date(2014, 2, 28), date(2014, 3, 31), date(2014, 4, 30));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 31), date(2014, 4, 30), everyMonth());
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateSequenceByYear() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2015, 5, 10), date(2016, 5, 10));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2016, 5, 10), everyYear());
        assertThat(actual, is(expected));
    }

    @Test
    public void testLeapYearHandling() {
        List<Date> expected = Lists.newArrayList(date(2012, 2, 29), date(2013, 2, 28), date(2014, 2, 28), date(2015, 2, 28), date(2016, 2, 29));
        List<Date> actual = Lamma4j.sequence(date(2012, 2, 29), date(2016, 2, 29), everyYear());
        assertThat(actual, is(expected));
    }

    @Test
    public void testMoreRecurrencePatternDays() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 5, 13), date(2014, 5, 16), date(2014, 5, 19));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 5, 19), days(3));
        assertThat(actual, is(expected));
    }

    @Test
    public void testMoreRecurrencePatternMonths() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 8, 10), date(2014, 11, 10));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 11, 10), months(3));
        assertThat(actual, is(expected));
    }

    @Test
    public void testSelectAllLeapDays() {
        List<Date> expected = Lists.newArrayList(date(2012, 2, 29), date(2016, 2, 29), date(2020, 2, 29));
        List<Date> actual = Lamma4j.sequence(date(2012, 2, 29), date(2020, 2, 29), years(4));
        assertThat(actual, is(expected));
    }

    @Test
    public void testWorkingDays() {
        List<Date> expected = Lists.newArrayList(date(2015, 10, 5), date(2015, 10, 12), date(2015, 10, 19));
        List<Date> actual = Lamma4j.sequence(date(2015, 10, 5), date(2015, 10, 19), workingDays(5, weekendCalendar()));
        assertThat(actual, is(expected));
    }

    @Test
    public void testBackward() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 5, 11), date(2014, 5, 12));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 5, 12), daysBackward(1));
        assertThat(actual, is(expected));
    }

    @Test
    public void testForwardWithFraction() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 8, 10));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 10, 20), months(3));
        assertThat(actual, is(expected));
    }

    @Test
    public void testBackwardWithFraction() {
        List<Date> expected = Lists.newArrayList(date(2014, 7, 20), date(2014, 10, 20));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 10, 20), monthsBackward(3));
        assertThat(actual, is(expected));
    }

    @Test
    public void testWeekday() {
        // a list of every 3 Tuesday from 2014-05-05 to 2014-07-01
        List<Date> expected = Lists.newArrayList(date(2014, 5, 13), date(2014, 6, 3), date(2014, 6, 24));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 10), date(2014, 7, 1), weeks(3, TUESDAY));
        assertThat(actual, is(expected));
    }

    @Test
    public void testPositionOfMonth() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 10), date(2014, 6, 10), date(2014, 7, 10));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 1), date(2014, 7, 30), months(1, nthDayOfMonth(10)));
        assertThat(actual, is(expected));
    }

    @Test
    public void testAWeekdayWithInAMonth() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 9), date(2014, 7, 11), date(2014, 9, 12));
        List<Date> actual = Lamma4j.sequence(date(2014, 5, 1), date(2014, 9, 30), months(2, nthWeekdayOfMonth(2, FRIDAY)));
        assertThat(actual, is(expected));
    }

    /**
     * match first day in Feb, 3rd day for other months
     */
    static class MyPositionOfMonth implements PositionOfMonth {

        @Override
        public boolean isValidDOM(Date d) {
            return d.month() == FEBRUARY ? d.dd() == 1 : d.dd() == 3;
        }
    }

    @Test
    public void testCustomPosition() {
        List<Date> expected = Lists.newArrayList(date(2014, 1, 3), date(2014, 2, 1), date(2014, 3, 3));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 30), months(1, new MyPositionOfMonth()));
        assertThat(actual, is(expected));
    }

    @Test
    public void testPositionOfYear() {
        List<Date> expected = Lists.newArrayList(date(2014, 12, 30), date(2015, 12, 29), date(2016, 12, 27));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2016, 12, 31), years(1, lastWeekdayOfYear(TUESDAY)));
        assertThat(actual, is(expected));
    }

    @Test
    public void testComplicatedExample() {
        //  find all 3rd Friday of February for every 3 years in 2010s
        List<Date> expected = Lists.newArrayList(date(2010,2,19), date(2013,2,15), date(2016,2,19), date(2019,2,15));
        List<Date> actual = Lamma4j.sequence(date(2010, 1, 1), date(2019, 12, 31), years(3, nthMonthOfYear(FEBRUARY, nthWeekdayOfMonth(3, FRIDAY))));
        assertThat(actual, is(expected));
    }

    @Test
    public void testShiftor() {
        // I want 3rd last day of every month
        List<Date> expected = Lists.newArrayList(date(2014,1,29), date(2014,2,26), date(2014,3,29));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(1, lastDayOfMonth()), shiftCalendarDays(-2));
        assertThat(actual, is(expected));
    }

    @Test
    public void testShiftByWorkingDays() {
        List<Date> expected = Lists.newArrayList(date(2014,1,29), date(2014,2,26), date(2014,3,27));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(1, lastDayOfMonth()), shiftWorkingDays(-2, weekendCalendar()));
        assertThat(actual, is(expected));
    }

    @Test
    public void testSelector() {
        // further select result date after the date is shifted
        List<Date> expected = Lists.newArrayList(date(2014,1,29), date(2014,2,26), date(2014,3,31));   // last date is different
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(1, lastDayOfMonth()), shiftCalendarDays(-2), forward(weekendCalendar()));
        assertThat(actual, is(expected));
    }

    // ========= edge cases ======

    @Test
    public void testSameFromAndToDate() {
        List<Date> expected = Lists.newArrayList(date(2014, 1, 1));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 1, 1), monthsBackward(6));
        assertThat(actual, is(expected));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExceptionCase() {
        Lamma4j.sequence(date(2014, 1, 1), date(2013, 12, 31));
    }

    @Test
    public void testRecurrencePatternTooLongForward() {
        List<Date> expected = Lists.newArrayList(date(2014, 1, 1));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(6));
        assertThat(actual, is(expected));
    }

    @Test
    public void testRecurrencePatternTooLongBackward() {
        List<Date> expected = Lists.newArrayList(date(2014, 3, 31));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), monthsBackward(6));
        assertThat(actual, is(expected));
    }
}
