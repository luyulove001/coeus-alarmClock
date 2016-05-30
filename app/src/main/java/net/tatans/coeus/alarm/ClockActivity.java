package net.tatans.coeus.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import java.io.IOException;

/**
 * Created by SiLiPing on 2016/5/26.
 */
public class ClockActivity extends Activity {

    private MediaPlayer mediaPlayer;

    /**
     * 配合初始化的静音响铃
     * @param player
     * @throws java.io.IOException
     * @throws IllegalArgumentException
     * @throws IllegalStateException
     */
    private void startAlarm(MediaPlayer player)
            throws java.io.IOException, IllegalArgumentException,
            IllegalStateException {
        final AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        // do not play alarms if stream volume is 0
        // (typically because ringer mode is silent).
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(true);
            player.prepare();
            player.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(this, getSystemDefultRingtoneUri());
            startAlarm(mediaPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //创建一个闹钟提醒的对话框,点击确定关闭铃声与页面
        new AlertDialog.Builder(ClockActivity.this)
                .setTitle("闹钟")
                .setMessage("嗨,大傻逼起床啦")
                .setPositiveButton("关闭闹铃", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                ClockActivity.this.finish();
            }
        }).show();
    }

    //获取系统默认铃声的Uri
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);
    }
}
