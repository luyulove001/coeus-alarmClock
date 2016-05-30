package net.tatans.coeus.alarm.activitities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import net.tatans.coeus.alarm.*;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by cly on 2016/5/27.
 */
@ContentView(R.layout.activity_add_alarm)
public class AddAlarmActivity extends BaseActivity {

    @ViewIoc(R.id.switch_vibrate)
    private ImageView switch_vibrate;

    @ViewIoc(R.id.alarm_repeat)
    private TextView alarm_repeat;

    @ViewIoc(R.id.alert)
    private TextView alert;

    @ViewIoc(R.id.time_alarm)
    private TextView alarm_time;

    private boolean btnOnOff = false;
    private Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.layout_alarm_time)
    public void setTime_alarm() {
        TatansStartActivity(net.tatans.coeus.alarm.MainActivity.class);
    }

    @OnClick(R.id.layout_repeat)
    public void setAlarm_repeat() {
        intent.setClass(AddAlarmActivity.this, SetAlarmRepeatActivity.class);
        startActivityForResult(intent, Const.REQUEST_REPEAT);
    }

    @OnClick(R.id.layout_alert)
    public void setAlert() {
        TatansStartActivity(SetAlarmRepeatActivity.class);
    }

    @OnClick(R.id.layout_alarm_vibrate)
    public void setSwitch_vibrate() {
        switch_vibrate.setImageResource(btnOnOff ? R.mipmap.open_icon : R.mipmap.close_icon);
        btnOnOff = !btnOnOff;
    }

    @OnClick(R.id.lyt_confirm)
    public void confirm() {
        // TODO: 2016/5/30 将预设属性保存到数据库 provider  值从后面的其他页面传回本页面
        /**
         * alarm_repeat --> alarm.daysOfWeek
         * alarm_time --> alarm.time  alarm.hour  alarm.minute
         * btnOnOff --> alarm.vibrate
         * alarm_alert --> alarm.alert
         * if alert == null --> alarm.silent
         * 默认 alarm.enable = true
         */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_TIME && resultCode == Activity.RESULT_OK) {

        } else if (requestCode == Const.REQUEST_REPEAT && resultCode == Activity.RESULT_OK) {
            alarm_repeat.setText(data.getStringExtra("repeat_model"));
        } else if (requestCode == Const.REQUEST_ALERT && resultCode == Activity.RESULT_OK) {

        }
    }

}
