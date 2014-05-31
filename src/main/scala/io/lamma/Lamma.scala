package io.lamma

import io.lamma.Shifter.{NoShift, shift}
import io.lamma.Selector.{SameDay, select}
import io.lamma.StartRule.NoStartRule
import io.lamma.EndRule.NoEndRule
import io.lamma.Recurrence.EveryDay

/**
 * Entry point of the library
 *
 * @since 1.0
 */
object Lamma {

  /**
   *
   * @param start
   * @param end
   * @param pattern
   * @param startRule
   * @param endRule
   * @param dateDefs
   * @return
   *
   * @since 1.0
   */
  def schedule(start: Date,
               end: Date,
               pattern: Recurrence,
               startRule: StartRule = NoStartRule,
               endRule: EndRule = NoEndRule,
               dateDefs: List[DateDef]) = {
    DateDef.validate(dateDefs)

    val dates = pattern.endDays(start, end)

    val periods = {
      val p = Period.fromDates(dates)
      val withStartRule = startRule.applyRule(start, p)
      endRule.applyRule(end, withStartRule)
    }

    Schedule(periods, dateDefs)
  }

  /**
   *
   * @param start
   * @param end
   * @param pattern
   * @param selector
   * @param shifter
   * @param cal
   * @return
   *
   * @since 1.0
   */
  def sequence(start: Date,
               end: Date,
               pattern: Recurrence = EveryDay,
               selector: Selector = SameDay,
               shifter: Shifter = NoShift,
               cal: HolidayCalendar = NoHolidayCalendar) = {
    pattern.gen(start, end).map(shift(_, shifter, cal)).map(select(_, selector, cal))
  }
}
