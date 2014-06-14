package io.lamma

import io.lamma.DayOfWeek.{Sunday, Saturday}

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

  /**
   * merge holiday with another holiday, NoHoliday will be ignored
   */
  def and(that: Calendar) = (this, that) match {
    case (NoHoliday, cal2) => cal2
    case (cal1, NoHoliday) => cal1
    case (CompositeCalendar(cals1), CompositeCalendar(cals2)) => CompositeCalendar(cals1 ++ cals2)
    case (CompositeCalendar(cals1), cal2) => CompositeCalendar(cals1 + cal2)
    case (cal1, CompositeCalendar(cals2)) => CompositeCalendar(cals2 + cal1)
    case (cal1, cal2) => CompositeCalendar(cal1, cal2)
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
    d.dayOfWeek == Saturday || d.dayOfWeek == Sunday
  }
}

/**
 * a calendar composed of multiple calendars.
 * this calendar will treat a day as holiday if any one of underlying calendar returns true
 *
 * @param cals list of calendars
 */
case class CompositeCalendar(cals: Set[Calendar] = Set.empty) extends Calendar {
  override def isHoliday(d: Date) = cals.exists(_.isHoliday(d))
}

object CompositeCalendar {
  def apply(cals: Calendar*) = new CompositeCalendar(cals.toSet)
}

