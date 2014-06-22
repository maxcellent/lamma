package io.lamma

case class DateTable(headers: List[String], dates: List[List[Date]]) {

  require(dates.forall(_.size == headers.size))

  lazy val colCount = headers.size

  lazy val rowCount = dates.size

  lazy val rows = dates.zipWithIndex map {
    case (ds, i) => DateRow(i, ds)
  }

  lazy val allHeaders = "" :: headers   // header with first empty cell to print sequence

  /**
   * the fixed length column width based on header and date (ISO string) length
   */
  lazy val colWidths = allHeaders.map {
    header => math.max(header.size, Date.ISOStringLen)
  }

  def genLine(tokens: List[String]) = {
    val formattedTokens = colWidths zip tokens map {
      case (colWidth, token) => "%" + colWidth + "s" format token  // use format like %5s to format token
    }

    formattedTokens.mkString("||", " | ", " ||")  // keep paddings between columns
  }

  lazy val rowTokens = rows.map(_.strs)

  lazy val headerLine = genLine(allHeaders)

  lazy val rowLines = rowTokens.map(genLine)

  lazy val printableString = (headerLine :: rowLines).mkString("\n")

  lazy val headerLineHtml = allHeaders.mkString("<tr><th>", "</th><th>", "</th></tr>")

  lazy val rowLinesHtml = rowTokens.map { _.mkString("<tr><td>", "</td><td>", "</td></tr>") }
}

/**
 * @param i row index, starts from 0
 * @param dates dates of the row, starting from left
 */
case class DateRow(i: Int, dates: List[Date]) {
  lazy val strs = (i + 1).toString :: dates.map(_.toISOString)
}

/**
 * @param header column header
 * @param dates dates of the column, starting from top
 */
case class DateCol(header: String, dates: List[Date])