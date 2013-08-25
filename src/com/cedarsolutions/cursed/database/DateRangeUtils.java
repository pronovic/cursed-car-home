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
 * Language : Java 6
 * Project  : Cursed Car Home
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.cursed.database;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Utilities tied to date range.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DateRangeUtils {

    /** Date range object. */
    public static class DateRange {
        private long start;
        private long end;

        public long getStart() {
            return this.start;
        }

        public void setStart(long start) {
            this.start = start;
        }

        public long getEnd() {
            return this.end;
        }

        public void setEnd(long end) {
            this.end = end;
        }
    }

    /** Generate a a date range for the daily report (past 24 hours). */
    public static DateRange generateDailyRange() {
        DateRange range = new DateRange();

        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        range.setEnd(calendar.getTime().getTime());

        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 24); // past 24 hours
        range.setStart(calendar.getTime().getTime());

        return range;
    }

    /** Generate a limit that defines which data to purge (older than 1 month). */
    public static long generatePurgeLimit() {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getTime().getTime();
    }

}
