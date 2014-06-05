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
    public static <E> Iterable<E> iterable(E ... elems) {
        return collectionAsScalaIterableConverter(new ArrayList<E>(Arrays.asList(elems))).asScala();
    }

    public static <E> List<E> list(E ... elems) {
        return iterable(elems).toList();
    }

    public static <E> Set<E> set(E ... elems) {
        return iterable(elems).toSet();
    }

    // ========= date & calendars ==========
    public static Date date(int yyyy, int mm, int dd) {
        return new Date(yyyy, mm, dd);
    }

    public static Calendar noHoliday() {
        return NoHoliday$.MODULE$;
    }

    public static Calendar weekendCalendar() {
        return WeekendCalendar$.MODULE$;
    }

    public static Calendar simpleCalendar(Date ... holidays) {
        return new SimpleCalendar(iterable(holidays).<Date>toSet());
    }

    public static Calendar simpleCalendar(Set<Date> holidays) {
        return new SimpleCalendar(holidays);
    }

    public static Calendar compositeCalendar(Calendar ... calendars) {
        return new CompositeCalendar(iterable(calendars).<Calendar>toSeq());
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

    public static Shifter shiftWorkingDays(int days, Calendar calendar) {
        return new Shifter.ShiftWorkingDays(days, calendar);
    }

    // ========= selectors =========
    public static Selector sameDay() {
        return Selector.SameDay$.MODULE$;
    }

    public static Selector forward() {
        return new Selector.Forward(Selector.Forward$.MODULE$.apply$default$1());
    }

    public static Selector forward(Calendar calendar) {
        return new Selector.Forward(calendar);
    }

    public static Selector backward() {
        return new Selector.Backward(Selector.Backward$.MODULE$.apply$default$1());
    }

    public static Selector backward(Calendar calendar) {
        return new Selector.Backward(calendar);
    }

    public static Selector modifiedFollowing() {
        return new Selector.ModifiedFollowing(Selector.ModifiedFollowing$.MODULE$.apply$default$1());
    }

    public static Selector modifiedFollowing(Calendar calendar) {
        return new Selector.ModifiedFollowing(calendar);
    }

    public static Selector modifiedPreceding() {
        return new Selector.ModifiedPreceding(Selector.ModifiedPreceding$.MODULE$.apply$default$1());
    }

    public static Selector modifiedPreceding(Calendar calendar) {
        return new Selector.ModifiedPreceding(calendar);
    }

    // ========== position of month ==============
    public static PositionOfMonth firstDayOfMonth() {
        return PositionOfMonth$.MODULE$.FirstDayOfMonth();
    }

    public static PositionOfMonth nthDayOfMonth(int n) {
        return new PositionOfMonth.NthDayOfMonth(n);
    }

    public static PositionOfMonth lastDayOfMonth() {
        return PositionOfMonth.LastDayOfMonth$.MODULE$;
    }

    public static PositionOfMonth firstWeekdayOfMonth(Weekday weekday) {
        return PositionOfMonth$.MODULE$.FirstWeekdayOfMonth(weekday);
    }

    public static PositionOfMonth nthWeekdayOfMonth(int n, Weekday weekday) {
        return new PositionOfMonth.NthWeekdayOfMonth(n, weekday);
    }

    public static PositionOfMonth lastWeekdayOfMonth(Weekday weekday) {
        return new PositionOfMonth.LastWeekdayOfMonth(weekday);
    }

    // ========== position of year ==============
    public static PositionOfYear firstDayOfYear() {
        return PositionOfYear$.MODULE$.FirstDayOfYear();
    }

    public static PositionOfYear secondDayOfYear() {
        return PositionOfYear$.MODULE$.SecondDayOfYear();
    }

    public static PositionOfYear nthDayOfYear(int n) {
        return new PositionOfYear.NthDayOfYear(n);
    }

    public static PositionOfYear lastDayOfYear() {
        return PositionOfYear.LastDayOfYear$.MODULE$;
    }

    public static PositionOfYear firstWeekDayOfYear(Weekday weekday) {
        return PositionOfYear$.MODULE$.FirstWeekDayOfYear(weekday);
    }

    public static PositionOfYear nthWeekdayOfYear(int n, Weekday weekday) {
        return new PositionOfYear.NthWeekdayOfYear(n, weekday);
    }

    public static PositionOfYear lastWeekdayOfYear(Weekday weekday) {
        return PositionOfYear$.MODULE$.LastWeekdayOfYear(weekday);
    }

    public static PositionOfYear firstMonthOfYear(PositionOfMonth pom) {
        return PositionOfYear$.MODULE$.FirstMonthOfYear(pom);
    }

    public static PositionOfYear nthMonthOfYear(Month m, PositionOfMonth pom) {
        return new PositionOfYear.NthMonthOfYear(m, pom);
    }

    public static PositionOfYear lastMonthOfYear(PositionOfMonth pom) {
        return PositionOfYear$.MODULE$.LastMonthOfYear(pom);
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

    public static Recurrence.Days everyWorkingDay(Calendar calendar) {
        return Recurrence$.MODULE$.EveryWorkingDay(calendar);
    }

    public static Recurrence.Days workingDays(int days) {
        return Recurrence.Days$.MODULE$.workingDays(days, Recurrence.Days$.MODULE$.workingDays$default$2());
    }

    public static Recurrence.Days workingDays(int days, Calendar calendar) {
        return Recurrence.Days$.MODULE$.workingDays(days, calendar);
    }

    public static Recurrence.DaysBackward daysBackward(int days) {
        return Recurrence.DaysBackward$.MODULE$.apply(days);
    }

    public static Recurrence.DaysBackward workingDaysBackward(int days) {
        return Recurrence.DaysBackward$.MODULE$.workingDays(days, Recurrence.Days$.MODULE$.workingDays$default$2());
    }

    public static Recurrence.DaysBackward workingDaysBackward(int days, Calendar calendar) {
        return Recurrence.DaysBackward$.MODULE$.workingDays(days, calendar);
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

    public static Recurrence.Weeks weeks(Weekday weekday) {
        return Recurrence.Weeks$.MODULE$.apply(weekday);
    }

    public static Recurrence.Weeks weeks(int weeks, Weekday weekday) {
        return Recurrence.Weeks$.MODULE$.apply(weeks, weekday);
    }

    public static Recurrence.WeeksBackward weeksBackward(int weeks) {
        return new Recurrence.WeeksBackward(weeks, Recurrence.WeeksBackward$.MODULE$.apply$default$2());
    }

    public static Recurrence.WeeksBackward weeksBackward(Weekday weekday) {
        return Recurrence.WeeksBackward$.MODULE$.apply(weekday);
    }

    public static Recurrence.WeeksBackward weeksBackward(int weeks, Weekday weekday) {
        return Recurrence.WeeksBackward$.MODULE$.apply(weeks, weekday);
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

    public static Recurrence.Months months(PositionOfMonth pom) {
        return Recurrence.Months$.MODULE$.apply(pom);
    }

    public static Recurrence.Months months(int n, PositionOfMonth pom) {
        return Recurrence.Months$.MODULE$.apply(n, pom);
    }

    public static Recurrence.MonthsBackward monthsBackward(int n) {
        return Recurrence.MonthsBackward$.MODULE$.apply(n, Recurrence.MonthsBackward$.MODULE$.apply$default$2());
    }

    public static Recurrence.MonthsBackward monthsBackward(PositionOfMonth pom) {
        return Recurrence.MonthsBackward$.MODULE$.apply(pom);
    }

    public static Recurrence.MonthsBackward monthsBackward(int n, PositionOfMonth pom) {
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

    public static Recurrence.Years years(PositionOfYear poy) {
        return Recurrence.Years$.MODULE$.apply(poy);
    }

    public static Recurrence.Years years(int n, PositionOfYear poy) {
        return Recurrence.Years$.MODULE$.apply(n, poy);
    }

    public static Recurrence.YearsBackward yearsBackward(int n) {
        return Recurrence.YearsBackward$.MODULE$.apply(n, Recurrence.YearsBackward$.MODULE$.apply$default$2());
    }

    public static Recurrence.YearsBackward yearsBackward(PositionOfYear poy) {
        return Recurrence.YearsBackward$.MODULE$.apply(poy);
    }

    public static Recurrence.YearsBackward yearsBackward(int n, PositionOfYear poy) {
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
