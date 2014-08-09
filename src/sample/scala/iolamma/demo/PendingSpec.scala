package iolamma.demo

import io.lamma.Anchor.PeriodEnd
import io.lamma.DayOfMonth.NthDayOfMonth
import io.lamma.StubRulePeriodBuilder._
import io.lamma._
import org.scalatest.{Matchers, WordSpec}

/**
 * FIXME: group them into separated detailed topics
 */
class PendingSpec extends WordSpec with Matchers {

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
      case object CustomPattern extends Pattern {
        override def recur(from: Date, to: Date) = {
          from :: from + 7 :: from + 7 + 5 :: to :: Nil
        }
      }

      "let's use it to generate a sequence" in {
        val expected = Date(2014, 1, 1) :: Date(2014, 1, 8) :: Date(2014, 1, 13) :: Date(2014, 1, 31) :: Nil
        DateRange(Date(2014, 1, 1), Date(2014, 1, 31), CustomPattern).toList should be(expected)
      }

      "let's use it to generate a schedule" in {
        val expected = List(Date(2015, 1, 7)) :: List(Date(2015, 1, 12)) :: List(Date(2015, 1, 31)) :: Nil

        val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd) :: Nil
        Schedule(Date(2015, 1, 1), Date(2015, 1, 31), CustomPattern, dateDefs = dateDefs).generatedDates should be(expected)
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
        val actual = Date(2015, 10, 8) to Date(2015, 10, 30) by week shift CustomShifter
        actual.toList should be(expected)
      }

      "let's use it to generate a schedule" in {
        val expected = List(Date(2015, 10, 8)) :: List(Date(2015, 10, 17)) :: List(Date(2015, 10, 22)) :: List(Date(2015, 10, 29)) :: Nil

        val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, shifter = CustomShifter) :: Nil
        Schedule(Date(2015, 10, 2), Date(2015, 10, 29), Weekly(1), dateDefs = dateDefs).generatedDates should be(expected)
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
        val actual = Date(2015, 10, 1) to Date(2015, 10, 10) by (3 days) select CustomSelector
        actual.toList should be(expected)
      }

      "let's use it to generate a schedule" in {
        val expected = Date(2015, 10, 3) :: Date(2015, 10, 5) :: Date(2015, 10, 9) :: Nil

        val dateDefs = DateDef("CouponDate", relativeTo = PeriodEnd, selector = CustomSelector) :: Nil
        Schedule(Date(2015, 10, 1), Date(2015, 10, 9), Daily(3), dateDefs = dateDefs)("CouponDate") should be(expected)
      }
    }
  }

  "topic: holiday rule" should {
    "you can customize your own holiday rule" should {
      /**
       * all Wednesdays are now holiday :)
       */
      case object WednesdayHolidayRule extends HolidayRule {
        override def isHoliday(d: Date) = d.dayOfWeek == Wednesday
      }

      "let's use it to generate a sequence" in {
        val expected = Date(2015, 10, 13) :: Date(2015, 10, 15) :: Date(2015, 10, 16) :: Nil
        val actual = Date(2015, 10, 13) to Date(2015, 10, 16) except WednesdayHolidayRule
        actual.toList should be(expected)
      }
    }

    "you can compose multiple holiday rules with CompositeHolidayRule" in {
      val ukHolidays2014 = SimpleHolidayRule(Date(2014, 1, 1), Date(2014, 4, 18), Date(2014, 4, 21),
        Date(2014, 5, 5), Date(2014, 5, 26), Date(2014, 8, 25), Date(2014, 12, 25), Date(2014, 12, 26))

      val expected = List(Date(2014,4,16), Date(2014,4,17), Date(2014,4,22), Date(2014,4,23), Date(2014,4,24))
      val actual = Date(2014, 4, 16) to Date(2014, 4, 24) except ukHolidays2014 except Weekends
      actual.toList should be(expected)
    }
  }

  "topic: period builder / stub rules" should {
    // start / end + recurrence pattern will generate a list of recurrence date
    // you will need a PeriodBuilder to build your period
    "the default one will just work as expected" in {
      val start = Date(2014, 10, 1)
      val end = Date(2014, 10, 30)
      val pattern = Daily(10)

      val expected = Period((2014, 10, 1) -> (2014, 10, 10)) :: Period((2014, 10, 11) -> (2014, 10, 20)) :: Period((2014, 10, 21) -> (2014, 10, 30)) :: Nil

      Schedule(start, end, pattern).periods should be(expected)
    }

    "but sometimes the scenario is a little bit complicated, for example this will generate an extra end stub: last period only have 5 days" in {
      val start = Date(2014, 10, 1)
      val end = Date(2014, 10, 25)
      val pattern = Daily(10)

      val expected = Period((2014, 10, 1) -> (2014, 10, 10)) :: Period((2014, 10, 11) -> (2014, 10, 20)) :: Period((2014, 10, 21) -> (2014, 10, 25)) :: Nil

      Schedule(start, end, pattern).periods should be(expected)
    }

    "and for this case, the first period only have 5 days" in {
      val start = Date(2014, 10, 1)
      val end = Date(2014, 10, 25)
      val pattern = Daily(-10)

      val expected = Period((2014, 10, 1) -> (2014, 10, 5)) :: Period((2014, 10, 6) -> (2014, 10, 15)) :: Period((2014, 10, 16) -> (2014, 10, 25)) :: Nil

      Schedule(start, end, pattern, direction = Direction.BACKWARD).periods should be(expected)
    }

    "and for this case, both first and last period are very short" in {
      val start = Date(2014, 10, 7)
      val end = Date(2014, 10, 26)
      val pattern = Weekly(1, Wednesday)

      val expected = List(
        Period((2014, 10, 7) -> (2014, 10, 8)),    // 2 days period
        Period((2014, 10, 9) -> (2014, 10, 15)),   // 7 days period
        Period((2014, 10, 16) -> (2014, 10, 22)),  // 7 days period
        Period((2014, 10, 23) -> (2014, 10, 26))   // 4 days period
      )

      Schedule(start, end, pattern).periods should be(expected)
    }

    "in order to merge them, you will need a stub rule" in {
      val start = Date(2014, 10, 1)
      val end = Date(2014, 10, 25)
      val pattern = Daily(10)

      val expected = Period((2014, 10, 1) -> (2014, 10, 10)) :: Period((2014, 10, 11) -> (2014, 10, 25)) :: Nil

      // LongEnd(15) means: I am ok with a longer end period, as long as it's no more than 15 days
      val periodBuilder = StubRulePeriodBuilder(endRule = LongEnd(15))

      Schedule(start, end, pattern, periodBuilder).periods should be(expected)
    }

    "similarly we have stub rule for starting period" in {
      val start = Date(2014, 10, 1)
      val end = Date(2014, 10, 25)
      val pattern = Daily(-10)

      val expected = Period((2014, 10, 1) -> (2014, 10, 15)) :: Period((2014, 10, 16) -> (2014, 10, 25)) :: Nil

      // LongStart(15) means: I am ok with a longer start period, as long as it's no more than 15 days
      val periodBuilder = StubRulePeriodBuilder(startRule = LongStart(15))

      Schedule(start, end, pattern, periodBuilder, direction = Direction.BACKWARD).periods should be(expected)
    }

    "we can apply both at the same time" in {
      val start = Date(2014, 10, 7)
      val end = Date(2014, 10, 26)
      val pattern = Weekly(1, Wednesday)
      val periodBuilder = StubRulePeriodBuilder(LongStart(10), LongEnd(10))

      val expected = List(
        Period((2014, 10, 7) -> (2014, 10, 15)),   // merged start period
        Period((2014, 10, 16) -> (2014, 10, 22)),  // 7 days period

        // end period is not merged, because the merged period will have 11 days, which exceeds longest end stub: 10 days
        Period((2014, 10, 23) -> (2014, 10, 26))   // 4 days period
      )

      Schedule(start, end, pattern, periodBuilder).periods should be(expected)
    }

    "not like LongStart / LongEnd rule, ShortStart / ShortEnd rule is looking to split start / end period. For example" in {
      val start = Date(2014, 10, 7)
      val end = Date(2014, 10, 26)
      val pattern = Weekly(1, Wednesday)
      // ShortStart(2) => split a separated starting period as long as there are 2 days
      // ShortEnd(2) => split a separated end period as long as there are 2 days
      val periodBuilder = StubRulePeriodBuilder(ShortStart(2), ShortEnd(2))

      val expected = List(
        Period((2014, 10, 7) -> (2014, 10, 8)),    // 2 days short starting period (2 >= 2)
        Period((2014, 10, 9) -> (2014, 10, 15)),   // 7 days period
        Period((2014, 10, 16) -> (2014, 10, 22)),  // 7 days period
        Period((2014, 10, 23) -> (2014, 10, 26))   // 4 days short ending period (4 >= 2)
      )

      Schedule(start, end, pattern, periodBuilder).periods should be(expected)
    }

    // this is exactly as our first defaulting example
    "yes, the default behavior is ShortStart(0) + ShortEnd(0), which means Lamma will always create start / end period" in {
      val start = Date(2014, 10, 1)
      val end = Date(2014, 10, 30)
      val pattern = Daily(10)
      val periodBuilder = StubRulePeriodBuilder(ShortStart(0), ShortEnd(0))
      val expected = Period((2014, 10, 1) -> (2014, 10, 10)) :: Period((2014, 10, 11) -> (2014, 10, 20)) :: Period((2014, 10, 21) -> (2014, 10, 30)) :: Nil

      Schedule(start, end, pattern, periodBuilder).periods should be(expected)
    }

    // so why do we need long rule and short rule? aren't they completely interchangable?
    //  for example, for a pattern Days(30), LongStart(45) and ShortStart(15) are exactly the same right?
    // yes, in this case indeed. But in some rare case, period length varies a lot.
    //  In order to have more control on the generated schedule, we decide to keep both of them.
    //  If your use case is mainly on fixed length period, then you can always Short rule or Long rule, whichever convenient to you.

    // because StubRulePeriodBuilder is too tightly coupled with start / end rules,
    // we do not allow you to override single start or end rule
    "you can customize your period builder" in {

      /**
       * this period builder will merge every two periods into one
       * and then prepend start stub and append end stub
       */
      case object CustomPeriodBuilder extends PeriodBuilder {
        override def build(start: Date, end: Date, periodEnds: List[Date]) = {
          val end0 = start - 1
          val endDays = (end0 :: periodEnds.grouped(2).map(_.head).toList) :+ end
          Period.fromPeriodEndDays(endDays)
        }
      }

      // first two periods are merged together
      val expected = Period((2015, 10, 1) -> (2015, 10, 3)) :: Period((2015, 10, 4) -> (2015, 10, 9)) :: Period((2015, 10, 10) -> (2015, 10, 10)) :: Nil
      Schedule(Date(2015, 10, 1), Date(2015, 10, 10), Daily(3), CustomPeriodBuilder).periods should be(expected)
    }

    // ==== edge cases ====

    "when there are no recurrence date generated (most likely your recurrence pattern is too long), Lamma will simply return single Period(start, end)" in {
      val start = Date(2014, 10, 1)
      val end = Date(2014, 10, 5)
      val expected = Period(start, end) :: Nil

      Schedule(start, end, Monthly(1)).periods should be(expected)
      Schedule(start, end, Monthly(-1)).periods should be(expected)
    }

    "when there are one or two recurrence date generated then there will be racing condition. Just remember one rule: start rule always " should {

      "when there are only one recurrence date generated" should {
        val start = Date(2014, 10, 1)
        val end = Date(2014, 10, 10)
        val dom = NthDayOfMonth(5)  // single recurrence date Date(2014, 10, 5)

        val split = Period((2014, 10, 1) -> (2014, 10, 5)) :: Period((2014, 10, 6) -> (2014, 10, 10)) :: Nil
        val merged = Period((2014, 10, 1) -> (2014, 10, 10)) :: Nil

        def period(startRule: StartRule, endRule: EndRule) = {
          Schedule(start, end, Monthly(1, dom), StubRulePeriodBuilder(startRule, endRule)).periods
        }

        "if Start Rule will merge, then end rule will not split too (always be ignored)" in {
          period(LongStart(10), ShortEnd(5)) should be(merged)
          period(LongStart(10), ShortEnd(6)) should be(merged)
          period(LongStart(10), LongEnd(9)) should be(merged)
          period(LongStart(10), LongEnd(10)) should be(merged)

          period(ShortStart(6), ShortEnd(5)) should be(merged)
          period(ShortStart(6), ShortEnd(6)) should be(merged)
          period(ShortStart(6), LongEnd(9)) should be(merged)
          period(ShortStart(6), LongEnd(10)) should be(merged)
        }

        "if start rule will not merge, then end rule will not merge too (always be ignored)" in {
          period(LongStart(9), ShortEnd(5)) should be(split)
          period(LongStart(9), ShortEnd(6)) should be(split)
          period(LongStart(9), LongEnd(9)) should be(split)
          period(LongStart(9), LongEnd(10)) should be(split)

          period(ShortStart(5), ShortEnd(5)) should be(split)
          period(ShortStart(5), ShortEnd(6)) should be(split)
          period(ShortStart(5), LongEnd(9)) should be(split)
          period(ShortStart(5), LongEnd(10)) should be(split)
        }
      }

      "when there are two recurrence date generated" should {
        val start = Date(2014, 10, 1)
        val end = Date(2014, 10, 30)
        val pattern = Daily(10)    // two recurrence dates, 2014-10-10, 2014-10-20

        val allSplit = Period((2014, 10, 1) -> (2014, 10, 10)) :: Period((2014, 10, 11) -> (2014, 10, 20)) :: Period((2014, 10, 21) -> (2014, 10, 30)) :: Nil
        val headMerged = Period((2014, 10, 1) -> (2014, 10, 20)) :: Period((2014, 10, 21) -> (2014, 10, 30)) :: Nil
        val tailMerged = Period((2014, 10, 1) -> (2014, 10, 10)) :: Period((2014, 10, 11) -> (2014, 10, 30)) :: Nil

        def period(startRule: StartRule, endRule: EndRule) = {
          Schedule(start, end, pattern, StubRulePeriodBuilder(startRule, endRule)).periods
        }

        "if start rule will merge, then the end rule will always be split" in {
          period(LongStart(20), ShortEnd(10)) should be(headMerged)
          period(LongStart(20), ShortEnd(11)) should be(headMerged)
          period(LongStart(20), LongEnd(19)) should be(headMerged)
          period(LongStart(20), LongEnd(20)) should be(headMerged)

          period(ShortStart(11), ShortEnd(10)) should be(headMerged)
          period(ShortStart(11), ShortEnd(11)) should be(headMerged)
          period(ShortStart(11), LongEnd(19)) should be(headMerged)
          period(ShortStart(11), LongEnd(20)) should be(headMerged)
        }

        "if start rule will NOT merge, then the end rule will be applied normally" in {
          period(LongStart(19), ShortEnd(10)) should be(allSplit)
          period(LongStart(19), ShortEnd(11)) should be(tailMerged)
          period(LongStart(19), LongEnd(19)) should be(allSplit)
          period(LongStart(19), LongEnd(20)) should be(tailMerged)

          period(ShortStart(10), ShortEnd(10)) should be(allSplit)
          period(ShortStart(10), ShortEnd(11)) should be(tailMerged)
          period(ShortStart(10), LongEnd(19)) should be(allSplit)
          period(ShortStart(10), LongEnd(20)) should be(tailMerged)
        }
      }
    }
  }

}
