package io.lamma

import org.scalatest.{WordSpec, Matchers}
import io.lamma.PositionOfMonth.{LastWeekdayOfMonth, NthWeekdayOfMonth, LastDayOfMonth, NthDayOfMonth}
import io.lamma.Weekday.Wednesday

class PositionOfMonthSpec extends WordSpec with Matchers {

  "NthDayOfMonth" should {
    "return true when input date is valid" in {
      NthDayOfMonth(10).isValidDOM(Date(2014, 4, 10)) should be(true)
    }

    "return false when input date is not valid" in {
      NthDayOfMonth(5).isValidDOM(Date(2014, 4, 10)) should be(false)
    }

    "return true when n is later than the last posible date of the month" in {
      NthDayOfMonth(30).isValidDOM(Date(2014, 2, 28)) should be(true)
    }
  }

  "LastDayOfMonth" should {
    "return true when input date is the last day of the month" in {
      LastDayOfMonth.isValidDOM(Date(2014, 2, 28)) should be(true)
    }

    "return false when input date is not last day of the month" in {
      LastDayOfMonth.isValidDOM(Date(2016, 2, 28)) should be(false)
    }
  }

  "NthWeekdayOfMonth" should {
    "return false if input weekday is not correct" in {
      NthWeekdayOfMonth(2, Wednesday).isValidDOM(Date(2014, 2, 4)) should be(false)
    }

    "return false if input weekday is correct but nth is incorrect" in {
      NthWeekdayOfMonth(2, Wednesday).isValidDOM(Date(2014, 2, 5)) should be(false)
    }

    "return true if both nth and weekday are correct" in {
      NthWeekdayOfMonth(2, Wednesday).isValidDOM(Date(2014, 2, 12)) should be(true)
    }

    "return true if weekday is correct, and this is the last weekday of the month, and number of weekdays < n" in {
      NthWeekdayOfMonth(5, Wednesday).isValidDOM(Date(2014, 2, 26)) should be(true)
    }
  }

  "LastWeekdayOfMonth" should {
    "return false if input weekday is not correct" in {
      LastWeekdayOfMonth(Wednesday).isValidDOM(Date(2014, 2, 27)) should be(false)
    }

    "return false if input weekday is correct but not the last day" in {
      LastWeekdayOfMonth(Wednesday).isValidDOM(Date(2014, 2, 19)) should be(false)
    }

    "return true if input weekday is the last weekday of the month" in {
      LastWeekdayOfMonth(Wednesday).isValidDOM(Date(2014, 2, 26)) should be(true)
    }
  }
}
