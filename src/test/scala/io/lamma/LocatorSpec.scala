package io.lamma

import io.lamma.DayOfMonth.{LastWeekdayOfMonth, NthWeekdayOfMonth, LastDayOfMonth, NthDayOfMonth}
import io.lamma.DayOfYear.{NthMonthOfYear, NthWeekdayOfYear, LastDayOfYear, NthDayOfYear}
import io.lamma.Month.February
import org.scalatest.{WordSpec, Matchers}

class LocatorSpec extends WordSpec with Matchers {

  "OrdinalLocator" should {
    "generate nth day of week" in {
      val loc = 2 nd day
      loc.dow should be(Tuesday)
    }

    "generate nth day of month" in {
      val loc = 2 nd day
      loc.dom should be(NthDayOfMonth(2))
    }

    "generate nth day of year" in {
      val loc = 2 nd day
      loc.doy should be(NthDayOfYear(2))
    }

    "generate last day of week" in {
      lastDay.dow should be(Sunday)
    }

    "generate last day of month" in {
      lastDay.dom should be(LastDayOfMonth)
    }

    "generate last day of year" in {
      lastDay.doy should be(LastDayOfYear)
    }
  }

  "OrdinalWeekLocator" should {
    "generate nth weekday of month" in {
      val loc = 2 nd Friday
      loc.dom should be(NthWeekdayOfMonth(2, Friday))
    }

    "generate last weekday of month" in {
      lastFriday.dom should be(LastWeekdayOfMonth(Friday))
    }

    "generate nth weekday of year" in {
      val loc = 2 nd Friday
      loc.doy should be(NthWeekdayOfYear(2, Friday))
    }

    "generate last weekday of year" in {
      lastFriday.doy should be(NthWeekdayOfYear(53, Friday))
    }
  }

  "OrdinalMonthLocator" should {
    "generate nth weekday of a month of a year" in {
      val loc = 3 rd Friday of February
      loc.doy should be(NthMonthOfYear(February, NthWeekdayOfMonth(3, Friday)))
    }

    "generate last weekday of a month of a year" in {
      val loc = lastFriday of February
      loc.doy should be(NthMonthOfYear(February, LastWeekdayOfMonth(Friday)))
    }
  }
}
