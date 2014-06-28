package io.lamma.demo;

/**
 * always import this line when you are using Java
 */
import static io.lamma.LammaJavaImports.*;

import io.lamma.Date;


/**
 * this will be used in Quick Start page
 */
public class Date0Test {

    public static void main(String [] args) {
        // ========== date creation =============
        System.out.println(new Date(2014, 7, 5));  // create date with constructor
        // Output: Date(2014,7,5)

        System.out.println(date(2014, 7, 5));   // create date with helper method
        // Output: Date(2014,7,5)

        System.out.println(date("2014-07-05"));   // create date with ISO 8601 format
        // Output: Date(2014,7,5)

        // ========= compare dates =============
        System.out.println(date(2014, 7, 7).isBefore(date(2014, 7, 8)));
        // Output: true

        // also support isAfter, isOnOrBefore and isOnOrAfter
        System.out.println(date(2014, 7, 7).isOnOrAfter(date(2014, 7, 8)));
        // Output: false

        // ========== manipulate dates =============
        System.out.println(date(2014, 7, 5).plusDays(2));   // plus days
        // Output: Date(2014,7,7)

        System.out.println(date(2014, 7, 5).plusWeeks(2));  // plus weeks
        // Output: Date(2014,7,19)

        System.out.println(date(2014, 7, 5).plusMonths(5)); // plus months
        // Output: Date(2014,12,5)

        System.out.println(date(2014, 7, 5).plusYears(10)); // plus years
        // Output: Date(2024,7,5)

        System.out.println(date(2014, 7, 5).minusDays(5));  // minus will work similar as plus
        // Output: Date(2014,6,30)

        // minus two Dates will result day difference in int
        System.out.println(date(2014, 7, 10).minus(date(2014, 7, 3)));
        // Output: 7 (int)

        // ==== week operations  ======
        System.out.println(date(2014, 7, 7).isMonday()); // from isMonday to ... isSunday
        // Output: true

        System.out.println(date(2014, 7, 7).comingFriday()); // find the next Friday excludes today
        // Output: Date(2014,7,11)

        System.out.println(date(2014, 7, 7).pastWednesday()); // find the past Wednesday excludes today
        // Output: Date(2014,7,2)

        System.out.println(date(2014, 7, 7).thisWeekBegin()); // fist day of this week.
        // Output: Date(2014,7,7)   (In Lamma, a week starts from Monday)

        System.out.println(date(2014, 7, 7).thisWeekEnd()); // last day of this week.
        // Output: Date(2014,7,13)   (In Lamma, a week ends with Sunday)

        // list of dates of this week
        for (Date d : date(2014, 7, 7).thisWeek().javaIterable()) {
            System.out.println(d);
        }
        // Output: from Date(2014,7,7) to Date(2014,7,13)

        // ======== month operations ======
        // lists of Dates of this month
        for (Date d : date(2014, 7, 7).daysOfMonth().javaIterable()) {
            System.out.println(d);
        }
        // Output: from Date(2014,7,1) to Date(2014,7,31)

        System.out.println(date(2016, 2, 5).maxDayOfMonth());   // max day of month, leap day is considered
        // Output: 29

        System.out.println(date(2016, 2, 5).maxDayOfMonth());   // last day of this month, leap day is considered
        // Output: Date(2016, 2, 29)

        // all Fridays in Feb, 2016
        for (Date d : date(2016, 2, 5).sameWeekdaysOfMonth4j()) {
            System.out.println(d);
        }
        // Output: Date(2016,2,5), Date(2016,2,12), Date(2016,2,19) and Date(2016,2,26)

        // ======== year operations ======
        System.out.println(date(2016, 2, 5).maxDayOfYear());     // max day of year, leap year is considered
        // Output: 366

        System.out.println(date(2016, 2, 5).dayOfYear());       // day of year
        // Output: 36 (2016-02-05 is the 36th day of 2016)

        // all Fridays in 2016
        for (Date d : date(2016, 2, 5).sameWeekdaysOfYear4j()) {
            System.out.println(d);
        }
        // Output: Date(2016,1,1), Date(2016,1,8), Date(2016,1,15), ..., Date(2016,12,30)
    }
}
