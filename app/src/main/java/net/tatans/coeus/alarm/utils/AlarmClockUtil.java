package net.tatans.coeus.alarm.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import net.tatans.coeus.alarm.ClockActivity;
import net.tatans.coeus.alarm.activitities.MainActivity;
import net.tatans.coeus.network.tools.TatansApplication;

import java.util.Calendar;

/**
 * coeus-alarmClock [v 2.0.0]
 * classes : net.tatans.coeus.alarm.utils.AlarmClockUtil
 * Yuriy create at 2016/5/26 9:23
 */
public class AlarmClockUtil {
    public void setAlarmClock(int hour,int minute){
        // 指定要启动的是Activity组件,通过PendingIntent调用getActivity来设置
        Intent intent = new Intent(TatansApplication.getContext(), ClockActivity.class);
        PendingIntent pi = PendingIntent.getActivity(TatansApplication.getContext(), 0, intent, 0);
        // ①获取AlarmManager对象:
        AlarmManager alarmManager = (AlarmManager) TatansApplication.getContext().getSystemService(TatansApplication.getContext().ALARM_SERVICE);
        Calendar currentTime = Calendar.getInstance();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        // 根据用户选择的时间来设置Calendar对象
        c.set(Calendar.HOUR, hour);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);                //设置闹钟的秒数
        c.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数
        // ②设置AlarmManager在Calendar对应的时间启动Activity
        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
    }
    public void turnOffAlarmClock(){

    }


}
