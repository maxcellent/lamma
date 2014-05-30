package io.lamma

import org.scalatest.{Matchers, FlatSpec}
import io.lamma.Duration.Days

class DurationSpec extends FlatSpec with Matchers {

  "when length is less than 1 there" should "be exception" in {
    intercept[IllegalArgumentException] {
      Days(-1)
    }
  }

  "implicit duration" should "work" in {
    import Duration._

    (1 day) should be(Days(1))
    (5 days) should be(Days(5))
    (1 week) should be(Weeks(1))
    (5 weeks) should be(Weeks(5))
    (1 month) should be(Months(1))
    (5 months) should be(Months(5))
    (1 year) should be(Years(1))
    (5 years) should be(Years(5))
  }
}
