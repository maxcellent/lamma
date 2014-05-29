package com.lamma4s

import org.scalatest.{Matchers, WordSpec}

class HolidayCalendarSpec extends WordSpec with Matchers {

  "shiftBizDay" should {
    "return date if by is 0" in {
      HolidayCalendar.shiftBizDay(Date(2014, 4, 10), 0, WeekendHolidayCalendar) should be(Date(2014, 4, 10))
    }

    "shift biz day forward when start day is working day" in {
      HolidayCalendar.shiftBizDay(Date(2014, 4, 10), 10, WeekendHolidayCalendar) should be(Date(2014, 4, 24))
    }

    "shift biz day backward when start day is working day" in {
      HolidayCalendar.shiftBizDay(Date(2014, 4, 10), -5, WeekendHolidayCalendar) should be(Date(2014, 4, 3))
    }

    "shift biz day forward when start day is NOT working day" in {
      HolidayCalendar.shiftBizDay(Date(2014, 4, 11), 3, WeekendHolidayCalendar) should be(Date(2014, 4, 16))
      HolidayCalendar.shiftBizDay(Date(2014, 4, 12), 3, WeekendHolidayCalendar) should be(Date(2014, 4, 16))
      HolidayCalendar.shiftBizDay(Date(2014, 4, 13), 3, WeekendHolidayCalendar) should be(Date(2014, 4, 16))
    }

    "shift biz day backward when start day is NOT working day" in {
      HolidayCalendar.shiftBizDay(Date(2014, 4, 12), -3, WeekendHolidayCalendar) should be(Date(2014, 4, 9))
      HolidayCalendar.shiftBizDay(Date(2014, 4, 13), -3, WeekendHolidayCalendar) should be(Date(2014, 4, 9))
      HolidayCalendar.shiftBizDay(Date(2014, 4, 14), -3, WeekendHolidayCalendar) should be(Date(2014, 4, 9))
    }
  }

  "forward" should {
    "return itself when the input date is not holiday" in {
      WeekendHolidayCalendar.forward(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "forward when the input the date is a holiday" in {
      WeekendHolidayCalendar.forward(Date(2014, 4, 12)) should be(Date(2014, 4, 14))
      WeekendHolidayCalendar.forward(Date(2014, 4, 13)) should be(Date(2014, 4, 14))
    }
  }

  "backward" should {
    "return itself when the input date is not holiday" in {
      WeekendHolidayCalendar.backward(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "backward when the input the date is a holiday" in {
      WeekendHolidayCalendar.backward(Date(2014, 4, 12)) should be(Date(2014, 4, 11))
      WeekendHolidayCalendar.backward(Date(2014, 4, 13)) should be(Date(2014, 4, 11))
    }
  }

  "modifiedFollowing" should {
    "return itself when the input date is not holiday" in {
      WeekendHolidayCalendar.modifiedFollowing(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "forward when the input the date is a holiday" in {
      WeekendHolidayCalendar.modifiedFollowing(Date(2014, 4, 12)) should be(Date(2014, 4, 14))
      WeekendHolidayCalendar.modifiedFollowing(Date(2014, 4, 13)) should be(Date(2014, 4, 14))
    }

    "backward when the forward date cross new month" in {
      WeekendHolidayCalendar.modifiedFollowing(Date(2014, 5, 31)) should be(Date(2014, 5, 30))
    }
  }

  "modifiedPreceding" should {
    "return itself when the input date is not holiday" in {
      WeekendHolidayCalendar.modifiedPreceding(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "backward when the input the date is a holiday" in {
      WeekendHolidayCalendar.modifiedPreceding(Date(2014, 4, 12)) should be(Date(2014, 4, 11))
      WeekendHolidayCalendar.modifiedPreceding(Date(2014, 4, 13)) should be(Date(2014, 4, 11))
    }

    "backward when the forward date cross new month" in {
      WeekendHolidayCalendar.modifiedPreceding(Date(2014, 6, 1)) should be(Date(2014, 6, 2))
    }
  }
}
