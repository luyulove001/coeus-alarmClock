package net.tatans.coeus.alarm.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.bean.Alarm;
import net.tatans.coeus.alarm.receiver.AlarmReceiver;
import net.tatans.coeus.network.tools.TatansPreferences;

import java.util.Calendar;

/**
 * Alarm Clock alarm alert: pops visible indicator and plays alarm
 * tone. This activity is the full screen version which shows over the lock
 * screen with the wallpaper as the background.
 */
public class AlarmAlertFullScreen extends Activity {

    // These defaults must match the values in res/xml/settings.xml
    private static final String DEFAULT_SNOOZE = "10";
    private static final String DEFAULT_VOLUME_BEHAVIOR = "2";
    protected static final String SCREEN_OFF = "screen_off";

    protected Alarm mAlarm;
    private int mVolumeBehavior = 0;/**音量键设置*/
    private int snoozeMinutes;/**间隔时间*/
    private TextView tv_snooze_time;
    private LockLayer lockLayer;
    private View alert;

    // Receives the ALARM_KILLED action from the AlarmKlaxon,
    // and also ALARM_SNOOZE_ACTION / ALARM_DISMISS_ACTION from other applications
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Alarms.ALARM_SNOOZE_ACTION)) {
                snooze();
            } else if (action.equals(Alarms.ALARM_DISMISS_ACTION)) {
                dismiss(false);
            } else {
                Alarm alarm = intent.getParcelableExtra(Alarms.ALARM_INTENT_EXTRA);
                if (alarm != null && mAlarm.id == alarm.id) {
                    dismiss(true);
                }
            }
        }
    };

    /**
     * 用于实现电源键广播接收
     */
    private BroadcastReceiver mKeyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("ACTION_KEY_CODE")) {
                String keyStr = (String) TatansPreferences.get(Const.KEY_CODE,"0");
                if (keyStr.equals("1")){
                    snooze();
                }else if (keyStr.equals("2")){
                    dismiss(false);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        mAlarm = getIntent().getParcelableExtra(Alarms.ALARM_INTENT_EXTRA);
        //sign changed by reason
        mAlarm = Alarms.getAlarm(getContentResolver(), mAlarm.id);

        // Get the volume/camera button behavior setting
        /**音量键设置*/
        String keyCode = (String)TatansPreferences.get(Const.KEY_CODE,"0");
        mVolumeBehavior = Integer.parseInt(keyCode);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        // Turn on the screen unless we are being launched from the AlarmAlert
        // subclass.
        if (!getIntent().getBooleanExtra(SCREEN_OFF, false)) {
            win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        }
        updateLayout();
        initRegBroadcastReceiver();
    }

    /**广播注册*/
    private void initRegBroadcastReceiver() {
        IntentFilter filter = new IntentFilter(Alarms.ALARM_KILLED);
        filter.addAction(Alarms.ALARM_SNOOZE_ACTION);
        filter.addAction(Alarms.ALARM_DISMISS_ACTION);
        registerReceiver(mReceiver, filter);
        /**电源键广播注册*/
        IntentFilter filterKey = new IntentFilter();
        filterKey.addAction("ACTION_KEY_CODE");
        registerReceiver(mKeyReceiver, filterKey);
    }

    private void updateLayout() {
        LayoutInflater inflater = LayoutInflater.from(this);
        lockLayer = LockLayer.getInstance(this);
        alert = inflater.inflate(R.layout.alarm_alert, null);
        lockLayer.setLockView(alert);
        lockLayer.lock();
//        setContentView(inflater.inflate(R.layout.alarm_alert, null));

        /* snooze behavior: pop a snooze confirmation view, kick alarm
           manager. */
        LinearLayout snooze = (LinearLayout) alert.findViewById(R.id.snooze);
        TextView tv_label = (TextView) alert.findViewById(R.id.remarks);
        if(!mAlarm.label.equals("无")){
            tv_label.setText(mAlarm.label);
        }
        snooze.requestFocus();
        snooze.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                snooze();
            }
        });

        /* dismiss button: close notification */
        alert.findViewById(R.id.dismiss).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                dismiss(false);
            }
        });

        /**获取间隔时间*/
        String time = (String)TatansPreferences.get(Const.KEY_ALARM_BELL_INTERVAL_TIME,Const.KEY_TEN_MINUTE);
        snoozeMinutes = Integer.parseInt(time);
        /**动态设置button的间隔时间*/
        tv_snooze_time = (TextView) alert.findViewById(R.id.tv_snooze_time);
        tv_snooze_time.setText(getString(R.string.alarm_alert_snooze_text, time+"分钟"));
    }

    // Attempt to snooze this alert.
    /**再响间隔设置*/
    private void snooze() {
        // Do not snooze if the snooze button is disabled.
        if (!alert.findViewById(R.id.snooze).isEnabled()) {
            dismiss(false);
            return;
        }

        final long snoozeTime = System.currentTimeMillis() + (1000 * 60 * snoozeMinutes);
        Alarms.saveSnoozeAlert(AlarmAlertFullScreen.this, mAlarm.id, snoozeTime);

        // Get the display time for the snooze and update the notification.
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(snoozeTime);

        // Append (snoozed) to the label.
        String label = mAlarm.getLabelOrDefault(this);
//        label = getString(R.string.alarm_notify_snooze_label, label);
        label = getString(R.string.alarm_notify_snooze_label, "闹钟");

        // Notify the user that the alarm has been snoozed.
        Intent cancelSnooze = new Intent(this, AlarmReceiver.class);
        cancelSnooze.setAction(Alarms.CANCEL_SNOOZE);
        cancelSnooze.putExtra(Alarms.ALARM_ID, mAlarm.id);
        PendingIntent broadcast = PendingIntent.getBroadcast(this, mAlarm.id, cancelSnooze, 0);
        NotificationManager nm = getNotificationManager();

        Notification n = new Notification.Builder(AlarmAlertFullScreen.this).
                setAutoCancel(true)
                .setContentTitle(label)
                .setContentText(this.getString(R.string.alarm_notify_snooze_text, Alarms.formatTime(this, c)))
                .setContentIntent(broadcast)
                .setSmallIcon(R.mipmap.icon_set)
                .setWhen(0).build();
//        Notification n = new Notification(R.drawable.stat_notify_alarm,
//                label, 0);
//        n.setLatestEventInfo(this, label,
//                getString(R.string.alarm_notify_snooze_text,
//                    Alarms.formatTime(this, c)), broadcast);
        n.flags |= Notification.FLAG_AUTO_CANCEL
                | Notification.FLAG_ONGOING_EVENT;
        nm.notify(mAlarm.id, n);

        String displayTime = getString(R.string.alarm_alert_snooze_set, snoozeMinutes);
        // Intentionally log the snooze time for debugging.
        Log.v("antony", " AlarmAlertFullScreen" + displayTime);

        // Display the snooze minutes in a toast.
        Toast.makeText(AlarmAlertFullScreen.this, displayTime, Toast.LENGTH_LONG).show();
        stopService(new Intent(Alarms.ALARM_ALERT_ACTION));
        lockLayer.unlock();
        finish();
    }

    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    // Dismiss the alarm.
    private void dismiss(boolean killed) {
        // The service told us that the alarm has been killed, do not modify
        // the notification or stop the service.
        if (!killed) {
            // Cancel the notification and stop playing the alarm
            NotificationManager nm = getNotificationManager();
            nm.cancel(mAlarm.id);
            stopService(new Intent(Alarms.ALARM_ALERT_ACTION));
        }
        lockLayer.unlock();
        finish();
    }

    /**
     * this is called when a second alarm is triggered while a
     * previous alert window is still active.
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.v("antony", "AlarmAlert.OnNewIntent()");
        mAlarm = intent.getParcelableExtra(Alarms.ALARM_INTENT_EXTRA);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If the alarm was deleted at some point, disable snooze.
        if (Alarms.getAlarm(getContentResolver(), mAlarm.id) == null) {
            LinearLayout snooze = (LinearLayout) alert.findViewById(R.id.snooze);
            snooze.setEnabled(false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.v("antony", "AlarmAlert.onDestroy()");
        // No longer care about the alarm being killed.
        unregisterReceiver(mReceiver);
        unregisterReceiver(mKeyReceiver);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        // Do this on key down to handle a few of the system keys.
        boolean up = event.getAction() == KeyEvent.ACTION_UP;
        switch (event.getKeyCode()) {
            // Volume keys and camera keys dismiss the alarm
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_CAMERA:
            case KeyEvent.KEYCODE_FOCUS:
                if (up) {
                    switch (mVolumeBehavior) {
                        case 1:
                            snooze();
                            break;

                        case 2:
                            dismiss(false);
                            break;

                        default:
                            break;
                    }
                }
                return true;
            default:
                break;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        // Don't allow back to dismiss. This method is overriden by AlarmAlert
        // so that the dialog is dismissed.
        return;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
