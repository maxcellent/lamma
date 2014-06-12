package io.lamma.partial.date

import io.lamma.Date
import io.lamma.PositionOfYear._
import org.scalatest.{Matchers, FlatSpec}

class YearOpsSpec extends FlatSpec with Matchers {

  "thisYearBegin" should "work" in {
    Date(2014, 4, 10).thisYearBegin should be(Date(2014, 1, 1))
    Date(2014, 1, 1).thisYearBegin should be(Date(2014, 1, 1))
  }

  "thisYearEnd" should "work" in {
    Date(2014, 4, 10).thisYearEnd should be(Date(2014, 12, 31))
    Date(2014, 12, 31).thisYearEnd should be(Date(2014, 12, 31))
  }

  "sameWeekdaysOfYear" should "contain leap day" in {
    Date(2016, 3, 21).sameWeekdaysOfYear should contain(Date(2016, 2, 29))
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

  "maxDayOfYear" should "work" in {
    Date(2014, 5, 1).maxDayOfYear should be(365)
    Date(2016, 5, 1).maxDayOfYear should be(366)
  }

  "isLastDayOfYear" should "work" in {
    Date(2014, 12, 31).isLastDayOfYear should be(true)
    Date(2016, 12, 31).isLastDayOfYear should be(true)
  }

  "dayOfYear" should "work" in {
    Date(2014, 2, 9).dayOfYear should be(40)
    Date(2014, 3, 9).dayOfYear should be(68)
    Date(2016, 3, 9).dayOfYear should be(69)
  }
}
