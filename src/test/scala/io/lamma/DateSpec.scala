package io.lamma

import org.scalatest.{Matchers, FlatSpec}
import Duration._
import io.lamma.PositionOfMonth._
import io.lamma.PositionOfYear._
import io.lamma.Weekday._

class DateSpec extends FlatSpec with Matchers {

  "init" should "throw exception when input date is not valid" in {
    intercept[IllegalArgumentException] {
      Date(2014, 13, 20)
    }
  }

  "+" should "work for int (days)" in {
    Date(2014, 4, 10) + 10 should be(Date(2014, 4, 20))
  }

  "+" should "work for days" in {
    Date(2014, 4, 10) + (7 days) should be(Date(2014, 4, 17))
  }

  "+" should "work for weeks" in {
    Date(2014, 4, 10) + (2 weeks) should be(Date(2014, 4, 24))
  }

  "+" should "work for months" in {
    Date(2014, 4, 10) + (5 months) should be(Date(2014, 9, 10))
  }

  "+" should "work for years" in {
    Date(2014, 4, 10) + (10 years) should be(Date(2024, 4, 10))
  }

  "-" should "work for int (days)" in {
    Date(2014, 4, 10) - 5 should be(Date(2014, 4, 5))
  }

  "-" should "work for days" in {
    Date(2014, 4, 10) - (4 days) should be(Date(2014, 4, 6))
  }

  "-" should "work for weeks" in {
    Date(2014, 4, 10) - (1 week) should be(Date(2014, 4, 3))
  }

  "-" should "work for months" in {
    Date(2014, 4, 10) - (5 months) should be(Date(2013, 11, 10))
  }

  "-" should "work for years" in {
    Date(2014, 4, 10) - (10 years) should be(Date(2004, 4, 10))
  }

  "-" should "work for Date object" in {
    Date(2014, 4, 10) - Date(2014, 4, 10) should be(0)
    Date(2014, 4, 10) - Date(2014, 4, 5) should be(5)
    Date(2014, 4, 10) - Date(2014, 4, 15) should be(-5)
  }

  ">" should "work" in {
    Date(2014, 4, 10) > Date(2014, 4, 5) should be(true)
    Date(2014, 4, 10) > Date(2014, 4, 10) should be(false)
    Date(2014, 4, 10) > Date(2014, 4, 12) should be(false)
  }

  "<" should "work" in {
    Date(2014, 4, 10) < Date(2014, 4, 5) should be(false)
    Date(2014, 4, 10) < Date(2014, 4, 10) should be(false)
    Date(2014, 4, 10) < Date(2014, 4, 12) should be(true)
  }

  ">=" should "work" in {
    Date(2014, 4, 10) >= Date(2014, 4, 5) should be(true)
    Date(2014, 4, 10) >= Date(2014, 4, 10) should be(true)
    Date(2014, 4, 10) >= Date(2014, 4, 12) should be(false)
  }

  "<=" should "work" in {
    Date(2014, 4, 10) <= Date(2014, 4, 5) should be(false)
    Date(2014, 4, 10) <= Date(2014, 4, 10) should be(true)
    Date(2014, 4, 10) <= Date(2014, 4, 12) should be(true)
  }

  "weekday" should "work" in {
    Date(2014, 4, 10).weekday should be(Thursday)
  }

  "thisMonthBegin" should "work" in {
    Date(2014, 4, 10).thisMonthBegin should be(Date(2014, 4, 1))
    Date(2014, 4, 1).thisMonthBegin should be(Date(2014, 4, 1))
  }

  "thisMonthEnd" should "work" in {
    Date(2014, 4, 10).thisMonthEnd should be(Date(2014, 4, 30))
    Date(2014, 2, 10).thisMonthEnd should be(Date(2014, 2, 28))
    Date(2016, 2, 10).thisMonthEnd should be(Date(2016, 2, 29))
  }

  "thisYearBegin" should "work" in {
    Date(2014, 4, 10).thisYearBegin should be(Date(2014, 1, 1))
    Date(2014, 1, 1).thisYearBegin should be(Date(2014, 1, 1))
  }

