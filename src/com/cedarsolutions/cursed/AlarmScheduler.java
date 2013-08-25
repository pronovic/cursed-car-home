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

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Schedules alarms for this application.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class AlarmScheduler {

    /** Configure all alarms for this application.  */
    public static void configureAllAlarms(Context context) {
        configureDailyCleanupAlarm(context);
        configureDailyReportAlarm(context);
    }

    /** Configure the alarm for the daily cleanup job. */
    private static void configureDailyCleanupAlarm(Context context) {
        cancelAlarm(context, DailyCleanupAlarmReceiver.class, "Daily cleanup alarm");
        scheduleDailyAlarmUtc(context, "0005", DailyCleanupAlarmReceiver.class, "Daily cleanup alarm");
    }

    /** Configure the alarm for the daily report job. */
    private static void configureDailyReportAlarm(Context context) {
        CursedCarHomeConfig config = new CursedCarHomeConfig(context);
        cancelAlarm(context, DailyReportAlarmReceiver.class, "Daily report alarm");
        if (config.getDailyReportEnabled()) {
            scheduleDailyAlarmLocal(context, config.getDailyReportTime(), DailyReportAlarmReceiver.class, "Daily report alarm");
        } else {
            Log.d("CursedCarHome", "AlarmScheduler: daily report alarm not enabled");
        }
    }

    /** Cancel an alarm for a particular class. */
    private static void cancelAlarm(Context context, Class<?> alarmClass, String alarmName) {
        Intent intent = new Intent(context, alarmClass);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        Log.d("CursedCarHome", "AlarmScheduler: canceled alarm [" + alarmName + "]");
    }

    /** Schedule a daily alarm for a particular class, in UTC. */
    private static void scheduleDailyAlarmUtc(Context context, String timeOfday, Class<?> alarmClass, String alarmName) {
        Date scheduledTime = DateUtils.getNextUtcOccurrence(timeOfday);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, alarmClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, scheduledTime.getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("CursedCarHome", "AlarmScheduler: scheduled alarm [" + alarmName + "] starting " + DateUtils.formatIso8601Utc(scheduledTime));
    }

    /** Schedule a daily alarm for a particular class, in local. */
    private static void scheduleDailyAlarmLocal(Context context, String timeOfday, Class<?> alarmClass, String alarmName) {
        Date scheduledTime = DateUtils.getNextOccurrence(timeOfday);
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, alarmClass);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, scheduledTime.getTime(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("CursedCarHome", "AlarmScheduler: scheduled alarm [" + alarmName + "] starting " + DateUtils.formatIso8601(scheduledTime));
    }

}
