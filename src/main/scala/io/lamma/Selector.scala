package io.lamma

/**
 * once we shifted, how we are going to select the date to use
 */
trait Selector {
  def select(d: Date): Date
}

object Selector {

  /**
   * select current date
   */
  case object SameDay extends Selector {
    override def select(d: Date) = d
  }

  /**
   * if current day not a working day,
   * then move forward to the next working day
   */
  case class Forward(cal: HolidayRule = Weekends) extends Selector {
    override def select(d: Date) = cal.forward(d)
  }

  /**
   * if current day not a working day,
   * then move backward to the previous working day
   */
  case class Backward(cal: HolidayRule = Weekends) extends Selector {
    override def select(d: Date) = cal.backward(d)
  }

  /**
   * the next working day unless that working day crosses over a new month
   */
  case class ModifiedFollowing(cal: HolidayRule = Weekends) extends Selector {
    override def select(d: Date) = cal.modifiedFollowing(d)
  }

  /**
   * previous working day unless that working day crosses over a new month
   */
  case class ModifiedPreceding(cal: HolidayRule = Weekends) extends Selector {
    override def select(d: Date) = cal.modifiedPreceding(d)
  }
}
