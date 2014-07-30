package iolamma.demo;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import io.lamma.HolidayRule;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static io.lamma.LammaJavaImports.*;

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
    public void generateByStep() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 5, 12), new Date(2014, 5, 14));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 5, 15).by(2).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void generateByNegativeStep() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 20), new Date(2014, 5, 18), new Date(2014, 5, 16));
        List<Date> actual = Dates.from("2014-05-20").to("2014-05-15").by(-2).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateDatesByWeek() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 5, 17), new Date(2014, 5, 24));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 5, 24).byWeek().build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateDatesByMonth() {
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
    public void testGenerateDatesByYear() {
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
    public void testFilterOurWeekends() {
        // import static io.lamma.LammaConversion.*;
        List<Date> expected = Lists.newArrayList(date(2015, 10, 8), date(2015, 10, 9), date(2015, 10, 12));
        List<Date> actual = Dates.from(2015, 10, 8).to(2015, 10, 12).except(weekends()).build();
        assertThat(actual, is(expected));
    }

    @Test
    public void testFilterOurWeekendsAndOtherHoliday() {
        // import static io.lamma.LammaConversion.*;

        List<Date> expected = Lists.newArrayList(date(2015, 12, 23), date(2015, 12, 24), date(2015, 12, 29), date(2015, 12, 30));

        HolidayRule ukHoliday2015 = simpleHolidayRule(
                date(2015, 1, 1), date(2015, 4, 3), date(2015, 4, 6),
                date(2015, 5, 4), date(2015, 5, 25), date(2015, 8, 31),
                date(2015, 12, 25), date(2015, 12, 28)
        );

        // these two are identical
        List<Date> alt1 = Dates.from(2015, 12, 23).to(2015, 12, 30).except(weekends()).except(ukHoliday2015).build();
        List<Date> alt2 = Dates.from(2015, 12, 23).to(2015, 12, 30).except(weekends().and(ukHoliday2015)).build();

        assertThat(alt1, is(expected));
        assertThat(alt2, is(expected));
    }

    // not in tutorial
    @Test
    public void testForwardWithFraction() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 5, 10), new Date(2014, 8, 10));
        List<Date> actual = Dates.from(2014, 5, 10).to(2014, 10, 20).byMonths(3).build();
        assertThat(actual, is(expected));
    }

    // not in tutorial
    @Test
    public void testBackwardWithFraction() {
        List<Date> expected = Lists.newArrayList(new Date(2014, 10, 20), new Date(2014, 7, 20));
        List<Date> actual = Dates.from(2014, 10, 20).to(2014, 5, 10).byMonths(-3).build();
        assertThat(actual, is(expected));
    }
}
