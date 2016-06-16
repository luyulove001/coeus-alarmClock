package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by Administrator on 2016/5/31.
 */
@ContentView(R.layout.activity_set_time_minute)
public class SetAlarmTimeMinuteActivity extends BaseActivity {
    @ViewIoc(R.id.tv_custom_minute_set)
    TextView tv_custom_minute_set;
    private int hour = 0, minute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_custom_minute_set.setText(getIntent().getStringExtra("hour"));
        splitText();
    }

    @OnClick(R.id.lyt_confirm)
    public void confirm() {
        TatansToast.showAndCancel("确定");
        Intent i = new Intent();
        i.putExtra("alarm_time", tv_custom_minute_set.getText().toString());
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    @OnClick(R.id.add_one_minute)
    public void addOneHour() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, 1, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
    }

    @OnClick(R.id.minus_one_minute)
    public void minusOneHour() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, -1, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
    }

    @OnClick(R.id.add_ten_minute)
    public void addFiveHour() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, 10, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
    }

    @OnClick(R.id.minus_ten_minute)
    public void minusFiveHour() {
        tv_custom_minute_set.setText(SetAlarmTimeHourActivity.calculateTime(true, -10, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
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
