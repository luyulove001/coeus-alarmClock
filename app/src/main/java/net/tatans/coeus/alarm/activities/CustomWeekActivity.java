package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.adapter.CustomWeekAdapter;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by cly on 2016/5/30.
 */
@ContentView(R.layout.activity_public_list)
public class CustomWeekActivity extends BaseActivity {
    @ViewIoc(R.id.lyt_confirm)
    LinearLayout lyt_confirm;
    @ViewIoc(R.id.lv_bell_time)
    ListView lv_weekday;
    private Alarm.DaysOfWeek dayOfWeek;
    private CustomWeekAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lyt_confirm.setVisibility(View.VISIBLE);
        adapter = new CustomWeekAdapter(getApplicationContext(), getIntent());
        lv_weekday.setAdapter(adapter);
        dayOfWeek = (Alarm.DaysOfWeek) getIntent().getSerializableExtra("dayOfWeek");
    }

    @OnClick(R.id.lyt_confirm)
    public void confirm() {
        Intent i = new Intent();
        i.putExtra("days_of_week", adapter.getDaysOfWeek() == null ? dayOfWeek : adapter.getDaysOfWeek());
        setResult(Activity.RESULT_OK, i);
        finish();
    }
}
