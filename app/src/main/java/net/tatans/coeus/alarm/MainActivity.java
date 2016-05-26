package net.tatans.coeus.alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import net.tatans.coeus.network.tools.TatansToast;

import java.util.Calendar;

/**
 * Created by Lion on 2016/5/26.
 */
public class MainActivity extends Activity {

    private Button btnSetClock;
    private Button btnbtnCloseClock;
    private AlarmManager alarmManager;
    private PendingIntent pi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSetClock = (Button) findViewById(R.id.btnSetClock);
        btnbtnCloseClock = (Button) findViewById(R.id.btnCloseClock);

        // ①获取AlarmManager对象:
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        // 指定要启动的是Activity组件,通过PendingIntent调用getActivity来设置
        Intent intent = new Intent(MainActivity.this, ClockActivity.class);
        pi = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

        btnSetClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar currentTime = Calendar.getInstance();
                // 弹出一个时间设置的对话框,供用户选择时间
                new TimePickerDialog(MainActivity.this, 0,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                //设置当前时间
                                Calendar c = Calendar.getInstance();
                                c.setTimeInMillis(System.currentTimeMillis());
                                // 根据用户选择的时间来设置Calendar对象
                                c.set(Calendar.HOUR, hourOfDay);
                                c.set(Calendar.MINUTE, minute);
                                c.set(Calendar.SECOND, 0);                //设置闹钟的秒数
                                c.set(Calendar.MILLISECOND, 0);            //设置闹钟的毫秒数
                                // ②设置AlarmManager在Calendar对应的时间启动Activity
                                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
                                // 提示闹钟设置完毕:
                                TatansToast.showAndCancel("闹钟设置完毕");
                            }
                        }, currentTime.get(Calendar.HOUR_OF_DAY), currentTime.get(Calendar.MINUTE), false).show();
                btnbtnCloseClock.setVisibility(View.VISIBLE);
            }
        });

        btnbtnCloseClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pi);
                btnbtnCloseClock.setVisibility(View.GONE);
                TatansToast.showAndCancel("闹钟已取消");
            }
        });
    }
}
