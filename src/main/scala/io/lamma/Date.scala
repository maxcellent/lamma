package io.lamma

import annotation.tailrec
import io.lamma.Duration._
import java.sql.{Date => SDate}
import io.lamma.Weekday._
import io.lamma.PositionOfMonth._
import io.lamma.PositionOfYear._
import io.lamma.Selector.{ModifiedPreceding, ModifiedFollowing, Backward, Forward}

case class Date(yyyy: Int, mm: Int, dd: Int) extends Ordered[Date] {

  // simply make sure yyyy-MM-dd is a valid date
  SDate.valueOf(toISOString)

  def +(n: Int): Date = this + (n days)

  def +(d: Duration) = JavaDateUtil.plus(this, d)

  def -(n: Int): Date = this - (n days)

  def -(d: Duration) = JavaDateUtil.minus(this, d)

  def -(that: Date) = JavaDateUtil.daysBetween(that, this)

  /**
   * select the date with forward convention for given calendar.
   *
   * @see io.lamma.Selector.Forward
   */
  def forward(cal: Calendar) = Forward(cal).select(this)

  /**
   * select the date with backward convention for given calendar.
   *
   * @see io.lamma.Selector.Backward
   */
  def backward(cal: Calendar) = Backward(cal).select(this)

  /**
   * select the date with modified following convention for given calendar.
   *
   * @see io.lamma.Selector.ModifiedFollowing
   */
  def modifiedFollowing(cal: Calendar) = ModifiedFollowing(cal).select(this)

  /**
   * select the date with modified preceding convention for given calendar.
   *
   * @see io.lamma.Selector.ModifiedPreceding
   */
  def modifiedPreceding(cal: Calendar) = ModifiedPreceding(cal).select(this)

  lazy val weekday = JavaDateUtil.dayOfWeek(this)

  lazy val isMonday = weekday == Monday

  lazy val isTuesday = weekday == Tuesday

  lazy val isWednesday = weekday == Wednesday

  lazy val isThursday = weekday == Thursday

  lazy val isFriday = weekday == Friday

  lazy val isSaturday = weekday == Saturday

  lazy val isSunday = weekday == Sunday

  lazy val month = Month(mm)

  /**
   * max day of this month, different month length and leap month are considered
   */
  lazy val maxDayOfMonth = JavaDateUtil.maxDayOfMonth(this)

  /**
   * max day of this year, leap year is considered
   */
  lazy val maxDayOfYear = JavaDateUtil.maxDayOfYear(this)

  lazy val isLastDayOfMonth = dd == maxDayOfMonth

  lazy val dayOfMonth = JavaDateUtil.dayOfMonth(this)

  lazy val dayOfYear = JavaDateUtil.dayOfYear(this)

  lazy val isLastDayOfYear = dayOfYear == maxDayOfYear

  lazy val monthSinceBC = yyyy * 12 + mm

  /**
   * The first day of this week (Monday) <br>
   *   http://en.wikipedia.org/wiki/ISO_week_date
   */
  lazy val thisWeekBegin = (this + 1).pastMonday

  /**
   * The last day of this week (Sunday) <br>
   *   http://en.wikipedia.org/wiki/ISO_week_date
   */
  lazy val thisWeekEnd = (this - 1).comingSunday

  /**
   * an iterable for every day in this week <br>
   *   (week starts on Monday according to ISO 8601: http://en.wikipedia.org/wiki/ISO_week_date)
   */
  lazy val thisWeed = thisWeekBegin to thisWeekEnd

  /**
   * first day of the month
   */
  lazy val thisMonthBegin = Date(yyyy, mm, 1)

  /**
   * last day of the month, different month end and leap month are handled properly
   */
  lazy val thisMonthEnd = Date(yyyy, mm, maxDayOfMonth)

  /**
   * an iterable for every day in the month
   */
  lazy val thisMonth = thisMonthBegin to thisMonthEnd

  /**
   * first day of the year
   */
  lazy val thisYearBegin = Date(yyyy, 1, 1)

  /**
   * last day of the year
   */
  lazy val thisYearEnd = Date(yyyy, 12, 31)

  /**
   * an iterable for every day in the year
   */
  lazy val thisYear = thisYearBegin to thisYearEnd

  /**
   * Every day in the same month with same weekday <br>
   * eg, if this.weekday == Wednesday, then this is a list of all Wednesday in the same month
   */
  lazy val sameWeekdaysOfMonth = thisMonth.filter(_.weekday == weekday).toList

  /**
   * Every day in the same year with same weekday <br>
   * eg, if this.weekday == Wednesday, then this is a list of all Wednesday in the same year
   */
  lazy val sameWeekdaysOfYear = thisYear.filter(_.weekday == weekday).toList

  /**
   * calculate the coming weekday excluding this date: <br>
   *   <br>
   *   For example: <br>
   *   Date(2014-07-05).comingWeekday(Monday) => Date(2014-07-07) <br>
   *   Date(2014-07-05).comingWeekday(Saturday) => Date(2014-07-12) // note 2014-07-05 itself is already Saturday <br>
   */
  def comingWeekday(wd: Weekday) = Date.comingWeekday(this + 1, wd)

  /**
   * shorthand of comingWeekday(Monday)
   */
  lazy val comingMonday = comingWeekday(Monday)

  /**
   * shorthand of comingWeekday(Tuesday)
   */
  lazy val comingTuesday = comingWeekday(Tuesday)

  /**
   * shorthand of comingWeekday(Wednesday)
   */
  lazy val comingWednesday = comingWeekday(Wednesday)

  /**
   * shorthand of comingWeekday(Thursday)
   */
  lazy val comingThursday = comingWeekday(Thursday)

  /**
   * shorthand of comingWeekday(Friday)
   */
  lazy val comingFriday = comingWeekday(Friday)

  /**
   * shorthand of comingWeekday(Saturday)
   */
  lazy val comingSaturday = comingWeekday(Saturday)

  /**
   * shorthand of comingWeekday(Sunday)
   */
  lazy val comingSunday = comingWeekday(Sunday)

  /**
   * past weekday excluding this date<br>
   *   <br>
   *   For example: <br>
   *   Date(2014-07-05).pastWeekday(Monday) => Date(2014-06-30) <br>
   *   Date(2014-07-05).pastWeekday(Saturday) => Date(2014-06-28) // note 2014-07-05 itself is already Saturday <br>
   */
  def pastWeekday(wd: Weekday) = Date.pastWeekday(this - 1, wd)

  /**
   * shorthand of pastWeekday(Monday)
   */
  lazy val pastMonday = pastWeekday(Monday)

  /**
   * shorthand of pastWeekday(Tuesday)
   */
  lazy val pastTuesday = pastWeekday(Tuesday)

  /**
   * shorthand of pastWeekday(Wednesday)
   */
  lazy val pastWednesday = pastWeekday(Wednesday)

  /**
   * shorthand of pastWeekday(Thursday)
   */
  lazy val pastThursday = pastWeekday(Thursday)

  /**
   * shorthand of pastWeekday(Friday)
   */
  lazy val pastFriday = pastWeekday(Friday)

  /**
   * shorthand of pastWeekday(Saturday)
   */
  lazy val pastSaturday = pastWeekday(Saturday)

  /**
   * shorthand of pastWeekday(Sunday)
   */
  lazy val pastSunday = pastWeekday(Sunday)

  /**
   * coming day of month excluding this date
   */
  def comingDayOfMonth(pom: PositionOfMonth) = Date.comingDayOfMonth(this + 1, pom)

  /**
   * shorthand of comingDayOfMonth(LastDayOfMonth)<br>
   *   For example:<br>
   *   Date(2014, 7, 30).comingMonthEnd => Date(2014, 7, 31)<br>
   *   Date(2014, 7, 31).comingMonthEnd => Date(2014, 8, 31)<br>
   */
  lazy val comingMonthEnd = comingDayOfMonth(LastDayOfMonth)

  /**
   * shorthand of comingDayOfMonth(FirstDayOfMonth)<br>
   *   For example:<br>
   *   Date(2014, 7, 31).comingMonthBegin => Date(2014, 8, 1)<br>
   *   Date(2014, 8,  1).comingMonthBegin => Date(2014, 9, 1)<br>
   */
  lazy val comingMonthBegin = comingDayOfMonth(FirstDayOfMonth)

  /**
   * past day of month excluding this date
   */
  def pastDayOfMonth(pom: PositionOfMonth) = Date.pastDayOfMonth(this - 1, pom)

  /**
   * shorthand of pastDayOfMonth(LastDayOfMonth)<br>
   *  For example:<br>
   *  Date(2014, 8, 5).pastMonthEnd => Date(2014, 7, 31)<br>
   *  Date(2014, 7, 31).pastMonthEnd => Date(2014, 6, 30)<br>
   */
  lazy val pastMonthEnd = pastDayOfMonth(LastDayOfMonth)

  /**
   * shorthand of pastDayOfMonth(FirstDayOfMonth)<br>
   *  For example:<br>
   *  Date(2014, 8, 2).pastMonthBegin => Date(2014, 8, 1)<br>
   *  Date(2014, 8, 1).pastMonthBegin => Date(2014, 7, 1)<br>
   */
  lazy val pastMonthBegin = pastDayOfMonth(FirstDayOfMonth)

  /**
   * coming day of year excluding this date
   */
  def comingDayOfYear(poy: PositionOfYear) = Date.comingDayOfYear(this + 1, poy)

  /**
   * shorthand of comingDayOfYear(LastDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).comingYearEnd => Date(2014, 12, 31)  <br>
   *     Date(2014, 12, 31).comingYearEnd => Date(2015, 12, 31)<br>
   */
  lazy val comingYearEnd = comingDayOfYear(LastDayOfYear)

  /**
   * shorthand of comingDayOfYear(FirstDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).comingYearBegin => Date(2015, 1, 1)<br>
   *     Date(2015, 1, 1).comingYearBegin => Date(2016, 1, 1)<br>
   */
  lazy val comingYearBegin = comingDayOfYear(FirstDayOfYear)

  /**
   * past day of year excluding this date
   */
  def pastDayOfYear(poy: PositionOfYear) = Date.pastDayOfYear(this - 1, poy)

  /**
   * shorthand of pastDayOfYear(LastDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).pastYearEnd => Date(2013, 12, 31) <br>
   *     Date(2013, 12, 31).pastYearEnd => Date(2012, 12, 31) <br>
   */
  lazy val pastYearEnd = pastDayOfYear(LastDayOfYear)

  /**
   * shorthand of pastDayOfYear(FirstDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).pastYearBegin => Date(2014, 1, 1) <br>
   *     Date(2014, 1, 1).pastYearBegin => Date(2013, 1, 1) <br>
   */
  lazy val pastYearBegin = pastDayOfYear(FirstDayOfYear)

  def compare(that: Date) = this.toISOString.compare(that.toISOString)

  /**
   * generate a DateRange iterable, which can be used similar as scala.collection.immutable.Range. eg, <br>
   *   for (d <- Date(2014, 5, 5) to Date(2014, 5, 10)) println(d)
   */
  def to(that: Date) = DateRange(this, that)

  /**
   * standard ISO string in yyyy-mm-dd. eg, 2014-05-23 or 2014-11-02
   */
  lazy val toISOString = f"$yyyy%04d-$mm%02d-$dd%02d"
}

