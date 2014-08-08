package io.lamma

import io.lamma.DayOfMonth._
import io.lamma.DayOfWeek._
import io.lamma.DayOfYear._
import org.scalatest.{Matchers, FlatSpec}

import Patterns._

class PatternsSpec extends FlatSpec with Matchers {

  // ========== recurrence pattern: day patterns ==============
  "EVERY_DAY" should "be the same as lamma version" in {
    EVERY_DAY should be(Daily(1))
  }

  "EVERY_OTHER_DAY" should "be the same as lamma version" in {
    EVERY_OTHER_DAY should be(Daily(2))
  }

  "newDailyPattern" should "be the same as lamma version" in {
    newDailyPattern(22) should be(Daily(22))
  }

  "newDailyBackwardPattern" should "be the same as lamma version" in {
    newDailyBackwardPattern(22) should be(Daily(-22))
  }

  // ========== recurrence pattern: week patterns ==============
  "EVERY_WEEK" should "be the same as lamma version" in {
    EVERY_WEEK should be(Weekly(1))
  }

  "EVERY_OTHER_WEEK" should "be the same as lamma version" in {
    EVERY_OTHER_WEEK should be(Weekly(2))
  }

  "newWeeklyPattern" should "be the same as lamma version" in {
    newWeeklyPattern(5) should be(Weekly(5))
    newWeeklyPattern(FRIDAY) should be(Weekly(1, Friday))
    newWeeklyPattern(2, TUESDAY) should be(Weekly(2, Tuesday))
  }

  "newWeeklyBackwardPattern" should "be the same as lamma version" in {
    newWeeklyBackwardPattern(5) should be(Weekly(-5))
    newWeeklyBackwardPattern(FRIDAY) should be(Weekly(-1, Friday))
    newWeeklyBackwardPattern(2, TUESDAY) should be(Weekly(-2, Tuesday))
  }

  // ========== recurrence pattern: month patterns ==============
  "EVERY_MONTH" should "be the same as lamma version" in {
    EVERY_MONTH should be(Monthly(1))
  }

  "EVERY_OTHER_MONTH" should "be the same as lamma version" in {
    EVERY_OTHER_MONTH should be(Monthly(2))
  }

  "newMonthlyPattern" should "be the same as lamma version" in {
    newMonthlyPattern(3) should be(Monthly(3))
    newMonthlyPattern(DayOfMonths.lastWeekdayOfMonth(FRIDAY)) should be(Monthly(1, LastWeekdayOfMonth(Friday)))
    newMonthlyPattern(5, DayOfMonths.firstDayOfMonth()) should be(Monthly(5, FirstDayOfMonth))
  }

  "newMonthlyBackwardPattern" should "be the same as lamma version" in {
    newMonthlyBackwardPattern(3) should be(Monthly(-3))
    newMonthlyBackwardPattern(DayOfMonths.lastWeekdayOfMonth(FRIDAY)) should be(Monthly(-1, LastWeekdayOfMonth(Friday)))
    newMonthlyBackwardPattern(5, DayOfMonths.firstDayOfMonth()) should be(Monthly(-5, FirstDayOfMonth))
  }

  // ========== recurrence pattern: year patterns ==============
  "EVERY_YEAR" should "be the same as lamma version" in {
    EVERY_YEAR should be(Yearly(1))
  }

  "EVERY_OTHER_YEAR" should "be the same as lamma version" in {
    EVERY_OTHER_YEAR should be(Yearly(2))
  }

  "newYearlyPattern" should "be the same as lamma version" in {
    newYearlyPattern(5) should be(Yearly(5))
    newYearlyPattern(DayOfYears.firstDayOfYear()) should be(Yearly(1, FirstDayOfYear))
    newYearlyPattern(2, DayOfYears.lastDayOfYear()) should be(Yearly(2, LastDayOfYear))
  }

  "newYearlyBackwardPattern" should "be the same as lamma version" in {
    newYearlyBackwardPattern(5) should be(Yearly(-5))
    newYearlyBackwardPattern(DayOfYears.firstDayOfYear()) should be(Yearly(-1, FirstDayOfYear))
    newYearlyBackwardPattern(2, DayOfYears.lastDayOfYear()) should be(Yearly(-2, LastDayOfYear))
  }

}
