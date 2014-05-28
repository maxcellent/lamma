package com.lamma4s

import com.lamma4s.Weekday.{Sunday, Saturday}

trait HolidayCalendar {

  def isHoliday(d: Date): Boolean

}

case class SimpleHolidayCalendar(holidays: Set[Date]) extends HolidayCalendar {
  override def isHoliday(d: Date) = holidays.contains(d)
}

/**
 * consider all weekend as holiday
 */
case object WeekendHolidayCalendar extends HolidayCalendar {
  override def isHoliday(d: Date) = {
    d.weekday == Saturday || d.weekday == Sunday
  }
}

/**
 * only consider Sunday as holiday
 */
case object SundayHolidayCalendar extends HolidayCalendar {
  override def isHoliday(d: Date) = d.weekday == Sunday
}

