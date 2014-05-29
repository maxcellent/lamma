package com.lamma4s

import org.scalatest.{Matchers, WordSpec}

class StartStubSpec extends WordSpec with Matchers {

  "LongStart stub rule" should {
    "return Nil when input periods are empty" in {
      LongStart(10).applyRule(Date(2014, 5, 1), Nil) should be('empty)
    }

    "prepend new period if the merged period is too long" in {
      val start = Date(2014, 6, 4)
      val input = Period((2014, 6, 10) -> (2014, 6, 20)) :: Period((2014, 6, 20) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 10)) :: input
      LongStart(15).applyRule(start, input) should be(expected)
    }

    "merge to first period if the merged period is not too long" in {
      val start = Date(2014, 6, 5)
      val input = Period((2014, 6, 10) -> (2014, 6, 20)) :: Period((2014, 6, 20) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 20)) :: Period((2014, 6, 20) -> (2014, 6, 30)) :: Nil
      LongStart(15).applyRule(start, input) should be(expected)
    }
  }

  "ShortStart stub rule" should {
    "return Nil when input periods are empty" in {
      ShortStart(10).applyRule(Date(2014, 5, 1), Nil) should be('empty)
    }

    "prepend new period if the merged period is long enough" in {
      val start = Date(2014, 6, 5)
      val input = Period((2014, 6, 10) -> (2014, 6, 20)) :: Period((2014, 6, 20) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 10)) :: input
      ShortStart(5).applyRule(start, input) should be(expected)
    }

    "merge to first period if the merged period is not long enough" in {
      val start = Date(2014, 6, 6)
      val input = Period((2014, 6, 10) -> (2014, 6, 20)) :: Period((2014, 6, 20) -> (2014, 6, 30)) :: Nil
      val expected = Period(start, Date(2014, 6, 20)) :: Period((2014, 6, 20) -> (2014, 6, 30)) :: Nil
      ShortStart(5).applyRule(start, input) should be(expected)
    }
  }
}
