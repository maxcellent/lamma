package io.lamma

/**
 * abstract class is used instead of trait
 *
 * otherwise it's not quite java friendly https://github.com/maxcellent/lamma/issues/4
 */
abstract class HolidayRule {

  def isHoliday(d: Date): Boolean

  def shiftWorkingDay(d: Date, by: Int) = {
    require(by != 0)
    HolidayRule.shiftWorkingDay(d, by, this)
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
   * merge holiday rule with another holiday rule, NoHoliday will be ignored
   */
  def and(that: HolidayRule) = (this, that) match {
    case (NoHoliday, cal2) => cal2
    case (cal1, NoHoliday) => cal1
    case (CompositeHolidayRule(cals1), CompositeHolidayRule(cals2)) => CompositeHolidayRule(cals1 ++ cals2)
    case (CompositeHolidayRule(cals1), cal2) => CompositeHolidayRule(cals1 + cal2)
    case (cal1, CompositeHolidayRule(cals2)) => CompositeHolidayRule(cals2 + cal1)
    case (cal1, cal2) => CompositeHolidayRule(cal1, cal2)
  }
}

object HolidayRule {
  def shiftWorkingDay(d: Date, by: Int, cal: HolidayRule): Date = by match {
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

case object NoHoliday extends HolidayRule {
  override def isHoliday(d: Date) = false
}

case class SimpleHolidayRule(holidays: Set[Date]) extends HolidayRule {
  override def isHoliday(d: Date) = holidays.contains(d)
}

object SimpleHolidayRule {
  def apply(holidays: Date*): SimpleHolidayRule = SimpleHolidayRule(holidays.toSet)

  def fromISOStrings(holidays: String*): SimpleHolidayRule = SimpleHolidayRule(holidays.map(Date(_)).toSet)
}

/**
 * consider all weekend as holiday
 */
case object Weekends extends HolidayRule {
  override def isHoliday(d: Date) = {
    d.dayOfWeek == Saturday || d.dayOfWeek == Sunday
  }
}

/**
 * a holiday rule composed of multiple holiday rules.
 * this rule will treat a day as holiday if any one of underlying rule returns true
 *
 * @param rules list of holiday rules
 */
case class CompositeHolidayRule(rules: Set[HolidayRule] = Set.empty) extends HolidayRule {
  override def isHoliday(d: Date) = rules.exists(_.isHoliday(d))
}

object CompositeHolidayRule {
  def apply(rules: HolidayRule*) = new CompositeHolidayRule(rules.toSet)
}

