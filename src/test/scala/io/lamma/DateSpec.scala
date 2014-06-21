package io.lamma

import org.scalatest.{Matchers, FlatSpec}

class DateSpec extends FlatSpec with Matchers {

  "init" should "throw exception when input date is not valid" in {
    intercept[IllegalArgumentException] {
      Date(2014, 13, 20)
    }
  }

  "+" should "work for int (days)" in {
    Date(2014, 4, 10) + 10 should be(Date(2014, 4, 20))
  }

  "+" should "work for days" in {
    Date(2014, 4, 10) + (7 days) should be(Date(2014, 4, 17))
  }

  "+" should "work for weeks" in {
    Date(2014, 4, 10) + (2 weeks) should be(Date(2014, 4, 24))
  }

  "+" should "work for months" in {
    Date(2014, 4, 10) + (5 months) should be(Date(2014, 9, 10))
  }

  "+" should "work for years" in {
    Date(2014, 4, 10) + (10 years) should be(Date(2024, 4, 10))
  }

  "-" should "work for int (days)" in {
    Date(2014, 4, 10) - 5 should be(Date(2014, 4, 5))
  }

  "-" should "work for days" in {
    Date(2014, 4, 10) - (4 days) should be(Date(2014, 4, 6))
  }

  "-" should "work for weeks" in {
    Date(2014, 4, 10) - (1 week) should be(Date(2014, 4, 3))
  }

  "-" should "work for months" in {
    Date(2014, 4, 10) - (5 months) should be(Date(2013, 11, 10))
  }

  "-" should "work for years" in {
    Date(2014, 4, 10) - (10 years) should be(Date(2004, 4, 10))
  }

  "-" should "work for Date object" in {
    Date(2014, 4, 10) - Date(2014, 4, 10) should be(0)
    Date(2014, 4, 10) - Date(2014, 4, 5) should be(5)
    Date(2014, 4, 10) - Date(2014, 4, 15) should be(-5)
  }

  ">" should "work" in {
    Date(2014, 4, 10) > Date(2014, 4, 5) should be(true)
    Date(2014, 4, 10) > Date(2014, 4, 10) should be(false)
    Date(2014, 4, 10) > Date(2014, 4, 12) should be(false)
  }

  "<" should "work" in {
    Date(2014, 4, 10) < Date(2014, 4, 5) should be(false)
    Date(2014, 4, 10) < Date(2014, 4, 10) should be(false)
    Date(2014, 4, 10) < Date(2014, 4, 12) should be(true)
  }

  ">=" should "work" in {
    Date(2014, 4, 10) >= Date(2014, 4, 5) should be(true)
    Date(2014, 4, 10) >= Date(2014, 4, 10) should be(true)
    Date(2014, 4, 10) >= Date(2014, 4, 12) should be(false)
  }

  "<=" should "work" in {
    Date(2014, 4, 10) <= Date(2014, 4, 5) should be(false)
    Date(2014, 4, 10) <= Date(2014, 4, 10) should be(true)
    Date(2014, 4, 10) <= Date(2014, 4, 12) should be(true)
  }

  "forward" should "work" in {
    Date(2016, 2, 5).forward(Weekends) should be(Date(2016, 2, 5))
    Date(2016, 2, 6).forward(Weekends) should be(Date(2016, 2, 8))
  }

  "backward" should "work" in {
    Date(2016, 2, 5).backward(Weekends) should be(Date(2016, 2, 5))
    Date(2016, 2, 6).backward(Weekends) should be(Date(2016, 2, 5))
  }

  "modifiedFollowing" should "work" in {
    Date(2016, 2, 27).modifiedFollowing(Weekends) should be(Date(2016, 2, 29))
    Date(2016, 4, 30).modifiedFollowing(Weekends) should be(Date(2016, 4, 29))
  }

  "modifiedPreceding" should "work" in {
    Date(2016, 2, 27).modifiedPreceding(Weekends) should be(Date(2016, 2, 26))
    Date(2016, 5, 1).modifiedPreceding(Weekends) should be(Date(2016, 5, 2))
  }

  "Date.monthsBetween" should "work" in {
    Date.monthsBetween((2014, 4, 10) -> (2014, 4, 20)) should be(0)
    Date.monthsBetween((2014, 4, 10) -> (2014, 5, 10)) should be(1)
    Date.monthsBetween((2014, 4, 10) -> (2014, 6,  9)) should be(1)
    Date.monthsBetween((2014, 4, 10) -> (2014, 6, 10)) should be(2)
    Date.monthsBetween((2016, 2, 29) -> (2017, 2, 28)) should be(12)
  }

  "Date.yearsBetween" should "work" in {
    Date.yearsBetween((2014, 4, 10) -> (2015, 4, 5)) should be(0)
    Date.yearsBetween((2014, 4, 10) -> (2015, 4, 10)) should be(1)
    Date.yearsBetween((2014, 4, 10) -> (2016, 4, 9)) should be(1)
  }

  "toISOString" should "work" in {
    Date(978, 1, 5).toISOString should be("0978-01-05")
    Date(2014, 11, 29).toISOString should be("2014-11-29")
  }

  "apply" should "support ISO string" in {
    Date("2014-05-20") should be(Date(2014, 5, 20))

    intercept[IllegalArgumentException] {
      Date("2014-0520")
    }
  }
}
