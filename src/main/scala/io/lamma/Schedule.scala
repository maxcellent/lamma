package io.lamma

import Schedule._

case class Schedule(periods: List[Period], dateDefs: List[DateDef]) {

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

      Row(populatedDates)
  }

  val tableHeaders = PeriodHeader :: dateDefs.map(_.name)

  val tableRows = rows.zipWithIndex.map {
    case (row, index) => (index + 1).toString :: row.dates.map(_.toISOString)
  }

  val printableString = Schedule.toPrintableString(tableHeaders, tableRows)
}

object Schedule {
  val PeriodHeader = "Period"

  def toPrintableString(headers: List[String], rows: List[List[String]]) = {
    require(rows.forall(_.size == headers.size))

    val colWidths = headers.map {
      header => math.max(header.size, Date.ISOStringLen)
    }

    def genLine(tokens: List[String]) = {
      val formattedTokens = colWidths zip tokens map {
        case (colWidth, token) => "%" + colWidth + "s" format token
      }

      formattedTokens.mkString("|", "|", "|")
    }

    (genLine(headers) :: rows.map(genLine)).mkString("\n")
  }
}

case class Row(dates: List[Date])

object Row {
  def apply(dates: Date*) = new Row(dates.toList)
}