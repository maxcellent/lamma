package io.lamma

sealed trait Duration {
  val n: Int

  require(n >= 0)
}

object Duration {

  case class Days(n: Int) extends Duration

  case class Weeks(n: Int) extends Duration

  case class Months(n: Int) extends Duration

  case class Years(n: Int) extends Duration

  implicit class DurationInt(n: Int) {

    def day = Days(n)

    def days = Days(n)

    def week = Weeks(n)

    def weeks = Weeks(n)

    def month = Months(n)

    def months = Months(n)

    def year = Years(n)

    def years = Years(n)
  }
}
