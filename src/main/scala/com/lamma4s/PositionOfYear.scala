package com.lamma4s

sealed trait PositionOfYear {

  /**
   * @return true if the input date is valid to the currently defined position of year
   */
  def isValidDOY(d: Date): Boolean
}

object PositionOfYear {
  val FirstDayOfYear = NthDayOfYear(1)

  val SecondDayOfYear = NthDayOfYear(2)

  case class NthDayOfYear(n: Int) extends PositionOfYear {
    require(n > 0 && n <= 366, "Day of year is valid from 1 to 366")

    override def isValidDOY(d: Date) = {
      if (n > d.maxDayOfYear) {
        d.isLastDayOfYear
      } else {
        d.dayOfYear == n
      }
    }
  }

  case object LastDayOfYear extends PositionOfYear {
    override def isValidDOY(d: Date) = d.isLastDayOfYear
  }

  def FirstWeekDayOfYear(weekday: Weekday) = NthWeekdayOfYear(1, weekday)

  case class NthWeekdayOfYear(n: Int, weekday: Weekday) extends PositionOfYear {
    require(n > 0 && n <= 53, "Weekday of year is valid from 1 to 53")
    
    override def isValidDOY(d: Date) = {
      if (d.weekday == weekday) {
        if (d.sameWeekdaysOfYear.size < n) {
          d.sameWeekdaysOfYear.last == d
        } else {
          d.sameWeekdaysOfYear(n - 1) == d
        }
      } else {
        false
      }
    }
  }

  case class LastWeekdayOfYear(weekday: Weekday) extends PositionOfYear {
    override def isValidDOY(d: Date) = d.sameWeekdaysOfYear.last == d
  }

  case class NthMonthOfYear(m: Month, pom: PositionOfMonth) extends PositionOfYear {
    override def isValidDOY(d: Date) = d.month == m && pom.isValidDOM(d)
  }
}


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
