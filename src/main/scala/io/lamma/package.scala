package io

import language.postfixOps
import io.lamma.Duration.{YearDuration, MonthDuration, WeekDuration, DayDuration}
import io.lamma.Locator.Last

/**
 * <b>Lamma schedule generator</b> is a professional financial schedule generation library. <br>
 * <br>
 * Some use cases are:
 * <ol>
 *   <li>mortgage repayment schedule generation</li>
 *   <li>fixed income coupon payment schedule generation</li>
 *   <li>equity derivative fixing date generation</li>
 *   <li>..etc..</li>
 * </ol>
 *
 * <br>
 * The starting point of the libary is the {@link com.lamma.Lamma} class
 *
 * @see <a href="http://www.lamma.io" target="_blank">http://www.lamma.io</a> for samples and tutorials
 */
package object lamma {

  // =========== durations ===============
  val week = 1 week

  val month = 1 month

  val year = 1 year

  implicit class DurationInt(n: Int) {

    def day = DayDuration(n)

    def days = DayDuration(n)

    def week = WeekDuration(n)

    def weeks = WeekDuration(n)

    def month = MonthDuration(n)

    def months = MonthDuration(n)

    def year = YearDuration(n)

    def years = YearDuration(n)
  }

  // =========== holiday rules ==================
  val weekends = new HolidayRule {
    override def isHoliday(d: Date) = d.isWeekend
  }

  // ======== ordinal =================

  // this is only used as a place holder to complete expression like `10 th day`
  object day

  implicit class LocatorImplicit(n: Int) {
    def st(d: day.type) = th(d)

    def nd(d: day.type) = th(d)

    def rd(d: day.type) = th(d)

    def th(d: day.type) = Locator(n)

    def st(dow: DayOfWeek) = th(dow)

    def nd(dow: DayOfWeek) = th(dow)

    def rd(dow: DayOfWeek) = th(dow)

    def th(dow: DayOfWeek) = Locator(n, dow)
  }


  // because of the way scala works
  // we cannot make this like: last day / last Friday
  // http://stackoverflow.com/questions/20163450/scala-does-not-take-parameters-when-chaining-method-calls-without-periods

  val lastDay = Locator(Last)

  val lastMonday = Locator(Last, Some(Monday))

  val lastTuesday = Locator(Last, Some(Tuesday))

  val lastWednesday = Locator(Last, Some(Wednesday))

  val lastThursday = Locator(Last, Some(Thursday))

  val lastFriday = Locator(Last, Some(Friday))

  val lastSaturday = Locator(Last, Some(Saturday))

  val lastSunday = Locator(Last, Some(Sunday))
}