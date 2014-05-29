package com.lamma4s

import org.scalatest.{Matchers, WordSpec}

class EndStubSpec extends WordSpec with Matchers {

  "LongEnd stub rule" should {
    "return Nil when input periods are empty" in {
      LongEnd(10).applyRule(Date(2014, 5, 1), Nil) should be('empty)
    }

    "append new period if the merged period is too long" in {
      val end = Date(2014, 6, 27)
      val input = Period((2014, 6, 1) -> (2014, 6, 11)) :: Period((2014, 6, 11) -> (2014, 6, 21)) :: Nil
      val expected = input :+ Period(Date(2014, 6, 21), end)
      LongEnd(15).applyRule(end, input) should be(expected)
    }

    "merge to last period if the merged period is not too long" in {
      val end = Date(2014, 6, 26)
      val input = Period((2014, 6, 1) -> (2014, 6, 11)) :: Period((2014, 6, 11) -> (2014, 6, 21)) :: Nil
      val expected = Period((2014, 6, 1) -> (2014, 6, 11)) :: Period((2014, 6, 11) -> (2014, 6, 26)) :: Nil
      LongEnd(15).applyRule(end, input) should be(expected)
    }
  }

  "ShortEnd stub rule" should {
    "return Nil when input periods are empty" in {
      ShortEnd(10).applyRule(Date(2014, 5, 1), Nil) should be('empty)
    }

    "append new period if the merged period is long enough" in {
      val end = Date(2014, 6, 30)
      val input = Period((2014, 6, 5) -> (2014, 6, 15)) :: Period((2014, 6, 15) -> (2014, 6, 25)) :: Nil
      val expected = input :+ Period(Date(2014, 6, 25), end)
      ShortEnd(5).applyRule(end, input) should be(expected)
    }

    "merge to last period if the merged period is not long enough" in {
      val end = Date(2014, 6, 29)
      val input = Period((2014, 6, 5) -> (2014, 6, 15)) :: Period((2014, 6, 15) -> (2014, 6, 25)) :: Nil
      val expected = Period((2014, 6, 5) -> (2014, 6, 15)) :: Period(Date(2014, 6, 15), end) :: Nil
      ShortEnd(5).applyRule(end, input) should be(expected)
    }
  }
}
