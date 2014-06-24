package io.lamma.demo;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.DateDef;
import io.lamma.Schedule4j;
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
        List<Date> expectedCouponDates = Lists.newArrayList(date(2015, 6, 30), date(2015, 12, 31), date(2016, 6, 30), date(2016, 12, 30));
        List<Date> expectedSettlementDates = Lists.newArrayList(date(2015, 7, 2), date(2016, 1, 4), date(2016, 7, 4), date(2017, 1, 3));

        DateDef couponDate = dateDef("CouponDate", periodEnd(), modifiedFollowing(weekends()));
        DateDef settlementDate = dateDef("settlementDate", otherDate("CouponDate"), shiftWorkingDays(2, weekends()));

        Schedule4j result = Schedule4j.schedule(date(2015, 1, 1), date(2016, 12, 31), months(6, lastDayOfMonth()), list(couponDate, settlementDate));

        assertThat(result.get("CouponDate"), is(expectedCouponDates));
        assertThat(result.get("settlementDate"), is(expectedSettlementDates));
    }

    @Test
    public void testDefaultStubHandling() {
        List<Date> expectedCouponDates = Lists.newArrayList(date(2015, 6, 30), date(2015, 12, 31), date(2016, 6, 30), date(2016, 12, 30), date(2017, 1, 31));
                
        DateDef couponDate = dateDef("CouponDate", periodEnd(), modifiedFollowing(weekends()));
        Schedule4j result = Schedule4j.schedule(date(2015, 1, 1), date(2017, 1, 31), months(6, lastDayOfMonth()), list(couponDate));

        assertThat(result.get("CouponDate"), is(expectedCouponDates));
    }

    @Test
    public void testStubRules() {
        List<Date> expectedCouponDates = Lists.newArrayList(date(2015, 6, 30), date(2015, 12, 31), date(2016, 6, 30), date(2017, 1, 31));

        DateDef couponDate = dateDef("CouponDate", periodEnd(), modifiedFollowing(weekends()));
        Schedule4j result = Schedule4j.schedule(date(2015, 1, 1), date(2017, 1, 31), months(6, lastDayOfMonth()), stubRulePeriodBuilder(longEnd(270)), list(couponDate));

        assertThat(result.get("CouponDate"), is(expectedCouponDates));
    }
}
