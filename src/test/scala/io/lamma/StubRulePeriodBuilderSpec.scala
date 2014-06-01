package io.lamma

import org.scalatest.{WordSpec, Matchers}
import io.lamma.StubRulePeriodBuilder._

class StubRulePeriodBuilderSpec extends WordSpec with Matchers {

  "LongStart stub rule" should {
    "prepend new period if the merged period is too long when max is specified" in {
      val start = Date(2014, 6, 5)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: first :: second :: Nil

      LongStart1(15).start(start, first, second) should be(expected)
    }

    "merge to first period if the merged period is not too long when max is specified" in {
      val start = Date(2014, 6, 6)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: second :: Nil

      LongStart1(15).start(start, first, second) should be(expected)
    }

    "prepend new period if the merged period is too long when max is unspecified" in {
      val start = Date(2014, 6, 5)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: first :: second :: Nil

      LongStart1().start(start, first, second) should be(expected)
    }

    "merge to first period if the merged period is not too long when max is unspecified" in {
      val start = Date(2014, 6, 6)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: second :: Nil

      LongStart1().start(start, first, second) should be(expected)
    }
  }

  "ShortStart stub rule" should {
    "prepend new period if the merged period is long enough when min is specified" in {
      val start = Date(2014, 6, 6)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: first :: second :: Nil

      ShortStart1(5).start(start, first, second) should be(expected)
    }

    "merge to first period if the merged period is not long enough when min is specified" in {
      val start = Date(2014, 6, 7)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: second :: Nil

      ShortStart1(5).start(start, first, second) should be(expected)
    }

    "prepend new period if the merged period is long enough when min is unspecified" in {
      val start = Date(2014, 6, 6)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: first :: second :: Nil

      ShortStart1().start(start, first, second) should be(expected)
    }

    "merge to first period if the merged period is not long enough when min is unspecified" in {
      val start = Date(2014, 6, 7)
      val first = Date(2014, 6, 11)
      val second = Date(2014, 6, 21)
      val expected = start :: second :: Nil

      ShortStart1().start(start, first, second) should be(expected)
    }
  }

  "LongEnd stub rule" should {
    "append new period if the merged period is too long when max is specified" in {
      val secondLast = Date(2014, 6, 10)
      val last = Date(2014, 6, 20)
      val end = Date(2014, 6, 26)
      val expected = secondLast :: last :: end :: Nil
      LongEnd1(15).end(secondLast, last, end) should be(expected)
    }

    "merge to last period if the merged period is not too long when max is specified" in {
      val secondLast = Date(2014, 6, 10)
      val last = Date(2014, 6, 20)
      val end = Date(2014, 6, 25)
      val expected = secondLast :: end :: Nil
      LongEnd1(15).end(secondLast, last, end) should be(expected)
    }

    "append new period if the merged period is too long when max is unspecified" in {
      val secondLast = Date(2014, 6, 10)
      val last = Date(2014, 6, 20)
      val end = Date(2014, 6, 26)
      val expected = secondLast :: last :: end :: Nil
      LongEnd1().end(secondLast, last, end) should be(expected)
    }

    "merge to last period if the merged period is not too long when max is unspecified" in {
      val secondLast = Date(2014, 6, 10)
      val last = Date(2014, 6, 20)
      val end = Date(2014, 6, 25)
      val expected = secondLast :: end :: Nil
      LongEnd1().end(secondLast, last, end) should be(expected)
    }
  }

