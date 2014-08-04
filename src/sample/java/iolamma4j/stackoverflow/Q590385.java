package iolamma4j.stackoverflow;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import io.lamma.DayOfWeek;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/590385/how-to-get-all-dates-of-sundays-in-a-particular-year-in-java
 */
public class Q590385 {

    @Test
    public void test() {
        List<Date> expected = Lists.newArrayList(
                new Date(2014,1,5), new Date(2014,1,12), new Date(2014,1,19), new Date(2014,1,26)
        );

        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 1, 31).byWeek().on(DayOfWeek.SUNDAY).build();

        assertEquals(expected, actual);
    }
}
