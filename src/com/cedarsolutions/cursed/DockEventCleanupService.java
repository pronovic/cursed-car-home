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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * Service that cleans up after CarHome via CarHomeCleanupThread.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockEventCleanupService extends Service {

    private boolean started = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!started) {
            Log.d("CursedCarHome", "DockEventCleanupService started");
            DockEventCleanupThread.startThread(this, this.getApplicationContext());
            started = true;
        } else {
            Log.d("CursedCarHome", "DockEventCleanupService was already started, no-op");
        }
        return Service.START_NOT_STICKY;  // it's ok for the system to kill it
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
