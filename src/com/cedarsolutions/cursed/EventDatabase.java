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

import android.content.ContentValues;
import android.content.Context;
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
    public void insertEvent(ThreadEvent event) {
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

}
