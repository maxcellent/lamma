package iolamma.demo

import io.lamma.{Date, Weekends, _}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

/**
 * this class covers all scala code used in Tutorial 1: Basic Date Generation
 *   it was Tutorial 1: Basic Sequence Generation in 1.x
 */
@RunWith(classOf[JUnitRunner])
class Date1Spec extends WordSpec with Matchers {
  "generate date sequence by day" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 11) :: Date(2014, 5, 12) :: Nil
    val actual = Date(2014, 5, 10) to Date(2014, 5, 12)
    actual.toList should be(expected)
  }

  "generate dates by step" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 12) :: Date(2014, 5, 14) :: Nil
    val actual = (2014, 5, 10) to (2014, 5, 15) by 2
    actual.toList should be(expected)
  }

  "generate dates by negative step" in {
    val expected = Date(2014, 5, 20) :: Date(2014, 5, 18) :: Date(2014, 5, 16) :: Nil
    val actual = "2014-05-20" to "2014-05-15" by -2
    actual.toList should be(expected)
  }

  "generate sequence by week" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 17) :: Date(2014, 5, 24) :: Nil
    val actual = (2014, 5, 10) to (2014, 5, 24) by week
    actual.toList should be(expected)
  }

  "also, generate sequence by month" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
    val actual = (2014, 5, 10) to (2014, 7, 10) by month
    actual.toList should be(expected)
  }

  "month end is handled properly" in {
    val expected = Date(2014, 1, 31) :: Date(2014, 2, 28) :: Date(2014, 3, 31) :: Date(2014, 4, 30) :: Nil
    val actual = (2014, 1, 31) to (2014, 4, 30) by month
    actual.toList should be(expected)
  }

  "generate dates by year" in {
    val expected = Date(2014, 5, 10) :: Date(2015, 5, 10) :: Date(2016, 5, 10) :: Nil
    val actual = (2014, 5, 10) to (2016, 5, 10) by year
    actual.toList should be(expected)
  }

  "leap year is handled properly" in {
    val expected = Date(2012, 2, 29) :: Date(2013, 2, 28) :: Date(2014, 2, 28) :: Date(2015, 2, 28) :: Date(2016, 2, 29) :: Nil
    val actual = (2012, 2, 29) to (2016, 2, 29) by year
    actual.toList should be(expected)
  }

  "the first example is equivalent to this example: `by day`" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 11) :: Date(2014, 5, 12) :: Nil
    val actual = (2014, 5, 10) to (2014, 5, 12) by day
    actual.toList should be(expected)
  }

  "not surprisingly, your can customize durations, for example, generate a date sequence every 3 days" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 13) :: Date(2014, 5, 16) :: Date(2014, 5, 19) :: Nil
    val actual = (2014, 5, 10) to (2014, 5, 19) by 3
    actual.toList should be(expected)
  }

  "same as this" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 13) :: Date(2014, 5, 16) :: Date(2014, 5, 19) :: Nil
    val actual = (2014, 5, 10) to (2014, 5, 19) by (3 days)
    actual.toList should be(expected)
  }

  "and Weeks / Months / Years, for example" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 8, 10) :: Date(2014, 11, 10) :: Nil
    val actual = (2014, 5, 10) to (2014, 11, 10) by (3 months)
    actual.toList should be(expected)
  }

  "select all leap days" in {
    val expected = Date(2012, 2, 29) :: Date(2016, 2, 29) :: Date(2020, 2, 29) :: Nil
    val actual = (2012, 2, 29) to (2020, 2, 29) by (4 years)
    actual.toList should be(expected)
  }

  "filter out weekend from the result" in {
    val expected = Date(2015, 10, 8) :: Date(2015, 10, 9) :: Date(2015, 10, 12) :: Nil
    val workingDays = (2015, 10, 8) to (2015, 10, 12) except Weekends
    workingDays.toList should be(expected)
  }

  "filter out weekend and other holiday" should {
    val UKHoliday2015 = SimpleHolidayRule(
      Date(2015, 1, 1),
      Date(2015, 4, 3),
      Date(2015, 4, 6),
      Date(2015, 5, 4),
      Date(2015, 5, 25),
      Date(2015, 8, 31),
      Date(2015, 12, 25),
      Date(2015, 12, 28)
    )

    val expected = Date(2015, 12, 23) :: Date(2015, 12, 24) :: Date(2015, 12, 29) :: Date(2015, 12, 30) :: Nil

    "we can chain except keywords" in {
      val workingDays = (2015, 12, 23) to (2015, 12, 30) except Weekends except UKHoliday2015
      workingDays.toList should be(expected)
    }

    "or composite with `and` keyword" in {
      val workingDays = (2015, 12, 23) to (2015, 12, 30) except (Weekends and UKHoliday2015)
      workingDays.toList should be(expected)
    }
  }

  "by week / month / year are all handled in the same way" in {
    val expected = Date(2014, 5, 12) :: Date(2014, 5, 5) :: Nil
    val actual = (2014, 5, 12) to (2014, 5, 1) by (-1 week)
    actual.toList should be(expected)
  }

  // not in tutorial
  "other than order, the difference between forward and backward is when there is a fraction days" in {
    ((2014, 5, 10) to (2014, 10, 20) by (3 months)).toList should be(Date(2014, 5, 10) :: Date(2014, 8, 10) :: Nil)
    ((2014, 10, 20) to (2014, 5, 10) by (-3 months)).toList should be(Date(2014, 10, 20) :: Date(2014, 7, 20) :: Nil)
  }

  // not in tutorial
  "generate every nth working day is not natively supported, but can be easily achieved by using together with Scala collection library" in {
    val expected = Date(2015, 10, 5) :: Date(2015, 10, 12) :: Date(2015, 10, 19) :: Nil
    val workingDays = (2015, 10, 5) to (2015, 10, 19) except Weekends
    val every5WorkingDays = workingDays.toList.zipWithIndex.collect {
      case (d, i) if i % 5 == 0 => d
    }
    every5WorkingDays should be(expected)
  }
}
