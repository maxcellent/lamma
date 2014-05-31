package io.lamma

import org.scalatest.{Matchers, WordSpec}

class CalendarSpec extends WordSpec with Matchers {

  "shiftBizDay" should {
    "return date if by is 0" in {
      Calendar.shiftBizDay(Date(2014, 4, 10), 0, WeekendCalendar) should be(Date(2014, 4, 10))
    }

    "shift biz day forward when start day is working day" in {
      Calendar.shiftBizDay(Date(2014, 4, 10), 10, WeekendCalendar) should be(Date(2014, 4, 24))
    }

    "shift biz day backward when start day is working day" in {
      Calendar.shiftBizDay(Date(2014, 4, 10), -5, WeekendCalendar) should be(Date(2014, 4, 3))
    }

    "shift biz day forward when start day is NOT working day" in {
      Calendar.shiftBizDay(Date(2014, 4, 11), 3, WeekendCalendar) should be(Date(2014, 4, 16))
      Calendar.shiftBizDay(Date(2014, 4, 12), 3, WeekendCalendar) should be(Date(2014, 4, 16))
      Calendar.shiftBizDay(Date(2014, 4, 13), 3, WeekendCalendar) should be(Date(2014, 4, 16))
    }

    "shift biz day backward when start day is NOT working day" in {
      Calendar.shiftBizDay(Date(2014, 4, 12), -3, WeekendCalendar) should be(Date(2014, 4, 9))
      Calendar.shiftBizDay(Date(2014, 4, 13), -3, WeekendCalendar) should be(Date(2014, 4, 9))
      Calendar.shiftBizDay(Date(2014, 4, 14), -3, WeekendCalendar) should be(Date(2014, 4, 9))
    }
  }

  "forward" should {
    "return itself when the input date is not holiday" in {
      WeekendCalendar.forward(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "forward when the input the date is a holiday" in {
      WeekendCalendar.forward(Date(2014, 4, 12)) should be(Date(2014, 4, 14))
      WeekendCalendar.forward(Date(2014, 4, 13)) should be(Date(2014, 4, 14))
    }
  }

  "backward" should {
    "return itself when the input date is not holiday" in {
      WeekendCalendar.backward(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "backward when the input the date is a holiday" in {
      WeekendCalendar.backward(Date(2014, 4, 12)) should be(Date(2014, 4, 11))
      WeekendCalendar.backward(Date(2014, 4, 13)) should be(Date(2014, 4, 11))
    }
  }

  "modifiedFollowing" should {
    "return itself when the input date is not holiday" in {
      WeekendCalendar.modifiedFollowing(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "forward when the input the date is a holiday" in {
      WeekendCalendar.modifiedFollowing(Date(2014, 4, 12)) should be(Date(2014, 4, 14))
      WeekendCalendar.modifiedFollowing(Date(2014, 4, 13)) should be(Date(2014, 4, 14))
    }

    "backward when the forward date cross new month" in {
      WeekendCalendar.modifiedFollowing(Date(2014, 5, 31)) should be(Date(2014, 5, 30))
    }
  }

  "modifiedPreceding" should {
    "return itself when the input date is not holiday" in {
      WeekendCalendar.modifiedPreceding(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "backward when the input the date is a holiday" in {
      WeekendCalendar.modifiedPreceding(Date(2014, 4, 12)) should be(Date(2014, 4, 11))
      WeekendCalendar.modifiedPreceding(Date(2014, 4, 13)) should be(Date(2014, 4, 11))
    }

    "backward when the forward date cross new month" in {
      WeekendCalendar.modifiedPreceding(Date(2014, 6, 1)) should be(Date(2014, 6, 2))
    }
  }

  "CompositeCalendar" should {
    "always return false if no underlying calendar is defined" in {
      (Date(2014, 1, 1) to Date(2014, 12, 31)).exists(CompositeCalendar().isHoliday) should be(false)
    }

    "union results from underlying calendars" in {
      val simpleCal = SimpleCalendar(Date(2014, 6, 1), Date(2014, 6, 2))
      val compositeCal = CompositeCalendar(simpleCal, WeekendCalendar)

      compositeCal.isHoliday(Date(2014, 5, 30)) should be(false)
      compositeCal.isHoliday(Date(2014, 5, 31)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  1)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  2)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  3)) should be(false)
    }
  }
}
