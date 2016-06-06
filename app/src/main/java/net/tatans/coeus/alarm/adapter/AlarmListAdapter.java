package net.tatans.coeus.alarm.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.activities.AddAlarmActivity;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.alarm.utils.Alarms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cly on 2016/6/3.
 */
public class AlarmListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Alarm> alarms = new ArrayList<Alarm>();

    public AlarmListAdapter(Context context) {
        this.mContext = context;
        getAlarm();
    }

    final static class ViewHolder {
        private RelativeLayout lyt_alarm_item;
        private TextView tv_time_alarm;
        private TextView tv_week;
        private TextView tv_state_on_off;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_main_alarm_list, null);
            viewHolder.lyt_alarm_item = (RelativeLayout) convertView.findViewById(R.id.lyt_alarm_item);
            viewHolder.tv_time_alarm = (TextView) convertView.findViewById(R.id.tv_time_alarm);
            viewHolder.tv_week = (TextView) convertView.findViewById(R.id.tv_week);
            viewHolder.tv_state_on_off = (TextView) convertView.findViewById(R.id.tv_state_on_off);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_time_alarm.setText(AddAlarmActivity.changeTimeStyle(alarms.get(position).hour)
                + ":" + AddAlarmActivity.changeTimeStyle(alarms.get(position).minutes));
        viewHolder.tv_week.setText(alarms.get(position).daysOfWeek.toString(mContext, true));
        viewHolder.tv_state_on_off.setText(alarms.get(position).enabled ? "开启" : "未开启");
        viewHolder.lyt_alarm_item.setOnClickListener(new ItemClickListener(position));
        return convertView;
    }

    private void getAlarm() {
        //获取闹钟的cursor
        Cursor mCursor = Alarms.getAlarmsCursor(mContext.getContentResolver());
        Log.e("alarmTime", "alarm：" + mCursor.getCount());
        while (mCursor.moveToNext()) {
            Alarm alarm = new Alarm(mCursor);
            alarms.add(alarm);
        }
    }

    private class ItemClickListener implements View.OnClickListener {
        private int mPosition;

        public ItemClickListener(int position) {
            this.mPosition = position;
        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(mContext, AddAlarmActivity.class);
            i.putExtra("alarm_id", alarms.get(mPosition).id);
            mContext.startActivity(i);
        }
    }
}
