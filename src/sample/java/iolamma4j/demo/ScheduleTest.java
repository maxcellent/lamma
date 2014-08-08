package iolamma4j.demo;

import com.google.common.collect.Lists;
import io.lamma.*;
import org.junit.Test;

import java.util.List;

// always import this line when using Java, this will make our life a lot easier
import static io.lamma.LammaJavaImports.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * this class covers all java code used in Tutorial 3: Advanced Schedule Generation
 */
public class ScheduleTest {

    @Test
    public void testTwoDatesSchedule() {
        List<Date> expectedCouponDates = Lists.newArrayList(
                Dates.newDate(2015, 6, 30),
                Dates.newDate(2015, 12, 31),
                Dates.newDate(2016, 6, 30),
                Dates.newDate(2016, 12, 30));
        List<Date> expectedSettlementDates = Lists.newArrayList(
                Dates.newDate(2015, 7, 2),
                Dates.newDate(2016, 1, 4),
                Dates.newDate(2016, 7, 4),
                Dates.newDate(2017, 1, 3));

        DateDef couponDate = dateDef("CouponDate", periodEnd(), Selectors.newModifiedFollowingSelector(HolidayRules.WEEKENDS));
        DateDef settlementDate = dateDef("settlementDate", otherDate("CouponDate"), Shifters.newWorkingDaysShifter(2, HolidayRules.WEEKENDS));

        Schedule4j result = Schedule4j.schedule(
                Dates.newDate(2015, 1, 1),
                Dates.newDate(2016, 12, 31),
                months(6, DayOfMonths.lastDayOfMonth()),
                list(couponDate, settlementDate));

        assertThat(result.get("CouponDate"), is(expectedCouponDates));
        assertThat(result.get("settlementDate"), is(expectedSettlementDates));
    }

    @Test
    public void testDefaultStubHandling() {
        List<Date> expectedCouponDates = Lists.newArrayList(
                Dates.newDate(2015, 6, 30),
                Dates.newDate(2015, 12, 31),
                Dates.newDate(2016, 6, 30),
                Dates.newDate(2016, 12, 30),
                Dates.newDate(2017, 1, 31));
                
        DateDef couponDate = dateDef("CouponDate", periodEnd(), Selectors.newModifiedFollowingSelector(HolidayRules.WEEKENDS));
        Schedule4j result = Schedule4j.schedule(
                Dates.newDate(2015, 1, 1),
                Dates.newDate(2017, 1, 31), months(6, DayOfMonths.lastDayOfMonth()), list(couponDate));

        assertThat(result.get("CouponDate"), is(expectedCouponDates));
    }

    @Test
    public void testStubRules() {
        List<Date> expectedCouponDates = Lists.newArrayList(
                Dates.newDate(2015, 6, 30),
                Dates.newDate(2015, 12, 31),
                Dates.newDate(2016, 6, 30),
                Dates.newDate(2017, 1, 31));

        DateDef couponDate = dateDef("CouponDate", periodEnd(), Selectors.newModifiedFollowingSelector(HolidayRules.WEEKENDS));
        Schedule4j result = Schedule4j.schedule(
                Dates.newDate(2015, 1, 1),
                Dates.newDate(2017, 1, 31),
                months(6, DayOfMonths.lastDayOfMonth()),
                stubRulePeriodBuilder(longEnd(270)), list(couponDate));

        assertThat(result.get("CouponDate"), is(expectedCouponDates));
    }
}
