package io.lamma

import io.lamma.StubRulePeriodBuilders._
import io.lamma.StubRulePeriodBuilder.{ShortEnd, LongEnd, ShortStart, LongStart}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}

@RunWith(classOf[JUnitRunner])
class StubRulePeriodBuildersSpec extends FlatSpec with Matchers {

  "longStart" should "be the same as lamma version" in {
    Rules.longStart(5) should be(LongStart(5))
    Rules.longStart() should be(LongStart())
  }

  "shortStart" should "be the same as lamma version" in {
    Rules.shortStart(5) should be(ShortStart(5))
    Rules.shortStart() should be(ShortStart())
  }

  "longEnd" should "be the same as lamma version" in {
    Rules.longEnd(5) should be(LongEnd(5))
    Rules.longEnd() should be(LongEnd())
  }

  "shortEnd" should "be the same as lamma version" in {
    Rules.shortEnd(5) should be(ShortEnd(5))
    Rules.shortEnd() should be(ShortEnd())
  }

  "stubRulePeriodBuilder" should "be the same as lamma version" in {
    StubRulePeriodBuilders.noStartNoEnd() should be(StubRulePeriodBuilder())
    StubRulePeriodBuilders.of(Rules.shortStart(5)) should be(StubRulePeriodBuilder(startRule = ShortStart(5)))
    StubRulePeriodBuilders.of(Rules.longEnd(20)) should be(StubRulePeriodBuilder(endRule = LongEnd(20)))
    StubRulePeriodBuilders.of(Rules.shortStart(3), Rules.longEnd(6)) should be(StubRulePeriodBuilder(ShortStart(3), LongEnd(6)))
  }
}
