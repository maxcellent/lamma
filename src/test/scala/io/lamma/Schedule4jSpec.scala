package io.lamma

import org.scalatest.{FlatSpec, Matchers}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import io.lamma.Anchor.{OtherDate, PeriodEnd}
import io.lamma.Shifter.{ShiftWorkingDays, NoShift}
import io.lamma.Selector.Forward
import com.google.common.collect.Lists

@RunWith(classOf[JUnitRunner])
class Schedule4jSpec extends FlatSpec with Matchers {

   val couponDef = DateDef("CouponDate", PeriodEnd, NoShift, Forward())
   val settlementDef = DateDef("SettlementDate", OtherDate("CouponDate"), ShiftWorkingDays(2), Forward())
   val defs = couponDef :: settlementDef :: Nil
   val periods = Period((2014, 4, 1) -> (2014, 4, 30)) :: Period((2014, 5, 1) -> (2014, 5, 31)) :: Nil
   val schedule = Schedule(periods, defs)
   val schedule4j = new Schedule4j(schedule)

   "getPeriods" should "return period in java list" in {
     val expected = Lists.newArrayList(Period((2014, 4, 1) -> (2014, 4, 30)), Period((2014, 5, 1) -> (2014, 5, 31)))
     schedule4j.getPeriods should be(expected)
   }

   "get" should "throw exception when date is not defined" in {
     intercept[IllegalArgumentException] {
       schedule4j.get("CouponDate1")
     }
   }

   "get" should "return date in java list" in {
     val expected = Lists.newArrayList(Date(2014, 4, 30), Date(2014, 6, 2))    // Forward to next working day
     schedule4j.get("CouponDate") should be(expected)
   }
 }
