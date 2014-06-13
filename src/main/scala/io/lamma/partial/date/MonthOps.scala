package io.lamma.partial.date

import io.lamma.DayOfMonth._
import io.lamma.{JavaDateUtil, Month, DayOfMonth, Date}

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

  /**
   * coming day of month excluding this date
   */
  def comingDayOfMonth(pom: DayOfMonth) = MonthOps.comingDayOfMonth(this + 1, pom)

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
  def pastDayOfMonth(pom: DayOfMonth) = MonthOps.pastDayOfMonth(this - 1, pom)

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
  private def comingDayOfMonth(d: Date, pom: DayOfMonth): Date = {
    if (pom.isValidDOM(d)) {
      d
    } else {
      comingDayOfMonth(d + 1, pom)
    }
  }

  @tailrec
  private def pastDayOfMonth(d: Date, pom: DayOfMonth): Date = {
    if (pom.isValidDOM(d)) {
      d
    } else {
      pastDayOfMonth(d - 1, pom)
    }
  }
}