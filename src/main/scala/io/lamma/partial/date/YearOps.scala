package io.lamma.partial.date

import io.lamma.DayOfMonth.{LastWeekdayOfMonth, NthWeekdayOfMonth, LastDayOfMonth, NthDayOfMonth}
import io.lamma.DayOfYear._
import io.lamma._

import collection.JavaConverters._
import scala.annotation.tailrec                  

private[lamma] trait YearOps {
  this: Date =>

  /**
   * max day of this year, leap year is considered
   */
  lazy val maxDayOfYear = JavaDateUtil.maxDayOfYear(this)

  lazy val dayOfYear = JavaDateUtil.dayOfYear(this)

  lazy val isLastDayOfYear = dayOfYear == maxDayOfYear

  @deprecated("replaced by dayOfYear(DayOfYear)", "2.1.0")
  def thisDayOfYear(doy: DayOfYear) = withDayOfYear(doy)

  @deprecated("replaced by dayOfYear(DayOfYear)", "2.1.0")
  def dayOfThisYear(doy: DayOfYear) = withDayOfYear(doy)

  /**
   * find the day of this year matching input DayOfYear <br>
   * an IllegalArgumentException will be thrown if there is no or more than one dates.
   */
  def withDayOfYear(doy: DayOfYear) = {
    val matched = daysOfYear.filter(doy.isValidDOY)
    require(matched.size == 1, s"Invalid DayOfYear: $doy. Matched dates: $matched")
    matched.head
  }

  def withDayOfYear(n: Int): Date = withDayOfYear(NthDayOfYear(n))

  @deprecated("replace with firstDayOfYear", "2.1.0")
  def thisYearBegin = firstDayOfYear

  /**
   * first day of the year
   */
  lazy val firstDayOfYear = Date(yyyy, 1, 1)

  lazy val firstDayOfNextYear = Date(yyyy + 1, 1, 1)

  lazy val firstDayOfPreviousYear = Date(yyyy - 1, 1, 1)

  @deprecated("replace with lastDayOfYear", "2.1.0")
  def thisYearEnd = lastDayOfYear

  /**
   * last day of the year
   */
  lazy val lastDayOfYear = Date(yyyy, 12, 31)

  lazy val lastDayOfNextYear = Date(yyyy + 1, 12, 31)

  lazy val lastDayOfPreviousYear = Date(yyyy - 1, 12, 31)

  @deprecated("replace with daysOfYear", "2.1.0")
  def thisYear = daysOfYear

  /**
   * an iterable for every day in the year
   */
  lazy val daysOfYear = firstDayOfYear to lastDayOfYear

  /**
   * Every day in the same year with same dow <br>
   * eg, if this.dayOfWeek == Wednesday, then this is a list of all Wednesday in the same year
   */
  lazy val sameWeekdaysOfYear = daysOfYear.filter(_.dayOfWeek == dayOfWeek).toList

  lazy val sameWeekdaysOfYear4j = sameWeekdaysOfYear.asJava

  def nextOrSame(doy: DayOfYear) = YearOps.nextOrSame(this, doy)

  @deprecated("replaced with next(DayOfYear)", "2.1.0")
  def comingDayOfYear(doy: DayOfYear) = next(doy)
  
  /**
   * coming day of year excluding this date
   */
  def next(doy: DayOfYear) = YearOps.nextOrSame(this + 1, doy)

  @deprecated("replaced by nextLastDayOfYear", "2.1.0")
  def comingYearEnd = next(LastDayOfYear)

  /**
   * shorthand of next(LastDayOfYear). For example:
   * {{{
   * Date(2014, 8, 2).comingYearEnd => Date(2014, 12, 31)
   * Date(2014, 12, 31).comingYearEnd => Date(2015, 12, 31)
   * }}}
   * Note this is different from lastDayOfNextYear
   */
  lazy val nextLastDayOfYear = next(LastDayOfYear)

  @deprecated("replaced by nextFirstDayOfYear", "2.1.0")
  def comingYearBegin = nextFirstDayOfYear

  /**
   * shorthand of next(FirstDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).comingYearBegin => Date(2015, 1, 1)<br>
   *     Date(2015, 1, 1).comingYearBegin => Date(2016, 1, 1)<br>
   */
  lazy val nextFirstDayOfYear = next(FirstDayOfYear)

  def previousOrSame(doy: DayOfYear) = YearOps.previousOrSame(this, doy)

  @deprecated("replace with previous(DayOfYear)")
  def pastDayOfYear(doy: DayOfYear) = previous(doy)

  /**
   * previous day of year before this date
   */
  def previous(doy: DayOfYear) = YearOps.previousOrSame(this - 1, doy)

  @deprecated("replaced by previousLastDayOfYear", "2.1.0")
  def pastYearEnd = previousLastDayOfYear

  /**
   * shorthand of previous(LastDayOfYear)<br>
   *   For example: <br>
   *     Date(2014, 8, 2).previousLastDayOfYear => Date(2013, 12, 31) <br>
   *     Date(2013, 12, 31).previousLastDayOfYear => Date(2012, 12, 31) <br>
   */
  lazy val previousLastDayOfYear = previous(LastDayOfYear)

  @deprecated("replaced by previousFirstDayOfYear", "2.1.0")
  def pastYearBegin = previousFirstDayOfYear

  /**
   * shorthand of previous(FirstDayOfYear). For example:
   * {{{
   * Date(2014, 8, 2).previousFirstDayOfYear => Date(2014, 1, 1)
   * Date(2014, 1, 1).previousFirstDayOfYear => Date(2013, 1, 1)
   * }}}
   * Note this is different from firstDayOfPreviousYear
   */
  lazy val previousFirstDayOfYear = previous(FirstDayOfYear)

  def dayOfWeekInYear(n: Int, dow: DayOfWeek) = this.withDayOfYear(NthWeekdayOfYear(n, dow))

  def firstInYear(dow: DayOfWeek) = this.dayOfWeekInYear(1, dow)

  def lastInYear(dow: DayOfWeek) = this.withDayOfYear(LastWeekdayOfYear(dow))

  def dayOfWeekInYear(n: Int, dow: DayOfWeek, m: Month) = this.withDayOfYear(NthMonthOfYear(m, NthWeekdayOfMonth(n, dow)))

  def firstInYear(dow: DayOfWeek, m: Month) = this.dayOfWeekInYear(1, dow, m)

  def lastInYear(dow: DayOfWeek, m: Month) = this.withDayOfYear(NthMonthOfYear(m, LastWeekdayOfMonth(dow)))

  def dayOfMonthInYear(n: Int, m: Month) = this.withDayOfYear(NthMonthOfYear(m, NthDayOfMonth(n)))

  def firstInYear(m: Month) = this.dayOfMonthInYear(1, m)

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