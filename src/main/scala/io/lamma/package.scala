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

}