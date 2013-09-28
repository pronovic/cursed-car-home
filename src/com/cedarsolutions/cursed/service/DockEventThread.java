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
package com.cedarsolutions.cursed.service;

import static com.cedarsolutions.cursed.util.DateUtils.convertMillisecondsToNanoseconds;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.content.Context;
import android.content.res.Configuration;

import com.cedarsolutions.cursed.config.CursedCarHomeConfig;
import com.cedarsolutions.cursed.database.DockCleanupDatabase;
import com.cedarsolutions.cursed.database.DockCleanupEvent;
import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * Thread that cleans up after Car Home.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockEventThread implements Runnable {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(DockEventThread.class);

    /** Dock cleanup event tied to this thread. */
    private DockCleanupEvent event;

    /** Parent service associated with this thread. */
    private DockEventService parent;

    /** Application context associated with parent. */
    private Context context;

    /** Configuration for the application. */
    private CursedCarHomeConfig config;

    /** Constructor. */
    private DockEventThread(DockEventService parent, Context context, CursedCarHomeConfig config) {
        this.event = new DockCleanupEvent();
        this.parent = parent;
        this.context = context;
        this.config = config;
    }

    /** Start a new thread. */
    protected static void startThread(DockEventService parent, Context context) {
        CursedCarHomeConfig config = new CursedCarHomeConfig(context);
        DockEventThread thread = new DockEventThread(parent, context, config);
        new Thread(thread).start();
    }

    /** Get the parent service associated with this thread. */
    public DockEventService getParent() {
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
    @Override
    public void run() {
        LOGGER.debug("DockEventCleanupThread starting");

        if (isEnabled(this.getConfig())) {
            LOGGER.debug("Monitoring is not enabled");
        } else {
            LOGGER.debug("Monitoring is enabled");
            this.markStart();
            this.watchForAWhile();
            this.markStop();
        }

        this.stopParent();
        LOGGER.debug("DockEventCleanupThread completed");
    }

    /** Whether monitoring is enabled. */
    private static boolean isEnabled(CursedCarHomeConfig config) {
        // The config screen doesn't allow the user to set zeroes, so if any
        // are set, it's not configured.  Treat that as disabled.
        return (!config.getMonitoringEnabled()) ||
               config.getInitialDelayMs() == 0 ||
               config.getMaxDelayMs() == 0 ||
               config.getMaxLifetimeMs() == 0;
    }

    /** Watch the system for a while, cleaning up CarHome if it rears its ugly head. */
    private void watchForAWhile() {
        int delay = this.config.getInitialDelayMs();
        long limit = System.nanoTime() + convertMillisecondsToNanoseconds(this.config.getMaxLifetimeMs());
        while (System.nanoTime() <= limit) {
            this.disableCarModeIfNecessary();
            this.forceStopCarHomeApp();
            this.updateDatabase();
            delay = delay * 2 >= this.config.getMaxDelayMs() ? this.config.getMaxDelayMs() : delay * 2;
            sleep(delay);
        }
    }

    /** Stop the parent service. */
    private void stopParent() {
        LOGGER.debug("Stopping parent service");
        this.getParent().stopSelf();
    }

    /** Exit from car mode. */
    private void disableCarModeIfNecessary() {
        UiModeManager uiModeManager = this.getUiModeManager();
        if (uiModeManager.getCurrentModeType() == Configuration.UI_MODE_TYPE_CAR) {
            LOGGER.debug("UI mode was set to car, disabling now");
            uiModeManager.disableCarMode(0);
            this.event.markDisableAttempt();
        }
    }

    /** Try to force-stop the CarHome application, if it's running. */
    private void forceStopCarHomeApp() {
        try {
            LOGGER.debug("Killing background packages for \"com.google.android.carhome\" package");
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

    /** Mark that the thread started, and insert the related event. */
    private void markStart() {
        LOGGER.debug("Marking thread start.");
        DockCleanupDatabase database = new DockCleanupDatabase(this.getContext());
        this.event.markStart();
        database.insertEvent(this.event);
    }

    /** Mark that the thread stopped, and update the related event. */
    private void markStop() {
        LOGGER.debug("Marking thread stop.");
        DockCleanupDatabase database = new DockCleanupDatabase(this.getContext());
        this.event.markStop();
        database.updateEvent(this.event);
    }

    /** Update the event in the database with the latest counts. */
    private void updateDatabase() {
        LOGGER.debug("Updating event in database.");
        DockCleanupDatabase database = new DockCleanupDatabase(this.getContext());
        database.updateEvent(this.event);
        LOGGER.debug("Done updating event in database.");
    }

}
