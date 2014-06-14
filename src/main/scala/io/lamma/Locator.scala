package io.lamma
//
///**
// *
// */
//sealed trait Locator
//
//trait Ordinal {
//  val n: Int
//
//  def day = DayOrdinal(n)
//
//  def week = WeekOrdinal(n)
//
//  def month = MonthOrdinal(n)
//
//  def year = YearOrdinal(n)
//}
//
///**
// * every nth day
// */
//case class DayOrdinal(n: Int) extends Ordinal
//
///**
// * every nth week
// * every nth week + day of week (eg, nth Friday)
// */
//case class WeekOrdinal(n: Int, dow: Option[DayOfWeek] = None) extends Ordinal {
//  def of(month: Month) = ???
//}
//
///**
// * every nth month
// * every nth month + day of month
// */
//case class MonthOrdinal(n: Int, dom: Option[DayOfMonth] = None) extends Ordinal
//
//case class YearOrdinal(n: Int, doy: Option[PositionOfYear] = None) extends Ordinal

import Locator._
import io.lamma.DayOfMonth.{NthWeekdayOfMonth, LastWeekdayOfMonth, NthDayOfMonth, LastDayOfMonth}
import io.lamma.PositionOfYear.{NthWeekdayOfYear, NthMonthOfYear, NthDayOfYear, LastDayOfYear}

/**
 * cannot be created directly
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
