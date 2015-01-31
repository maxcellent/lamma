package io.lamma

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, FlatSpec}

@RunWith(classOf[JUnitRunner])
class LocatorsSpec extends FlatSpec with Matchers {

  "firstDay" should "work" in {
    Locators.firstDay().build() should be(1 st day)
  }

  "lastDay" should "work" in {
    Locators.lastDay().build() should be(lastDay)
  }

  "nthDay" should "work" in {
    Locators.nthDay(5).build() should be(5 th day)
  }

  "firstDay" should "work with month" in {
    Locators.firstDay().of(February).build() should be(1 st day of February)
  }

  "lastDay" should "work with month" in {
    Locators.lastDay().of(February).build() should be(lastDay of February)
  }

  "nthDay" should "work with month" in {
    Locators.nthDay(5).of(February).build() should be(5 th day of February)
  }

  "first weekday" should "work" in {
    Locators.first(Monday).build() should be(1 st Monday)
  }

  "last weekday" should "work" in {
    Locators.last(Monday).build() should be(lastMonday)
  }

  "nth weekday" should "work" in {
    Locators.nth(3, Monday).build() should be(3 rd Monday)
  }

  "first weekday" should "work with month" in {
    Locators.first(Monday).of(February).build() should be(1 st Monday of February)
  }

  "last weekday" should "work with month" in {
    Locators.last(Monday).of(February).build() should be(lastMonday of February)
  }

  "nth weekday" should "work with month" in {
    Locators.nth(3, Monday).of(February).build() should be(3 rd Monday of February)
  }

}
