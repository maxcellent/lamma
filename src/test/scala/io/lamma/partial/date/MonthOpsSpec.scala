package io.lamma.partial.date

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import collection.JavaConversions._
import io.lamma._
import io.lamma.DayOfMonth.NthDayOfMonth
import org.scalatest.{Matchers, FlatSpec}

@RunWith(classOf[JUnitRunner])
class MonthOpsSpec extends FlatSpec with Matchers {

  "daysOfMonth4j" should "work" in {
    val d = Date(2014, 4, 15)
    d.daysOfMonth4j.toList should be(d.daysOfMonth.toList)
  }

  "sameWeekdaysOfMonth4j" should "work" in {
    val d = Date(2014, 4, 15)
    d.sameWeekdaysOfMonth4j.toList should be(d.sameWeekdaysOfMonth.toList)
  }

  "withDayOfMonth" should "throw exception when input DayOfMonth is invalid" in {
    val dom = new DayOfMonth {
      override def isValidDOM(d: Date) = d.dd == 31
    }
    intercept[IllegalArgumentException] {
      Date(2014, 4, 10).withDayOfMonth(dom)
    }
  }

  "withDayOfMonth" should "work" in {
    Date(2014, 4, 10).withDayOfMonth(10 th day) should be(Date(2014, 4, 10))
    Date(2014, 4, 9).withDayOfMonth(10 th day) should be(Date(2014, 4, 10))
    Date(2014, 4, 11).withDayOfMonth(10 th day) should be(Date(2014, 4, 10))
    Date(2014, 8, 15).withDayOfMonth(2 nd Friday) should be(Date(2014, 8, 8))
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

  "nextOrSameDayOfMonth" should "work" in {
    Date(2014, 4, 10).nextOrSameDayOfMonth(10 th day) should be(Date(2014, 4, 10))
    Date(2014, 4, 9).nextOrSameDayOfMonth(10 th day) should be(Date(2014, 4, 10))
    Date(2014, 4, 11).nextOrSameDayOfMonth(10 th day) should be(Date(2014, 5, 10))
  }

  "nextOrSame" should "work" in {
    Date(2014, 4, 10).nextOrSame(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
    Date(2014, 4, 9).nextOrSame(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
    Date(2014, 4, 11).nextOrSame(NthDayOfMonth(10)) should be(Date(2014, 5, 10))
  }

  "nextDayOfMonth" should "work" in {
    Date(2014, 4, 10).nextDayOfMonth(10 th day) should be(Date(2014, 5, 10))
    Date(2014, 4, 9).nextDayOfMonth(10 th day) should be(Date(2014, 4, 10))
    Date(2014, 4, 11).nextDayOfMonth(10 th day) should be(Date(2014, 5, 10))
  }

  "next" should "work" in {
    Date(2014, 4, 10).next(NthDayOfMonth(10)) should be(Date(2014, 5, 10))
    Date(2014, 4, 9).next(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
    Date(2014, 4, 11).next(NthDayOfMonth(10)) should be(Date(2014, 5, 10))
  }

  "nextLastDayOfMonth" should "work" in {
    Date(2014, 7, 30).nextLastDayOfMonth should be(Date(2014, 7, 31))
    Date(2014, 7, 31).nextLastDayOfMonth should be(Date(2014, 8, 31))
  }

  "nextFirstDayOfMonth" should "work" in {
    Date(2014, 7, 31).nextFirstDayOfMonth should be(Date(2014, 8, 1))
    Date(2014, 8,  1).nextFirstDayOfMonth should be(Date(2014, 9, 1))
  }

  "previousOrSameDayOfMonth" should "work" in {
    Date(2014, 4, 10).previousOrSameDayOfMonth(10 th day) should be(Date(2014, 4, 10))
    Date(2014, 4, 9).previousOrSameDayOfMonth(10 th day) should be(Date(2014, 3, 10))
    Date(2014, 4, 11).previousOrSameDayOfMonth(10 th day) should be(Date(2014, 4, 10))
  }

  "previousOrSame" should "work" in {
    Date(2014, 4, 10).previousOrSame(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
    Date(2014, 4, 9).previousOrSame(NthDayOfMonth(10)) should be(Date(2014, 3, 10))
    Date(2014, 4, 11).previousOrSame(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
  }

  "previousDayOfMonth" should "work" in {
    Date(2014, 4, 10).previousDayOfMonth(10 th day) should be(Date(2014, 3, 10))
    Date(2014, 4, 9).previousDayOfMonth(10 th day) should be(Date(2014, 3, 10))
    Date(2014, 4, 11).previousDayOfMonth(10 th day) should be(Date(2014, 4, 10))
  }

  "previous" should "work" in {
    Date(2014, 4, 10).previous(NthDayOfMonth(10)) should be(Date(2014, 3, 10))
    Date(2014, 4, 9).previous(NthDayOfMonth(10)) should be(Date(2014, 3, 10))
    Date(2014, 4, 11).previous(NthDayOfMonth(10)) should be(Date(2014, 4, 10))
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

  "dayOfMonth" should "work" in {
    Date(2014, 5, 9).dayOfMonth should be(9)
  }

  "dayOfWeekInMonth" should "work" in {
    Date(2014, 7, 5).dayOfWeekInMonth(2, Friday) should be(Date(2014, 7, 11))

    Date(2014, 7, 5).withDayOfMonth(2 nd Friday) should be(Date(2014, 7, 11))
  }

  "firstInMonth" should "work" in {
    Date(2014, 7, 5).firstInMonth(Friday) should be(Date(2014, 7, 4))
  }

  "lastInMonth" should "work" in {
    Date(2014, 7, 5).lastInMonth(Friday) should be(Date(2014, 7, 25))
  }
}
