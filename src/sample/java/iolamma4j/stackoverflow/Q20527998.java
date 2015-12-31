package iolamma4j.stackoverflow;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import io.lamma.DayOfWeek;
import org.junit.Test;

import java.util.List;

import static io.lamma.Dates.newDate;
import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/20527998/get-all-fridays-in-a-date-range-in-java
 */
public class Q20527998 {
    @Test
    public void test() {
        List<Date> expected = Lists.newArrayList(
                newDate(2015, 12, 4), newDate(2015, 12, 11), newDate(2015, 12, 18), newDate(2015, 12, 25), newDate(2016, 1, 1)
        );

        List<Date> actual = Dates.from(2015, 12, 1).to(2016, 1, 1).byWeek().on(DayOfWeek.FRIDAY).build();

        assertEquals(expected, actual);
    }
}
