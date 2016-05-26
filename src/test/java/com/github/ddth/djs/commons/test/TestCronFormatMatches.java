package com.github.ddth.djs.commons.test;

import com.github.ddth.djs.utils.CronFormat;

import junit.framework.TestCase;

public class TestCronFormatMatches extends TestCase {
    public void test1() {
        String pattern = "*";
        assertTrue(CronFormat.matches(1, pattern));
    }

    public void test2() {
        String pattern = "*/2";
        assertFalse(CronFormat.matches(1, pattern));
        assertTrue(CronFormat.matches(4, pattern));
    }

    public void test3() {
        String pattern = "1,3,5";
        assertTrue(CronFormat.matches(1, pattern));
        assertTrue(CronFormat.matches(3, pattern));
        assertTrue(CronFormat.matches(5, pattern));
        assertFalse(CronFormat.matches(2, pattern));
        assertFalse(CronFormat.matches(4, pattern));
        assertFalse(CronFormat.matches(6, pattern));
    }

    public void test4() {
        String pattern = "2,4,6-10,12,14-20";
        assertTrue(CronFormat.matches(2, pattern));
        assertTrue(CronFormat.matches(4, pattern));
        assertTrue(CronFormat.matches(6, pattern));
        assertTrue(CronFormat.matches(8, pattern));
        assertTrue(CronFormat.matches(10, pattern));
        assertTrue(CronFormat.matches(12, pattern));
        assertTrue(CronFormat.matches(14, pattern));
        assertTrue(CronFormat.matches(16, pattern));
        assertTrue(CronFormat.matches(18, pattern));
        assertTrue(CronFormat.matches(20, pattern));
        assertFalse(CronFormat.matches(1, pattern));
        assertFalse(CronFormat.matches(3, pattern));
        assertFalse(CronFormat.matches(5, pattern));
        assertFalse(CronFormat.matches(11, pattern));
        assertFalse(CronFormat.matches(13, pattern));
        assertFalse(CronFormat.matches(21, pattern));
    }

    public void test5() {
        String pattern = "*";
        assertTrue(CronFormat.matchesDow(CronFormat.MONDAY, pattern));
        assertFalse(CronFormat.matchesDow(0, pattern));
        assertFalse(CronFormat.matchesDow(10, pattern));
    }

    public void test6() {
        String pattern = "mon,TUE,Wednesday";
        assertTrue(CronFormat.matchesDow(CronFormat.MONDAY, pattern));
        assertTrue(CronFormat.matchesDow(CronFormat.TUESDAY, pattern));
        assertTrue(CronFormat.matchesDow(CronFormat.WEDNESDAY, pattern));
        assertFalse(CronFormat.matchesDow(CronFormat.THURSDAY, pattern));
        assertFalse(CronFormat.matchesDow(CronFormat.FRIDAY, pattern));
        assertFalse(CronFormat.matchesDow(CronFormat.SATURDAY, pattern));
        assertFalse(CronFormat.matchesDow(CronFormat.SUNDAY, pattern));
        assertFalse(CronFormat.matchesDow(0, pattern));
        assertFalse(CronFormat.matchesDow(10, pattern));
    }

    public void test7() {
        String pattern = "TUE,ThU-Saturday";
        assertFalse(CronFormat.matchesDow(CronFormat.MONDAY, pattern));
        assertTrue(CronFormat.matchesDow(CronFormat.TUESDAY, pattern));
        assertFalse(CronFormat.matchesDow(CronFormat.WEDNESDAY, pattern));
        assertTrue(CronFormat.matchesDow(CronFormat.THURSDAY, pattern));
        assertTrue(CronFormat.matchesDow(CronFormat.FRIDAY, pattern));
        assertTrue(CronFormat.matchesDow(CronFormat.SATURDAY, pattern));
        assertFalse(CronFormat.matchesDow(CronFormat.SUNDAY, pattern));
        assertFalse(CronFormat.matchesDow(0, pattern));
        assertFalse(CronFormat.matchesDow(10, pattern));
    }

    public void test8() {
        String pattern = "*";
        assertTrue(CronFormat.matchesMonth(CronFormat.APRIL, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.MARCH, pattern));
        assertFalse(CronFormat.matchesMonth(0, pattern));
        assertFalse(CronFormat.matchesMonth(13, pattern));
    }

    public void test9() {
        String pattern = "jan,MAR,May";
        assertTrue(CronFormat.matchesMonth(CronFormat.JANUARY, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.FEBRUARY, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.MARCH, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.APRIL, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.MAY, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.JUNE, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.JULY, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.AUGUST, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.SEPTEMBER, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.OCTOBER, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.NOVEMBER, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.DECEMBER, pattern));
        assertFalse(CronFormat.matchesMonth(0, pattern));
        assertFalse(CronFormat.matchesMonth(13, pattern));
    }

    public void test10() {
        String pattern = "feb,APR-JuNe,AUGUST-october";
        assertFalse(CronFormat.matchesMonth(CronFormat.JANUARY, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.FEBRUARY, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.MARCH, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.APRIL, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.MAY, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.JUNE, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.JULY, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.AUGUST, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.SEPTEMBER, pattern));
        assertTrue(CronFormat.matchesMonth(CronFormat.OCTOBER, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.NOVEMBER, pattern));
        assertFalse(CronFormat.matchesMonth(CronFormat.DECEMBER, pattern));
        assertFalse(CronFormat.matchesMonth(0, pattern));
        assertFalse(CronFormat.matchesMonth(13, pattern));
    }
}
