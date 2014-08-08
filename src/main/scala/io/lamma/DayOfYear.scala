package io.lamma

/**
 * This class locates one day in a year <br>
 *
 * For each DayOfYear implementation,
 * Lamma expect there is one and only one day match the criteria in each year
 */
trait DayOfYear {

  /**
   * @return true if the input date is valid to the currently defined position of year
   */
  def isValidDOY(d: Date): Boolean
}

object DayOfYear {

  def validate(doy: DayOfYear) = {
    def validate(yyyy: Int) = {
      val result = (Date(yyyy, 1, 1) to Date(yyyy, 12, 31)).filter(doy.isValidDOY).toList
      if (result.size != 1) {
        throw new InvalidDayOfYearException(doy, yyyy, result)
      }
    }

    validate(1900)  // special non-leap year
    validate(2000)  // leap year
    validate(2014)  // non-leap year
  }

  val FirstDayOfYear = NthDayOfYear(1)

  val SecondDayOfYear = NthDayOfYear(2)

  case class NthDayOfYear(n: Int) extends DayOfYear {
    require(n > 0 && n <= 366, "Day of year is valid from 1 to 366")

    override def isValidDOY(d: Date) = {
      if (n > d.maxDayOfYear) {
        d.isLastDayOfYear
      } else {
        d.dayOfYear == n
      }
    }
  }

  case object LastDayOfYear extends DayOfYear {
    override def isValidDOY(d: Date) = d.isLastDayOfYear
  }

  def FirstWeekDayOfYear(dow: DayOfWeek) = NthWeekdayOfYear(1, dow)

  case class NthWeekdayOfYear(n: Int, dow: DayOfWeek) extends DayOfYear {
    require(n > 0 && n <= 53, "Weekday of year is valid from 1 to 53")
    
    override def isValidDOY(d: Date) = {
      if (d.dayOfWeek == dow) {
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

  def LastWeekdayOfYear(weekday: DayOfWeek) = NthWeekdayOfYear(53, weekday)

  def FirstMonthOfYear(dom: DayOfMonth) = NthMonthOfYear(January, dom)

  case class NthMonthOfYear(m: Month, dom: DayOfMonth) extends DayOfYear {
    override def isValidDOY(d: Date) = d.month == m && dom.isValidDOM(d)
  }

  def LastMonthOfYear(dom: DayOfMonth) = NthMonthOfYear(December, dom)
}

class InvalidDayOfYearException(doy: DayOfYear, failingYear: Int, result: List[Date])
  extends RuntimeException(s"${doy.toString} does not work for year $failingYear. Please make sure there is one and only one day matches for each year. Actual result: $result")