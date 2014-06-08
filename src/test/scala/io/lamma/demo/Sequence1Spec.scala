package io.lamma.demo

import io.lamma.{WeekendCalendar, Lamma, Date}
import io.lamma.Recurrence._
import org.scalatest.{WordSpec, Matchers}

/**
 * this class covers all scala code used in Tutorial 1: Basic Sequence Generation
 */
class Sequence1Spec extends WordSpec with Matchers {

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
}
