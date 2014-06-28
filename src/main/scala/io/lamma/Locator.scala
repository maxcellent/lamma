package io.lamma

import Locator._
import io.lamma.DayOfMonth.{LastWeekdayOfMonth, NthWeekdayOfMonth, LastDayOfMonth, NthDayOfMonth}
import io.lamma.DayOfYear.{NthMonthOfYear, NthWeekdayOfYear, LastDayOfYear, NthDayOfYear}

sealed trait Locator

object Locator {
  case object Last

  def apply(dow: DayOfWeek): Locator = Locator(dow.n)

  def apply(n: Int) = OrdinalLocator(Left(n))

  def apply(l: Last.type) = OrdinalLocator(Right(Last))

  def apply(n: Int, dow: DayOfWeek) = OrdinalWeekLocator(Left(n), dow)

  def apply(l: Last.type, dow: DayOfWeek) = OrdinalWeekLocator(Right(Last), dow)

  def apply(n: Int, m: Month) = OrdinalMonthLocator(Left(m.ordinal), OrdinalLocator(Left(n)))

  def apply(l: Last.type, m: Month) = OrdinalMonthLocator(Left(m.ordinal), OrdinalLocator(Right(Last)))

  def apply(n: Int, dow: DayOfWeek, m: Month): OrdinalMonthLocator = OrdinalMonthLocator(Left(m.ordinal), Locator(n, dow))

  def apply(l: Last.type, dow: DayOfWeek, m: Month): OrdinalMonthLocator = OrdinalMonthLocator(Left(m.ordinal), Locator(Last, dow))
}

sealed trait DayOfWeekSupport {
  this: Locator =>

  def dow: DayOfWeek
}

sealed trait DayOfMonthSupport {
  this: Locator =>

  def dom: DayOfMonth

  def of(m: Month) = OrdinalMonthLocator(Left(m.ordinal), this)
}

sealed trait DayOfYearSupport {
  this: Locator =>

  def doy: DayOfYear
}

/**
 * works for Weekly, Monthly and Yearly patterns
 */
case class OrdinalLocator(o: Either[Int, Last.type]) extends Locator with DayOfWeekSupport with DayOfMonthSupport with DayOfYearSupport {
  lazy val dow = o match {
    case Left(n) => DayOfWeek.of(n)
    case Right(Last) => Sunday
  }

  lazy val dom = o match {
    case Left(n) => NthDayOfMonth(n)
    case Right(Last) => LastDayOfMonth
  }

  lazy val doy = o match {
    case Left(n) => NthDayOfYear(n)
    case Right(Last) => LastDayOfYear
  }
}

/**
 * works for Monthly and Yearly patterns
 */
case class OrdinalWeekLocator(o: Either[Int, Last.type], dow: DayOfWeek) extends Locator with DayOfMonthSupport with DayOfYearSupport {
  lazy val dom = o match {
    case Left(n) => NthWeekdayOfMonth(n, dow)
    case Right(Last) => LastWeekdayOfMonth(dow)
  }

  lazy val doy = o match {
    case Left(n) => NthWeekdayOfYear(n, dow)
    case Right(Last) => NthWeekdayOfYear(53, dow)
  }
}

/**
 * works for Yearly pattern only
 * @param o ordinal of the month
 * @param dom
 */
case class OrdinalMonthLocator(o: Either[Int, Last.type], dom: DayOfMonthSupport) extends Locator with DayOfYearSupport {
  lazy val doy = o match {
    case Left(n) => NthMonthOfYear(Month(n), dom.dom)
    case Right(Last) => NthMonthOfYear(December, dom.dom)
  }
}
