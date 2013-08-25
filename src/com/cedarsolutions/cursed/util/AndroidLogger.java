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
package com.cedarsolutions.cursed.util;

import android.util.Log;

/**
 * A logger that standardize behavior.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AndroidLogger {

    // To see logs from this logger, use:
    //    C:\Ken\Program Files\Android\android-sdk\platform-tools>adb logcat CursedCarHome:D *:S

    /** Tag that will always be used by this logger. */
    private static final String TAG = "CursedCarHome";

    /** Name of the log source, derived from the class. */
    private String source;

    /** Create a logger for a class. */
    private AndroidLogger(Class<?> clazz) {
        this(clazz.getSimpleName());
    }

    /** Create a logger for a string source. */
    private AndroidLogger(String source) {
        this.source = source;
    }

    /** Get a logger for a class. */
    public static AndroidLogger getLogger(Class<?> clazz) {
        return new AndroidLogger(clazz);
    }

    /** Get a logger for a string source. */
    public static AndroidLogger getLogger(String source) {
        return new AndroidLogger(source);
    }

    /** Log a debug message. */
    public void debug(String message) {
        Log.d(TAG, format(Level.DEBUG, message));
    }

    /** Log an info message. */
    public void info(String message) {
        Log.i(TAG, format(Level.INFO, message));
    }

    /** Log a warning message.  */
    public void warn(String message) {
        Log.w(TAG, format(Level.WARN, message));
    }

    /** Log an error message. */
    public void error(String message) {
        Log.e(TAG, format(Level.ERROR, message));
    }

    /** Format a message. */
    private String format(Level level, String message) {
        String date = DateUtils.formatIso8601(System.currentTimeMillis());
        return date + " [" + level + "] --> [" + this.source + "] " + message;
    }

    /** Log level. */
    private enum Level {
        DEBUG,
        INFO,
        WARN,
        ERROR
    }

}
