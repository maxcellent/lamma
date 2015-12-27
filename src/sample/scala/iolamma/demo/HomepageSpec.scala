package iolamma.demo

import io.lamma.Anchor.{OtherDate, PeriodEnd}
import io.lamma.DayOfMonth.LastDayOfMonth
import io.lamma.Selector.ModifiedFollowing
import io.lamma.Shifter.ShiftWorkingDays
import io.lamma.StubRulePeriodBuilder._
import io.lamma._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

/**
 * used in homepage
 */
@RunWith(classOf[JUnitRunner])
class HomepageSpec extends WordSpec with Matchers {

  // ============= basic date manipulation ===============
  "basic" in {
    Date(2014, 7, 2) + 3 should be(Date(2014, 7, 5))

    Date(2014, 7, 8) - Date(2014, 7, 2) should be(6)

    Date(2014, 7, 8) > Date(2014, 7, 2) should be(true)
  }

  // ============= advanced date generation ==============
  "advanced" in {
    val actual = Date(2014, 12, 1) to Date(2014, 12, 31) by 5 except Weekends
    val expected = List(Date(2014,12,1), Date(2014,12,11), Date(2014,12,16), Date(2014,12,26), Date(2014,12,31))
    actual.toList should be(expected)
  }

  // ============= Professional schedule generation ======
  "schedule generation for a 37m tenor FCN" in {
    // a real business calendar will be used in production
    val cal = Weekends
    // coupon date = end date of each generated period, modified following convention
    val couponDate = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(cal))
    // settlement date = coupon date + 2 working days with the same calendar
    val settlementDate = DateDef("SettlementDate", relativeTo = OtherDate("CouponDate"), shifter = ShiftWorkingDays(2, cal))

    val expectedPeriods = List(
      Period((2014, 3, 1) -> (2014, 8, 31)),
      Period((2014, 9, 1) -> (2015, 2, 28)),
      Period((2015, 3, 1) -> (2015, 8, 31)),
      Period((2015, 9, 1) -> (2016, 2, 29)),
      Period((2016, 3, 1) -> (2016, 8, 31)),
      Period((2016, 9, 1) -> (2017, 3, 31))
    )

    val expectedCouponDates = List(Date(2014, 8, 29), Date(2015, 2, 27),
      Date(2015, 8, 31), Date(2016, 2, 29), Date(2016, 8, 31), Date(2017, 3, 31))

    val expectedSettlementDates = List(Date(2014, 9, 2), Date(2015, 3, 3),
      Date(2015, 9, 2), Date(2016, 3, 2), Date(2016, 9, 2), Date(2017, 4, 4))

    val result = Schedule(
      start = Date(2014, 3, 1),   // issue date = 2014-03-01
      end = Date(2017, 3, 31),    // expiry date = 2017-03-31
      pattern = Monthly(6, LastDayOfMonth),  // recurring the last day of every 6 months
      periodBuilder = StubRulePeriodBuilder(endRule = LongEnd(270)),  // merge last stub if the merged period is no longer than 270 days
      dateDefs = couponDate :: settlementDate :: Nil   // generate coupon date and settlement date for each period
    )

    result.periods should be(expectedPeriods)
    result("CouponDate") should be(expectedCouponDates)
    result("SettlementDate") should be(expectedSettlementDates)
  }
}
