package io.lamma;

import static scala.collection.JavaConverters.collectionAsScalaIterableConverter;

import scala.collection.Iterable;
import scala.collection.JavaConverters;
import scala.collection.immutable.List;
import scala.collection.immutable.Set;

import java.util.ArrayList;
import java.util.Arrays;

public class LammaConversion {

    public static <E> java.util.List<E> javaList(List<E> scalaList) {
        return JavaConverters.seqAsJavaListConverter(scalaList).asJava();
    }

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

    // ========= date & calendars ==========
    public static Date date(int yyyy, int mm, int dd) {
        return new Date(yyyy, mm, dd);
    }

    public static HolidayRule noHoliday() {
        return NoHoliday$.MODULE$;
    }

    public static HolidayRule weekendCalendar() {
        return Weekends$.MODULE$;
    }

    public static HolidayRule simpleCalendar(Date ... holidays) {
        return new SimpleCalendar(iterable(holidays).<Date>toSet());
    }

    public static HolidayRule simpleCalendar(Set<Date> holidays) {
        return new SimpleCalendar(holidays);
    }

    public static HolidayRule compositeCalendar(HolidayRule... calendars) {
        return CompositeHolidayRules$.MODULE$.apply(iterable(calendars).<HolidayRule>toSeq());
    }

    // ========= shifters =========
    public static Shifter noShift() {
        return Shifter.NoShift$.MODULE$;
    }

    public static Shifter shiftCalendarDays(int days) {
        return new Shifter.ShiftCalendarDays(days);
    }

    public static Shifter shiftWorkingDays(int days) {
        return new Shifter.ShiftWorkingDays(days, Shifter.ShiftWorkingDays$.MODULE$.apply$default$2());
    }

    public static Shifter shiftWorkingDays(int days, HolidayRule rule) {
        return new Shifter.ShiftWorkingDays(days, rule);
    }

    // ========= selectors =========
    public static Selector sameDay() {
        return Selector.SameDay$.MODULE$;
    }

    public static Selector forward() {
        return new Selector.Forward(Selector.Forward$.MODULE$.apply$default$1());
    }

    public static Selector forward(HolidayRule rule) {
        return new Selector.Forward(rule);
    }

    public static Selector backward() {
        return new Selector.Backward(Selector.Backward$.MODULE$.apply$default$1());
    }

    public static Selector backward(HolidayRule rule) {
        return new Selector.Backward(rule);
    }

    public static Selector modifiedFollowing() {
        return new Selector.ModifiedFollowing(Selector.ModifiedFollowing$.MODULE$.apply$default$1());
    }

    public static Selector modifiedFollowing(HolidayRule rule) {
        return new Selector.ModifiedFollowing(rule);
    }

    public static Selector modifiedPreceding() {
        return new Selector.ModifiedPreceding(Selector.ModifiedPreceding$.MODULE$.apply$default$1());
    }

    public static Selector modifiedPreceding(HolidayRule rule) {
        return new Selector.ModifiedPreceding(rule);
    }

    // ========== position of month ==============
    public static DayOfMonth firstDayOfMonth() {
        return DayOfMonth$.MODULE$.FirstDayOfMonth();
    }

    public static DayOfMonth nthDayOfMonth(int n) {
        return new DayOfMonth.NthDayOfMonth(n);
    }

    public static DayOfMonth lastDayOfMonth() {
        return DayOfMonth.LastDayOfMonth$.MODULE$;
    }

    public static DayOfMonth firstWeekdayOfMonth(DayOfWeek dow) {
        return DayOfMonth$.MODULE$.FirstWeekdayOfMonth(dow);
    }

    public static DayOfMonth nthWeekdayOfMonth(int n, DayOfWeek dow) {
        return new DayOfMonth.NthWeekdayOfMonth(n, dow);
    }

    public static DayOfMonth lastWeekdayOfMonth(DayOfWeek dow) {
        return new DayOfMonth.LastWeekdayOfMonth(dow);
    }

    // ========== position of year ==============
    public static DayOfYear firstDayOfYear() {
        return DayOfYear$.MODULE$.FirstDayOfYear();
    }

    public static DayOfYear secondDayOfYear() {
        return DayOfYear$.MODULE$.SecondDayOfYear();
    }

    public static DayOfYear nthDayOfYear(int n) {
        return new DayOfYear.NthDayOfYear(n);
    }

    public static DayOfYear lastDayOfYear() {
        return DayOfYear.LastDayOfYear$.MODULE$;
    }

    public static DayOfYear firstWeekDayOfYear(DayOfWeek dow) {
        return DayOfYear$.MODULE$.FirstWeekDayOfYear(dow);
    }

