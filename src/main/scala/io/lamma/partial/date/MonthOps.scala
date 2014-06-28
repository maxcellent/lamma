package io.lamma.partial.date

import io.lamma._
import io.lamma.DayOfMonth._
import io.lamma.{JavaDateUtil, Month, DayOfMonth, Date}

import collection.JavaConverters._
import annotation.tailrec

private[lamma] trait MonthOps {
  this: Date =>

  lazy val month = Month.of(mm)

  /**
   * max day of this month, different month length and leap month are considered
   */
  lazy val maxDayOfMonth = JavaDateUtil.maxDayOfMonth(this)

  lazy val isLastDayOfMonth = dd == maxDayOfMonth

  lazy val dayOfMonth = dd

  @deprecated(message = "replaced by withDayOfMonth", since = "2.1.0")
  def thisDayOfMonth(dom: DayOfMonth) = withDayOfMonth(dom)

  @deprecated(message = "replaced by withDayOfMonth", since = "2.1.0")
  def dayOfThisMonth(dom: DayOfMonth) = withDayOfMonth(dom)

  /**
   * find the day of this month matching input DayOfMonth <br>
   * an IllegalArgumentException will be thrown if there is no or more than one dates.
   */
  def withDayOfMonth(dom: DayOfMonth) = {
    val matched = daysOfMonth.filter(dom.isValidDOM)
    require(matched.size == 1, s"Invalid DayOfMonth: $dom. Matched dates: $matched")
    matched.head
  }

  @deprecated(message = "replaced by firstDayOfMonth", since = "2.1.0")
  def thisMonthBegin = firstDayOfMonth

  /**
   * first day of current month
   */
  lazy val firstDayOfMonth = Date(yyyy, mm, 1)

  @deprecated(message = "replaced by lastDayOfMonth", since = "2.1.0")
  def thisMonthEnd = lastDayOfMonth

  /**
   * last day of the month, different month end and leap month are handled properly
   */
  lazy val lastDayOfMonth = Date(yyyy, mm, maxDayOfMonth)

  @deprecated(message = "replaced by daysOfMonth", since = "2.1.0")
  def thisMonth =  daysOfMonth

  /**
   * an iterable for every day in the month
   */
  lazy val daysOfMonth = firstDayOfMonth to lastDayOfMonth

  lazy val daysOfMonth4j = daysOfMonth.javaIterable

  /**
   * Every day in the same month with same dow <br>
   * eg, if this.dayOfWeek == Wednesday, then this is a list of all Wednesday in the same month
   */
  lazy val sameWeekdaysOfMonth = daysOfMonth.filter(_.dayOfWeek == dayOfWeek).toList

  lazy val sameWeekdaysOfMonth4j = sameWeekdaysOfMonth.asJava

  def nextOrSame(dom: DayOfMonth) = MonthOps.nextOrSame(this, dom)

  @deprecated("replace with next(DayOfMonth)", "2.1.0")
  def comingDayOfMonth(dom: DayOfMonth) = next(dom)
  
  /**
   * coming day of month excluding this date
   */
  def next(dom: DayOfMonth) = MonthOps.nextOrSame(this + 1, dom)

  @deprecated("replaced by nextLastDayOfMonth", "2.1.0")
  def comingMonthEnd = nextLastDayOfMonth

  /**
   * shorthand of comingDayOfMonth(LastDayOfMonth)<br>
   *   For example:
   *   {{{
   *   Date(2014, 7, 30).comingMonthEnd => Date(2014, 7, 31)
   *   Date(2014, 7, 31).comingMonthEnd => Date(2014, 8, 31)
   *   }}}
   *
   *   Note this is different from lastDayOfNextMonth
   */
  lazy val nextLastDayOfMonth = next(LastDayOfMonth)

  @deprecated("replaced by comingFirstDayOfMonth", "2.1.0")
  def comingMonthBegin = next(FirstDayOfMonth)

  /**
   * shorthand of comingDayOfMonth(FirstDayOfMonth)<br>
   *   For example:
   *   {{{
   *   Date(2014, 7, 31).comingMonthBegin => Date(2014, 8, 1)
   *   Date(2014, 8,  1).comingMonthBegin => Date(2014, 9, 1)
   *   }}}
   */
  lazy val nextFirstDayOfMonth = next(FirstDayOfMonth)

  def previousOrSame(dom: DayOfMonth) = MonthOps.previousOrSame(this, dom)

  @deprecated("replace with previous(DayOfMonth)", "2.1.0")
  def pastDayOfMonth(dom: DayOfMonth) = MonthOps.previousOrSame(this - 1, dom)
  
  /**
   * past day of month excluding this date
   */
  def previous(dom: DayOfMonth) = MonthOps.previousOrSame(this - 1, dom)

  @deprecated("replace by previousLastDayOfMonth", "2.1.0")
  def pastMonthEnd = previousLastDayOfMonth

  /**
   * shorthand of pastDayOfMonth(LastDayOfMonth)<br>
   *  For example:<br>
   *    {{{
   *    Date(2014, 8, 5).pastMonthEnd => Date(2014, 7, 31)
   *    Date(2014, 7, 31).pastMonthEnd => Date(2014, 6, 30)
   *    }}}
   */
  lazy val previousLastDayOfMonth = previous(LastDayOfMonth)

  @deprecated("replaced by previousFirstDayOfMonth", "2.1.0")
  def pastMonthBegin = previousFirstDayOfMonth

  /**
   * shorthand of pastDayOfMonth(FirstDayOfMonth)<br>
   *  For example:<br>
   *    {{{
   *    Date(2014, 8, 2).pastMonthBegin => Date(2014, 8, 1)
   *    Date(2014, 8, 1).pastMonthBegin => Date(2014, 7, 1)
   *    }}}
   *
   *  Note this is different from firstDayOfPreviousMonth
   */
  lazy val previousFirstDayOfMonth = previous(FirstDayOfMonth)

  /**
   * day of next month. A shorthand of
   * {{{this + (1 month) dayOfMonth (dom)}}}
   */
  def dayOfNextMonth(dom: DayOfMonth) = this + (1 month) withDayOfMonth dom

  /**
   * shorthand of
   * {{{dayOfNextMonth(FirstDayOfMonth)}}}
   */
  lazy val firstDayOfNextMonth = dayOfNextMonth(FirstDayOfMonth)

  /**
   * shorthand of
   * {{{dayOfNextMonth(LastDayOfMonth)}}}
   */
  lazy val lastDayOfNextMonth = dayOfNextMonth(LastDayOfMonth)

  /**
   * day of previous month. A shorthand of
   * {{{this - (1 month) dayOfMonth (dom)}}}
   */
  def dayOfPreviousMonth(dom: DayOfMonth) = this - (1 month) withDayOfMonth dom

  /**
   * shorthand of
   * {{{dayOfPreviousMonth(FirstDayOfMonth)}}}
   */
  lazy val firstDayOfPreviousMonth = dayOfPreviousMonth(FirstDayOfMonth)

  /**
   * shorthand of
   * {{{dayOfPreviousMonth(LastDayOfMonth)}}}
   */
  lazy val lastDayOfPreviousMonth = dayOfPreviousMonth(LastDayOfMonth)

  /**
   * Returns the day-of-week in the same month.
   *
   * @param n ordinal of the month, from 1 to 5
   * @param dow DayOfWeek
   * @return a new copy of the date
   */
  def dayOfWeekInMonth(n: Int, dow: DayOfWeek) = this.withDayOfMonth(n th dow)

  def firstInMonth(dow: DayOfWeek) = this.withDayOfMonth(1 st dow)

  def lastInMonth(dow: DayOfWeek) = this.withDayOfMonth(LastWeekdayOfMonth(dow))
}

private object MonthOps {
  @tailrec
  private def nextOrSame(d: Date, dom: DayOfMonth): Date = {
    if (dom.isValidDOM(d)) {
      d
    } else {
      nextOrSame(d + 1, dom)
    }
  }

  @tailrec
  private def previousOrSame(d: Date, dom: DayOfMonth): Date = {
    if (dom.isValidDOM(d)) {
      d
    } else {
      previousOrSame(d - 1, dom)
    }
  }
}