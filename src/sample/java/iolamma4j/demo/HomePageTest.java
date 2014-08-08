package iolamma4j.demo;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Lists;
import io.lamma.*;
import org.junit.Test;

import java.util.List;

/**
 * this test is written for home page sample
 */
public class HomePageTest {

    @Test
    public void scheduleGenerationForA37mTenorFCN() {
        // a real business holiday rule will be used in production
        HolidayRule cal = HolidayRules.WEEKENDS;
        // coupon date = end date of each generated period, modified following convention
        DateDef couponDate = DateDefs.of("CouponDate", Anchors.periodEnd(), Selectors.newModifiedFollowingSelector(cal));
        // settlement date = coupon date + 2 working days with the same calendar
        DateDef settlementDate = DateDefs.of("SettlementDate", Anchors.otherDate("CouponDate"), Shifters.newWorkingDaysShifter(2, cal));

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(Dates.newDate(2014, 3, 1), Dates.newDate(2014, 8, 31)),
                new Period(Dates.newDate(2014, 9, 1), Dates.newDate(2015, 2, 28)),
                new Period(Dates.newDate(2015, 3, 1), Dates.newDate(2015, 8, 31)),
                new Period(Dates.newDate(2015, 9, 1), Dates.newDate(2016, 2, 29)),
                new Period(Dates.newDate(2016, 3, 1), Dates.newDate(2016, 8, 31)),
                new Period(Dates.newDate(2016, 9, 1), Dates.newDate(2017, 3, 31))
        );

        List<Date> expectedCouponDates = Lists.newArrayList(
                Dates.newDate(2014, 8, 29),
                Dates.newDate(2015, 2, 27),
                Dates.newDate(2015, 8, 31),
                Dates.newDate(2016, 2, 29),
                Dates.newDate(2016, 8, 31),
                Dates.newDate(2017, 3, 31));

        List<Date> expectedSettlementDates = Lists.newArrayList(
                Dates.newDate(2014, 9, 2),
                Dates.newDate(2015, 3, 3),
                Dates.newDate(2015, 9, 2),
                Dates.newDate(2016, 3, 2),
                Dates.newDate(2016, 9, 2),
                Dates.newDate(2017, 4, 4));

        // we are calling Lamma4j this time, it's a Java wrapper
        Schedule4j result = Schedule4j.schedule(
                Dates.newDate(2014, 3, 1),              // issue date = 2014-03-01
                Dates.newDate(2017, 3, 31),             // expiry date = 2017-03-31
                Patterns.newMonthlyPattern(6, DayOfMonths.lastDayOfMonth()),// recurring the last day of every 6 months
                StubRulePeriodBuilders.of(StubRulePeriodBuilders.Rules.longEnd(270)),    // merge last stub if the merged period is no longer than 270 days
                Lists.newArrayList(couponDate, settlementDate));      // generate coupon date and settlement date for each period

        assertThat(result.getPeriods(), is(expectedPeriods));
        assertThat(result.get("CouponDate"), is(expectedCouponDates));
        assertThat(result.get("SettlementDate"), is(expectedSettlementDates));
    }

}
