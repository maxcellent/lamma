package iolamma.demo

import io.lamma._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class RecurrenceSpec extends WordSpec with Matchers {

  "daily recurrence pattern should work" should {
    "by days" in {
      val expected = Date(2015, 5, 10) :: Date(2015, 5, 15) :: Date(2015, 5, 20) :: Date(2015, 5, 25) :: Nil
      val actual = Date(2015, 5, 10) to Date(2015, 5, 27) by (5 days)
      actual should be(expected)
    }

    "by days is inclusive" in {
      val expected = Date(2015, 5, 10) :: Date(2015, 5, 15) :: Date(2015, 5, 20) :: Nil
      val actual = Date(2015, 5, 10) to Date(2015, 5, 20) by (5 days)
      actual should be(expected)
    }

    "you can omit `days` keyword" in {
      val expected = Date(2015, 5, 10) :: Date(2015, 5, 15) :: Date(2015, 5, 20) :: Nil
      val actual = Date(2015, 5, 10) to Date(2015, 5, 20) by 5
      actual should be(expected)
    }

    "by (1 day) is the default behavior if you don't specify recurrenc pattern at all" in {
      val expected = Date(2015, 5, 10) :: Date(2015, 5, 11) :: Date(2015, 5, 12) :: Nil
      val actual = Date(2015, 5, 10) to Date(2015, 5, 12)
      actual should be(expected)
    }

    "by days can be negative, the result will be reverse chronological sorted as well" in {
      val expected = Date(2015, 5, 20) :: Date(2015, 5, 15) :: Date(2015, 5, 10) :: Nil
      val actual = Date(2015, 5, 20) to Date(2015, 5, 10) by -5
      actual should be(expected)
    }
  }
}
