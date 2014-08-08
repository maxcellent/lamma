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

  // ========== recurrence pattern: day patterns ==============
  "everyDay" should "be the same as lamma version" in {
    everyDay() should be(Daily(1))
  }

  "everyOtherDay" should "be the same as lamma version" in {
    everyOtherDay() should be(Daily(2))
  }

  "days" should "be the same as lamma version" in {
    days(22) should be(Daily(22))
  }

  "daysBackward" should "be the same as lamma version" in {
    daysBackward(22) should be(Daily(-22))
  }

  // ========== recurrence pattern: week patterns ==============
  "everyWeek" should "be the same as lamma version" in {
    everyWeek() should be(Weekly(1))
  }

  "everyOtherWeek" should "be the same as lamma version" in {
    everyOtherWeek() should be(Weekly(2))
  }

  "weeks" should "be the same as lamma version" in {
    weeks(5) should be(Weekly(5))
    weeks(FRIDAY) should be(Weekly(1, Friday))
    weeks(2, TUESDAY) should be(Weekly(2, Tuesday))
  }

  "weeksBackward" should "be the same as lamma version" in {
    weeksBackward(5) should be(Weekly(-5))
    weeksBackward(FRIDAY) should be(Weekly(-1, Friday))
    weeksBackward(2, TUESDAY) should be(Weekly(-2, Tuesday))
  }

  // ========== recurrence pattern: month patterns ==============
  "everyMonth" should "be the same as lamma version" in {
    everyMonth() should be(Monthly(1))
  }

  "everyOtherMonth" should "be the same as lamma version" in {
    everyOtherMonth() should be(Monthly(2))
  }

  "months" should "be the same as lamma version" in {
    months(3) should be(Monthly(3))
    months(DayOfMonths.lastWeekdayOfMonth(FRIDAY)) should be(Monthly(1, LastWeekdayOfMonth(Friday)))
    months(5, DayOfMonths.firstDayOfMonth()) should be(Monthly(5, FirstDayOfMonth))
  }

  "monthsBackward" should "be the same as lamma version" in {
    monthsBackward(3) should be(Monthly(-3))
    monthsBackward(DayOfMonths.lastWeekdayOfMonth(FRIDAY)) should be(Monthly(-1, LastWeekdayOfMonth(Friday)))
    monthsBackward(5, DayOfMonths.firstDayOfMonth()) should be(Monthly(-5, FirstDayOfMonth))
  }

  // ========== recurrence pattern: year patterns ==============
  "everyYear" should "be the same as lamma version" in {
    everyYear() should be(Yearly(1))
  }

  "everyOtherYear" should "be the same as lamma version" in {
    everyOtherYear() should be(Yearly(2))
  }

  "years" should "be the same as lamma version" in {
    years(5) should be(Yearly(5))
    years(DayOfYears.firstDayOfYear()) should be(Yearly(1, FirstDayOfYear))
    years(2, DayOfYears.lastDayOfYear()) should be(Yearly(2, LastDayOfYear))
  }

  "yearsBackward" should "be the same as lamma version" in {
    yearsBackward(5) should be(Yearly(-5))
    yearsBackward(DayOfYears.firstDayOfYear()) should be(Yearly(-1, FirstDayOfYear))
    yearsBackward(2, DayOfYears.lastDayOfYear()) should be(Yearly(-2, LastDayOfYear))
  }

  // ============== period builder / stub rule ===============
  "longStart" should "be the same as lamma version" in {
    longStart(5) should be(LongStart(5))
    longStart() should be(LongStart())
  }

  "shortStart" should "be the same as lamma version" in {
    shortStart(5) should be(ShortStart(5))
    shortStart() should be(ShortStart())
  }

  "longEnd" should "be the same as lamma version" in {
    longEnd(5) should be(LongEnd(5))
    longEnd() should be(LongEnd())
  }

  "shortEnd" should "be the same as lamma version" in {
    shortEnd(5) should be(ShortEnd(5))
    shortEnd() should be(ShortEnd())
  }

  "stubRulePeriodBuilder" should "be the same as lamma version" in {
    stubRulePeriodBuilder() should be(StubRulePeriodBuilder())
    stubRulePeriodBuilder(shortStart(5)) should be(StubRulePeriodBuilder(startRule = ShortStart(5)))
    stubRulePeriodBuilder(longEnd(20)) should be(StubRulePeriodBuilder(endRule = LongEnd(20)))
    stubRulePeriodBuilder(shortStart(3), longEnd(6)) should be(StubRulePeriodBuilder(ShortStart(3), LongEnd(6)))
  }

  // ============== datedef and anchor ===============
  "periodEnd" should "be the same as lamma version" in {
    periodEnd() should be(PeriodEnd)
  }

  "periodStart" should "be the same as lamma version" in {
    periodStart() should be(PeriodStart)
  }

  "otherDate" should "be the same as lamma version" in {
    otherDate("SomeDate") should be(OtherDate("SomeDate"))
  }

  "dateDef" should "be the same as lamma version" in {
    dateDef("SomeDate") should be(DateDef("SomeDate"))
    dateDef("SomeDate", periodStart()) should be(DateDef("SomeDate", relativeTo = PeriodStart))
    dateDef("SomeDate", Shifters.newCalendarDaysShifter(2)) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2)))
    dateDef("SomeDate", Selectors.newForwardSelector()) should be(DateDef("SomeDate", selector = Forward()))
    dateDef("SomeDate", periodStart(), Shifters.newCalendarDaysShifter(2)) should be(DateDef("SomeDate", relativeTo = PeriodStart, shifter = ShiftCalendarDays(2)))
    dateDef("SomeDate", periodStart(), Selectors.newForwardSelector()) should be(DateDef("SomeDate", relativeTo = PeriodStart, selector = Forward()))
    dateDef("SomeDate", Shifters.newCalendarDaysShifter(2), Selectors.newForwardSelector()) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2), selector = Forward()))
    dateDef("SomeDate", periodStart(), Shifters.newCalendarDaysShifter(2), Selectors.newForwardSelector()) should be(DateDef("SomeDate", PeriodStart, ShiftCalendarDays(2), Forward()))
  }
}