  "thisYearEnd" should "work" in {
    Date(2014, 4, 10).thisYearEnd should be(Date(2014, 12, 31))
    Date(2014, 12, 31).thisYearEnd should be(Date(2014, 12, 31))
  }

  "sameWeekdaysOfMonth" should "work" in {
    val expected = Date(2016, 2, 1) :: Date(2016, 2, 8) :: Date(2016, 2, 15) :: Date(2016, 2, 22) :: Date(2016, 2, 29) :: Nil
    Date(2016, 2, 15).sameWeekdaysOfMonth should be(expected)
  }

  "sameWeekdaysOfYear" should "contain leap day" in {
    Date(2016, 3, 21).sameWeekdaysOfYear should contain(Date(2016, 2, 29))
  }

  "comingWeekday" should "work" in {
    Date(2014, 4, 10).comingWeekday(Monday) should be(Date(2014, 4, 14))
    Date(2014, 4, 10).comingWeekday(Thursday) should be(Date(2014, 4, 17))

    Date(2014, 4, 10).comingMonday should be(Date(2014, 4, 14))
    Date(2014, 4, 10).comingTuesday should be(Date(2014, 4, 15))
    Date(2014, 4, 10).comingWednesday should be(Date(2014, 4, 16))
    Date(2014, 4, 10).comingThursday should be(Date(2014, 4, 17))
    Date(2014, 4, 10).comingFriday should be(Date(2014, 4, 11))
    Date(2014, 4, 10).comingSaturday should be(Date(2014, 4, 12))
    Date(2014, 4, 10).comingSunday should be(Date(2014, 4, 13))
  }

  "pastWeekday" should "work" in {
    Date(2014, 4, 10).pastWeekday(Monday) should be(Date(2014, 4, 7))
    Date(2014, 4, 10).pastWeekday(Thursday) should be(Date(2014, 4, 3))

    Date(2014, 4, 10).pastMonday should be(Date(2014, 4, 7))
    Date(2014, 4, 10).pastTuesday should be(Date(2014, 4, 8))
    Date(2014, 4, 10).pastWednesday should be(Date(2014, 4, 9))
    Date(2014, 4, 10).pastThursday should be(Date(2014, 4, 3))
    Date(2014, 4, 10).pastFriday should be(Date(2014, 4, 4))
    Date(2014, 4, 10).pastSaturday should be(Date(2014, 4, 5))
    Date(2014, 4, 10).pastSunday should be(Date(2014, 4, 6))
  }

