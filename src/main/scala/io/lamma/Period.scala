package io.lamma

case class Period(from: Date, to: Date) {
  require(from <= to)
}

object Period {

  def apply(input: ((Int, Int, Int), (Int, Int, Int))) = {
    val ((fy, fm, fd), (ty, tm, td)) = input
    new Period(Date(fy, fm, fd), Date(ty, tm, td))
  }

  def fromDates(dates: List[Date]) = dates match {
    case Nil => Nil
    case dts =>
      (dts.dropRight(1) zip dts.drop(1)) map {
        case (from, to) => Period(from + 1, to)
      }
  }
}
