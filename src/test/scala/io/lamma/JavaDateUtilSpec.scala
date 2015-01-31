package io.lamma

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{WordSpec, Matchers}
import JavaDateUtil._
import java.util.TimeZone

@RunWith(classOf[JUnitRunner])
class JavaDateUtilSpec extends WordSpec with Matchers {

  "calendar" should {
    "construct date in UTC timezone" in {
      calendar(2015, 1, 1).getTimeZone should be(TimeZone.getTimeZone("UTC"))
    }

    "construct date properly" in {
      val expectedMillis = 1420070400000l   // Thu Jan 01 00:00:00 GMT 2015
      calendar(2015, 1, 1).getTimeInMillis should be(expectedMillis)
    }
  }

  "date" should {
    "construct io.lamma.Date properly" in {
      date(calendar(2014, 5, 1)) should be(Date(2014, 5, 1))
    }
  }

  "plus" should {
    "work with days" in {
      plus(Date(2015, 2, 25), 1 day) should be(Date(2015, 2, 26))
    }

    "work with weeks" in {
      plus(Date(2015, 2, 25), 1 week) should be(Date(2015, 3, 4))
    }

    "work with months" in {
      plus(Date(2015, 1, 31), 1 month) should be(Date(2015, 2, 28))
      plus(Date(2016, 1, 31), 1 month) should be(Date(2016, 2, 29))
    }

    "work with years" in {
      plus(Date(2016, 2, 29), 1 year) should be(Date(2017, 2, 28))
    }
  }

  "daysBetween" should {
    "work with any two days" in {
      daysBetween(Date(2014, 1, 5), Date(2014, 1, 20)) should be(15)
    }

    "work with daylight saving" in {
      daysBetween(Date(2014, 3, 1), Date(2014, 4, 2)) should be(32)
    }
  }

  "monthsBetween" should {
    "return 0 for days in the same month" in {
      monthsBetween((2014, 1, 5), (2014, 1, 20)) should be(0)
    }

    "return 0 for not completed month, even they are on different month" in {
      monthsBetween((2014, 1, 5), (2014, 2, 4)) should be(0)
    }

    "return completed month" in {
      monthsBetween((2014, 1, 5), (2014, 2, 5)) should be(1)
    }

    "consider leap month" in {
      monthsBetween((2016, 2, 29), (2017, 2, 28)) should be(12)
    }
  }

  "yearsBetween" should {
    "return 0 for days in the same year" in {
      yearsBetween(Date(2014, 1, 5), Date(2014, 5, 1)) should be(0)
    }

    "return 0 for not completed year, even yyyy is different" in {
      yearsBetween(Date(2014, 1, 5), Date(2015, 1, 4)) should be(0)
    }

    "return complete year" in {
      yearsBetween(Date(2014, 1, 5), Date(2015, 1, 5)) should be(1)
    }

    "consider leap year" in {
      yearsBetween(Date(2016, 2, 29), Date(2017, 2, 28)) should be(1)
    }
  }
}
