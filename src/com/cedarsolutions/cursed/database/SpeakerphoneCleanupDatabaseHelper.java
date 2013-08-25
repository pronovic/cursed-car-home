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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * Database helper class for the speakerphone cleanup event database.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SpeakerphoneCleanupDatabaseHelper extends SQLiteOpenHelper {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(SpeakerphoneCleanupDatabaseHelper.class);

    /** Database name. */
    private static final String DATABASE_NAME = "speakerphone_cleanup_events";

    /** Database version. */
    private static final int DATABASE_VERSION = 1;

    /** Create a helper for a context. */
    public SpeakerphoneCleanupDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Create the database. */
    @Override
    public void onCreate(SQLiteDatabase database) {
        LOGGER.debug("Creating speakerphone cleanup event database.");
        database.execSQL(buildCreateSql());
    }

    /** Upgrade the database. */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        LOGGER.debug("Upgrading to new version of speakerphone cleanup event database, which will destroy old data.");
        database.execSQL(buildDropTableSql());
        this.onCreate(database);
    }

    /** Build the SQL create statement. */
    private static String buildCreateSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("create table if not exists\n");
        sql.append("speakerphone_cleanup_events(id string, \n");
        sql.append("                            timestamp long)\n");
        return sql.toString();
    }

    /** Build the SQL drop table statement. */
    private static String buildDropTableSql() {
        return "drop table speakerphone_cleanup_events";
    }
}
