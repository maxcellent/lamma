package io.lamma

import org.scalatest.{Matchers, FlatSpec}

class DayOfWeekSpec extends FlatSpec with Matchers {

  "ordinal" should "work" in {
    Monday.ordinal should be(1)
    Tuesday.ordinal should be(2)
    Wednesday.ordinal should be(3)
    Thursday.ordinal should be(4)
    Friday.ordinal should be(5)
    Saturday.ordinal should be(6)
    Sunday.ordinal should be(7)
  }

  "apply" should "work" in {
    DayOfWeek(1) should be(Monday)
    DayOfWeek(2) should be(Tuesday)
    DayOfWeek(3) should be(Wednesday)
    DayOfWeek(4) should be(Thursday)
    DayOfWeek(5) should be(Friday)
    DayOfWeek(6) should be(Saturday)
    DayOfWeek(7) should be(Sunday)
  }

}
