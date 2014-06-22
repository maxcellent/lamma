package io.lamma.demo;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * this class covers all java code used in Tutorial 1: Basic Date Generation
 *  it was Tutorial 1: Basic Sequence Generation in 1.x
 */
public class Dates1Test {

    @Test
    public void testGenerateDates() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 5, 11), new Date(2014, 5, 12));
        List<Date> actual = Dates.from(new Date(2014, 5, 10)).to(new Date(2014, 5, 12)).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void specifyWithNumber() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 5, 11), new Date(2014, 5, 12));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 5, 12).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void specifyWithISOString() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 5, 11), new Date(2014, 5, 12));
        List<Date> actual = Dates.from("2014-05-10").to("2014-05-12").build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateSequenceByWeek() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 5, 17), new Date(2014, 5, 24));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 5, 24).byWeek().build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateSequenceByMonth() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 6, 10), new Date(2014, 7, 10));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 7, 10).byMonth().build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testMonthEndHandling() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 1, 31), new Date(2014, 2, 28), new Date(2014, 3, 31), new Date(2014, 4, 30));
        List<Date> actual = Dates.from(2014, 1, 31).to(2014, 4, 30).byMonth().build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateSequenceByYear() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2015, 5, 10), new Date(2016, 5, 10));
        List<Date> actual = Dates.from(2014, 5, 10).to(2016, 5, 10).byYear().build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testLeapYearHandling() {
        List<Date> expected = Lists.newArrayList(new Date(2012, 2, 29), new Date(2013, 2, 28), new Date(2014, 2, 28), new Date(2015, 2, 28), new Date(2016, 2, 29));
        List<Date> actual = Dates.from(2012, 2, 29).to(2016, 2, 29).byYear().build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testMoreRecurrencePatternDays() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 5, 13), new Date(2014, 5, 16), new Date(2014, 5, 19));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 5, 19).byDays(3).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testMoreRecurrencePatternMonths() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 8, 10), new Date(2014, 11, 10));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 11, 10).byMonths(3).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testSelectAllLeapDays() {
        List<Date> expected = Lists.newArrayList(new Date(2012, 2, 29), new Date(2016, 2, 29), new Date(2020, 2, 29));
        List<Date> actual = Dates.from(2012, 2, 29).to(2020, 2, 29).byYears(4).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testBackward() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 12), new Date(2014, 5, 11), new Date(2014, 5, 10));
        List<Date> actual = Dates.from(2014, 5, 12).to(2014, 5, 10).byDays(-1).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testForwardWithFraction() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 8, 10));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 10, 20).byMonths(3).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testBackwardWithFraction() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 10, 20), new Date(2014, 7, 20));
        List<Date> actual = Dates.from(2014, 10, 20).to(2014, 5, 10).byMonths(-3).build();
        assertThat(actual, is(expected));
    }
}
