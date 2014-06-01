package io.lamma

import org.scalatest.{Matchers, WordSpec}
import io.lamma.Recurrence._
import io.lamma.Weekday.{Wednesday, Friday, Tuesday}
import io.lamma.PositionOfMonth.{LastDayOfMonth, NthWeekdayOfMonth, NthDayOfMonth}
import io.lamma.Month.February
import io.lamma.PositionOfYear.{NthMonthOfYear, LastWeekdayOfYear}
import io.lamma.Shifter.{ShiftWorkingDays, ShiftCalendarDays}
import io.lamma.Selector.{ModifiedFollowing, Forward}
import io.lamma.Anchor.{OtherDateDef, PeriodEnd}
import io.lamma.StubRulePeriodBuilder.LongEnd

/**
 * this spec is written in tutorial order in order to verify and maintain everything used in tutorial
 * please don't reorder them
 *
 * this Spec is also used as functional test, because library users are always supposed to use Lamma.xxx
 */
class LammaSpec extends WordSpec with Matchers {

  "get started: generate a list if date" should {
    "generate date sequence" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 5, 11) :: Date(2014, 5, 12) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 12)) should be(expected)
    }

    "generate sequence by week" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 5, 17) :: Date(2014, 5, 24) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 30), EveryWeek) should be(expected)
    }

    "also, generate sequence by month" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 7, 10), EveryMonth) should be(expected)
    }

    "month end will be handled properly" in {
      val expected = Date(2014, 1, 31) :: Date(2014, 2, 28) :: Date(2014, 3, 31) :: Date(2014, 4, 30) :: Nil
      Lamma.sequence(Date(2014, 1, 31), Date(2014, 5, 1), EveryMonth) should be(expected)
    }

    "obviously, generate sequence by year" in {
      val expected = Date(2014, 5, 10) :: Date(2015, 5, 10) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2015, 7, 10), EveryYear) should be(expected)
    }

    "leap year is considered" in {
      val expected = Date(2012, 2, 29) :: Date(2013, 2, 28) :: Date(2014, 2, 28) :: Date(2015, 2, 28) :: Date(2016, 2, 29) :: Nil
      Lamma.sequence(Date(2012, 2, 29), Date(2016, 3, 1), EveryYear) should be(expected)
    }

    "not surprisingly, your can customize durations, for example, generate a date sequence every 3 days" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 5, 13) :: Date(2014, 5, 16) :: Date(2014, 5, 19) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 5, 20), Days(3)) should be(expected)
    }

    "and Weeks / Months / Years, for example" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 8, 10) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 10, 20), Months(3)) should be(expected)
    }

    "select all leap days" in {
      val expected = Date(2012, 2, 29) :: Date(2016, 2, 29) :: Date(2020, 2, 29) :: Nil
      Lamma.sequence(Date(2012, 2, 29), Date(2020, 2, 29), Years(4)) should be(expected)
    }

    "working day generation is also supported, in this case WeekendCalendar is used which means all weekend will be skipped" in {
      val expected = Date(2015, 10, 5) :: Date(2015, 10, 12) :: Date(2015, 10, 19) :: Nil
      Lamma.sequence(Date(2015, 10, 5), Date(2015, 10, 20), Days.workingDay(5, WeekendCalendar)) should be(expected)
    }

    "you can also generate date in backward direction, for example, use our last example" in {
      val expected = Date(2014, 7, 20) :: Date(2014, 10, 20) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 10, 20), MonthsBackward(3)) should be(expected)
    }

    "you can specify weekday when generating with Weeks or WeeksBackward" in {
      // a list of every 3 Tuesday from 2014-05-05 to 2014-07-01
      val expected = Date(2014, 5, 13) :: Date(2014, 6, 3) :: Date(2014, 6, 24) :: Nil
      Lamma.sequence(Date(2014, 5, 10), Date(2014, 7, 1), Weeks(3, Tuesday)) should be(expected)
    }

    "and for month, we have a similar concept for Monthly called PositionOfMonth" in {
      val expected = Date(2014, 5, 10) :: Date(2014, 6, 10) :: Date(2014, 7, 10) :: Nil
      Lamma.sequence(Date(2014, 5, 1), Date(2014, 7, 30), Months(1, NthDayOfMonth(10))) should be(expected)
    }

    "you can also select weekday inside a month" in {
      val expected = Date(2014, 5, 9) :: Date(2014, 7, 11) :: Date(2014, 9, 12) :: Nil
      Lamma.sequence(Date(2014, 5, 1), Date(2014, 9, 30), Months(2, NthWeekdayOfMonth(2, Friday))) should be(expected)
    }

    "it's very easy to implement your own PositionOfMonth" in {
      /**
       * match first day in Feb, 3rd day for other months
       */
      case object MyPositionOfMonth extends PositionOfMonth {
        override def isValidDOM(d: Date) = {
          if (d.month == February) {
            d.dd == 1
          } else {
            d.dd == 3
          }
        }
      }

      val expected = Date(2014, 1, 3) :: Date(2014, 2, 1) :: Date(2014, 3, 3) :: Nil
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 30), Months(1, MyPositionOfMonth)) should be(expected)
    }

    "similarly, year" in {
      val expected = Date(2014, 12, 30) :: Date(2015, 12, 29) :: Date(2016, 12, 27) :: Nil
      Lamma.sequence(Date(2014, 1, 1), Date(2016, 12, 31), Years(1, LastWeekdayOfYear(Tuesday))) should be(expected)
    }

    "find all 3rd Friday of February for every 3 years in 2010s" in {
      val expected = Date(2010,2,19) :: Date(2013,2,15) :: Date(2016,2,19) :: Date(2019,2,15) :: Nil
      Lamma.sequence(Date(2010, 1, 1), Date(2019, 12, 31), Years(3, NthMonthOfYear(February, NthWeekdayOfMonth(3, Friday)))) should be(expected)
    }

    "you can shift a date based on the result. For example, I want 3rd last day of every month" in {
      val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,29) :: Nil
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(1, LastDayOfMonth), ShiftCalendarDays(-2)) should be(expected)
    }

    "you can also shift by working days" in {
      val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,27) :: Nil
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(1, LastDayOfMonth), ShiftWorkingDays(-2, WeekendCalendar)) should be(expected)
    }

    "you can further select result date after the date is shifted" in {
      val expected = Date(2014,1,29) :: Date(2014,2,26) :: Date(2014,3,31) :: Nil   // last date is different
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(1, LastDayOfMonth), ShiftCalendarDays(-2), Forward(WeekendCalendar)) should be(expected)
    }

    // some edge cases
    "if the recurrence pattern is too long, then there will be only one day generated, based on the direction (forward or backward?)" in {
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), Months(6)) should(be(List(Date(2014, 1, 1))))
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 3, 31), MonthsBackward(6)) should(be(List(Date(2014, 3, 31))))
    }

    "single date will be generated, when start date equals to the end date" in {
      Lamma.sequence(Date(2014, 1, 1), Date(2014, 1, 1), MonthsBackward(6)) should(be(List(Date(2014, 1, 1))))
    }

    "exception will be thrown when start date is before end date" in {
      intercept[IllegalArgumentException] {
        Lamma.sequence(Date(2014, 1, 1), Date(2013, 12, 31))
      }
    }
  }

  "advanced: generate a full schedule" should {
    // sometimes dates need to be generated in pairs.
    // For example, for a bond coupon payment schedule,
    //  not only we need to generate coupon date, but also settlement date

    "let's start with a coupon date only" in {
      val expected = Row(2015, 6, 30) :: Row(2015, 12, 31) :: Row(2016, 6, 30) :: Row(2016, 12, 30) :: Nil  // 2016-12-31 is Saturday

      val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(WeekendCalendar)) :: Nil
      Lamma.schedule(Date(2015, 1, 1), Date(2016, 12, 31), Months(6, LastDayOfMonth), dateDefs = dateDefs).rows should be(expected)
    }

    "now settlement delay is 2 days" in {
      val expected = List(
        Row(Date(2015, 6, 30), Date(2015, 7, 2)),
        Row(Date(2015, 12, 31), Date(2016, 1, 4)), // 2016-01-02 is Saturday, 2016-01-03 is Sunday
        Row(Date(2016, 6, 30), Date(2016, 7, 4)),  // 2016-07-02 is Saturday, 2016-07-03 is Sunday
        Row(Date(2016, 12, 30), Date(2017, 1, 3))  // 2016-12-31 is Saturday, 2017-01-01 is Sunday
      )

      val dateDefs = List(
        DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(WeekendCalendar)),
        DateDef("SettlementDate", relativeTo = OtherDateDef("CouponDate"), shifter = ShiftWorkingDays(2, WeekendCalendar))
      )
      Lamma.schedule(Date(2015, 1, 1), Date(2016, 12, 31), Months(6, LastDayOfMonth), dateDefs = dateDefs).rows should be(expected)
    }

    "how about the schedule is fractional? for example, end day is 1 month later, then an extra row will be generated" in {
      val expected = Row(2015, 6, 30) :: Row(2015, 12, 31) :: Row(2016, 6, 30) :: Row(2016, 12, 30) :: Row(2017, 1, 31) :: Nil

      val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(WeekendCalendar)) :: Nil
      Lamma.schedule(Date(2015, 1, 1), Date(2017, 1, 31), Months(6, LastDayOfMonth), dateDefs = dateDefs).rows should be(expected)
    }

    "let's merge it by applying a long end stub rule" in {
      val expected = Row(2015, 6, 30) :: Row(2015, 12, 31) :: Row(2016, 6, 30) :: Row(2017, 1, 31) :: Nil
      val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = ModifiedFollowing(WeekendCalendar)) :: Nil
      Lamma.schedule(Date(2015, 1, 1), Date(2017, 1, 31), Months(6, LastDayOfMonth), StubRulePeriodBuilder(endRule = LongEnd(270)), dateDefs = dateDefs).rows should be(expected)
    }

    // edge cases

    "single row with end day will be generated when the duration between start and end day is too short" in {
      val expected = Row(2015, 3, 30) :: Nil
      val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil

      Lamma.schedule(Date(2015, 1, 1), Date(2015, 3, 30), Months(6), dateDefs = dateDefs).rows should be(expected)
      Lamma.schedule(Date(2015, 1, 1), Date(2015, 3, 30), MonthsBackward(6), dateDefs = dateDefs).rows should be(expected)
    }

    "and if start date is end date" in {
      val expected = Row(2015, 1, 1) :: Nil
      val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil
      Lamma.schedule(Date(2015, 1, 1), Date(2015, 1, 1), EveryMonth, dateDefs = dateDefs).rows should be(expected)
    }

    "throw exception when input start date is later than end date" in {
      intercept[IllegalArgumentException] {
        Lamma.schedule(Date(2015, 1, 1), Date(2014, 3, 30), EveryMonth)
      }
    }
  }

  "topic: recurrence pattern" should {

    "you can customize your own recurrence pattern" should {

      /**
       * this custom recurrence pattern will work as follows:
       * 1) first recurrence date is the from date
       * 2) second recurrence date is 7 days later
       * 3) third recurrence date is 5 days later
       * 4) fourth recurrence date is to date
       *
       * this is just a reference for demo purpose, some logics like validations are skipped
       */
      case object CustomRecurrence extends Recurrence {
        override def recur(from: Date, to: Date) = {
          from :: from + 7 :: from + 7 + 5 :: to :: Nil
        }
      }

      "let's use it to generate a sequence" in {
        val expected = Date(2014, 1, 1) :: Date(2014, 1, 8) :: Date(2014, 1, 13) :: Date(2014, 1, 31) :: Nil
        Lamma.sequence(Date(2014, 1, 1), Date(2014, 1, 31), CustomRecurrence) should be(expected)
      }

      "let's use it to generate a schedule" in {
        val expected = Row(2015, 1, 7) :: Row(2015, 1, 12) :: Row(2015, 1, 31) :: Nil

        val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil
        Lamma.schedule(Date(2015, 1, 1), Date(2015, 1, 31), CustomRecurrence, dateDefs = dateDefs).rows should be(expected)
      }
    }
  }

  "topic: shifters" should {
    "you can customize your own shifter" should {
      /**
       * this custom shifter work as follows:
       * if it's a 15th day of a month, then shift by 2 days, otherwise remains unchanged
       */
      case object CustomShifter extends Shifter {
        override def shift(d: Date) = if (d.dd == 15) d + 2 else d
      }

      "let's use it to generate a sequence" in {
        val expected = Date(2015, 10, 8) :: Date(2015, 10, 17) :: Date(2015, 10, 22) :: Date(2015, 10, 29) :: Nil
        Lamma.sequence(Date(2015, 10, 8), Date(2015, 10, 30), EveryWeek, CustomShifter) should be(expected)
      }

      "let's use it to generate a schedule" in {
        val expected = Row(2015, 10, 8) :: Row(2015, 10, 17) :: Row(2015, 10, 22) :: Row(2015, 10, 29) :: Nil

        val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, shifter = CustomShifter) :: Nil
        Lamma.schedule(Date(2015, 10, 2), Date(2015, 10, 29), EveryWeek, dateDefs = dateDefs).rows should be(expected)
      }
    }
  }

  "topic: selectors" should {
    "you can customize your own selector" should {
      /**
       * this custom selector work as follows:
       * if it's an even day, select the previous day. otherwise select current day
       */
      case object CustomSelector extends Selector {
        override def select(d: Date) = if (d.dd % 2 == 0) d - 1 else d
      }

      "let's use it to generate a sequence" in {
        val expected = Date(2015, 10, 1) :: Date(2015, 10, 3) :: Date(2015, 10, 7) :: Date(2015, 10, 9) :: Nil
        Lamma.sequence(Date(2015, 10, 1), Date(2015, 10, 10), Days(3), selector = CustomSelector) should be(expected)
      }

      "let's use it to generate a schedule" in {
        val expected = Date(2015, 10, 3) :: Date(2015, 10, 5) :: Date(2015, 10, 9) :: Nil

        val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = CustomSelector) :: Nil
        Lamma.schedule(Date(2015, 10, 1), Date(2015, 10, 9), Days(3), dateDefs = dateDefs)("CouponDate") should be(expected)
      }
    }
  }

  "topic: holiday calendar" should {
    "you can customize your own calendar" should {
      /**
       * all Wednesdays are now holiday :)
       */
      case object WednesdayCalendar extends Calendar {
        override def isHoliday(d: Date) = d.weekday == Wednesday
      }

      "let's use it to generate a sequence" in {
        val expected = Date(2015, 10, 13) :: Date(2015, 10, 15) :: Date(2015, 10, 16) :: Nil
        Lamma.sequence(Date(2015, 10, 13), Date(2015, 10, 16), EveryWorkingDay(WednesdayCalendar)) should be(expected)
      }
    }

    "you can compose multiple calendars with CompositeCalendar" in {
      val ukHolidays2014 = SimpleCalendar(Date(2014, 1, 1), Date(2014, 4, 18), Date(2014, 4, 21),
        Date(2014, 5, 5), Date(2014, 5, 26), Date(2014, 8, 25), Date(2014, 12, 25), Date(2014, 12, 26))

      val composedCalendar = CompositeCalendar(ukHolidays2014, WeekendCalendar)

      // 2014-04-18 is a UK holiday
      // 2014-04-20 is Sunday
      val expected = Date(2014, 4, 16) :: Date(2014, 4, 22) :: Date(2014, 4, 24) :: Nil
      Lamma.sequence(Date(2014, 4, 16), Date(2014, 4, 24), Days.workingDay(2, composedCalendar)) should be(expected)
    }
  }

  "topic: period builder / stub rules" should {
    "you can customize your period builder" in {

      /**
       * this period builder will merge every two periods into one
       * and then prepend start stub and append end stub
       */
      case object CustomPeriodBuilder extends PeriodBuilder {
        override def build(start: Date, end: Date, periodEnds: List[Date]) = {
          val dates = (start - 1 :: periodEnds.grouped(2).map(_.head).toList) :+ end
          Period.fromDates(dates)
        }
      }

      // first two periods are merged together
      val expected = Period((2015, 10, 1) -> (2015, 10, 3)) :: Period((2015, 10, 4) -> (2015, 10, 9)) :: Period((2015, 10, 10) -> (2015, 10, 10)) :: Nil
      Lamma.schedule(Date(2015, 10, 1), Date(2015, 10, 10), Days(3), CustomPeriodBuilder).periods should be(expected)
    }
  }
}
