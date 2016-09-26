package net.tatans.coeus.alarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.alarm.utils.Alarms;
import net.tatans.coeus.network.tools.TatansActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.coeus.network.view.ViewInject;

/**
 * 闹钟菜单页面，开/关闹钟，重设闹钟，删除闹钟
 */
public class AlarmOperateActivity extends TatansActivity implements View.OnClickListener {
    @ViewInject(id = R.id.close_alarm, click = "onClick")
    private TextView tv_close;
    @ViewInject(id = R.id.reset_alarm, click = "onClick")
    private TextView reset_alarm;
    @ViewInject(id = R.id.del_alarm, click = "onClick")
    private TextView del_alarm;
    private int mId;
    private Alarm mAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_operate);
        setTitle("闹钟菜单");
        mId = getIntent().getIntExtra("alarm_id", 0);
        mAlarm = Alarms.getAlarm(getContentResolver(), mId);
        tv_close.setText(mAlarm.enabled ? getString(R.string.close_alarm) : getString(R.string.open_alarm));
        tv_close.setContentDescription(mAlarm.enabled ? getString(R.string.close_alarm) + "。按钮"
                : getString(R.string.open_alarm) + "。按钮");
    }

    public void close_alarm() {
        mAlarm.enabled = !mAlarm.enabled;
        Alarms.setAlarm(this, mAlarm);
        finish();
    }

    public void reset_alarm() {
        Intent i = new Intent(AlarmOperateActivity.this, AddAlarmActivity.class);
        i.putExtra("alarm_id", mId);
        startActivityForResult(i, 100);
    }

    public void del_alarm() {
        Alarms.deleteAlarm(this, mId);
        TatansToast.showAndCancel("闹钟删除成功");
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == 101) finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_alarm:
                reset_alarm();
                break;
            case R.id.close_alarm:
                close_alarm();
                break;
            case R.id.del_alarm:
                del_alarm();
                break;
        }
    }
}
