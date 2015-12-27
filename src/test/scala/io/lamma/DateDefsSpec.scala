package io.lamma

import io.lamma.Anchor.PeriodStart
import io.lamma.Selector.Forward
import io.lamma.Shifter.ShiftCalendarDays
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class DateDefsSpec extends FlatSpec with Matchers {

  "DateDefs.of" should "be the same as lamma version" in {
    DateDefs.of("SomeDate") should be(DateDef("SomeDate"))
    DateDefs.of("SomeDate", Anchors.periodStart()) should be(DateDef("SomeDate", relativeTo = PeriodStart))
    DateDefs.of("SomeDate", Shifters.byCalendarDays(2)) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2)))
    DateDefs.of("SomeDate", Selectors.forward()) should be(DateDef("SomeDate", selector = Forward()))
    DateDefs.of("SomeDate", Anchors.periodStart(), Shifters.byCalendarDays(2)) should be(DateDef("SomeDate", relativeTo = PeriodStart, shifter = ShiftCalendarDays(2)))
    DateDefs.of("SomeDate", Anchors.periodStart(), Selectors.forward()) should be(DateDef("SomeDate", relativeTo = PeriodStart, selector = Forward()))
    DateDefs.of("SomeDate", Shifters.byCalendarDays(2), Selectors.forward()) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2), selector = Forward()))
    DateDefs.of("SomeDate", Anchors.periodStart(), Shifters.byCalendarDays(2), Selectors.forward()) should be(DateDef("SomeDate", PeriodStart, ShiftCalendarDays(2), Forward()))
  }
}

