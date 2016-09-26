package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.network.tools.TatansActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.coeus.network.view.ViewInject;

/**
 * Created by Administrator on 2016/5/31.
 */
public class SetAlarmTimeMinuteActivity extends TatansActivity implements View.OnClickListener {
    @ViewInject(id = R.id.tv_custom_minute_set)
    TextView tv_custom_minute_set;
    @ViewInject(id = R.id.lyt_confirm, click = "onClick")
    LinearLayout lytConfirm;
    @ViewInject(id = R.id.add_one_minute, click = "onClick")
    LinearLayout addOneMinute;
    @ViewInject(id = R.id.minus_one_minute, click = "onClick")
    LinearLayout minusOneMinute;
    @ViewInject(id = R.id.add_ten_minute, click = "onClick")
    LinearLayout addTenMinute;
    @ViewInject(id = R.id.minus_ten_minute, click = "onClick")
    LinearLayout minusTenMinute;
    private int hour = 0;
    private int minute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_minute);
        tv_custom_minute_set.setText(getIntent().getStringExtra("hour"));
        splitText();
        setTitle("分钟设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyt_confirm:
                confirm();
                break;
            case R.id.add_one_minute:
                addOneMinute();
                break;
            case R.id.minus_one_minute:
                minusOneMinute();
                break;
            case R.id.add_ten_minute:
                addFiveMinute();
                break;
            case R.id.minus_ten_minute:
                minusFiveMinute();
                break;
        }
    }

    public void confirm() {
        TatansToast.showAndCancel("确定");
        Intent i = new Intent();
        i.putExtra("alarm_time", tv_custom_minute_set.getText().toString());
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    public void addOneMinute() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, 1, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    public void minusOneMinute() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, -1, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    public void addFiveMinute() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, 10, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    public void minusFiveMinute() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, -10, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    private void splitText() {
        String[] time = tv_custom_minute_set.getText().toString().split(":");
        setHour(Integer.valueOf(time[0]));
        setMinute(Integer.valueOf(time[1]));
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getHour() {

        return hour;
    }

    public int getMinute() {
        return minute;
    }

}
