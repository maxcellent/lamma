package io.lamma.partial.date

import io.lamma.Date
import io.lamma.DayOfMonth.NthDayOfMonth
import org.scalatest.{Matchers, FlatSpec}

class MonthOpsSpec extends FlatSpec with Matchers {

  "thisMonthBegin" should "work" in {
    Date(2014, 4, 10).thisMonthBegin should be(Date(2014, 4, 1))
    Date(2014, 4, 1).thisMonthBegin should be(Date(2014, 4, 1))
  }

  "thisMonthEnd" should "work" in {
    Date(2014, 4, 10).thisMonthEnd should be(Date(2014, 4, 30))
    Date(2014, 2, 10).thisMonthEnd should be(Date(2014, 2, 28))
    Date(2016, 2, 10).thisMonthEnd should be(Date(2016, 2, 29))
  }

  "sameWeekdaysOfMonth" should "work" in {
    val expected = Date(2016, 2, 1) :: Date(2016, 2, 8) :: Date(2016, 2, 15) :: Date(2016, 2, 22) :: Date(2016, 2, 29) :: Nil
    Date(2016, 2, 15).sameWeekdaysOfMonth should be(expected)
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

  "maxDayOfMonth" should "work" in {
    Date(2014, 1, 5).maxDayOfMonth should be(31)
    Date(2014, 2, 5).maxDayOfMonth should be(28)
    Date(2016, 2, 5).maxDayOfMonth should be(29)
    Date(2016, 6, 5).maxDayOfMonth should be(30)
  }

  "dayOfMonth" should "work" in {
    Date(2014, 5, 9).dayOfMonth should be(9)
  }

  "monthSinceBC" should "work" in {
    Date(2000, 5, 5).monthSinceBC should be(24005)
  }
}
