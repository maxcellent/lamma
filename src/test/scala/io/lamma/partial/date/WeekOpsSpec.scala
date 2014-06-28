package io.lamma.partial.date

import collection.JavaConversions._
import io.lamma._
import org.scalatest.{Matchers, FlatSpec}

class WeekOpsSpec extends FlatSpec with Matchers {

  "daysOfWeek4j" should "work" in {
    val d = Date(2014, 4, 15)
    d.daysOfWeek4j.toList should be(d.daysOfWeek.toList)
  }

  "dayOfWeek" should "work" in {
    Date(2014, 4, 10).dayOfWeek should be(Thursday)
  }

  "nextOrSame" should "work" in {
    Date(2014, 7, 5).nextOrSame(Monday) should be(Date(2014, 7, 7))
    Date(2014, 7, 5).nextOrSame(Saturday) should be(Date(2014, 7, 5)) // note 2014-07-05 itself is already Saturday
  }

  "next" should "work" in {
    Date(2014, 4, 10).next(Monday) should be(Date(2014, 4, 14))
    Date(2014, 4, 10).next(Thursday) should be(Date(2014, 4, 17))
  }

  "previousOrSame" should "work" in {
    Date("2014-07-05").previousOrSame(Monday) should be(Date("2014-06-30"))
    Date("2014-07-05").previousOrSame(Saturday) should be(Date("2014-07-05")) // note 2014-07-05 itself is already Saturday
  }

  "previous" should "work" in {
    Date(2014, 4, 10).previous(Monday) should be(Date(2014, 4, 7))
    Date(2014, 4, 10).previous(Thursday) should be(Date(2014, 4, 3))
  }

  "isWeekend" should "work" in {
    (2014, 8, 8).isWeekend should be(false)
    (2014, 8, 9).isWeekend should be(true)
    (2014, 8, 10).isWeekend should be(true)
  }
}
