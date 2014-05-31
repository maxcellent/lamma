package io.lamma

/**
 * once we shifted, how we are going to select the date to use
 */
trait Selector

object Selector {
  case object SameDay extends Selector

  /**
   * if current day not a working day,
   * then move forward to the next working day
   */
  case object Forward extends Selector

  /**
   * if current day not a working day,
   * then move backward to the previous working day
   */
  case object Backward extends Selector

  /**
   * the next working day unless that working day crosses over a new month
   */
  case object ModifiedFollowing extends Selector

  /**
   * previous working day unless that working day crosses over a new month
   */
  case object ModifiedPreceding extends Selector

  def select(d: Date, selector: Selector, cal: Calendar) = selector match {
    case SameDay => d
    case Forward => cal.forward(d)
    case Backward => cal.backward(d)
    case ModifiedFollowing => cal.modifiedFollowing(d)
    case ModifiedPreceding => cal.modifiedPreceding(d)
  }
}
