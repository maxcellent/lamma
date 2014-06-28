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

  def apply(input: (Date, Date)) = new Period(input._1, input._2)

  /**
   * Create list of periods from period end days. <br>
   * <br>
   * <b>Note</b> this method expect first date to be period0 date, ie, 1 day before first period.
   * <br>
   * For example: List(2013-12-31, 2014-01-05, 2014-01-10)
   * will generate: Period(2014-01-01 -> 2014-01-05) and Period(2014-01-06 -> 2014-01-10)
   */
  def fromPeriodEndDays(dates: List[Date]) = dates match {
    case Nil => Nil
    case dts =>
      (dts.dropRight(1) zip dts.drop(1)) map {
        case (from, to) => Period(start = from + 1, end = to)
      }
  }
}
