package io.lamma

import org.scalatest.{Matchers, WordSpec}

class HolidayRuleSpec extends WordSpec with Matchers {

  "shiftWorkingDay" should {
    "return date if by is 0" in {
      HolidayRule.shiftWorkingDay(Date(2014, 4, 10), 0, Weekends) should be(Date(2014, 4, 10))
    }

    "shift working day forward when start day is working day" in {
      HolidayRule.shiftWorkingDay(Date(2014, 4, 10), 10, Weekends) should be(Date(2014, 4, 24))
    }

    "shift working day backward when start day is working day" in {
      HolidayRule.shiftWorkingDay(Date(2014, 4, 10), -5, Weekends) should be(Date(2014, 4, 3))
    }

    "shift working day forward when start day is NOT working day" in {
      HolidayRule.shiftWorkingDay(Date(2014, 4, 11), 3, Weekends) should be(Date(2014, 4, 16))
      HolidayRule.shiftWorkingDay(Date(2014, 4, 12), 3, Weekends) should be(Date(2014, 4, 16))
      HolidayRule.shiftWorkingDay(Date(2014, 4, 13), 3, Weekends) should be(Date(2014, 4, 16))
    }

    "shift working day backward when start day is NOT working day" in {
      HolidayRule.shiftWorkingDay(Date(2014, 4, 12), -3, Weekends) should be(Date(2014, 4, 9))
      HolidayRule.shiftWorkingDay(Date(2014, 4, 13), -3, Weekends) should be(Date(2014, 4, 9))
      HolidayRule.shiftWorkingDay(Date(2014, 4, 14), -3, Weekends) should be(Date(2014, 4, 9))
    }
  }

  "forward" should {
    "return itself when the input date is not holiday" in {
      Weekends.forward(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "forward when the input the date is a holiday" in {
      Weekends.forward(Date(2014, 4, 12)) should be(Date(2014, 4, 14))
      Weekends.forward(Date(2014, 4, 13)) should be(Date(2014, 4, 14))
    }
  }

  "backward" should {
    "return itself when the input date is not holiday" in {
      Weekends.backward(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "backward when the input the date is a holiday" in {
      Weekends.backward(Date(2014, 4, 12)) should be(Date(2014, 4, 11))
      Weekends.backward(Date(2014, 4, 13)) should be(Date(2014, 4, 11))
    }
  }

  "modifiedFollowing" should {
    "return itself when the input date is not holiday" in {
      Weekends.modifiedFollowing(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "forward when the input the date is a holiday" in {
      Weekends.modifiedFollowing(Date(2014, 4, 12)) should be(Date(2014, 4, 14))
      Weekends.modifiedFollowing(Date(2014, 4, 13)) should be(Date(2014, 4, 14))
    }

    "backward when the forward date cross new month" in {
      Weekends.modifiedFollowing(Date(2014, 5, 31)) should be(Date(2014, 5, 30))
    }
  }

  "modifiedPreceding" should {
    "return itself when the input date is not holiday" in {
      Weekends.modifiedPreceding(Date(2014, 4, 10)) should be(Date(2014, 4, 10))
    }

    "backward when the input the date is a holiday" in {
      Weekends.modifiedPreceding(Date(2014, 4, 12)) should be(Date(2014, 4, 11))
      Weekends.modifiedPreceding(Date(2014, 4, 13)) should be(Date(2014, 4, 11))
    }

    "backward when the forward date cross new month" in {
      Weekends.modifiedPreceding(Date(2014, 6, 1)) should be(Date(2014, 6, 2))
    }
  }

  "CompositeCalendar" should {
    "always return false if no underlying rule is defined" in {
      (Date(2014, 1, 1) to Date(2014, 12, 31)).exists(CompositeHolidayRules().isHoliday) should be(false)
    }

    "union results from underlying calendars" in {
      val simpleCal = SimpleCalendar(Date(2014, 6, 1), Date(2014, 6, 2))
      val compositeCal = CompositeHolidayRules(simpleCal, Weekends)

      compositeCal.isHoliday(Date(2014, 5, 30)) should be(false)
      compositeCal.isHoliday(Date(2014, 5, 31)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  1)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  2)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  3)) should be(false)
    }
  }
}
