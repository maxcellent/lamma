package io.lamma;

import static io.lamma.LammaConversion.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class DateRangeTest {

    @Test
    public void testJavaIterator() {
        List<Date> expected = Lists.newArrayList(date(2014, 5, 5), date(2014, 5, 6), date(2014, 5, 7), date(2014, 5, 8));

        Date fromDate = date(2014, 5, 5);
        Date toDate = date(2014, 5, 8);
        DateRange range = fromDate.to(toDate);

        List<Date> actual = new LinkedList<Date>();
        for (Date d: range.javaIterable()) {
            actual.add(d);
        }

        assertThat(actual, is(expected));
    }

}
