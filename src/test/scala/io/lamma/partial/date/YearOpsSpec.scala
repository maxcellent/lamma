package io.lamma.partial.date

import io.lamma.{February, DayOfYear, Date}
import io.lamma.DayOfYear._
import org.scalatest.{Matchers, FlatSpec}

class YearOpsSpec extends FlatSpec with Matchers {

  "dayOfThisYear" should "throw exception when input DayOfMonth is invalid" in {
    val dom = new DayOfYear {
      override def isValidDOY(d: Date) = d.dd == 29 && d.month == February
    }
    intercept[IllegalArgumentException] {
      Date(2014, 4, 10).withDayOfYear(dom)
    }
  }

  "dayOfThisYear" should "work" in {
    Date(2014, 4, 10).withDayOfYear(FirstDayOfYear) should be(Date(2014, 1, 1))
    Date(2014, 1, 1).withDayOfYear(FirstDayOfYear) should be(Date(2014, 1, 1))
  }

  "firstDayOfYear" should "work" in {
    Date(2014, 4, 10).firstDayOfYear should be(Date(2014, 1, 1))
    Date(2014, 1, 1).firstDayOfYear should be(Date(2014, 1, 1))
  }

  "lastDayOfYear" should "work" in {
    Date(2014, 4, 10).lastDayOfYear should be(Date(2014, 12, 31))
    Date(2014, 12, 31).lastDayOfYear should be(Date(2014, 12, 31))
  }

  "sameWeekdaysOfYear" should "contain leap day" in {
    Date(2016, 3, 21).sameWeekdaysOfYear should contain(Date(2016, 2, 29))
  }

  "next" should "work" in {
    Date(2014, 4, 10).next(FirstDayOfYear) should be(Date(2015, 1, 1))
    Date(2014, 1, 1).next(FirstDayOfYear) should be(Date(2015, 1, 1))
  }

  "nextLastDayOfYear" should "work" in {
    Date(2014, 8, 2).nextLastDayOfYear should be(Date(2014, 12, 31))
    Date(2014, 12, 31).nextLastDayOfYear should be(Date(2015, 12, 31))
  }

  "nextFirstDayOfYear" should "work" in {
    Date(2014, 8, 2).nextFirstDayOfYear should be(Date(2015, 1, 1))
    Date(2015, 1, 1).nextFirstDayOfYear should be(Date(2016, 1, 1))
  }

  "pastDayOfYear" should "work" in {
    Date(2014, 4, 10).previous(FirstDayOfYear) should be(Date(2014, 1, 1))
    Date(2014, 1, 1).previous(FirstDayOfYear) should be(Date(2013, 1, 1))
  }

  "previousLastDayOfYear" should "work" in {
    Date(2014, 8, 2).previousLastDayOfYear should be(Date(2013, 12, 31))
    Date(2013, 12, 31).previousLastDayOfYear should be(Date(2012, 12, 31))
  }

  "previousFirstDayOfYear" should "work" in {
    Date(2014, 8, 2).previousFirstDayOfYear should be(Date(2014, 1, 1))
    Date(2014, 1, 1).previousFirstDayOfYear should be(Date(2013, 1, 1))
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
