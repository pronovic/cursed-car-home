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

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.cedarsolutions.cursed.R;
import com.cedarsolutions.cursed.config.CursedCarHomeConfig;

/**
 * Activity to edit application preferences.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class EditPreferencesActivity extends Activity {

    /** Called when the activity is starting. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CursedCarHomeConfig config = new CursedCarHomeConfig(this);
        setContentView(R.layout.settings);
        TextView settingsTextView = (TextView) findViewById(R.id.settings);
        settingsTextView.setText(config.toString());
    }

}
