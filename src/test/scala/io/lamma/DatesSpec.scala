package io.lamma

import io.lamma.DayOfMonth.NthDayOfMonth
import io.lamma.DayOfYear.NthDayOfYear
import io.lamma.Selector.{Backward, Forward, ModifiedFollowing, ModifiedPreceding}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

import scala.collection.JavaConversions._

@RunWith(classOf[JUnitRunner])
class DatesSpec extends WordSpec with Matchers {

  // ========== date & calendars ==========
  "newDate" should {
    "construct lamma date" in {
      Dates.newDate(2014, 5, 5) should be(Date(2014, 5, 5))
    }

    "throw exception when yyyy is null" in {
      intercept[IllegalArgumentException] {
        Dates.newDate(null, 5, 5)
      }
    }

    "throw exception when mm is null" in {
      intercept[IllegalArgumentException] {
        Dates.newDate(2014, null, 5)
      }
    }

    "throw exception when dd is null" in {
      intercept[IllegalArgumentException] {
        Dates.newDate(2014, 5, null)
      }
    }
  }

  "newDate" should {
    "construct lamma date from ISO string" in {
      Dates.newDate("2014-05-05") should be(Date(2014, 5, 5))
    }

    "throw exception when input string is null" in {
      intercept[IllegalArgumentException] {
        Dates.newDate(null)
      }
    }
  }

  "from / to helper" should {
    "construct Dates object properly with Date object" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)) should be(new Dates(Date(2014, 5, 5), Date(2014, 5, 10)))
    }

    "construct Dates object properly with ints" in {
      Dates.from(2014, 5, 5).to(2014, 5, 10) should be(new Dates(Date(2014, 5, 5), Date(2014, 5, 10)))
    }

    "construct Dates object properly with iso format" in {
      Dates.from("2014-05-05").to("2014-05-10") should be(new Dates(Date(2014, 5, 5), Date(2014, 5, 10)))
    }
  }

  "byXXX" should {
    "work for by" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).by(2).getDuration should be(DayDuration(2))
    }

    "work for byDay" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byDay().getDuration should be(DayDuration(1))
    }

    "work for byDays" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byDays(4).getDuration should be(DayDuration(4))
    }

    "work for byWeek" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byWeek().getDuration should be(WeekDuration(1))
    }

    "work for byWeeks" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byWeeks(3).getDuration should be(WeekDuration(3))
    }

    "work for byMonth" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byMonth().getDuration should be(MonthDuration(1))
    }

    "work for byMonths" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byMonths(2).getDuration should be(MonthDuration(2))
    }

    "work for byYear" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byYear().getDuration should be(YearDuration(1))
    }

    "work for byYears" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).byYears(5).getDuration should be(YearDuration(5))
    }
  }

  "except" should {
    "work" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).except(Weekends).getHoliday should be(Weekends)
    }

    val anotherHoliday = SimpleHolidayRule.fromISOStrings("2014-05-20", "2014-05-30")
    val expected = Weekends and anotherHoliday

    "work for chained except methods" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).except(Weekends).except(anotherHoliday).getHoliday should be(expected)
    }

    "work for `and` except methods" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).except(Weekends and anotherHoliday).getHoliday should be(expected)
    }
  }

  "on" should {
    "work with weekday" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).on(Tuesday).getLoc should be(Locator(2))
    }

    "work with locator" in {
      val loc = Locator(2)
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).on(loc).getLoc should be(loc)
    }

    "work with locator builder" in {
      val builder = Locators.lastDay()
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).on(builder).getLoc should be(lastDay)
    }
  }

  "shift" should {
    "work with calendar days" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).shift(-2).getShifters.get(0) should be(Shifter(-2))
    }

    "work with working days" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).shift(-2, Weekends).getShifters.get(0) should be(Shifter(-2, Weekends))
    }

    "work with shifter object" in {
      val shifter = Shifter(-2, Weekends)
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).shift(shifter).getShifters.get(0) should be(shifter)
    }

    "work with chained shifters" in {
      val shifter1 = Shifter(-2, Weekends)
      val shifter2 = Shifter(2)
      val expected = shifter1 :: shifter2 :: Nil
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).shift(shifter1).shift(shifter2).getShifters.toList should be(expected)
    }
  }

  "select" should {
    "work with forward selector" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).forward(Weekends).getSelector should be(Forward(Weekends))
    }

    "work with backward selector" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).backward(Weekends).getSelector should be(Backward(Weekends))
    }

    "work with modifiedFollowing selector" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).modifiedFollowing(Weekends).getSelector should be(ModifiedFollowing(Weekends))
    }

    "work with modifiedPreceding selector" in {
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).modifiedPreceding(Weekends).getSelector should be(ModifiedPreceding(Weekends))
    }

    "work with selector" in {
      val selector = ModifiedPreceding(Weekends)
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).select(selector).getSelector should be(selector)
    }
  }

  "customDayOfMonth" should {
    "work" in {
      val dom = NthDayOfMonth(10)
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).on(dom).getCustomDayOfMonth should be(dom)
    }
  }

  "customDayOfYear" should {
    "work" in {
      val doy = NthDayOfYear(10)
      Dates.from(Date(2014, 5, 5)).to(Date(2014, 5, 10)).on(doy).getCustomDayOfYear should be(doy)
    }
  }
}
