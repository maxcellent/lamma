package io.lamma

import io.lamma.DayOfMonth.{LastDayOfMonth, NthWeekdayOfMonth}
import io.lamma.DayOfYear.{LastDayOfYear, NthMonthOfYear}
import io.lamma.Pattern.recur
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}

@RunWith(classOf[JUnitRunner])
class PatternSpec extends WordSpec with Matchers {

  "recur" should {
    "throw exception when step is 0" in {
      intercept[IllegalArgumentException] {
        recur(Date(2014, 5, 5), Date(2014, 5, 10), 0)
      }
    }

    "positive step" should {
      "return single date when from date is the same as to date" in {
        val expected = Date(2014, 5, 5) :: Nil
        recur(Date(2014, 5, 5), Date(2014, 5, 5), 1) should be(expected)
      }

      "return empty when from date is after as to date" in {
        recur(Date(2014, 5, 5), Date(2014, 5, 1), 1) should be('empty)
      }

      "return last day is last day is recurred" in {
        val expected = Date(2014, 5, 5) :: Date(2014, 5, 7) :: Date(2014, 5, 9) :: Nil
        recur(Date(2014, 5, 5), Date(2014, 5, 9), 2) should be(expected)
      }

      "return without last day is the last day is not recurred" in {
        val expected = Date(2014, 5, 5) :: Date(2014, 5, 7) :: Date(2014, 5, 9) :: Nil
        recur(Date(2014, 5, 5), Date(2014, 5, 10), 2) should be(expected)
      }
    }

    "negative step" should {
      "return empty when from date is the same as to date" in {
        val expected = Date(2014, 5, 5) :: Nil
        recur(Date(2014, 5, 5), Date(2014, 5, 5), -1) should be(expected)
      }

      "return empty when from date is before as to date" in {
        recur(Date(2014, 5, 5), Date(2014, 5, 10), -1) should be('empty)
      }

      "return last day is last day is recurred" in {
        val expected = Date(2014, 5, 9) :: Date(2014, 5, 7) :: Date(2014, 5, 5) :: Nil
        recur(Date(2014, 5, 9), Date(2014, 5, 5), -2) should be(expected)
      }

      "return without last day is the last day is not recurred" in {
        val expected = Date(2014, 5, 9) :: Date(2014, 5, 7) :: Date(2014, 5, 5) :: Nil
        recur(Date(2014, 5, 9), Date(2014, 5, 4), -2) should be(expected)
      }
    }
  }

  "Daily" should {
    "throw exception when step is 0" in {
      intercept[IllegalArgumentException] {
        Daily(0)
      }
    }

    "work" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 5, 12) :: Date(2014, 5, 14) :: Nil
      Daily(2).recur(Date(2014, 5, 10), Date(2014, 5, 15)) should be(expected)
    }
  }

  "Weekly" should {
    "throw exception when step is 0" in {
      intercept[IllegalArgumentException] {
        Daily(0)
      }
    }

    "construct object properly with apply function" in {
      Weekly(Wednesday) should be(Weekly(1, Wednesday))
      Weekly(5, Wednesday) should be(Weekly(5, Some(Wednesday)))
    }

    "adjustedFrom" should {
      "throw exception when step is 0" in {
        intercept[IllegalArgumentException] {
          Weekly.adjustedFrom(Date(2014, 7, 13), 0, Sunday)
        }
      }

      "find the next weekday when step is positive" in {
        Weekly.adjustedFrom(Date(2014, 6, 13), 1, Sunday) should be(Date(2014, 6, 15))
      }

      "find the previous weekday when step is negative" in {
        Weekly.adjustedFrom(Date(2014, 6, 13), -1, Sunday) should be(Date(2014, 6, 8))
      }

      "return from date when it already matches weekday" in {
        Weekly.adjustedFrom(Date(2014, 6, 15), 1, Sunday) should be(Date(2014, 6, 15))
        Weekly.adjustedFrom(Date(2014, 6, 15), -1, Sunday) should be(Date(2014, 6, 15))
      }
    }

    "work when no DOW is defined" in {
      val expected = Date(2014, 6, 15) :: Date(2014, 6, 22) :: Date(2014, 6, 29) :: Nil
      Weekly(1).recur(Date(2014, 6, 15), Date(2014, 6, 30)) should be(expected)
    }

    "work when DOW is defined" in {
      val expected = Date(2014, 6, 15) :: Date(2014, 6, 22) :: Date(2014, 6, 29) :: Nil
      Weekly(1, Sunday).recur(Date(2014, 6, 13), Date(2014, 6, 30)) should be(expected)
    }

    "work when creating with implicit conversions" in {
      (5 weeks) on Tuesday should be(Weekly(5, Tuesday))
    }
  }

