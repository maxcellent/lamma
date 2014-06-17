package io.lamma.demo

import org.scalatest.{Matchers, WordSpec}
import io.lamma._
import io.lamma.Anchor.PeriodEnd
import io.lamma.Recurrence._
import io.lamma.DayOfMonth.LastDayOfMonth
import io.lamma.StubRulePeriodBuilder.LongEnd
import io.lamma.Anchor.OtherDate
import io.lamma.Shifter.ShiftWorkingDays
import io.lamma.Selector.ModifiedFollowing

/**
 * this class covers all scala code used in Tutorial 3: Advanced Schedule Generation
 */
class ScheduleSpec extends WordSpec with Matchers {

  // this one is skipped on Tutorial
  "let's start with a coupon date only" in {
    val expected = List(Date(2015, 6, 30)) :: List(Date(2015, 12, 31)) :: List(Date(2016, 6, 30)) :: List(Date(2016, 12, 30)) :: Nil  // 2016-12-31 is Saturday

    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends)) :: Nil
    Lamma.schedule(Date(2015, 1, 1), Date(2016, 12, 31), Months(6, LastDayOfMonth), dateDefs = dateDefs).generatedDates should be(expected)
  }

  "now settlement delay is 2 days" in {
    val expected = List(
      List(Date(2015, 6, 30), Date(2015, 7, 2)),
      List(Date(2015, 12, 31), Date(2016, 1, 4)), // 2016-01-02 is Saturday, 2016-01-03 is Sunday
      List(Date(2016, 6, 30), Date(2016, 7, 4)),  // 2016-07-02 is Saturday, 2016-07-03 is Sunday
      List(Date(2016, 12, 30), Date(2017, 1, 3))  // 2016-12-31 is Saturday, 2017-01-01 is Sunday
    )

    val dateDefs = List(
      DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends)),
      DateDef("SettlementDate", relativeTo = OtherDate("CouponDate"), shifter = ShiftWorkingDays(2, Weekends))
    )
    Lamma.schedule(Date(2015, 1, 1), Date(2016, 12, 31), Months(6, LastDayOfMonth), dateDefs = dateDefs).generatedDates should be(expected)
  }

  "how about the schedule is fractional? for example, end day is 1 month later, then an extra row will be generated" in {
    val expected = List(Date(2015, 6, 30)) :: List(Date(2015, 12, 31)) :: List(Date(2016, 6, 30)) :: List(Date(2016, 12, 30)) :: List(Date(2017, 1, 31)) :: Nil

    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends)) :: Nil
    Lamma.schedule(Date(2015, 1, 1), Date(2017, 1, 31), Months(6, LastDayOfMonth), dateDefs = dateDefs).generatedDates should be(expected)
  }

  "let's merge it by applying a long end stub rule" in {
    val expected = List(Date(2015, 6, 30)) :: List(Date(2015, 12, 31)) :: List(Date(2016, 6, 30)) :: List(Date(2017, 1, 31)) :: Nil
    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(Weekends)) :: Nil
    val schedule = Lamma.schedule(Date(2015, 1, 1), Date(2017, 1, 31), Months(6, LastDayOfMonth), StubRulePeriodBuilder(endRule = LongEnd(270)), dateDefs = dateDefs)
    schedule.generatedDates should be(expected)
  }

  // this part is skipped in the tutorial, very similar as sequence edge cases
  // schedule generation edge cases
  "single row with end day will be generated when the duration between start and end day is too short" in {
    val expected = List(Date(2015, 3, 30)) :: Nil
    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil

    Lamma.schedule(Date(2015, 1, 1), Date(2015, 3, 30), Months(6), dateDefs = dateDefs).generatedDates should be(expected)
    Lamma.schedule(Date(2015, 1, 1), Date(2015, 3, 30), MonthsBackward(6), dateDefs = dateDefs).generatedDates should be(expected)
  }

  "and if start date is end date" in {
    val expected = List(Date(2015, 1, 1)) :: Nil
    val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil
    Lamma.schedule(Date(2015, 1, 1), Date(2015, 1, 1), EveryMonth, dateDefs = dateDefs).generatedDates should be(expected)
  }

  "throw exception when input start date is later than end date" in {
    intercept[IllegalArgumentException] {
      Lamma.schedule(Date(2015, 1, 1), Date(2014, 3, 30), EveryMonth)
    }
  }
}
