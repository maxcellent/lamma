package io.lamma.partial.date

import io.lamma._
import org.scalatest.{Matchers, FlatSpec}

class WeekOpsSpec extends FlatSpec with Matchers {

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
}
