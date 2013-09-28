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
package com.cedarsolutions.cursed.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

import com.cedarsolutions.cursed.R;
import com.cedarsolutions.cursed.intent.AlarmScheduler;
import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * The main activity for CursedCarHome (manages preferences).
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class CursedCarHomeMain extends PreferenceActivity implements OnSharedPreferenceChangeListener {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(CursedCarHomeMain.class);

    /** Settings menu identifier. */
    private static final int SETTINGS_MENU = 0;

    /** Report menu identifier. */
    private static final int REPORT_MENU = 1;

    /** Help menu identifier. */
    private static final int HELP_MENU = 2;

    /** Called when the activity is starting. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferences);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    /** Initialize the contents of the Activity's standard options menu. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, SETTINGS_MENU, 0, "Show Current Settings");
        menu.add(Menu.NONE, REPORT_MENU, 1, "Run Daily Report");
        menu.add(Menu.NONE, HELP_MENU, 2, "Help");
        return super.onCreateOptionsMenu(menu);
    }

    /** Called whenever an item in your options menu is selected. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case SETTINGS_MENU:
            this.startActivity(new Intent(this, EditPreferencesActivity.class));
            return true;
        case REPORT_MENU:
            this.startActivity(new Intent(this, DailyReportActivity.class));
            return true;
        case HELP_MENU:
            this.startActivity(new Intent(this, HelpActivity.class));
            return true;
        default:
            return false;
        }
    }

    /** Called when a shared preference is changed, added, or removed. */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        LOGGER.debug("Re-configuring all alarms because configuration changed");
        AlarmScheduler.configureAllAlarms(this);
    }
}
