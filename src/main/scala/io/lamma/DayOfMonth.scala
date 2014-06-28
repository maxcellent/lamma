package io.lamma

/**
 * for each PositionOfMonth implementation
 * Lamma expect there is one and only one day match the criteria
 */
trait DayOfMonth {

  /**
   * @return true if the input date is valid to the currently defined position of month
   */
  def isValidDOM(d: Date): Boolean

}

object DayOfMonth {
  def validate(dom: DayOfMonth) = {

    def validate(yyyy: Int, mm: Int) = {
      val start = Date(yyyy, mm, 1)
      val end = start + (1 month) - 1
      val result = (start to end).filter(dom.isValidDOM).toList

      if (result.size != 1) {
        throw new InvalidDayOfMonthException(dom, yyyy, mm, result)
      }
    }

    validate(1900, 2) // special non-leap year Feb
    validate(2000, 2) // leap year Feb
    validate(2000, 3) // leap year non-Feb
    validate(2014, 2) // non-leap year Feb
    validate(2014, 1) // non-leap year month with 31 days
    validate(2014, 4) // non-leap year month with 30 days
  }

  val FirstDayOfMonth = NthDayOfMonth(1)

  case class NthDayOfMonth(n: Int) extends DayOfMonth {
    require(n > 0 && n <= 31, "Day of month is valid from 1 to 31")

    override def isValidDOM(d: Date) = if (n > d.maxDayOfMonth) {
      d.isLastDayOfMonth
    } else {
      d.dd == n
    }
  }

  case object LastDayOfMonth extends DayOfMonth {
    override def isValidDOM(d: Date) = d.isLastDayOfMonth
  }

  def FirstWeekdayOfMonth(dow: DayOfWeek) = NthWeekdayOfMonth(1, dow)

  case class NthWeekdayOfMonth(n: Int, dow: DayOfWeek) extends DayOfMonth {
    require(n > 0 && n <= 5, "Week of month is valid from 1 to 5")

    override def isValidDOM(d: Date) = {
      if (d.dayOfWeek == dow) {
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

  case class LastWeekdayOfMonth(dow: DayOfWeek) extends DayOfMonth {
    override def isValidDOM(d: Date) = d.dayOfWeek == dow && d.sameWeekdaysOfMonth.last == d
  }
}

class InvalidDayOfMonthException(dom: DayOfMonth, failingYear: Int, failingMonth: Int, result: List[Date])
  extends RuntimeException(s"${dom.toString} does not work for $failingYear-$failingMonth. Please make sure there is one and only one day matches for each month. Actual result: $result")