package io.lamma

sealed trait Month {
  def ordinal = Month.Months.indexOf(this) + 1
}

object Month {
  /**
   * get month from int, starting from 1
   * @param n
   * @return
   */
  def apply(n: Int): Month = Months(n - 1)

  final val Months = List(January, February, March, April, May, June, July, August, September, October, November, December)
}

case object January extends Month

case object February extends Month

case object March extends Month

case object April extends Month

case object May extends Month

case object June extends Month

case object July extends Month

case object August extends Month

case object September extends Month

case object October extends Month

case object November extends Month

case object December extends Month
