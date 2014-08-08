package io.lamma;

import static io.lamma.LammaJavaImports.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class Schedule4jTest {

    @Test
    public void testScheduleGenerationWith3Params() {
        Date start = Dates.newDate(2014, 5, 1);
        Date end = Dates.newDate(2014, 8, 31);
        Pattern pattern = Patterns.newMonthlyPattern(DayOfMonths.lastDayOfMonth());

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 31)),
                new Period(Dates.newDate(2014, 6, 1), Dates.newDate(2014, 6, 30)),
                new Period(Dates.newDate(2014, 7, 1), Dates.newDate(2014, 7, 31)),
                new Period(Dates.newDate(2014, 8, 1), Dates.newDate(2014, 8, 31))
        );

        Schedule4j schedule = Schedule4j.schedule(start, end, pattern);
        assertThat(schedule.getPeriods(), is(expectedPeriods));
    }

    @Test
    public void testScheduleGenerationWith4Params() {
        Date start = Dates.newDate(2014, 5, 1);
        Date end = Dates.newDate(2014, 8, 31);
        Pattern pattern = Patterns.newMonthlyPattern(DayOfMonths.lastDayOfMonth());
        PeriodBuilder periodBuilder = stubRulePeriodBuilder();

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 31)),
                new Period(Dates.newDate(2014, 6, 1), Dates.newDate(2014, 6, 30)),
                new Period(Dates.newDate(2014, 7, 1), Dates.newDate(2014, 7, 31)),
                new Period(Dates.newDate(2014, 8, 1), Dates.newDate(2014, 8, 31))
        );

        Schedule4j schedule = Schedule4j.schedule(start, end, pattern, periodBuilder);
        assertThat(schedule.getPeriods(), is(expectedPeriods));
    }

    @Test
    public void testScheduleGenerationWith5Params() {
        Date start = Dates.newDate(2014, 5, 1);
        Date end = Dates.newDate(2014, 8, 31);
        Pattern pattern = Patterns.newMonthlyPattern(DayOfMonths.lastDayOfMonth());
        PeriodBuilder periodBuilder = stubRulePeriodBuilder();
        DateDef couponDate = dateDef("CouponDate", Selectors.newModifiedFollowingSelector());
        DateDef settlementDate = dateDef("SettlementDate", otherDate("CouponDate"), Shifters.newCalendarDaysShifter(2));

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 31)),
                new Period(Dates.newDate(2014, 6, 1), Dates.newDate(2014, 6, 30)),
                new Period(Dates.newDate(2014, 7, 1), Dates.newDate(2014, 7, 31)),
                new Period(Dates.newDate(2014, 8, 1), Dates.newDate(2014, 8, 31))
        );

        Schedule4j schedule = Schedule4j.schedule(start, end, pattern, periodBuilder, list(couponDate, settlementDate));
        assertThat(schedule.getPeriods(), is(expectedPeriods));
    }
}
