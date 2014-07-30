package iolamma4j.stackoverflow;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/21848648/date-list-increment-method-in-java
 */
public class Q21848648 {

    @Test
    public void test() {
        List<Date> expected = Lists.newArrayList(
                new Date(2014, 1, 1), new Date(2014, 1, 2), new Date(2014, 1, 3), new Date(2014, 1, 4), new Date(2014, 1, 5)
        );

        List<Date> actual = Dates.from(2014, 1, 1).to(2014, 1, 5).build();

        assertEquals(expected, actual);
    }
}
