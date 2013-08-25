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
package com.cedarsolutions.cursed.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Date-related utilities.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DateUtils {

    /** Number of nanoseconds per millisecond. */
    public static final long NANOSECONDS_PER_MILLISECOND = 1000000;

    /** Convert milliseconds to nanoseconds. */
    public static long convertMillisecondsToNanoseconds(long milliseconds) {
        return milliseconds * NANOSECONDS_PER_MILLISECOND;
    }

    /** Format a date as an ISO 8601 string in local time. */
    public static String formatIso8601(Date date) {
        return formatIso8601(date.getTime());
    }

    /** Format a millisecond timestamp as an ISO 8601 string in local time. */
    public static String formatIso8601(long timestamp) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS ZZ", Locale.US);
        return format.format(new Date(timestamp));
    }

    /** Format as an ISO 8601 string in UTC. */
    public static String formatIso8601Utc(Date date) {
        return formatIso8601Utc(date.getTime());
    }

    /** Format a millisecond timestamp as an ISO 8601 string in UTC. */
    public static String formatIso8601Utc(long timestamp) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS ZZ", Locale.US);
        format.setTimeZone(tz);
        return format.format(new Date(timestamp));
    }

    /** Create a date in the local time zone. */
    public static Date createDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.US);
        calendar.clear();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);

        return calendar.getTime();
    }

    /** Create a date in UTC. */
    public static Date createUtcDate(int year, int month, int day, int hour, int minute, int second, int millisecond) {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.clear();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DATE, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);

        return calendar.getTime();
    }

    /** Get a 4-digit string time from a passed-in date, like "1145". */
    public static String getTime(Date date) {
        return getTime(date.getTime());
    }

    /** Get a 4-digit string time from a passed-in timestamp, like "1145". */
    public static String getTime(long timestamp) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.US);
        calendar.setTimeInMillis(timestamp);
        int time = (calendar.get(Calendar.HOUR_OF_DAY) * 100) + calendar.get(Calendar.MINUTE);
        return time < 1000 ? "0" + String.valueOf(time) : String.valueOf(time);
    }

    /** Get a 4-digit string time in UTC from a passed-in date, like "1145". */
    public static String getUtcTime(Date date) {
        return getUtcTime(date.getTime());
    }

    /** Get a 4-digit string time in UTC from a passed-in timestamp, like "1145". */
    public static String getUtcTime(long timestamp) {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.setTimeInMillis(timestamp);
        int time = (calendar.get(Calendar.HOUR_OF_DAY) * 100) + calendar.get(Calendar.MINUTE);
        return time < 1000 ? "0" + String.valueOf(time) : String.valueOf(time);
    }

    /**
     * Return a date for the next occurrence of a time in the local timezone.
     *
     * <p>
     * So, if it's 10:00am local and the passed-in time is "1005", the result
     * will be the same date at 10:05am.  If it's 11:00am instead, the result
     * will be the next day at 10:05am.  This is useful when generating Android
     * alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @return Java date for the next occurrence of the time in the local time zone
     */
    public static Date getNextOccurrence(String time) {
        return getNextOccurrence(time, new Date());
    }

    /**
     * Return a date for the next occurrence of a time.
     *
     * <p>
     * So, if it's 10:00am local and the passed-in time is "1005", the result
     * will be the same date at 10:05am.  If it's 11:00am instead, the result
     * will be the next day at 10:05am.  This is useful when generating Android
     * alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @param now    Date to consider as "now" for calculations
     *
     * @return Java date for the next occurrence of the time in the local time zone
     */
    public static Date getNextOccurrence(String time, Date now) {
        if (time.length() == 3) {
            // so "900" turns to "0900"
            time = "0" + time;
        }

        Calendar calendar = GregorianCalendar.getInstance(Locale.US);
        calendar.setTime(now);

        int timeDesired = Integer.parseInt(time);
        int timeNow = Integer.parseInt(getTime(calendar.getTime().getTime()));

        int hour = Integer.parseInt(time.substring(0, 2).replaceFirst("^0", ""));
        int minute = Integer.parseInt(time.substring(2, 4).replaceFirst("^0", ""));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (timeDesired <= timeNow) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        }

        return calendar.getTime();
    }

    /**
     * Return a date for the next occurrence of a UTC time.
     *
     * <p>
     * So, if it's 10:00am UTC and the passed-in time is "1005", the result
     * will be the same date at 10:05am.  If it's 11:00am instead, the result
     * will be the next day at 10:05am.  This is useful when generating Android
     * alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @return Java date for the next occurrence of the time in UTC
     */
    public static Date getNextUtcOccurrence(String time) {
        return getNextUtcOccurrence(time, new Date());
    }

    /**
     * Return a date for the next occurrence of a UTC time.
     *
     * <p>
     * So, if it's 10:00am UTC and the passed-in time is "1005", the result
     * will be the same date at 10:05am.  If it's 11:00am instead, the result
     * will be the next day at 10:05am.  This is useful when generating Android
     * alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @param now    Date to consider as "now" for calculations
     *
     * @return Java date for the next occurrence of the time in UTC
     */
    public static Date getNextUtcOccurrence(String time, Date now) {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.setTime(now);

        int timeDesired = Integer.parseInt(time);
        int timeNow = Integer.parseInt(getUtcTime(calendar.getTime().getTime()));

        int hour = Integer.parseInt(time.substring(0, 2).replaceFirst("^0", ""));
        int minute = Integer.parseInt(time.substring(2, 4).replaceFirst("^0", ""));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        if (timeDesired <= timeNow) {
            calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        }

        return calendar.getTime();
    }

}
