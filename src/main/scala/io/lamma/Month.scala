package io.lamma

sealed trait Month

object Month {
  def apply(n: Int): Month = n match {
    case 1 => January
    case 2 => February
    case 3 => March
    case 4 => April
    case 5 => May
    case 6 => June
    case 7 => July
    case 8 => August
    case 9 => September
    case 10 => October
    case 11 => November
    case 12 => December
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
}
