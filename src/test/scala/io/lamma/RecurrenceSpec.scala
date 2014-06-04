package io.lamma

import org.scalatest.{Matchers, WordSpec}
import io.lamma.Recurrence._
import io.lamma.PositionOfMonth.{LastDayOfMonth, LastWeekdayOfMonth}
import io.lamma.Weekday.{Tuesday, Friday}
import io.lamma.PositionOfYear.{LastDayOfYear, NthWeekdayOfYear}
import io.lamma.Shifter.{ShiftCalendarDays, NoShift, ShiftWorkingDays}

class RecurrenceSpec extends WordSpec with Matchers {

  "recurForward" should {
    "generate empty schedule if start date is earlier than end date" in {
      Recurrence.recurForward(Date(2014, 4, 15), Date(2014, 4, 10), 5) should be('empty)
    }

    "generate schedule properly" in {
      val expected = Date(2014, 4, 15) :: Date(2014, 4, 22) :: Date(2014, 4, 29) :: Nil
      Recurrence.recurForward(Date(2014, 4, 15), Date(2014, 4, 30), 7) should be(expected)
    }
  }

  "recurBackward" should {
    "generate empty schedule if start date is earlier than end date" in {
      Recurrence.recurBackward(Date(2014, 4, 15), Date(2014, 4, 10), 1) should be('empty)
    }

    "generate schedule properly" in {
      val expected = Date(2014, 4, 16) :: Date(2014, 4, 23) :: Date(2014, 4, 30) :: Nil
      Recurrence.recurBackward(Date(2014, 4, 15), Date(2014, 4, 30), 7) should be(expected)
    }
  }

  "Days" should {
    "generate end days properly" in {
      val expected = Date(2014, 4, 12) :: Date(2014, 4, 15) :: Date(2014, 4, 18) :: Nil
      Days(3).periodEndDays(Date(2014, 4, 10), Date(2014, 4, 20)) should be (expected)
    }

    "return empty when duration between start and end is too short" in {
      Days(5).periodEndDays(Date(2014, 4, 10), Date(2014, 4, 5)) should be('empty)
      Days(5).periodEndDays(Date(2014, 4, 10), Date(2014, 4, 10)) should be('empty)
      Days(5).periodEndDays(Date(2014, 4, 10), Date(2014, 4, 11)) should be('empty)
      Days(5).periodEndDays(Date(2014, 4, 10), Date(2014, 4, 13)) should be('empty)
    }

    "generate working days properly" in {
      val expected = Date(2015, 10, 5) :: Date(2015, 10, 12) :: Date(2015, 10, 19) :: Nil
      Days.workingDays(5).recur(Date(2015, 10, 5), Date(2015, 10, 20)) should be(expected)
    }

    "generate working days properly even when start day is a holiday" in {
      val expected = Date(2015, 10, 5) :: Date(2015, 10, 12) :: Date(2015, 10, 19) :: Nil
      Days.workingDays(5).recur(Date(2015, 10, 3), Date(2015, 10, 20)) should be(expected)
    }

    "throw exception when NoShift is used" in {
      intercept[IllegalArgumentException] {
        Days(NoShift)
      }
    }

    "throw exception when ShiftCalendarDays with a non-positive number is used" in {
      intercept[IllegalArgumentException] {
        DaysBackward(ShiftCalendarDays(0))
      }
    }

    "throw exception when ShiftWorkingDays with a non-positive number is used" in {
      intercept[IllegalArgumentException] {
        DaysBackward(ShiftWorkingDays(0))
      }
    }

    "throw exception when 0 day is specified" in {
      intercept[IllegalArgumentException] {
        Days(0)
      }
    }

    "throw exception when netative day is specified" in {
      intercept[IllegalArgumentException] {
        Days(-1)
      }
    }

    "throw exception when 0 working day is specified" in {
      intercept[IllegalArgumentException] {
        Days.workingDays(0)
      }
    }

    "throw exception when netative working day is specified" in {
      intercept[IllegalArgumentException] {
        Days.workingDays(-1)
      }
    }
  }

  "DaysBackward" should {
    "generate end days properly" in {
      val expected = Date(2014, 4, 11) :: Date(2014, 4, 14) :: Date(2014, 4, 17) :: Date(2014, 4, 20) :: Nil
      DaysBackward(3).periodEndDays(Date(2014, 4, 10), Date(2014, 4, 20)) should be (expected)
    }

    "generate working days properly" in {
      val expected = Date(2015, 10, 6) :: Date(2015, 10, 13) :: Date(2015, 10, 20) :: Nil
      DaysBackward.workingDays(5).recur(Date(2015, 10, 5), Date(2015, 10, 20)) should be(expected)
    }

    "generate working days properly even when start day is a holiday" in {
      val expected = Date(2015, 10, 9) :: Date(2015, 10, 16) :: Date(2015, 10, 23) :: Nil
      DaysBackward.workingDays(5).recur(Date(2015, 10, 5), Date(2015, 10, 25)) should be(expected)
    }

    "throw exception when NoShift is used" in {
      intercept[IllegalArgumentException] {
        DaysBackward(NoShift)
      }
    }

    "throw exception when ShiftCalendarDays with a non-negative number is used" in {
      intercept[IllegalArgumentException] {
        DaysBackward(ShiftCalendarDays(0))
      }
    }

    "throw exception when ShiftWorkingDays with a non-negative number is used" in {
      intercept[IllegalArgumentException] {
        DaysBackward(ShiftWorkingDays(0))
      }
    }

    "throw exception when 0 day is specified" in {
      intercept[IllegalArgumentException] {
        DaysBackward(0)
      }
    }

    "throw exception when negative backward day is specified" in {
      intercept[IllegalArgumentException] {
        DaysBackward(-1)
      }
    }

    "throw exception when 0 working day is specified" in {
      intercept[IllegalArgumentException] {
        Days.workingDays(0)
      }
    }

    "throw exception when netative working day is specified" in {
      intercept[IllegalArgumentException] {
        Days.workingDays(-1)
      }
    }
  }

  "Weeks" should {
    "have all apply methods working" in {
      Weeks(5, Tuesday) should be(Weeks(5, Some(Tuesday)))
      Weeks(Tuesday) should be(Weeks(1, Some(Tuesday)))
    }

    "generate end days if weekday is NOT defined" in {
      val expected = Date(2014, 4, 24) :: Nil
      Weeks(2).periodEndDays(Date(2014, 4, 11), Date(2014, 5, 1)) should be(expected)
    }

    "generate end days if weekday is already defined" in {
      val expected = Date(2014,4,11) :: Date(2014,4,25) :: Nil
      Weeks(2, Some(Friday)).periodEndDays(Date(2014, 4, 10), Date(2014, 5, 1)) should be(expected)
    }
  }

  "WeeksBackward" should {
    "have all apply methods working" in {
      WeeksBackward(5, Tuesday) should be(WeeksBackward(5, Some(Tuesday)))
      WeeksBackward(Tuesday) should be(WeeksBackward(1, Some(Tuesday)))
    }

    "generate end days if weekday is NOT defined" in {
      val expected = Date(2014, 4, 16) :: Date(2014, 4, 30) :: Nil
      WeeksBackward(2).periodEndDays(Date(2014, 4, 10), Date(2014, 4, 30)) should be(expected)
    }

    "generate end days if weekday is already defined" in {
      val expected = Date(2014,4,18) :: Date(2014, 5, 2) :: Nil
      WeeksBackward(2, Some(Friday)).periodEndDays(Date(2014, 4, 10), Date(2014, 5, 2)) should be(expected)
    }
  }

  "Months" should {
    "have all apply methods working" in {
      Months(5, LastDayOfMonth) should be(Months(5, Some(LastDayOfMonth)))
      Months(LastDayOfMonth) should be(Months(1, Some(LastDayOfMonth)))
    }

    "generate end days if position of month is NOT defined" in {
      val expected = Date(2014, 2, 28) :: Date(2014, 5, 30) :: Nil
      Months(3).periodEndDays(Date(2013, 12, 1), Date(2014, 6, 30)) should be(expected)
    }

    "generate end days if position of month is NOT defined and start dom is smaller" in {
      val expected = Date(2013, 5, 30) :: Nil
      Months(1).periodEndDays(Date(2013, 5, 1), Date(2013, 6, 5)) should be(expected)
    }

    "generate end days if position of month is already defined" in {
      val expected = Date(2014,2,25) :: Date(2014,4,29) :: Date(2014,6,24) :: Nil
      val pom = LastWeekdayOfMonth(Tuesday)
      Months(2, Some(pom)).periodEndDays(Date(2014, 1, 1), Date(2014, 8, 15)) should be(expected)
    }
  }

  "MonthsBackward" should {
    "have all apply methods working" in {
      MonthsBackward(5, LastDayOfMonth) should be(MonthsBackward(5, Some(LastDayOfMonth)))
      MonthsBackward(LastDayOfMonth) should be(MonthsBackward(1, Some(LastDayOfMonth)))
    }

    "generate end days if position of month is NOT defined" in {
      val expected = Date(2014, 2, 28) :: Date(2014, 4, 30) :: Date(2014, 6, 30) :: Nil
      MonthsBackward(2).periodEndDays(Date(2014, 1, 30), Date(2014, 6, 30)) should be(expected)
    }

    "generate end days if position of month is already defined" in {
      val expected = Date(2014,1,28) :: Date(2014,3,25) :: Date(2014,5,27) :: Date(2014,7,29) :: Nil
      val pom = LastWeekdayOfMonth(Tuesday)
      MonthsBackward(2, Some(pom)).periodEndDays(Date(2013, 12, 30), Date(2014, 8, 15)) should be(expected)
    }
  }

  "Years" should {
    "have all apply methods working" in {
      Years(5, LastDayOfYear) should be(Years(5, Some(LastDayOfYear)))
      Years(LastDayOfYear) should be(Years(1, Some(LastDayOfYear)))
    }

    "generate end days if position of year is NOT defined" in {
      val expected = Date(2011, 12, 31) :: Date(2013, 12, 31) :: Nil
      Years(2).periodEndDays(Date(2010, 1, 1), Date(2015, 5, 1)) should be(expected)
    }

    "generate end days if position of year is NOT defined and start doy is bigger" in {
      val expected = Date(2012,9,30) :: Date(2013,9,30) :: Nil
      Years(1).periodEndDays(Date(2011, 10, 1), Date(2014, 5, 1)) should be(expected)
    }

    "generate end days if position of year is NOT defined and doy are the same" in {
      val expected = Date(2011,9,30) :: Date(2012,9,30) :: Nil
      Years(1).periodEndDays(Date(2010, 10, 1), Date(2012, 9, 30)) should be(expected)
    }

    "generate end days if position of year is NOT defined and start doy is smaller" in {
      val expected = Date(2011,4,30) :: Date(2012,4,30) :: Nil
      Years(1).periodEndDays(Date(2010, 5, 1), Date(2012, 9, 30)) should be(expected)
    }

    "generate end days if position of year is already defined" in {
      val expected = Date(2011,1,14) :: Date(2013,1,11) :: Date(2015,1,9) :: Nil
      val poy = NthWeekdayOfYear(2, Friday)
      Years(2, Some(poy)).periodEndDays(Date(2010, 10, 1), Date(2015, 5, 1)) should be(expected)
    }
  }

  "YearsBackward" should {
    "have all apply methods working" in {
      YearsBackward(5, LastDayOfYear) should be(YearsBackward(5, Some(LastDayOfYear)))
      YearsBackward(LastDayOfYear) should be(YearsBackward(1, Some(LastDayOfYear)))
    }

    "generate end days if position of year is NOT defined and start doy is bigger" in {
      val expected = Date(2013,5,1) :: Date(2015,5,1) :: Nil
      YearsBackward(2).periodEndDays(Date(2011, 10, 1), Date(2015, 5, 1)) should be(expected)
    }

    "generate end days if position of year is NOT defined and doy are the same" in {
      val expected = Date(2011,4,30) :: Date(2012,4,30) :: Date(2013,4,30) :: Nil
      YearsBackward(1).periodEndDays(Date(2010, 5, 1), Date(2013, 4, 30)) should be(expected)
    }

    "generate end days if position of year is NOT defined and start doy is smaller" in {
      val expected = Date(2011,5,1) :: Date(2013,5,1) :: Date(2015, 5, 1) :: Nil
      YearsBackward(2).periodEndDays(Date(2011, 3, 1), Date(2015, 5, 1)) should be(expected)
    }

    "generate end days if position of year is already defined" in {
      val expected = Date(2013,1,11) :: Date(2015,1,9) :: Nil
      val poy = NthWeekdayOfYear(2, Friday)
      YearsBackward(2, Some(poy)).periodEndDays(Date(2011, 2, 20), Date(2015, 5, 1)) should be(expected)
    }
  }
}
