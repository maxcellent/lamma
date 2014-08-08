package io.lamma

import io.lamma.DayOfMonth._
import io.lamma.DayOfWeek._
import io.lamma.DayOfYear._
import io.lamma.DayOfYears._
import org.scalatest.{Matchers, FlatSpec}

class DayOfYearsSpec extends FlatSpec with Matchers {

  "firstDayOfYear" should "be the same as lamma version" in {
    firstDayOfYear() should be(FirstDayOfYear)
  }

  "secondDayOfYear" should "be the same as lamma version" in {
    secondDayOfYear() should be(SecondDayOfYear)
  }

  "nthDayOfYear" should "be the same as lamma version" in {
    nthDayOfYear(243) should be(NthDayOfYear(243))
  }

  "lastDayOfYear" should "be the same as lamma version" in {
    lastDayOfYear() should be(LastDayOfYear)
  }

  "firstWeekDayOfYear" should "be the same as lamma version" in {
    firstWeekDayOfYear(FRIDAY) should be(FirstWeekDayOfYear(Friday))
  }

  "nthWeekdayOfYear" should "be the same as lamma version" in {
    nthWeekdayOfYear(23, WEDNESDAY) should be(NthWeekdayOfYear(23, Wednesday))
  }

  "lastWeekdayOfYear" should "be the same as lamma version" in {
    lastWeekdayOfYear(MONDAY) should be(LastWeekdayOfYear(Monday))
  }

  "firstMonthOfYear" should "be the same as lamma version" in {
    firstMonthOfYear(DayOfMonths.lastDayOfMonth()) should be(FirstMonthOfYear(LastDayOfMonth))
  }

  "nthMonthOfYear" should "be the same as lamma version" in {
    nthMonthOfYear(Month.FEBRUARY, DayOfMonths.lastDayOfMonth()) should be(NthMonthOfYear(February, LastDayOfMonth))
  }

  "lastMonthOfYear" should "be the same as lamma version" in {
    lastMonthOfYear(DayOfMonths.firstDayOfMonth()) should be(LastMonthOfYear(FirstDayOfMonth))
  }
}
