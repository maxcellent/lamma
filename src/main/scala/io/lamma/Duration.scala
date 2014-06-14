package io.lamma

sealed trait Duration {
  val n: Int
}

object Duration {

  // TODO: move to root package (io.lamma)

  case class DayDuration(n: Int) extends Duration

  case class WeekDuration(n: Int) extends Duration

  case class MonthDuration(n: Int) extends Duration

  case class YearDuration(n: Int) extends Duration
}

