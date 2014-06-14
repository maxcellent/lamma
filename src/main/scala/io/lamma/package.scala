package io

import io.lamma.Duration.{YearDuration, MonthDuration, WeekDuration, DayDuration}

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
//  val day = 1 day
//
//  val week = 1 week
//
//  val month = 1 month
//
//  val year = 1 year

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
  case class DayKeyword()
  val day = DayKeyword()

  implicit class LocatorImplicit(n: Int) {
    def st(d: DayKeyword) = th(d)

    def nd(d: DayKeyword) = th(d)

    def rd(d: DayKeyword) = th(d)

    def th(d: DayKeyword) = Locator(n)

    def st(dow: DayOfWeek) = th(dow)

    def nd(dow: DayOfWeek) = th(dow)

    def rd(dow: DayOfWeek) = th(dow)

    def th(dow: DayOfWeek) = Locator(n, dow)
  }

  // this is ONLY used as a place holder to complete expression like 'last day / last Friday'
//  case class LastKeyword() {
//    def day = Locator(Last)
//
//    def apply(dow: DayOfWeek) = Locator(Last, Some(dow))
//  }
//  val last = LastKeyword()

  val last = Int.MaxValue th day
}