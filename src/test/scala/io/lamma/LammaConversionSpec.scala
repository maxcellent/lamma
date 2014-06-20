package io.lamma

import org.scalatest.{FlatSpec, Matchers}
import LammaConversion._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import io.lamma.Shifter.NoShift
import io.lamma.Selector._
import LammaConst._
import DayOfMonth._
import DayOfYear._
import io.lamma._
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

@RunWith(classOf[JUnitRunner])
class LammaConversionSpec extends FlatSpec with Matchers {

  "javaList" should "convert scala list to Java list" in {
    javaList(List(1, 2, 3)) should be(Lists.newArrayList(1, 2, 3))
  }

  "list" should "convert java array to scala iterable" in {
    list(1, 2, 3, 4, 5) should be(List(1, 2, 3, 4, 5))
  }

  "set" should "convert java array to scala set" in {
    set(1, 2, 3, 4, 5) should be(Set(1, 2, 3, 4, 5))
  }

  // ========== date & calendars ==========
  "date" should "construct lamma date" in {
    date(2014, 5, 5) should be(Date(2014, 5, 5))
  }

  "noHoliday" should "be the same as lamma version" in {
    noHoliday() should be(NoHoliday)
  }

  "weekends" should "be the same as lamma version" in {
    weekends() should be(Weekends)
  }

  "simpeHolidayRule" should "be the same as lamma version" in {
    val expected = SimpleHolidayRule(Date(2014, 5, 1), Date(2014, 5, 2))
    simpleHolidayRule(date(2014, 5, 1), date(2014, 5, 2)) should be(expected)
    simpleHolidayRule(set(date(2014, 5, 1), date(2014, 5, 2))) should be(expected)
  }

  "compositeHolidayRules" should "be the same as lamma version" in {
    compositeHolidayRules(noHoliday(), weekends()) should be(CompositeHolidayRule(NoHoliday, Weekends))
  }

  // ========== shifters ==========
  "noShift" should "be the same as lamma version" in {
    noShift() should be(NoShift)
  }

  "shiftCalendarDays" should "be the same as lamma version" in {
    shiftCalendarDays(5) should be(ShiftCalendarDays(5))
  }

  "shiftWorkingDays" should "be the same as lamma version" in {
    shiftWorkingDays(10) should be(ShiftWorkingDays(10))
    shiftWorkingDays(10, noHoliday()) should be(ShiftWorkingDays(10, NoHoliday))
  }

  // ========= selectors =========
  "sameDay" should "be the same as lamma version" in {
    sameDay() should be(SameDay)
  }

  "forward" should "be the same as lamma version" in {
    forward() should be(Forward())
    forward(noHoliday()) should be(Forward(NoHoliday))
  }

  "backward" should "be the same as lamma version" in {
    backward() should be(Backward())
    backward(noHoliday()) should be(Backward(NoHoliday))
  }

  "modifiedFollowing" should "be the same as lamma version" in {
    modifiedFollowing() should be(ModifiedFollowing())
    modifiedFollowing(noHoliday()) should be(ModifiedFollowing(NoHoliday))
  }

  "modifiedPreceding" should "be the same as lamma version" in {
    modifiedPreceding() should be(ModifiedPreceding())
    modifiedPreceding(noHoliday()) should be(ModifiedPreceding(NoHoliday))
  }

  // ========== position of month ==============
  "firstDayOfMonth" should "be the same as lamma version" in {
    firstDayOfMonth() should be(FirstDayOfMonth)
  }

  "nthDayOfMonth" should "be the same as lamma version" in {
    nthDayOfMonth(12) should be(NthDayOfMonth(12))
  }

  "lastDayOfMonth" should "be the same as lamma version" in {
    lastDayOfMonth() should be(LastDayOfMonth)
  }

  "firstWeekdayOfMonth" should "be the same as lamma version" in {
    firstWeekdayOfMonth(FRIDAY) should be(FirstWeekdayOfMonth(Friday))
  }

