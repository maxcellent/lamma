package io.lamma

import scala.annotation.tailrec
import Duration._

sealed trait Recurrence {
  val n: Int

  require(n > 0)

  /**
   * generate recurrence date based on start and end date
   * @param start
   * @param end
   * @return list of recurrence date (period end date)
   */
  def generate(start: Date, end: Date): List[Date]
}

/**
 * frequency is used to generate recurrence dates
 */
object Recurrence {

  // ============= shared methods =========
  @tailrec
  private[lamma] def genForward(current: Date, end: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    val next = current + freq
    if (next > end) {
      acc
    } else {
      genForward(next, end, freq, acc :+ next)
    }
  }

  @tailrec
  private[lamma] def genBackward(start: Date, current: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    val previous = current - freq
    if (previous < start) {
      acc
    } else {
      genBackward(start, previous, freq, previous :: acc)
    }
  }

  // ========== daily ==========
  val EveryDay = DailyForward(1)

  case class DailyForward(n: Int) extends Recurrence {
    override def generate(start: Date, end: Date) = genForward(start - 1, end, n)
  }

  case class DailyBackward(n: Int) extends Recurrence {
    override def generate(start: Date, end: Date) = genBackward(start - 1, end, n)
  }

  // ========= weekly ==========
  val EveryWeek = WeeklyForward(1)

  case class WeeklyForward(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    val freq = n * 7

    override def generate(start: Date, end: Date) = weekday match {
      case None => genForward(start - 1, end, freq)
      case Some(wd) => genForward(start.nextWeekday(wd) - freq, end, freq)
    }
  }

  case class WeeklyBackward(n: Int, weekday: Option[Weekday] = None) extends Recurrence {
    val freq = n * 7

    override def generate(start: Date, end: Date) = weekday match {
      case None => genBackward(start - 1, end, freq)
      case Some(wd) => genBackward(start - 1, end.previousWeekday(wd), freq)
    }
  }

  // ========= monthly ========
  val EveryMonth = MonthlyForward(1)

  case class MonthlyForward(n: Int, pom: Option[PositionOfMonth] = None) extends Recurrence {
    override def generate(start: Date, end: Date) = {
      val dates = pom match {
        case Some(p) =>
          val days = (start to end).filter(p.isValidDOM).toList
          for (i <- 0 until days.size by n) yield days(i)
        case None =>
          val totalMonths = Date.monthsBetween(start, end)
          for (i <- 0 to totalMonths by n) yield start + (i months)
      }
      dates.toList
    }
  }

  case class MonthlyBackward(n: Int, pom: Option[PositionOfMonth] = None) extends Recurrence {
    override def generate(start: Date, end: Date) = {
      val dates = pom match {
        case Some(p) =>
          val days = (start to end).filter(p.isValidDOM).toList
          for (i <- days.size - 1 to 0 by -n) yield days(i)
        case None =>
          val totalMonths = Date.monthsBetween(start, end)
          for (i <- 0 to totalMonths by n) yield end - (i months)
      }
      dates.reverse.toList
    }
  }

  // ========= yearly ==========
  val EveryYear = YearlyForward(1)

  case class YearlyForward(n: Int, poy: Option[PositionOfYear] = None) extends Recurrence {
    override def generate(start: Date, end: Date) = {
      val dates = poy match {
        case Some(p) =>
          val days = (start to end).filter(p.isValidDOY).toList
          for (i <- 0 until days.size by n) yield days(i)
        case None =>
          val totalYears = end.yyyy - start.yyyy
          for (i <- 0 to totalYears by n) yield start + (i years)
      }
      dates.toList
    }
  }

  case class YearlyBackward(n: Int, poy: Option[PositionOfYear] = None) extends Recurrence {
    override def generate(start: Date, end: Date) = {
      val dates = poy match {
        case Some(p) =>
          val days = (start to end).filter(p.isValidDOY).toList
          for (i <- days.size - 1 to 0 by -n) yield days(i)
        case None =>
          val totalYears = end.yyyy - start.yyyy
          for (i <- 0 to totalYears by n) yield end - (i years)
      }
      dates.reverse.toList
    }
  }
}

