package io.lamma

import org.scalatest.{Matchers, WordSpec}
import io.lamma.PositionOfYear.{NthWeekdayOfYear, NthDayOfYear}
import io.lamma.Weekday.{Tuesday, Wednesday}

class PositionOfYearSpec extends WordSpec with Matchers {

  "NthDayOfYear" should {
    "return false when input is not correct" in {
      NthDayOfYear(40).isValidDOY(Date(2014, 2, 8)) should be(false)
    }

    "return true when input is correct" in {
      NthDayOfYear(40).isValidDOY(Date(2014, 2, 9)) should be(true)
    }

    "return false when n exceeds max daysOfYear but input date is not the last day of the year" in {
      NthDayOfYear(366).isValidDOY(Date(2014, 12, 30)) should be(false)
    }

    "return true when n exceeds max daysOfYear and input date is the last day of the year" in {
      NthDayOfYear(366).isValidDOY(Date(2014, 12, 31)) should be(true)
    }
  }

  "NthWeekdayOfYear" should {
    "return false if weekday does not match" in {
      NthWeekdayOfYear(10, Wednesday).isValidDOY(Date(2014, 3, 4)) should be(false)
    }

    "return true if weekday does match" in {
      NthWeekdayOfYear(10, Wednesday).isValidDOY(Date(2014, 3, 5)) should be(true)
      NthWeekdayOfYear(53, Wednesday).isValidDOY(Date(2014, 12, 31)) should be(true)
    }

    "return false if number of weekdays of year < n, but weekday is not the last weekday" in {
      NthWeekdayOfYear(53, Tuesday).isValidDOY(Date(2014, 12, 23)) should be(false)
    }

    "return true if number of weekdays of year < n and weekday is the last weekday" in {
      NthWeekdayOfYear(53, Tuesday).isValidDOY(Date(2014, 12, 30)) should be(true)
    }
  }



}
