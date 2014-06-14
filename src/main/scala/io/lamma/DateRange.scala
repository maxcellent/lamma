package io.lamma

import io.lamma.Duration.{YearDuration, WeekDuration, MonthDuration, DayDuration}
import io.lamma.Recurrence._

import annotation.tailrec
import collection.JavaConverters._


/**
 * The `DateRange` class represents all date values in range. Both start and end date included. <br>
 *
 * This class does not create all Date object to construct a new range. Its complexity is O(1). <br>
 *
 * For example:
 *
 *  {{{
 *     Date(2015, 7, 7) to Date(2015, 7, 10) foreach println
 *  }}}
 *
 *  output:
 *  {{{
 *    Date(2015,7,7)
 *    Date(2015,7,8)
 *    Date(2015,7,9)
 *    Date(2015,7,10)
 *  }}}
 *
 *  @param from      the start of this range.
 *  @param to        the exclusive end of the range.
 *  @param step      the step for the range, default 1
 *  @param holiday  a collection of Holiday calendars
 *
 */
case class DateRange(from: Date, to: Date, step: Duration = 1 day, holiday: HolidayRule = NoHoliday, loc: Option[Locator] = None) extends Traversable[Date] {
  require(step.n != 0, "step cannot be 0.")

  override def foreach[U](f: Date => U) = DateRange.eachDate(f, from, to, step, holiday)

  // TODO: need to refactor all recurrence patterns, REMOVE all backward patterns
//  override def foreach[U](f: Date => U) = Lamma.sequence(from, to, pattern, holiday = holiday).foreach(f)

  def by(step: Int): DateRange = by(step days)

  def by(step: Duration) = this.copy(step = step)

  def except(holiday: HolidayRule) = this.copy(holiday = this.holiday and holiday)

  // TODO: test
  def on(loc: Locator) = {
    // TODO: validation, make sure the locator is compatible with the step
    this.copy(loc = Some(loc))
  }

  // TODO: test
  /**
   * create recurrence pattern based on step and location
   */
  lazy val pattern = step match {
    case DayDuration(n) if n > 0 => Days(n)
    case DayDuration(n) if n < 0 => DaysBackward(-n)
    case WeekDuration(n) if n > 0 =>
      loc match {
        case Some(Locator(_, Some(dow), _)) => Weeks(n, dow)
        case _ => Weeks(n)
      }
    case WeekDuration(n) if n < 0 =>
      loc match {
        case Some(Locator(_, Some(dow), _)) => WeeksBackward(-n, dow)
        case _ => WeeksBackward(-n)
      }
    case MonthDuration(n) if n > 0 => Months(n, loc.map(_.pom))
    case MonthDuration(n) if n < 0 => MonthsBackward(-n, loc.map(_.pom))
    case YearDuration(n) if n > 0 => Years(n, loc.map(_.poy))
    case YearDuration(n) if n < 0 => YearsBackward(-n, loc.map(_.poy))
  }

  /**
   * return an instance of java.lang.Iterable can be used in java for comprehension
   */
  lazy val javaIterable = this.toIterable.asJava
}

object DateRange {
  @tailrec
  private def eachDate[U](f: Date => U, current: Date, to: Date, step: Duration, holiday: HolidayRule): Unit = {
    if ((step.n > 0 && current <= to) || (step.n < 0 && current >= to)) {
      if (! holiday.isHoliday(current)) {
        f(current)
      }
      eachDate(f, current + step, to, step, holiday)
    }
  }
}