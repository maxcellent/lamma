package com.lamma4s

import org.scalatest.{Matchers, FlatSpec}
import com.lamma4s.Weekday.{Tuesday, Thursday, Monday}
import Duration._
import com.lamma4s.PositionOfMonth.LastWeekdayOfMonth

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

  "nextWeekday" should "work" in {
    Date(2014, 4, 10).nextWeekday(Monday) should be(Date(2014, 4, 14))
    Date(2014, 4, 10).nextWeekday(Thursday) should be(Date(2014, 4, 10))
  }

  "previousWeekday" should "work" in {
    Date(2014, 4, 10).previousWeekday(Monday) should be(Date(2014, 4, 7))
    Date(2014, 4, 10).previousWeekday(Thursday) should be(Date(2014, 4, 10))
  }

  "monthsBetween" should "work" in {
    Date.monthsBetween(Date(2014, 4, 10), Date(2014, 3, 20)) should be(0)
    Date.monthsBetween(Date(2014, 4, 10), Date(2014, 4, 20)) should be(0)
    Date.monthsBetween(Date(2014, 4, 10), Date(2014, 5, 20)) should be(1)
    Date.monthsBetween(Date(2014, 4, 10), Date(2014, 6, 9)) should be(1)
    Date.monthsBetween(Date(2014, 4, 10), Date(2014, 6, 10)) should be(2)
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

  "nextMonthPosition" should "work" in {
    Date(2014, 1, 5).nextMonthPosition(LastWeekdayOfMonth(Tuesday)) should be(Date(2014, 1, 28))
  }

  "DateRange" should "work" in {
    val expected = Date(2014, 1, 5) :: Date(2014, 1, 6) :: Date(2014, 1, 7) :: Date(2014, 1, 8) :: Nil
    DateRange(Date(2014, 1, 5), Date(2014, 1, 8)).toList should be(expected)
  }

  "toISOString" should "work" in {
    Date(978, 1, 5).toISOString should be("0978-01-05")
    Date(2014, 11, 29).toISOString should be("2014-11-29")
  }
}
