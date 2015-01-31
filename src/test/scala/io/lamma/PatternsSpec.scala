package io.lamma

import io.lamma.DayOfMonth._
import io.lamma.DayOfWeek._
import io.lamma.DayOfYear._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}

import Patterns._

@RunWith(classOf[JUnitRunner])
class PatternsSpec extends FlatSpec with Matchers {

  // ========== recurrence pattern: day patterns ==============
  "everyDay" should "be the same as lamma version" in {
    everyDay should be(Daily(1))
  }

  "everyOtherDay" should "be the same as lamma version" in {
    everyOtherDay should be(Daily(2))
  }

  "daily" should "be the same as lamma version" in {
    daily(22) should be(Daily(22))
  }

  "dailyBackward" should "be the same as lamma version" in {
    dailyBackward(22) should be(Daily(-22))
  }

  // ========== recurrence pattern: week patterns ==============
  "everyWeek" should "be the same as lamma version" in {
    everyWeek should be(Weekly(1))
  }

  "everyOtherWeek" should "be the same as lamma version" in {
    everyOtherWeek should be(Weekly(2))
  }

  "weekly" should "be the same as lamma version" in {
    weekly(5) should be(Weekly(5))
    weekly(FRIDAY) should be(Weekly(1, Friday))
    weekly(2, TUESDAY) should be(Weekly(2, Tuesday))
  }

  "weeklyBackward" should "be the same as lamma version" in {
    weeklyBackward(5) should be(Weekly(-5))
    weeklyBackward(FRIDAY) should be(Weekly(-1, Friday))
    weeklyBackward(2, TUESDAY) should be(Weekly(-2, Tuesday))
  }

  // ========== recurrence pattern: month patterns ==============
  "everyMonth" should "be the same as lamma version" in {
    everyMonth should be(Monthly(1))
  }

  "everyOtherMonth" should "be the same as lamma version" in {
    everyOtherMonth should be(Monthly(2))
  }

  "monthly" should "be the same as lamma version" in {
    monthly(3) should be(Monthly(3))
    monthly(DayOfMonths.lastWeekday(FRIDAY)) should be(Monthly(1, LastWeekdayOfMonth(Friday)))
    monthly(5, DayOfMonths.firstDay()) should be(Monthly(5, FirstDayOfMonth))
  }

  "monthlyBackward" should "be the same as lamma version" in {
    monthlyBackward(3) should be(Monthly(-3))
    monthlyBackward(DayOfMonths.lastWeekday(FRIDAY)) should be(Monthly(-1, LastWeekdayOfMonth(Friday)))
    monthlyBackward(5, DayOfMonths.firstDay()) should be(Monthly(-5, FirstDayOfMonth))
  }

  // ========== recurrence pattern: year patterns ==============
  "everyYear" should "be the same as lamma version" in {
    everyYear should be(Yearly(1))
  }

  "everyOtherYear" should "be the same as lamma version" in {
    everyOtherYear should be(Yearly(2))
  }

  "yearly" should "be the same as lamma version" in {
    yearly(5) should be(Yearly(5))
    yearly(DayOfYears.firstDay()) should be(Yearly(1, FirstDayOfYear))
    yearly(2, DayOfYears.lastDay()) should be(Yearly(2, LastDayOfYear))
  }

  "yearlyBackward" should "be the same as lamma version" in {
    yearlyBackward(5) should be(Yearly(-5))
    yearlyBackward(DayOfYears.firstDay()) should be(Yearly(-1, FirstDayOfYear))
    yearlyBackward(2, DayOfYears.lastDay()) should be(Yearly(-2, LastDayOfYear))
  }

}
