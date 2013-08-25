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

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cedarsolutions.cursed.database.DockCleanupDatabase;
import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * Service that purges old data from the database.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DatabasePurgeService extends Service {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(DatabasePurgeService.class);

    /** Called by the system every time a client explicitly starts the service. */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LOGGER.debug("DatabasePurgeService started");
        DockCleanupDatabase database = new DockCleanupDatabase(this);
        database.purgeOldData();
        return Service.START_NOT_STICKY;  // it's ok for the system to kill it
    }

    /** Called by the system to notify a Service that it is no longer used and is being removed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LOGGER.debug("DatabasePurgeService destroyed");
    }

    /** Return the communication channel to the service. */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
