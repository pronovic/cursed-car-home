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

    /** Format a date as an ISO 8601 date. */
    public static String formatIso8601(Date date) {
        return formatIso8601(date.getTime());
    }

    /** Format a millisecond timestamp as an ISO 8601 date. */
    public static String formatIso8601(long timestamp) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS XXX", Locale.US);
        return df.format(new Date(timestamp));
    }

    /** Format a UTC date as an ISO 8601 date. */
    public static String formatIso8601Utc(Date date) {
        return formatIso8601Utc(date.getTime());
    }

    /** Format a UTC millisecond timestamp as an ISO 8601 date. */
    public static String formatIso8601Utc(long timestamp) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss,SSS XXX", Locale.US);
        df.setTimeZone(tz);
        return df.format(new Date(timestamp));
    }

    /** Create a date. */
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

    /** Get the time from a passed-in date. */
    public static String getTime(Date date) {
        return getTime(date.getTime());
    }

    /** Get the time from a passed-in timestamp. */
    public static String getTime(long timestamp) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.US);
        calendar.setTimeInMillis(timestamp);
        int time = (calendar.get(Calendar.HOUR_OF_DAY) * 100) + calendar.get(Calendar.MINUTE);
        return String.valueOf(time);
    }

    /** Get the current time. */
    public static String getCurrentTime() {
        return getTime(new Date());
    }

    /** Get the UTC time from a passed-in date. */
    public static String getUtcTime(Date date) {
        return getUtcTime(date.getTime());
    }

    /** Get the UTC time from a passed-in timestamp. */
    public static String getUtcTime(long timestamp) {
        Calendar calendar = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
        calendar.setTimeInMillis(timestamp);
        int time = (calendar.get(Calendar.HOUR_OF_DAY) * 100) + calendar.get(Calendar.MINUTE);
        return String.valueOf(time);
    }

    /** Get the current time in UTC. */
    public static String getCurrentUtcTime() {
        return getUtcTime(new Date());
    }

    /**
     * Return a date for the next occurrence of a time.
     *
     * <p>
     * So, if it's 10:00am and the passed-in time is "1005", the result will be
     * the same date at 10:05am.  If it's 11:00am instead, the result will be
     * the next day at 10:05am.  This is useful when generating Android alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @return Java date for the next occurrence
     */
    public static Date getNextOccurrence(String time) {
        return getNextUtcOccurrence(time, new Date());
    }

    /**
     * Return a date for the next occurrence of a time.
     *
     * <p>
     * So, if it's 10:00am and the passed-in time is "1005", the result will be
     * the same date at 10:05am.  If it's 11:00am instead, the result will be
     * the next day at 10:05am.  This is useful when generating Android alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @param now    Date to consider as "now" for calculations
     *
     * @return Java date for the next occurrence
     */
    public static Date getNextOccurrence(String time, Date now) {
        Calendar calendar = GregorianCalendar.getInstance(Locale.US);
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

    /**
     * Return a date for the next occurrence of a UTC time.
     *
     * <p>
     * So, if it's 10:00am and the passed-in time is "1005", the result will be
     * the same date at 10:05am.  If it's 11:00am instead, the result will be
     * the next day at 10:05am.  This is useful when generating Android alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @return Java date for the next occurrence
     */
    public static Date getNextUtcOccurrence(String time) {
        return getNextUtcOccurrence(time, new Date());
    }

    /**
     * Return a date for the next occurrence of a UTC time.
     *
     * <p>
     * So, if it's 10:00am and the passed-in time is "1005", the result will be
     * the same date at 10:05am.  If it's 11:00am instead, the result will be
     * the next day at 10:05am.  This is useful when generating Android alarms.
     * </p>
     *
     * @param time   Time as a string, like "1100" for 11:00am or "1425" for 2:25pm.
     * @param now    Date to consider as "now" for calculations
     *
     * @return Java date for the next occurrence
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
