package io.lamma

import java.sql.{Date => SDate}
import java.util.Calendar
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
  /**
   * Returns a copy of this Date plus the specified number of days. <br>
   *   Following lines are identical:
   *   {{{
   *   Date(2014, 5, 5) + 5
   *   Date(2014, 5, 5) - -5
   *   Date(2014, 5, 5) + 5.days
   *   Date(2014, 5, 5) + (5 days)
   *   Date(2014, 5, 5) - -5.days
   *   Date(2014, 5, 5) - (-5 days)
   *   Date(2014, 5, 5).plusDays(5)   // Java friendly
   *   Date(2014, 5, 5).minusDays(-5) // Java friendly
   *   }}}
   *
   * @param n the amount of days to add, may be negative
   * @return new Date object plus the increased days
   */
  def +(n: Int): Date = this + (n days)

  /**
   * Returns a copy of this Date plus the specified duration. <br>
   *   <em>dot</em> or <em>bracket</em> is required to prevent confusing compiler.
   *   For example:
   *   {{{
   *     Date(2014, 5, 5) + (5 weeks)
   *     Date(2014, 5, 5) + 5.weeks
   *     Date(2014, 5, 5) + (2 years)
   *     Date(2014, 5, 5) + 2.years
   *   }}}
   *
   *   Following lines are identical:
   *   {{{
   *   Date(2014, 5, 5) + 5
   *   Date(2014, 5, 5) - -5
   *   Date(2014, 5, 5) + 5.days
   *   Date(2014, 5, 5) + (5 days)
   *   Date(2014, 5, 5) - -5.days
   *   Date(2014, 5, 5) - (-5 days)
   *   Date(2014, 5, 5).plusDays(5)   // Java friendly
   *   Date(2014, 5, 5).minusDays(-5) // Java friendly
   *   }}}
   *
   * @param d duration to add, may be negative
   * @return new Date object plus the increased duration
   */
  def +(d: Duration) = JavaDateUtil.plus(this, d)

  /**
   * Returns a copy of this Date minus the specified number of days. <br>
   *   Following lines are identical:
   *   {{{
   *   Date(2014, 5, 5) - 5
   *   Date(2014, 5, 5) + -5
   *   Date(2014, 5, 5) - 5.days
   *   Date(2014, 5, 5) - (5 days)
   *   Date(2014, 5, 5) + -5.days
   *   Date(2014, 5, 5) + (-5 days)
   *   Date(2014, 5, 5).minusDays(5)  // Java friendly
   *   Date(2014, 5, 5).plusDays(-5)  // Java friendly
   *   }}}
   *
   * @param n the amount of days to minus, may be positive
   * @return new Date object minus the decreased days
   */
  def -(n: Int): Date = this - (n days)

  /**
   * Returns a copy of this Date minus the specified duration. <br>
   *   <em>dot</em> or <em>bracket</em> is required to prevent confusing compiler.
   *   For example:
   *   {{{
   *     Date(2014, 5, 5) - (5 weeks)
   *     Date(2014, 5, 5) - 5.weeks
   *     Date(2014, 5, 5) - (2 years)
   *     Date(2014, 5, 5) - 2.years
   *   }}}
   *
   *   Following lines are identical:
   *   {{{
   *   Date(2014, 5, 5) - 5
   *   Date(2014, 5, 5) + -5
   *   Date(2014, 5, 5) - 5.days
   *   Date(2014, 5, 5) - (5 days)
   *   Date(2014, 5, 5) + -5.days
   *   Date(2014, 5, 5) + (-5 days)
   *   Date(2014, 5, 5).minusDays(5)  // Java friendly
   *   Date(2014, 5, 5).plusDays(-5)  // Java friendly
   *   }}}
   *
   * @param d duration to minus, may be positive
   * @return new Date object minus the decreased duration
   */
  def -(d: Duration) = JavaDateUtil.minus(this, d)

  /**
   * calculate the different between this date and that date
   * {{{
   *   Date(2014, 5, 5) - Date(2014, 5, 1) => 4
   * }}}
   */
  def -(that: Date) = JavaDateUtil.daysBetween(that, this)

  // + / - operations for Java use
  /**
   * <b>Java Friendly.</b> it is recommended to use [[+]] for Scala.
   *
   * Returns a copy of this Date plus the specified number of days. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).plusDays(5);
   *   new Date(2014, 5, 5).minusDays(-5);
   *   }}}
   *
   * @param n the amount of days to add, may be negative
   * @return new Date object plus the increased days
   */
  def plusDays(n: Int) = this + n

  /**
   * <b>Java Friendly.</b> it is recommended to use [[-]] for Scala.
   *
   * Returns a copy of this Date minus the specified number of days. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).minusDays(5);
   *   new Date(2014, 5, 5).plusDays(-5);
   *   }}}
   *
   * @param n the amount of days to minus, may be positive
   * @return new Date object minus the decreased days
   */
  def minusDays(n: Int) = this - n

  /**
   * <b>Java Friendly.</b> it is recommended to use [[+]] for Scala.
   *
   * Returns a copy of this Date plus the specified number of weeks. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).plusWeeks(5);
   *   new Date(2014, 5, 5).minusWeeks(-5);
   *   }}}
   *
   * @param n the amount of weeks to add, may be negative
   * @return new Date object plus the increased weeks
   */
  def plusWeeks(n: Int) = this + (n weeks)

  /**
   * <b>Java Friendly.</b> it is recommended to use [[-]] for Scala.
   *
   * Returns a copy of this Date minus the specified number of weeks. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).minusWeeks(5);
   *   new Date(2014, 5, 5).minusWeeks(-5);
   *   }}}
   *
   * @param n the amount of weeks to minus, may be positive
   * @return new Date object minus the decreased weeks
   */
  def minusWeeks(n: Int) = this - (n weeks)

  /**
   * <b>Java Friendly.</b> it is recommended to use [[+]] for Scala.
   *
   * Returns a copy of this Date plus the specified number of months. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).plusMonths(5);
   *   new Date(2014, 5, 5).minusMonths(-5);
   *   }}}
   *
   * @param n the amount of months to add, may be negative
   * @return new Date object plus the increased months
   */
  def plusMonths(n: Int) = this + (n months)

  /**
   * <b>Java Friendly.</b> it is recommended to use [[-]] for Scala.
   *
   * Returns a copy of this Date minus the specified number of months. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).minusMonths(5);
   *   new Date(2014, 5, 5).minusMonths(-5);
   *   }}}
   *
   * @param n the amount of months to minus, may be positive
   * @return new Date object minus the decreased months
   */
  def minusMonths(n: Int) = this - (n months)

  /**
   * <b>Java Friendly.</b> it is recommended to use [[+]] for Scala.
   *
   * Returns a copy of this Date plus the specified number of years. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).plusYears(5);
   *   new Date(2014, 5, 5).minusYears(-5);
   *   }}}
   *
   * @param n the amount of years to add, may be negative
   * @return new Date object plus the increased years
   */
  def plusYears(n: Int) = this + (n years)

  /**
   * <b>Java Friendly.</b> it is recommended to use [[-]] for Scala.
   *
   * Returns a copy of this Date minus the specified number of years. <br>
   *   Following lines are identical:
   *   {{{
   *   new Date(2014, 5, 5).minusYears(5);
   *   new Date(2014, 5, 5).minusYears(-5);
   *   }}}
   *
   * @param n the amount of years to minus, may be positive
   * @return new Date object minus the decreased years
   */
  def minusYears(n: Int) = this - (n years)

  /**
   * <b>Java Friendly.</b> it is recommended to use [[-]] for Scala. <br>
   *
   * calculate the different between this date and that date
   * {{{
   *   new Date(2014, 5, 5).minus(new Date(2014, 5, 1)) => 4
   * }}}
   */
  def minus(that: Date) = this - that

  /**
   * <b>Java Friendly.</b> It is recommended to use [[<]] for Scala.
   */
  def isBefore(that: Date) = this < that

  /**
   * <b>Java Friendly.</b> It is recommended to use [[>]] for Scala.
   */
  def isAfter(that: Date) = this > that

  /**
   * <b>Java Friendly.</b> It is recommended to use [[<=]] for Scala.
   */
  def isOnOrBefore(that: Date) = this <= that

  /**
   * <b>Java Friendly.</b> It is recommended to use [[>=]] for Scala.
   */
  def isOnOrAfter(that: Date) = this >= that

  def shift(shifter: Shifter) = shifter.shift(this)

  def select(selector: Selector) = selector.select(this)

  /**
   * select the date with forward convention for given holiday.
   *
   * @see [[io.lamma.Selector.Forward]]
   */
  def forward(cal: HolidayRule) = select(Forward(cal))

  /**
   * select the date with backward convention for given holiday.
   *
   * @see [[io.lamma.Selector.Backward]]
   */
  def backward(cal: HolidayRule) = select(Backward(cal))

  /**
   * select the date with modified following convention for given holiday.
   *
   * @see [[io.lamma.Selector.ModifiedFollowing]]
   */
  def modifiedFollowing(cal: HolidayRule) = select(ModifiedFollowing(cal))

  /**
   * select the date with modified preceding convention for given holiday.
   *
   * @see [[io.lamma.Selector.ModifiedPreceding]]
   */
  def modifiedPreceding(cal: HolidayRule) = select(ModifiedPreceding(cal))

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

  def until(isoRepr: String): DateRangeBuilder = until(Date(isoRepr))

  def until(yyyy: Int, mm: Int, dd: Int): DateRangeBuilder = until(Date(yyyy, mm, dd))

  /**
   * standard ISO string in yyyy-mm-dd. eg, 2014-05-23 or 2014-11-02
   */
  lazy val toISOString = f"$yyyy%04d-$mm%02d-$dd%02d"

  /**
   * int representation in yyyyMMdd form
   */
  lazy val toISOInt = yyyy * 10000 + mm * 100 + dd
}

object Date {
  private[lamma] final val ISOStringLen = "1978-01-01".size

  private[lamma] def monthsBetween(d: (Date, Date)): Int = monthsBetween(d._1, d._2)

  private[lamma] def monthsBetween(d1: Date, d2: Date) = JavaDateUtil.monthsBetween(d1, d2)

  private[lamma] def yearsBetween(input: (Date, Date)): Int = yearsBetween(input._1, input._2)

  private[lamma] def yearsBetween(d1: Date, d2: Date) = JavaDateUtil.yearsBetween(d1, d2)

  /**
   * @param isoRepr in yyyy-MM-dd format, eg, 2014-02-26
   */
  def apply(isoRepr: String): Date = JavaDateUtil.date(JavaDateUtil.calendar(isoRepr))

  /**
   * Allocates a <code>Date</code> object and initializes it to
   * represent the integer in yyyyMMdd form
   *
   * @param date date representation in yyyyMMdd form
   */
  def apply(date: Int): Date = {
    val yyyy = date / 10000
    val mm = date % 10000 / 100
    val dd = date % 100
    Date(yyyy, mm, dd)
  }

  /**
   * @return a new instance of today in system timezone
   */
  def today() = JavaDateUtil.date(Calendar.getInstance)
}
