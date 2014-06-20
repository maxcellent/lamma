package io.lamma

import io.lamma.DayOfMonth.NthDayOfMonth
import io.lamma.DayOfYear.NthMonthOfYear

import scala.annotation.tailrec

/**
 * recurrence pattern
 */
trait Pattern {

  /**
   * @param from recur from date
   * @param to recur to date
   * @return list of recurrence date
   */
  def recur(from: Date, to: Date): List[Date]
}

object Pattern {

  /**
   * fixed step recurrence
   * @param current
   * @param to
   * @param step
   * @param acc
   * @return
   */
  @tailrec
  private[lamma] def recur(current: Date, to: Date, step: Int, acc: List[Date] = Nil): List[Date] = {
    require(step != 0, "step cannot be 0.")

    if ((step > 0 && current > to) || (step < 0 && current < to )) {
      acc
    } else {
      recur(current + step, to, step, acc :+ current)
    }
  }
}

/**
 * always recur by calendar day
 *
 * use cases like: "d1 to d2 by n working days" can be solved by:
 *  val dates = Date(2014, 5, 10) to Date(2014, 10, 10) except weekends
 *  dates.toList.grouped(5).map(_.last).foreach(println)
 *
 * @param step in days
 */
case class Daily(step: Int = 1) extends Pattern {
  require(step != 0, "step cannot be 0.")

  override def recur(from: Date, to: Date) = Pattern.recur(from, to, step)
}

/**
 * @param step number of weeks for each step
 * @param dowOpt
 */
case class Weekly(step: Int, dowOpt: Option[DayOfWeek] = None) extends Pattern {
  require(step != 0, "step cannot be 0.")

  val stepInDay = step * 7

  override def recur(from: Date, to: Date) = dowOpt match {
    case None => Pattern.recur(from, to, stepInDay)
    case Some(dow) => Pattern.recur(Weekly.adjustedFrom(from, step, dow), to, stepInDay)
  }

  def on(s: DayOfWeekSupport) = this.copy(dowOpt = Some(s.dow))
}

object Weekly {
  def apply(dow: DayOfWeek): Weekly = apply(1, dow)

  def apply(step: Int, dow: DayOfWeek) = new Weekly(step, Some(dow))

  /**
   * adjusted from date based on step and DayOfWeek
   *
   * @param from
   * @param step
   * @param dow
   * @return
   */
  private[lamma] def adjustedFrom(from: Date, step: Int, dow: DayOfWeek) = {
    require(step != 0, "step cannot be 0.")

    if (step > 0) {
      (from - 1).comingDayOfWeek(dow)
    } else {
      (from + 1).pastDayOfWeek(dow)
    }
  }
}

/**
 *
 * @param step number of months for each recurring step
 * @param domOpt
 */
case class Monthly(step: Int, domOpt: Option[DayOfMonth] = None) extends Pattern {
  require(step != 0, "step cannot be 0.")

  override def recur(from: Date, to: Date) = {
    val dom = domOpt.getOrElse(NthDayOfMonth(from.dd))

    @tailrec
    def doRecur(current: Date, to: Date, acc: List[Date] = Nil): List[Date] = {
      if ((step > 0 && current > to) || (step < 0 && current < to)) {
        acc
      } else {
        val next = current + (step months)
        doRecur(next.dayOfThisMonth(dom), to, acc :+ current)
      }
    }

    doRecur(Monthly.adjustedFrom(from, step, dom), to)
  }

  def on(s: DayOfMonthSupport) = this.copy(domOpt = Some(s.dom))
}

object Monthly {
  def apply(dom: DayOfMonth): Monthly = apply(1, dom)

  def apply(step: Int, dom: DayOfMonth) = new Monthly(step, Some(dom))

  private[lamma] def adjustedFrom(from: Date, step: Int, dom: DayOfMonth) = {
    require(step != 0, "step cannot be 0.")

    if (step > 0) {
      (from - 1).comingDayOfMonth(dom)
    } else {
      (from + 1).pastDayOfMonth(dom)
    }
  }
}

case class Yearly(step: Int, doyOpt: Option[DayOfYear] = None) extends Pattern {
  require(step != 0, "step cannot be 0.")

  override def recur(from: Date, to: Date): List[Date] = {
    val doy = doyOpt.getOrElse(NthMonthOfYear(from.month, NthDayOfMonth(from.dd)))

    @tailrec
    def doRecur(current: Date, to: Date, acc: List[Date] = Nil): List[Date] = {
      if ((step > 0 && current > to) || (step < 0 && current < to)) {
        acc
      } else {
        val next = current + (step years)
        val adjusted = next.dayOfThisYear(doy)
        doRecur(adjusted, to, acc :+ current)
      }
    }

    doRecur(Yearly.adjustedFrom(from, step, doy), to)
  }

  def on(s: DayOfYearSupport) = this.copy(doyOpt = Some(s.doy))
}

object Yearly {
  def apply(doy: DayOfYear): Yearly = apply(1, doy)

  def apply(step: Int, doy: DayOfYear) = new Yearly(step, Some(doy))

  private[lamma] def adjustedFrom(from: Date, step: Int, doy: DayOfYear) = {
    require(step != 0, "step cannot be 0.")

    if (step > 0) {
      (from - 1).comingDayOfYear(doy)
    } else {
      (from + 1).pastDayOfYear(doy)
    }
  }

  @tailrec
  private[lamma] def recur(current: Date, to: Date, step: Int, doy: DayOfYear, acc: List[Date] = Nil): List[Date] = {
    if ((step > 0 && current > to) || (step < 0 && current < to)) {
      acc
    } else {
      val next = current + (step years)
      recur(next, to, step, doy, acc :+ next.dayOfThisYear(doy))
    }
  }
}