package io.lamma

sealed trait StartRule {
  /**
   * apply start rule to periods
   */
  def applyRule(start: Date, periods: List[Period]): List[Period]
}

case object NoStartRule extends StartRule {
  override def applyRule(start: Date, periods: List[Period]) = periods
}

/**
 *
 * @param max period length, start date inclusive, end date exclusive.
 */
case class LongStart(max: Int) extends StartRule {
  require(max > 0, "max start should be larger than 0")

  override def applyRule(start: Date, periods: List[Period]) = periods match {
    case Nil => Nil
    case first :: rest =>
      if (first.to - start <= max) {
        Period(start, first.to) :: rest
      } else {
        Period(start, first.from) :: periods
      }
  }
}

/**
 *
 * @param min period length, start date inclusive, end date exclusive.
 */
case class ShortStart(min: Int) extends StartRule {
  require(min > 0, "min start should be larger than 0")

  override def applyRule(start: Date, periods: List[Period]) = periods match {
    case Nil => Nil
    case first :: rest =>
      if (first.from - start >= min) {
        Period(start, first.from) :: periods
      } else {
        Period(start, first.to) :: rest
      }
  }
}