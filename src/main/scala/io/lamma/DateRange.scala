package io.lamma

import io.lamma.Selector.SameDay

import scala.annotation.meta.beanGetter
import scala.collection.JavaConverters._
import scala.collection.immutable.IndexedSeq

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
 *  @param pattern   pattern used to generate date range
 *  @param holiday   except which holidays
 *  @param shifters  how to shift the date once it's generated? eg, no shift, 2 days later, 2 working days later
 *  @param selector  How to select the date once the date is generated? eg, same day, following working day?
 *
 */
class DateRange(from: Date,
                to: Date,
                pattern: Pattern,
                holiday: HolidayRule,
                shifters: List[Shifter],
                selector: Selector) extends IndexedSeq[Date] {

  private[lamma] lazy val generated = pattern.recur(from, to)

  private[lamma] lazy val filtered = generated.filterNot(holiday.isHoliday)

  private[lamma] lazy val shifted = filtered.map {
    d => (d /: shifters) {
      _ shift _
    }
  }

  private[lamma] lazy val selected = shifted.map {
    selector.select
  }

  override def foreach[U](f: Date => U) = selected.foreach(f)

  /**
   * return an instance of {{{ java.lang.Iterable }}}
   * can be used in java for comprehension
   */
  @beanGetter
  lazy val javaIterable = this.toIterable.asJava

  @beanGetter
  lazy val javaList = this.toList.asJava

  override def length = generated.size

  def apply(idx: Int) = generated(idx)
}

object DateRange {
  def apply(from: Date,
            to: Date,
            pattern: Pattern = Daily(1),
            holiday: HolidayRule = NoHoliday,
            shifters: List[Shifter] = Nil,
            selector: Selector = SameDay) = new DateRange(from, to, pattern, holiday, shifters, selector)
}