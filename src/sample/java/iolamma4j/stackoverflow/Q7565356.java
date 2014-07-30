package iolamma4j.stackoverflow;

import io.lamma.Date;
import io.lamma.DayOfWeek;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/7565356/get-first-monday-after-certain-date
 */
public class Q7565356 {

    @Test
    public void test() {
        assertEquals(new Date(2014,6,30), new Date(2014, 6, 27).next(DayOfWeek.MONDAY));
    }

}
