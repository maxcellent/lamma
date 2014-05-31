package io.lamma

import org.scalatest.{Matchers, WordSpec}
import io.lamma.EndRule.{NoEndRule, ShortEnd, LongEnd}

class EndRuleSpec extends WordSpec with Matchers {

  "NoEndRule" should {
    val periods = Period((2014, 4, 1) -> (2014, 4, 30)) :: Period((2014, 5, 1) -> (2014, 5, 31)) :: Nil

    "return original period if there is no end fraction" in {
      NoEndRule.applyRule(Date(2014, 5, 31), periods) should be(periods)
    }

    "add new period if there is a fraction" in {
      val end = Date(2014, 6, 10)
      val expected = periods :+ Period((2014, 6, 1) -> (2014, 6, 10))
      NoEndRule.applyRule(end, periods) should be(expected)
    }
  }

  "LongEnd stub rule" should {
    "append new period if the merged period is too long when max is specified" in {
      val end = Date(2014, 6, 27)
      val input = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 20)) :: Nil
      val expected = input :+ Period(Date(2014, 6, 21), end)
      LongEnd(15).applyRule(end, input) should be(expected)
    }

    "merge to last period if the merged period is not too long when max is specified" in {
      val end = Date(2014, 6, 25)
      val input = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 20)) :: Nil
      val expected = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 25)) :: Nil
      LongEnd(15).applyRule(end, input) should be(expected)
    }

    "append new period if the merged period is too long when max is unspecified" in {
      val end = Date(2014, 6, 27)
      val input = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 20)) :: Nil
      val expected = input :+ Period(Date(2014, 6, 21), end)
      LongEnd().applyRule(end, input) should be(expected)
    }

    "merge to last period if the merged period is not too long when max is unspecified" in {
      val end = Date(2014, 6, 25)
      val input = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 20)) :: Nil
      val expected = Period((2014, 6, 1) -> (2014, 6, 10)) :: Period((2014, 6, 11) -> (2014, 6, 25)) :: Nil
      LongEnd().applyRule(end, input) should be(expected)
    }
  }

  "ShortEnd stub rule" should {
    "append new period if the merged period is long enough when min is specified" in {
      val end = Date(2014, 6, 29)
      val input = Period((2014, 6, 5) -> (2014, 6, 14)) :: Period((2014, 6, 15) -> (2014, 6, 24)) :: Nil
      val expected = input :+ Period(Date(2014, 6, 25), end)
      ShortEnd(5).applyRule(end, input) should be(expected)
    }

    "merge to last period if the merged period is not long enough when min is specified" in {
      val end = Date(2014, 6, 28)
      val input = Period((2014, 6, 5) -> (2014, 6, 14)) :: Period((2014, 6, 15) -> (2014, 6, 24)) :: Nil
      val expected = Period((2014, 6, 5) -> (2014, 6, 14)) :: Period(Date(2014, 6, 15), end) :: Nil
      ShortEnd(5).applyRule(end, input) should be(expected)
    }

    "append new period if the merged period is long enough when min is unspecified" in {
      val end = Date(2014, 6, 29)
      val input = Period((2014, 6, 5) -> (2014, 6, 14)) :: Period((2014, 6, 15) -> (2014, 6, 24)) :: Nil
      val expected = input :+ Period(Date(2014, 6, 25), end)
      ShortEnd().applyRule(end, input) should be(expected)
    }

    "merge to last period if the merged period is not long enough when min is unspecified" in {
      val end = Date(2014, 6, 28)
      val input = Period((2014, 6, 5) -> (2014, 6, 14)) :: Period((2014, 6, 15) -> (2014, 6, 24)) :: Nil
      val expected = Period((2014, 6, 5) -> (2014, 6, 14)) :: Period(Date(2014, 6, 15), end) :: Nil
      ShortEnd().applyRule(end, input) should be(expected)
    }
  }
}
