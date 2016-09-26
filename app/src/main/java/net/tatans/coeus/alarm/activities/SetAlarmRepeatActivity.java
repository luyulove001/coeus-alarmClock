package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.adapter.AlarmRepeatAdapter;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.TatansActivity;
import net.tatans.coeus.network.view.ViewInject;

/**
 * Created by Administrator on 2016/5/27.
 */
public class SetAlarmRepeatActivity extends TatansActivity {
    @ViewInject(id = R.id.lv_bell_time)
    private ListView lv_bell_time;
    private String mark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_list);
        lv_bell_time.setAdapter(new AlarmRepeatAdapter(SetAlarmRepeatActivity.this, getIntent()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CUSTOM_WEEK && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data);
            finish();
        }
    }
}
