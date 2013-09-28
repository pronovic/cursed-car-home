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
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Activity that displays the daily report.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class HelpActivity extends Activity {

    /** Called when the activity is starting. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.refreshReportDisplay();
    }

    /** Refresh the report display. */
    private void refreshReportDisplay() {
        // See: http://stackoverflow.com/questions/1748977/making-textview-scrollable-in-android
        String helpHtml = generateHelpHtml();
        ScrollView scroller = new ScrollView(this);
        TextView textView = new TextView(this);
        textView.setText(Html.fromHtml(helpHtml));
        scroller.addView(textView);
        setContentView(scroller);
    }

    /** Generate HTML text for the help screen. */
    private static String generateHelpHtml() {
        StringBuffer html = new StringBuffer();

        html.append("<html>\n");

        html.append("<h2>The Problem</h2>\n");

        html.append("<p>\n");
        html.append("My Android phone is \"cursed\" periodically by a phantom that repeatedly turns on \n");
        html.append("the Car Home application as if the phone were being docked. (It's probably a \n");
        html.append("hardware problem.) Besides the annoyance caused by the Car Home homepage \n");
        html.append("itself, a bigger problem is that Car Home enables the speakerphone and never \n");
        html.append("turns it back off.\n");
        html.append("</p>\n");

        html.append("<h2>The Solution</h2>\n");

        html.append("<p>\n");
        html.append("I've tried several of the existing applications in the Android marketplace, but \n");
        html.append("nothing has quite worked for me. This application is my attempt to solve the \n");
        html.append("problem. It's a \"big hammer\" approach &mdash; once a dock event is handled, a \n");
        html.append("background process starts.  This process watches for Car Home and kills it if it's\n");
        html.append("ever found running.  Separately, I also hook into the phone call process to disable the \n");
        html.append("speakerphone every time an inbound or outbound call is initiated.\n");
        html.append("</p>\n");

        html.append("<h2>Using the Application</h2>\n");

        html.append("<p>\n");
        html.append("The default configuration should work fine for most people.  If you tend to see \n");
        html.append("clusters of events over a period of time, you may want to change some values.\n");
        html.append("</p>\n");

        html.append("<p>\n");
        html.append("For instance, on my phone, I often get several to a dozen dock events over a 30 \n");
        html.append("minute window (or sometimes even longer).  When first started, the background \n");
        html.append("process is very aggressive about how often it looks for problems.  As the time \n");
        html.append("between discovered problems grows, the background process gradually slows down \n");
        html.append("and looks for problems less often.\n");
        html.append("</p>\n");

        html.append("<p>\n");
        html.append("If you are seeing clusters of events, you may find that it takes too long for \n");
        html.append("the background process to notice problems and clean them up.  You can adjust \n");
        html.append("this behavior by decreasing the maximum delay.  You may also want to increase \n");
        html.append("the maximum lifetime of the background process.\n");
        html.append("</p>\n");

        html.append("<p>\n");
        html.append("I find the daily report useful, because it alerts me to problems that might \n");
        html.append("have occurred when I was not looking at the phone (such as overnight when \n");
        html.append("I am sleeping).  However, you can disable the report if you want to.  Even if\n");
        html.append("the daily report is disabled, you can always run the report manually. Open \n");
        html.append("the application and choosing <b>Menu &gt; Run Daily Report</b>.\n");
        html.append("</p>\n");

        html.append("</html>");

        return html.toString();
    }
}
