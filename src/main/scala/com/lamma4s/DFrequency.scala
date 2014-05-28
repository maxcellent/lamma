package com.lamma4s

import annotation.tailrec

@deprecated
sealed trait DFrequency {
  def generate(start: Date, end: Date): List[Date]
}

object DFrequency {

  // ================= fixed length generation ===============

  @tailrec
  private[lamma4s] def forwardGen(current: Date, end: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current > end) {
      acc
    } else {
      forwardGen(current + freq, end, freq, acc :+ current)
    }
  }

  @tailrec
  private[lamma4s] def backwardGen(start: Date, current: Date, freq: Int, acc: List[Date] = Nil): List[Date] = {
    if (current < start) {
      acc
    } else {
      backwardGen(start, current - freq, freq, current :: acc)
    }
  }

  val Daily = ForwardDaily(1)

  case class ForwardDaily(days: Int = 1) extends DFrequency {
    require(days > 0)
    override def generate(start: Date, end: Date) = forwardGen(start, end, days)
  }

  case class BackwardDaily(days: Int = 1) extends DFrequency {
    require(days > 0)
    override def generate(start: Date, end: Date) = backwardGen(start, end, days)
  }

  val Weekly = ForwardWeekly(1)

  case class ForwardWeekly(weeks: Int = 1, weekday: Option[Weekday] = None) extends DFrequency {
    require(weeks > 0)

    val freq = weeks * 7

    override def generate(start: Date, end: Date) = weekday match {
      case Some(wd) => forwardGen(start.nextWeekday(wd), end, freq)
      case None => forwardGen(start, end, freq)
    }
  }

  object ForwardWeekly {
    def apply(weekday: Weekday) = new ForwardWeekly(weekday = Some(weekday))

    def apply(weeks: Int, weekday: Weekday) = new ForwardWeekly(weeks, Some(weekday))
  }

  case class BackwardWeekly(weeks: Int = 1, weekday: Option[Weekday] = None) extends DFrequency {
    require(weeks > 0)

    val freq = weeks * 7

    override def generate(start: Date, end: Date) = weekday match {
      case Some(wd) => backwardGen(start, end.previousWeekday(wd), freq)
      case None => backwardGen(start, end, freq)
    }
  }

  object BackwardWeekly {
    def apply(weekday: Weekday) = new BackwardWeekly(weekday = Some(weekday))

    def apply(weeks: Int, weekday: Weekday) = new BackwardWeekly(weeks, Some(weekday))
  }

  // ================= floating length generation ===============

  /**
   * @param months
   * @param dom day of month, it will be start day when not specified
   */
  case class ForwardMonthlyByDays(months: Int, dom: Option[Int] = None) extends DFrequency {
    require(months > 0)

    override def generate(start: Date, end: Date) = ???
  }

  case class BackwardMonth(months: Int, dom: Option[Int] = None) extends DFrequency {
    override def generate(start: Date, end: Date): List[Date] = ???
  }

  case class ForwardMonthlyByWeeksAndDays()
}

case class Yearly(doy: Option[Int] = None) extends DFrequency {
  override def generate(start: Date, end: Date): List[Date] = ???
}




