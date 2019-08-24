_Note: As of December 2014, I no longer own the phone which had all of these
problems (a Google-branded T-Mobile G2 purchased in late 2010).  As a result, I
do not expect to enhance this code.  It is maintained for historical reference_

# Cursed Car Home

## The Problem

My Android phone is "cursed" periodically by a phantom that repeatedly turns on
the Car Home application as if the phone were being docked. (It's probably a
hardware problem.) Besides the annoyance caused by the Car Home application
itself, a bigger problem is that Car Home enables the speakerphone and never
turns it back off.

## The Solution

I've tried several of the existing applications in the Android marketplace
(such as Disable Car Home and Dock No-Op), but nothing has quite worked for me.
This application is my attempt to solve the problem. It's a "big hammer"
approach -- once a dock event is handled, dock mode is immediately disabled.
Then, a background process starts.  This process watches for future dock events
and also kills Car Home if it's ever found running.  Separately, I also hook
into the phone call process to disable the speakerphone every time an inbound
or outbound call is initiated.

## Using the Application

Install the application from the [Google Play Store](https://play.google.com/store/apps/details?id=com.cedarsolutions.cursed).

By default, all features are disabled.  I recommend enabling all features.
Once you have enabled a feature, the default settings for the other options
should work fine for most people. If you tend to see clusters of events over a
period of time, you may want to change some values.

For instance, on my phone, I often get several to a dozen dock events over a 30
minute window (or sometimes even longer).  When first started, the background
process is very aggressive about how often it looks for problems.  As the time
between discovered problems grows, the background process gradually slows down
and looks for problems less often.

If you are seeing clusters of events, you may find that it takes too long for
the background process to notice problems and clean them up.  You can adjust
this behavior by decreasing the maximum delay.  You may also want to increase
the maximum lifetime of the background process.

I find the daily report useful, because it alerts me to problems that might
have occurred when I was not looking at the phone (such as overnight when
I am sleeping).  However, you can disable the report if you want to.  Even if
the daily report is disabled, you can always run the report manually. Open
the application and choose **Menu > Run Daily Report**.

## Required Permissions

Cursed Car Home requires a fairly intrusive set of device permissions.  I wish
I could do with less, but part of the reason Cursed Car Home works better for
me is because it's different -- and the things it does require a lot of
permissions. Below is a list of the required permissions and what they are
needed for.

| **Permission** | **Purpose** |
|:---------------|:------------|
| RECEIVE\_BOOT\_COMPLETED | Schedule the daily report |
| KILL\_BACKGROUND\_PROCESSES | Kill the Car Home application |
| PROCESS\_OUTGOING\_CALLS | Check the speakerphone state when calls start |
| MODIFY\_AUDIO\_SETTINGS | Disable the speakerphone |
| READ\_PHONE\_STATE | Disable the speakerphone |

## Privacy Policy for Cursed Car Home

_This formal privacy policy is intended to address the 
[Google Play Privacy and Security](https://play.google.com/about/privacy-security/) 
requirements effective March 15, 2017._

Cursed Car Home does not read, capture, store, or transmit any private information
whatsoever.   

Below is a list of the required permissions and what they are used for:

| **Permission** | **Purpose** |
|:---------------|:------------|
| RECEIVE\_BOOT\_COMPLETED | Schedule the daily report |
| KILL\_BACKGROUND\_PROCESSES | Kill the Car Home application |
| PROCESS\_OUTGOING\_CALLS | Check the speakerphone state when calls start |
| MODIFY\_AUDIO\_SETTINGS | Disable the speakerphone |
| READ\_PHONE\_STATE | Disable the speakerphone |

These represent the bare minimum of permissions necessary for the app
to do what it needs to do.  

As always, please remember that this is open source code. The entire implementation 
is available here on GitHub, and you or others are free to review or audit 
the implementation if you have concerns about how these permissions are being used.
