package iolamma4j.stackoverflow;

import io.lamma.Date;
import io.lamma.DayOfWeek;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/11631588/java-check-if-date-is-next-sunday
 */
public class Q11631588 {

    @Test
    public void test() {
        Date today = new Date(2014, 7, 1);  // assume today is 2014-07-01
        assertEquals(new Date(2014, 7, 6), today.next(DayOfWeek.SUNDAY));
    }
}
