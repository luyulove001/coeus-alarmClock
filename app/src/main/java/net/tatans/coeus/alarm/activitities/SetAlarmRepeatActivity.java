package net.tatans.coeus.alarm.activitities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.adapter.AlarmRepeatAdapter;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by Administrator on 2016/5/27.
 */
@ContentView(R.layout.activity_public_list)
public class SetAlarmRepeatActivity extends BaseActivity {
    @ViewIoc(R.id.lv_bell_time)
    private ListView lv_bell_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lv_bell_time.setAdapter(new AlarmRepeatAdapter(SetAlarmRepeatActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Const.REQUEST_CUSTOM_WEEK && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK, data);
        }
    }
}