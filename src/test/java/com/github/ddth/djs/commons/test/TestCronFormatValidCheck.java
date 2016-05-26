package com.github.ddth.djs.commons.test;

import com.github.ddth.djs.utils.CronFormat;

import junit.framework.TestCase;

public class TestCronFormatValidCheck extends TestCase {
    /**
     * 
     */
    public void test1() {
        assertTrue(CronFormat.isValidSecondPattern("*"));
        assertTrue(CronFormat.isValidSecondPattern("*/1"));
        assertTrue(CronFormat.isValidSecondPattern("*/2"));
        assertTrue(CronFormat.isValidSecondPattern("*/3"));
        assertFalse(CronFormat.isValidSecondPattern("*/0"));

        assertTrue(CronFormat.isValidSecondPattern("0,1,3,5,59"));
        assertTrue(CronFormat.isValidSecondPattern("0,1,3-5,6,7-9"));
        assertFalse(CronFormat.isValidSecondPattern("*/1,3,5"));
        assertFalse(CronFormat.isValidSecondPattern("1,60"));
        assertFalse(CronFormat.isValidSecondPattern("1,55-60"));
    }

    public void test2() {
        assertTrue(CronFormat.isValidMinutePattern("*"));
        assertTrue(CronFormat.isValidMinutePattern("*/1"));
        assertTrue(CronFormat.isValidMinutePattern("*/2"));
        assertTrue(CronFormat.isValidMinutePattern("*/3"));
        assertFalse(CronFormat.isValidMinutePattern("*/0"));

        assertTrue(CronFormat.isValidSecondPattern("0,1,3,5,59"));
        assertTrue(CronFormat.isValidMinutePattern("0,1,3-5,6,7-9"));
        assertFalse(CronFormat.isValidMinutePattern("*/1,3,5"));
        assertFalse(CronFormat.isValidSecondPattern("1,60"));
        assertFalse(CronFormat.isValidMinutePattern("1,55-60"));
    }

    public void test3() {
        assertTrue(CronFormat.isValidHourPattern("*"));
        assertTrue(CronFormat.isValidHourPattern("*/1"));
        assertTrue(CronFormat.isValidHourPattern("*/2"));
        assertTrue(CronFormat.isValidHourPattern("*/3"));
        assertFalse(CronFormat.isValidHourPattern("*/0"));

        assertTrue(CronFormat.isValidHourPattern("0,1,3,5,23"));
        assertTrue(CronFormat.isValidHourPattern("0,1,3-5,6,7-9"));
        assertFalse(CronFormat.isValidHourPattern("*/1,3,5"));
        assertFalse(CronFormat.isValidHourPattern("1,24"));
        assertFalse(CronFormat.isValidHourPattern("1,22-24"));
    }

    public void test4() {
        assertTrue(CronFormat.isValidDayPattern("*"));
        assertTrue(CronFormat.isValidDayPattern("*/1"));
        assertTrue(CronFormat.isValidDayPattern("*/2"));
        assertTrue(CronFormat.isValidDayPattern("*/3"));
        assertFalse(CronFormat.isValidDayPattern("*/0"));

        assertTrue(CronFormat.isValidDayPattern("1,3,5,23,31"));
        assertTrue(CronFormat.isValidDayPattern("1,3-5,6,7-9"));
        assertFalse(CronFormat.isValidDayPattern("*/1,3,5"));
        assertFalse(CronFormat.isValidDayPattern("0,31"));
        assertFalse(CronFormat.isValidDayPattern("1,32"));
        assertFalse(CronFormat.isValidDayPattern("1,30-32"));
    }

    public void test5() {
        assertTrue(CronFormat.isValidMonthPattern("*"));
        assertTrue(CronFormat.isValidMonthPattern("*/1"));
        assertTrue(CronFormat.isValidMonthPattern("*/2"));
        assertTrue(CronFormat.isValidMonthPattern("*/3"));
        assertFalse(CronFormat.isValidMonthPattern("*/0"));

        assertTrue(CronFormat.isValidMonthPattern("1,3,5,7"));
        assertTrue(CronFormat.isValidMonthPattern("JANuary,mar,MAY,july"));

        // cannot mix month's numbers and names
        assertFalse(CronFormat.isValidMonthPattern("1,March-MAY,JunE,jul-SEPTEMPER"));

        assertTrue(CronFormat.isValidMonthPattern("1,3-5,6,7-9"));
        assertTrue(CronFormat.isValidMonthPattern("JANuary,March-MAY,JunE,jul-SEPTEMBER"));

        assertFalse(CronFormat.isValidMonthPattern("*/1,3,5"));
        assertFalse(CronFormat.isValidMonthPattern("0,12"));
        assertFalse(CronFormat.isValidMonthPattern("1,13"));
        assertFalse(CronFormat.isValidMonthPattern("1,10-13"));
    }

    public void test6() {
        assertTrue(CronFormat.isValidDowPattern("*"));
        assertTrue(CronFormat.isValidDowPattern("*/1"));
        assertTrue(CronFormat.isValidDowPattern("*/2"));
        assertTrue(CronFormat.isValidDowPattern("*/3"));
        assertFalse(CronFormat.isValidDowPattern("*/0"));

        assertTrue(CronFormat.isValidDowPattern("1,3,5,7"));
        assertTrue(CronFormat.isValidDowPattern("MONday,wed,THU,sunday"));

        // cannot mix dow's numbers and names
        assertFalse(CronFormat.isValidDowPattern("1,Tuesday-THU"));

        assertTrue(CronFormat.isValidDowPattern("1,2-4,5,6-7"));
        assertTrue(CronFormat.isValidDowPattern("MONday,Tue-THU,FridaY,sat-SUNDAY"));

        assertFalse(CronFormat.isValidDowPattern("*/1,3,5"));
        assertFalse(CronFormat.isValidDowPattern("0,7"));
        assertFalse(CronFormat.isValidDowPattern("1,8"));
        assertFalse(CronFormat.isValidDowPattern("1,6-8"));
    }
}
