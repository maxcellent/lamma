package io.lamma

sealed trait Duration {
  val n: Int
}

object Duration {

  case class DayDuration(n: Int) extends Duration

  case class WeekDuration(n: Int) extends Duration

  case class MonthDuration(n: Int) extends Duration

  case class YearDuration(n: Int) extends Duration

  implicit class DurationInt(n: Int) {

    def day = DayDuration(n)

    def days = DayDuration(n)

    def week = WeekDuration(n)

    def weeks = WeekDuration(n)

    def month = MonthDuration(n)

    def months = MonthDuration(n)

    def year = YearDuration(n)

    def years = YearDuration(n)
  }
}
