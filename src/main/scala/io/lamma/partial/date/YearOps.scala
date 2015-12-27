package io.lamma.partial.date

import io.lamma.DayOfMonth.{LastDayOfMonth, LastWeekdayOfMonth, NthDayOfMonth, NthWeekdayOfMonth}
import io.lamma.DayOfYear._
import io.lamma._

import scala.annotation.tailrec
import scala.collection.JavaConverters._

private[lamma] trait YearOps {
  this: Date =>

  /**
   * max day of this year, leap year is considered
   */
  lazy val maxDayOfYear = JavaDateUtil.maxDayOfYear(this)

  lazy val dayOfYear = JavaDateUtil.dayOfYear(this)

  lazy val isLastDayOfYear = dayOfYear == maxDayOfYear

  /**
   * find the day of this year matching specified day-of-year
   */
  /**
   * find the day of this year matching specified day-of-month. DSL can be used directly.
   *
   * Usage example:
   * {{{
   *    Date(2014, 5, 5).withDayOfYear(lastDay) => Date(2014,12,31)
   *    Date(2014, 5, 5).withDayOfYear(3 rd Friday) => Date(2014,1,17)
   *    Date(2014, 5, 5).withDayOfYear(20 th day) => Date(2014,1,20)
   *    Date(2014, 5, 5).withDayOfYear(2 nd Tuesday of September) => Date(2014,9,9)
   * }}}
   */
  def withDayOfYear(doy: DayOfYear) = {
    val matched = daysOfYear.filter(doy.isValidDOY)
    require(matched.size == 1, s"Invalid DayOfYear: $doy. Matched dates: $matched")
    matched.head
  }

  /**
   * first day of the year
   */
  lazy val firstDayOfYear = Date(yyyy, 1, 1)

  /**
   * first day of next year
   */
  lazy val firstDayOfNextYear = Date(yyyy + 1, 1, 1)

  /**
   * first day of previous year. This is different from [[previousFirstDayOfYear]]
   */
  lazy val firstDayOfPreviousYear = Date(yyyy - 1, 1, 1)

  /**
   * last day of the year
   */
  lazy val lastDayOfYear = Date(yyyy, 12, 31)

  /**
   * last day of next year. This is different from [[nextLastDayOfYear]]
   */
  lazy val lastDayOfNextYear = Date(yyyy + 1, 12, 31)

  /**
   * last day of previous year
   */
  lazy val lastDayOfPreviousYear = Date(yyyy - 1, 12, 31)

  /**
   * an iterable for every day in the year
   */
  lazy val daysOfYear = firstDayOfYear to lastDayOfYear

  /**
   * <b>Java Friendly.</b> It is recommended to use [[daysOfMonth]] for Scala.
   *
   * an iterable for every day in the year
   */
  lazy val daysOfYear4j = daysOfYear.javaIterable

  /**
   * Every day in the same year with same dow <br>
   * eg, if this.dayOfWeek == Wednesday, then this is a list of all Wednesday in the same year
   */
  lazy val sameWeekdaysOfYear = daysOfYear.filter(_.dayOfWeek == dayOfWeek).toList

  /**
   * <b>Java Friendly.</b> It is recommended to use [[sameWeekdaysOfYear]] for Scala.
   *
   * Every day in the same year with same dow <br>
   * eg, if this.dayOfWeek == Wednesday, then this is a list of all Wednesday in the same year
   */
  lazy val sameWeekdaysOfYear4j = sameWeekdaysOfYear.asJava

  /**
   * This method is an alias of [[nextOrSame(DayOfYear)]] for Scala to prevent overloading when using DSL.
   *
   * Usage example:
   * {{{
   *   Date(2014, 5, 5).nextOrSameDayOfYear(3 rd Friday)
   * }}}
   */
  def nextOrSameDayOfYear(doy: DayOfYear) = nextOrSame(doy)

  /**
   * <b>Java Friendly.</b> It is recommended to use [[nextOrSameDayOfYear]] for Scala.
   *
   * coming day-of-year including this date
   */
  def nextOrSame(doy: DayOfYear) = YearOps.nextOrSame(this, doy)

  /**
   * This method is an alias of [[next(DayOfYear)]] for Scala to prevent overloading when using DSL.
   *
   * Usage example:
   * {{{
   *   Date(2014, 5, 5).nextDayOfYear(3 rd Friday)
   * }}}
   */
  def nextDayOfYear(doy: DayOfYear) = next(doy)

  /**
   * <b>Java Friendly.</b> It is recommended to use [[nextDayOfYear]] for Scala.
   *
   * coming day-of-year excluding this date
   */
  def next(doy: DayOfYear) = YearOps.nextOrSame(this + 1, doy)

  /**
   * shorthand of next(LastDayOfYear). For example:
   * {{{
   * Date(2014, 8, 2).comingYearEnd => Date(2014, 12, 31)
   * Date(2014, 12, 31).comingYearEnd => Date(2015, 12, 31)
   * }}}
   * Note this is different from lastDayOfNextYear
   */
  lazy val nextLastDayOfYear = next(LastDayOfYear)

  /**
   * shorthand of next(FirstDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).comingYearBegin => Date(2015, 1, 1)<br>
   *     Date(2015, 1, 1).comingYearBegin => Date(2016, 1, 1)<br>
   */
  lazy val nextFirstDayOfYear = next(FirstDayOfYear)

  /**
   * This method is an alias of [[previousOrSame(DayOfYear)]] for Scala to prevent overloading when using DSL.
   *
   * Usage example:
   * {{{
   *   Date(2014, 5, 5).previousOrSameDayOfYear(3 rd Friday)
   * }}}
   */
  def previousOrSameDayOfYear(doy: DayOfYear) = previousOrSame(doy)

  /**
   * <b>Java Friendly.</b> It is recommended to use [[previousOrSameDayOfYear]] for Scala.
   *
   * past day-of-year including this date
   */
  def previousOrSame(doy: DayOfYear) = YearOps.previousOrSame(this, doy)

  /**
   * This method is an alias of [[previous(DayOfYear)]] for Scala to prevent overloading when using DSL.
   *
   * Usage example:
   * {{{
   *   Date(2014, 5, 5).previousDayOfYear(3 rd Friday)
   * }}}
   */
  def previousDayOfYear(doy: DayOfYear) = previous(doy)

  /**
   * <b>Java Friendly.</b> It is recommended to use [[previousDayOfYear]] for Scala.
   *
   * past day-of-year excluding this date
   */
  def previous(doy: DayOfYear) = YearOps.previousOrSame(this - 1, doy)

  /**
   * shorthand of previous(LastDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).previousLastDayOfYear => Date(2013, 12, 31) <br>
   *     Date(2013, 12, 31).previousLastDayOfYear => Date(2012, 12, 31) <br>
   */
  lazy val previousLastDayOfYear = previous(LastDayOfYear)

  /**
   * shorthand of previous(FirstDayOfYear). For example:
   * {{{
   * Date(2014, 8, 2).previousFirstDayOfYear => Date(2014, 1, 1)
   * Date(2014, 1, 1).previousFirstDayOfYear => Date(2013, 1, 1)
   * }}}
   * Note this is different from firstDayOfPreviousYear
   */
  lazy val previousFirstDayOfYear = previous(FirstDayOfYear)

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find nth occurrence of day-of-week in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).dayOfWeekInYear(3, DayOfWeek.FRIDAY); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(3 rd Friday) }}}
   * in Scala
   */
  def dayOfWeekInYear(n: Int, dow: DayOfWeek) = this.withDayOfYear(n th dow)

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find first occurrence of day-of-week in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).firstInYear(DayOfWeek.FRIDAY); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(1 st Friday) }}}
   * in Scala
   */
  def firstInYear(dow: DayOfWeek) = this.withDayOfYear(1 st dow)

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find last occurrence of day-of-week in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).lastInYear(DayOfWeek.FRIDAY); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(lastFriday) }}}
   * in Scala
   */
  def lastInYear(dow: DayOfWeek) = this.withDayOfYear(LastWeekdayOfYear(dow))

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find nth occurrence of day-of-week of specified month in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).dayOfWeekInYear(2, DayOfWeek.FRIDAY, Month.SEPTEMBER); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(2 nd Friday of September) }}}
   * in Scala
   */
  def dayOfWeekInYear(n: Int, dow: DayOfWeek, m: Month) = this.withDayOfYear(NthMonthOfYear(m, NthWeekdayOfMonth(n, dow)))

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find first occurrence of day-of-week of specified month in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).firstInYear(DayOfWeek.FRIDAY, Month.SEPTEMBER); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(1 st Friday of September) }}}
   * in Scala
   */
  def firstInYear(dow: DayOfWeek, m: Month) = this.dayOfWeekInYear(1, dow, m)

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find last occurrence of day-of-week of specified month in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).lastInYear(DayOfWeek.FRIDAY, Month.SEPTEMBER); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(lastFriday of September) }}}
   * in Scala
   */
  def lastInYear(dow: DayOfWeek, m: Month) = this.withDayOfYear(NthMonthOfYear(m, LastWeekdayOfMonth(dow)))

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find nth day of specified month in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).dayOfMonthInYear(15, Month.SEPTEMBER); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(15 th day of September) }}}
   * in Scala
   */
  def dayOfMonthInYear(n: Int, m: Month) = this.withDayOfYear(NthMonthOfYear(m, NthDayOfMonth(n)))

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find first day of specified month in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).firstInYear(Month.SEPTEMBER); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(1 st day of September) }}}
   * in Scala
   */
  def firstInYear(m: Month) = this.dayOfMonthInYear(1, m)

  /**
   * <b>Java Friendly.</b> For Scala, it is recommended to use DSL with [[withDayOfYear]] directly.
   *
   * Find last day of specified month in current year.
   *
   * For example:
   * {{{ new Date(2014, 5, 5).lastInYear(Month.SEPTEMBER); }}}
   *
   * which is identical to
   * {{{ Date(2014, 5, 5).withDayOfYear(lastDay of September) }}}
   * in Scala
   */
  def lastInYear(m: Month) = this.withDayOfYear(NthMonthOfYear(m, LastDayOfMonth))
}

private object YearOps {

  @tailrec
  private def nextOrSame(d: Date, doy: DayOfYear): Date = {
    if (doy.isValidDOY(d)) {
      d
    } else {
      nextOrSame(d + 1, doy)
    }
  }

  @tailrec
  private def previousOrSame(d: Date, doy: DayOfYear): Date = {
    if (doy.isValidDOY(d)) {
      d
    } else {
      previousOrSame(d - 1, doy)
    }
  }
}