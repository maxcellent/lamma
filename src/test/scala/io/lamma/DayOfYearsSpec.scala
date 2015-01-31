package io.lamma

import io.lamma.DayOfMonth._
import io.lamma.DayOfWeek._
import io.lamma.DayOfYear._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}

@RunWith(classOf[JUnitRunner])
class DayOfYearsSpec extends FlatSpec with Matchers {

  "firstDay" should "be the same as lamma version" in {
    DayOfYears.firstDay() should be(FirstDayOfYear)
  }

  "secondDay" should "be the same as lamma version" in {
    DayOfYears.secondDay() should be(SecondDayOfYear)
  }

  "nthDay" should "be the same as lamma version" in {
    DayOfYears.nthDay(243) should be(NthDayOfYear(243))
  }

  "lastDay" should "be the same as lamma version" in {
    DayOfYears.lastDay() should be(LastDayOfYear)
  }

  "firstWeekDay" should "be the same as lamma version" in {
    DayOfYears.firstWeekDay(FRIDAY) should be(FirstWeekDayOfYear(Friday))
  }

  "nthWeekday" should "be the same as lamma version" in {
    DayOfYears.nthWeekday(23, WEDNESDAY) should be(NthWeekdayOfYear(23, Wednesday))
  }

  "lastWeekday" should "be the same as lamma version" in {
    DayOfYears.lastWeekday(MONDAY) should be(LastWeekdayOfYear(Monday))
  }

  "firstMonth" should "be the same as lamma version" in {
    DayOfYears.firstMonth(DayOfMonths.lastDay()) should be(FirstMonthOfYear(LastDayOfMonth))
  }

  "nthMonth" should "be the same as lamma version" in {
    DayOfYears.nthMonth(Month.FEBRUARY, DayOfMonths.lastDay()) should be(NthMonthOfYear(February, LastDayOfMonth))
  }

  "lastMonth" should "be the same as lamma version" in {
    DayOfYears.lastMonth(DayOfMonths.firstDay()) should be(LastMonthOfYear(FirstDayOfMonth))
  }
}
