package io.lamma

import io.lamma.DayOfMonth.{NthWeekdayOfMonth, LastWeekdayOfMonth, NthDayOfMonth, LastDayOfMonth}
import io.lamma.PositionOfYear.{NthWeekdayOfYear, NthMonthOfYear, NthDayOfYear, LastDayOfYear}
import Locator._

/**
 * This should not be created directly
 *
 * @param pos if dow is defined, it means ordinal of weekday
 * @param dow
 * @param month
 */
case class Locator(pos: Position, dow: Option[DayOfWeek] = None, month: Option[Month] = None) {
  def of(m: Month) = this.copy(month = Some(m))

  lazy val pom: DayOfMonth = (pos, dow) match {
    case (Ordinal(n), None) => NthDayOfMonth(n)
    case (Last, None) => LastDayOfMonth
    case (Ordinal(n), Some(dow)) => NthWeekdayOfMonth(n, dow)
    case (Last, Some(dow)) => LastWeekdayOfMonth(dow)
  }

  lazy val poy = (pos, dow, month) match {
    case (Ordinal(n), None, None) => NthDayOfYear(n)
    case (Last, None, None) => LastDayOfYear
    case (Ordinal(n), Some(dow), None) => NthWeekdayOfYear(n, dow)
    case (Last, Some(dow), None) => PositionOfYear.LastWeekdayOfYear(dow)
    case (_, _, Some(m)) => NthMonthOfYear(m, pom)
  }
}

object Locator {
  def apply(n: Int) = new Locator(Ordinal(n))

  def apply(n: Int, dow: DayOfWeek) = new Locator(Ordinal(n), Some(dow))

  sealed trait Position

  case object Last extends Position

  case class Ordinal(n: Int) extends Position
}
