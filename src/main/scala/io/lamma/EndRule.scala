package io.lamma

trait EndRule {
  /**
   * apply end rule to naked periods
   *
   * @param end end date
   * @param nakedPeriods periods without taking start / end rules into consideration, contain at least one period
   * @return list periods with start rule applied
   */
  def applyRule(end: Date, nakedPeriods: List[Period]) = {
    require(nakedPeriods.size > 0)

    if (end > nakedPeriods.last.end) {
      doApplyRule(end, nakedPeriods)
    } else {
      nakedPeriods
    }
  }

  def doApplyRule(end: Date, nakedPeriods: List[Period]): List[Period]
}

object EndRule {

  val NoEndRule = LongEnd(0)

  case class LongEnd(maxOpt: Option[Int] = None) extends EndRule {

    override def doApplyRule(end: Date, nakedPeriods: List[Period]) = nakedPeriods match {
      case Nil => Nil
      case rest :+ last =>
        val max = maxOpt.map(_.toDouble).getOrElse(last.length * 1.5)
        if (end - last.start + 1 <= max) {
          rest :+ Period(last.start, end)
        } else {
          nakedPeriods :+ Period(last.end + 1, end)
        }
    }
  }

  object LongEnd {
    def apply(max: Int): LongEnd = LongEnd(Some(max))
  }

  case class ShortEnd(minOpt: Option[Int] = None) extends EndRule {

    override def doApplyRule(end: Date, nakedPeriods: List[Period]) = nakedPeriods match {
      case Nil => Nil
      case rest :+ last =>
        val min = minOpt.map(_.toDouble).getOrElse(last.length * 0.5)
        if (end - last.end >= min) {
          nakedPeriods :+ Period(last.end + 1, end)
        } else {
          rest :+ Period(last.start, end)
        }
    }
  }

  object ShortEnd {
    def apply(min: Int): ShortEnd = ShortEnd(Some(min))
  }
}
