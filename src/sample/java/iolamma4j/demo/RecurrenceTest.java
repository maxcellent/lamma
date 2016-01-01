package iolamma4j.demo;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RecurrenceTest {

    // ================= Daily recurrance pattern =================
    @Test
    public void byDays() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2015, 5, 10),
                Dates.newDate(2015, 5, 15),
                Dates.newDate(2015, 5, 20),
                Dates.newDate(2015, 5, 25)
        );

        List<Date> actual = Dates.from(2015, 5, 10).to(2015, 5, 27).byDays(5).build();

        assertEquals(actual, expected);
    }

    @Test
    public void byDaysInclusive() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2015, 5, 10),
                Dates.newDate(2015, 5, 15),
                Dates.newDate(2015, 5, 20)
        );

        List<Date> actual = Dates.from(2015, 5, 10).to(2015, 5, 20).byDays(5).build();

        assertEquals(actual, expected);
    }

    @Test
    public void omitDays() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2015, 5, 10),
                Dates.newDate(2015, 5, 15),
                Dates.newDate(2015, 5, 20)
        );

        List<Date> actual = Dates.from(2015, 5, 10).to(2015, 5, 20).by(5).build();

        assertEquals(actual, expected);
    }

    @Test
    public void defaultBehavior() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2015, 5, 10),
                Dates.newDate(2015, 5, 11),
                Dates.newDate(2015, 5, 12)
        );

        List<Date> actual = Dates.from(2015, 5, 10).to(2015, 5, 12).build();

        assertEquals(actual, expected);
    }

    @Test
    public void byNegativeDays() {
        List<Date> expected = Lists.newArrayList(
                Dates.newDate(2015, 5, 20),
                Dates.newDate(2015, 5, 15),
                Dates.newDate(2015, 5, 10)
        );

        List<Date> actual = Dates.from(2015, 5, 20).to(2015, 5, 10).by(-5).build();

        assertEquals(actual, expected);
    }
}
