package io.lamma.partial.date

import io.lamma.PositionOfYear._
import io.lamma.{JavaDateUtil, PositionOfYear, Date}

import scala.annotation.tailrec

private[lamma] trait YearOps {
  this: Date =>

  /**
   * max day of this year, leap year is considered
   */
  lazy val maxDayOfYear = JavaDateUtil.maxDayOfYear(this)

  lazy val dayOfYear = JavaDateUtil.dayOfYear(this)

  lazy val isLastDayOfYear = dayOfYear == maxDayOfYear

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
   * Every day in the same year with same weekday <br>
   * eg, if this.weekday == Wednesday, then this is a list of all Wednesday in the same year
   */
  lazy val sameWeekdaysOfYear = thisYear.filter(_.weekday == weekday).toList

  /**
   * coming day of year excluding this date
   */
  def comingDayOfYear(poy: PositionOfYear) = YearOps.comingDayOfYear(this + 1, poy)

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
  def pastDayOfYear(poy: PositionOfYear) = YearOps.pastDayOfYear(this - 1, poy)

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
}

private object YearOps {

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
}