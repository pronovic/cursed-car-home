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
package com.cedarsolutions.cursed.intent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.cedarsolutions.cursed.config.CursedCarHomeConfig;
import com.cedarsolutions.cursed.util.AndroidLogger;

/**
 * Receives phone call events and manges the speakerphone workaround.
 * @author Kenneth J. Pronovici <pronovic@ieee.org>
 */
public class PhoneCallEventReceiver extends BroadcastReceiver {

    /** Logger instance. */
    private static final AndroidLogger LOGGER = AndroidLogger.getLogger(PhoneCallEventReceiver.class);

    /** Receive events. */
    @Override
    public void onReceive(Context context, Intent intent) {
        LOGGER.debug("Received call-related event");
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
                LOGGER.debug("Call has started (state=" + state + ")");
                CursedCarHomeConfig config = new CursedCarHomeConfig(context);
                if (config.getWorkaroundEnabled()) {
                    LOGGER.debug("Speakerphone monitoring is enabled, will disable speakerphone");
                    disableSpeakerphone(context);
                } else {
                    LOGGER.debug("Speakerphone monitoring is NOT enabled, ignoring event");
                }
            } else {
                LOGGER.debug("Ignoring call with state (state=" + state + ")");
            }
        } else {
            LOGGER.debug("Ignoring event with no extras");
        }
    }

    /** Disable the speakerphone. */
    private static void disableSpeakerphone(Context context) {
        // Behavior for the speakerphone is kind of strange, and from what I've
        // read online there are various different behaviors in different
        // versions of the Android OS.  Testing for 2.3, I've found that I have
        // to set MODE_IN_CALL first, then turn the speaker off, then set
        // MODE_CURRENT to "apply" the change.  The reported state does not
        // seem to work properly, either -- often it's shown as "off" when I
        // can tell that it's actually on when testing in the emulator.
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMode(AudioManager.MODE_IN_CALL);
        audioManager.setSpeakerphoneOn(false);
        audioManager.setMode(AudioManager.MODE_CURRENT);
    }

}
