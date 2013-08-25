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
package com.cedarsolutions.cursed.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

import com.cedarsolutions.cursed.R;
import com.cedarsolutions.cursed.database.DockCleanupDatabase;
import com.cedarsolutions.cursed.database.DockCleanupReport;

/**
 * Activity that displays the daily report.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DailyReportActivity extends Activity {

    /** Called when the activity is starting. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DockCleanupDatabase database = new DockCleanupDatabase(this);
        DockCleanupReport report = database.createDailyDockCleanupReport();
        String reportHtml = generateReportHtml(report);

        TextView textView = new TextView(this);
        textView.setText(Html.fromHtml(reportHtml));
        setContentView(textView);
    }

    /** Initialize the contents of the Activity's standard options menu. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daily_report, menu);
        return true;
    }

    /** Generate HTML text based on a DockCleanupReport. */
    private static String generateReportHtml(DockCleanupReport report) {
        StringBuffer html = new StringBuffer();

        html.append("<html>\n");

        html.append("<h2>Cursed Car Home Daily Report</h2>\n");

        html.append("<p>\n");
        html.append("This report provides a summary of the docking events\n");
        html.append("that CursedCarHome has handled over the past 24 hours.\n");
        html.append("</p>\n");

        html.append("<h3>Summary</h3>\n");

        html.append("<b>Report start: </b>");
        html.append(report.getReportStart());
        html.append("<br/>\n");

        html.append("<b>Report end: </b>");
        html.append(report.getReportEnd());
        html.append("<br/>\n");

        html.append("<b>Spurious dock events: </b>");
        html.append(report.getEventsHandled());
        if (report.getEventsHandled() == 1) {
            html.append(" event<br/>\n");
        } else {
            html.append(" events<br/>\n");
        }

        html.append("<b>Disabled dock mode: </b>");
        html.append(report.getDisableAttempts());
        if (report.getDisableAttempts() == 1) {
            html.append(" time<br/>\n");
        } else {
            html.append(" times<br/>\n");
        }

        if (!report.getStartTimes().isEmpty()) {
            html.append("<h3>Events</h3>\n");

            int index = 1;
            for (String startTime : report.getStartTimes()) {
                html.append("<b>Event ");
                html.append(index);
                html.append(": </b> ");
                html.append(startTime);
                html.append("<br/>\n");
                index += 1;
            }
        }

        html.append("</html>");

        return html.toString();
    }
}
