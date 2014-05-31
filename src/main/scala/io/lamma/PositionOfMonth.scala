package io.lamma

trait PositionOfMonth {

  /**
   * @return true if the input date is valid to the currently defined position of month
   */
  def isValidDOM(d: Date): Boolean

}

object PositionOfMonth {
  val FirstDayOfMonth = NthDayOfMonth(1)

  case class NthDayOfMonth(n: Int) extends PositionOfMonth {
    require(n > 0 && n <= 31, "Day of month is valid from 1 to 31")

    override def isValidDOM(d: Date) = if (n > d.maxDayOfMonth) {
      d.isLastDayOfMonth
    } else {
      d.dd == n
    }
  }

  case object LastDayOfMonth extends PositionOfMonth {
    override def isValidDOM(d: Date) = d.isLastDayOfMonth
  }

  def FirstWeekdayOfMonth(weekday: Weekday) = NthWeekdayOfMonth(1, weekday)

  case class NthWeekdayOfMonth(n: Int, weekday: Weekday) extends PositionOfMonth {
    require(n > 0 && n <= 5, "Week of month is valid from 1 to 5")

    override def isValidDOM(d: Date) = {
      if (d.weekday == weekday) {
        if (d.sameWeekdaysOfMonth.size < n) {
          d.sameWeekdaysOfMonth.last == d
        } else {
          d.sameWeekdaysOfMonth(n - 1) == d
        }
      } else {
        false
      }
    }
  }

  case class LastWeekdayOfMonth(weekday: Weekday) extends PositionOfMonth {
    override def isValidDOM(d: Date) = d.weekday == weekday && d.sameWeekdaysOfMonth.last == d
  }
}