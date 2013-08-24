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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLite database helper class for the event database.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EventDatabaseHelper extends SQLiteOpenHelper {

    /** Database name. */
    private static final String DATABASE_NAME = "cursed_car_home_events";

    /** Database version. */
    private static final int DATABASE_VERSION = 1;

    /** Create a helper for a context. */
    public EventDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** Create the database. */
    @Override
    public void onCreate(SQLiteDatabase database) {
        Log.w("EventDatabaseHelper", "Creating event database.");
        database.execSQL(buildCreateSql());
    }

    /** Upgrade the database. */
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w("EventDatabaseHelper", "Upgrading to new version of event database, which will destroy old data.");
        database.execSQL(buildDropTableSql());
        onCreate(database);
    }

    /** Build the SQL create statement. */
    private static String buildCreateSql() {
        StringBuffer sql = new StringBuffer();
        sql.append("create table if not exists\n");
        sql.append("event(id string, \n");
        sql.append("      thread_start long, \n");
        sql.append("      thread_stop long, \n");
        sql.append("      disable_attempts int, \n");
        sql.append("      kill_attempts int)\n");
        return sql.toString();
    }

    /** Build the SQL drop table statement. */
    private static String buildDropTableSql() {
        return "drop table event";
    }
}
