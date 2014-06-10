package io.lamma

import org.scalatest.{Matchers, FlatSpec}

class DateRangeSpec extends FlatSpec with Matchers {

  "DateRange" should "work" in {
    val expected = Date(2014, 1, 5) :: Date(2014, 1, 6) :: Date(2014, 1, 7) :: Date(2014, 1, 8) :: Nil
    DateRange(Date(2014, 1, 5), Date(2014, 1, 8)).toList should be(expected)
  }

}
