package io.lamma;

import static io.lamma.LammaConversion.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * this test is written for home page sample
 */
public class Lamma4jHomePageTest {

    @Test
    public void scheduleGenerationForA37mTenorFCN() {
        Calendar cal = weekendCalendar();
        DateDef couponDate = dateDef("CouponDate", periodEnd(), modifiedFollowing(cal));
        DateDef settlementDate = dateDef("SettlementDate", otherDate("CouponDate"), shiftWorkingDays(2, cal));

        List<Period> expectedPeriods = Lists.newArrayList(
                new Period(date(2014, 3, 1), date(2014, 8, 31)),
                new Period(date(2014, 9, 1), date(2015, 2, 28)),
                new Period(date(2015, 3, 1), date(2015, 8, 31)),
                new Period(date(2015, 9, 1), date(2016, 2, 29)),
                new Period(date(2016, 3, 1), date(2016, 8, 31)),
                new Period(date(2016, 9, 1), date(2017, 3, 31))
        );

        List<Date> expectedCouponDates = Lists.newArrayList(date(2014, 8, 29), date(2015, 2, 27),
                date(2015, 8, 31), date(2016, 2, 29), date(2016, 8, 31), date(2017, 3, 31));

        List<Date> expectedSettlementDates = Lists.newArrayList(date(2014, 9, 2), date(2015, 3, 3),
                date(2015, 9, 2), date(2016, 3, 2), date(2016, 9, 2), date(2017, 4, 4));

        Schedule4j result = Lamma4j.schedule(
                date(2014, 3, 1), date(2017, 3, 31),
                months(6, lastDayOfMonth()),
                stubRulePeriodBuilder(longEnd(270)),
                list(couponDate, settlementDate));

        assertThat(result.getPeriods(), is(expectedPeriods));
        assertThat(result.get("CouponDate"), is(expectedCouponDates));
        assertThat(result.get("SettlementDate"), is(expectedSettlementDates));
    }

}
