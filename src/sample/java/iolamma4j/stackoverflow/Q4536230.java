package iolamma4j.stackoverflow;

import io.lamma.Date;
import io.lamma.DayOfWeek;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/4536230/how-to-get-the-past-sunday-and-the-coming-sunday-in-java
 */
public class Q4536230 {

    @Test
    public void test() {
        Date today = new Date(2014, 7, 1);      // assume today is 2014-07-01

        assertEquals(new Date(2014, 6, 29), today.previousOrSame(DayOfWeek.SUNDAY));
        assertEquals(new Date(2014, 7, 6), today.next(DayOfWeek.SUNDAY));
    }
}
