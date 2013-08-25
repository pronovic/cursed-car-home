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
package com.cedarsolutions.cursed.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Configuration for CursedCarHome.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class CursedCarHomeConfig {

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

    /** Create configuration based on an application context. */
    public CursedCarHomeConfig(Context context) {
        // Remember that the real defaults are configured in preferences.xml.
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.workaroundEnabled = preferences.getBoolean("workaroundEnabled", false);
        this.monitoringEnabled = preferences.getBoolean("monitoringEnabled", false);
        this.maxLifetimeMs = Integer.parseInt(preferences.getString("maxLifetimeMs", "0"));
        this.initialDelayMs = Integer.parseInt(preferences.getString("initialDelayMs", "0"));
        this.maxDelayMs = Integer.parseInt(preferences.getString("maxDelayMs", "0"));
        this.dailyReportEnabled = preferences.getBoolean("dailyReportEnabled", false);
        this.dailyReportTime = preferences.getString("dailyReportTime", "0");
    }

    /** String representation of this object. */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("CursedCarHome Configuration\n");

        buffer.append("workaroundEnabled = ");
        buffer.append(this.workaroundEnabled);
        buffer.append("\n");

        buffer.append("monitoringEnabled = ");
        buffer.append(this.monitoringEnabled);
        buffer.append("\n");

        buffer.append("maxLifetimeMs = ");
        buffer.append(this.maxLifetimeMs);
        buffer.append("\n");

        buffer.append("initialDelayMs = ");
        buffer.append(this.initialDelayMs);
        buffer.append("\n");

        buffer.append("maxDelayMs = ");
        buffer.append(this.maxDelayMs);
        buffer.append("\n");

        buffer.append("dailyReportEnabled = ");
        buffer.append(this.dailyReportEnabled);
        buffer.append("\n");

        buffer.append("dailyReportTime = ");
        buffer.append(this.dailyReportTime);
        buffer.append("\n");

        return buffer.toString();
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
