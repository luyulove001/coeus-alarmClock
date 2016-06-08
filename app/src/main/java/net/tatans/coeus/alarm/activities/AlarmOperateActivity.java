package net.tatans.coeus.alarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.alarm.utils.Alarms;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * 闹钟菜单页面，开/关闹钟，重设闹钟，删除闹钟
 */
@ContentView(R.layout.activity_alarm_operate)
public class AlarmOperateActivity extends BaseActivity {
    @ViewIoc(R.id.close_alarm)
    private TextView tv_close;
    private int mId;
    private Alarm mAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("闹钟菜单");
        mId = getIntent().getIntExtra("alarm_id", 0);
        mAlarm = Alarms.getAlarm(getContentResolver(), mId);
        tv_close.setText(mAlarm.enabled ? getString(R.string.close_alarm) : getString(R.string.open_alarm));
        tv_close.setContentDescription(mAlarm.enabled ? getString(R.string.close_alarm) + "。按钮"
                : getString(R.string.open_alarm) + "。按钮");
    }

    @OnClick(R.id.close_alarm)
    public void close_alarm() {
        mAlarm.enabled = !mAlarm.enabled;
        Alarms.setAlarm(this, mAlarm);
        finish();
    }

    @OnClick(R.id.reset_alarm)
    public void reset_alarm() {
        Intent i = new Intent(AlarmOperateActivity.this, AddAlarmActivity.class);
        i.putExtra("alarm_id", mId);
        startActivity(i);
    }

    @OnClick(R.id.del_alarm)
    public void del_alarm() {
        Alarms.deleteAlarm(this, mId);
        TatansToast.showAndCancel("闹钟删除成功");
        finish();
    }

}
