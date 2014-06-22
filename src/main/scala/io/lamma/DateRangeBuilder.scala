package io.lamma

import io.lamma.Selector._

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
 * @param selector
 */
case class DateRangeBuilder(from: Date,
                            to: Date,
                            step: Duration = 1 day,
                            holiday: HolidayRule = NoHoliday,
                            loc: Option[Locator] = None,
                            shifters: List[Shifter] = Nil,
                            selector: Selector = SameDay,
                            customDom: Option[DayOfMonth] = None,
                            customDoy: Option[DayOfYear] = None) extends Traversable[Date] {
  require(step.n != 0, "step cannot be 0.")

  lazy val dateRange = DateRange(from, to, pattern, holiday, shifters, selector)

  override def foreach[U](f: Date => U) = dateRange.foreach(f)

  def by(d: day.type): DateRangeBuilder = by(1)

  def by(step: Int): DateRangeBuilder = by(step days)

  def by(step: Duration) = this.copy(step = step)

  def except(holiday: HolidayRule) = this.copy(holiday = this.holiday and holiday)

  def on(dow: DayOfWeek): DateRangeBuilder = on(Locator(dow))

  def on(loc: Locator) = {
    step match {
      case DayDuration(n) => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} days")
      case WeekDuration(n) if !loc.isInstanceOf[DayOfWeekSupport] => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} weeks")
      case MonthDuration(n) if !loc.isInstanceOf[DayOfMonthSupport] => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} months")
      case YearDuration(n) if !loc.isInstanceOf[DayOfYearSupport] => throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} years")
      case _ => this.copy(loc = Some(loc))
    }
  }

  def on(dom: DayOfMonth) = this.copy(customDom = Some(dom))

  def on(doy: DayOfYear) = this.copy(customDoy = Some(doy))

  lazy val pattern: Pattern = (step, loc, customDom, customDoy) match {
    case (DayDuration(n), _, _, _) => Daily(n)
    case (WeekDuration(n), Some(s: DayOfWeekSupport), _, _) => Weekly(n, s.dow)
    case (WeekDuration(n), _, _, _) => Weekly(n)
    case (MonthDuration(n), Some(s: DayOfMonthSupport), _, _) => Monthly(n, s.dom)
    case (MonthDuration(n), _, Some(dom), _) => Monthly(n, Some(dom))
    case (MonthDuration(n), _, _, _) => Monthly(n)
    case (YearDuration(n), Some(s: DayOfYearSupport), _, _) => Yearly(n, s.doy)
    case (YearDuration(n), _, _, Some(doy)) => Yearly(n, doy)
    case (YearDuration(n), _, _, _) => Yearly(n)
  }

  /**
   * return an instance of {{{ java.lang.Iterable }}}
   * can be used in java for comprehension
   */
  lazy val javaIterable = this.toIterable.asJava

  lazy val javaList = this.toList.asJava

  def shift(d: Int): DateRangeBuilder = shift(Shifter(d))

  def shift(d: Int, holiday: HolidayRule): DateRangeBuilder = shift(Shifter(d, holiday))

  def shift(shifter: Shifter) = this.copy(shifters = shifters :+ shifter)

  def forward(holiday: HolidayRule) = select(Forward(holiday))

  def backward(holiday: HolidayRule) = select(Backward(holiday))

  def modifiedFollowing(holiday: HolidayRule) = select(ModifiedFollowing(holiday))

  def modifiedPreceding(holiday: HolidayRule) = select(ModifiedPreceding(holiday))

  def select(selector: Selector) = this.copy(selector = selector)
}
