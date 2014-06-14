package io.lamma

import io.lamma.Shifter.NoShift
import io.lamma.Selector.SameDay
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
   * @param periodBuilder how do we build periods (rows) based on the result of recurrence pattern
   * @param dateDefs a list of date definitions used to generate Date. In this case List(CouponDate, SettlementDate)
   * @return generated schedule
   *
   * @since 1.0
   */
  def schedule(start: Date,
               end: Date,
               pattern: Recurrence,
               periodBuilder: PeriodBuilder = StubRulePeriodBuilder(),
               dateDefs: List[DateDef] = Nil) = {
    require(start <= end, s"start date $start must be on or before end date $end")

    DateDef.validate(dateDefs)

    val periodEndDays = pattern.periodEndDays(start, end)
    val periods = periodBuilder.build(start, end, periodEndDays)
    Schedule(periods, dateDefs)
  }

  /**
   * Generate a list of dates from the input arguments
   *
   * @param from from date
   * @param to to date
   * @param pattern recurrence pattern. Determine how next date will be generated from the previous one
   * @param shifter how to shift the date once it's generated? eg, no shift, 2 days later, 2 working days later
   * @param selector How to select the date once the date is generated? eg, same day, following working day?
   * @param holiday holiday rule used to shift and select date
   * @return a list of generated dates
   *
   * @since 1.0
   */
  def sequence(from: Date,
               to: Date,
               pattern: Recurrence = EveryDay,
               shifter: Shifter = NoShift,
               selector: Selector = SameDay,
               holiday: HolidayRule = NoHoliday) = {
    require(from <= to, s"from date $from must be on or before to date $to")

    pattern.recur(from, to).map(shifter.shift).map(selector.select)
  }
}
