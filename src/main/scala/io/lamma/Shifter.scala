package io.lamma

/**
 * how we are going to shift relative to the anchor date
 */
trait Shifter {
  def shift(d: Date): Date
}

object Shifter {
  case object NoShift extends Shifter {
    override def shift(d: Date) = d
  }

  /**
   * @param n number of calendar days to shift
   */
  case class ShiftCalendarDays(n: Int) extends Shifter {
    override def shift(d: Date) = d + n
  }

  /**
   * @param n number of working days to shift
   * @param holiday holiday rule applied to determine working days
   */
  case class ShiftWorkingDays(n: Int, holiday: HolidayRule = Weekends) extends Shifter {
    override def shift(d: Date) = holiday.shiftWorkingDay(d, n)
  }
  
  def apply(n: Int) = ShiftCalendarDays(n)
  
  def apply(n: Int, holiday: HolidayRule) = ShiftWorkingDays(n, holiday)
}
