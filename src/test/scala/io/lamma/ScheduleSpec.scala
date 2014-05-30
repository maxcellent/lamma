package io.lamma

import org.scalatest.{FlatSpec, Matchers}

class ScheduleSpec extends FlatSpec with Matchers {

  val couponDef = DateDef("CouponDate", PeriodEnd, NoShift, Forward)
  val settlementDef = DateDef("SettlementDate", OtherDateDef("CouponDate"), FutureBizDay(2), Forward)
  val defs = couponDef :: settlementDef :: Nil
  val periods = Period((2014, 3, 31) -> (2014, 4, 30)) :: Period((2014, 4, 30) -> (2014, 5, 31)) :: Nil
  val schedule = Schedule(periods, defs)

  "rows" should "be empty when input is empty" in {
    Schedule(Nil, defs).rows should be('empty)
  }

  "rows" should "be generated properly" in {
    val expected = Row(Date(2014, 4, 30), Date(2014, 5, 2)) :: Row(Date(2014, 6, 2), Date(2014, 6, 4)) :: Nil
    schedule.rows should be(expected)
  }

  "tableHeaders" should "be generated properly" in {
    val expected = "Period" :: "CouponDate" :: "SettlementDate" :: Nil
    schedule.tableHeaders should be(expected)
  }

  "tableRows" should "be generated properly" in {
    val expected = List("1", "2014-04-30", "2014-05-02") :: List("2", "2014-06-02", "2014-06-04") :: Nil
    schedule.tableRows should be(expected)
  }

  "toPrintableString" should "work" in {
    val header = "Period" :: "CouponDate" :: "SettlementDate" :: Nil
    val rows = List("1", "2014-04-30", "2014-05-02") :: List("2", "2014-06-02", "2014-06-04") :: Nil
    val expected =
      """
        ||    Period|CouponDate|SettlementDate|
        ||         1|2014-04-30|    2014-05-02|
        ||         2|2014-06-02|    2014-06-04|
      """.stripMargin
    Schedule.toPrintableString(header, rows) should be(expected.trim)
  }
}
