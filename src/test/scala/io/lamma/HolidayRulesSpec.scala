package io.lamma

import com.google.common.collect.Sets
import org.scalatest.{FlatSpec, Matchers}
import HolidayRules._

class HolidayRulesSpec extends FlatSpec with Matchers {

  "noHoliday" should "be the same as lamma version" in {
    noHoliday() should be(NoHoliday)
  }

  "Weekends" should "be the same as lamma version" in {
    weekends() should be(Weekends)
  }

  "simpeHolidayRule" should "be the same as lamma version" in {
    val expected = SimpleHolidayRule(Date(2014, 5, 1), Date(2014, 5, 2))
    simpleRule(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 2)) should be(expected)
    simpleRule(Sets.newHashSet(Dates.newDate(2014, 5, 1), Dates.newDate(2014, 5, 2))) should be(expected)
  }

  "compositeHolidayRules" should "be the same as lamma version" in {
    compositeRule(noHoliday(), weekends()) should be(CompositeHolidayRule(NoHoliday, Weekends))
  }

}
