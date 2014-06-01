package io.lamma

import annotation.tailrec
import io.lamma.Duration._
import java.sql.{Date => SDate}

case class Date(yyyy: Int, mm: Int, dd: Int) extends Ordered[Date] {

  // simply make sure yyyy-MM-dd is a valid date
  SDate.valueOf(toISOString)

  def +(n: Int): Date = this + (n days)

  def +(d: Duration) = JavaDateUtil.plus(this, d)

  def -(n: Int): Date = this - (n days)

  def -(d: Duration) = JavaDateUtil.minus(this, d)

  def -(that: Date) = JavaDateUtil.daysBetween(that, this)

  lazy val weekday = JavaDateUtil.dayOfWeek(this)

  lazy val month = Month(mm)

  lazy val maxDayOfMonth = JavaDateUtil.maxDayOfMonth(this)

  lazy val maxDayOfYear = JavaDateUtil.maxDayOfYear(this)

  lazy val isLastDayOfMonth = dd == maxDayOfMonth

  lazy val dayOfMonth = JavaDateUtil.dayOfMonth(this)

  lazy val dayOfYear = JavaDateUtil.dayOfYear(this)

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

  lazy val toISOString = f"$yyyy%04d-$mm%02d-$dd%02d"
  
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

  def compare(that: Date) = this.toISOString.compare(that.toISOString)
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
