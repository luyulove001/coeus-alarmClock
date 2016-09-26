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
 * Created by Administrator on 2016/5/30.
 */
public class SetAlarmTimeHourActivity extends TatansActivity implements View.OnClickListener {
    @ViewInject(id = R.id.tv_custom_hour_set)
    TextView tvCustomHourSet;
    @ViewInject(id = R.id.lyt_confirm, click = "onClick")
    LinearLayout lytConfirm;
    @ViewInject(id = R.id.add_one_hour, click = "onClick")
    LinearLayout addOneHour;
    @ViewInject(id = R.id.minus_one_hour, click = "onClick")
    LinearLayout minusOneHour;
    @ViewInject(id = R.id.add_five_hour, click = "onClick")
    LinearLayout addFiveHour;
    @ViewInject(id = R.id.minus_five_hour, click = "onClick")
    LinearLayout minusFiveHour;
    private int hour = 0;
    private int minute = 0;
    private static final int RequestMinute = 010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_time_hour);
        tvCustomHourSet.setText(getIntent().getStringExtra("time_set"));
        splitText();
        setTitle("小时设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lyt_confirm:
                confirm();
                break;
            case R.id.add_one_hour:
                addOneHour();
                break;
            case R.id.minus_one_hour:
                minusOneHour();
                break;
            case R.id.add_five_hour:
                addFiveHour();
                break;
            case R.id.minus_five_hour:
                minusFiveHour();
                break;
        }
    }

    public void confirm() {
        TatansToast.showAndCancel("确定");
        Intent i = new Intent(this, SetAlarmTimeMinuteActivity.class);
        i.putExtra("hour", tvCustomHourSet.getText().toString());
        startActivityForResult(i, RequestMinute);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestMinute && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }

    public void addOneHour() {
        tvCustomHourSet.setText(calculateTime(false, 1, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    public void minusOneHour() {
        tvCustomHourSet.setText(calculateTime(false, -1, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    public void addFiveHour() {
        tvCustomHourSet.setText(calculateTime(false, 5, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    public void minusFiveHour() {
        tvCustomHourSet.setText(calculateTime(false, -5, getHour(), getMinute()));
        splitText();
        TatansToast.showAndCancel(AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute));
    }

    private void splitText() {
        String[] time = tvCustomHourSet.getText().toString().split(":");
        setHour(Integer.valueOf(time[0]));
        setMinute(Integer.valueOf(time[1]));
    }

    /**
     * @param isMinute 是否是对分钟的操作
     * @param time     增加的时间量
     * @param hour     用于操作的小时数
     * @param minute   用于操作的分钟数
     */
    public static String calculateTime(boolean isMinute, int time, int hour, int minute) {
        if (!isMinute)
            hour += time;
        else
            minute += time;
        if (hour < 0)
            hour = 0;
        else if (hour >= 23)
            hour = 23;
        if (minute < 0) {
            if (hour <= 0) {
                minute = 0;
                hour = 0;
            } else {
                minute += 60;
                hour -= 1;
            }
        } else if (minute >= 60) {
            if (hour >= 23) {
                hour = 23;
                minute = 59;
            } else {
                minute -= 60;
                hour += 1;
            }
        }
        return AddAlarmActivity.changeTimeStyle(hour) + ":" + AddAlarmActivity.changeTimeStyle(minute);
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
