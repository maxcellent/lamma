package io.lamma

import com.google.common.collect.Sets
import org.scalatest.{FlatSpec, Matchers}
import HolidayRules._

class HolidayRulesSpec extends FlatSpec with Matchers {

  "noHoliday" should "be the same as lamma version" in {
    NO_HOLIDAY should be(NoHoliday)
  }

  "Weekends" should "be the same as lamma version" in {
    WEEKENDS should be(Weekends)
  }

  "simpeHolidayRule" should "be the same as lamma version" in {
    val expected = SimpleHolidayRule(Date(2014, 5, 1), Date(2014, 5, 2))
    newSimpleHolidayRule(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 2)) should be(expected)
    newSimpleHolidayRule(Sets.newHashSet(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 2))) should be(expected)
  }

  "compositeHolidayRules" should "be the same as lamma version" in {
    newCompositeHolidayRule(NO_HOLIDAY, WEEKENDS) should be(CompositeHolidayRule(NoHoliday, Weekends))
  }

}
