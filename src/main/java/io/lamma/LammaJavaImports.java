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

    // ============ months ==========
    public static final Month JANUARY = January$.MODULE$;

    public static final Month FEBRUARY = February$.MODULE$;

    public static final Month MARCH = March$.MODULE$;

    public static final Month APRIL = April$.MODULE$;

    public static final Month MAY = May$.MODULE$;

    public static final Month JUNE = June$.MODULE$;

    public static final Month JULY = July$.MODULE$;

    public static final Month AUGUST = August$.MODULE$;

    public static final Month SEPTEMBER = September$.MODULE$;

    public static final Month OCTOBER = October$.MODULE$;

    public static final Month NOVEMBER = November$.MODULE$;

    public static final Month DECEMBER = December$.MODULE$;


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
    public static Date date(int yyyy, int mm, int dd) {
        return new Date(yyyy, mm, dd);
    }

    public static Date date(String isoRepr) {
        return Date$.MODULE$.apply(isoRepr);
    }

    public static HolidayRule noHoliday() {
        return NoHoliday$.MODULE$;
    }

    public static HolidayRule weekends() {
        return Weekends$.MODULE$;
    }

    public static HolidayRule simpleHolidayRule(Date... holidays) {
        return new SimpleHolidayRule(iterable(holidays).<Date>toSet());
    }

    public static HolidayRule simpleHolidayRule(Set<Date> holidays) {
        return new SimpleHolidayRule(holidays);
    }

    public static HolidayRule compositeHolidayRules(HolidayRule... rules) {
        return CompositeHolidayRule$.MODULE$.apply(iterable(rules).<HolidayRule>toSeq());
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

    public static DayOfYear firstMonthOfYear(DayOfMonth dom) {
        return DayOfYear$.MODULE$.FirstMonthOfYear(dom);
    }

    public static DayOfYear nthMonthOfYear(Month m, DayOfMonth dom) {
        return new DayOfYear.NthMonthOfYear(m, dom);
    }

    public static DayOfYear lastMonthOfYear(DayOfMonth dom) {
        return DayOfYear$.MODULE$.LastMonthOfYear(dom);
    }

    // ========== recurrence pattern ==============
    public static Daily everyDay() {
        return new Daily(1);
    }

    public static Daily everyOtherDay() {
        return new Daily(2);
    }

    public static Daily days(int days) {
        return new Daily(days);
    }

    public static Daily daysBackward(int days) {
        return new Daily(- days);
    }

    public static Weekly everyWeek() {
        return Weekly$.MODULE$.apply(1, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly everyOtherWeek() {
        return Weekly$.MODULE$.apply(2, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly weeks(int weeks) {
        return Weekly$.MODULE$.apply(weeks, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly weeks(DayOfWeek dow) {
        return Weekly$.MODULE$.apply(1, dow);
    }

    public static Weekly weeks(int weeks, DayOfWeek dow) {
        return Weekly$.MODULE$.apply(weeks, dow);
    }

    public static Weekly weeksBackward(int weeks) {
        return Weekly$.MODULE$.apply(- weeks, Weekly$.MODULE$.apply$default$2());
    }

    public static Weekly weeksBackward(DayOfWeek dow) {
        return Weekly$.MODULE$.apply(-1, dow);
    }

    public static Weekly weeksBackward(int weeks, DayOfWeek dow) {
        return Weekly$.MODULE$.apply(- weeks, dow);
    }

    public static Monthly everyMonth() {
        return Monthly$.MODULE$.apply(1, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly everyOtherMonth() {
        return Monthly$.MODULE$.apply(2, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly months(int n) {
        return Monthly$.MODULE$.apply(n, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly months(DayOfMonth dom) {
        return Monthly$.MODULE$.apply(1, dom);
    }

    public static Monthly months(int n, DayOfMonth dom) {
        return Monthly$.MODULE$.apply(n, dom);
    }

    public static Monthly monthsBackward(int n) {
        return Monthly$.MODULE$.apply(-n, Monthly$.MODULE$.apply$default$2());
    }

    public static Monthly monthsBackward(DayOfMonth dom) {
        return Monthly$.MODULE$.apply(-1, dom);
    }

    public static Monthly monthsBackward(int n, DayOfMonth dom) {
        return Monthly$.MODULE$.apply(-n, dom);
    }

    public static Yearly everyYear() {
        return Yearly$.MODULE$.apply(1, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly everyOtherYear() {
        return Yearly$.MODULE$.apply(2, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly years(int n) {
        return Yearly$.MODULE$.apply(n, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly years(DayOfYear doy) {
        return Yearly$.MODULE$.apply(1, doy);
    }

    public static Yearly years(int n, DayOfYear doy) {
        return Yearly$.MODULE$.apply(n, doy);
    }

    public static Yearly yearsBackward(int n) {
        return Yearly$.MODULE$.apply(-n, Yearly$.MODULE$.apply$default$2());
    }

    public static Yearly yearsBackward(DayOfYear doy) {
        return Yearly$.MODULE$.apply(-1, doy);
    }

    public static Yearly yearsBackward(int n, DayOfYear doy) {
        return Yearly$.MODULE$.apply(-n, doy);
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
