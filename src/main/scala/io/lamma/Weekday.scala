package io.lamma

sealed trait Weekday

object Weekday {
  case object Monday extends Weekday

  case object Tuesday extends Weekday

  case object Wednesday extends Weekday

  case object Thursday extends Weekday

  case object Friday extends Weekday

  case object Saturday extends Weekday

  case object Sunday extends Weekday
}