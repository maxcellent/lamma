package io.lamma.demo

import io.lamma.{PositionOfMonth, WeekendCalendar, Lamma, Date}
import io.lamma.Recurrence._
import io.lamma.Weekday.{Friday, Tuesday}
import io.lamma.PositionOfMonth._
import io.lamma.PositionOfYear._
import io.lamma.Month.February
import io.lamma.PositionOfMonth.NthWeekdayOfMonth
import io.lamma.PositionOfYear.NthMonthOfYear
import io.lamma.PositionOfMonth.NthDayOfMonth
import io.lamma.Shifter.{ShiftWorkingDays, ShiftCalendarDays}
import io.lamma.Selector.Forward
import org.scalatest.{WordSpec, Matchers}

class SequenceSpec extends WordSpec with Matchers {

  // ========= Basic Sequence Generation =========

  "generate date sequence" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 11) :: Date(2014, 5, 12) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 12)) should be(expected)
  }

  "generate sequence by week" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 17) :: Date(2014, 5, 24) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 24), EveryWeek) should be(expected)
  }

  "also, generate sequence by month" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 7, 10), EveryMonth) should be(expected)
  }

  "month end will be handled properly" in {
    val expected = Date(2014, 1, 31) :: Date(2014, 2, 28) :: Date(2014, 3, 31) :: Date(2014, 4, 30) :: Nil
    Lamma.sequence(Date(2014, 1, 31), Date(2014, 4, 30), EveryMonth) should be(expected)
  }

  "obviously, generate sequence by year" in {
    val expected = Date(2014, 5, 10) :: Date(2015, 5, 10) :: Date(2016, 5, 10) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2016, 5, 10), EveryYear) should be(expected)
  }

  "leap year is considered" in {
    val expected = Date(2012, 2, 29) :: Date(2013, 2, 28) :: Date(2014, 2, 28) :: Date(2015, 2, 28) :: Date(2016, 2, 29) :: Nil
    Lamma.sequence(Date(2012, 2, 29), Date(2016, 2, 29), EveryYear) should be(expected)
  }

  "not surprisingly, your can customize durations, for example, generate a date sequence every 3 days" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 13) :: Date(2014, 5, 16) :: Date(2014, 5, 19) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 19), Days(3)) should be(expected)
  }

  "and Weeks / Months / Years, for example" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 8, 10) :: Date(2014, 11, 10) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 11, 10), Months(3)) should be(expected)
  }

  "select all leap days" in {
    val expected = Date(2012, 2, 29) :: Date(2016, 2, 29) :: Date(2020, 2, 29) :: Nil
    Lamma.sequence(Date(2012, 2, 29), Date(2020, 2, 29), Years(4)) should be(expected)
  }

  "working day generation is also supported, in this case WeekendCalendar is used which means all weekend will be skipped" in {
    val expected = Date(2015, 10, 5) :: Date(2015, 10, 12) :: Date(2015, 10, 19) :: Nil
    Lamma.sequence(Date(2015, 10, 5), Date(2015, 10, 19), Days.workingDays(5, WeekendCalendar)) should be(expected)
  }

  "recurring backward" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 11) :: Date(2014, 5, 12) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 12), DaysBackward(1)) should be(expected)
  }

  "the difference between forward and backward is when there is a fraction days" in {
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 10, 20), Months(3)) should be(Date(2014, 5, 10) :: Date(2014, 8, 10) :: Nil)
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 10, 20), MonthsBackward(3)) should be(Date(2014, 7, 20) :: Date(2014, 10, 20) :: Nil)
  }

  // ========= Advanced Sequence Generation =========

  "you can specify weekday when generating with Weeks or WeeksBackward" in {
    val expected = Date(2014, 5, 13) :: Date(2014, 6, 3) :: Date(2014, 6, 24) :: Nil
    Lamma.sequence(Date(2014, 5, 10), Date(2014, 7, 1), Weeks(3, Tuesday)) should be(expected)
  }

  "and for month, we have a similar concept for Monthly called PositionOfMonth" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
    Lamma.sequence(Date(2014, 5, 1), Date(2014, 7, 30), Months(1, NthDayOfMonth(10))) should be(expected)
  }

  "you can also select weekday inside a month" in {
    val expected = Date(2014, 5, 9) :: Date(2014, 7, 11) :: Date(2014, 9, 12) :: Nil
    Lamma.sequence(Date(2014, 5, 1), Date(2014, 9, 30), Months(2, NthWeekdayOfMonth(2, Friday))) should be(expected)
  }

  "similarly, year" in {
    val expected = Date(2014, 12, 30) :: Date(2015, 12, 29) :: Date(2016, 12, 27) :: Nil
    Lamma.sequence(Date(2014, 1, 1), Date(2016, 12, 31), Years(1, LastWeekdayOfYear(Tuesday))) should be(expected)
  }

  "find all 3rd Friday of February for every 3 years in 2010s" in {
    val expected = Date(2010,2,19) :: Date(2013,2,15) :: Date(2016,2,19) :: Date(2019,2,15) :: Nil
    Lamma.sequence(Date(2010, 1, 1), Date(2019, 12, 31), Years(3, NthMonthOfYear(February, NthWeekdayOfMonth(3, Friday)))) should be(expected)
  }

  "you can implement your own PositionOfMonth" in {
    /**
     * match first day in Feb, 3rd day for other months
     */
    case object MyPositionOfMonth extends PositionOfMonth {
      override def isValidDOM(d: Date) = {
        if (d.month == February) {
          d.dd == 1
        } else {
          d.dd == 3
        }
      }
    }

    val expected = Date(2014, 1, 3) :: Date(2014, 2, 1) :: Date(2014, 3, 3) :: Nil
    Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(1, MyPositionOfMonth)) should be(expected)
  }

  "you can shift a date based on the result. For example, I want 3rd last day of every month" in {
    val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,29) :: Nil
    Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(1, LastDayOfMonth), ShiftCalendarDays(-2)) should be(expected)
  }

  "you can also shift by working days" in {
    val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,27) :: Nil
    Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(1, LastDayOfMonth), ShiftWorkingDays(-2, WeekendCalendar)) should be(expected)
  }

  "you can further select result date after the date is shifted" in {
    val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,31) :: Nil   // last date is different
    Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(1, LastDayOfMonth), ShiftCalendarDays(-2), Forward(WeekendCalendar)) should be(expected)
  }

  // some edge cases
  "if the recurrence pattern is too long, then there will be only one day generated, based on the direction (forward or backward?)" in {
    Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(6)) should(be(List(Date(2014, 1, 1))))
    Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), MonthsBackward(6)) should(be(List(Date(2014, 3, 31))))
  }

  "single date will be generated, when start date equals to the end date" in {
    Lamma.sequence(Date(2014, 1, 1), Date(2014, 1, 1), Months(6)) should(be(List(Date(2014, 1, 1))))
  }

  "exception will be thrown when start date is before end date" in {
    intercept[IllegalArgumentException] {
      Lamma.sequence(Date(2014, 1, 1), Date(2013, 12, 31))
    }
  }
}
