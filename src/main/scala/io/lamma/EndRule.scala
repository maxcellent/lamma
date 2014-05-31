package io.lamma

trait EndRule {
  /**
   * apply end rule to periods
   */
  def applyRule(end: Date, periods: List[Period]): List[Period]
}

object EndRule {

  case object NoEndRule extends EndRule {
    override def applyRule(end: Date, periods: List[Period]) = periods
  }

  case class LongEnd(max: Int) extends EndRule {
    require(max > 0, "max end should be larger than 0")

    override def applyRule(end: Date, periods: List[Period]) = periods match {
      case Nil => Nil
      case rest :+ last =>
        if (end - last.from <= max) {
          rest :+ Period(last.from, end)
        } else {
          periods :+ Period(last.to, end)
        }
    }
  }

  case class ShortEnd(min: Int) extends EndRule {
    require(min > 0, "min end should be larger than 0")

    override def applyRule(end: Date, periods: List[Period]) = periods match {
      case Nil => Nil
      case rest :+ last =>
        if (end - last.to >= min) {
          periods :+ Period(last.to, end)
        } else {
          rest :+ Period(last.from, end)
        }
    }
  }

}