object Date {
  final val ISOStringLen = "1978-01-01".size

  private[lamma] def monthsBetween(input: ((Int, Int, Int), (Int, Int, Int))): Int = {
    val (from, to) = unpack(input)
    monthsBetween(from, to)
  }

  private[lamma] def monthsBetween(d1: Date, d2: Date) = JavaDateUtil.monthsBetween(d1, d2)

  private[lamma] def yearsBetween(input: ((Int, Int, Int), (Int, Int, Int))): Int = {
    val (from, to) = unpack(input)
    yearsBetween(from, to)
  }

  private[lamma] def yearsBetween(d1: Date, d2: Date) = JavaDateUtil.yearsBetween(d1, d2)

  @tailrec
  private def comingWeekday(d: Date, target: Weekday): Date = {
    if (d.weekday == target) {
      d
    } else {
      comingWeekday(d + 1, target)
    }
  }

  @tailrec
  private def pastWeekday(d: Date, target: Weekday): Date = {
    if (d.weekday == target) {
      d
    } else {
      pastWeekday(d - 1, target)
    }
  }

  @tailrec
  private def comingDayOfMonth(d: Date, pom: PositionOfMonth): Date = {
    if (pom.isValidDOM(d)) {
      d
    } else {
      comingDayOfMonth(d + 1, pom)
    }
  }

  @tailrec
  private def pastDayOfMonth(d: Date, pom: PositionOfMonth): Date = {
    if (pom.isValidDOM(d)) {
      d
    } else {
      pastDayOfMonth(d - 1, pom)
    }
  }

  @tailrec
  private def comingDayOfYear(d: Date, poy: PositionOfYear): Date = {
    if (poy.isValidDOY(d)) {
      d
    } else {
      comingDayOfYear(d + 1, poy)
    }
  }

  @tailrec
  private def pastDayOfYear(d: Date, poy: PositionOfYear): Date = {
    if (poy.isValidDOY(d)) {
      d
    } else {
      pastDayOfYear(d - 1, poy)
    }
  }

  private[lamma] def unpack(pair: ((Int, Int, Int), (Int, Int, Int))) = {
    val ((y1, m1, d1), (y2, m2, d2)) = pair
    Date(y1, m1, d1) -> Date(y2, m2, d2)
  }
}
