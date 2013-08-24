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
 * Receives the standard BOOT_COMPLETED event and schedules jobs.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class BootCompletedReceiver extends BroadcastReceiver {

    /** Handle the BOOT_COMPLETED intent. */
    @Override
    public void onReceive(Context context, Intent intent) {

        // Note that in the emulator, the application which receives this event
        // is the old application (the one that was previously installed), not
        // the new one that you're trying to test.  You need to manually go in
        // and change configuration to trigger the alarms in the emulator.
        // (Incidentally, the same is true when installing the app for the first
        // time... none of the alarms will run until the app has been configured.)

        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Log.d("CursedCarHome", "BootCompletedReceiver got BOOT_COMPLETED intent");
            AlarmScheduler.configureAllAlarms(context);
        }
    }

}
