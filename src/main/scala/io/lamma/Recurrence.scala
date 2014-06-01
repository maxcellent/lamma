package io.lamma

import scala.annotation.tailrec
import Duration._
import io.lamma.Selector.{Backward, Forward, SameDay}
import io.lamma.Shifter.{NoShift, ShiftWorkingDays, ShiftCalendarDays}

/**
 *
 */
trait Recurrence {

  /**
   * generate recurrence date based on start and end date
   *
   * @param start schedule start date
   * @param end schedule end date
   * @return list of recurrence date (period end dates)
   */
  def periodEndDays(start: Date, end: Date) = {
    val end0 = start - 1  // last period end day (period 0)
    recur(end0, end) match {
      case Nil => Nil
      case `end0` :: endDays => endDays
      case endDays => endDays
    }
  }

  /**
   * @param from recur from date
   * @param to recur to date
   * @return list of recurrence date
   */
  private[lamma] def recur(from: Date, to: Date): List[Date]
}

/**
 * frequency is used to generate recurrence dates
 */
object Recurrence {

  // ============= shared methods =========
  @tailrec
  private[lamma] def recurForward(current: Date, to: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current > to) {
      acc
    } else {
      recurForward(current + freq, to, freq, acc :+ current)
    }
  }

  @tailrec
  private[lamma] def recurBackward(from: Date, current: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current < from) {
      acc
    } else {
      recurBackward(from, current - freq, freq, current :: acc)
    }
  }

  // ========== daily ==========
  val EveryDay = Days(1)

  val EveryOtherDay = Days(2)

  /**
   * @param shifter only ShiftCalendarDays / ShiftWorkingDays are allowed
   * @param selector selector is only used to select from date,
   *                 if we want to select each result date, then we can use sequence level or schedule level selector
   */
  case class Days(shifter: Shifter, selector: Selector = SameDay) extends Recurrence {
    shifter match {
      case NoShift => throw new IllegalArgumentException(s"$NoShift should not be used here. Otherwise an infinite loop is likely to happen")
      case ShiftCalendarDays(n) => require(n > 0, s"Calendar day shifter $shifter should have a positive delta. Otherwise an infinite loop is likely to happen. ")
      case ShiftWorkingDays(n, _) => require(n > 0, s"Working day shifter $shifter should have a positive delta. Otherwise an infinite loop is likely to happen. ")
    }

    override private[lamma] def recur(from: Date, to: Date) = shift(selector.select(from), to)

    @tailrec
    private def shift(current: Date, to: Date, acc: List[Date] = Nil): List[Date] = {
      if (current > to) {
        acc
      } else {
        shift(shifter.shift(current), to , acc :+ current)
      }
    }
  }

  object Days {
    def apply(n: Int): Days = {
      require(n > 0, s"Number of days must be a positive number. Otherwise an infinite loop is likely to happen. Actual value = $n")
      Days(ShiftCalendarDays(n))
    }

    def workingDay(n: Int, cal: Calendar = WeekendCalendar) = {
      require(n > 0, s"Number of working days must be a positive number. Otherwise an infinite loop is likely to happen. Actual value = $n")
      Days(ShiftWorkingDays(n, cal), Forward(cal))
    }
  }

  /**
   *
   * @param shifter  only ShiftCalendarDays / ShiftWorkingDays are allowed
   * @param selector selector is only used to select to date,
   *                 if we want to select each result date, then we can use sequence level or schedule level selector
   */
  case class DaysBackward(shifter: Shifter, selector: Selector = SameDay) extends Recurrence {
    shifter match {
      case NoShift => throw new IllegalArgumentException(s"$NoShift should not be used here. Otherwise an infinite loop is likely to happen")
      case ShiftCalendarDays(n) => require(n < 0, s"Calendar day shifter $shifter should have a negative delta. Otherwise an infinite loop is likely to happen. ")
      case ShiftWorkingDays(n, _) => require(n < 0, s"Working day shifter $shifter should have a negative delta. Otherwise an infinite loop is likely to happen. ")
    }

    private[lamma] override def recur(from: Date, to: Date) = shift(from, selector.select(to))

    @tailrec
    private def shift(from: Date, current: Date, acc: List[Date] = Nil): List[Date] = {
      if (current < from) {
        acc
      } else {
        shift(from, shifter.shift(current), current :: acc)
      }
    }
  }

  object DaysBackward {
    def apply(n: Int): DaysBackward = {
      require(n > 0, s"Number of days must be a positive number. Otherwise an infinite loop is likely to happen. Actual value = $n")
      DaysBackward(ShiftCalendarDays(-n))
    }

    def workingDay(n: Int, cal: Calendar = WeekendCalendar) = {
      require(n > 0, s"Number of working days must be a positive number. Otherwise an infinite loop is likely to happen. Actual value = $n")
      DaysBackward(ShiftWorkingDays(-n, cal), Backward(cal))
    }
  }

  // ========= weekly ==========
  val EveryWeek = Weeks(1)

  val EveryOtherWeek = Weeks(2)

  /**
   * @param n
   * @param weekday which weekday to recur
   */
  case class Weeks(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    require(n > 0)

    private val freq = n * 7

    private[lamma] override def recur(from: Date, to: Date) = weekday match {
      case None => recurForward(from, to, freq)
      case Some(wd) => recurForward(from.nextWeekday(wd), to, freq)
    }
  }

  object Weeks {
    def apply(weekday: Weekday): Weeks = Weeks(1, weekday)

    def apply(n: Int, weekday: Weekday): Weeks = Weeks(n, Some(weekday))
  }

  case class WeeksBackward(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    require(n > 0)

    val freq = n * 7

    private[lamma] override def recur(from: Date, to: Date) = weekday match {
      case None => recurBackward(from, to, freq)
      case Some(wd) => recurBackward(from, to.previousWeekday(wd), freq)
    }
  }

  object WeeksBackward {
    def apply(weekday: Weekday): WeeksBackward = WeeksBackward(1, weekday)

    def apply(n: Int, weekday: Weekday): WeeksBackward = WeeksBackward(n, Some(weekday))
  }

  // ========= monthly ========
  val EveryMonth = Months(1)

  val EveryOtherMonth = Months(2)

  case class Months(n: Int, pom: Option[PositionOfMonth] = None) extends Recurrence {
    require(n > 0)

    private[lamma] override def recur(from: Date, to: Date) = {
      val dates = pom match {
        case Some(p) =>
          val days = (from to to).filter(p.isValidDOM).toList
          for (i <- 0 until days.size by n) yield days(i)
        case None =>
          val totalMonths = Date.monthsBetween(from, to)
          for (i <- 0 to totalMonths by n) yield from + (i months)
      }
      dates.toList
    }
  }

  object Months {
    def apply(pom: PositionOfMonth): Months = Months(1, pom)

    def apply(n: Int, pom: PositionOfMonth): Months = Months(n, Some(pom))
  }

  case class MonthsBackward(n: Int, pom: Option[PositionOfMonth] = None) extends Recurrence {
    require(n > 0)

    private[lamma] override def recur(from: Date, to: Date) = {
      val dates = pom match {
        case Some(p) =>
          val days = (from to to).filter(p.isValidDOM).toList
          for (i <- days.size - 1 to 0 by -n) yield days(i)
        case None =>
          val totalMonths = Date.monthsBetween(from, to)
          for (i <- 0 to totalMonths by n) yield to - (i months)
      }
      dates.reverse.toList
    }
  }

  object MonthsBackward {
    def apply(pom: PositionOfMonth): MonthsBackward = MonthsBackward(1, pom)

    def apply(n: Int, pom: PositionOfMonth): MonthsBackward = MonthsBackward(n, Some(pom))
  }

  // ========= yearly ==========
  val EveryYear = Years(1)

  val EveryOtherYear = Years(2)

  case class Years(n: Int, poy: Option[PositionOfYear] = None) extends Recurrence {
    require(n > 0)

    private[lamma] override def recur(from: Date, to: Date) = {
      val dates = poy match {
        case Some(p) =>
          val days = (from to to).filter(p.isValidDOY).toList
          for (i <- 0 until days.size by n) yield days(i)
        case None =>
          for (i <- 0 to Date.yearsBetween(from, to) by n) yield from + (i years)
      }
      dates.toList
    }
  }

  object Years {
    def apply(poy: PositionOfYear): Years = Years(1, poy)

    def apply(n: Int, poy: PositionOfYear): Years = Years(n, Some(poy))
  }

  case class YearsBackward(n: Int, poy: Option[PositionOfYear] = None) extends Recurrence {
    require(n > 0)

    private[lamma] override def recur(from: Date, to: Date) = {
      val dates = poy match {
        case Some(p) =>
          val days = (from to to).filter(p.isValidDOY).toList
          for (i <- days.size - 1 to 0 by -n) yield days(i)
        case None =>
          for (i <- 0 to Date.yearsBetween(from, to) by n) yield to - (i years)
      }
      dates.reverse.toList
    }
  }

  object YearsBackward {
    def apply(poy: PositionOfYear): YearsBackward = YearsBackward(1, poy)

    def apply(n: Int, poy: PositionOfYear): YearsBackward = YearsBackward(n, Some(poy))
  }
}

