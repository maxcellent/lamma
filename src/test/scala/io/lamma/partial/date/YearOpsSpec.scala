package io.lamma.partial.date

import collection.JavaConversions._
import io.lamma._
import io.lamma.DayOfYear._
import org.scalatest.{Matchers, FlatSpec}

class YearOpsSpec extends FlatSpec with Matchers {

  "daysOfYear4j" should "work" in {
    val d = Date(2014, 4, 15)
    d.daysOfYear4j.toList should be(d.daysOfYear.toList)
  }

  "sameWeekdaysOfYear4j" should "work" in {
    val d = Date(2014, 4, 15)
    d.sameWeekdaysOfYear4j.toList should be(d.sameWeekdaysOfYear.toList)
  }

  "dayOfThisYear" should "throw exception when input DayOfMonth is invalid" in {
    val dom = new DayOfYear {
      override def isValidDOY(d: Date) = d.dd == 29 && d.month == February
    }
    intercept[IllegalArgumentException] {
      Date(2014, 4, 10).withDayOfYear(dom)
    }
  }

  "dayOfThisYear" should "work" in {
    Date(2014, 4, 10).withDayOfYear(1 st day) should be(Date(2014, 1, 1))
    Date(2014, 1, 1).withDayOfYear(1 st day) should be(Date(2014, 1, 1))
    Date(2014, 5, 1).withDayOfYear(2 nd Friday of February) should be(Date(2014, 2, 14))
    Date(2012, 5, 5).withDayOfYear(lastDay of February) should be(Date(2012, 2, 29))
    Date(2014, 5, 5).withDayOfYear(10 th Friday) should be(Date(2014, 3, 7))
  }

  "firstDayOfYear" should "work" in {
    Date(2014, 4, 10).firstDayOfYear should be(Date(2014, 1, 1))
    Date(2014, 1, 1).firstDayOfYear should be(Date(2014, 1, 1))
  }

  "firstDayOfNextYear" should "work" in {
    Date(2014, 4, 10).firstDayOfNextYear should be(Date(2015, 1, 1))
    Date(2014, 1, 1).firstDayOfNextYear should be(Date(2015, 1, 1))
  }

  "firstDayOfPreviousYear" should "work" in {
    Date(2014, 4, 10).firstDayOfPreviousYear should be(Date(2013, 1, 1))
    Date(2014, 1, 1).firstDayOfPreviousYear should be(Date(2013, 1, 1))
  }

  "lastDayOfYear" should "work" in {
    Date(2014, 4, 10).lastDayOfYear should be(Date(2014, 12, 31))
    Date(2014, 12, 31).lastDayOfYear should be(Date(2014, 12, 31))
  }

  "lastDayOfNextYear" should "work" in {
    Date(2014, 4, 10).lastDayOfNextYear should be(Date(2015, 12, 31))
    Date(2014, 12, 31).lastDayOfNextYear should be(Date(2015, 12, 31))
  }

  "lastDayOfPreviousYear" should "work" in {
    Date(2014, 4, 10).lastDayOfPreviousYear should be(Date(2013, 12, 31))
    Date(2014, 12, 31).lastDayOfPreviousYear should be(Date(2013, 12, 31))
  }

  "sameWeekdaysOfYear" should "contain leap day" in {
    Date(2016, 3, 21).sameWeekdaysOfYear should contain(Date(2016, 2, 29))
  }

  "nextOrSame" should "work" in {
    Date(2014, 4, 10).nextOrSame(FirstDayOfYear) should be(Date(2015, 1, 1))
    Date(2014, 1, 1).nextOrSame(FirstDayOfYear) should be(Date(2014, 1, 1))
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

  "previousOrSame" should "work" in {
    Date(2014, 4, 10).previousOrSame(FirstDayOfYear) should be(Date(2014, 1, 1))
    Date(2014, 1, 1).previousOrSame(FirstDayOfYear) should be(Date(2014, 1, 1))
  }

  "previous" should "work" in {
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

  "dayOfWeekInYear" should "work" in {
    Date(2014, 2, 9).dayOfWeekInYear(10, Friday) should be(Date(2014, 3, 7))
  }

  "firstInYear" should "work" in {
    Date(2014, 2, 9).firstInYear(Friday) should be(Date(2014, 1, 3))
    Date(2014, 2, 9).firstInYear(Friday, February) should be(Date(2014, 2, 7))
    Date(2014, 2, 9).firstInYear(February) should be(Date(2014, 2, 1))
  }

  "lastInYear" should "work" in {
    Date(2014, 2, 9).lastInYear(Friday) should be(Date(2014, 12, 26))
    Date(2014, 2, 9).lastInYear(Friday, February) should be(Date(2014, 2, 28))
    Date(2014, 2, 9).lastInYear(February) should be(Date(2014, 2, 28))
  }
}
