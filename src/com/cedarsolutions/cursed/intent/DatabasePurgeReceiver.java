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

import com.cedarsolutions.cursed.service.DatabasePurgeService;
import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * Starts the database purge process.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DatabasePurgeReceiver extends BroadcastReceiver {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(DatabasePurgeReceiver.class);

    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.debug("Database purge alarm was triggered");
        Intent service = new Intent(context, DatabasePurgeService.class);
        context.startService(service);
    }

}
