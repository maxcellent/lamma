package io.lamma

/**
 * how we are going to shift relative to the anchor date
 */
trait Shifter

object Shifter {
  case object NoShift extends Shifter

  case class FutureDay(n: Int) extends Shifter {
    require(n > 0)
  }

  case class PastDay(n: Int) extends Shifter {
    require(n > 0)
  }

  case class FutureBizDay(n: Int) extends Shifter {
    require(n > 0)
  }

  case class PastBizDay(n: Int) extends Shifter {
    require(n > 0)
  }

  def shift(d: Date, shifter: Shifter, cal: Calendar) = shifter match {
    case NoShift => d
    case FutureDay(n) => d + n
    case PastDay(n) => d - n
    case FutureBizDay(n) => cal.shiftBizDay(d, n)
    case PastBizDay(n) => cal.shiftBizDay(d, -n)
  }
}
