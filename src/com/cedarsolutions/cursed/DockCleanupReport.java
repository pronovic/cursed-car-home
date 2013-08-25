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

import java.util.ArrayList;
import java.util.List;

/**
 * Report about dock cleanup events.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DockCleanupReport {

    private String reportStart;
    private String reportEnd;
    private int eventsHandled;
    private int disableAttempts;
    private List<String> startTimes;

    public DockCleanupReport() {
        this.startTimes = new ArrayList<String>();
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

    public int getDisableAttempts() {
        return disableAttempts;
    }

    public void setDisableAttempts(int disableAttempts) {
        this.disableAttempts = disableAttempts;
    }

    public List<String> getStartTimes() {
        return startTimes;
    }

    public void setStartTimes(List<String> startTimes) {
        this.startTimes = startTimes;
    }

}
