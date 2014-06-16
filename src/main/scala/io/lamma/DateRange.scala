package io.lamma

import io.lamma.Selector.{ModifiedPreceding, ModifiedFollowing, Backward, Forward}
import io.lamma.Shifter.{ShiftWorkingDays, ShiftCalendarDays}

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
 *  @param holiday   except which holidays
 *
 */
case class DateRange(from: Date,
                     to: Date,
                     step: Duration = 1 day,
                     holiday: HolidayRule = NoHoliday,
                     loc: Option[Locator] = None,
                     shifters: List[Shifter] = Nil,
                     selectors: List[Selector] = Nil) extends Traversable[Date] {
  require(step.n != 0, "step cannot be 0.")

  lazy val generated = pattern.recur(from, to)

  lazy val filtered = generated.filterNot(holiday.isHoliday)

  lazy val shifted = filtered.map { d => (d /: shifters) {_ shift _} }

  lazy val selected = shifted.map { d => (d /: selectors) {_ select _} }

  override def foreach[U](f: Date => U) = selected.foreach(f)

  def by(step: Int): DateRange = by(step days)

  def by(step: Duration) = this.copy(step = step)

  def except(holiday: HolidayRule) = this.copy(holiday = this.holiday and holiday)

  def on(dow: DayOfWeek): DateRange = on(Locator(dow.ordinal))

  // TODO: test
  def on(loc: Locator) = {
    // TODO: validation, make sure the locator is compatible with the step
    this.copy(loc = Some(loc))
  }

  lazy val pattern: Pattern = (step, loc) match {
    case (DayDuration(step), _) => Daily(step)
    case (WeekDuration(step), Some(s: DayOfWeekSupport)) => Weekly(step, s.dow)
    case (WeekDuration(step), _) => Weekly(step)
    case (MonthDuration(step), Some(s: DayOfMonthSupport)) => Monthly(step, s.dom)
    case (MonthDuration(step), _) => Monthly(step)
    case (YearDuration(step), Some(s: DayOfYearSupport)) => Yearly(step, s.doy)
    case (YearDuration(step), _) => Yearly(step)
  }

  /**
   * return an instance of {{{ java.lang.Iterable }}}
   * can be used in java for comprehension
   */
  lazy val javaIterable = this.toIterable.asJava

  def shift(d: Int) = this.copy(shifters = shifters :+ ShiftCalendarDays(d))

  def shift(d: Int, holiday: HolidayRule) = this.copy(shifters = shifters :+ ShiftWorkingDays(d, holiday))

  def forward(holiday: HolidayRule) = this.copy(selectors = selectors :+ Forward(holiday))

  def backward(holiday: HolidayRule) = this.copy(selectors = selectors :+ Backward(holiday))

  def modifiedFollowing(holiday: HolidayRule) = this.copy(selectors = selectors :+ ModifiedFollowing(holiday))

  def modifiedPreceding(holiday: HolidayRule) = this.copy(selectors = selectors :+ ModifiedPreceding(holiday))
}
