package io.lamma

import scala.annotation.tailrec
import Duration._

/**
 *
 */
sealed trait Recurrence {
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
  val EveryDay = DailyForward(1)

  case class DailyForward(n: Int) extends Recurrence {
    private[lamma] override def gen(end0: Date, end: Date) = genForward(end0, end, n)
  }

  case class DailyBackward(n: Int) extends Recurrence {
    private[lamma] override def gen(end0: Date, end: Date) = genBackward(end0, end, n)
  }

  // ========= weekly ==========
  val EveryWeek = WeeklyForward(1)

  /**
   * @param n
   * @param weekday which weekday to recur
   */
  case class WeeklyForward(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    private val freq = n * 7

    private[lamma] override def gen(end0: Date, end: Date) = weekday match {
      case None => genForward(end0, end, freq)
      case Some(wd) => genForward(end0.nextWeekday(wd), end, freq)
    }
  }

  case class WeeklyBackward(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    val freq = n * 7

    private[lamma] override def gen(end0: Date, end: Date) = weekday match {
      case None => genBackward(end0, end, freq)
      case Some(wd) => genBackward(end0, end.previousWeekday(wd), freq)
    }
  }

  // ========= monthly ========
  val EveryMonth = MonthlyForward(1)

  case class MonthlyForward(n: Int, pom: Option[PositionOfMonth] = None) extends Recurrence {
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

  case class MonthlyBackward(n: Int, pom: Option[PositionOfMonth] = None) extends Recurrence {
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

  // ========= yearly ==========
  val EveryYear = YearlyForward(1)

  case class YearlyForward(n: Int, poy: Option[PositionOfYear] = None) extends Recurrence {
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

  case class YearlyBackward(n: Int, poy: Option[PositionOfYear] = None) extends Recurrence {
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
}

