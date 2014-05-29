package com.lamma4s

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
}

case class Row(dates: List[Date])

object Row {
  def apply(dates: Date*) = new Row(dates.toList)
}