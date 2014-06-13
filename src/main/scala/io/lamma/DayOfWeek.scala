package io.lamma

sealed trait DayOfWeek

object DayOfWeek {
  case object Monday extends DayOfWeek

  case object Tuesday extends DayOfWeek

  case object Wednesday extends DayOfWeek

  case object Thursday extends DayOfWeek

  case object Friday extends DayOfWeek

  case object Saturday extends DayOfWeek

  case object Sunday extends DayOfWeek
}