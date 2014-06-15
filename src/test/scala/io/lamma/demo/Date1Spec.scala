package io.lamma.demo

import io.lamma._
import io.lamma.Recurrence._
import io.lamma.{Weekends, Lamma, Date}
import org.scalatest.{Matchers, WordSpec}

/**
 * this class covers all scala code used in Tutorial 1: Basic Date Generation
 *   it was Tutorial 1: Basic Sequence Generation in 1.x
 */
class Date1Spec extends WordSpec with Matchers {
  "generate date sequence" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 11) :: Date(2014, 5, 12) :: Nil
    val actual = Date(2014, 5, 10) to Date(2014, 5, 12)
    actual.toList should be(expected)
  }

  "generate sequence by week" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 17) :: Date(2014, 5, 24) :: Nil
    val actual = Date(2014, 5, 10) to Date(2014, 5, 24) by week
    actual.toList should be(expected)
  }

  "also, generate sequence by month" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
    val actual = Date(2014, 5, 10) to Date(2014, 7, 10) by month
    actual.toList should be(expected)
  }

  "month end will be handled properly" in {
    val expected = Date(2014, 1, 31) :: Date(2014, 2, 28) :: Date(2014, 3, 31) :: Date(2014, 4, 30) :: Nil
    val actual = Date(2014, 1, 31) to Date(2014, 4, 30) by month
    actual.toList should be(expected)
  }

  "obviously, generate sequence by year" in {
    val expected = Date(2014, 5, 10) :: Date(2015, 5, 10) :: Date(2016, 5, 10) :: Nil
    val actual = Date(2014, 5, 10) to Date(2016, 5, 10) by year
    actual.toList should be(expected)
  }

  "leap year is considered" in {
    val expected = Date(2012, 2, 29) :: Date(2013, 2, 28) :: Date(2014, 2, 28) :: Date(2015, 2, 28) :: Date(2016, 2, 29) :: Nil
    val actual = Date(2012, 2, 29) to Date(2016, 2, 29) by year
    actual.toList should be(expected)
  }

  "not surprisingly, your can customize durations, for example, generate a date sequence every 3 days" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 5, 13) :: Date(2014, 5, 16) :: Date(2014, 5, 19) :: Nil
    val actual = Date(2014, 5, 10) to Date(2014, 5, 19) by 3
    actual.toList should be(expected)
  }

  "and Weeks / Months / Years, for example" in {
    val expected = Date(2014, 5, 10) :: Date(2014, 8, 10) :: Date(2014, 11, 10) :: Nil
    val actual = Date(2014, 5, 10) to Date(2014, 11, 10) by (3 months)
    actual.toList should be(expected)
  }

  "select all leap days" in {
    val expected = Date(2012, 2, 29) :: Date(2016, 2, 29) :: Date(2020, 2, 29) :: Nil
    val actual = Date(2012, 2, 29) to Date(2020, 2, 29) by (4 years)
    actual.toList should be(expected)
  }

  // TODO: working days is not supported
  "working day generation is also supported, in this case WeekendCalendar is used which means all weekend will be skipped" in {
    val expected = Date(2015, 10, 5) :: Date(2015, 10, 12) :: Date(2015, 10, 19) :: Nil
    Lamma.sequence(Date(2015, 10, 5), Date(2015, 10, 19), Days.workingDays(5, Weekends)) should be(expected)
  }

  "recurring backward" in {
    val expected = Date(2014, 5, 12) :: Date(2014, 5, 11) :: Date(2014, 5, 10) :: Nil
    val actual = Date(2014, 5, 12) to Date(2014, 5, 10) by -1
    actual.toList should be(expected)
  }

  "the difference between forward and backward is when there is a fraction days" in {
    (Date(2014, 5, 10) to Date(2014, 10, 20) by (3 months)).toList should be(Date(2014, 5, 10) :: Date(2014, 8, 10) :: Nil)
    (Date(2014, 10, 20) to Date(2014, 5, 10) by (-3 months)).toList should be(Date(2014, 10, 20) :: Date(2014, 7, 20) :: Nil)
  }
}
