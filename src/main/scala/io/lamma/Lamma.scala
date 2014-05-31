package io.lamma

import io.lamma.Shifter.NoShift
import io.lamma.Selector.SameDay
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
   * generate a full schedule based on input arguments <br>
   * <br>
   * For example: <br>
   *
   * <pre>
   * ||    Period | CouponDate | SettlementDate || <br>
   * ||         1 | 2014-03-30 |     2014-05-02 || <br>
   * ||         2 | 2014-06-02 |     2014-06-04 || <br>
   * </pre>
   *
   * @param start start date
   * @param end end date
   * @param pattern recurrence pattern. Determine how anchor dates will be generated
   * @param startRule start stub rule, how do we handle fraction period on start? (if any)
   * @param endRule end stub rule, how do we handle fraction period at the end? (if any)
   * @param dateDefs a list of date definitions used to generate Date. In this case List(CouponDate, SettlementDate)
   * @return generated schedule
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
   * Generate a list of dates from the input criteria
   *
   * @param start start date
   * @param end end date
   * @param pattern recurrence pattern. Determine how next date will be generated from the previous one
   * @param shifter how to shift the date once it's generated? eg, no shift, 2 days later, 2 working days later
   * @param selector How to select the date once the date is generated? eg, same day, following business date?
   * @param cal calendar used to shift and select date
   * @return a list of generated dates
   *
   * @since 1.0
   */
  def sequence(start: Date,
               end: Date,
               pattern: Recurrence = EveryDay,
               shifter: Shifter = NoShift,
               selector: Selector = SameDay,
               cal: Calendar = NoHoliday) = {
    pattern.gen(start, end).map(shifter.shift).map(selector.select)
  }
}
