package io.lamma

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class DateTableSpec extends WordSpec with Matchers {

  "init" should {
    "work with empty headers and empty body" in {
      DateTable(Nil, Nil)
    }

    "work with empty body" in {
      DateTable("Header 1" :: "Header 2" :: Nil, Nil)
    }

    "throw exception when header size does not match column size" in {
      intercept[IllegalArgumentException] {
        val headers = "Header 1" :: "Header 2" :: Nil
        val body = List(
          Date(2014, 5, 5) :: Nil,
          Date(2014, 10, 10) :: Nil
        )
        DateTable(headers, body)
      }
    }

    "throw exception when row sizes are different" in {
      intercept[IllegalArgumentException] {
        val headers = "Header 1" :: "Header 2" :: Nil
        val body = List(
          Date(2014, 5, 5) :: Date(2014, 8, 6) :: Nil,
          Date(2014, 10, 10) :: Nil
        )
        DateTable(headers, body)
      }
    }

    "populate colCount and rowCount properly" in {
      val headers = "Header 1" :: "Header 2" :: Nil
      val body = List(
        Date(2014, 5, 5) :: Date(2014, 8, 6) :: Nil,
        Date(2014, 10, 10) :: Date(2014, 7, 7) :: Nil,
        Date(2014, 10, 15) :: Date(2014, 12, 12) :: Nil
      )
      val table = DateTable(headers, body)
      table.colCount should be(2)
      table.rowCount should be(3)
    }
  }

  val headers = "Coupon" :: "SettlementDate" :: Nil
  val dates = List(
    Date(2014, 4, 30) :: Date(2014, 5, 2) :: Nil,
    Date(2014, 6, 2) :: Date(2014, 6, 4) :: Nil
  )
  val table = DateTable(headers, dates)

  "colWidths" should {
    "generate column width as well as first empty header" in {
      table.colWidths should be(10 :: 10 :: 14 :: Nil)
    }
  }

  "headerLine" should {
    "be generated properly" in {
      table.headerLine should be("||           |     Coupon | SettlementDate ||")
    }
  }

  "headerLineHtml" should {
    "be generated properly" in {
      table.headerLineHtml should be("<tr><th></th><th>Coupon</th><th>SettlementDate</th></tr>")
    }
  }

  "rowLines" should {
    "be generated properly" in {
      val expected = List(
        "||         1 | 2014-04-30 |     2014-05-02 ||",
        "||         2 | 2014-06-02 |     2014-06-04 ||"
      )
      table.rowLines should be(expected)
    }
  }

  "rowLinesHtml" should {
    "be generated properly" in {
      val expected = List(
        "<tr><td>1</td><td>2014-04-30</td><td>2014-05-02</td></tr>",
        "<tr><td>2</td><td>2014-06-02</td><td>2014-06-04</td></tr>"
      )
      table.rowLinesHtml should be(expected)
    }
  }

  "printableString" should {
    "work" in {
      val expected =
        """
          |||           |     Coupon | SettlementDate ||
          |||         1 | 2014-04-30 |     2014-05-02 ||
          |||         2 | 2014-06-02 |     2014-06-04 ||
        """.stripMargin
      DateTable(headers, dates).printableString should be(expected.trim)
    }
  }
}
