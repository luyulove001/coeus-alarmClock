/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.tatans.coeus.alarm.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

import net.tatans.coeus.alarm.utils.AlarmAlertFullScreen;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.TatansPreferences;

/**
 * Full screen alarm alert: pops visible indicator and plays alarm tone. This
 * activity shows the alert as a dialog.
 * 闹钟dialog是extends AlarmAlertFullScreen
 */
public class AlarmAlert extends AlarmAlertFullScreen {

    private final BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleScreenOff();
        }
    };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // Listen for the screen turning off so that when the screen comes back
        // on, the user does not need to unlock the phone to dismiss the alarm.
        registerReceiver(mScreenOffReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mScreenOffReceiver);
    }

    private void handleScreenOff() {
        String keyStr = (String) TatansPreferences.get(Const.KEY_CODE,"0");
        Log.e("wangxianming", "熄屏下关闭闹钟"+"----keyStr:"+keyStr);
        if (!keyStr.equals("0")){
            Intent mIntent = new Intent("ACTION_KEY_CODE");
            mIntent.putExtra(SCREEN_OFF, true);
            sendBroadcast(mIntent);
        }
    }

}
