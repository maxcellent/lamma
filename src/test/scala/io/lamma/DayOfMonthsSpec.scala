package io.lamma

import DayOfMonths._
import io.lamma.DayOfMonth._
import io.lamma.DayOfWeek._
import org.scalatest.{Matchers, FlatSpec}

class DayOfMonthsSpec extends FlatSpec with Matchers {

  "firstDayOfMonth" should "be the same as lamma version" in {
    firstDayOfMonth() should be(FirstDayOfMonth)
  }

  "nthDayOfMonth" should "be the same as lamma version" in {
    nthDayOfMonth(12) should be(NthDayOfMonth(12))
  }

  "lastDayOfMonth" should "be the same as lamma version" in {
    lastDayOfMonth() should be(LastDayOfMonth)
  }

  "firstWeekdayOfMonth" should "be the same as lamma version" in {
    firstWeekdayOfMonth(FRIDAY) should be(FirstWeekdayOfMonth(Friday))
  }

  "nthWeekdayOfMonth" should "be the same as lamma version" in {
    nthWeekdayOfMonth(3, THURSDAY) should be(NthWeekdayOfMonth(3, Thursday))
  }

  "lastWeekdayOfMonth" should "be the same as lamma version" in {
    lastWeekdayOfMonth(MONDAY) should be(LastWeekdayOfMonth(Monday))
  }
}
