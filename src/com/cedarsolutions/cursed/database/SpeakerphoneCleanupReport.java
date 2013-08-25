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
package com.cedarsolutions.cursed.database;

import java.util.ArrayList;
import java.util.List;

/**
 * Report about speakerphone cleanup events.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class SpeakerphoneCleanupReport {

    /** Start of the range the report covers, as an ISO 8601 date string. */
    private String reportStart;

    /** Endi of the range the report covers, as an ISO 8601 date string. */
    private String reportEnd;

    /** Number of speakerphone events handled during the range. */
    private int eventsHandled;

    /** Timestamps for each event that was handled. */
    private List<String> timestamps;

    /** Default constructor. */
    public SpeakerphoneCleanupReport() {
        this.timestamps = new ArrayList<String>();
    }

    public String getReportStart() {
        return reportStart;
    }

    public void setReportStart(String reportStart) {
        this.reportStart = reportStart;
    }

    public String getReportEnd() {
        return reportEnd;
    }

    public void setReportEnd(String reportEnd) {
        this.reportEnd = reportEnd;
    }

    public int getEventsHandled() {
        return eventsHandled;
    }

    public void setEventsHandled(int eventsHandled) {
        this.eventsHandled = eventsHandled;
    }

    public List<String> getTimestamps() {
        return this.timestamps;
    }

    public void setTimestamps(List<String> timestamps) {
        this.timestamps = timestamps;
    }

}
