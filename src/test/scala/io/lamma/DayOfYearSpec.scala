package io.lamma

import io.lamma.DayOfYear.{NthDayOfYear, NthWeekdayOfYear}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class DayOfYearSpec extends WordSpec with Matchers {

  "NthDayOfYear" should {
    "be valid" in {
      DayOfYear.validate(NthDayOfYear(40))
    }

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
    "be valid" in {
      DayOfYear.validate(NthWeekdayOfYear(40, Monday))
    }

    "return false if dow does not match" in {
      NthWeekdayOfYear(10, Wednesday).isValidDOY(Date(2014, 3, 4)) should be(false)
    }

    "return true if dow does match" in {
      NthWeekdayOfYear(10, Wednesday).isValidDOY(Date(2014, 3, 5)) should be(true)
      NthWeekdayOfYear(53, Wednesday).isValidDOY(Date(2014, 12, 31)) should be(true)
    }

    "return false if number of dow of year < n, but dow is not the last weekday" in {
      NthWeekdayOfYear(53, Tuesday).isValidDOY(Date(2014, 12, 23)) should be(false)
    }

    "return true if number of dow of year < n and dow is the last dow" in {
      NthWeekdayOfYear(53, Tuesday).isValidDOY(Date(2014, 12, 30)) should be(true)
    }
  }

  "validate" should {
    "throw exception when input PositionOfYear is invalid" in {

      /**
       * this DOY will match last day of every month
       */
      case object InvalidDOY extends DayOfYear {
        override def isValidDOY(d: Date) = d.isLastDayOfMonth
      }

      intercept[InvalidDayOfYearException] {
        DayOfYear.validate(InvalidDOY)
      }
    }
  }

}
