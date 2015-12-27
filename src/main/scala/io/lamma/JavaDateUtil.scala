package io.lamma

import java.text.{ParseException, SimpleDateFormat}
import java.util.Calendar._
import java.util.{Calendar => JCalendar, TimeZone}

import scala.util.{Failure, Success, Try}

/**
 * all operations requiring java.util.Date are maintained here
 */
private[lamma] object JavaDateUtil {

  def calendar(d: Date): JCalendar = calendar(d.yyyy, d.mm, d.dd)

  def calendar(yyyy: Int, mm: Int, dd: Int) = {
    val cal = newCalender()
    cal.set(yyyy, mm - 1, dd)   // java month starts from 0. ie, Jan = 0
    cal
  }

  /**
   * use method instead of val
   * because SimpleDateFormat is not thread safe and thus cannot be shared among multiple threads
   */
  def isoFmt = {
    val fmt = new SimpleDateFormat("yyyy-MM-dd")
    fmt.setCalendar(newCalender)  // specify UTC calendar here, to make sure the formatted date is also in UTC
    fmt
  }

  def calendar(isoRepr: String): JCalendar = {
    Try(isoFmt.parse(isoRepr)) match {
      case Success(javaDate) =>
        val javaDate = isoFmt.parse(isoRepr)
        val cal = newCalender()
        cal.setTime(javaDate)
        cal
      case Failure(_: ParseException) => throw new IllegalArgumentException(s"Unable to parse date from string '$isoRepr'. ISO 8601 date format is expected. eg, 2015-02-27")
      case Failure(x) => throw x
    }
  }

  /**
   * new calendar with all hour / minutes / seconds / ms cleared
   */
  private def newCalender() = {
    val cal = JCalendar.getInstance(TimeZone.getTimeZone("UTC"))
    cal.set(HOUR_OF_DAY, 0)     // somehow cal.clear(HOUR_OF_DAY) does not quite work
    cal.clear(HOUR)
    cal.clear(MINUTE)
    cal.clear(SECOND)
    cal.clear(MILLISECOND)
    cal
  }

  def date(cal: JCalendar) = Date(cal.get(YEAR), cal.get(MONTH) + 1, cal.get(DAY_OF_MONTH))

  def plus(d: Date, duration: Duration) = {
    val cal = calendar(d)

    duration match {
      case DayDuration(n) => cal.add(DATE, n)
      case WeekDuration(n) => cal.add(DATE, n * 7)
      case MonthDuration(n) => cal.add(MONTH, n)
      case YearDuration(n) => cal.add(YEAR, n)
    }

    date(cal)
  }

  def minus(d: Date, duration: Duration) = {
    val cal = calendar(d)

    duration match {
      case DayDuration(n) => cal.add(DATE, - n)
      case WeekDuration(n) => cal.add(DATE, - n * 7)
      case MonthDuration(n) => cal.add(MONTH, - n)
      case YearDuration(n) => cal.add(YEAR, - n)
    }

    date(cal)
  }

  final val OneDay = 24 * 60 * 60 * 1000

  /**
   * because calendar is always UTC here, so no need to worry about day light saving
   */
  def daysBetween(d1: Date, d2: Date) = {
    val d1Millis = calendar(d1).getTimeInMillis
    val d2Millis = calendar(d2).getTimeInMillis
    ((d2Millis - d1Millis) / OneDay).toInt
  }

  /**
   * complete months between d1, and d2
   */
  def monthsBetween(d1: Date, d2: Date) = {
    val cal1 = calendar(d1)
    val cal2 = calendar(d2)

    val unadjusted = (cal2.get(YEAR) - cal1.get(YEAR)) * 12 + cal2.get(MONTH) - cal1.get(MONTH)

    cal1.add(MONTH, unadjusted)

    if (cal1.getTimeInMillis > cal2.getTimeInMillis) {
      unadjusted - 1
    } else {
      unadjusted
    }
  }

  /**
   * complete years between d1 and d2
   *
   * eg, 2014-02-01 -> 2015-01-31 will return 0
   */
  def yearsBetween(d1: Date, d2: Date) = {
    val cal1 = calendar(d1)
    val cal2 = calendar(d2)

    val unadjusted = cal2.get(YEAR) - cal1.get(YEAR)

    cal1.add(YEAR, unadjusted)

    if (cal1.getTimeInMillis > cal2.getTimeInMillis) {
      unadjusted - 1
    } else {
      unadjusted
    }
  }

  def dayOfWeek(d: Date): DayOfWeek = calendar(d).get(DAY_OF_WEEK) match {
    case 1 => Sunday
    case 2 => Monday
    case 3 => Tuesday
    case 4 => Wednesday
    case 5 => Thursday
    case 6 => Friday
    case 7 => Saturday
  }

  def dayOfMonth(d: Date) = calendar(d).get(DAY_OF_MONTH)

  def maxDayOfMonth(d: Date) = calendar(d).getActualMaximum(DAY_OF_MONTH)

  def dayOfYear(d: Date) = calendar(d).get(DAY_OF_YEAR)

  def maxDayOfYear(d: Date) = calendar(d).getActualMaximum(DAY_OF_YEAR)
}
