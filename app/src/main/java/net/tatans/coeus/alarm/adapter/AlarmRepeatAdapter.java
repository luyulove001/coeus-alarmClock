package net.tatans.coeus.alarm.adapter;

import android.app.Activity;
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
import net.tatans.coeus.alarm.activitities.CustomWeekActivity;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.TatansPreferences;

/**
 * Created by Administrator on 2016/5/30.
 */
public class AlarmRepeatAdapter extends BaseAdapter {
    private String[] listData;
    private Context mContext;
    private String mark;

    final static class ViewHolder {
        private LinearLayout lyt_repeat;
        private ImageView iv_isSelect;
        private TextView tv_repeat;
    }

    public AlarmRepeatAdapter(Context ctx,String mk) {
        this.mContext = ctx;
        this.mark = mk;
        if (mk.equals(Const.REQUEST_REPEAT+"")){
            listData = Const.REPEAT_MODEL_LIST;
        }else{
            listData = Const.BELL_NAME;
        }
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
            viewHolder.lyt_repeat = (LinearLayout) convertView.findViewById(R.id.bell_time);
            viewHolder.iv_isSelect = (ImageView) convertView.findViewById(R.id.img_check);
            viewHolder.tv_repeat = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_repeat.setText(listData[position]);
        setSelect(viewHolder, position);
        viewHolder.lyt_repeat.setOnClickListener(new ItemClickListener(position, viewHolder));
        return convertView;
    }

    private void setSelect(ViewHolder vh, int position) {
        if (mark.equals(Const.REQUEST_REPEAT+"")){
            String repeat_model = (String) TatansPreferences.get(Const.REPEAT_PREF, "0");
            if (repeat_model.equals(position + "")) {
                vh.lyt_repeat.setContentDescription(listData[position] + "已选中");
                vh.iv_isSelect.setBackgroundResource(R.mipmap.icon_multiple_choice);
            } else {
                vh.lyt_repeat.setContentDescription(listData[position]);
                vh.iv_isSelect.setBackgroundResource(R.color.black);
            }
        }else{
            int bellID = (Integer) TatansPreferences.get(Const.BELL_URI, 0);
            if (bellID == position){
                vh.lyt_repeat.setContentDescription(listData[position] + "已选中");
                vh.iv_isSelect.setBackgroundResource(R.mipmap.icon_multiple_choice);
            }else{
                vh.lyt_repeat.setContentDescription(listData[position]);
                vh.iv_isSelect.setBackgroundResource(R.color.black);
            }
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
            viewH.iv_isSelect.setBackgroundResource(R.mipmap.icon_multiple_choice);
            viewH.lyt_repeat.setContentDescription(listData[mPosition] + ",已选中");
            Intent i = new Intent();
            if (mark.equals(Const.REQUEST_REPEAT+"")){
                TatansPreferences.put(Const.REPEAT_PREF, mPosition + "");
                if (mPosition == listData.length - 1) {
                    ((Activity) mContext).startActivityForResult(new Intent(mContext, CustomWeekActivity.class), 1);
                } else {
//                    i.putExtra("repeat_model", listData[mPosition]);
                    Alarm.DaysOfWeek daysOfWeek = new Alarm.DaysOfWeek(0);
                    switch (mPosition) {
                        case 0:
                            daysOfWeek = new Alarm.DaysOfWeek(0);//只响一次
                            break;
                        case 1:
                            daysOfWeek = new Alarm.DaysOfWeek(0x7f);//每天
                            break;
                        case 2:
                            // TODO: 2016/5/31 法定工作日
                            break;
                        case 3:
                            daysOfWeek = new Alarm.DaysOfWeek(0x1f);//周一到周五
                            break;
                        default:
                            break;
                    }
                    i.putExtra("days_of_week", daysOfWeek);
                }
            }else{
                TatansPreferences.put(Const.BELL_URI, mPosition);
                i.putExtra("bell_uri", listData[mPosition]);
                i.putExtra("bell_position", mPosition + "");
            }
            ((Activity) mContext).setResult(Activity.RESULT_OK,i);
            ((Activity) mContext).finish();
        }
    }
}
