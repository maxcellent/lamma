package io.lamma

import io.lamma.Selector._

import scala.collection.JavaConverters._
import scala.collection.immutable.IndexedSeq

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
class DateRangeBuilder(from: Date,
                       to: Date,
                       step: Duration,
                       holiday: HolidayRule,
                       loc: Option[Locator],
                       shifters: List[Shifter],
                       selector: Selector,
                       customDom: Option[DayOfMonth],
                       customDoy: Option[DayOfYear]) extends IndexedSeq[Date] {
  require(step.n != 0, "step cannot be 0.")

  lazy val dateRange = DateRange(from, to, pattern, holiday, shifters, selector)

  override def foreach[U](f: Date => U) = {
    def iae(msg: String) = throw new IllegalArgumentException(s"$loc is not applicable to step by ${step.n} days")

    // validate when loc is defined
    loc.foreach {
      l =>
        step match {
          case DayDuration(n) => iae(s"$loc is not applicable to step by ${step.n} days")
          case WeekDuration(n) if !l.isInstanceOf[DayOfWeekSupport] => iae(s"$loc is not applicable to step by ${step.n} weeks")
          case MonthDuration(n) if !l.isInstanceOf[DayOfMonthSupport] => iae(s"$loc is not applicable to step by ${step.n} months")
          case YearDuration(n) if !l.isInstanceOf[DayOfYearSupport] => iae(s"$loc is not applicable to step by ${step.n} years")
          case _ => // pass
        }
    }

    dateRange.foreach(f)
  }

  def by(d: day.type): DateRangeBuilder = by(1)

  def by(step: Int): DateRangeBuilder = by(step days)

  def by(step: Duration) = DateRangeBuilder(from, to, step, holiday, loc, shifters, selector, customDom, customDoy)

  def except(holiday: HolidayRule) = DateRangeBuilder(from, to, step, this.holiday and holiday, loc, shifters, selector, customDom, customDoy)

  def on(dow: DayOfWeek): DateRangeBuilder = on(Locator(dow))

  def on(loc: Locator) = DateRangeBuilder(from, to, step, holiday, Some(loc), shifters, selector, customDom, customDoy)

  def on(dom: DayOfMonth) = DateRangeBuilder(from, to, step, holiday, loc, shifters, selector, Some(dom), customDoy)

  def on(doy: DayOfYear) = DateRangeBuilder(from, to, step, holiday, loc, shifters, selector, customDom, Some(doy))

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

  def shift(shifter: Shifter) = DateRangeBuilder(from, to, step, holiday, loc, shifters :+ shifter, selector, customDom, customDoy)

  def forward(holiday: HolidayRule) = select(Forward(holiday))

  def backward(holiday: HolidayRule) = select(Backward(holiday))

  def modifiedFollowing(holiday: HolidayRule) = select(ModifiedFollowing(holiday))

  def modifiedPreceding(holiday: HolidayRule) = select(ModifiedPreceding(holiday))

  def select(selector: Selector) = DateRangeBuilder(from, to, step, holiday, loc, shifters, selector, customDom, customDoy)

  /**
   * override toString method so to make builder transparent
   */
  override def toString = dateRange.toString

  override def length = dateRange.length

  def apply(idx: Int) = dateRange(idx)
}


object DateRangeBuilder {
  def apply(from: Date,
            to: Date,
            step: Duration = 1 day,
            holiday: HolidayRule = NoHoliday,
            loc: Option[Locator] = None,
            shifters: List[Shifter] = Nil,
            selector: Selector = SameDay,
            customDom: Option[DayOfMonth] = None,
            customDoy: Option[DayOfYear] = None) =
    new DateRangeBuilder(from, to, step, holiday, loc, shifters, selector, customDom, customDoy)
}