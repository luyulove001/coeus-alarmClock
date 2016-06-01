package net.tatans.coeus.alarm.activitities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.alarm.utils.Alarms;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by cly on 2016/5/27.
 * 添加闹钟页面，也是设置闹钟页面
 */
@ContentView(R.layout.activity_add_alarm)
public class AddAlarmActivity extends BaseActivity {

    @ViewIoc(R.id.switch_vibrate)
    private ImageView switch_vibrate;//震动

    @ViewIoc(R.id.alarm_repeat)
    private TextView tv_alarm_repeat;//重复

    @ViewIoc(R.id.alert)
    private TextView tv_alert;//铃声

    @ViewIoc(R.id.time_alarm)
    private TextView tv_alarm_time;//闹钟时间
    private int mHour = 0, mMinute = 0;

    private boolean btnOnOff = false;
    private Intent intent;
    private Alarm mOriginalAlarm;//原闹钟若是添加闹钟，则为新闹钟
    private int mId;//判断ID是否为-1来判断是新闹钟还是已存在的闹钟
    private String mLabel;
    private Alarm.DaysOfWeek newDaysOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        mId = i.getIntExtra(Alarms.ALARM_ID, -1);
        Alarm alarm = null;
        if (mId == -1) {
            alarm = new Alarm();
        } else {
            alarm = Alarms.getAlarm(getContentResolver(), mId);
            // 防止空指针异常.
            if (alarm == null) {
                finish();
                return;
            }
        }
        mOriginalAlarm = alarm;
        updateAlarmView(mOriginalAlarm);
    }

    private void updateAlarmView(Alarm alarm) {
        // TODO: 2016/6/1  需要改成08:05格式
        tv_alarm_time.setText(alarm.hour + ":" + alarm.minutes); 
        switch_vibrate.setImageResource(alarm.vibrate ? R.mipmap.open_icon : R.mipmap.close_icon);
        tv_alarm_repeat.setText(alarm.daysOfWeek.toString(getApplicationContext(), true));
        mHour = alarm.hour;
        mMinute = alarm.minutes;
        newDaysOfWeek = alarm.daysOfWeek;
        mLabel = "0";
        // TODO: 2016/5/31 alert 界面更新
    }

    @OnClick(R.id.layout_alarm_time)
    public void setTime_alarm() {
        intent = new Intent();
        intent.setClass(AddAlarmActivity.this, SetAlarmTimeHourActivity.class);
        intent.putExtra("time_set", tv_alarm_time.getText().toString());
        startActivityForResult(intent, Const.REQUEST_TIME);
    }

    @OnClick(R.id.layout_repeat)
    public void setAlarm_repeat() {
        intent = new Intent();
        intent.putExtra("mark", Const.REQUEST_REPEAT + "");
        // TODO: 2016/5/31 保存preference有问题，需要把daysOfWeek传过去
        intent.setClass(AddAlarmActivity.this, SetAlarmRepeatActivity.class);
        startActivityForResult(intent, Const.REQUEST_REPEAT);
    }

    @OnClick(R.id.layout_alert)
    public void setAlert() {
        intent = new Intent();
        intent.putExtra("mark", Const.REQUEST_ALERT + "");
        intent.setClass(AddAlarmActivity.this, SetAlarmRepeatActivity.class);
        startActivityForResult(intent, Const.REQUEST_ALERT);
//        TatansStartActivity(SetAlarmRepeatActivity.class);
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
         * tv_alarm_repeat --> alarm.daysOfWeek
         * tv_alarm_time --> alarm.time//暂时不管  alarm.hour  alarm.minute
         * btnOnOff --> alarm.vibrate
         * alarm_alert --> alarm.alert //暂时存到label中
         * if tv_alert == null --> alarm.silent
         * 默认 alarm.enable = true
         */
        saveAlarm();
        TatansToast.showAndCancel("保存成功");
        finish();
    }

    /**
     * 根据id 添加/更新数据库中的alarm
     * @return
     */
    private long saveAlarm() {
        Alarm alarm = new Alarm();
        alarm.id = mId;
        alarm.enabled = true;
        alarm.hour = mHour;
        alarm.minutes = mMinute;
        alarm.daysOfWeek = newDaysOfWeek;
        alarm.vibrate = btnOnOff;
        alarm.label = mLabel;
        Log.e("antony", mId + ","+mHour + ","+mMinute + ","+newDaysOfWeek.toString(getApplicationContext(), true) + "," + btnOnOff + "," + mLabel);

        long time;
        if (alarm.id == -1) {
            time = Alarms.addAlarm(this, alarm);
            // addAlarm populates the alarm with the new id. Update mId so that
            // changes to other preferences update the new alarm.
            mId = alarm.id;
            Log.e("antony", mId+"");
        } else {
            time = Alarms.setAlarm(this, alarm);
        }
        return time;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_TIME && resultCode == Activity.RESULT_OK) {
            tv_alarm_time.setText(data.getStringExtra("alarm_time"));
            String[] str = tv_alarm_time.getText().toString().split(":");
            mHour = Integer.valueOf(str[0]);
            mMinute = Integer.valueOf(str[1]);
        } else if (requestCode == Const.REQUEST_REPEAT && resultCode == Activity.RESULT_OK) {
            newDaysOfWeek = (Alarm.DaysOfWeek) data.getSerializableExtra("days_of_week");
            tv_alarm_repeat.setText(newDaysOfWeek.toString(getApplicationContext(), true));
        } else if (requestCode == Const.REQUEST_ALERT && resultCode == Activity.RESULT_OK) {
            tv_alert.setText(data.getStringExtra("bell_uri"));
            mLabel = data.getStringExtra("bell_position");
        }
    }

}
