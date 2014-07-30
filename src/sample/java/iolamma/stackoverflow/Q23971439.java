package iolamma.stackoverflow;

import io.lamma.Date;
import io.lamma.DayOfWeek;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/23971439/get-the-first-monday-of-a-month
 */
public class Q23971439 {

    @Test
    public void test() {
        assertEquals(new Date(2014, 1, 6), firstMonday(2014, 1));
        assertEquals(new Date(2014, 2, 3), firstMonday(2014, 2));
        assertEquals(new Date(2014, 3, 3), firstMonday(2014, 3));
        assertEquals(new Date(2014, 4, 7), firstMonday(2014, 4));
        assertEquals(new Date(2014, 5, 5), firstMonday(2014, 5));
        assertEquals(new Date(2014, 6, 2), firstMonday(2014, 6));
    }

    public Date firstMonday(int year, int month) {
        Date firstDayOfMonth = new Date(year, month, 1);
        return firstDayOfMonth.nextOrSame(DayOfWeek.MONDAY);
    }
}
