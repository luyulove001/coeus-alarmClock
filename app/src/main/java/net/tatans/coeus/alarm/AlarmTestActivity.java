package net.tatans.coeus.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.rhea.network.view.ContentView;

import java.util.Calendar;

/**
 * Created by Administrator on 2016/5/25.
 */
@ContentView(R.layout.activity_alarm_test)
public class AlarmTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AlarmTestActivity.this, OneShotAlarm.class);
                PendingIntent sender = PendingIntent.getBroadcast(AlarmTestActivity.this,
                        0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

                // Schedule the alarm!
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 15);
                calendar.set(Calendar.MINUTE, 47);
                calendar.set(Calendar.SECOND, 10);
                calendar.set(Calendar.MILLISECOND, 0);

                am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        0, sender);
                TatansToast.showAndCancel("OnClickListener()");
            }
        });
    }

//    @OnClick(R.id.button)
    public void oneShotAlarm() {

        Intent intent = new Intent(AlarmTestActivity.this, OneShotAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                AlarmTestActivity.this, 0, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        TatansToast.showAndCancel("oneShotAlarm()");
    }

//    @OnClick(R.id.button2)
    public void repeatingAlarm() {

        Intent intent = new Intent(AlarmTestActivity.this,
                RepeatingAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                AlarmTestActivity.this, 0, intent, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(), 10 * 1000, sender);
        TatansToast.showAndCancel("repeatingAlarm()");
    }

//    @OnClick(R.id.button3)
    public void stopRepeating() {
        Intent intent = new Intent(AlarmTestActivity.this,
                RepeatingAlarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(
                AlarmTestActivity.this, 0, intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.cancel(sender);
        TatansToast.showAndCancel("stopRepeating()");
    }
}
