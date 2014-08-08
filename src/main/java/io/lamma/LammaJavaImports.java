package io.lamma;

import scala.collection.immutable.Set;

/**
 * This class contains interop alias to use Lamma with Java <br>
 *
 * It is suggested to always static import whole class when you are using Java<br>
 *  <br>
 *  import io.lamma.LammaJavaImports.*;
 */
public class LammaJavaImports {

    // ========= date & holidays ==========
    /**
     * helper method, alias of new Date(yyyy, mm, dd)
     *
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use constructor `new Date(yyyy, mm, dd)` instead
     */
    @Deprecated
    public static Date date(int yyyy, int mm, int dd) {
        return Dates.newDate(yyyy, mm, dd);
    }

    /**
     * helper method, takes an ISO representation to create a Date object. eg, date("2014-09-30")
     *
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Dates.newDate(isoRepr)` instead
     */
    @Deprecated
    public static Date date(String isoRepr) {
        return Dates.newDate(isoRepr);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.noHoliday` instead
     */
    @Deprecated
    public static HolidayRule noHoliday() {
        return HolidayRules.noHoliday();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.weekends` instead
     */
    @Deprecated
    public static HolidayRule weekends() {
        return HolidayRules.weekends();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.simpleRule` instead
     */
    @Deprecated
    public static HolidayRule simpleHolidayRule(Date... holidays) {
        return HolidayRules.simpleRule(holidays);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.simpleRule` instead
     */
    @Deprecated
    public static HolidayRule simpleHolidayRule(Set<Date> holidays) {
        return new SimpleHolidayRule(holidays);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.newCompositeHolidayRules` instead
     */
    @Deprecated
    public static HolidayRule compositeHolidayRules(HolidayRule... rules) {
        return HolidayRules.compositeRule(rules);
    }

    // ========= shifters =========
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.noShift` instead
     */
    @Deprecated
    public static Shifter noShift() {
        return Shifters.noShift();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.byCalendarDays` instead
     */
    @Deprecated
    public static Shifter shiftCalendarDays(int days) {
        return Shifters.byCalendarDays(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.byWorkingDays` instead
     */
    @Deprecated
    public static Shifter shiftWorkingDays(int days) {
        return Shifters.byWorkingDays(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.byWorkingDays` instead
     */
    @Deprecated
    public static Shifter shiftWorkingDays(int days, HolidayRule rule) {
        return Shifters.byWorkingDays(days, rule);
    }

    // ========= selectors =========
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.SAME_DAY_SELECTOR` instead
     */
    @Deprecated
    public static Selector sameDay() {
        return Selectors.SAME_DAY_SELECTOR;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.forward` instead
     */
    @Deprecated
    public static Selector forward() {
        return Selectors.forward();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.forward` instead
     */
    @Deprecated
    public static Selector forward(HolidayRule rule) {
        return Selectors.forward(rule);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.backward` instead
     */
    @Deprecated
    public static Selector backward() {
        return Selectors.backward();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.backward` instead
     */
    @Deprecated
    public static Selector backward(HolidayRule rule) {
        return Selectors.backward(rule);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.modifiedFollowing` instead
     */
    @Deprecated
    public static Selector modifiedFollowing() {
        return Selectors.modifiedFollowing();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.modifiedFollowing` instead
     */
    @Deprecated
    public static Selector modifiedFollowing(HolidayRule rule) {
        return Selectors.modifiedFollowing(rule);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.modifiedPreceding` instead
     */
    @Deprecated
    public static Selector modifiedPreceding() {
        return Selectors.modifiedPreceding();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.modifiedPreceding` instead
     */
    @Deprecated
    public static Selector modifiedPreceding(HolidayRule rule) {
        return Selectors.modifiedPreceding(rule);
    }

    // ========== day of month ==============
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.firstDay` instead
     */
    @Deprecated
    public static DayOfMonth firstDayOfMonth() {
        return DayOfMonths.firstDay();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.nthDay` instead
     */
    @Deprecated
    public static DayOfMonth nthDayOfMonth(int n) {
        return DayOfMonths.nthDay(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.lastDay` instead
     */
    @Deprecated
    public static DayOfMonth lastDayOfMonth() {
        return DayOfMonths.lastDay();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.firstWeekday` instead
     */
    @Deprecated
    public static DayOfMonth firstWeekdayOfMonth(DayOfWeek dow) {
        return DayOfMonths.firstWeekday(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.nthWeekday` instead
     */
    @Deprecated
    public static DayOfMonth nthWeekdayOfMonth(int n, DayOfWeek dow) {
        return DayOfMonths.nthWeekday(n, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.lastWeekday` instead
     */
    @Deprecated
    public static DayOfMonth lastWeekdayOfMonth(DayOfWeek dow) {
        return DayOfMonths.lastWeekday(dow);
    }

    // ========== day of year ==============
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.firstDay` instead
     */
    @Deprecated
    public static DayOfYear firstDayOfYear() {
        return DayOfYears.firstDay();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.secondDay` instead
     */
    @Deprecated
    public static DayOfYear secondDayOfYear() {
        return DayOfYears.secondDay();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.nthDay` instead
     */
    @Deprecated
    public static DayOfYear nthDayOfYear(int n) {
        return DayOfYears.nthDay(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.lastDay` instead
     */
    @Deprecated
    public static DayOfYear lastDayOfYear() {
        return DayOfYears.lastDay();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.firstWeekDay` instead
     */
    @Deprecated
    public static DayOfYear firstWeekDayOfYear(DayOfWeek dow) {
        return DayOfYears.firstWeekDay(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.nthWeekday` instead
     */
    @Deprecated
    public static DayOfYear nthWeekdayOfYear(int n, DayOfWeek dow) {
        return DayOfYears.nthWeekday(n, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.lastWeekday` instead
     */
    @Deprecated
    public static DayOfYear lastWeekdayOfYear(DayOfWeek dow) {
        return DayOfYears.lastWeekday(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.firstMonth` instead
     */
    @Deprecated
    public static DayOfYear firstMonthOfYear(DayOfMonth dom) {
        return DayOfYears.firstMonth(dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.nthMonth` instead
     */
    @Deprecated
    public static DayOfYear nthMonthOfYear(Month m, DayOfMonth dom) {
        return DayOfYears.nthMonth(m, dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.lastMonth` instead
     */
    @Deprecated
    public static DayOfYear lastMonthOfYear(DayOfMonth dom) {
        return DayOfYears.lastMonth(dom);
    }

    // ========== recurrence pattern ==============
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyDay` instead
     */
    @Deprecated
    public static Daily everyDay() {
        return Patterns.everyDay();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyOtherDay` instead
     */
    @Deprecated
    public static Daily everyOtherDay() {
        return Patterns.everyOtherDay();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.daily` instead
     */
    @Deprecated
    public static Daily days(int days) {
        return Patterns.daily(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.dailyBackward` instead
     */
    @Deprecated
    public static Daily daysBackward(int days) {
        return Patterns.dailyBackward(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyWeek` instead
     */
    @Deprecated
    public static Weekly everyWeek() {
        return Patterns.everyWeek();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyOtherWeek` instead
     */
    @Deprecated
    public static Weekly everyOtherWeek() {
        return Patterns.everyOtherWeek();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.weekly` instead
     */
    @Deprecated
    public static Weekly weeks(int weeks) {
        return Patterns.weekly(weeks);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.weekly` instead
     */
    @Deprecated
    public static Weekly weeks(DayOfWeek dow) {
        return Patterns.weekly(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.weekly` instead
     */
    @Deprecated
    public static Weekly weeks(int weeks, DayOfWeek dow) {
        return Patterns.weekly(weeks, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.weeklyBackward` instead
     */
    @Deprecated
    public static Weekly weeksBackward(int weeks) {
        return Patterns.weeklyBackward(weeks);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.weeklyBackward` instead
     */
    @Deprecated
    public static Weekly weeksBackward(DayOfWeek dow) {
        return Patterns.weeklyBackward(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.weeklyBackward` instead
     */
    @Deprecated
    public static Weekly weeksBackward(int weeks, DayOfWeek dow) {
        return Patterns.weeklyBackward(weeks, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyMonth` instead
     */
    @Deprecated
    public static Monthly everyMonth() {
        return Patterns.everyMonth();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyOtherMonth` instead
     */
    @Deprecated
    public static Monthly everyOtherMonth() {
        return Patterns.everyOtherMonth();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.monthly` instead
     */
    @Deprecated
    public static Monthly months(int n) {
        return Patterns.monthly(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.monthly` instead
     */
    @Deprecated
    public static Monthly months(DayOfMonth dom) {
        return Patterns.monthly(dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.monthly` instead
     */
    @Deprecated
    public static Monthly months(int n, DayOfMonth dom) {
        return Patterns.monthly(n, dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.monthlyBackward` instead
     */
    @Deprecated
    public static Monthly monthsBackward(int n) {
        return Patterns.monthlyBackward(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.monthlyBackward` instead
     */
    @Deprecated
    public static Monthly monthsBackward(DayOfMonth dom) {
        return Patterns.monthlyBackward(dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.monthlyBackward` instead
     */
    @Deprecated
    public static Monthly monthsBackward(int n, DayOfMonth dom) {
        return Patterns.monthlyBackward(n, dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyYear` instead
     */
    @Deprecated
    public static Yearly everyYear() {
        return Patterns.everyYear();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.everyOtherYear` instead
     */
    @Deprecated
    public static Yearly everyOtherYear() {
        return Patterns.everyOtherYear();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.yearly` instead
     */
    @Deprecated
    public static Yearly years(int n) {
        return Patterns.yearly(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.yearly` instead
     */
    @Deprecated
    public static Yearly years(DayOfYear doy) {
        return Patterns.yearly(doy);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.yearly` instead
     */
    @Deprecated
    public static Yearly years(int n, DayOfYear doy) {
        return Patterns.yearly(n, doy);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.yearlyBackward` instead
     */
    @Deprecated
    public static Yearly yearsBackward(int n) {
        return Patterns.yearlyBackward(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.yearlyBackward` instead
     */
    @Deprecated
    public static Yearly yearsBackward(DayOfYear doy) {
        return Patterns.yearlyBackward(doy);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.yearlyBackward` instead
     */
    @Deprecated
    public static Yearly yearsBackward(int n, DayOfYear doy) {
        return Patterns.yearlyBackward(n, doy);
    }

    // ============== period builder / stub rule ===============
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.noStartNoEnd` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder stubRulePeriodBuilder() {
        return StubRulePeriodBuilders.noStartNoEnd();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.of` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder stubRulePeriodBuilder(StubRulePeriodBuilder.StartRule start) {
        return StubRulePeriodBuilders.of(start);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.of` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder stubRulePeriodBuilder(StubRulePeriodBuilder.EndRule end) {
        return StubRulePeriodBuilders.of(end);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.of` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder stubRulePeriodBuilder(StubRulePeriodBuilder.StartRule start, StubRulePeriodBuilder.EndRule end) {
        return StubRulePeriodBuilders.of(start, end);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.noStart` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.StartRule noStartRule() {
        return StubRulePeriodBuilders.Rules.noStart();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.longStart` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.StartRule longStart() {
        return StubRulePeriodBuilders.Rules.longStart();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.longStart` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.StartRule longStart(int n) {
        return StubRulePeriodBuilders.Rules.longStart(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.shortStart` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.StartRule shortStart() {
        return StubRulePeriodBuilders.Rules.shortStart();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.shortStart` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.StartRule shortStart(int n) {
        return StubRulePeriodBuilders.Rules.shortStart(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.noEnd` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.EndRule noEndRule() {
        return StubRulePeriodBuilders.Rules.noEnd();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.longEnd` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.EndRule longEnd() {
        return StubRulePeriodBuilders.Rules.longEnd();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.longEnd` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.EndRule longEnd(int n) {
        return StubRulePeriodBuilders.Rules.longEnd(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.shortEnd` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.EndRule shortEnd() {
        return StubRulePeriodBuilders.Rules.shortEnd();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `StubRulePeriodBuilders.Rules.shortEnd` instead
     */
    @Deprecated
    public static StubRulePeriodBuilder.EndRule shortEnd(int n) {
        return StubRulePeriodBuilders.Rules.shortEnd(n);
    }

    // ================== DateDef and Anchor================
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Anchors.periodEnd` instead
     */
    @Deprecated
    public static Anchor periodEnd() {
        return Anchors.periodEnd();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Anchors.periodStart` instead
     */
    @Deprecated
    public static Anchor periodStart() {
        return Anchors.periodStart();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Anchors.otherDate` instead
     */
    @Deprecated
    public static Anchor otherDate(String name) {
        return Anchors.otherDate(name);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name) {
        return DateDefs.of(name);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name, Anchor relativeTo) {
        return DateDefs.of(name, relativeTo);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name, Shifter shifter) {
        return DateDefs.of(name, shifter);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name, Selector selector) {
        return DateDefs.of(name, selector);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name, Anchor relativeTo, Shifter shifter) {
        return DateDefs.of(name, relativeTo, shifter);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name, Anchor relativeTo, Selector selector) {
        return DateDefs.of(name, relativeTo, selector);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name, Shifter shifter, Selector selector) {
        return DateDefs.of(name, shifter, selector);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DateDefs.of` instead
     */
    @Deprecated
    public static DateDef dateDef(String name, Anchor relativeTo, Shifter shifter, Selector selector) {
        return DateDefs.of(name, relativeTo, shifter, selector);
    }
}
