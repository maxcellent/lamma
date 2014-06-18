package io.lamma

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
 *  @param shifters
 *  @param selectors
 *
 */
case class DateRange(from: Date,
                     to: Date,
                     pattern: Pattern,
                     holiday: HolidayRule = NoHoliday,
                     shifters: List[Shifter] = Nil,
                     selectors: List[Selector] = Nil) extends Traversable[Date] {

  lazy val generated = pattern.recur(from, to)

  lazy val filtered = generated.filterNot(holiday.isHoliday)

  lazy val shifted = filtered.map { d => (d /: shifters) {_ shift _} }

  lazy val selected = shifted.map { d => (d /: selectors) {_ select _} }

  override def foreach[U](f: Date => U) = selected.foreach(f)
}
