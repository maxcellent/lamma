package io.lamma

import io.lamma.DayOfMonth._
import io.lamma.DayOfWeek._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class DayOfMonthsSpec extends FlatSpec with Matchers {

  "firstDay" should "be the same as lamma version" in {
    DayOfMonths.firstDay() should be(FirstDayOfMonth)
  }

  "nthDay" should "be the same as lamma version" in {
    DayOfMonths.nthDay(12) should be(NthDayOfMonth(12))
  }

  "lastDay" should "be the same as lamma version" in {
    DayOfMonths.lastDay() should be(LastDayOfMonth)
  }

  "firstWeekday" should "be the same as lamma version" in {
    DayOfMonths.firstWeekday(FRIDAY) should be(FirstWeekdayOfMonth(Friday))
  }

  "nthWeekday" should "be the same as lamma version" in {
    DayOfMonths.nthWeekday(3, THURSDAY) should be(NthWeekdayOfMonth(3, Thursday))
  }

  "lastWeekday" should "be the same as lamma version" in {
    DayOfMonths.lastWeekday(MONDAY) should be(LastWeekdayOfMonth(Monday))
  }
}
