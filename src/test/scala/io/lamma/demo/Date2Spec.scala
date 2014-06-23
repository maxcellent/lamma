package io.lamma.demo

import io.lamma._
import org.scalatest.{Matchers, WordSpec}

/**
 * this class covers all scala code used in Tutorial 2: Advanced Date Generation
 *  it was Tutorial 2: Advanced Sequence Generation in 1.x
 */
class Date2Spec extends WordSpec with Matchers {

  "you can specify day of week when generating with Weeks" in {
    val expected = Date(2014, 5, 13) :: Date(2014, 6, 3) :: Date(2014, 6, 24) :: Nil
    val actual = (2014, 5, 10) to (2014, 7, 1) by (3 weeks) on Tuesday
    actual.toList should be(expected)
  }

  "and for month, we have a similar concept for Monthly called PositionOfMonth" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
    val actual = (2014, 5, 1) to (2014, 7, 30) by month on (10 th day)
    actual.toList should be(expected)
  }

  "you can also select day of week inside a month" in {
    val expected = Date(2014, 5, 9) :: Date(2014, 7, 11) :: Date(2014, 9, 12) :: Nil
    val actual = (2014, 5, 1) to (2014, 9, 30) by (2 months) on (2 nd Friday)
    actual.toList should be(expected)
  }

  "similarly, year" in {
    val expected = Date(2014, 12, 30) :: Date(2015, 12, 29) :: Date(2016, 12, 27) :: Nil
    val actual = (2014, 1, 1) to (2016, 12, 31) by year on lastTuesday
    actual.toList should be(expected)
  }

  "find all 3rd Friday of February for every 3 years in 2010s" in {
    val expected = Date(2010,2,19) :: Date(2013,2,15) :: Date(2016,2,19) :: Date(2019,2,15) :: Nil
    val actual = (2010, 1, 1) to (2019, 12, 31) by (3 years) on (3 rd Friday of February)
    actual.toList should be(expected)
  }

  "you can implement your own PositionOfMonth" in {
    /**
     * match first day in Feb, 3rd day for other months
     */
    case object MyPositionOfMonth extends DayOfMonth {
      override def isValidDOM(d: Date) = {
        if (d.month == February) {
          d.dd == 1
        } else {
          d.dd == 3
        }
      }
    }

    val expected = Date(2014, 1, 3) :: Date(2014, 2, 1) :: Date(2014, 3, 3) :: Nil
    val actual = (2014, 1, 1) to (2014, 3, 31) by month on MyPositionOfMonth
    actual.toList should be(expected)
  }

  "you can shift a date based on the result. For example, I want 3rd last day of every month" in {
    val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,29) :: Nil
    val actual = (2014, 1, 1) to (2014, 3, 31) by month on lastDay shift -2
    actual.toList should be(expected)
  }

  "you can also shift by working days" in {
    val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,27) :: Nil
    val actual = (2014, 1, 1) to (2014, 3, 31) by month on lastDay shift(-2, Weekends)
    actual.toList should be(expected)
  }

  "you can further select result date after the date is shifted" in {
    val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,31) :: Nil   // last date is different
    val actual = (2014, 1, 1) to (2014, 3, 31) by month on lastDay shift -2 forward Weekends
    actual.toList should be(expected)
  }

  // some edge cases
  "if the recurrence pattern is too long, then there will be only one day generated, based on the direction (forward or backward?)" in {
    ((2014, 1, 1) to (2014, 3, 31) by (6 months)).toList should(be(List(Date(2014, 1, 1))))

    ((2014, 3, 31) to (2014, 1, 1) by (-6 months)).toList should(be(List(Date(2014, 3, 31))))
  }

  "single date will be generated, when start date equals to the end date" in {
    ((2014, 1, 1) to (2014, 1, 1) by (6 months)).toList should(be(List(Date(2014, 1, 1))))
  }
}