  "ShortEnd stub rule" should {
    "append new period if the merged period is long enough when min is specified" in {
      val secondLast = Date(2014, 6, 14)
      val last = Date(2014, 6, 24)
      val end = Date(2014, 6, 29)
      val expected = secondLast :: last :: end :: Nil
      ShortEnd1(5).end(secondLast, last, end) should be(expected)
    }

    "merge to last period if the merged period is not long enough when min is specified" in {
      val secondLast = Date(2014, 6, 14)
      val last = Date(2014, 6, 24)
      val end = Date(2014, 6, 28)
      val expected = secondLast :: end :: Nil
      ShortEnd1(5).end(secondLast, last, end) should be(expected)
    }

    "append new period if the merged period is long enough when min is unspecified" in {
      val secondLast = Date(2014, 6, 14)
      val last = Date(2014, 6, 24)
      val end = Date(2014, 6, 29)
      val expected = secondLast :: last :: end :: Nil
      ShortEnd1().end(secondLast, last, end) should be(expected)
    }

    "merge to last period if the merged period is not long enough when min is unspecified" in {
      val secondLast = Date(2014, 6, 14)
      val last = Date(2014, 6, 24)
      val end = Date(2014, 6, 28)
      val expected = secondLast :: end :: Nil
      ShortEnd1().end(secondLast, last, end) should be(expected)
    }
  }

  "build" should {
    "throw exception when start date is later than end date" in {
      intercept[IllegalArgumentException] {
        StubRulePeriodBuilder().build(Date(2014, 6, 1), Date(2014, 5, 1), Nil)
      }
    }

    "when period date list is empty then return single period from start to end" in {
      val expected = Period((2014, 6, 1) -> (2014, 7, 1)) :: Nil
      StubRulePeriodBuilder().build(Date(2014, 6, 1), Date(2014, 7, 1), Nil) should be(expected)
    }

    "throw exception when any date is earlier than start date" in {
      intercept[IllegalArgumentException] {
        val endDates = Date(2014, 5, 30) :: Nil
        StubRulePeriodBuilder().build(Date(2014, 6, 1), Date(2014, 7, 1), endDates)
      }
    }

    "throw exception when any date is later than end date" in {
      intercept[IllegalArgumentException] {
        val endDates = Date(2014, 7, 2) :: Nil
        StubRulePeriodBuilder().build(Date(2014, 6, 1), Date(2014, 7, 1), endDates)
      }
    }

    "when period date list only have one element" should {
      "return single period from start to end if the date equals to end day" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val expected = Period((2014, 6, 1) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder().build(start, end, end :: Nil) should be(expected)
      }

      "split to two period if NoStartRule and NoEndRule is used" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 20) :: Nil
        val expected = Period((2014, 6, 1) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(NoStartRule, NoEndRule).build(start, end, end :: anchors) should be(expected)
      }

      // ======= start rule always override end rule ======
      "LongStart always merge when max is big enough" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Nil
        val expected = Period((2014, 6, 1) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(LongStart1(30), LongEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(30), LongEnd1(25)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(30), ShortEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(30), ShortEnd1(25)).build(start, end, anchors) should be(expected)
      }

      "LongStart always append when max is NOT bit enough" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Nil
        val expected = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(LongStart1(29), LongEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(29), LongEnd1(25)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(29), ShortEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(29), ShortEnd1(25)).build(start, end, anchors) should be(expected)
      }

      "ShortStart always merge when min is too big" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Nil
        val expected = Period((2014, 6, 1) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(ShortStart1(11), LongEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(11), LongEnd1(25)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(11), ShortEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(11), ShortEnd1(25)).build(start, end, anchors) should be(expected)
      }

