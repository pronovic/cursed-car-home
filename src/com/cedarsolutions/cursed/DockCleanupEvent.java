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

import java.util.UUID;

/**
 * Event tied to execution of the DockEventCleanupThread.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockCleanupEvent {

    /** Event identifier. */
    private String id;

    /** Start time, as from System.currentTimeMs(). */
    private long startTime;

    /** Stop time, as from System.currentTimeMs(). */
    private long stopTime;

    /** Number of attempts made to disable dock mode. */
    private int disableAttempts;

    /** Number of attempts made to kill the CarHome background process. */
    private int killAttempts;

    /** Default constructor. */
    public DockCleanupEvent() {
        this.id = UUID.randomUUID().toString();
    }

    /** Mark start. */
    public void markStart() {
        this.startTime = System.currentTimeMillis();
    }

    /** Mark stop. */
    public void markStop() {
        this.stopTime = System.currentTimeMillis();
    }

    /** Increment the disable attempts. */
    public void markDisableAttempt() {
        this.disableAttempts += 1;
    }

    /** Increment the kill attempts. */
    public void markKillAttempt() {
        this.killAttempts += 1;
    }

    /** String representation of object. */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("event(");

        buffer.append("id=");
        buffer.append(this.getId());
        buffer.append(", ");

        buffer.append("startTime=");
        buffer.append(DateUtils.formatIso8601Utc(this.getStartTime()));
        buffer.append(", ");

        buffer.append("stopTime=");
        buffer.append(DateUtils.formatIso8601Utc(this.getStopTime()));
        buffer.append(", ");

        buffer.append("disableAttempts=");
        buffer.append(this.getDisableAttempts());
        buffer.append(", ");

        buffer.append("killAttempts=");
        buffer.append(this.getKillAttempts());
        buffer.append(")");

        return buffer.toString();
    }

    /** Event identifier. */
    public String getId() {
        return this.id;
    }

    /** Start time, as from System.currentTimeMs(). */
    public long getStartTime() {
        return startTime;
    }

    /** Stop time, as from System.currentTimeMs(). */
    public long getStopTime() {
        return stopTime;
    }

    /** Number of attempts made to disable dock mode. */
    public int getDisableAttempts() {
        return disableAttempts;
    }

    /** Number of attempts made to kill the CarHome background process. */
    public int getKillAttempts() {
        return killAttempts;
    }

}
