package com.lamma4s

import com.lamma4s.Weekday.{Sunday, Saturday}

trait HolidayCalendar {

  def isHoliday(d: Date): Boolean

  def shiftBizDay(d: Date, by: Int) = {
    require(by != 0)
    HolidayCalendar.shiftBizDay(d, by, this)
  }

  def forward(d: Date) = if (isHoliday(d)) shiftBizDay(d, 1) else d

  def backward(d: Date) = if (isHoliday(d)) shiftBizDay(d, -1) else d

  def modifiedFollowing(d: Date) = {
    val forwardDt = forward(d)
    if (d.mm == forwardDt.mm) {
      forwardDt
    } else {
      backward(d)
    }
  }

  def modifiedPreceding(d: Date) = {
    val precedingDt = backward(d)
    if (d.mm == precedingDt.mm) {
      precedingDt
    } else {
      forward(d)
    }
  }
}

object HolidayCalendar {
  def shiftBizDay(d: Date, by: Int, cal: HolidayCalendar): Date = by match {
    case 0 => d
    case by if by < 0 =>
      val nextDay = d - 1
      val isHoliday = cal.isHoliday(nextDay)
      val nextBy = if (isHoliday) by else by + 1
      shiftBizDay(nextDay, nextBy, cal)
    case by if by > 0 =>
      val nextDay = d + 1
      val isHoliday = cal.isHoliday(nextDay)
      val nextBy = if (isHoliday) by else by - 1
      shiftBizDay(nextDay, nextBy, cal)
  }
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

