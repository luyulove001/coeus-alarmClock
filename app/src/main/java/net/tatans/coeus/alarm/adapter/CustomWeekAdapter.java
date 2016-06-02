package net.tatans.coeus.alarm.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.alarm.utils.Const;

/**
 * Created by Administrator on 2016/5/30.
 */
public class CustomWeekAdapter extends BaseAdapter {
    private String[] listData;
    private boolean[] isSelect;
    private Context mContext;
    private Alarm.DaysOfWeek daysOfWeek;

    final static class ViewHolder {
        private LinearLayout lyt_week;
        private ImageView iv_isSelect;
        private TextView tv_week;
    }

    public CustomWeekAdapter(Context ctx, Intent intent) {
        this.mContext = ctx;
        listData = Const.WEEKDAY;
        daysOfWeek = (Alarm.DaysOfWeek) intent.getSerializableExtra("dayOfWeek");
        isSelect = daysOfWeek.getBooleanArray();
    }

    @Override
    public int getCount() {
        return listData.length;
    }

    @Override
    public Object getItem(int position) {
        return listData[position];
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_public_list_item, null);
            viewHolder.lyt_week = (LinearLayout) convertView.findViewById(R.id.bell_time);
            viewHolder.iv_isSelect = (ImageView) convertView.findViewById(R.id.img_check);
            viewHolder.tv_week = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_week.setText(listData[position]);
        setSelect(viewHolder, position);
        viewHolder.lyt_week.setOnClickListener(new ItemClickListener(position, viewHolder));
        return convertView;
    }

    private void setSelect(ViewHolder viewHolder, int position) {
        if (isSelect[position]) {
            viewHolder.lyt_week.setContentDescription(listData[position] + "已选中");
            viewHolder.iv_isSelect.setBackgroundResource(R.mipmap.icon_multiple_choice);
        } else {
            viewHolder.lyt_week.setContentDescription(listData[position]);
            viewHolder.iv_isSelect.setBackgroundResource(R.color.black);
        }
    }

    private class ItemClickListener implements View.OnClickListener {
        private int mPosition;
        private ViewHolder viewH;

        public ItemClickListener(int position, ViewHolder vHolder) {
            this.mPosition = position;
            this.viewH = vHolder;
        }

        @Override
        public void onClick(View view) {
            if (!daysOfWeek.getBooleanArray()[mPosition]) {
                viewH.lyt_week.setContentDescription(listData[mPosition] + "已选中");
                viewH.iv_isSelect.setBackgroundResource(R.mipmap.icon_multiple_choice);
                daysOfWeek.set(mPosition, true);
            } else {
                viewH.lyt_week.setContentDescription(listData[mPosition]);
                viewH.iv_isSelect.setBackgroundResource(R.color.black);
                daysOfWeek.set(mPosition, false);
            }
        }
    }

    public Alarm.DaysOfWeek getDaysOfWeek() {
        return daysOfWeek;
    }
}