  "Monthly" should {
    "throw exception when step is 0" in {
      intercept[IllegalArgumentException] {
        Daily(0)
      }
    }

    "construct object properly with apply function" in {
      Monthly(LastDayOfMonth) should be(Monthly(1, LastDayOfMonth))
      Monthly(5, LastDayOfMonth) should be(Monthly(5, Some(LastDayOfMonth)))
    }

    "adjustedFrom" should {
      "throw exception when step is 0" in {
        intercept[IllegalArgumentException] {
          Monthly.adjustedFrom(Date(2014, 7, 13), 0, LastDayOfMonth)
        }
      }

      "find the next dom when step is positive" in {
        Monthly.adjustedFrom(Date(2014, 6, 13), 1, LastDayOfMonth) should be(Date(2014, 6, 30))
      }

      "find the previous dom when step is negative" in {
        Monthly.adjustedFrom(Date(2014, 6, 13), -1, LastDayOfMonth) should be(Date(2014, 5, 31))
      }

      "return from date when it already matches dom" in {
        Monthly.adjustedFrom(Date(2014, 6, 30), 1, LastDayOfMonth) should be(Date(2014, 6, 30))
        Monthly.adjustedFrom(Date(2014, 6, 30), -1, LastDayOfMonth) should be(Date(2014, 6, 30))
      }
    }

    "work when step is positive" should {
      "work when no DOM is defined" in {
        val expected = Date(2015, 1, 30) :: Date(2015, 2, 28) :: Date(2015, 3, 30) :: Nil
        Monthly(1).recur(Date(2015, 1, 30), Date(2015, 4, 1)) should be(expected)
      }

      "work when DOM is already defined" in {
        val expected = Date(2015, 1, 31) :: Date(2015, 2, 28) :: Date(2015, 3, 31) :: Nil
        Monthly(1, LastDayOfMonth).recur(Date(2015, 1, 1), Date(2015, 4, 1)) should be(expected)
      }
    }

    "work when step is negative" should {
      "work when no DOM is defined" in {
        val expected = Date(2015, 3, 30) :: Date(2015, 2, 28) :: Date(2015, 1, 30) :: Nil
        Monthly(-1).recur(Date(2015, 3, 30), Date(2015, 1, 25)) should be(expected)
      }

      "work when DOM is already defined" in {
        val expected = Date(2015, 3, 31) :: Date(2015, 2, 28) :: Date(2015, 1, 31) :: Nil
        Monthly(-1, LastDayOfMonth).recur(Date(2015, 4, 1), Date(2015, 1, 1)) should be(expected)
      }
    }

    "work when creating with implicit conversions" in {
      (5 months) on (2 nd Tuesday) should be(Monthly(5, NthWeekdayOfMonth(2, Tuesday)))
    }
  }

  "Yearly" should {
    "throw exception when step is 0" in {
      intercept[IllegalArgumentException] {
        Daily(0)
      }
    }

    "construct object properly with apply function" in {
      Yearly(LastDayOfYear) should be(Yearly(1, Some(LastDayOfYear)))
      Yearly(5, LastDayOfYear) should be(Yearly(5, Some(LastDayOfYear)))
    }

    "adjustedFrom" should {
      "throw exception when step is 0" in {
        intercept[IllegalArgumentException] {
          Yearly.adjustedFrom(Date(2014, 7, 13), 0, LastDayOfYear)
        }
      }

      "find the next doy when step is positive" in {
        Yearly.adjustedFrom(Date(2014, 6, 13), 1, LastDayOfYear) should be(Date(2014, 12, 31))
      }

      "find the previous doy when step is negative" in {
        Yearly.adjustedFrom(Date(2014, 6, 13), -1, LastDayOfYear) should be(Date(2013, 12, 31))
      }

      "return from date when it already matches doy" in {
        Yearly.adjustedFrom(Date(2014, 12, 31), 1, LastDayOfYear) should be(Date(2014, 12, 31))
        Yearly.adjustedFrom(Date(2014, 12, 31), -1, LastDayOfYear) should be(Date(2014, 12, 31))
      }
    }

    "work when step is positive" should {
      "work when no DOY is defined" in {
        val expected = Date(2015, 1, 30) :: Date(2016, 1, 30) :: Date(2017, 1, 30) :: Nil
        Yearly(1).recur(Date(2015, 1, 30), Date(2017, 4, 1)) should be(expected)
      }

      "work when DOY is already defined" in {
        val expected = Date(2015, 12, 31) :: Date(2016, 12, 31) :: Nil
        Yearly(1, LastDayOfYear).recur(Date(2015, 1, 30), Date(2017, 4, 30)) should be(expected)
      }
    }

    "work when step is negative" should {
      "work when no DOY is defined" in {
        val expected = Date(2017, 4, 1) :: Date(2016, 4, 1) :: Date(2015, 4, 1) :: Nil
        Yearly(-1).recur(Date(2017, 4, 1), Date(2015, 1, 30)) should be(expected)
      }

      "work when DOY is already defined" in {
        val expected = Date(2016, 12, 31) :: Date(2015, 12, 31) :: Nil
        Yearly(-1, LastDayOfYear).recur(Date(2017, 4, 1), Date(2015, 1, 1)) should be(expected)
      }
    }

    "work when creating with implicit conversions" in {
      (2 year) on (2 nd Tuesday of February) should be(Yearly(2, NthMonthOfYear(February, NthWeekdayOfMonth(2, Tuesday))))
    }
  }
}
