package iolamma4j.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.Lists;
import io.lamma.*;
import org.junit.Test;

import java.util.List;

/**
 * this test is written for home page sample
 */
public class HomePageTest {

    // ============= Basic date manipulation ==============
    @Test
    public void basic() {
        assertEquals(new Date(2014, 7, 5), new Date(2014, 7, 2).plusDays(3));

        assertEquals(6, new Date(2014, 7, 8).minus(new Date(2014, 7, 2)));

        assertTrue(new Date(2014, 7, 8).isAfter(new Date(2014, 7, 2)));
    }

    // ============= advanced date generation ==============
    @Test
    public void advanced() {
        List<Date> expected = Lists.newArrayList(
                new Date(2014,12,1),
                new Date(2014,12,11),
                new Date(2014,12,16),
                new Date(2014,12,26),
                new Date(2014,12,31)
        );

        List<Date> actual = Dates.from(2014, 12, 1).to(2014, 12, 31).byDays(5).except(HolidayRules.weekends()).build();

        assertEquals(expected, actual);
    }

    // ============= Professional schedule generation ======

    @Test
    public void scheduleGenerationForA37mTenorFCN() {
        // a real business holiday rule will be used in production
        HolidayRule cal = HolidayRules.weekends();
        // coupon date = end date of each generated period, modified following convention
        DateDef couponDate = DateDefs.of("CouponDate", Anchors.periodEnd(), Selectors.modifiedFollowing(cal));
        // settlement date = coupon date + 2 working days with the same calendar
        DateDef settlementDate = DateDefs.of("SettlementDate", Anchors.otherDate("CouponDate"), Shifters.byWorkingDays(2, cal));

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

        Schedule4j result = Schedule4j.schedule(
                Dates.newDate(2014, 3, 1),              // issue date = 2014-03-01
                Dates.newDate(2017, 3, 31),             // expiry date = 2017-03-31
                Patterns.monthly(6, DayOfMonths.lastDay()),// recurring the last day of every 6 months
                StubRulePeriodBuilders.of(StubRulePeriodBuilders.Rules.longEnd(270)),    // merge last stub if the merged period is no longer than 270 days
                Lists.newArrayList(couponDate, settlementDate));      // generate coupon date and settlement date for each period

        assertEquals(result.getPeriods(), expectedPeriods);
        assertEquals(result.get("CouponDate"), expectedCouponDates);
        assertEquals(result.get("SettlementDate"), expectedSettlementDates);
    }

}
