package net.tatans.coeus.alarm.activitities;

import net.tatans.coeus.network.tools.TatansApplication;

/**
 * coeus-alarmclock [v 2.0.0]
 * classes : net.tatans.coeus.alarm.activitities.AlarmClockApplication
 * Yuriy create at 2016/5/26 16:19
 */
public class AlarmClockApplication  extends TatansApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        setAppSpeaker();
    }
}