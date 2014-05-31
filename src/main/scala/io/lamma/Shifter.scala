package io.lamma

/**
 * how we are going to shift relative to the anchor date
 */
trait Shifter

object Shifter {
  case object NoShift extends Shifter

  /**
   * @param n number of calendar days to shift
   */
  case class ShiftCalendarDays(n: Int) extends Shifter

  /**
   * @param n number of working days to shift
   */
  case class ShiftWorkingDays(n: Int) extends Shifter

  def shift(d: Date, shifter: Shifter, cal: Calendar) = shifter match {
    case NoShift => d
    case ShiftCalendarDays(n) => d + n
    case ShiftWorkingDays(n) => cal.shiftBizDay(d, n)
  }
}
