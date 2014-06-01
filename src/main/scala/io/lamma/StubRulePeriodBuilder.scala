package io.lamma

import StubRulePeriodBuilder._

/**
 * build periods based on stub rules
 *
 * @param startRule
 * @param endRule
 */
case class StubRulePeriodBuilder(startRule: StartRule1 = NoStartRule, endRule: EndRule1 = NoEndRule) extends PeriodBuilder {

  override def build(start: Date, end: Date, ends: List[Date]) = {
    require(start <= end)

    ends.sorted match {
      case Nil => Period(start, end) :: Nil
      case sorted =>
        val end0 = start - 1

        require(end0 <= ends.head)
        require(end >= ends.last)

        val stubbed = sorted.filterNot(_ == end0).filterNot(_ == end) match {
          case Nil => end0 :: end :: Nil
          case end1 :: Nil => startRule.start(end0, end1, end)    // start rule override end rule
          case end1 :: end2 :: Nil =>
            // try to use start rule, and then fallback to end rule if failed
            startRule.start(end0, end1, end2) match {
              case `end0` :: `end1` :: `end2` :: Nil => end0 :: endRule.end(end1, end2, end)
              case started => started :+ end // already started
            }
          case end1 :: end2 :: end3 :: Nil => startRule.start(end0, end1, end2).dropRight(1) ::: endRule.end(end2, end3, end)
          case (end1 :: end2 :: rest) :+ endx :+ endy => startRule.start(end0, end1, end2) ::: rest ::: endRule.end(endx, endy, end)
        }

        Period.fromDates(stubbed)
    }
  }
}

object StubRulePeriodBuilder {
  /**
   * always create a new start period, identical with LongStart(0)
   */
  val NoStartRule = LongStart1(0)

  sealed trait StartRule1 {
    def start(end0: Date, end1: Date, end2: Date): List[Date]
  }

  case class LongStart1(maxOpt: Option[Int] = None) extends StartRule1 {
    override def start(end0: Date, end1: Date, end2: Date) = {
      val max = maxOpt.map(_.toDouble).getOrElse( (end2 - end1) * 1.5 )
      if (end2 - end0 <= max) {
        end0 :: end2 :: Nil  // skip the first anchor date
      } else {
        end0 :: end1 :: end2 :: Nil // to long, keep first anchor date
      }
    }
  }

  object LongStart1 {
    def apply(max: Int): LongStart1 = LongStart1(Some(max))
  }

  case class ShortStart1(minOpt: Option[Int] = None) extends StartRule1 {
    override def start(end0: Date, end1: Date, end2: Date) = {
      val min = minOpt.map(_.toDouble).getOrElse( (end2 - end1) * 0.5 )
      if (end1 - end0 >= min) {
        end0 :: end1 :: end2 :: Nil // long enough to short start
      } else {
        end0 :: end2 :: Nil // not long enough, have to merge
      }
    }
  }

  object ShortStart1 {
    def apply(min: Int): ShortStart1 = ShortStart1(Some(min))
  }

  /**
   * always create a new end period, identical with LongEnd(0)
   */
  val NoEndRule = LongEnd1(0)

  sealed trait EndRule1 {
    def end(endx: Date, endy: Date, endz: Date): List[Date]
  }

  case class LongEnd1(maxOpt: Option[Int] = None) extends EndRule1 {
    override def end(endx: Date, endy: Date, endz: Date) = {
      val max = maxOpt.map(_.toDouble).getOrElse( (endy - endx) * 1.5)
      if (endz - endx <= max) {
        endx :: endz :: Nil // merge to the last period
      } else {
        endx :: endy :: endz :: Nil // create a new period
      }
    }
  }

  object LongEnd1 {
    def apply(max: Int): LongEnd1 = LongEnd1(Some(max))
  }

  case class ShortEnd1(minOpt: Option[Int] = None) extends EndRule1 {
    override def end(endx: Date, endy: Date, endz: Date) = {
      val min = minOpt.map(_.toDouble).getOrElse( (endy - endx) * 0.5)
      if (endz - endy >= min) {
        endx :: endy :: endz :: Nil // long enough to short end
      } else {
        endx :: endz :: Nil // merge to the last period
      }
    }
  }

  object ShortEnd1 {
    def apply(min: Int): ShortEnd1 = ShortEnd1(Some(min))
  }
}
