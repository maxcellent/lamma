package io.lamma.demo;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Lamma4j;
import org.junit.Test;

import java.util.List;

// always import these two lines when using Java, this will make our life a lot easier
import static io.lamma.LammaConversion.*;
import static io.lamma.LammaConst.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * this class covers all java code used in Tutorial 1: Basic Sequence Generation
 */
public class Sequence1Test {

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
}
