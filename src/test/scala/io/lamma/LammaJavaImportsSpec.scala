package io.lamma

import org.scalatest.{FlatSpec, Matchers}
import LammaJavaImports._
import io.lamma.Shifter.NoShift
import io.lamma.Selector._
import DayOfMonth._
import DayOfYear._
import io.lamma.DayOfMonth.LastWeekdayOfMonth
import io.lamma.DayOfMonth.NthWeekdayOfMonth
import io.lamma.Selector.ModifiedPreceding
import io.lamma.Shifter.ShiftCalendarDays
import io.lamma.Selector.Backward
import io.lamma.DayOfYear.NthMonthOfYear
import io.lamma.Selector.ModifiedFollowing
import io.lamma.DayOfMonth.NthDayOfMonth
import io.lamma.Selector.Forward
import io.lamma.Shifter.ShiftWorkingDays
import io.lamma.DayOfYear.NthWeekdayOfYear
import io.lamma.DayOfYear.NthDayOfYear
import io.lamma.StubRulePeriodBuilder.{ShortEnd, LongEnd, ShortStart, LongStart}
import io.lamma.Anchor.{PeriodEnd, PeriodStart, OtherDate}
import com.google.common.collect.Lists
import DayOfWeek._

class LammaJavaImportsSpec extends FlatSpec with Matchers {

  "list" should "convert java array to scala iterable" in {
    list(1, 2, 3, 4, 5) should be(List(1, 2, 3, 4, 5))
  }

  "set" should "convert java array to scala set" in {
    set(1, 2, 3, 4, 5) should be(Set(1, 2, 3, 4, 5))
  }

  "scalaList" should "convert a java list to scala list" in {
    scalaList(Lists.newArrayList(1, 2, 3)) should be(List(1, 2, 3))
  }

  "javaList" should "convert scala list to Java list" in {
    javaList(List(1, 2, 3)) should be(Lists.newArrayList(1, 2, 3))
  }

  // ============== datedef and anchor ===============
  "dateDef" should "be the same as lamma version" in {
    dateDef("SomeDate") should be(DateDef("SomeDate"))
    dateDef("SomeDate", Anchors.periodStart()) should be(DateDef("SomeDate", relativeTo = PeriodStart))
    dateDef("SomeDate", Shifters.newCalendarDaysShifter(2)) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2)))
    dateDef("SomeDate", Selectors.newForwardSelector()) should be(DateDef("SomeDate", selector = Forward()))
    dateDef("SomeDate", Anchors.periodStart(), Shifters.newCalendarDaysShifter(2)) should be(DateDef("SomeDate", relativeTo = PeriodStart, shifter = ShiftCalendarDays(2)))
    dateDef("SomeDate", Anchors.periodStart(), Selectors.newForwardSelector()) should be(DateDef("SomeDate", relativeTo = PeriodStart, selector = Forward()))
    dateDef("SomeDate", Shifters.newCalendarDaysShifter(2), Selectors.newForwardSelector()) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2), selector = Forward()))
    dateDef("SomeDate", Anchors.periodStart(), Shifters.newCalendarDaysShifter(2), Selectors.newForwardSelector()) should be(DateDef("SomeDate", PeriodStart, ShiftCalendarDays(2), Forward()))
  }
}
