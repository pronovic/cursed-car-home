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
package com.cedarsolutions.cursed.database;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cedarsolutions.cursed.util.AndroidLogger;
import com.cedarsolutions.cursed.util.DateUtils;

/**
 * Provides operations on the dock cleanup event database.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockCleanupDatabase {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(DockCleanupDatabase.class);

    /** Underlying SQLite database. */
    private SQLiteDatabase database;

    /** Create a database. */
    public DockCleanupDatabase(Context context) {
        DockCleanupDatabaseHelper helper = new DockCleanupDatabaseHelper(context);
        this.database = helper.getWritableDatabase();
    }

    /** Insert an event into the database. */
    public void insertEvent(DockCleanupEvent event) {
        ContentValues values = new ContentValues();
        values.put("id", event.getId());
        values.put("thread_start", event.getStartTime());
        values.put("thread_stop", event.getStopTime());
        values.put("disable_attempts", event.getDisableAttempts());
        values.put("kill_attempts", event.getKillAttempts());
        long result = this.database.insert("dock_cleanup_events", null, values);
        if (result == -1) {
            LOGGER.error("Failed to insert: " + event.toString());
        } else {
            LOGGER.debug("Inserted: " + event.toString());
        }
    }

    /** Purge old data from the database. */
    public void purgeOldData() {
        long limit = generatePurgeLimit();
        LOGGER.debug("Deleting data older than 1 month (<= " + DateUtils.formatIso8601Utc(limit) + ")");
        this.database.delete("dock_cleanup_events", "thread_start <= ?", new String[] { Long.toString(limit), });
    }

    /** Create a DockCleanupReport covering the last 24 hours. */
    public DockCleanupReport createDailyDockCleanupReport() {
        LOGGER.debug("Creating dock cleanup report.");

        DateRange range = generateDailyRange();
        LOGGER.debug("Using date range \"between " + range.start + " and " + range.end + "\" for query");

        DockCleanupReport report = new DockCleanupReport();
        report.setReportStart(DateUtils.formatIso8601(range.start));
        report.setReportEnd(DateUtils.formatIso8601(range.end));
        LOGGER.debug("Report start is " + report.getReportStart());
        LOGGER.debug("Report end is " + report.getReportEnd());

        this.fillDisableAttempts(report, range.start, range.end);
        this.fillStartTimes(report, range.start, range.end);
        report.setEventsHandled(report.getStartTimes().size());

        return report;
    }

    /** Fill disable attempts into a report. */
    private void fillDisableAttempts(DockCleanupReport report, long start, long end) {
        Cursor c = null;

        try {
            String sql = "select coalesce(sum(disable_attempts), 0) from dock_cleanup_events where thread_start between ? and ?";
            c = this.database.rawQuery(sql, new String[] { Long.toString(start), Long.toString(end), });
            c.moveToFirst();
            int disableAttempts = Integer.parseInt(c.getString(0));
            report.setDisableAttempts(disableAttempts);
            LOGGER.debug("Retrieved disableAttempts = " + report.getDisableAttempts());
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve disable attempts: " + e.getMessage());
            report.setDisableAttempts(0);
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
    }

    /** Fill start times into a report. */
    private void fillStartTimes(DockCleanupReport report, long start, long end) {
        Cursor c = null;

        try {
            String sql = "select thread_start from dock_cleanup_events where thread_start between ? and ?";
            c = this.database.rawQuery(sql, new String[] { Long.toString(start), Long.toString(end), });
            while (c.moveToNext()) {
                String startTime = DateUtils.formatIso8601Utc(Long.parseLong(c.getString(0)));
                report.getStartTimes().add(startTime);
                LOGGER.debug("Added start time = " + startTime);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve start times: " + e.getMessage());
            report.getStartTimes().clear();
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
    }

    /** Date range object. */
    private static class DateRange {
        protected long start;
        protected long end;
    }

    /** Generate a a date range for the daily report (past 24 hours). */
    private static DateRange generateDailyRange() {
        DateRange range = new DateRange();

        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        range.end = calendar.getTime().getTime();

        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 24); // past 24 hours
        range.start = calendar.getTime().getTime();

        return range;
    }

    /** Generate a limit that defines which data to purge (older than 1 month). */
    private static long generatePurgeLimit() {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getTime().getTime();
    }

}
