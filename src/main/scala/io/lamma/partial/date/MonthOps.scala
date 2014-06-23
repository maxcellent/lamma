package io.lamma.partial.date

import io.lamma.DayOfMonth._
import io.lamma.{JavaDateUtil, Month, DayOfMonth, Date}

import collection.JavaConverters._
import scala.annotation.tailrec

private[lamma] trait MonthOps {
  this: Date =>

  lazy val month = Month(mm)

  lazy val monthSinceBC = yyyy * 12 + mm

  /**
   * max day of this month, different month length and leap month are considered
   */
  lazy val maxDayOfMonth = JavaDateUtil.maxDayOfMonth(this)

  lazy val isLastDayOfMonth = dd == maxDayOfMonth

  lazy val dayOfMonth = dd

  def thisDayOfMonth(dom: DayOfMonth) = dayOfThisMonth(dom)

  /**
   * find the day of this month matching input DayOfMonth <br>
   * an IllegalArgumentException will be thrown if there is no or more than one dates.
   */
  def dayOfThisMonth(dom: DayOfMonth) = {
    val matched = thisMonth.filter(dom.isValidDOM)
    require(matched.size == 1, s"Invalid DayOfMonth: $dom. Matched dates: $matched")
    matched.head
  }

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
   * Every day in the same month with same dow <br>
   * eg, if this.dayOfWeek == Wednesday, then this is a list of all Wednesday in the same month
   */
  lazy val sameWeekdaysOfMonth = thisMonth.filter(_.dayOfWeek == dayOfWeek).toList

  lazy val sameWeekdaysOfMonth4j = sameWeekdaysOfMonth.asJava

  /**
   * coming day of month excluding this date
   */
  def comingDayOfMonth(dom: DayOfMonth) = MonthOps.comingDayOfMonth(this + 1, dom)

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
  def pastDayOfMonth(dom: DayOfMonth) = MonthOps.pastDayOfMonth(this - 1, dom)

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
}

private object MonthOps {
  @tailrec
  private def comingDayOfMonth(d: Date, dom: DayOfMonth): Date = {
    if (dom.isValidDOM(d)) {
      d
    } else {
      comingDayOfMonth(d + 1, dom)
    }
  }

  @tailrec
  private def pastDayOfMonth(d: Date, dom: DayOfMonth): Date = {
    if (dom.isValidDOM(d)) {
      d
    } else {
      pastDayOfMonth(d - 1, dom)
    }
  }
}