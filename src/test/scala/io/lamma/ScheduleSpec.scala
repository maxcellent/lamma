package io.lamma

import io.lamma.Anchor.{OtherDate, PeriodEnd}
import io.lamma.Selector.Forward
import io.lamma.Shifter.{NoShift, ShiftWorkingDays}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{FlatSpec, Matchers}

@RunWith(classOf[JUnitRunner])
class ScheduleSpec extends FlatSpec with Matchers {

  val couponDef = DateDef("CouponDate", PeriodEnd, NoShift, Forward())
  val settlementDef = DateDef("SettlementDate", OtherDate("CouponDate"), ShiftWorkingDays(2), Forward())
  val defs = couponDef :: settlementDef :: Nil
  val periods = Period((2014, 4, 1) -> (2014, 4, 30)) :: Period((2014, 5, 1) -> (2014, 5, 31)) :: Nil
  val schedule = Schedule(periods, defs)

  "rows" should "be empty when input is empty" in {
    Schedule(Nil, defs).generatedDates should be('empty)
  }

  "dates" should "be generated properly" in {
    val expected = List(Date(2014, 4, 30), Date(2014, 5, 2)) :: List(Date(2014, 6, 2), Date(2014, 6, 4)) :: Nil
    schedule.generatedDates should be(expected)
  }

  "tableHeaders" should "be generated properly" in {
    val expected = "From Date" :: "To Date" :: "CouponDate" :: "SettlementDate" :: Nil
    schedule.tableHeaders should be(expected)
  }

  "apply" should "throw exception when load undefined def" in {
    intercept[IllegalArgumentException] {
      schedule("SomeDate")
    }
  }

  "apply" should "return a list of dates" in {
    val expected = Date(2014, 5, 2) :: Date(2014, 6, 4) :: Nil
    schedule("SettlementDate") should be(expected)
  }
}
