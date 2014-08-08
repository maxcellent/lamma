package io.lamma

import Selectors._
import io.lamma.Selector._
import org.scalatest.{Matchers, FlatSpec}

class SelectorsSpec extends FlatSpec with Matchers {

  "sameDay" should "be the same as lamma version" in {
    SAME_DAY_SELECTOR should be(SameDay)
  }

  "newForwardSelector" should "be the same as lamma version" in {
    newForwardSelector() should be(Forward())
    newForwardSelector(HolidayRules.NO_HOLIDAY) should be(Forward(NoHoliday))
  }

  "newBackwardSelector" should "be the same as lamma version" in {
    newBackwardSelector() should be(Backward())
    newBackwardSelector(HolidayRules.NO_HOLIDAY) should be(Backward(NoHoliday))
  }

  "newModifiedFollowingSelector" should "be the same as lamma version" in {
    newModifiedFollowingSelector() should be(ModifiedFollowing())
    newModifiedFollowingSelector(HolidayRules.NO_HOLIDAY) should be(ModifiedFollowing(NoHoliday))
  }

  "newModifiedPrecedingSelector" should "be the same as lamma version" in {
    newModifiedPrecedingSelector() should be(ModifiedPreceding())
    newModifiedPrecedingSelector(HolidayRules.NO_HOLIDAY) should be(ModifiedPreceding(NoHoliday))
  }
}
