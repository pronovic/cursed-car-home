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

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.cedarsolutions.cursed.config.CursedCarHomeConfig;
import com.cedarsolutions.cursed.util.AndroidLogger;
import com.cedarsolutions.cursed.util.DateUtils;

/**
 * Schedules alarms for CursedCarHome.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AlarmScheduler {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(AlarmScheduler.class);

    /** Configure all alarms for CursedCarHome.  */
    public static void configureAllAlarms(Context context) {
        configureDatabasePurgeAlarm(context);
        configureDailyReportAlarm(context);
    }

    /** Configure the alarm for the database purge job. */
    private static void configureDatabasePurgeAlarm(Context context) {
        // The database purge job runs at a few minutes after midnight UTC, every day
        cancelAlarm(context, DatabasePurgeReceiver.class, "Database purge alarm");
        scheduleDailyAlarmUtc(context, "0005", DatabasePurgeReceiver.class, "Database purge alarm");
    }

    /** Configure the alarm for the daily report job. */
    private static void configureDailyReportAlarm(Context context) {
        // If enabled, the daily report runs at a local time configured in preferences
        CursedCarHomeConfig config = new CursedCarHomeConfig(context);
        cancelAlarm(context, DailyReportReceiver.class, "Daily report alarm");
        if (config.getDailyReportEnabled() && config.getDailyReportTime() != null) {
            LOGGER.debug("Daily report time is [" + config.getDailyReportTime() + "]");
            //scheduleDailyAlarmLocal(context, config.getDailyReportTime(), DailyReportReceiver.class, "Daily report alarm");
            scheduleDailyAlarmLocal(context, "0041", DailyReportReceiver.class, "Daily report alarm");
        } else {
            LOGGER.debug("Daily report alarm not enabled");
        }
    }

    /** Cancel an alarm for a particular class. */
    private static void cancelAlarm(Context context, Class<?> alarmClass, String alarmName) {
        Intent intent = new Intent(context, alarmClass);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        LOGGER.debug("Canceled alarm: " + alarmName);
    }

    /** Schedule a daily alarm for a particular class, in UTC. */
    private static void scheduleDailyAlarmUtc(Context context, String timeOfday, Class<?> alarmClass, String alarmName) {
        Date scheduledTime = DateUtils.getNextUtcOccurrence(timeOfday);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, alarmClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, scheduledTime.getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
        LOGGER.debug("Scheduled alarm " + alarmName + ", starting " + DateUtils.formatIso8601Utc(scheduledTime));
    }

    /** Schedule a daily alarm for a particular class, in local. */
    private static void scheduleDailyAlarmLocal(Context context, String timeOfday, Class<?> alarmClass, String alarmName) {
        Date scheduledTime = DateUtils.getNextOccurrence(timeOfday);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, alarmClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, scheduledTime.getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
        LOGGER.debug("Scheduled alarm " + alarmName + ", starting " + DateUtils.formatIso8601(scheduledTime));
    }

}
