package iolamma.stackoverflow;

import static org.junit.Assert.*;

import io.lamma.Date;
import io.lamma.DayOfWeek;
import org.junit.Test;

/**
 * http://stackoverflow.com/questions/12283794/determinining-the-date-of-a-particular-day-of-week
 */
public class Q12283794 {

    @Test
    public void test() {
        assertEquals(new Date(2014, 6, 27), new Date(2014, 7, 1).previous(DayOfWeek.FRIDAY));
        assertEquals(new Date(2014, 6, 27), new Date(2014, 7, 2).previous(DayOfWeek.FRIDAY));
        assertEquals(new Date(2014, 6, 27), new Date(2014, 7, 3).previous(DayOfWeek.FRIDAY));
        assertEquals(new Date(2014, 6, 27), new Date(2014, 7, 4).previous(DayOfWeek.FRIDAY));
        assertEquals(new Date(2014, 7, 4), new Date(2014, 7, 5).previous(DayOfWeek.FRIDAY));
        assertEquals(new Date(2014, 7, 4), new Date(2014, 7, 6).previous(DayOfWeek.FRIDAY));
        assertEquals(new Date(2014, 7, 4), new Date(2014, 7, 7).previous(DayOfWeek.FRIDAY));
    }

}
