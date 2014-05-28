package com.lamma4s

sealed trait Pattern {
  def name: String
}

case class SameDay(name: String) extends Pattern

case class FutureDays(name: String, n: Int)

case class PreviousDays(name: String, n: Int)

case class FutureBizDays(name: String, n: Int, calendar: HolidayCalendar = WeekendHolidayCalendar)

case class PreviousBizDays(name: String, n: Int, calendar: HolidayCalendar = WeekendHolidayCalendar)