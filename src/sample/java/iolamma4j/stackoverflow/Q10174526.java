package iolamma4j.stackoverflow;

import com.google.common.collect.Lists;
import io.lamma.Date;
import io.lamma.Dates;
import io.lamma.HolidayRule;
import io.lamma.HolidayRules;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * http://stackoverflow.com/questions/10174526/generating-appointments-date
 */
public class Q10174526 {

    @Test
    public void test() {
        HolidayRule ukHoliday2015 = HolidayRules.newSimpleHolidayRule(
                new Date(2015, 1, 1), new Date(2015, 4, 3), new Date(2015, 4, 6),
                new Date(2015, 5, 4), new Date(2015, 5, 25), new Date(2015, 8, 31),
                new Date(2015, 12, 25), new Date(2015, 12, 28)
        );

        List<Date> expected = Lists.newArrayList(new Date(2015, 12, 23), new Date(2015, 12, 29));

        List<Date> actual = Dates.from(2015, 12, 23).to(2015, 12, 30).byDays(2).except(HolidayRules.WEEKENDS).except(ukHoliday2015).build();

        assertEquals(expected, actual);
    }
}