      "ShortStart always append when min is small enough" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Nil
        val expected = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(ShortStart1(10), LongEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(10), LongEnd1(25)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(10), ShortEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(10), ShortEnd1(25)).build(start, end, anchors) should be(expected)
      }
    }

    "when period date list has two element" should {
      "split to two period if NoStartRule and NoEndRule is used" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Date(2014, 6, 20) :: Nil
        val expected = Period((2014, 6, 1) ->(2014, 6, 10)) :: Period((2014, 6, 11) ->(2014, 6, 20)) :: Period((2014, 6, 21) ->(2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(NoStartRule, NoEndRule).build(start, end, end :: anchors) should be(expected)
      }

      "LongStart should override end rule when successfully merged" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Date(2014, 6, 20) :: Nil
        val expected = Period((2014, 6, 1) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(LongStart1(20), LongEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(20), LongEnd1(25)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(20), ShortEnd1(5)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(LongStart1(20), ShortEnd1(15)).build(start, end, anchors) should be(expected)
      }

      "EndRule should take effect when long start rule does not merge" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Date(2014, 6, 20) :: Nil
        val expectMerged = Period((2014, 6, 1) ->(2014, 6, 10)) :: Period((2014, 6, 11) ->(2014, 6, 30)) :: Nil
        val expectAppended = Period((2014, 6, 1) ->(2014, 6, 10)) :: Period((2014, 6, 11) ->(2014, 6, 20)) :: Period((2014, 6, 21) ->(2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(LongStart1(19), LongEnd1(15)).build(start, end, anchors) should be(expectAppended)
        StubRulePeriodBuilder(LongStart1(19), LongEnd1(25)).build(start, end, anchors) should be(expectMerged)
        StubRulePeriodBuilder(LongStart1(19), ShortEnd1(5)).build(start, end, anchors) should be(expectAppended)
        StubRulePeriodBuilder(LongStart1(19), ShortEnd1(15)).build(start, end, anchors) should be(expectMerged)
      }

      "ShortStart should override end rule when successfully merged" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Date(2014, 6, 20) :: Nil
        val expected = Period((2014, 6, 1) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(ShortStart1(11), LongEnd1(15)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(11), LongEnd1(25)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(11), ShortEnd1(5)).build(start, end, anchors) should be(expected)
        StubRulePeriodBuilder(ShortStart1(11), ShortEnd1(15)).build(start, end, anchors) should be(expected)
      }

      "EndRule should take effect when short start rule does not merge" in {
        val start = Date(2014, 6, 1)
        val end = Date(2014, 6, 30)
        val anchors = Date(2014, 6, 10) :: Date(2014, 6, 20) :: Nil
        val expectMerged = Period((2014, 6, 1) ->(2014, 6, 10)) :: Period((2014, 6, 11) ->(2014, 6, 30)) :: Nil
        val expectAppended = Period((2014, 6, 1) ->(2014, 6, 10)) :: Period((2014, 6, 11) ->(2014, 6, 20)) :: Period((2014, 6, 21) ->(2014, 6, 30)) :: Nil
        StubRulePeriodBuilder(ShortStart1(10), LongEnd1(15)).build(start, end, anchors) should be(expectAppended)
        StubRulePeriodBuilder(ShortStart1(10), LongEnd1(25)).build(start, end, anchors) should be(expectMerged)
        StubRulePeriodBuilder(ShortStart1(10), ShortEnd1(5)).build(start, end, anchors) should be(expectAppended)
        StubRulePeriodBuilder(ShortStart1(10), ShortEnd1(15)).build(start, end, anchors) should be(expectMerged)
      }
    }

    "when period date list has three or more element, start and end rules are applied separately" in {
      val start = Date(2014, 6, 1)
      val end = Date(2014, 6, 20)
      val anchors = Date(2014, 6, 5) :: Date(2014, 6, 10) :: Date(2014, 6, 15) :: Nil
      val expected = Period((2014, 6, 1) ->(2014, 6, 10)) :: Period((2014, 6, 11) ->(2014, 6, 20)) :: Nil

      StubRulePeriodBuilder(LongStart1(10), LongEnd1(10)).build(start, end, anchors) should be(expected)
    }

    "when period date list has more than 3 element, start and end rules are applied separately" in {
      val start = Date(2014, 6, 1)
      val end = Date(2014, 6, 25)
      val anchors = Date(2014, 6, 5) :: Date(2014, 6, 10) :: Date(2014, 6, 15) :: Date(2014, 6, 20) :: Nil

      val expected = List(
        Period((2014, 6, 1) ->(2014, 6, 10)),
        Period((2014, 6, 11) ->(2014, 6, 15)),
        Period((2014, 6, 16) ->(2014, 6, 25))
      )

      StubRulePeriodBuilder(LongStart1(10), LongEnd1(10)).build(start, end, anchors) should be(expected)
    }
  }
}
