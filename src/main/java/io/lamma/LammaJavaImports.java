package io.lamma;

import static scala.collection.JavaConverters.collectionAsScalaIterableConverter;

import scala.collection.Iterable;
import scala.collection.JavaConverters;
import scala.collection.immutable.List;
import scala.collection.immutable.Set;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class contains interop alias to use Lamma with Java <br>
 *
 * It is suggested to always static import whole class when you are using Java<br>
 *  <br>
 *  import io.lamma.LammaJavaImports.*;
 */
public class LammaJavaImports {

    // =========== collections ================
    @SafeVarargs
    public static <E> Iterable<E> iterable(E ... elems) {
        return collectionAsScalaIterableConverter(new ArrayList<E>(Arrays.asList(elems))).asScala();
    }

    @SafeVarargs
    public static <E> List<E> list(E ... elems) {
        return iterable(elems).toList();
    }

    @SafeVarargs
    public static <E> Set<E> set(E ... elems) {
        return iterable(elems).toSet();
    }

    public static <E> List<E> scalaList(java.util.List<E> javaList) {
        return collectionAsScalaIterableConverter(javaList).asScala().toList();
    }

    public static <E> java.util.List<E> javaList(List<E> scalaList) {
        return JavaConverters.seqAsJavaListConverter(scalaList).asJava();
    }

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
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.NO_HOLIDAY` instead
     */
    @Deprecated
    public static HolidayRule noHoliday() {
        return HolidayRules.NO_HOLIDAY;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.WEEKENDS` instead
     */
    @Deprecated
    public static HolidayRule weekends() {
        return HolidayRules.WEEKENDS;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.newSimpleHolidayRule` instead
     */
    @Deprecated
    public static HolidayRule simpleHolidayRule(Date... holidays) {
        return HolidayRules.newSimpleHolidayRule(holidays);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `HolidayRules.newSimpleHolidayRule` instead
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
        return CompositeHolidayRule$.MODULE$.apply(iterable(rules).<HolidayRule>toSeq());
    }

    // ========= shifters =========
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.NO_SHIFT` instead
     */
    @Deprecated
    public static Shifter noShift() {
        return Shifters.NO_SHIFT;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.newCalendarDaysShifter` instead
     */
    @Deprecated
    public static Shifter shiftCalendarDays(int days) {
        return Shifters.newCalendarDaysShifter(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.newWorkingDaysShifter` instead
     */
    @Deprecated
    public static Shifter shiftWorkingDays(int days) {
        return Shifters.newWorkingDaysShifter(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Shifters.newWorkingDaysShifter` instead
     */
    @Deprecated
    public static Shifter shiftWorkingDays(int days, HolidayRule rule) {
        return Shifters.newWorkingDaysShifter(days, rule);
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
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newForwardSelector` instead
     */
    @Deprecated
    public static Selector forward() {
        return Selectors.newForwardSelector();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newForwardSelector` instead
     */
    @Deprecated
    public static Selector forward(HolidayRule rule) {
        return Selectors.newForwardSelector(rule);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newBackwardSelector` instead
     */
    @Deprecated
    public static Selector backward() {
        return Selectors.newBackwardSelector();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newBackwardSelector` instead
     */
    @Deprecated
    public static Selector backward(HolidayRule rule) {
        return Selectors.newBackwardSelector(rule);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newModifiedFollowingSelector` instead
     */
    @Deprecated
    public static Selector modifiedFollowing() {
        return Selectors.newModifiedFollowingSelector();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newModifiedFollowingSelector` instead
     */
    @Deprecated
    public static Selector modifiedFollowing(HolidayRule rule) {
        return Selectors.newModifiedFollowingSelector(rule);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newModifiedPrecedingSelector` instead
     */
    @Deprecated
    public static Selector modifiedPreceding() {
        return Selectors.newModifiedPrecedingSelector();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Selectors.newModifiedPrecedingSelector` instead
     */
    @Deprecated
    public static Selector modifiedPreceding(HolidayRule rule) {
        return Selectors.newModifiedPrecedingSelector(rule);
    }

    // ========== day of month ==============
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.firstDayOfMonth` instead
     */
    @Deprecated
    public static DayOfMonth firstDayOfMonth() {
        return DayOfMonths.firstDayOfMonth();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.nthDayOfMonth` instead
     */
    @Deprecated
    public static DayOfMonth nthDayOfMonth(int n) {
        return DayOfMonths.nthDayOfMonth(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.lastDayOfMonth` instead
     */
    @Deprecated
    public static DayOfMonth lastDayOfMonth() {
        return DayOfMonths.lastDayOfMonth();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.firstWeekdayOfMonth` instead
     */
    @Deprecated
    public static DayOfMonth firstWeekdayOfMonth(DayOfWeek dow) {
        return DayOfMonths.firstWeekdayOfMonth(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.nthWeekdayOfMonth` instead
     */
    @Deprecated
    public static DayOfMonth nthWeekdayOfMonth(int n, DayOfWeek dow) {
        return DayOfMonths.nthWeekdayOfMonth(n, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfMonths.lastWeekdayOfMonth` instead
     */
    @Deprecated
    public static DayOfMonth lastWeekdayOfMonth(DayOfWeek dow) {
        return DayOfMonths.lastWeekdayOfMonth(dow);
    }

    // ========== day of year ==============
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.firstDayOfYear` instead
     */
    @Deprecated
    public static DayOfYear firstDayOfYear() {
        return DayOfYears.firstDayOfYear();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.secondDayOfYear` instead
     */
    @Deprecated
    public static DayOfYear secondDayOfYear() {
        return DayOfYears.secondDayOfYear();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.nthDayOfYear` instead
     */
    @Deprecated
    public static DayOfYear nthDayOfYear(int n) {
        return DayOfYears.nthDayOfYear(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.lastDayOfYear` instead
     */
    @Deprecated
    public static DayOfYear lastDayOfYear() {
        return DayOfYears.lastDayOfYear();
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.firstWeekDayOfYear` instead
     */
    @Deprecated
    public static DayOfYear firstWeekDayOfYear(DayOfWeek dow) {
        return DayOfYears.firstWeekDayOfYear(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.nthWeekdayOfYear` instead
     */
    @Deprecated
    public static DayOfYear nthWeekdayOfYear(int n, DayOfWeek dow) {
        return DayOfYears.nthWeekdayOfYear(n, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.lastWeekdayOfYear` instead
     */
    @Deprecated
    public static DayOfYear lastWeekdayOfYear(DayOfWeek dow) {
        return DayOfYears.lastWeekdayOfYear(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.firstMonthOfYear` instead
     */
    @Deprecated
    public static DayOfYear firstMonthOfYear(DayOfMonth dom) {
        return DayOfYears.firstMonthOfYear(dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.nthMonthOfYear` instead
     */
    @Deprecated
    public static DayOfYear nthMonthOfYear(Month m, DayOfMonth dom) {
        return DayOfYears.nthMonthOfYear(m, dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `DayOfYears.lastMonthOfYear` instead
     */
    @Deprecated
    public static DayOfYear lastMonthOfYear(DayOfMonth dom) {
        return DayOfYears.lastMonthOfYear(dom);
    }

    // ========== recurrence pattern ==============
    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_DAY` instead
     */
    @Deprecated
    public static Daily everyDay() {
        return Patterns.EVERY_DAY;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_OTHER_DAY` instead
     */
    @Deprecated
    public static Daily everyOtherDay() {
        return Patterns.EVERY_OTHER_DAY;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newDailyPattern` instead
     */
    @Deprecated
    public static Daily days(int days) {
        return Patterns.newDailyPattern(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newDailyBackwardPattern` instead
     */
    @Deprecated
    public static Daily daysBackward(int days) {
        return Patterns.newDailyBackwardPattern(days);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_WEEK` instead
     */
    @Deprecated
    public static Weekly everyWeek() {
        return Patterns.EVERY_WEEK;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_OTHER_WEEK` instead
     */
    @Deprecated
    public static Weekly everyOtherWeek() {
        return Patterns.EVERY_OTHER_WEEK;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newWeeklyPattern` instead
     */
    @Deprecated
    public static Weekly weeks(int weeks) {
        return Patterns.newWeeklyPattern(weeks);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newWeeklyPattern` instead
     */
    @Deprecated
    public static Weekly weeks(DayOfWeek dow) {
        return Patterns.newWeeklyPattern(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newWeeklyPattern` instead
     */
    @Deprecated
    public static Weekly weeks(int weeks, DayOfWeek dow) {
        return Patterns.newWeeklyPattern(weeks, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newWeeklyBackwardPattern` instead
     */
    @Deprecated
    public static Weekly weeksBackward(int weeks) {
        return Patterns.newWeeklyBackwardPattern(weeks);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newWeeklyBackwardPattern` instead
     */
    @Deprecated
    public static Weekly weeksBackward(DayOfWeek dow) {
        return Patterns.newWeeklyBackwardPattern(dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newWeeklyBackwardPattern` instead
     */
    @Deprecated
    public static Weekly weeksBackward(int weeks, DayOfWeek dow) {
        return Patterns.newWeeklyBackwardPattern(weeks, dow);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_MONTH` instead
     */
    @Deprecated
    public static Monthly everyMonth() {
        return Patterns.EVERY_MONTH;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_OTHER_MONTH` instead
     */
    @Deprecated
    public static Monthly everyOtherMonth() {
        return Patterns.EVERY_OTHER_MONTH;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newMonthlyPattern` instead
     */
    @Deprecated
    public static Monthly months(int n) {
        return Patterns.newMonthlyPattern(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newMonthlyPattern` instead
     */
    @Deprecated
    public static Monthly months(DayOfMonth dom) {
        return Patterns.newMonthlyPattern(dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newMonthlyPattern` instead
     */
    @Deprecated
    public static Monthly months(int n, DayOfMonth dom) {
        return Patterns.newMonthlyPattern(n, dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newMonthlyBackwardPattern` instead
     */
    @Deprecated
    public static Monthly monthsBackward(int n) {
        return Patterns.newMonthlyBackwardPattern(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newMonthlyBackwardPattern` instead
     */
    @Deprecated
    public static Monthly monthsBackward(DayOfMonth dom) {
        return Patterns.newMonthlyBackwardPattern(dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newMonthlyBackwardPattern` instead
     */
    @Deprecated
    public static Monthly monthsBackward(int n, DayOfMonth dom) {
        return Patterns.newMonthlyBackwardPattern(n, dom);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_YEAR` instead
     */
    @Deprecated
    public static Yearly everyYear() {
        return Patterns.EVERY_YEAR;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.EVERY_OTHER_YEAR` instead
     */
    @Deprecated
    public static Yearly everyOtherYear() {
        return Patterns.EVERY_OTHER_YEAR;
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newYearlyPattern` instead
     */
    @Deprecated
    public static Yearly years(int n) {
        return Patterns.newYearlyPattern(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newYearlyPattern` instead
     */
    @Deprecated
    public static Yearly years(DayOfYear doy) {
        return Patterns.newYearlyPattern(doy);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newYearlyPattern` instead
     */
    @Deprecated
    public static Yearly years(int n, DayOfYear doy) {
        return Patterns.newYearlyPattern(n, doy);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newYearlyBackwardPattern` instead
     */
    @Deprecated
    public static Yearly yearsBackward(int n) {
        return Patterns.newYearlyBackwardPattern(n);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newYearlyBackwardPattern` instead
     */
    @Deprecated
    public static Yearly yearsBackward(DayOfYear doy) {
        return Patterns.newYearlyBackwardPattern(doy);
    }

    /**
     * @deprecated (since 2.2.0 will be removed in 2.3.0) use `Patterns.newYearlyBackwardPattern` instead
     */
    @Deprecated
    public static Yearly yearsBackward(int n, DayOfYear doy) {
        return Patterns.newYearlyBackwardPattern(n, doy);
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
    public static Anchor periodEnd() {
        return Anchor.PeriodEnd$.MODULE$;
    }

    public static Anchor periodStart() {
        return Anchor.PeriodStart$.MODULE$;
    }

    public static Anchor otherDate(String name) {
        return new Anchor.OtherDate(name);
    }

    public static DateDef dateDef(String name) {
        return new DateDef(name, DateDef$.MODULE$.apply$default$2(), DateDef$.MODULE$.apply$default$3(), DateDef$.MODULE$.apply$default$4());
    }

    public static DateDef dateDef(String name, Anchor relativeTo) {
        return new DateDef(name, relativeTo, DateDef$.MODULE$.apply$default$3(), DateDef$.MODULE$.apply$default$4());
    }

    public static DateDef dateDef(String name, Shifter shifter) {
        return new DateDef(name, DateDef$.MODULE$.apply$default$2(), shifter, DateDef$.MODULE$.apply$default$4());
    }

    public static DateDef dateDef(String name, Selector selector) {
        return new DateDef(name, DateDef$.MODULE$.apply$default$2(), DateDef$.MODULE$.apply$default$3(), selector);
    }

    public static DateDef dateDef(String name, Anchor relativeTo, Shifter shifter) {
        return new DateDef(name, relativeTo, shifter, DateDef$.MODULE$.apply$default$4());
    }

    public static DateDef dateDef(String name, Anchor relativeTo, Selector selector) {
        return new DateDef(name, relativeTo, DateDef$.MODULE$.apply$default$3(), selector);
    }

    public static DateDef dateDef(String name, Shifter shifter, Selector selector) {
        return new DateDef(name, DateDef$.MODULE$.apply$default$2(), shifter, selector);
    }

    public static DateDef dateDef(String name, Anchor relativeTo, Shifter shifter, Selector selector) {
        return new DateDef(name, relativeTo, shifter, selector);
    }
}
