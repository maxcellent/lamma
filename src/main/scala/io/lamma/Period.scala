package io.lamma

/**
 *
 * @param start period start date inclusive
 * @param end period end date inclusive
 */
case class Period(start: Date, end: Date) {
  require(start <= end, s"period start day $start must be on or before end day $end")

  val length = end - start + 1
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
        case (from, to) => Period(start = from + 1, end = to)
      }
  }
}
