package net.tatans.coeus.alarm.activities;

import net.tatans.coeus.network.tools.TatansApplication;

/**
 * coeus-alarmclock [v 2.0.0]
 * classes : net.tatans.coeus.alarm.activitities.AlarmClockApplication
 * Yuriy create at 2016/5/26 16:19
 */
public class AlarmClockApplication  extends TatansApplication{
    public static final String PREFERENCES = "AlarmClock";
    private static AlarmClockApplication  sInstance ;
    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
//        SpeechUtility.createUtility(AlarmClockApplication.this,"appid="+"579affe0");
    }
    public static AlarmClockApplication getInstance() {
        return sInstance;
    }
}
