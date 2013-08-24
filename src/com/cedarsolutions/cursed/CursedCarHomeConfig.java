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
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Configuration tied to this application.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class CursedCarHomeConfig {

    /** Default value for speakerEnabled. */
    public static final boolean DEFAULT_SPEAKER_ENABLED = true;

    /** Default value for monitoringEnabled. */
    public static final boolean DEFAULT_MONITORING_ENABLED = true;

    /** Default value for maxLifetimeMs. */
    public static final String DEFAULT_MAX_LIFETIME_MS = "120000";

    /** Default value for initialDelayMs. */
    public static final String DEFAULT_INITIAL_DELAY_MS = "100";

    /** Default value for maxDelayMs. */
    public static final String DEFAULT_MAX_DELAY_MS = "5000";

    /** Default value for dailyReportEnabled. */
    public static final boolean DEFAULT_DAILY_REPORT_ENABLED = false;

    /** Default value for dailyReportTime. */
    public static final String DEFAULT_DAILY_REPORT_TIME = "1100";

    /** Whether speakerphone workaround is in enabled. */
    private boolean workaroundEnabled;

    /** Whether the CarHome monitoring process is enabled. */
    private boolean monitoringEnabled;

    /** Maximum lifetime of the CarHome monitoring process, in milliseconds. */
    private int maxLifetimeMs;

    /** Initial delay before starting the monitoring process, in milliseconds. */
    private int initialDelayMs;

    /** Maximum delay between monitoring process checks, in milliseconds. */
    private int maxDelayMs;

    /** Whether daily reporting is enabled. */
    private boolean dailyReportEnabled;

    /** Time of day to run the daily report, if enabled. */
    private String dailyReportTime;

    /** Create a empty configuration. */
    public CursedCarHomeConfig() {
    }

    /** Create configuration based on an application context. */
    public CursedCarHomeConfig(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.workaroundEnabled = preferences.getBoolean("workaroundEnabled", DEFAULT_SPEAKER_ENABLED);
        this.monitoringEnabled = preferences.getBoolean("monitoringEnabled", DEFAULT_MONITORING_ENABLED);
        this.maxLifetimeMs = Integer.parseInt(preferences.getString("maxLifetimeMs", DEFAULT_MAX_LIFETIME_MS));
        this.initialDelayMs = Integer.parseInt(preferences.getString("initialDelayMs", DEFAULT_INITIAL_DELAY_MS));
        this.maxDelayMs = Integer.parseInt(preferences.getString("maxDelayMs", DEFAULT_MAX_DELAY_MS));
        this.dailyReportEnabled = preferences.getBoolean("dailyReportEnabled", DEFAULT_DAILY_REPORT_ENABLED);
        this.dailyReportTime = preferences.getString("dailyReportTime", DEFAULT_DAILY_REPORT_TIME);
    }

    public boolean getWorkaroundEnabled() {
        return workaroundEnabled;
    }

    public void setWorkaroundEnabled(boolean workaroundEnabled) {
        this.workaroundEnabled = workaroundEnabled;
    }

    public boolean getMonitoringEnabled() {
        return monitoringEnabled;
    }

    public void setMonitoringEnabled(boolean monitoringEnabled) {
        this.monitoringEnabled = monitoringEnabled;
    }

    public int getMaxLifetimeMs() {
        return maxLifetimeMs;
    }

    public void setMaxLifetimeMs(int maxLifetimeMs) {
        this.maxLifetimeMs = maxLifetimeMs;
    }

    public int getInitialDelayMs() {
        return initialDelayMs;
    }

    public void setInitialDelayMs(int initialDelayMs) {
        this.initialDelayMs = initialDelayMs;
    }

    public int getMaxDelayMs() {
        return maxDelayMs;
    }

    public void setMaxDelayMs(int maxDelayMs) {
        this.maxDelayMs = maxDelayMs;
    }

    public boolean getDailyReportEnabled() {
        return dailyReportEnabled;
    }

    public void setDailyReportEnabled(boolean dailyReportEnabled) {
        this.dailyReportEnabled = dailyReportEnabled;
    }

    public String getDailyReportTime() {
        return dailyReportTime;
    }

    public void setDailyReportTime(String dailyReportTime) {
        this.dailyReportTime = dailyReportTime;
    }
}
