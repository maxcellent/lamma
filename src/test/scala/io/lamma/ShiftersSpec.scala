package io.lamma

import io.lamma.Shifters._
import io.lamma.Shifter.{ShiftWorkingDays, ShiftCalendarDays, NoShift}
import org.scalatest.{Matchers, FlatSpec}

class ShiftersSpec extends FlatSpec with Matchers {

  "noShift" should "be the same as lamma version" in {
    noShift should be(NoShift)
  }

  "byCalendarDays" should "be the same as lamma version" in {
    byCalendarDays(5) should be(ShiftCalendarDays(5))
  }

  "byWorkingDays" should "be the same as lamma version" in {
    byWorkingDays(10) should be(ShiftWorkingDays(10))
    byWorkingDays(10, HolidayRules.noHoliday()) should be(ShiftWorkingDays(10, NoHoliday))
  }

}
