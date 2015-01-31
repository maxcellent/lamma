package io.lamma

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class DateRangeBuilderSpec extends WordSpec with Matchers {

  "on" should {
    "throw exception if dow is assigned to a day pattern" in {
      intercept[IllegalArgumentException] {
        val range = Date(2014, 5, 1) to Date(2014, 5, 5) by (5 days) on Tuesday
        range.toList
      }
    }

    "throw exception if dom is assigned to a day pattern" in {
      intercept[IllegalArgumentException] {
        val range = Date(2014, 5, 1) to Date(2014, 5, 5) by (5 days) on (3 rd Friday)
        range.toList
      }
    }

    "throw exception if dom is assigned to a week pattern" in {
      intercept[IllegalArgumentException] {
        val range = Date(2014, 5, 1) to Date(2014, 5, 5) by (5 weeks) on (3 rd Friday)
        range.toList
      }
    }

    "throw exception if doy is assigned to a day pattern" in {
      intercept[IllegalArgumentException] {
        val range = Date(2014, 5, 1) to Date(2014, 5, 5) by (5 day) on (3 rd Friday of February)
        range.toList
      }
    }

    "throw exception if doy is assigned to a week pattern" in {
      intercept[IllegalArgumentException] {
        val range = Date(2014, 5, 1) to Date(2014, 5, 5) by (5 weeks) on (3 rd Friday of February)
        range.toList
      }
    }

    "throw exception if doy is assigned to a month pattern" in {
      intercept[IllegalArgumentException] {
        val range = Date(2014, 5, 1) to Date(2014, 5, 5) by (5 months) on (3 rd Friday of February)
        range.toList
      }
    }
  }

}
