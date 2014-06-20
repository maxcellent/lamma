package io.lamma.demo;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Lamma4j;
import io.lamma.DayOfMonth;
import org.junit.Test;

import java.util.List;

import static io.lamma.LammaConversion.*;
import static io.lamma.LammaConst.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * this class covers all java code used in Tutorial 2: Advanced Sequence Generation
 */
public class Sequence2Test {

    @Test
    public void testWeekday() {
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

    @Test
    public void testPositionOfYear() {
        List<Date> expected = Lists.newArrayList(date(2014, 12, 30), date(2015, 12, 29), date(2016, 12, 27));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2016, 12, 31), years(1, lastWeekdayOfYear(TUESDAY)));
        assertThat(actual, is(expected));
    }

    @Test
    public void testComplicatedExample() {
        List<Date> expected = Lists.newArrayList(date(2010,2,19), date(2013,2,15), date(2016,2,19), date(2019,2,15));
        List<Date> actual = Lamma4j.sequence(date(2010, 1, 1), date(2019, 12, 31), years(3, nthMonthOfYear(FEBRUARY, nthWeekdayOfMonth(3, FRIDAY))));
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
        List<Date> expected = Lists.newArrayList(date(2014, 1, 3), date(2014, 2, 1), date(2014, 3, 3));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(1, new MyPositionOfMonth()));
        assertThat(actual, is(expected));
    }

    @Test
    public void testShiftor() {
        List<Date> expected = Lists.newArrayList(date(2014,1,29), date(2014,2,26), date(2014,3,29));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(1, lastDayOfMonth()), shiftCalendarDays(-2));
        assertThat(actual, is(expected));
    }

    @Test
    public void testShiftByWorkingDays() {
        List<Date> expected = Lists.newArrayList(date(2014,1,29), date(2014,2,26), date(2014,3,27));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(1, lastDayOfMonth()), shiftWorkingDays(-2, weekends()));
        assertThat(actual, is(expected));
    }

    @Test
    public void testSelector() {
        // further select result date after the date is shifted
        List<Date> expected = Lists.newArrayList(date(2014,1,29), date(2014,2,26), date(2014,3,31));   // last date is different
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(1, lastDayOfMonth()), shiftCalendarDays(-2), forward(weekends()));
        assertThat(actual, is(expected));
    }

    // ========= edge cases ======

    @Test
    public void testRecurrencePatternTooLongForward() {
        List<Date> expected = Lists.newArrayList(date(2014, 1, 1));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 3, 31), months(6));
        assertThat(actual, is(expected));
    }

    @Test
    public void testRecurrencePatternTooLongBackward() {
        List<Date> expected = Lists.newArrayList(date(2014, 3, 31));
        List<Date> actual = Lamma4j.sequence(date(2014, 3, 31), date(2014, 1, 1), monthsBackward(6));
        assertThat(actual, is(expected));
    }

    @Test
    public void testSameFromAndToDate() {
        List<Date> expected = Lists.newArrayList(date(2014, 1, 1));
        List<Date> actual = Lamma4j.sequence(date(2014, 1, 1), date(2014, 1, 1), months(6));
        assertThat(actual, is(expected));
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void testExceptionCase() {
//        Lamma4j.sequence(date(2014, 1, 1), date(2013, 12, 31));
//    }
}
