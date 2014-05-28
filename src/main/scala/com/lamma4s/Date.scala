package com.lamma4s

import org.joda.time.{Period => JPeriod, PeriodType, LocalDate}
import annotation.tailrec
import com.lamma4s.Duration._

case class Date(yyyy: Int, mm: Int, dd: Int) {

  private val internal = new LocalDate(yyyy, mm, dd)

  def +(n: Int): Date = this + (n days)

  def +(d: Duration) = d match {
    case Days(n) => Date(internal.plusDays(n))
    case Weeks(n) => Date(internal.plusWeeks(n))
    case Months(n) => Date(internal.plusMonths(n))
    case Years(n) => Date(internal.plusYears(n))
  }

  def -(n: Int): Date = this - (n days)

  def -(d: Duration) = d match {
    case Days(n) => Date(internal.minusDays(n))
    case Weeks(n) => Date(internal.minusWeeks(n))
    case Months(n) => Date(internal.minusMonths(n))
    case Years(n) => Date(internal.minusYears(n))
  }

  def <(that: Date) = internal.isBefore(that.internal)

  def <=(that: Date) = this < that || this == that

  def >(that: Date) = internal.isAfter(that.internal)

  def >=(that: Date) = this > that || this == that

  lazy val weekday = Weekday.fromJoda(internal)

  lazy val month = Month(mm)

  lazy val maxDayOfMonth = internal.dayOfMonth.withMaximumValue.getDayOfMonth

  lazy val maxDayOfYear = internal.dayOfYear.withMaximumValue.getDayOfYear

  lazy val isLastDayOfMonth = dd == maxDayOfMonth

  lazy val dayOfYear = internal.getDayOfYear

  lazy val isLastDayOfYear = dayOfYear == maxDayOfYear
  
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
}

object Date {
  def apply(d: LocalDate) = new Date(d.getYear, d.getMonthOfYear, d.getDayOfMonth)

  private[lamma4s] def monthsBetween(d1: Date, d2: Date) = {
    new JPeriod(d1.internal, d2.internal, PeriodType.months).getMonths
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
