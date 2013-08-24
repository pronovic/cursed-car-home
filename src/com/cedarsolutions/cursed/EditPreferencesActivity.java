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

import static com.cedarsolutions.cursed.CursedCarHomeConfig.DEFAULT_DAILY_REPORT_ENABLED;
import static com.cedarsolutions.cursed.CursedCarHomeConfig.DEFAULT_DAILY_REPORT_TIME;
import static com.cedarsolutions.cursed.CursedCarHomeConfig.DEFAULT_INITIAL_DELAY_MS;
import static com.cedarsolutions.cursed.CursedCarHomeConfig.DEFAULT_MAX_DELAY_MS;
import static com.cedarsolutions.cursed.CursedCarHomeConfig.DEFAULT_MAX_LIFETIME_MS;
import static com.cedarsolutions.cursed.CursedCarHomeConfig.DEFAULT_MONITORING_ENABLED;
import static com.cedarsolutions.cursed.CursedCarHomeConfig.DEFAULT_SPEAKER_ENABLED;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

/**
 * Activity to edit application preferences.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EditPreferencesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        StringBuilder builder = new StringBuilder();
        builder.append("\n" + sharedPrefs.getBoolean("speakerEnabled", DEFAULT_SPEAKER_ENABLED));
        builder.append("\n" + sharedPrefs.getBoolean("monitoringEnabled", DEFAULT_MONITORING_ENABLED));
        builder.append("\n" + sharedPrefs.getString("maxLifetimeMs", DEFAULT_MAX_LIFETIME_MS));
        builder.append("\n" + sharedPrefs.getString("initialDelayMs", DEFAULT_INITIAL_DELAY_MS));
        builder.append("\n" + sharedPrefs.getString("maxDelayMs", DEFAULT_MAX_DELAY_MS));
        builder.append("\n" + sharedPrefs.getBoolean("dailyReportEnabled", DEFAULT_DAILY_REPORT_ENABLED));
        builder.append("\n" + sharedPrefs.getString("dailyReportTime", DEFAULT_DAILY_REPORT_TIME));

        TextView settingsTextView = (TextView) findViewById(R.id.settings);
        settingsTextView.setText(builder.toString());
    }

}
