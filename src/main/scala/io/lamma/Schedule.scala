package io.lamma

import Schedule._

case class Schedule(periods: List[Period], dateDefs: List[DateDef]) {

  val dateDefNames = dateDefs.map(_.name)

  val rows = periods.map {
    period =>
      val populatedDates = (List.empty[Date] /: dateDefs) {
        (dates, dateDef) =>
          val populatedDefs = dateDefs.take(dates.size)
          val populatedMap = (populatedDefs zip dates).map {
            case (dateDef, date) => dateDef.name -> date
          }.toMap
          dates :+ dateDef.populate(period, populatedMap)
      }

      DRow(populatedDates)
  }

  val tableHeaders = PeriodHeader :: FromHeader :: ToHeader :: dateDefNames

  val tableRows = rows.zip(periods).zipWithIndex.map {
    case ((row, p), index) =>
      (index + 1).toString :: p.start.toISOString :: p.end.toISOString :: row.dates.map(_.toISOString)
  }

  val printableString = Schedule.toPrintableString(tableHeaders, tableRows)

  def apply(dateDefName: String) = {
    require(dateDefNames.contains(dateDefName), s"$dateDefName is not defined in the schedule. Dates defined: $dateDefNames")

    val idx = dateDefNames.indexOf(dateDefName)
    rows.map(_.dates(idx))
  }
}

object Schedule {
  val PeriodHeader = "Period"
  val FromHeader = "From Date"
  val ToHeader = "To Date"

  def toPrintableString(headers: List[String], rows: List[List[String]]) = {
    require(rows.forall(_.size == headers.size))

    val colWidths = headers.map {
      header => math.max(header.size, Date.ISOStringLen)
    }

    def genLine(tokens: List[String]) = {
      val formattedTokens = colWidths zip tokens map {
        case (colWidth, token) => "%" + colWidth + "s" format token
      }

      formattedTokens.mkString("||", " | ", " ||")
    }

    (genLine(headers) :: rows.map(genLine)).mkString("\n")
  }
}

case class DRow(dates: List[Date])

object DRow {
  def apply(dates: Date*) = new DRow(dates.toList)

  def apply(yyyy: Int, mm: Int, dd: Int): DRow = DRow(Date(yyyy, mm, dd))
}