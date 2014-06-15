package io.lamma

sealed trait DayOfWeek {
  def ordinal = DayOfWeek.DayOfWeeks.indexOf(this) + 1
}

object DayOfWeek {
  final val DayOfWeeks = List(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)

  /**
   *
   * @param n Monday == 1
   * @return
   */
  def apply(n: Int) = DayOfWeeks(n - 1)
}

case object Monday extends DayOfWeek

case object Tuesday extends DayOfWeek

case object Wednesday extends DayOfWeek

case object Thursday extends DayOfWeek

case object Friday extends DayOfWeek

case object Saturday extends DayOfWeek

case object Sunday extends DayOfWeek