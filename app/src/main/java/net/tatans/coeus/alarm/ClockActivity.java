package net.tatans.coeus.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.TatansPreferences;

import java.io.IOException;

/**
 * Created by SiLiPing on 2016/5/26.
 */
public class ClockActivity extends Activity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mediaPlayer = new MediaPlayer();
        try {
            int id = (Integer) TatansPreferences.get(Const.BELL_URI, 0);
            setDataSourceFromResource(getResources(), mediaPlayer, Const.BELL_ID[id]);
            //mediaPlayer.setDataSource(this, getSystemDefultRingtoneUri());/**系统默认音效*/
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

    /**
     * 把R.raw.xxx路径setDataSource
     * @param resources
     * @param player
     * @param res
     * @throws java.io.IOException
     */
    private void setDataSourceFromResource(Resources resources, MediaPlayer player, int res) throws java.io.IOException {
        AssetFileDescriptor afd = resources.openRawResourceFd(res);
        if (afd != null) {
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());afd.close();
        }
    }

    /**系统默认闹钟音效uri*/
    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM);
    }
}
