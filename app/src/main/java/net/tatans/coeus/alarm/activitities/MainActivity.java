package net.tatans.coeus.alarm.activitities;

import android.widget.ListView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * coeus-alarmClock [v 2.0.0]
 * classes : net.tatans.coeus.alarm.activitities.MainActivity
 * Yuriy create at 2016/5/26 9:41
 */
@ContentView(R.layout.activity_main_alarm)
public class MainActivity extends BaseActivity{

    @ViewIoc(R.id.lv_alarm_clock_list) private ListView lv_alarm_clock_list;/**闹钟列表*/

    /**添加闹钟*/
    @OnClick(R.id.add_alarm_clock)
    private void addAlarmClock(){
        TatansStartActivity(AddAlarmActivity.class);
    }

    /**闹钟设置*/
    @OnClick(R.id.alarm_clock_setting)
    private void alarmClockSetting(){
        TatansStartActivity(SettingActivity.class);
    }

}
