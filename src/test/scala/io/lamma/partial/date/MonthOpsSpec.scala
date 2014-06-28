package io.lamma.partial.date

import io.lamma._
import io.lamma.DayOfMonth.NthDayOfMonth
import org.scalatest.{Matchers, FlatSpec}

class MonthOpsSpec extends FlatSpec with Matchers {

  "dayOfMonth" should "throw exception when input DayOfMonth is invalid" in {
    val dom = new DayOfMonth {
      override def isValidDOM(d: Date) = d.dd == 31
    }
    intercept[IllegalArgumentException] {
      Date(2014, 4, 10).dayOfMonth(dom)
    }
  }

  "dayOfMonth" should "work" in {
    Date(2014, 4, 10).dayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
    Date(2014, 4, 9).dayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
    Date(2014, 4, 11).dayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
  }

  "firstDayOfMonth" should "work" in {
    Date(2014, 4, 10).firstDayOfMonth should be(Date(2014, 4, 1))
    Date(2014, 4, 1).firstDayOfMonth should be(Date(2014, 4, 1))
  }

  "lastDayOfMonth" should "work" in {
    Date(2014, 4, 10).lastDayOfMonth should be(Date(2014, 4, 30))
    Date(2014, 2, 10).lastDayOfMonth should be(Date(2014, 2, 28))
    Date(2016, 2, 10).lastDayOfMonth should be(Date(2016, 2, 29))
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

  "comingLastDayOfMonth" should "work" in {
    Date(2014, 7, 30).nextLastDayOfMonth should be(Date(2014, 7, 31))
    Date(2014, 7, 31).nextLastDayOfMonth should be(Date(2014, 8, 31))
  }

  "comingFirstDayOfMonth" should "work" in {
    Date(2014, 7, 31).nextFirstDayOfMonth should be(Date(2014, 8, 1))
    Date(2014, 8,  1).nextFirstDayOfMonth should be(Date(2014, 9, 1))
  }

  "pastDayOfMonth" should "work" in {
    Date(2014, 4, 10).pastDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 3, 10))
    Date(2014, 4, 9).pastDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 3, 10))
    Date(2014, 4, 11).pastDayOfMonth(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
  }

  "previousLastDayOfMonth" should "work" in {
    Date(2014, 8, 5).previousLastDayOfMonth should be(Date(2014, 7, 31))
    Date(2014, 7, 31).previousLastDayOfMonth should be(Date(2014, 6, 30))
  }

  "previousFirstDayOfMonth" should "work" in {
    Date(2014, 8, 2).previousFirstDayOfMonth should be(Date(2014, 8, 1))
    Date(2014, 8, 1).previousFirstDayOfMonth should be(Date(2014, 7, 1))
  }

  "firstDayOfNextMonth" should "work" in {
    Date(2014, 7, 31).firstDayOfNextMonth should be(Date(2014, 8, 1))
    Date(2014, 8,  1).firstDayOfNextMonth should be(Date(2014, 9, 1))
  }

  "lastDayOfNextMonth" should "work" in {
    Date(2014, 7, 30).lastDayOfNextMonth should be(Date(2014, 8, 31))
    Date(2014, 7, 31).lastDayOfNextMonth should be(Date(2014, 8, 31))
  }

  "lastDayOfPreviousMonth" should "work" in {
    Date(2014, 8, 5).lastDayOfPreviousMonth should be(Date(2014, 7, 31))
    Date(2014, 7, 31).lastDayOfPreviousMonth should be(Date(2014, 6, 30))
  }

  "firstDayOfPreviousMonth" should "work" in {
    Date(2014, 8, 2).firstDayOfPreviousMonth should be(Date(2014, 7, 1))
    Date(2014, 8, 1).firstDayOfPreviousMonth should be(Date(2014, 7, 1))
  }

  "maxDayOfMonth" should "work" in {
    Date(2014, 1, 5).maxDayOfMonth should be(31)
    Date(2014, 2, 5).maxDayOfMonth should be(28)
    Date(2016, 2, 5).maxDayOfMonth should be(29)
    Date(2016, 6, 5).maxDayOfMonth should be(30)
  }

  "nthDayOfMonth" should "work" in {
    Date(2014, 5, 9).nthDayOfMonth should be(9)
  }

  "monthSinceBC" should "work" in {
    Date(2000, 5, 5).monthSinceBC should be(24005)
  }

  "dayOfWeekInMonth" should "work" in {
    Date(2014, 7, 5).dayOfWeekInMonth(2, Friday) should be(Date(2014, 7, 11))
  }

  "firstInMonth" should "work" in {
    Date(2014, 7, 5).firstInMonth(Friday) should be(Date(2014, 7, 4))
  }

  "lastInMonth" should "work" in {
    Date(2014, 7, 5).lastInMonth(Friday) should be(Date(2014, 7, 25))
  }
}
