package io.lamma;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class Schedule4jTest {

    @Test
    public void testScheduleGenerationWith3Params() {
        Date start = Dates.newDate(2014, 5, 1);
        Date end = Dates.newDate(2014, 8, 31);
        Pattern pattern = Patterns.monthly(DayOfMonths.lastDay());

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 31)),
                new Period(Dates.newDate(2014, 6, 1), Dates.newDate(2014, 6, 30)),
                new Period(Dates.newDate(2014, 7, 1), Dates.newDate(2014, 7, 31)),
                new Period(Dates.newDate(2014, 8, 1), Dates.newDate(2014, 8, 31))
        );

        Schedule4j schedule = Schedule4j.schedule(start, end, pattern);
        assertEquals(schedule.getPeriods(), expectedPeriods);
    }

    @Test
    public void testScheduleGenerationWith4Params() {
        Date start = Dates.newDate(2014, 5, 1);
        Date end = Dates.newDate(2014, 8, 31);
        Pattern pattern = Patterns.monthly(DayOfMonths.lastDay());
        PeriodBuilder periodBuilder = StubRulePeriodBuilders.noStartNoEnd();

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 31)),
                new Period(Dates.newDate(2014, 6, 1), Dates.newDate(2014, 6, 30)),
                new Period(Dates.newDate(2014, 7, 1), Dates.newDate(2014, 7, 31)),
                new Period(Dates.newDate(2014, 8, 1), Dates.newDate(2014, 8, 31))
        );

        Schedule4j schedule = Schedule4j.schedule(start, end, pattern, periodBuilder);
        assertEquals(schedule.getPeriods(), expectedPeriods);
    }

    @Test
    public void testScheduleGenerationWith5Params() {
        Date start = Dates.newDate(2014, 5, 1);
        Date end = Dates.newDate(2014, 8, 31);
        Pattern pattern = Patterns.monthly(DayOfMonths.lastDay());
        PeriodBuilder periodBuilder = StubRulePeriodBuilders.noStartNoEnd();
        DateDef couponDate = DateDefs.of("CouponDate", Selectors.modifiedFollowing());
        DateDef settlementDate = DateDefs.of("SettlementDate", Anchors.otherDate("CouponDate"), Shifters.byCalendarDays(2));

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 31)),
                new Period(Dates.newDate(2014, 6, 1), Dates.newDate(2014, 6, 30)),
                new Period(Dates.newDate(2014, 7, 1), Dates.newDate(2014, 7, 31)),
                new Period(Dates.newDate(2014, 8, 1), Dates.newDate(2014, 8, 31))
        );

        Schedule4j schedule = Schedule4j.schedule(start, end, pattern, periodBuilder, Lists.newArrayList(couponDate, settlementDate));
        assertEquals(schedule.getPeriods(), expectedPeriods);
    }
}
