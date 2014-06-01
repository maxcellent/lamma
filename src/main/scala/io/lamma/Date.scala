package io.lamma

import org.joda.time.{Period => JPeriod, Days => JDays, DateTimeConstants, PeriodType, LocalDate}
import annotation.tailrec
import io.lamma.Duration._
import io.lamma.Weekday._
import io.lamma.Duration.WeekDuration
import io.lamma.Duration.DayDuration
import io.lamma.Duration.MonthDuration
import io.lamma.Duration.YearDuration

case class Date(yyyy: Int, mm: Int, dd: Int) {

  private val internal = new LocalDate(yyyy, mm, dd)

  def +(n: Int): Date = this + (n days)

  def +(d: Duration) = d match {
    case DayDuration(n) => Date(internal.plusDays(n))
    case WeekDuration(n) => Date(internal.plusWeeks(n))
    case MonthDuration(n) => Date(internal.plusMonths(n))
    case YearDuration(n) => Date(internal.plusYears(n))
  }

  def -(n: Int): Date = this - (n days)

  def -(d: Duration) = d match {
    case DayDuration(n) => Date(internal.minusDays(n))
    case WeekDuration(n) => Date(internal.minusWeeks(n))
    case MonthDuration(n) => Date(internal.minusMonths(n))
    case YearDuration(n) => Date(internal.minusYears(n))
  }

  def -(that: Date) = JDays.daysBetween(that.internal, this.internal).getDays

  def <(that: Date) = internal.isBefore(that.internal)

  def <=(that: Date) = this < that || this == that

  def >(that: Date) = internal.isAfter(that.internal)

  def >=(that: Date) = this > that || this == that

  lazy val weekday: Weekday = internal.getDayOfWeek match {
    case DateTimeConstants.MONDAY => Monday
    case DateTimeConstants.TUESDAY => Tuesday
    case DateTimeConstants.WEDNESDAY => Wednesday
    case DateTimeConstants.THURSDAY => Thursday
    case DateTimeConstants.FRIDAY => Friday
    case DateTimeConstants.SATURDAY => Saturday
    case DateTimeConstants.SUNDAY => Sunday
  }

  lazy val month = Month(mm)

  lazy val maxDayOfMonth = internal.dayOfMonth.withMaximumValue.getDayOfMonth

  lazy val maxDayOfYear = internal.dayOfYear.withMaximumValue.getDayOfYear

  lazy val isLastDayOfMonth = dd == maxDayOfMonth

  lazy val dayOfMonth = internal.getDayOfMonth

  lazy val dayOfYear = internal.getDayOfYear

  lazy val isLastDayOfYear = dayOfYear == maxDayOfYear

  lazy val monthSinceBC = yyyy * 12 + mm

  /**
   * eg, if this.weekday == Wednesday, then this is a list of all Wednesday in the same month
   */
  lazy val sameWeekdaysOfMonth = {
    val daysOfMonth = Date(yyyy, mm, 1) to Date(yyyy, mm, maxDayOfMonth)
    daysOfMonth.filter(_.weekday == weekday).toList
  }

  /**
   * eg, if this.weekday == Wednesday, then this is a list of all Wednesday in the same year
   */
  lazy val sameWeekdaysOfYear = {
    val daysOfYear = Date(yyyy, 1, 1) to Date(yyyy, 12, 31)
    daysOfYear.filter(_.weekday == weekday).toList
  }

  /**
   * next weekday including today
   */
  def nextWeekday(wd: Weekday) = Date.nextWeekday(this, wd)

  def to(that: Date) = DateRange(this, that)

  /**
   * next month position including today
   */
  def nextMonthPosition(pom: PositionOfMonth) = Date.nextPositionOfMonth(this, pom)

  /**
   * previous weekday including today
   */
  def previousWeekday(wd: Weekday) = Date.previousWeekday(this, wd)

  val toISOString = f"$yyyy%04d-$mm%02d-$dd%02d"
}

object Date {
  final val ISOStringLen = "1978-01-01".size

  def apply(d: LocalDate) = new Date(d.getYear, d.getMonthOfYear, d.getDayOfMonth)

  private[lamma] def monthsBetween(input: ((Int, Int, Int), (Int, Int, Int))): Int = {
    val (from, to) = unpack(input)
    monthsBetween(from, to)
  }

  private[lamma] def monthsBetween(d1: Date, d2: Date) = {
    new JPeriod(d1.internal, d2.internal, PeriodType.months).getMonths
  }

  private[lamma] def yearsBetween(input: ((Int, Int, Int), (Int, Int, Int))): Int = {
    val (from, to) = unpack(input)
    yearsBetween(from, to)
  }

  private[lamma] def yearsBetween(d1: Date, d2: Date) = {
    new JPeriod(d1.internal, d2.internal, PeriodType.years).getYears
  }

  @tailrec
  private def nextWeekday(d: Date, target: Weekday): Date = {
    if (d.weekday == target) {
      d
    } else {
      nextWeekday(d + 1, target)
    }
  }

  @tailrec
  private def previousWeekday(d: Date, target: Weekday): Date = {
    if (d.weekday == target) {
      d
    } else {
      previousWeekday(d - 1, target)
    }
  }

  @tailrec
  private def nextPositionOfMonth(d: Date, pom: PositionOfMonth): Date = {
    if (pom.isValidDOM(d)) {
      d
    } else {
      nextPositionOfMonth(d + 1, pom)
    }
  }

  private[lamma] def unpack(pair: ((Int, Int, Int), (Int, Int, Int))) = {
    val ((y1, m1, d1), (y2, m2, d2)) = pair
    Date(y1, m1, d1) -> Date(y2, m2, d2)
  }
}

case class DateRange(from: Date, to: Date) extends Traversable[Date] {
  override def foreach[U](f: Date => U) = DateRange.eachDate(f, from, to)
}

object DateRange {
  @tailrec
  def eachDate[U](f: Date => U, current: Date, to: Date): Unit = {
    if (current <= to) {
      f(current)
      eachDate(f, current + 1, to)
    }
  }
}
