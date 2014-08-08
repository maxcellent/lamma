package io.lamma.partial.date

import io.lamma._

import scala.annotation.tailrec

private[lamma] trait WeekOps {
  this: Date =>

  /**
   * an iterable for every day in this week <br>
   *   (week starts on Monday and ends on Sunday according to ISO 8601: http://en.wikipedia.org/wiki/ISO_week_date)
   */
  lazy val daysOfWeek = previousOrSame(Monday) to nextOrSame(Sunday)

  /**
   * <b>Java Friendly.</b> It is recommended to use [[daysOfWeek]] for Scala.
   *
   * an iterable for every day in this week <br>
   *   (week starts on Monday and ends on Sunday according to ISO 8601: http://en.wikipedia.org/wiki/ISO_week_date)
   */
  lazy val daysOfWeek4j = daysOfWeek.javaIterable

  lazy val dayOfWeek = JavaDateUtil.dayOfWeek(this)

  /**
   * find the day of this week matching specified day-of-week <br>
   *
   * A week starts with Monday according to ISO8601 http://en.wikipedia.org/wiki/ISO_week_date
   *
   */
  def withDayOfWeek(dow: DayOfWeek) = daysOfWeek.find(_.is(dow)).get

  def is(dow: DayOfWeek) = this.dayOfWeek == dow

  def isWeekend = is(Saturday) || is(Sunday)

  /**
   * return the first occurrence of the specified day-of-week after current date, unless current date is already on that day.
   *   <br>
   *   For example: <br>
   *     {{{
   *     Date(2014-07-05).nextOrSame(Monday) => Date(2014-07-07)
   *     Date(2014-07-05).nextOrSame(Saturday) => Date(2014-07-5) // note 2014-07-05 itself is already Saturday
   *     }}}
   *    <br>
   */
  def nextOrSame(dow: DayOfWeek) = WeekOps.nextOrSame(this, dow)

  /**
   * return the first occurrence of the specified day-of-week after current date: <br>
   *   <br>
   *   For example: <br>
   *   Date(2014-07-05).next(Monday) => Date(2014-07-07) <br>
   *   Date(2014-07-05).next(Saturday) => Date(2014-07-12) // note 2014-07-05 itself is already Saturday <br>
   */
  def next(dow: DayOfWeek) = WeekOps.nextOrSame(this + 1, dow)

  /**
   * previous day of week before current date, unless current date is already on specified day-of-week <br>
   *
   * For example: <br>
   * {{{
   *  Date(2014-07-05).previousOrSame(Monday) => Date(2014-06-30)
   *  Date(2014-07-05).previousOrSame(Saturday) => Date(2014-07-05) // note 2014-07-05 itself is already Saturday
   * }}}
   */
  def previousOrSame(dow: DayOfWeek) = WeekOps.previousOrSame(this, dow)

  /**
   * previous day-of-week excluding this date. For example:
   * {{{
   *   Date(2014-07-05).previous(Monday) => Date(2014-06-30) <br>
   *   Date(2014-07-05).previous(Saturday) => Date(2014-06-28) // note 2014-07-05 itself is already Saturday <br>
   * }}}
   */
  def previous(dow: DayOfWeek) = WeekOps.previousOrSame(this - 1, dow)
}

private object WeekOps {

  @tailrec
  private def nextOrSame(d: Date, target: DayOfWeek): Date = {
    if (d.dayOfWeek == target) {
      d
    } else {
      nextOrSame(d + 1, target)
    }
  }

  @tailrec
  private def previousOrSame(d: Date, target: DayOfWeek): Date = {
    if (d.dayOfWeek == target) {
      d
    } else {
      previousOrSame(d - 1, target)
    }
  }
}