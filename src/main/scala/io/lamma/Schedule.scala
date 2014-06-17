package io.lamma

import Schedule._

case class Schedule(periods: List[Period], dateDefs: List[DateDef]) {

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
}