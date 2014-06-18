package io.lamma

import io.lamma.Selector.{ModifiedPreceding, ModifiedFollowing, Backward, Forward}
import io.lamma.Shifter.{ShiftWorkingDays, ShiftCalendarDays}

import collection.JavaConverters._

/**
 * builder class to manipulate DateRange
 *
 * @param from from date
 * @param to to date
 * @param step
 * @param holiday
 * @param loc
 * @param shifters
 * @param selectors
 */
case class DateRangeBuilder(from: Date,
                            to: Date,
                            step: Duration = 1 day,
                            holiday: HolidayRule = NoHoliday,
                            loc: Option[Locator] = None,
                            shifters: List[Shifter] = Nil,
                            selectors: List[Selector] = Nil) extends Traversable[Date] {
  require(step.n != 0, "step cannot be 0.")

  lazy val dateRange = DateRange(from, to, pattern, holiday, shifters, selectors)

  override def foreach[U](f: Date => U) = dateRange.foreach(f)

  def by(step: Int): DateRangeBuilder = by(step days)

  def by(step: Duration) = this.copy(step = step)

  def except(holiday: HolidayRule) = this.copy(holiday = this.holiday and holiday)

  def on(dow: DayOfWeek): DateRangeBuilder = on(Locator(dow.ordinal))

  def on(loc: Locator) = {
    step match {
      case DayDuration(n) => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} days")
      case WeekDuration(n) if !loc.isInstanceOf[DayOfWeekSupport] => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} weeks")
      case MonthDuration(n) if !loc.isInstanceOf[DayOfMonthSupport] => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} months")
      case YearDuration(n) if !loc.isInstanceOf[DayOfYearSupport] => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} years")
      case _ => this.copy(loc = Some(loc))
    }
  }

  lazy val pattern: Pattern = (step, loc) match {
    case (DayDuration(n), _) => Daily(n)
    case (WeekDuration(n), Some(s: DayOfWeekSupport)) => Weekly(n, s.dow)
    case (WeekDuration(n), _) => Weekly(n)
    case (MonthDuration(n), Some(s: DayOfMonthSupport)) => Monthly(n, s.dom)
    case (MonthDuration(n), _) => Monthly(n)
    case (YearDuration(n), Some(s: DayOfYearSupport)) => Yearly(n, s.doy)
    case (YearDuration(n), _) => Yearly(n)
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
