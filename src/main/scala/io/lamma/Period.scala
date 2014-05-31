package io.lamma

/**
 *
 * @param from from date inclusive
 * @param to to date inclusive
 */
case class Period(from: Date, to: Date) {
  require(from <= to)

  val length = to - from + 1
}

object Period {

  def apply(input: ((Int, Int, Int), (Int, Int, Int))) = {
    val (from, to) = Date.unpack(input)
    new Period(from, to)
  }

  def fromDates(dates: List[Date]) = dates match {
    case Nil => Nil
    case dts =>
      (dts.dropRight(1) zip dts.drop(1)) map {
        case (from, to) => Period(from + 1, to)
      }
  }
}
