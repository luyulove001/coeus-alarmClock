package net.tatans.coeus.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import net.tatans.coeus.network.tools.TatansToast;

/**
 * Created by Administrator on 2016/5/25.
 */
public class RepeatingAlarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        TatansToast.showAndCancel("重复闹钟30秒");
        Log.e("aaa", "重复闹钟30秒");
    }
}
