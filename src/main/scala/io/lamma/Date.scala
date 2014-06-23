package io.lamma

import java.sql.{Date => SDate}
import io.lamma.partial.date.{YearOps, MonthOps, WeekOps}
import io.lamma.Selector.{ModifiedPreceding, ModifiedFollowing, Backward, Forward}

/**
 * immutable object to represent a Date. All mutating operations will return a new Date.
 */
case class Date(yyyy: Int, mm: Int, dd: Int)
  extends Ordered[Date] with WeekOps with MonthOps with YearOps {

  // simply make sure yyyy-MM-dd is a valid date
  SDate.valueOf(toISOString)

  // + / - operations for Scala use
  def +(n: Int): Date = this + (n days)

  def +(d: Duration) = JavaDateUtil.plus(this, d)

  def -(n: Int): Date = this - (n days)

  def -(d: Duration) = JavaDateUtil.minus(this, d)

  def -(that: Date) = JavaDateUtil.daysBetween(that, this)

  // + / - operations for Java use
  def plusDays(n: Int) = this + n

  def minusDays(n: Int) = this - n

  def plusWeeks(n: Int) = this + (n weeks)

  def minusWeeks(n: Int) = this - (n weeks)

  def plusMonths(n: Int) = this + (n months)

  def minusMonths(n: Int) = this - (n months)

  def plusYears(n: Int) = this + (n years)

  def minusYears(n: Int) = this - (n years)

  def minus(that: Date) = this - that

  // comparators for Java
  def isBefore(that: Date) = this < that

  def isAfter(that: Date) = this > that

  def isOnOrBefore(that: Date) = this <= that

  def isOnOrAfter(that: Date) = this >= that

  def shift(shifter: Shifter) = shifter.shift(this)

  def select(selector: Selector) = selector.select(this)

  /**
   * select the date with forward convention for given holiday.
   *
   * @see io.lamma.Selector.Forward
   */
  def forward(cal: HolidayRule) = Forward(cal).select(this)

  /**
   * select the date with backward convention for given holiday.
   *
   * @see io.lamma.Selector.Backward
   */
  def backward(cal: HolidayRule) = Backward(cal).select(this)

  /**
   * select the date with modified following convention for given holiday.
   *
   * @see io.lamma.Selector.ModifiedFollowing
   */
  def modifiedFollowing(cal: HolidayRule) = ModifiedFollowing(cal).select(this)

  /**
   * select the date with modified preceding convention for given holiday.
   *
   * @see io.lamma.Selector.ModifiedPreceding
   */
  def modifiedPreceding(cal: HolidayRule) = ModifiedPreceding(cal).select(this)

  def compare(that: Date) = this.toISOString.compare(that.toISOString)

  /**
   * generate a DateRange iterable, which can be used similar as scala.collection.immutable.Range. eg, <br>
   * both <em>this</em> date and <em>that</em> date included <br>
   *   for (d <- Date(2014, 5, 5) to Date(2014, 5, 10)) println(d)
   */
  def to(that: Date) = DateRangeBuilder(this, that)

  def to(isoRepr: String): DateRangeBuilder = to(Date(isoRepr))

  def to(yyyy: Int, mm: Int, dd: Int): DateRangeBuilder = to(Date(yyyy, mm, dd))

  /**
   * generate a DateRange iterable, which can be used similar as scala.collection.immutable.Range. eg, <br>
   * <em>this</em> date is included but <em>that</em> date isn't <br>
   *   for (d <- Date(2014, 5, 5) until Date(2014, 5, 10)) println(d)
   */
  def until(that: Date) = DateRangeBuilder(this, that - 1)

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

  private[lamma] def unpack(pair: ((Int, Int, Int), (Int, Int, Int))) = {
    val ((y1, m1, d1), (y2, m2, d2)) = pair
    Date(y1, m1, d1) -> Date(y2, m2, d2)
  }

  /**
   * @param isoRepr in yyyy-MM-dd format, eg, 2014-02-26
   */
  def apply(isoRepr: String): Date = JavaDateUtil.date(JavaDateUtil.calendar(isoRepr))
}
