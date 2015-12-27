package io.lamma

import io.lamma.Shifter.{NoShift, ShiftCalendarDays, ShiftWorkingDays}
import io.lamma.Shifters._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
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
