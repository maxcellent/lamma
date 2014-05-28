package com.lamma4s

case class Schedule(rows: List[Row]) {

}

case class Row(seq: Int, dt: Date, cols: List[Cell])

case class Cell(name: String, dt: Date)