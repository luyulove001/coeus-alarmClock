package net.tatans.coeus.alarm.activities;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansPreferences;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ResInject;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by SiLiPing on 2016/5/27.
 * 闹钟设置
 */
@ContentView(R.layout.activity_alarm_setting)
public class SettingActivity extends BaseActivity {

    @ViewIoc(R.id.img_open) private ImageView imgOpen;/**静音响铃开关*/
    @ViewIoc(R.id.alarm_in_silent_mode) private RelativeLayout alarm_in_silent_mode;/**静音响铃*/
    @ResInject(id=R.string.alarm_in_silent_mode_open) String MUTE_OPEN;/**静音响铃打开*/
    @ResInject(id=R.string.alarm_in_silent_mode_close) String MUTE_CLOSE;/**静音响铃关闭*/
    @ViewIoc(R.id.tv_alarm_snooze_duration) private TextView tv_alarm_snooze_duration;/**稍后响铃时间*/
    @ViewIoc(R.id.tv_alarm_time) private TextView tv_alarm_time;/**响铃时间*/

    private static final int ALARM_STREAM_TYPE_BIT = 1 << AudioManager.STREAM_ALARM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    private void initData() {
        boolean isFlage = (Boolean)TatansPreferences.get(Const.KEY_ALARM_IN_SILENT_MODE,true);
        refresh(isFlage,false);

        /**初始化信息*/
        String timeStop = (String)TatansPreferences.get(Const.KEY_ALARM_BELL_TIME,Const.KEY_TEN_MINUTE_STOP);
        if(timeStop.equals("0")){
            tv_alarm_time.setText(Const.KEY_NEVER_STOP);
        }else{
            tv_alarm_time.setText(timeStop+Const.KEY_MINUTE_STOP);
        }
        String time = (String)TatansPreferences.get(Const.KEY_ALARM_BELL_INTERVAL_TIME,Const.KEY_TEN_MINUTE);
        tv_alarm_snooze_duration.setText(time+Const.KEY_MINUTE);
    }

    /**静音响铃*/
    @OnClick(R.id.alarm_in_silent_mode)
    public void alarmInSilentMode(){
        boolean isFlage = (Boolean)TatansPreferences.get(Const.KEY_ALARM_IN_SILENT_MODE,true);
        refresh(!isFlage,true);
    }

    /**响铃时间*/
    @OnClick(R.id.alarm_in_silent_time)
    public void alarm_in_silent_time(){
        Intent intent=new Intent();
        intent.putExtra("sign", Const.KEY_ALARM_BELL_TIME);
        intent.setClass(this, PublicListActivity.class);
        startActivityForResult(intent,0);
    }

    /**稍后响铃*/
    @OnClick(R.id.alarm_snooze_duration)
    public void alarm_snooze_duration(){
        Intent intent=new Intent();
        intent.putExtra("sign", Const.KEY_ALARM_BELL_INTERVAL_TIME);
        intent.setClass(this, PublicListActivity.class);
        startActivityForResult(intent,1);
    }

    /**按键设置*/
    @OnClick(R.id.alarm_key_setting)
    public void alarm_key_setting(){
        Intent intent=new Intent();
        intent.setClass(this, KeySettingActivity.class);
        startActivityForResult(intent,1);
    }


    /**更新UI及数据*/
    public void refresh(boolean isFlage,boolean isToast){
        if(isFlage){
            TatansPreferences.put(Const.KEY_ALARM_IN_SILENT_MODE,true);
            initAlarmInSilentSetting(true);
            imgOpen.setBackgroundResource(R.mipmap.open_icon);
            alarm_in_silent_mode.setContentDescription(MUTE_OPEN);
            if (isToast) TatansToast.showAndCancel(MUTE_OPEN);
        }else{
            TatansPreferences.put(Const.KEY_ALARM_IN_SILENT_MODE,false);
            initAlarmInSilentSetting(false);
            imgOpen.setBackgroundResource(R.mipmap.close_icon);
            alarm_in_silent_mode.setContentDescription(MUTE_CLOSE);
            if (isToast) TatansToast.showAndCancel(MUTE_CLOSE);
        }
    }
    /**静音响铃的方法*/
    public void initAlarmInSilentSetting(boolean isChecked){
        int ringerModeStreamTypes = Settings.System.getInt(
                getContentResolver(),
                Settings.System.MODE_RINGER_STREAMS_AFFECTED, 0);
        if (isChecked) {
            // 静音模式下仍然闹铃，也就是Alarm不受RingerMode设置的影响
            ringerModeStreamTypes &= ~ALARM_STREAM_TYPE_BIT;
        } else {
            // 静音模式下不闹铃，也就是Alarm受RingerMode设置的影响
            ringerModeStreamTypes |= ALARM_STREAM_TYPE_BIT;
        }
        Settings.System.putInt(getContentResolver(), Settings.System.MODE_RINGER_STREAMS_AFFECTED, ringerModeStreamTypes);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String time = (String)TatansPreferences.get(Const.KEY_ALARM_BELL_TIME,Const.KEY_TEN_MINUTE_STOP);
            if(time.equals("0")){
                tv_alarm_time.setText(Const.KEY_NEVER_STOP);
            }else{
                tv_alarm_time.setText(time+Const.KEY_MINUTE_STOP);
            }
        }else if(requestCode == 1 && resultCode == RESULT_OK) {
            String time = (String)TatansPreferences.get(Const.KEY_ALARM_BELL_INTERVAL_TIME,Const.KEY_TEN_MINUTE);
            tv_alarm_snooze_duration.setText(time+Const.KEY_MINUTE);
        }
    }
}
