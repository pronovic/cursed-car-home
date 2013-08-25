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

import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;

/**
 * Thread that cleans up after Car Home.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockEventCleanupThread implements Runnable {

    /** Number of nanoseconds per millisecond. */
    private static final long NANOSECONDS_PER_MILLISECOND = 1000000;

    /** Thread event tied to this thread. */
    private DockCleanupEvent event;

    /** Parent service associated with this thread. */
    private DockEventCleanupService parent;

    /** Application context associated with parent. */
    private Context context;

    /** Configuration for the application. */
    private CursedCarHomeConfig config;

    /** Constructor. */
    private DockEventCleanupThread(DockEventCleanupService parent, Context context, CursedCarHomeConfig config) {
        this.event = new DockCleanupEvent();
        this.parent = parent;
        this.context = context;
        this.config = config;
    }

    /** Start a new thread. */
    protected static void startThread(DockEventCleanupService parent, Context context) {
        CursedCarHomeConfig config = new CursedCarHomeConfig(context);
        DockEventCleanupThread thread = new DockEventCleanupThread(parent, context, config);
        new Thread(thread).start();
    }

    /** Get the parent service associated with this thread. */
    public DockEventCleanupService getParent() {
        return parent;
    }

    /** Get the application context associated with this thread. */
    public Context getContext() {
        return this.context;
    }

    /** Get the current configuration. */
    public CursedCarHomeConfig getConfig() {
        return this.config;
    }

    /** Thread main routine. */
    public void run() {
        Log.d("CursedCarHome", "DockEventCleanupThread: starting");

        if (!this.config.getMonitoringEnabled()) {
            Log.d("CursedCarHome", "DockEventCleanupThread: CarHome monitoring is not enabled");
        } else {
            Log.d("CursedCarHome", "DockEventCleanupThread: CarHome monitoring is enabled");
            this.event.markStart();
            this.watchForAWhile();
            this.event.markStop();
            this.logEvent();
        }

        Log.d("CursedCarHome", "DockEventCleanupThread: stopping parent");
        this.stopParent();

        Log.d("CursedCarHome", "DockEventCleanupThread: completed");
    }

    /** Watch the system for a while, cleaning up CarHome if it rears its ugly head. */
    private void watchForAWhile() {
        int delay = this.config.getInitialDelayMs();
        long limit = System.nanoTime() + (this.config.getMaxLifetimeMs() * NANOSECONDS_PER_MILLISECOND);
        while (System.nanoTime() <= limit) {
            this.disableCarModeIfNecessary();
            forceStopCarHomeApp();
            delay = delay * 2 >= this.config.getMaxDelayMs() ? this.config.getMaxDelayMs() : delay * 2;
            sleep(delay);
        }
    }

    /** Stop the parent service. */
    private void stopParent() {
        this.getParent().stopSelf();
    }

    /** Exit from car mode. */
    private void disableCarModeIfNecessary() {
        UiModeManager uiModeManager = this.getUiModeManager();
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_CAR) {
            Log.d("CursedCarHome", "DockEventCleanupThread: UI mode was set to car, disabling now");
            uiModeManager.disableCarMode(0);
            this.event.markDisableAttempt();
        }
    }

    /** Try to force-stop the CarHome application, if it's running. */
    private void forceStopCarHomeApp() {
        try {
            Log.d("CursedCarHome", "DockEventCleanupThread: killing background packages for \"com.google.android.carhome\" package");
            ActivityManager activityManager = (ActivityManager) this.getContext().getSystemService(Context.ACTIVITY_SERVICE);
            activityManager.killBackgroundProcesses("com.google.android.carhome");  // requires KILL_BACKGROUND_PROCESSES
            this.event.markKillAttempt();
        } catch (Exception e) { }
    }

    /** Sleep for a defined number of milliseconds, ignoring interrupts. */
    private static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) { }
    }

    /** Get a reference to the UIModeManager from the application context. */
    private UiModeManager getUiModeManager() {
        return (UiModeManager) this.getContext().getSystemService(Context.UI_MODE_SERVICE);
    }

    /** Log the thread event to the database. */
    private void logEvent() {
        EventDatabase database = new EventDatabase(this.getContext());
        database.insertEvent(this.event);
    }

}
