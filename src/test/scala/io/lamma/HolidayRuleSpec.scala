package io.lamma

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
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

  "and" should {
    "return holiday itself if it's merging with NoHoliday" in {
      Weekends and NoHoliday should be(Weekends)
      NoHoliday and Weekends should be(Weekends)
    }

    "return composite holiday rule if none of them are composite holiday" in {
      Weekends and SimpleHolidayRule() should be(CompositeHolidayRule(Weekends, SimpleHolidayRule()))
    }

    "return composite holiday rule if one of them are already composite holiday" in {
      val rule1 = Weekends
      val rule2 = CompositeHolidayRule(SimpleHolidayRule())
      val expected = CompositeHolidayRule(SimpleHolidayRule(), Weekends)

      rule1 and rule2 should be(expected)
      rule2 and rule1 should be(expected)
    }

    "merge composite holidays if both of them are composite holiday" in {
      val rule1 = CompositeHolidayRule(Weekends)
      val rule2 = CompositeHolidayRule(SimpleHolidayRule())
      val expected = CompositeHolidayRule(SimpleHolidayRule(), Weekends)

      rule1 and rule2 should be(expected)
      rule2 and rule1 should be(expected)
    }

    "work with multiple holiday rule" in {
      val rule1 = SimpleHolidayRule( (2014, 5, 5), (2014, 6, 7))
      val rule2 = SimpleHolidayRule( (2014, 5, 10) )
      val rule3 = SimpleHolidayRule( (2014, 8, 10) )

      rule1 and rule2 and rule3 should be(CompositeHolidayRule(rule1, rule2, rule3))
    }
  }

  "CompositeHolidayRule" should {
    "always return false if no underlying rule is defined" in {
      (Date(2014, 1, 1) to Date(2014, 12, 31)).exists(CompositeHolidayRule().isHoliday) should be(false)
    }

    "union results from underlying calendars" in {
      val simpleCal = SimpleHolidayRule(Date(2014, 6, 1), Date(2014, 6, 2))
      val compositeCal = CompositeHolidayRule(simpleCal, Weekends)

      compositeCal.isHoliday(Date(2014, 5, 30)) should be(false)
      compositeCal.isHoliday(Date(2014, 5, 31)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  1)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  2)) should be(true)
      compositeCal.isHoliday(Date(2014, 6,  3)) should be(false)
    }
  }
}
