package net.tatans.coeus.alarm.activitities;

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
 * Created by Administrator on 2016/5/30.
 */
@ContentView(R.layout.activity_set_time_hour)
public class SetAlarmTimeHourActivity extends BaseActivity {
    @ViewIoc(R.id.tv_custom_hour_set)
    TextView tv_custom_hour_set;
    private int hour = 0, minute = 0;
    private static final int RequestMinute = 010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_custom_hour_set.setText(getIntent().getStringExtra("time_set"));
    }

    @OnClick(R.id.lyt_confirm)
    public void confirm() {
        TatansToast.showAndCancel(getString(R.string.btn_confirm).toString());
        Intent i = new Intent(this, SetAlarmTimeMinuteActivity.class);
        i.putExtra("hour", tv_custom_hour_set.getText().toString());
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

    @OnClick(R.id.add_one_hour)
    public void addOneHour() {
        splitText();
        tv_custom_hour_set.setText(calculateTime(false, 1, getHour(), getMinute()));
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
    }

    @OnClick(R.id.minus_one_hour)
    public void minusOneHour() {
        splitText();
        tv_custom_hour_set.setText(calculateTime(false, -1, getHour(), getMinute()));
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
    }

    @OnClick(R.id.add_five_hour)
    public void addFiveHour() {
        splitText();
        tv_custom_hour_set.setText(calculateTime(false, 5, getHour(), getMinute()));
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
    }

    @OnClick(R.id.minus_five_hour)
    public void minusFiveHour() {
        splitText();
        tv_custom_hour_set.setText(calculateTime(false, -5, getHour(), getMinute()));
        TatansToast.showAndCancel(hour + "小时" + minute + "分");
    }

    private void splitText() {
        String[] time = tv_custom_hour_set.getText().toString().split(":");
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
