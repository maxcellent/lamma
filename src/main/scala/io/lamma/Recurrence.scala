package io.lamma

import scala.annotation.tailrec
import Duration._

/**
 *
 */
trait Recurrence {
  /**
   * recurrence duration
   */
  val n: Int

  require(n > 0)

  /**
   * generate recurrence date based on start and end date
   *
   * @param start
   * @param end
   * @return list of recurrence date (period end dates)
   */
  def endDays(start: Date, end: Date) = {
    val end0 = start - 1
    gen(end0, end) match {
      case Nil => Nil
      case `end0` :: endDays => endDays
      case endDays => endDays
    }
  }

  /**
   * @param end0 last end day before start day (period 0 end day)
   * @param end final end day
   * @return list of recurrence date (period end date)
   */
  private[lamma] def gen(end0: Date, end: Date): List[Date]
}

/**
 * frequency is used to generate recurrence dates
 */
object Recurrence {

  // ============= shared methods =========
  @tailrec
  private[lamma] def genForward(current: Date, end: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current > end) {
      acc
    } else {
      genForward(current + freq, end, freq, acc :+ current)
    }
  }

  @tailrec
  private[lamma] def genBackward(end0: Date, current: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current < end0) {
      acc
    } else {
      genBackward(end0, current - freq, freq, current :: acc)
    }
  }

  // ========== daily ==========
  val EveryDay = Days(1)

  val EveryOtherDay = Days(2)

  case class Days(n: Int) extends Recurrence {
    private[lamma] override def gen(end0: Date, end: Date) = genForward(end0, end, n)
  }

  case class DaysBackward(n: Int) extends Recurrence {
    private[lamma] override def gen(end0: Date, end: Date) = genBackward(end0, end, n)
  }

  // ========= weekly ==========
  val EveryWeek = Weeks(1)

  val EveryOtherWeek = Weeks(2)

  /**
   * @param n
   * @param weekday which weekday to recur
   */
  case class Weeks(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    private val freq = n * 7

    private[lamma] override def gen(end0: Date, end: Date) = weekday match {
      case None => genForward(end0, end, freq)
      case Some(wd) => genForward(end0.nextWeekday(wd), end, freq)
    }
  }

  object Weeks {
    def apply(weekday: Weekday): Weeks = Weeks(1, weekday)

    def apply(n: Int, weekday: Weekday): Weeks = Weeks(n, Some(weekday))
  }

  case class WeeksBackward(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    val freq = n * 7

    private[lamma] override def gen(end0: Date, end: Date) = weekday match {
      case None => genBackward(end0, end, freq)
      case Some(wd) => genBackward(end0, end.previousWeekday(wd), freq)
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
    private[lamma] override def gen(end0: Date, end: Date) = {
      val dates = pom match {
        case Some(p) =>
          val days = (end0 to end).filter(p.isValidDOM).toList
          for (i <- 0 until days.size by n) yield days(i)
        case None =>
          val totalMonths = Date.monthsBetween(end0, end)
          for (i <- 0 to totalMonths by n) yield end0 + (i months)
      }
      dates.toList
    }
  }

  object Months {
    def apply(pom: PositionOfMonth): Months = Months(1, pom)

    def apply(n: Int, pom: PositionOfMonth): Months = Months(n, Some(pom))
  }

  case class MonthsBackward(n: Int, pom: Option[PositionOfMonth] = None) extends Recurrence {
    private[lamma] override def gen(end0: Date, end: Date) = {
      val dates = pom match {
        case Some(p) =>
          val days = (end0 to end).filter(p.isValidDOM).toList
          for (i <- days.size - 1 to 0 by -n) yield days(i)
        case None =>
          val totalMonths = Date.monthsBetween(end0, end)
          for (i <- 0 to totalMonths by n) yield end - (i months)
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
    private[lamma] override def gen(end0: Date, end: Date) = {
      val dates = poy match {
        case Some(p) =>
          val days = (end0 to end).filter(p.isValidDOY).toList
          for (i <- 0 until days.size by n) yield days(i)
        case None =>
          for (i <- 0 to Date.yearsBetween(end0, end) by n) yield end0 + (i years)
      }
      dates.toList
    }
  }

  object Years {
    def apply(poy: PositionOfYear): Years = Years(1, poy)

    def apply(n: Int, poy: PositionOfYear): Years = Years(n, Some(poy))
  }

  case class YearsBackward(n: Int, poy: Option[PositionOfYear] = None) extends Recurrence {
    private[lamma] override def gen(end0: Date, end: Date) = {
      val dates = poy match {
        case Some(p) =>
          val days = (end0 to end).filter(p.isValidDOY).toList
          for (i <- days.size - 1 to 0 by -n) yield days(i)
        case None =>
          for (i <- 0 to Date.yearsBetween(end0, end) by n) yield end - (i years)
      }
      dates.reverse.toList
    }
  }

  object YearsBackward {
    def apply(poy: PositionOfYear): YearsBackward = YearsBackward(1, poy)

    def apply(n: Int, poy: PositionOfYear): YearsBackward = YearsBackward(n, Some(poy))
  }
}