  "nthWeekdayOfMonth" should "be the same as lamma version" in {
    nthWeekdayOfMonth(3, THURSDAY) should be(NthWeekdayOfMonth(3, Thursday))
  }

  "lastWeekdayOfMonth" should "be the same as lamma version" in {
    lastWeekdayOfMonth(MONDAY) should be(LastWeekdayOfMonth(Monday))
  }

  // ========== position of year ==============
  "firstDayOfYear" should "be the same as lamma version" in {
    firstDayOfYear() should be(FirstDayOfYear)
  }

  "secondDayOfYear" should "be the same as lamma version" in {
    secondDayOfYear() should be(SecondDayOfYear)
  }

  "nthDayOfYear" should "be the same as lamma version" in {
    nthDayOfYear(243) should be(NthDayOfYear(243))
  }

  "lastDayOfYear" should "be the same as lamma version" in {
    lastDayOfYear() should be(LastDayOfYear)
  }

  "firstWeekDayOfYear" should "be the same as lamma version" in {
    firstWeekDayOfYear(FRIDAY) should be(FirstWeekDayOfYear(Friday))
  }

  "nthWeekdayOfYear" should "be the same as lamma version" in {
    nthWeekdayOfYear(23, WEDNESDAY) should be(NthWeekdayOfYear(23, Wednesday))
  }

  "lastWeekdayOfYear" should "be the same as lamma version" in {
    lastWeekdayOfYear(MONDAY) should be(LastWeekdayOfYear(Monday))
  }

  "firstMonthOfYear" should "be the same as lamma version" in {
    firstMonthOfYear(lastDayOfMonth()) should be(FirstMonthOfYear(LastDayOfMonth))
  }

  "nthMonthOfYear" should "be the same as lamma version" in {
    nthMonthOfYear(FEBRUARY, lastDayOfMonth()) should be(NthMonthOfYear(February, LastDayOfMonth))
  }

  "lastMonthOfYear" should "be the same as lamma version" in {
    lastMonthOfYear(firstDayOfMonth()) should be(LastMonthOfYear(FirstDayOfMonth))
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
    months(lastWeekdayOfMonth(FRIDAY)) should be(Monthly(1, LastWeekdayOfMonth(Friday)))
    months(5, firstDayOfMonth()) should be(Monthly(5, FirstDayOfMonth))
  }

  "monthsBackward" should "be the same as lamma version" in {
    monthsBackward(3) should be(Monthly(-3))
    monthsBackward(lastWeekdayOfMonth(FRIDAY)) should be(Monthly(-1, LastWeekdayOfMonth(Friday)))
    monthsBackward(5, firstDayOfMonth()) should be(Monthly(-5, FirstDayOfMonth))
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
    years(firstDayOfYear()) should be(Yearly(1, FirstDayOfYear))
    years(2, lastDayOfYear()) should be(Yearly(2, LastDayOfYear))
  }

  "yearsBackward" should "be the same as lamma version" in {
    yearsBackward(5) should be(Yearly(-5))
    yearsBackward(firstDayOfYear()) should be(Yearly(-1, FirstDayOfYear))
    yearsBackward(2, lastDayOfYear()) should be(Yearly(-2, LastDayOfYear))
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
    dateDef("SomeDate", shiftCalendarDays(2)) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2)))
    dateDef("SomeDate", forward()) should be(DateDef("SomeDate", selector = Forward()))
    dateDef("SomeDate", periodStart(), shiftCalendarDays(2)) should be(DateDef("SomeDate", relativeTo = PeriodStart, shifter = ShiftCalendarDays(2)))
    dateDef("SomeDate", periodStart(), forward()) should be(DateDef("SomeDate", relativeTo = PeriodStart, selector = Forward()))
    dateDef("SomeDate", shiftCalendarDays(2), forward()) should be(DateDef("SomeDate", shifter = ShiftCalendarDays(2), selector = Forward()))
    dateDef("SomeDate", periodStart(), shiftCalendarDays(2), forward()) should be(DateDef("SomeDate", PeriodStart, ShiftCalendarDays(2), Forward()))
  }
}