    public static DayOfYear nthWeekdayOfYear(int n, DayOfWeek dow) {
        return new DayOfYear.NthWeekdayOfYear(n, dow);
    }

    public static DayOfYear lastWeekdayOfYear(DayOfWeek dow) {
        return DayOfYear$.MODULE$.LastWeekdayOfYear(dow);
    }

    public static DayOfYear firstMonthOfYear(DayOfMonth pom) {
        return DayOfYear$.MODULE$.FirstMonthOfYear(pom);
    }

    public static DayOfYear nthMonthOfYear(Month m, DayOfMonth pom) {
        return new DayOfYear.NthMonthOfYear(m, pom);
    }

    public static DayOfYear lastMonthOfYear(DayOfMonth pom) {
        return DayOfYear$.MODULE$.LastMonthOfYear(pom);
    }

    // ========== recurrence pattern ==============
    public static Recurrence.Days everyDay() {
        return Recurrence$.MODULE$.EveryDay();
    }

    public static Recurrence.Days everyOtherDay() {
        return Recurrence$.MODULE$.EveryOtherDay();
    }

    public static Recurrence.Days days(int days) {
        return Recurrence.Days$.MODULE$.apply(days);
    }

    public static Recurrence.Days everyWorkingDay()  {
        return Recurrence$.MODULE$.EveryWorkingDay(Recurrence$.MODULE$.EveryWorkingDay$default$1());
    }

    public static Recurrence.Days everyWorkingDay(HolidayRule rule) {
        return Recurrence$.MODULE$.EveryWorkingDay(rule);
    }

    public static Recurrence.Days workingDays(int days) {
        return Recurrence.Days$.MODULE$.workingDays(days, Recurrence.Days$.MODULE$.workingDays$default$2());
    }

    public static Recurrence.Days workingDays(int days, HolidayRule rule) {
        return Recurrence.Days$.MODULE$.workingDays(days, rule);
    }

    public static Recurrence.DaysBackward daysBackward(int days) {
        return Recurrence.DaysBackward$.MODULE$.apply(days);
    }

    public static Recurrence.DaysBackward workingDaysBackward(int days) {
        return Recurrence.DaysBackward$.MODULE$.workingDays(days, Recurrence.Days$.MODULE$.workingDays$default$2());
    }

    public static Recurrence.DaysBackward workingDaysBackward(int days, HolidayRule rule) {
        return Recurrence.DaysBackward$.MODULE$.workingDays(days, rule);
    }

    public static Recurrence.Weeks everyWeek() {
        return Recurrence$.MODULE$.EveryWeek();
    }

    public static Recurrence.Weeks everyOtherWeek() {
        return Recurrence$.MODULE$.EveryOtherWeek();
    }

    public static Recurrence.Weeks weeks(int weeks) {
        return new Recurrence.Weeks(weeks, Recurrence.Weeks$.MODULE$.apply$default$2());
    }

    public static Recurrence.Weeks weeks(DayOfWeek dow) {
        return Recurrence.Weeks$.MODULE$.apply(dow);
    }

    public static Recurrence.Weeks weeks(int weeks, DayOfWeek dow) {
        return Recurrence.Weeks$.MODULE$.apply(weeks, dow);
    }

    public static Recurrence.WeeksBackward weeksBackward(int weeks) {
        return new Recurrence.WeeksBackward(weeks, Recurrence.WeeksBackward$.MODULE$.apply$default$2());
    }

    public static Recurrence.WeeksBackward weeksBackward(DayOfWeek dow) {
        return Recurrence.WeeksBackward$.MODULE$.apply(dow);
    }

    public static Recurrence.WeeksBackward weeksBackward(int weeks, DayOfWeek dow) {
        return Recurrence.WeeksBackward$.MODULE$.apply(weeks, dow);
    }

    public static Recurrence.Months everyMonth() {
        return Recurrence$.MODULE$.EveryMonth();
    }

    public static Recurrence.Months everyOtherMonth() {
        return Recurrence$.MODULE$.EveryOtherMonth();
    }

    public static Recurrence.Months months(int n) {
        return Recurrence.Months$.MODULE$.apply(n, Recurrence.Months$.MODULE$.apply$default$2());
    }

    public static Recurrence.Months months(DayOfMonth pom) {
        return Recurrence.Months$.MODULE$.apply(pom);
    }

    public static Recurrence.Months months(int n, DayOfMonth pom) {
        return Recurrence.Months$.MODULE$.apply(n, pom);
    }

    public static Recurrence.MonthsBackward monthsBackward(int n) {
        return Recurrence.MonthsBackward$.MODULE$.apply(n, Recurrence.MonthsBackward$.MODULE$.apply$default$2());
    }

