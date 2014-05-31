package io.lamma

import org.scalatest.{Matchers, WordSpec}
import io.lamma.Recurrence._
import io.lamma.Weekday.{Friday, Tuesday}
import io.lamma.PositionOfMonth.{NthWeekdayOfMonth, NthDayOfMonth}
import io.lamma.Month.February
import io.lamma.PositionOfYear.{NthMonthOfYear, LastWeekdayOfYear}

/**
 * this spec is written in tutorial order in order to verify and maintain everything used in tutorial
 * please don't reorder them
 */
class LammaSpec extends WordSpec with Matchers {

  "seq" should {
    "generate date sequence" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 5, 11) :: Date(2014, 5, 12) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 12)) should be(expected)
    }

    "generate empty sequence when start day is later than end day" in {
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 5)) should be('empty)
    }

    "generate sequence by week" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 5, 17) :: Date(2014, 5, 24) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 30), EveryWeek) should be(expected)
    }

    "also, generate sequence by month" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 7, 10), EveryMonth) should be(expected)
    }

    "month end will be handled properly" in {
      val expected = Date(2014, 1, 31) :: Date(2014, 2, 28) :: Date(2014, 3, 31) :: Date(2014, 4, 30) :: Nil
      Lamma.sequence(Date(2014, 1, 31), Date(2014, 5, 1), EveryMonth) should be(expected)
    }

    "obviously, generate sequence by year" in {
      val expected = Date(2014, 5, 10) :: Date(2015, 5, 10) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2015, 7, 10), EveryYear) should be(expected)
    }

    "leap year is considered" in {
      val expected = Date(2012, 2, 29) :: Date(2013, 2, 28) :: Date(2014, 2, 28) :: Date(2015, 2, 28) :: Date(2016, 2, 29) :: Nil
      Lamma.sequence(Date(2012, 2, 29), Date(2016, 3, 1), EveryYear) should be(expected)
    }

    "not surprisingly, your can customize durations, for example, generate a date sequence every 3 days" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 5, 13) :: Date(2014, 5, 16) :: Date(2014, 5, 19) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 20), Days(3)) should be(expected)
    }

    "and Weeks / Months / Years, for example" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 8, 10) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 10, 20), Months(3)) should be(expected)
    }

    "select all leap days" in {
      val expected = Date(2012, 2, 29) :: Date(2016, 2, 29) :: Date(2020, 2, 29) :: Nil
      Lamma.sequence(Date(2012, 2, 29), Date(2020, 2, 29), Years(4)) should be(expected)
    }

    "you can also generate date in backward direction, for example, use our last example" in {
      val expected = Date(2014, 7, 20) :: Date(2014, 10, 20) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 10, 20), MonthsBackward(3)) should be(expected)
    }

    "you can specify weekday when generating with Weeks or WeeksBackward" in {
      // a list of every 3 Tuesday from 2014-05-05 to 2014-07-01
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

    "it's very easy to implement your own PositionOfMonth" in {
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
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 30), Months(1, MyPositionOfMonth)) should be(expected)
    }

    "similarly, year" in {
      val expected = Date(2014, 12, 30) :: Date(2015, 12, 29) :: Date(2016, 12, 27) :: Nil
      Lamma.sequence(Date(2014, 1, 1), Date(2016, 12, 31), Years(1, LastWeekdayOfYear(Tuesday))) should be(expected)
    }

    "find all 3rd Friday of February for every 3 years in 2010s" in {
      val expected = Date(2010,2,19) :: Date(2013,2,15) :: Date(2016,2,19) :: Date(2019,2,15) :: Nil
      Lamma.sequence(Date(2010, 1, 1), Date(2019, 12, 31), Years(3, NthMonthOfYear(February, NthWeekdayOfMonth(3, Friday)))) should be(expected)
    }
  }
}
