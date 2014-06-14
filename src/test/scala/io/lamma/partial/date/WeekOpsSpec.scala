package io.lamma.partial.date

import io.lamma._
import org.scalatest.{Matchers, FlatSpec}

class WeekOpsSpec extends FlatSpec with Matchers {

  "dayOfWeek" should "work" in {
    Date(2014, 4, 10).dayOfWeek should be(Thursday)
  }

  "thisWeekBegin" should "work" in {
    Date(2014, 6, 9).thisWeekBegin should be(Date(2014, 6, 9))
    Date(2014, 6, 12).thisWeekBegin should be(Date(2014, 6, 9))
  }

  "thisWeekEnd" should "work" in {
    Date(2014, 6, 8).thisWeekEnd should be(Date(2014, 6, 8))
    Date(2014, 6, 5).thisWeekEnd should be(Date(2014, 6, 8))
  }

  "comingWeekday" should "work" in {
    Date(2014, 4, 10).comingWeekday(Monday) should be(Date(2014, 4, 14))
    Date(2014, 4, 10).comingWeekday(Thursday) should be(Date(2014, 4, 17))

    Date(2014, 4, 10).comingMonday should be(Date(2014, 4, 14))
    Date(2014, 4, 10).comingTuesday should be(Date(2014, 4, 15))
    Date(2014, 4, 10).comingWednesday should be(Date(2014, 4, 16))
    Date(2014, 4, 10).comingThursday should be(Date(2014, 4, 17))
    Date(2014, 4, 10).comingFriday should be(Date(2014, 4, 11))
    Date(2014, 4, 10).comingSaturday should be(Date(2014, 4, 12))
    Date(2014, 4, 10).comingSunday should be(Date(2014, 4, 13))
  }

  "pastWeekday" should "work" in {
    Date(2014, 4, 10).pastWeekday(Monday) should be(Date(2014, 4, 7))
    Date(2014, 4, 10).pastWeekday(Thursday) should be(Date(2014, 4, 3))

    Date(2014, 4, 10).pastMonday should be(Date(2014, 4, 7))
    Date(2014, 4, 10).pastTuesday should be(Date(2014, 4, 8))
    Date(2014, 4, 10).pastWednesday should be(Date(2014, 4, 9))
    Date(2014, 4, 10).pastThursday should be(Date(2014, 4, 3))
    Date(2014, 4, 10).pastFriday should be(Date(2014, 4, 4))
    Date(2014, 4, 10).pastSaturday should be(Date(2014, 4, 5))
    Date(2014, 4, 10).pastSunday should be(Date(2014, 4, 6))
  }
}
