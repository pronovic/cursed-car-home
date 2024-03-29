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
package com.cedarsolutions.cursed.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cedarsolutions.cursed.service.DockEventService;
import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * Receives the standard Android dock event and starts the cleanup service.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockEventReceiver extends BroadcastReceiver {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(DockEventReceiver.class);

    // To test this, use ADB once the emulator is running:
    //    C:\Ken\Program Files\Android\android-sdk\platform-tools>adb shell
    //    # am broadcast -a android.intent.action.DOCK_EVENT --ei android.intent.extra.DOCK_STATE 2

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.debug("Got DOCK_EVENT event");
        Intent service = new Intent(context, DockEventService.class);
        context.startService(service);
    }

}
