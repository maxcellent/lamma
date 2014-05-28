package com.lamma4s

import org.scalatest.{Matchers, WordSpec}
import com.lamma4s.DFrequency.{BackwardWeekly, ForwardWeekly}
import com.lamma4s.Weekday.{Monday, Wednesday}

class DFrequencySpec extends WordSpec with Matchers {

  "forwardGen" should {
    "generate empty schedule if start date is earlier than end date" in {
      DFrequency.forwardGen(Date(2014, 4, 15), Date(2014, 4, 10), 1) should be('empty)
    }

    "generate schedule properly" in {
      val expected = Date(2014, 4, 15) :: Date(2014, 4, 22) :: Date(2014, 4, 29) :: Nil
      DFrequency.forwardGen(Date(2014, 4, 15), Date(2014, 4, 30), 7) should be(expected)
    }
  }

  "backwardGen" should {
    "generate empty schedule if start date is earlier than end date" in {
      DFrequency.backwardGen(Date(2014, 4, 15), Date(2014, 4, 10), 1) should be('empty)
    }

    "generate schedule properly" in {
      val expected = Date(2014, 4, 16) :: Date(2014, 4, 23) :: Date(2014, 4, 30) :: Nil
      DFrequency.backwardGen(Date(2014, 4, 15), Date(2014, 4, 30), 7) should be(expected)
    }
  }

  "ForwardWeekly frequency" should {
    "generate schedule properly when weekday is specified" in {
      val expected = Date(2014, 4, 16) :: Date(2014, 4, 30) :: Nil
      ForwardWeekly(2, Wednesday).generate(Date(2014, 4, 10), Date(2014, 4, 30)) should be(expected)
    }
  }

  "BackwardWeekly frequency" should {
    "generate schedule properly when weeday is specified" in {
      val expected = Date(2014, 4, 14) :: Date(2014, 4, 28) :: Nil
      BackwardWeekly(2, Monday).generate(Date(2014, 4, 10), Date(2014, 4, 30)) should be(expected)
    }
  }
}
