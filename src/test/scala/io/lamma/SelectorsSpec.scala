package io.lamma

import Selectors._
import io.lamma.Selector._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}

@RunWith(classOf[JUnitRunner])
class SelectorsSpec extends FlatSpec with Matchers {

  "sameDay" should "be the same as lamma version" in {
    SAME_DAY_SELECTOR should be(SameDay)
  }

  "forward" should "be the same as lamma version" in {
    forward() should be(Forward())
    forward(HolidayRules.noHoliday()) should be(Forward(NoHoliday))
  }

  "backward" should "be the same as lamma version" in {
    backward() should be(Backward())
    backward(HolidayRules.noHoliday()) should be(Backward(NoHoliday))
  }

  "modifiedFollowing" should "be the same as lamma version" in {
    modifiedFollowing() should be(ModifiedFollowing())
    modifiedFollowing(HolidayRules.noHoliday()) should be(ModifiedFollowing(NoHoliday))
  }

  "modifiedPreceding" should "be the same as lamma version" in {
    modifiedPreceding() should be(ModifiedPreceding())
    modifiedPreceding(HolidayRules.noHoliday()) should be(ModifiedPreceding(NoHoliday))
  }
}
