package io.lamma.demo

/**
 * this will be used in Quick Start page, all commands are executed in REPL order
 */
object Date0 extends App {

  import io.lamma._

  Date(2014, 7, 5)  // create a new date

  Date("2014-05-05")  // create with ISO string

  // ========= compare dates =============
  Date(2014, 7, 7) < Date(2014, 7, 8)

  (2014, 7, 7) < (2014, 7, 8)

  (2014, 7, 7) >= (2014, 7, 8)

  // ========== basic + / - operations ===============
  (2014, 7, 5) + 2 // add 2 days

  (2014, 7, 5) + (2 weeks)  // add 2 weeks

  (2014, 7, 5) + (5 months) // add 5 months

  (2014, 7, 5) + (10 years) // add 10 years

  (2014, 7, 5) - 5      // same as `-`

  (2014, 7, 10) - (2014, 7, 3)  // minus two dates

  // ==== week operations  ======
  Date(2014, 7, 7).isMonday

  Date(2014, 7, 7).comingFriday         // find the next Friday excludes today

  Date(2014, 7, 7).pastWednesday        // find the past Wednesday excludes today

  Date(2014, 7, 7).thisWeekBegin        // Monday of this week

  Date(2014, 7, 7).thisWeekEnd          // Sunday of this week
  
  Date(2014, 7, 7).thisWeek             // list of dates of this week

  // ======== month operations ======
  Date(2014, 7, 7).thisMonth            // lists of Dates from

  Date(2016, 2, 5).maxDayOfMonth

  Date(2016, 2, 5).thisMonthEnd         // last day of this month

  Date(2016, 2, 5).sameWeekdaysOfMonth

  // ======== year operations ======
  Date(2016, 2, 5).maxDayOfYear

  Date(2016, 2, 5).dayOfYear

  Date(2014, 7, 7).sameWeekdaysOfYear
}
