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
 * Language : Java 6
 * Project  : Cursed Car Home
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
package com.cedarsolutions.cursed.database;

import java.util.UUID;

import com.cedarsolutions.cursed.util.DateUtils;

/**
 * Event tied to cleanup of the speakerphone.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SpeakerphoneCleanupEvent {

    /** Event identifier. */
    private String id;

    /** Time at which event occurred. */
    private long timestamp;

    /** Default constructor. */
    public SpeakerphoneCleanupEvent() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    /** String representation of object. */
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("SpeakerphoneCleanupEvent(");

        buffer.append("id=");
        buffer.append(this.getId());
        buffer.append(", ");

        buffer.append("timestamp=");
        buffer.append(DateUtils.formatIso8601Utc(this.getTimestamp()));
        buffer.append(", ");

        return buffer.toString();
    }

    /** Event identifier. */
    public String getId() {
        return this.id;
    }

    /** Time at which event occurred. */
    public long getTimestamp() {
        return timestamp;
    }
}
