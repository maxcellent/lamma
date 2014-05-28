package com.lamma4s

import scala.annotation.tailrec
import Duration._

sealed trait Frequency {
  val n: Int

  require(n > 0)

  def generate(start: Date, end: Date): List[Date]
}

object Frequency {

  // ============= shared methods =========
  @tailrec
  private[lamma4s] def genForward(current: Date, end: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current > end) {
      acc
    } else {
      genForward(current + freq, end, freq, acc :+ current)
    }
  }

  @tailrec
  private[lamma4s] def genBackward(start: Date, current: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current < start) {
      acc
    } else {
      genBackward(start, current - freq, freq, current :: acc)
    }
  }

  // ========== daily ==========
  val EveryDay = DailyForward(1)

  case class DailyForward(n: Int) extends Frequency {
    override def generate(start: Date, end: Date) = genForward(start, end, n)
  }

  case class DailyBackward(n: Int) extends Frequency {
    override def generate(start: Date, end: Date) = genBackward(start, end, n)
  }

  // ========= weekly ==========
  val EveryWeek = WeeklyForward(1)

  case class WeeklyForward(n: Int, weekday: Option[Weekday] = None) extends Frequency {
    val freq = n * 7

    override def generate(start: Date, end: Date) = weekday match {
      case None => genForward(start, end, freq)
      case Some(wd) => genForward(start.nextWeekday(wd), end, freq)
    }
  }

  case class WeeklyBackward(n: Int, weekday: Option[Weekday] = None) extends Frequency {
    val freq = n * 7

    override def generate(start: Date, end: Date) = weekday match {
      case None => genBackward(start, end, freq)
      case Some(wd) => genBackward(start, end.previousWeekday(wd), freq)
    }
  }

  // ========= monthly ========
  val EveryMonth = MonthlyForward(1)

  case class MonthlyForward(n: Int, pom: Option[PositionOfMonth] = None) extends Frequency {
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

  case class MonthlyBackward(n: Int, pom: Option[PositionOfMonth] = None) extends Frequency {
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

  case class YearlyForward(n: Int, poy: Option[PositionOfYear] = None) extends Frequency {
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

  case class YearlyBackward(n: Int, poy: Option[PositionOfYear] = None) extends Frequency {
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