    public static Recurrence.MonthsBackward monthsBackward(DayOfMonth pom) {
        return Recurrence.MonthsBackward$.MODULE$.apply(pom);
    }

    public static Recurrence.MonthsBackward monthsBackward(int n, DayOfMonth pom) {
        return Recurrence.MonthsBackward$.MODULE$.apply(n, pom);
    }

    public static Recurrence.Years everyYear() {
        return Recurrence$.MODULE$.EveryYear();
    }

    public static Recurrence.Years everyOtherYear() {
        return Recurrence$.MODULE$.EveryOtherYear();
    }

    public static Recurrence.Years years(int n) {
        return Recurrence.Years$.MODULE$.apply(n, Recurrence.Years$.MODULE$.apply$default$2());
    }

    public static Recurrence.Years years(DayOfYear poy) {
        return Recurrence.Years$.MODULE$.apply(poy);
    }

    public static Recurrence.Years years(int n, DayOfYear poy) {
        return Recurrence.Years$.MODULE$.apply(n, poy);
    }

    public static Recurrence.YearsBackward yearsBackward(int n) {
        return Recurrence.YearsBackward$.MODULE$.apply(n, Recurrence.YearsBackward$.MODULE$.apply$default$2());
    }

    public static Recurrence.YearsBackward yearsBackward(DayOfYear poy) {
        return Recurrence.YearsBackward$.MODULE$.apply(poy);
    }

    public static Recurrence.YearsBackward yearsBackward(int n, DayOfYear poy) {
        return Recurrence.YearsBackward$.MODULE$.apply(n, poy);
    }

    // ============== period builder / stub rule ===============
    public static StubRulePeriodBuilder stubRulePeriodBuilder() {
        return StubRulePeriodBuilder$.MODULE$.apply(StubRulePeriodBuilder$.MODULE$.apply$default$1(), StubRulePeriodBuilder$.MODULE$.apply$default$2());
    }

    public static StubRulePeriodBuilder stubRulePeriodBuilder(StubRulePeriodBuilder.StartRule start) {
        return StubRulePeriodBuilder$.MODULE$.apply(start, StubRulePeriodBuilder$.MODULE$.apply$default$2());
    }

    public static StubRulePeriodBuilder stubRulePeriodBuilder(StubRulePeriodBuilder.EndRule end) {
        return StubRulePeriodBuilder$.MODULE$.apply(StubRulePeriodBuilder$.MODULE$.apply$default$1(), end);
    }

    public static StubRulePeriodBuilder stubRulePeriodBuilder(StubRulePeriodBuilder.StartRule start, StubRulePeriodBuilder.EndRule end) {
        return StubRulePeriodBuilder$.MODULE$.apply(start, end);
    }

    public static StubRulePeriodBuilder.StartRule noStartRule() {
        return StubRulePeriodBuilder$.MODULE$.NoStartRule();
    }

    public static StubRulePeriodBuilder.StartRule longStart() {
        return new StubRulePeriodBuilder.LongStart(StubRulePeriodBuilder.LongStart$.MODULE$.apply$default$1());
    }

    public static StubRulePeriodBuilder.StartRule longStart(int n) {
        return StubRulePeriodBuilder.LongStart$.MODULE$.apply(n);
    }

    public static StubRulePeriodBuilder.StartRule shortStart() {
        return new StubRulePeriodBuilder.ShortStart(StubRulePeriodBuilder.ShortStart$.MODULE$.apply$default$1());
    }

    public static StubRulePeriodBuilder.StartRule shortStart(int n) {
        return StubRulePeriodBuilder.ShortStart$.MODULE$.apply(n);
    }

    public static StubRulePeriodBuilder.EndRule noEndRule() {
        return StubRulePeriodBuilder$.MODULE$.NoEndRule();
    }

    public static StubRulePeriodBuilder.EndRule longEnd() {
        return new StubRulePeriodBuilder.LongEnd(StubRulePeriodBuilder.LongEnd$.MODULE$.apply$default$1());
    }

    public static StubRulePeriodBuilder.EndRule longEnd(int n) {
        return StubRulePeriodBuilder.LongEnd$.MODULE$.apply(n);
    }

    public static StubRulePeriodBuilder.EndRule shortEnd() {
        return new StubRulePeriodBuilder.ShortEnd(StubRulePeriodBuilder.ShortEnd$.MODULE$.apply$default$1());
    }

    public static StubRulePeriodBuilder.EndRule shortEnd(int n) {
        return StubRulePeriodBuilder.ShortEnd$.MODULE$.apply(n);
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
