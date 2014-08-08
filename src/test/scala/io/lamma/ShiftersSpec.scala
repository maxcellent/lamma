package io.lamma

import io.lamma.Shifters._
import io.lamma.Shifter.{ShiftWorkingDays, ShiftCalendarDays, NoShift}
import org.scalatest.{Matchers, FlatSpec}

class ShiftersSpec extends FlatSpec with Matchers {

  "noShift" should "be the same as lamma version" in {
    NO_SHIFT should be(NoShift)
  }

  "newCalendarDaysShifter" should "be the same as lamma version" in {
    newCalendarDaysShifter(5) should be(ShiftCalendarDays(5))
  }

  "newWorkingDaysShifter" should "be the same as lamma version" in {
    newWorkingDaysShifter(10) should be(ShiftWorkingDays(10))
    newWorkingDaysShifter(10, HolidayRules.NO_HOLIDAY) should be(ShiftWorkingDays(10, NoHoliday))
  }

}
