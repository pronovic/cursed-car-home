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

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.cedarsolutions.cursed.R;
import com.cedarsolutions.cursed.activity.DailyReportActivity;
import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * Service that runs the daily report.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DailyReportService  extends Service {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(DailyReportService.class);

    /** Called by the system every time a client explicitly starts the service. */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LOGGER.debug("DailyReportService started");
        this.triggerDailyReportActivity();
        return Service.START_NOT_STICKY;  // it's ok for the system to kill it
    }

    /** Called by the system to notify a Service that it is no longer used and is being removed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        LOGGER.debug("DailyReportService destroyed");
    }

    /** Return the communication channel to the service. */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** Trigger the daily report activity UI component. */
    private void triggerDailyReportActivity() {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.cch, "Cursed Car Home Daily Report", System.currentTimeMillis());
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // so it goes away after being selected
        CharSequence contentTitle = "Cursed Car Home Daily Report";
        CharSequence contentText = "Daily report is ready";
        Intent notificationIntent = new Intent(this, DailyReportActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(this, contentTitle, contentText, contentIntent);
        notification.setLatestEventInfo(this.getApplicationContext(), contentTitle, contentText, contentIntent);
        notificationManager.notify(0, notification);
    }

}
