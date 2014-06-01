package io.lamma

trait StartRule {
  /**
   * apply start rule to periods
   *
   * @param start start date
   * @param nakedPeriods periods without taking start / end rules into consideration, contain at least one period
   * @return list periods with start rule applied
   */
  def applyRule(start: Date, nakedPeriods: List[Period]) = {
    require(nakedPeriods.size > 0)

    if (start < nakedPeriods.head.start) {
      doApplyRule(start, nakedPeriods)
    } else {
      nakedPeriods
    }
  }

  def doApplyRule(start: Date, nakedPeriods: List[Period]): List[Period]
}

object StartRule {

  val NoStartRule = LongStart(0)

  /**
   * @param maxOpt period length, start date inclusive, end date exclusive.
   *               if undefined then max will be first period * 1.5
   */
  case class LongStart(maxOpt: Option[Int] = None) extends StartRule {


    override def doApplyRule(start: Date, nakedPeriods: List[Period]) = nakedPeriods match {
      case Nil => Nil
      case first :: rest =>
        val max = maxOpt.map(_.toDouble).getOrElse(first.length * 1.5)
        if (first.end - start + 1 <= max) {
          Period(start, first.end) :: rest
        } else {
          Period(start, first.start - 1) :: nakedPeriods
        }
    }
  }

  object LongStart {
    def apply(max: Int): LongStart = LongStart(Some(max))
  }

  /**
   * @param minOpt minimal period length in order to separate a short start stub
   */
  case class ShortStart(minOpt: Option[Int] = None) extends StartRule {

    override def doApplyRule(start: Date, nakedPeriods: List[Period]) = nakedPeriods match {
      case Nil => Nil
      case first :: rest =>
        val min = minOpt.map(_.toDouble).getOrElse(first.length * 0.5)
        if (first.start - start >= min) {
          Period(start, first.start - 1) :: nakedPeriods
        } else {
          Period(start, first.end) :: rest
        }
    }
  }

  object ShortStart {
    def apply(min: Int): ShortStart = ShortStart(Some(min))
  }
}