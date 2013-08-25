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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Provides operations on the event database.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EventDatabase {

    /** Underlying SQLite database. */
    private SQLiteDatabase database;

    /** Create an event database. */
    public EventDatabase(Context context) {
        EventDatabaseHelper helper = new EventDatabaseHelper(context);
        this.database = helper.getWritableDatabase();
    }

    /** Insert an event. */
    public void insertEvent(DockCleanupEvent event) {
        ContentValues values = new ContentValues();
        values.put("id", event.getId());
        values.put("thread_start", event.getStartTime());
        values.put("thread_stop", event.getStopTime());
        values.put("disable_attempts", event.getDisableAttempts());
        values.put("kill_attempts", event.getKillAttempts());
        long result = this.database.insert("event", null, values);
        if (result == -1) {
            Log.e("CursedCarHome", "EventDatabase.insertEvent(): failed to insert: " + event.toString());
        } else {
            Log.i("CursedCarHome", "EventDatabase.insertEvent(): inserted: " + event.toString());
        }
    }

    /** Delete old data. */
    public void deleteOldData() {
        long limit = generateDeleteLimit();
        Log.d("CursedCarHome", "EventDatabase: deleting data older than 1 month (<= " + DateUtils.formatIso8601(limit) + ")");
        this.database.delete("event", "thread_start <= ?", new String[] { Long.toString(limit), });
    }

    /** Create the daily report. */
    public DockCleanupReport createDockCleanupReport() {
        DateRange range = generateDateRange();
        Log.d("CursedCarHome", "EventDatabase: using date range \"between " + range.start + " and " + range.end + "\" for query");

        DockCleanupReport report = new DockCleanupReport();
        report.setReportStart(DateUtils.formatIso8601(range.start));
        report.setReportEnd(DateUtils.formatIso8601(range.end));
        Log.d("CursedCarHome", "EventDatabase: report start is " + report.getReportStart());
        Log.d("CursedCarHome", "EventDatabase: report end is " + report.getReportEnd());

        this.fillDisableAttempts(report, range.start, range.end);
        this.fillStartTimes(report, range.start, range.end);
        report.setEventsHandled(report.getStartTimes().size());

        return report;
    }

    /** Fill disable attempts into a report. */
    private void fillDisableAttempts(DockCleanupReport report, long start, long end) {
        Cursor c = null;

        try {
            String sql = "select coalesce(sum(disable_attempts), 0) from event where thread_start between ? and ?";
            c = this.database.rawQuery(sql, new String[] { Long.toString(start), Long.toString(end), });
            c.moveToFirst();
            int disableAttempts = Integer.parseInt(c.getString(0));
            report.setDisableAttempts(disableAttempts);
            Log.d("CursedCarHome", "EventDatabase: disableAttempts = " + report.getDisableAttempts());
        } catch (Exception e) {
            Log.e("CursedCarHome", "EventDatabase: failed to retrieve disable attempts: " + e.getMessage());
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
            String sql = "select thread_start from event where thread_start between ? and ?";
            c = this.database.rawQuery(sql, new String[] { Long.toString(start), Long.toString(end), });
            while (c.moveToNext()) {
                String startTime = DateUtils.formatIso8601(Long.parseLong(c.getString(0)));
                report.getStartTimes().add(startTime);
                Log.d("CursedCarHome", "EventDatabase: added start time = " + startTime);
            }
        } catch (Exception e) {
            Log.e("CursedCarHome", "EventDatabase: failed to retrieve start times: " + e.getMessage());
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

    /** Generate a 1-day date range for the previous day. */
    private static DateRange generateDateRange() {
        DateRange range = new DateRange();

        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        range.end = calendar.getTime().getTime();

        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 24); // past 24 hours
        range.start = calendar.getTime().getTime();

        return range;
    }

    /** Generate a limit that defines data older than 5 weeks. */
    private static long generateDeleteLimit() {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getTime().getTime();
    }

}
