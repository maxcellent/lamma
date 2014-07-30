package iolamma.stackoverflow;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/3977508/recurring-event-logic
 */
public class Q3977508 {

    @Test
    public void testByMonth() {
        List<Date> expected = Lists.newArrayList(
                new Date(2014,6,10), new Date(2014,7,10), new Date(2014,8,10), new Date(2014,9,10), new Date(2014,10,10)
        );
        List<Date> actual = Dates.from(2014, 6, 10).to(2014, 10, 10).byMonth().build();
        assertEquals(expected, actual);
    }

    @Test
    public void testByWeek() {
        List<Date> expected = Lists.newArrayList(
                new Date(2014,6,10), new Date(2014,6,17), new Date(2014,6,24), new Date(2014,7,1), new Date(2014,7,8)
        );
        List<Date> actual = Dates.from(2014, 6, 10).to(2014, 7, 10).byWeek().build();
        assertEquals(expected, actual);
    }

    @Test
    public void testByFortnight() {
        List<Date> expected = Lists.newArrayList(new Date(2014,6,10), new Date(2014,6,24), new Date(2014,7,8));
        List<Date> actual = Dates.from(2014, 6, 10).to(2014, 7, 10).byWeeks(2).build();
        assertEquals(expected, actual);
    }

    /**
     * edge cases are handled properly, for example, leap day
     */
    @Test
    public void testLeapDay() {
        List<Date> expected = Lists.newArrayList(
                new Date(2012,2,29), new Date(2013,2,28), new Date(2014,2,28), new Date(2015,2,28), new Date(2016,2,29)
        );
        List<Date> actual = Dates.from(2012, 2, 29).to(2016, 2, 29).byYear().build();
        assertEquals(expected, actual);
    }
}
