package io.lamma.partial.date

import io.lamma._

import scala.annotation.tailrec

private[lamma] trait WeekOps {
  this: Date =>

  /**
   * The first day of this week (Monday) <br>
   *   http://en.wikipedia.org/wiki/ISO_week_date
   */
  lazy val thisWeekBegin = (this + 1).pastMonday

  /**
   * The last day of this week (Sunday) <br>
   *   http://en.wikipedia.org/wiki/ISO_week_date
   */
  lazy val thisWeekEnd = (this - 1).comingSunday

  /**
   * an iterable for every day in this week <br>
   *   (week starts on Monday according to ISO 8601: http://en.wikipedia.org/wiki/ISO_week_date)
   */
  lazy val thisWeed = thisWeekBegin to thisWeekEnd

  lazy val dayOfWeek = JavaDateUtil.dayOfWeek(this)

  lazy val isMonday = dayOfWeek == Monday

  lazy val isTuesday = dayOfWeek == Tuesday

  lazy val isWednesday = dayOfWeek == Wednesday

  lazy val isThursday = dayOfWeek == Thursday

  lazy val isFriday = dayOfWeek == Friday

  lazy val isSaturday = dayOfWeek == Saturday

  lazy val isSunday = dayOfWeek == Sunday

  lazy val isWeekend = isSaturday || isSunday

  /**
   * calculate the coming day of week excluding this date: <br>
   *   <br>
   *   For example: <br>
   *   Date(2014-07-05).comingWeekday(Monday) => Date(2014-07-07) <br>
   *   Date(2014-07-05).comingWeekday(Saturday) => Date(2014-07-12) // note 2014-07-05 itself is already Saturday <br>
   */
  def comingWeekday(wd: DayOfWeek) = WeekOps.comingWeekday(this + 1, wd)

  /**
   * shorthand of comingWeekday(Monday)
   */
  lazy val comingMonday = comingWeekday(Monday)

  /**
   * shorthand of comingWeekday(Tuesday)
   */
  lazy val comingTuesday = comingWeekday(Tuesday)

  /**
   * shorthand of comingWeekday(Wednesday)
   */
  lazy val comingWednesday = comingWeekday(Wednesday)

  /**
   * shorthand of comingWeekday(Thursday)
   */
  lazy val comingThursday = comingWeekday(Thursday)

  /**
   * shorthand of comingWeekday(Friday)
   */
  lazy val comingFriday = comingWeekday(Friday)

  /**
   * shorthand of comingWeekday(Saturday)
   */
  lazy val comingSaturday = comingWeekday(Saturday)

  /**
   * shorthand of comingWeekday(Sunday)
   */
  lazy val comingSunday = comingWeekday(Sunday)

  /**
   * past day of week excluding this date<br>
   *   <br>
   *   For example: <br>
   *   Date(2014-07-05).pastWeekday(Monday) => Date(2014-06-30) <br>
   *   Date(2014-07-05).pastWeekday(Saturday) => Date(2014-06-28) // note 2014-07-05 itself is already Saturday <br>
   */
  def pastWeekday(wd: DayOfWeek) = WeekOps.pastWeekday(this - 1, wd)

  /**
   * shorthand of pastWeekday(Monday)
   */
  lazy val pastMonday = pastWeekday(Monday)

  /**
   * shorthand of pastWeekday(Tuesday)
   */
  lazy val pastTuesday = pastWeekday(Tuesday)

  /**
   * shorthand of pastWeekday(Wednesday)
   */
  lazy val pastWednesday = pastWeekday(Wednesday)

  /**
   * shorthand of pastWeekday(Thursday)
   */
  lazy val pastThursday = pastWeekday(Thursday)

  /**
   * shorthand of pastWeekday(Friday)
   */
  lazy val pastFriday = pastWeekday(Friday)

  /**
   * shorthand of pastWeekday(Saturday)
   */
  lazy val pastSaturday = pastWeekday(Saturday)

  /**
   * shorthand of pastWeekday(Sunday)
   */
  lazy val pastSunday = pastWeekday(Sunday)

}

private object WeekOps {

  @tailrec
  private def comingWeekday(d: Date, target: DayOfWeek): Date = {
    if (d.dayOfWeek == target) {
      d
    } else {
      comingWeekday(d + 1, target)
    }
  }

  @tailrec
  private def pastWeekday(d: Date, target: DayOfWeek): Date = {
    if (d.dayOfWeek == target) {
      d
    } else {
      pastWeekday(d - 1, target)
    }
  }

}