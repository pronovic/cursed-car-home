/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 *              C E D A R
 *          S O L U T I O N S       "Software done right."
 *           S O F T W A R E
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2013 Kenneth J. Pronovici.
 * All rights reserved.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the Apache License, Version 2.0.
 * See LICENSE for more information about the licensing terms.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Author   : Kenneth J. Pronovici <pronovic@ieee.org>
 * Language : Java 7
 * Project  : Cursed Car Home
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.cursed;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

import com.cedarsolutions.cursed.util.DateUtils;

/**
 * Unit tests for DateUtils.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DateUtilsTest {

    /** Test createUtcDate(). */
    @Test public void testCreateUtcDate() {
        Date date = DateUtils.createUtcDate(2013, 8, 24, 13, 5, 26, 444);
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.setTime(date);
        assertEquals(2013, calendar.get(Calendar.YEAR));
        assertEquals(8 - 1, calendar.get(Calendar.MONTH));
        assertEquals(24, calendar.get(Calendar.DATE));
        assertEquals(13, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(5, calendar.get(Calendar.MINUTE));
        assertEquals(26, calendar.get(Calendar.SECOND));
        assertEquals(444, calendar.get(Calendar.MILLISECOND));
    }

    /** Test formatIso8601(). */
    @Test public void testFormatIso8601() {
        Date date = DateUtils.createUtcDate(2013, 8, 24, 13, 5, 26, 444);
        assertEquals("2013-08-24T13:05:26,444Z", DateUtils.formatIso8601Utc(date));
    }

    /** Test getUtcTime(). */
    @Test public void testGetUtcTime() {
        Date date = DateUtils.createUtcDate(2013, 8, 24, 13, 5, 26, 444);
        assertEquals("1305", DateUtils.getUtcTime(date));
    }

    /** Test getNextOccurrence(). */
    @Test public void testGetNextOccurrence() {
        Date now;
        String time;

        // Time slightly after now
        now = DateUtils.createUtcDate(2013, 8, 24, 2, 14, 26, 444);
        time = "0215";
        assertGetNextOccurrenceWorks("2013-08-24T02:15:00,000Z", now, time);
        
        // Time slightly after now
        now = DateUtils.createUtcDate(2013, 8, 24, 13, 5, 26, 444);
        time = "1306";
        assertGetNextOccurrenceWorks("2013-08-24T13:06:00,000Z", now, time);

        // Time exactly equal to now
        now = DateUtils.createUtcDate(2013, 8, 24, 13, 5, 26, 444);
        time = "1305";
        assertGetNextOccurrenceWorks("2013-08-25T13:05:00,000Z", now, time);

        // Time slightly before now
        now = DateUtils.createUtcDate(2013, 8, 24, 13, 5, 26, 444);
        time = "1304";
        assertGetNextOccurrenceWorks("2013-08-25T13:04:00,000Z", now, time);

        // Time slightly after now, right before midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 23, 58, 26, 444);
        time = "2359";
        assertGetNextOccurrenceWorks("2013-08-24T23:59:00,000Z", now, time);

        // Time equal now, right before midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 23, 58, 26, 444);
        time = "2358";
        assertGetNextOccurrenceWorks("2013-08-25T23:58:00,000Z", now, time);

        // Time slightly before now, right before midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 23, 58, 26, 444);
        time = "2357";
        assertGetNextOccurrenceWorks("2013-08-25T23:57:00,000Z", now, time);

        // Time slightly after now, right before midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 23, 59, 26, 444);
        time = "0000";
        assertGetNextOccurrenceWorks("2013-08-25T00:00:00,000Z", now, time);

        // Time equal now, right before midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 23, 59, 26, 444);
        time = "2359";
        assertGetNextOccurrenceWorks("2013-08-25T23:59:00,000Z", now, time);

        // Time slightly before now, right before midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 23, 59, 26, 444);
        time = "2358";
        assertGetNextOccurrenceWorks("2013-08-25T23:58:00,000Z", now, time);

        // Time slightly after now, right at midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 0, 0, 26, 444);
        time = "0001";
        assertGetNextOccurrenceWorks("2013-08-24T00:01:00,000Z", now, time);

        // Time equal now, right at midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 0, 0, 26, 444);
        time = "0000";
        assertGetNextOccurrenceWorks("2013-08-25T00:00:00,000Z", now, time);

        // Time slightly before now, right at midnight
        now = DateUtils.createUtcDate(2013, 8, 24, 0, 0, 26, 444);
        time = "2359";
        assertGetNextOccurrenceWorks("2013-08-24T23:59:00,000Z", now, time);

        // Time slightly before now, month rolls
        now = DateUtils.createUtcDate(2013, 8, 31, 13, 5, 26, 444);
        time = "1304";
        assertGetNextOccurrenceWorks("2013-09-01T13:04:00,000Z", now, time);

        // Time slightly before now, year rolls
        now = DateUtils.createUtcDate(2013, 12, 31, 13, 5, 26, 444);
        time = "1304";
        assertGetNextOccurrenceWorks("2014-01-01T13:04:00,000Z", now, time);
    }

    /** Assert that getNextOccurrence() works. */
    private static void assertGetNextOccurrenceWorks(String timestamp, Date now, String time) {
        assertEquals(timestamp, DateUtils.formatIso8601Utc(DateUtils.getNextUtcOccurrence(time, now)));
    }

}
