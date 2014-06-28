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
  Date(2014, 7, 7).is(Monday)

  Date(2014, 7, 7).withDayOfWeek(Monday)        // Monday of this week

  Date(2014, 7, 7).withDayOfWeek(Sunday)          // Sunday of this week
  
  Date(2014, 7, 7).daysOfWeek             // list of dates of this week

  Date(2014, 7, 7).next(Friday)         // find the next Friday excludes today

  Date(2014, 7, 7).previous(Wednesday)        // find the past Wednesday excludes today

  // ======== month operations ======
  Date(2014, 7, 7).daysOfMonth            // lists of Dates from

  Date(2016, 2, 5).maxDayOfMonth

  Date(2016, 2, 5).lastDayOfMonth         // last day of this month

  Date(2016, 2, 5).sameWeekdaysOfMonth

  // ======== year operations ======
  Date(2016, 2, 5).maxDayOfYear

  Date(2016, 2, 5).nthDayOfYear

  Date(2014, 7, 7).sameWeekdaysOfYear
}
