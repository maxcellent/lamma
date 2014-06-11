package io.lamma

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
 *
 */
case class DateRange(from: Date, to: Date, step: Int = 1) extends Traversable[Date] {
  require(step != 0, "step cannot be 0.")

  override def foreach[U](f: Date => U) = DateRange.eachDate(f, from, to, step)

  def by(step: Int) = DateRange(from, to, step)

  /**
   * return an instance of java.lang.Iterable can be used in java for comprehension
   */
  lazy val javaIterable = this.toIterable.asJava
}

object DateRange {
  @tailrec
  private def eachDate[U](f: Date => U, current: Date, to: Date, step: Int): Unit = {
    if ((step > 0 && current <= to) || (step < 0 && current >= to)) {
      f(current)
      eachDate(f, current + step, to, step)
    }
  }
}