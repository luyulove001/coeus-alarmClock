package net.tatans.coeus.alarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.adapter.AlarmListAdapter;
import net.tatans.coeus.network.tools.TatansActivity;
import net.tatans.coeus.network.view.ViewInject;

/**
 * coeus-alarmClock [v 2.0.0]
 * classes : net.tatans.coeus.alarm.activities.MainActivity
 * Yuriy create at 2016/5/26 9:41
 */
public class MainActivity extends TatansActivity implements OnClickListener {

    @ViewInject(id = R.id.lv_alarm_clock_list)
    private ListView lv_alarm_clock_list;
    @ViewInject(id = R.id.add_alarm_clock, click = "onClick")
    private LinearLayout add_alarm_clock;
    @ViewInject(id = R.id.alarm_clock_setting, click = "onClick")
    private LinearLayout alarm_clock_setting;
    /**
     * 闹钟列表
     */
    private AlarmListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_alarm);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new AlarmListAdapter(this);
        lv_alarm_clock_list.setAdapter(adapter);
    }

    /**
     * 添加闹钟
     */
    private void addAlarmClock() {
        startActivity(new Intent(this, AddAlarmActivity.class));
    }

    /**
     * 闹钟设置
     */
    private void alarmClockSetting() {
        startActivity(new Intent(this, SettingActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_alarm_clock:
                addAlarmClock();
                break;
            case R.id.alarm_clock_setting:
                alarmClockSetting();
                break;
        }
    }
}
