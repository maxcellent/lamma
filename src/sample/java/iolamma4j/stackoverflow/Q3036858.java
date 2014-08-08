package iolamma4j.stackoverflow;

import io.lamma.Date;
import io.lamma.HolidayRule;
import org.junit.Test;

import static io.lamma.LammaJavaImports.weekends;
import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/3036858/how-can-i-find-the-most-recent-month-end-date-with-joda-time
 */
public class Q3036858 {

    @Test
    public void test() {
        assertEquals(new Date(2014, 5, 30), findDate(new Date(2014, 5, 31)));  // 2014-05-31 is holiday
        assertEquals(new Date(2014, 4, 30), findDate(new Date(2014, 5, 30)));
        assertEquals(new Date(2014, 4, 30), findDate(new Date(2014, 5, 25)));  // the most recent working day of 2014-05-25 is not the last working day of May
    }

    private static HolidayRule cal = weekends();   // let's use weekend calendar for now

    public static Date findDate(Date current) {
        if (cal.isHoliday(current)) {
            Date lastWorkingDayOfThisMonth = current.lastDayOfMonth().backward(cal);
            Date mostRecentWorkingDay = current.backward(cal);
            if (lastWorkingDayOfThisMonth.equals(mostRecentWorkingDay)) {
                return mostRecentWorkingDay;
            } else {
                return lastWorkingDayOfLastMonth(current);
            }
        } else {
            return lastWorkingDayOfLastMonth(current);
        }
    }

    public static Date lastWorkingDayOfLastMonth(Date d) {
        return d.lastDayOfPreviousMonth().backward(cal);
    }

}
