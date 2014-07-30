package iolamma4j.stackoverflow;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import io.lamma.DayOfWeek;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/8040790/joda-time-all-mondays-between-two-dates
 */
public class Q8040790 {

    @Test
    public void test() {
        List<Date> expected = Lists.newArrayList(
                new Date(2011, 11, 14), new Date(2011, 11, 21), new Date(2011, 11, 28),
                new Date(2011, 12, 5), new Date(2011, 12, 12), new Date(2011, 12, 19), new Date(2011, 12, 26)
        );

        List<Date> actual = Dates.from(2011, 11, 8).to(2011, 12, 30).byWeek().on(DayOfWeek.MONDAY).build();

        assertEquals(expected, actual);
    }
}
