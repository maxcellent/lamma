package io.lamma

import io.lamma.Weekday.{Sunday, Saturday}

trait Calendar {

  def isHoliday(d: Date): Boolean

  def shiftWorkingDay(d: Date, by: Int) = {
    require(by != 0)
    Calendar.shiftWorkingDay(d, by, this)
  }

  def forward(d: Date) = if (isHoliday(d)) shiftWorkingDay(d, 1) else d

  def backward(d: Date) = if (isHoliday(d)) shiftWorkingDay(d, -1) else d

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

object Calendar {
  def shiftWorkingDay(d: Date, by: Int, cal: Calendar): Date = by match {
    case 0 => d
    case by if by < 0 =>
      val nextDay = d - 1
      val isHoliday = cal.isHoliday(nextDay)
      val nextBy = if (isHoliday) by else by + 1
      shiftWorkingDay(nextDay, nextBy, cal)
    case by if by > 0 =>
      val nextDay = d + 1
      val isHoliday = cal.isHoliday(nextDay)
      val nextBy = if (isHoliday) by else by - 1
      shiftWorkingDay(nextDay, nextBy, cal)
  }
}

case object NoHoliday extends Calendar {
  override def isHoliday(d: Date) = false
}

case class SimpleCalendar(holidays: Set[Date]) extends Calendar {
  override def isHoliday(d: Date) = holidays.contains(d)
}

object SimpleCalendar {
  def apply(holidays: Date*): SimpleCalendar = SimpleCalendar(holidays.toSet)
}

/**
 * consider all weekend as holiday
 */
case object WeekendCalendar extends Calendar {
  override def isHoliday(d: Date) = {
    d.weekday == Saturday || d.weekday == Sunday
  }
}

/**
 * a calendar composed of multiple calendars.
 * this calendar will treat a day as holiday if any one of underlying calendar returns true
 *
 * @param cals list of calendars
 */
case class CompositeCalendar(cals: Calendar*) extends Calendar {
  override def isHoliday(d: Date) = cals.exists(_.isHoliday(d))
}

