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
import android.view.MenuItem;
import android.widget.ScrollView;
import android.widget.TextView;

import com.cedarsolutions.cursed.database.DockCleanupDatabase;
import com.cedarsolutions.cursed.database.DockCleanupReport;
import com.cedarsolutions.cursed.database.SpeakerphoneCleanupDatabase;
import com.cedarsolutions.cursed.database.SpeakerphoneCleanupReport;

/**
 * Activity that displays the daily report.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class DailyReportActivity extends Activity {

    /** Menu item identifiers. */
    private static final int REFRESH_MENU = 0;

    /** Called when the activity is starting. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.refreshReportDisplay();
    }

    /** Initialize the contents of the Activity's standard options menu. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, REFRESH_MENU, 0, "Refresh");
        return super.onCreateOptionsMenu(menu);
    }

    /** Called whenever an item in your options menu is selected. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case REFRESH_MENU:
            this.refreshReportDisplay();
            return true;
        default:
            return false;
        }
    }

    /** Refresh the report display. */
    private void refreshReportDisplay() {
        DockCleanupDatabase dockCleanupDatabase = new DockCleanupDatabase(this);
        DockCleanupReport dockCleanupReport = dockCleanupDatabase.createDailyDockCleanupReport();

        SpeakerphoneCleanupDatabase speakerphoneCleanupDatabase = new SpeakerphoneCleanupDatabase(this);
        SpeakerphoneCleanupReport speakerphoneCleanupReport = speakerphoneCleanupDatabase.createDailySpeakerphoneCleanupReport();

        String reportHtml = generateReportHtml(dockCleanupReport, speakerphoneCleanupReport);

        // See: http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android
        ScrollView scroller = new ScrollView(this);
        TextView textView = new TextView(this);
        textView.setText(Html.fromHtml(reportHtml));
        scroller.addView(textView);
        setContentView(scroller);
    }

    /** Generate HTML text based on a DockCleanupReport. */
    private static String generateReportHtml(DockCleanupReport dockCleanupReport, SpeakerphoneCleanupReport speakerphoneCleanupReport) {
        StringBuffer html = new StringBuffer();

        html.append("<html>\n");

        html.append("<h2>Cursed Car Home Daily Report</h2>\n");

        html.append("<p>\n");
        html.append("This report provides a summary of the docking events\n");
        html.append("that CursedCarHome has handled over the past 24 hours.\n");
        html.append("</p>\n");

        html.append("<h3>Summary</h3>\n");

        html.append("<b>Report start: </b>");
        html.append(dockCleanupReport.getReportStart());
        html.append("<br/>\n");

        html.append("<b>Report end: </b>");
        html.append(dockCleanupReport.getReportEnd());
        html.append("<br/>\n");

        html.append("<b>Spurious dock events: </b>");
        html.append(dockCleanupReport.getEventsHandled());
        if (dockCleanupReport.getEventsHandled() == 1) {
            html.append(" event<br/>\n");
        } else {
            html.append(" events<br/>\n");
        }

        html.append("<b>Disabled dock mode: </b>");
        html.append(dockCleanupReport.getDisableAttempts());
        if (dockCleanupReport.getDisableAttempts() == 1) {
            html.append(" time<br/>\n");
        } else {
            html.append(" times<br/>\n");
        }

        if (!dockCleanupReport.getStartTimes().isEmpty()) {
            html.append("<h3>Dock Cleanup Events</h3>\n");

            int index = 1;
            for (String startTime : dockCleanupReport.getStartTimes()) {
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
