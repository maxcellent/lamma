package io.lamma

import org.scalatest.{Matchers, FlatSpec}

class DurationSpec extends FlatSpec with Matchers {

  "implicit duration" should "work" in {
    import Duration._

    (1 day) should be(DayDuration(1))
    (5 days) should be(DayDuration(5))
    (1 week) should be(WeekDuration(1))
    (5 weeks) should be(WeekDuration(5))
    (1 month) should be(MonthDuration(1))
    (5 months) should be(MonthDuration(5))
    (1 year) should be(YearDuration(1))
    (5 years) should be(YearDuration(5))
  }
}
