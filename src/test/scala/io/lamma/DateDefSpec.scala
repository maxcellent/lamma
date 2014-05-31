package io.lamma

import org.scalatest.{Matchers, FlatSpec}
import io.lamma.Anchor.{OtherDateDef, PeriodEnd}
import io.lamma.Selector.Forward
import io.lamma.Shifter.ShiftCalendarDays

class DateDefSpec extends FlatSpec with Matchers {

  "populate" should "combine everything together" in {
    val dateDef = DateDef("Coupon Date", PeriodEnd, ShiftCalendarDays(2), Forward())
    val period = Period((2014, 5, 20) -> (2014, 5, 29))
    dateDef.populate(period) should be(Date(2014, 6, 2))
  }

  "validate" should "throw exception when some DateDef name is not unique" in {
    val def1 = DateDef("Date1", PeriodEnd, ShiftCalendarDays(2), Forward())
    val def2 = DateDef("Date2", PeriodEnd, ShiftCalendarDays(2), Forward())
    val def3 = DateDef("Date1", PeriodEnd, ShiftCalendarDays(2), Forward())
    val defs = def1 :: def2 :: def3 :: Nil
    intercept[IllegalArgumentException] {
      DateDef.validate(defs)
    }
  }

  "validate" should "throw exception when OtherDateDef.name does not exist" in {
    val def1 = DateDef("Date1", PeriodEnd, ShiftCalendarDays(2), Forward())
    val def2 = DateDef("Date2", OtherDateDef("Date4"), ShiftCalendarDays(2), Forward())
    val def3 = DateDef("Date3", PeriodEnd, ShiftCalendarDays(2), Forward())
    val defs = def1 :: def2 :: def3 :: Nil
    intercept[IllegalArgumentException] {
      DateDef.validate(defs)
    }
  }

  "validate" should "throw exception when OtherDateDef.name comes later" in {
    val def1 = DateDef("Date1", PeriodEnd, ShiftCalendarDays(2), Forward())
    val def2 = DateDef("Date2", OtherDateDef("Date3"), ShiftCalendarDays(2), Forward())
    val def3 = DateDef("Date3", PeriodEnd, ShiftCalendarDays(2), Forward())
    val defs = def1 :: def2 :: def3 :: Nil
    intercept[IllegalArgumentException] {
      DateDef.validate(defs)
    }
  }
}
