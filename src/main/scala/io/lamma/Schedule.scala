package io.lamma

import Schedule._

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

  def apply(start: Date,
            end: Date,
            pattern: Pattern,
            periodBuilder: PeriodBuilder = StubRulePeriodBuilder(),
            dateDefs: List[DateDef] = Nil) = {
    require(start <= end, s"start date $start must be on or before end date $end")

    val end0 = start - 1  // last period end day (period 0)
    val periodEndDays = pattern.recur(end0, end) match {
      case Nil => Nil
      case `end0` :: endDays => endDays
      case endDays => endDays
    }
    val periods = periodBuilder.build(start, end, periodEndDays)
    new Schedule(periods, dateDefs)
  }
}