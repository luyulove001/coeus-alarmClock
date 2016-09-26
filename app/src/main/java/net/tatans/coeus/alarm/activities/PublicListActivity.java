package net.tatans.coeus.alarm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.adapter.PublicListAdapter;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.TatansActivity;
import net.tatans.coeus.network.view.ViewInject;

/**
 * Created by SiLiPing on 2016/5/30.
 * 根据sign加载不同的String[]
 */
public class PublicListActivity extends TatansActivity {

    @ViewInject(id = R.id.lv_bell_time)
    private ListView lv_bell_time;
    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_list);
        Intent intent = getIntent();
        sign = intent.getStringExtra("sign");
        if (sign.equals(Const.KEY_ALARM_BELL_TIME)) {
            lv_bell_time.setAdapter(new PublicListAdapter(this, Const.BELL_TIME_LIST, sign));
            setTitle(getString(R.string.alarm_in_silent_time));
        } else {
            lv_bell_time.setAdapter(new PublicListAdapter(this, Const.BELL_INTERVAL_TIME_LIST, sign));
            setTitle(getString(R.string.alarm_snooze_duration));
        }
    }
}