  "comingDayOfMonth" should "work" in {
    Date(2014, 4, 10).comingDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 5, 10))
    Date(2014, 4, 9).comingDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
    Date(2014, 4, 11).comingDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 5, 10))
  }

  "comingMonthEnd" should "work" in {
    Date(2014, 7, 30).comingMonthEnd should be(Date(2014, 7, 31))
    Date(2014, 7, 31).comingMonthEnd should be(Date(2014, 8, 31))
  }

  "comingMonthBegin" should "work" in {
    Date(2014, 7, 31).comingMonthBegin should be(Date(2014, 8, 1))
    Date(2014, 8,  1).comingMonthBegin should be(Date(2014, 9, 1))
  }

  "pastDayOfMonth" should "work" in {
    Date(2014, 4, 10).pastDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 3, 10))
    Date(2014, 4, 9).pastDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 3, 10))
    Date(2014, 4, 11).pastDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
  }

  "pastMonthEnd" should "work" in {
    Date(2014, 8, 5).pastMonthEnd should be(Date(2014, 7, 31))
    Date(2014, 7, 31).pastMonthEnd should be(Date(2014, 6, 30))
  }

  "pastMonthBegin" should "work" in {
    Date(2014, 8, 2).pastMonthBegin should be(Date(2014, 8, 1))
    Date(2014, 8, 1).pastMonthBegin should be(Date(2014, 7, 1))
  }

  "comingDayOfYear" should "work" in {
    Date(2014, 4, 10).comingDayOfYear(FirstDayOfYear) should be(Date(2015, 1, 1))
    Date(2014, 1, 1).comingDayOfYear(FirstDayOfYear) should be(Date(2015, 1, 1))
  }

  "comingYearEnd" should "work" in {
    Date(2014, 8, 2).comingYearEnd should be(Date(2014, 12, 31))
    Date(2014, 12, 31).comingYearEnd should be(Date(2015, 12, 31))
  }

  "comingYearBegin" should "work" in {
    Date(2014, 8, 2).comingYearBegin should be(Date(2015, 1, 1))
    Date(2015, 1, 1).comingYearBegin should be(Date(2016, 1, 1))
  }

  "pastDayOfYear" should "work" in {
    Date(2014, 4, 10).pastDayOfYear(FirstDayOfYear) should be(Date(2014, 1, 1))
    Date(2014, 1, 1).pastDayOfYear(FirstDayOfYear) should be(Date(2013, 1, 1))
  }

  "pastYearEnd" should "work" in {
    Date(2014, 8, 2).pastYearEnd should be(Date(2013, 12, 31))
    Date(2013, 12, 31).pastYearEnd should be(Date(2012, 12, 31))
  }

  "pastYearBegin" should "work" in {
    Date(2014, 8, 2).pastYearBegin should be(Date(2014, 1, 1))
    Date(2014, 1, 1).pastYearBegin should be(Date(2013, 1, 1))
  }

  "Date.monthsBetween" should "work" in {
    Date.monthsBetween((2014, 4, 10) -> (2014, 4, 20)) should be(0)
    Date.monthsBetween((2014, 4, 10) -> (2014, 5, 10)) should be(1)
    Date.monthsBetween((2014, 4, 10) -> (2014, 6,  9)) should be(1)
    Date.monthsBetween((2014, 4, 10) -> (2014, 6, 10)) should be(2)
    Date.monthsBetween((2016, 2, 29) -> (2017, 2, 28)) should be(12)
  }

  "yearsBetween" should "work" in {
    Date.yearsBetween((2014, 4, 10) -> (2015, 4, 5)) should be(0)
    Date.yearsBetween((2014, 4, 10) -> (2015, 4, 10)) should be(1)
    Date.yearsBetween((2014, 4, 10) -> (2016, 4, 9)) should be(1)
  }

  "maxDayOfMonth" should "work" in {
    Date(2014, 1, 5).maxDayOfMonth should be(31)
    Date(2014, 2, 5).maxDayOfMonth should be(28)
    Date(2016, 2, 5).maxDayOfMonth should be(29)
    Date(2016, 6, 5).maxDayOfMonth should be(30)
  }

  "maxDayOfYear" should "work" in {
    Date(2014, 5, 1).maxDayOfYear should be(365)
    Date(2016, 5, 1).maxDayOfYear should be(366)
  }

  "isLastDayOfYear" should "work" in {
    Date(2014, 12, 31).isLastDayOfYear should be(true)
    Date(2016, 12, 31).isLastDayOfYear should be(true)
  }

  "daysInTheSameMonthWithSameWeekday" should "work" in {
    val expected = Seq(Date(2014, 2, 5), Date(2014, 2, 12), Date(2014, 2, 19), Date(2014, 2, 26))
    Date(2014, 2, 12).sameWeekdaysOfMonth should be(expected)
  }

  "DateRange" should "work" in {
    val expected = Date(2014, 1, 5) :: Date(2014, 1, 6) :: Date(2014, 1, 7) :: Date(2014, 1, 8) :: Nil
    DateRange(Date(2014, 1, 5), Date(2014, 1, 8)).toList should be(expected)
  }

  "toISOString" should "work" in {
    Date(978, 1, 5).toISOString should be("0978-01-05")
    Date(2014, 11, 29).toISOString should be("2014-11-29")
  }

  "dayOfMonth" should "work" in {
    Date(2014, 5, 9).dayOfMonth should be(9)
  }

  "dayOfYear" should "work" in {
    Date(2014, 2, 9).dayOfYear should be(40)
    Date(2014, 3, 9).dayOfYear should be(68)
    Date(2016, 3, 9).dayOfYear should be(69)
  }

  "monthSinceBC" should "work" in {
    Date(2000, 5, 5).monthSinceBC should be(24005)
  }
}
