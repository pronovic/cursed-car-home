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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cedarsolutions.cursed.database.DateRangeUtils.DateRange;
import com.cedarsolutions.cursed.util.AndroidLogger;
import com.cedarsolutions.cursed.util.DateUtils;

/**
 * Provides operations on the speakerphone cleanup event database.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SpeakerphoneCleanupDatabase {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(SpeakerphoneCleanupDatabase.class);

    /** Underlying SQLite database. */
    private SQLiteDatabase database;

    /** Create a database. */
    public SpeakerphoneCleanupDatabase(Context context) {
        SpeakerphoneCleanupDatabaseHelper helper = new SpeakerphoneCleanupDatabaseHelper(context);
        this.database = helper.getWritableDatabase();
    }

    /** Insert an event into the database. */
    public void insertEvent(SpeakerphoneCleanupEvent event) {
        ContentValues values = new ContentValues();
        values.put("id", event.getId());
        values.put("timestamp", event.getTimestamp());
        long result = this.database.insert("speakerphone_cleanup_events", null, values);
        if (result == -1) {
            LOGGER.error("Failed to insert: " + event.toString());
        } else {
            LOGGER.debug("Inserted: " + event.toString());
        }
    }

    /** Purge old data from the database. */
    public void purgeOldData() {
        long limit = DateRangeUtils.generatePurgeLimit();
        LOGGER.debug("Deleting data older than 1 month (<= " + DateUtils.formatIso8601Utc(limit) + ")");
        this.database.delete("speakerphone_cleanup_events", "timestamp <= ?", new String[] { Long.toString(limit), });
    }

    /** Create a SpeakerphoneCleanupReport covering the last 24 hours. */
    public SpeakerphoneCleanupReport createDailySpeakerphoneCleanupReport() {
        LOGGER.debug("Creating speakerphone cleanup report.");

        DateRange range = DateRangeUtils.generateDailyRange();
        LOGGER.debug("Using date range \"between " + range.getStart() + " and " + range.getEnd() + "\" for query");

        SpeakerphoneCleanupReport report = new SpeakerphoneCleanupReport();
        report.setReportStart(DateUtils.formatIso8601(range.getStart()));
        report.setReportEnd(DateUtils.formatIso8601(range.getEnd()));
        LOGGER.debug("Report start is " + report.getReportStart());
        LOGGER.debug("Report end is " + report.getReportEnd());

        this.fillTimestamps(report, range.getStart(), range.getEnd());
        report.setEventsHandled(report.getTimestamps().size());

        return report;
    }

    /** Fill timestamps into a report. */
    private void fillTimestamps(SpeakerphoneCleanupReport report, long start, long end) {
        Cursor c = null;

        try {
            String sql = "select timestamp from speakerphone_cleanup_events where timestamp between ? and ?";
            c = this.database.rawQuery(sql, new String[] { Long.toString(start), Long.toString(end), });
            while (c.moveToNext()) {
                String startTime = DateUtils.formatIso8601Utc(Long.parseLong(c.getString(0)));
                report.getTimestamps().add(startTime);
                LOGGER.debug("Added timestamp = " + startTime);
            }
        } catch (Exception e) {
            LOGGER.error("Failed to retrieve timestamps: " + e.getMessage());
            report.getTimestamps().clear();
        } finally {
            if (c != null) {
                c.close();
                c = null;
            }
        }
    }

}
