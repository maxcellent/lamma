package iolamma4j.stackoverflow;

import io.lamma.Date;
import io.lamma.DayOfWeek;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/17229613/getting-previous-particular-day-in-a-week
 */
public class Q17229613 {

    @Test
    public void test() {
        Date today = new Date(2014, 7, 1);

        Date to = today.previous(DayOfWeek.FRIDAY);  // Date(2014,6,27)
        Date from = to.previous(DayOfWeek.SATURDAY); // Date(2014,6,21)

        assertEquals(new Date(2014, 6, 21), from);
        assertEquals(new Date(2014, 6, 27), to);
    }
}
