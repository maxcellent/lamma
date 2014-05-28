package com.lamma4s

import org.scalatest.{Matchers, WordSpec}
import com.lamma4s.Frequency._
import com.lamma4s.PositionOfMonth.LastWeekdayOfMonth
import com.lamma4s.Weekday.{Tuesday, Friday}
import com.lamma4s.PositionOfYear.NthWeekdayOfYear

class FrequencySpec extends WordSpec with Matchers {

  "genForward" should {
    "generate empty schedule if start date is earlier than end date" in {
      Frequency.genForward(Date(2014, 4, 15), Date(2014, 4, 10), 1) should be('empty)
    }

    "generate schedule properly" in {
      val expected = Date(2014, 4, 15) :: Date(2014, 4, 22) :: Date(2014, 4, 29) :: Nil
      Frequency.genForward(Date(2014, 4, 15), Date(2014, 4, 30), 7) should be(expected)
    }
  }

  "genBackward" should {
    "generate empty schedule if start date is earlier than end date" in {
      Frequency.genBackward(Date(2014, 4, 15), Date(2014, 4, 10), 1) should be('empty)
    }

    "generate schedule properly" in {
      val expected = Date(2014, 4, 16) :: Date(2014, 4, 23) :: Date(2014, 4, 30) :: Nil
      Frequency.genBackward(Date(2014, 4, 15), Date(2014, 4, 30), 7) should be(expected)
    }
  }

  "DailyForward" should {
    "generate schedule properly" in {
      val expected = Date(2014, 4, 10) :: Date(2014, 4, 13) :: Date(2014, 4, 16) :: Date(2014, 4, 19) :: Nil
      DailyForward(3).generate(Date(2014, 4, 10), Date(2014, 4, 20)) should be (expected)
    }
  }

  "DailyBackward" should {
    "generate schedule properly" in {
      val expected = Date(2014, 4, 11) :: Date(2014, 4, 14) :: Date(2014, 4, 17) :: Date(2014, 4, 20) :: Nil
      DailyBackward(3).generate(Date(2014, 4, 10), Date(2014, 4, 20)) should be (expected)
    }
  }

  "WeeklyForward" should {
    "generate schedule if weekday is NOT defined" in {
      val expected = Date(2014, 4, 10) :: Date(2014, 4, 24) :: Nil
      WeeklyForward(2).generate(Date(2014, 4, 10), Date(2014, 5, 1)) should be(expected)
    }

    "generate schedule if weekday is already defined" in {
      val expected = Date(2014,4,11) :: Date(2014,4,25) :: Nil
      WeeklyForward(2, Some(Friday)).generate(Date(2014, 4, 10), Date(2014, 5, 1)) should be(expected)
    }
  }

  "WeeklyBackward" should {
    "generate schedule if weekday is NOT defined" in {
      val expected = Date(2014, 4, 16) :: Date(2014, 4, 30) :: Nil
      WeeklyBackward(2).generate(Date(2014, 4, 10), Date(2014, 4, 30)) should be(expected)
    }

    "generate schedule if weekday is already defined" in {
      val expected = Date(2014,4,18) :: Date(2014,5,2) :: Nil
      WeeklyBackward(2, Some(Friday)).generate(Date(2014, 4, 10), Date(2014, 5, 2)) should be(expected)
    }
  }

  "MonthlyForward" should {
    "generate schedule if position of month is NOT defined" in {
      val expected = Date(2013, 11, 30) :: Date(2014, 2, 28) :: Date(2014, 5, 30) :: Nil
      MonthlyForward(3).generate(Date(2013, 11, 30), Date(2014, 6, 30)) should be(expected)
    }

    "generate schedule if position of month is already defined" in {
      val expected = Date(2013,12,31) :: Date(2014,2,25) :: Date(2014,4,29) :: Date(2014,6,24) :: Nil
      val pom = LastWeekdayOfMonth(Tuesday)
      MonthlyForward(2, Some(pom)).generate(Date(2013, 12, 30), Date(2014, 8, 15)) should be(expected)
    }
  }

  "MonthlyBackward" should {
    "generate schedule if position of month is NOT defined" in {
      val expected = Date(2014, 2, 28) :: Date(2014, 4, 30) :: Date(2014, 6, 30) :: Nil
      MonthlyBackward(2).generate(Date(2014, 1, 30), Date(2014, 6, 30)) should be(expected)
    }

    "generate schedule if position of month is already defined" in {
      val expected = Date(2014,1,28) :: Date(2014,3,25) :: Date(2014,5,27) :: Date(2014,7,29) :: Nil
      val pom = LastWeekdayOfMonth(Tuesday)
      MonthlyBackward(2, Some(pom)).generate(Date(2013, 12, 30), Date(2014, 8, 15)) should be(expected)
    }
  }

  "YearlyForward" should {
    "generate schedule if position of year is NOT defined" in {
      val expected = Date(2010,10,1) :: Date(2012,10,1) :: Date(2014,10,1) :: Nil
      YearlyForward(2).generate(Date(2010, 10, 1), Date(2015, 5, 1)) should be(expected)
    }

    "generate schedule if position of year is already defined" in {
      val expected = Date(2011,1,14) :: Date(2013,1,11) :: Date(2015,1,9) :: Nil
      val poy = NthWeekdayOfYear(2, Friday)
      YearlyForward(2, Some(poy)).generate(Date(2010, 10, 1), Date(2015, 5, 1)) should be(expected)
    }
  }

  "YearlyBackward" should {
    "generate schedule if position of year is NOT defined" in {
      val expected = Date(2011,5,1) :: Date(2013,5,1) :: Date(2015,5,1) :: Nil
      YearlyBackward(2).generate(Date(2010, 10, 1), Date(2015, 5, 1)) should be(expected)
    }

    "generate schedule if position of year is already defined" in {
      val expected = Date(2013,1,11) :: Date(2015,1,9) :: Nil
      val poy = NthWeekdayOfYear(2, Friday)
      YearlyBackward(2, Some(poy)).generate(Date(2011, 2, 20), Date(2015, 5, 1)) should be(expected)
    }
  }
}
