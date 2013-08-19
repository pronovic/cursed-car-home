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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Receives the standard Android dock event and kicks off the CarHomeCleanupService.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockEventReceiver extends BroadcastReceiver {

    // To test this, use ADB once the emulator is running:
    //    C:\Ken\Program Files\Android\android-sdk\platform-tools>adb shell
    //    # am broadcast -a android.intent.action.DOCK_EVENT --ei android.intent.extra.DOCK_STATE 2

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("CursedCarHome", "DockEventReceiver.onReceive() got DOCK_EVENT intent");
        Intent service = new Intent(context, DockEventCleanupService.class);
        context.startService(service);
    }

}
