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
import android.util.Log;

/**
 * Thread that cleans up the event database.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DailyCleanupThread implements Runnable {

    /** Parent service associated with this thread. */
    private DailyCleanupService parent;

    /** Application context associated with parent. */
    private Context context;

    /** Constructor. */
    private DailyCleanupThread(DailyCleanupService parent, Context context) {
        this.parent = parent;
        this.context = context;
    }

    /** Start a new thread. */
    protected static void startThread(DailyCleanupService parent, Context context) {
        DailyCleanupThread thread = new DailyCleanupThread(parent, context);
        new Thread(thread).start();
    }

    /** Get the parent service associated with this thread. */
    public DailyCleanupService getParent() {
        return parent;
    }

    /** Get the application context associated with this thread. */
    public Context getContext() {
        return this.context;
    }

    /** Thread main routine. */
    public void run() {
        Log.d("CursedCarHome", "DailyCleanupThread: starting");

        Log.d("CursedCarHome", "DailyCleanupThread: stopping parent");
        this.stopParent();

        Log.d("CursedCarHome", "DailyCleanupThread: completed");
    }

    /** Stop the parent service. */
    private void stopParent() {
        this.getParent().stopSelf();
    }

}
