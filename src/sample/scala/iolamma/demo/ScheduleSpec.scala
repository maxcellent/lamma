package io.lamma.demo

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}
import io.lamma._
import io.lamma.Anchor.PeriodEnd
import io.lamma.StubRulePeriodBuilder.LongEnd
import io.lamma.Shifter.ShiftWorkingDays
import io.lamma.Selector.ModifiedFollowing

/**
 * this class covers all scala code used in Tutorial 3: Advanced Schedule Generation
 */
@RunWith(classOf[JUnitRunner])
class ScheduleSpec extends WordSpec with Matchers {

  // this one is skipped on Tutorial
  "let's start with a coupon date only" in {
    val expected = List(Date(2015, 6, 30)) :: List(Date(2015, 12, 31)) :: List(Date(2016, 6, 30)) :: List(Date(2016, 12, 30)) :: Nil  // 2016-12-31 is Saturday

    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends)) :: Nil
    Schedule(Date(2015, 1, 1), Date(2016, 12, 31), (6 months) on lastDay, dateDefs = dateDefs).generatedDates should be(expected)
  }

  "now settlement delay is 2 days" in {
    // 2016-01-02 is Saturday, 2016-01-03 is Sunday
    // 2016-07-02 is Saturday, 2016-07-03 is Sunday
    // 2016-12-31 is Saturday, 2017-01-01 is Sunday
    val expectedCouponDates = Date(2015, 6, 30) :: Date(2015, 12, 31) :: Date(2016, 6, 30) :: Date(2016, 12, 30) :: Nil
    val expectedSettlementDates = Date(2015, 7, 2) :: Date(2016, 1, 4) :: Date(2016, 7, 4) :: Date(2017, 1, 3) :: Nil

    val couponDate = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends))
    val settlementDate = DateDef("SettlementDate", relativeTo = Anchor(couponDate.name), shifter = ShiftWorkingDays(2, Weekends))
    val dateDefs = couponDate :: settlementDate :: Nil
    val schedule = Schedule(Date(2015, 1, 1), Date(2016, 12, 31), (6 months) on lastDay, dateDefs = dateDefs)

    schedule("CouponDate") should be(expectedCouponDates)
    schedule(settlementDate.name) should be(expectedSettlementDates)
  }

  "how about the schedule is fractional? for example, end day is 1 month later, then an extra row will be generated containing the fraction" in {
    val expected = Date(2015, 6, 30) :: Date(2015, 12, 31) :: Date(2016, 6, 30) :: Date(2016, 12, 30) :: Date(2017, 1, 31) :: Nil

    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends)) :: Nil
    val schedule = Schedule(Date(2015, 1, 1), Date(2017, 1, 31), (6 months) on lastDay, dateDefs = dateDefs)
    schedule("CouponDate") should be(expected)
  }

  "let's merge it by applying a long end stub rule with period builder" in {
    val expected = List(Date(2015, 6, 30)) :: List(Date(2015, 12, 31)) :: List(Date(2016, 6, 30)) :: List(Date(2017, 1, 31)) :: Nil
    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends)) :: Nil
    val periodBuilder = StubRulePeriodBuilder(endRule = LongEnd(270))
    val schedule = Schedule(Date(2015, 1, 1), Date(2017, 1, 31), (6 months) on lastDay, periodBuilder, dateDefs = dateDefs)
    schedule.generatedDates should be(expected)
  }

  "generate schedule in backward direction from the end date" in {
    val expectedCouponDates = Date(2015, 2, 6) :: Date(2015, 7, 3) :: Date(2015, 12, 4) :: Date(2015, 12, 31) :: Nil
    val expectedSettlementDates = Date(2015, 2, 10) :: Date(2015, 7, 7) :: Date(2015, 12, 8) :: Date(2016, 1, 4) :: Nil

    val couponDate = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends))
    val settlementDate = DateDef("SettlementDate", relativeTo = Anchor(couponDate.name), shifter = ShiftWorkingDays(2, Weekends))
    val dateDefs = couponDate :: settlementDate :: Nil
    val schedule = Schedule(Date(2015, 1, 1), Date(2015, 12, 31), (-5 months) on (1 st Friday), dateDefs = dateDefs, direction = Direction.BACKWARD)

    schedule("CouponDate") should be(expectedCouponDates)
    schedule(settlementDate.name) should be(expectedSettlementDates)
  }

  // this part is skipped in the tutorial, very similar as sequence edge cases
  // schedule generation edge cases
  "single row with end day will be generated when the duration between start and end day is too short" in {
    val expected = Date(2015, 3, 30) :: Nil
    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil

    Schedule(Date(2015, 1, 1), Date(2015, 3, 30), 6 months, dateDefs = dateDefs)("CouponDate") should be(expected)
    Schedule(Date(2015, 1, 1), Date(2015, 3, 30), -6 months, dateDefs = dateDefs)("CouponDate") should be(expected)
  }

  "and if start date is end date" in {
    val expected = List(Date(2015, 1, 1)) :: Nil
    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil
    Schedule(Date(2015, 1, 1), Date(2015, 1, 1), Monthly(1), dateDefs = dateDefs).generatedDates should be(expected)
  }

  "throw exception when input start date is later than end date" in {
    intercept[IllegalArgumentException] {
      Schedule(Date(2015, 1, 1), Date(2014, 3, 30), Monthly(1))
    }
  }
}
