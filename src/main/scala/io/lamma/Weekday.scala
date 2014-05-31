package io.lamma

import org.joda.time.{LocalDate, DateTimeConstants}

sealed trait Weekday

object Weekday {
  case object Monday extends Weekday

  case object Tuesday extends Weekday

  case object Wednesday extends Weekday

  case object Thursday extends Weekday

  case object Friday extends Weekday

  case object Saturday extends Weekday

  case object Sunday extends Weekday

  private[lamma] def fromJoda(d: LocalDate): Weekday = d.getDayOfWeek match {
    case DateTimeConstants.MONDAY => Monday
    case DateTimeConstants.TUESDAY => Tuesday
    case DateTimeConstants.WEDNESDAY => Wednesday
    case DateTimeConstants.THURSDAY => Thursday
    case DateTimeConstants.FRIDAY => Friday
    case DateTimeConstants.SATURDAY => Saturday
    case DateTimeConstants.SUNDAY => Sunday
  }
}