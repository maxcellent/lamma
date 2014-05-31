package io.lamma

import org.scalatest.{Matchers, WordSpec}
import io.lamma.StartRule.{NoStartRule, ShortStart, LongStart}

class StartRuleSpec extends WordSpec with Matchers {

  "NoStartRule" should {
    val periods = Period((2014, 4, 1) -> (2014, 4, 30)) :: Period((2014, 5, 1) -> (2014, 5, 31)) :: Nil

    "return original period if there is no start fraction" in {
      NoStartRule.applyRule(Date(2014, 4, 1), periods) should be(periods)
    }

    "add new period if there is a fraction" in {
      val start = Date(2014, 3, 25)
      val expected = Period((2014, 3, 25) -> (2014, 3, 31)) :: periods
      NoStartRule.applyRule(start, periods) should be(expected)
    }
  }

  "LongStart stub rule" should {
    "prepend new period if the merged period is too long when max is specified" in {
      val start = Date(2014, 6, 4)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 10)) :: input
      LongStart(15).applyRule(start, input) should be(expected)
    }

    "merge to first period if the merged period is not too long when max is specified" in {
      val start = Date(2014, 6, 6)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      LongStart(15).applyRule(start, input) should be(expected)
    }

    "prepend new period if the merged period is too long when max is unspecified" in {
      val start = Date(2014, 6, 5)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 10)) :: input
      LongStart().applyRule(start, input) should be(expected)
    }

    "merge to first period if the merged period is not too long when max is unspecified" in {
      val start = Date(2014, 6, 6)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      LongStart().applyRule(start, input) should be(expected)
    }
  }

  "ShortStart stub rule" should {
    "prepend new period if the merged period is long enough when min is specified" in {
      val start = Date(2014, 6, 6)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 10)) :: input
      ShortStart(5).applyRule(start, input) should be(expected)
    }

    "merge to first period if the merged period is not long enough when min is specified" in {
      val start = Date(2014, 6, 7)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      ShortStart(5).applyRule(start, input) should be(expected)
    }

    "prepend new period if the merged period is long enough when min is unspecified" in {
      val start = Date(2014, 6, 6)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 10)) :: input
      ShortStart().applyRule(start, input) should be(expected)
    }

    "merge to first period if the merged period is not long enough when min is unspecified" in {
      val start = Date(2014, 6, 7)
      val input = Period((2014, 6, 11) -> (2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 20)) :: Period((2014, 6, 21) -> (2014, 6, 30)) :: Nil
      ShortStart().applyRule(start, input) should be(expected)
    }
  }
}
