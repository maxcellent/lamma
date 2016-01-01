package io.lamma

sealed trait Duration {
  val n: Int

  def toPattern: Pattern
}

case class DayDuration(n: Int) extends Duration {
  lazy val toPattern = Daily(n)
}

case class WeekDuration(n: Int) extends Duration {
  lazy val toPattern = Weekly(n)
}

case class MonthDuration(n: Int) extends Duration {
  lazy val toPattern = Monthly(n)
}

case class YearDuration(n: Int) extends Duration {
  lazy val toPattern = Yearly(n)
}
