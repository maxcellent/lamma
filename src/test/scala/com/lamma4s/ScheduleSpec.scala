package com.lamma4s

import org.scalatest.{FlatSpec, Matchers}

class ScheduleSpec extends FlatSpec with Matchers {

  "rows" should "be generated properly" in {
    val couponDef = DateDef("CouponDate", End, NoShift, Forward)
    val settlementDef = DateDef("SettlementDate", OtherDateDef("CouponDate"), FutureBizDay(2), Forward)
    val defs = couponDef :: settlementDef :: Nil
    val periods = Period((2014, 3, 31) -> (2014, 4, 30)) :: Period((2014, 4, 30) -> (2014, 5, 31)) :: Nil
    val expected = Row(Date(2014, 4, 30), Date(2014, 5, 2)) :: Row(Date(2014, 6, 2), Date(2014, 6, 4)) :: Nil

    Schedule(Nil, defs).rows should be('empty)
    Schedule(periods, defs).rows should be(expected)
  }

}
