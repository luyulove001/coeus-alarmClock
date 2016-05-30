package net.tatans.coeus.alarm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.TatansPreferences;

/**
 * Created by SiLiPing on 2016/5/30.
 * 响铃时间、稍后再响间隔，公用数据适配
 */
public class PublicListAdapter extends BaseAdapter{

    private String[] listData = null;
    private Context mCtx;
    private String sing;

    final static class ViewHolder {
        private LinearLayout bell_time;
        private ImageView img_check;
        private TextView tv_time;
    }

    public PublicListAdapter(Context ctx,String[] list,String type) {
        this.listData = list;
        this.mCtx = ctx;
        this.sing = type;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mCtx).inflate(R.layout.activity_public_list_item, null);
            viewHolder.bell_time = (LinearLayout) convertView.findViewById(R.id.bell_time);
            viewHolder.img_check = (ImageView) convertView.findViewById(R.id.img_check);
            viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (sing.equals(Const.KEY_ALARM_BELL_TIME) && position==0){
            viewHolder.tv_time.setText(Const.KEY_NEVER_STOP);
        }else{
            viewHolder.tv_time.setText(listData[position]+Const.KEY_MINUTE);
        }
        refreshCheck(position,viewHolder);
        viewHolder.bell_time.setOnClickListener(new ItemOnClickListener(position,viewHolder));
        return convertView;
    }

    private class ItemOnClickListener implements View.OnClickListener {
        private int mPosition;
        private ViewHolder viewH;

        public ItemOnClickListener(int position,ViewHolder vHolder) {
            this.mPosition = position;
            this.viewH = vHolder;
        }

        @Override
        public void onClick(View v) {
            if (sing.equals(Const.KEY_ALARM_BELL_TIME)){
                TatansPreferences.put(Const.KEY_ALARM_BELL_TIME,listData[mPosition]);
            }else{
                TatansPreferences.put(Const.KEY_ALARM_BELL_INTERVAL_TIME,listData[mPosition]);
            }
            viewH.img_check.setBackgroundResource(R.mipmap.icon_multiple_choice);
            ((Activity) mCtx).setResult(Activity.RESULT_OK);
            ((Activity) mCtx).finish();
        }
    }

    /**处理选中机制*/
    private void refreshCheck(int posi,ViewHolder vH){
        if (sing.equals(Const.KEY_ALARM_BELL_TIME)){
            String checkStr = (String)TatansPreferences.get(Const.KEY_ALARM_BELL_TIME,"10");
            if (checkStr.equals(listData[posi]) && checkStr.equals("0")){
                vH.bell_time.setContentDescription(Const.KEY_NEVER_STOP+",已选中");
                vH.img_check.setBackgroundResource(R.mipmap.icon_multiple_choice);
            }else if (checkStr.equals(listData[posi]) && !checkStr.equals("0")){
                vH.bell_time.setContentDescription(listData[posi]+"分钟,已选中");
                vH.img_check.setBackgroundResource(R.mipmap.icon_multiple_choice);
            }else{
                if (listData[posi]=="0"){
                    vH.bell_time.setContentDescription(Const.KEY_NEVER_STOP);
                }else{
                    vH.bell_time.setContentDescription(listData[posi]+Const.KEY_MINUTE);
                }
                vH.img_check.setBackgroundResource(R.color.black);
            }
        }else{
            String checkStr = (String)TatansPreferences.get(Const.KEY_ALARM_BELL_INTERVAL_TIME,"10");
            if (checkStr.equals(listData[posi])){
                vH.bell_time.setContentDescription(listData[posi]+"分钟,已选中");
                vH.img_check.setBackgroundResource(R.mipmap.icon_multiple_choice);
            }else{
                vH.bell_time.setContentDescription(listData[posi]+Const.KEY_MINUTE);
                vH.img_check.setBackgroundResource(R.color.black);
            }
        }
    }


}
