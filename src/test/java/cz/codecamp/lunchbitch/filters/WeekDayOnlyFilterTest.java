package cz.codecamp.lunchbitch.filters;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

public class WeekDayOnlyFilterTest {


    @Test
    public void sundayIsWeekend() {
        LocalDateTime sundayDay = LocalDateTime.of(2017, Month.JANUARY, 8, 11, 0, 0);
        assertTrue(new WeekDayOnlyFilter().isWeekend(sundayDay));
    }

    @Test
    public void mondayIsNotWeekend() {
        LocalDateTime mondayDay = LocalDateTime.of(2017, Month.JANUARY, 9, 11, 0, 0);
        assertFalse(new WeekDayOnlyFilter().isWeekend(mondayDay));
    }

    @Test
    public void fridayIsNotWeekend() {
        LocalDateTime fridayDay = LocalDateTime.of(2017, Month.JANUARY, 6, 11, 0, 0);
        assertFalse(new WeekDayOnlyFilter().isWeekend(fridayDay));
    }

    @Test
    public void saturdayIsWeekend() {
        LocalDateTime saturdayDay = LocalDateTime.of(2017, Month.JANUARY, 7, 11, 0, 0);
        assertTrue(new WeekDayOnlyFilter().isWeekend(saturdayDay));
    }

}