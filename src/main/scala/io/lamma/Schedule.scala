package io.lamma

import io.lamma.Schedule._

case class Schedule(periods: List[Period], dateDefs: List[DateDef]) {

  DateDef.validate(dateDefs)

  val dateDefNames = dateDefs.map(_.name)

  val tableHeaders = FromHeader :: ToHeader :: dateDefNames

  val generatedDates = periods.map {
    period =>
      (List.empty[Date] /: dateDefs) {
        (dates, dateDef) =>
          val populatedDefs = dateDefs.take(dates.size)
          val populatedMap = (populatedDefs zip dates).map {
            case (dateDef, date) => dateDef.name -> date
          }.toMap
          dates :+ dateDef.populate(period, populatedMap)
      }
  }

  val dates = periods zip generatedDates map {
    case (period, generatedRow) => period.start :: period.end :: generatedRow
  }

  val table = DateTable(tableHeaders, dates)

  val printableString = table.printableString

  def apply(dateDefName: String) = {
    require(dateDefNames.contains(dateDefName), s"$dateDefName is not defined in the schedule. Dates defined: $dateDefNames")

    val idx = dateDefNames.indexOf(dateDefName)
    generatedDates.map(_(idx))
  }
}

object Schedule {
  val FromHeader = "From Date"

  val ToHeader = "To Date"

  /**
   * generate a full schedule based on input arguments <br>
   * <br>
   * For example: <br>
   *
   * <pre>
   * ||    Period | CouponDate | SettlementDate || <br>
   * ||         1 | 2014-03-30 |     2014-05-02 || <br>
   * ||         2 | 2014-06-02 |     2014-06-04 || <br>
   * </pre>
   *
   * @param start start date
   * @param end end date
   * @param pattern recurrence pattern. Determine how anchor dates will be generated
   * @param periodBuilder how do we build periods (rows) based on the result of recurrence pattern
   * @param dateDefs a list of date definitions used to generate Date. In this case List(CouponDate, SettlementDate)
   * @return generated schedule
   */
  def apply(start: Date,
            end: Date,
            pattern: Pattern,
            periodBuilder: PeriodBuilder = StubRulePeriodBuilder(),
            dateDefs: List[DateDef] = Nil,
            direction: Direction = Direction.FORWARD) = {
    require(start <= end, s"start date $start must be on or before end date $end")

    val end0 = start - 1  // last period end day (period 0)

    val recurringDates = direction match {
      case Direction.FORWARD => pattern.recur(end0, end)
      case Direction.BACKWARD => pattern.recur(end, end0).reverse
    }

    val periodEndDays = recurringDates match {
      case Nil => Nil
      case `end0` :: endDays => endDays
      case endDays => endDays
    }
    val periods = periodBuilder.build(start, end, periodEndDays)
    new Schedule(periods, dateDefs)
  }
}
