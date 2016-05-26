package net.tatans.coeus.alarm;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;

/**
 * Created by Lion on 2016/5/26.
 */
public class ClockActivity extends Activity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);

        mediaPlayer = MediaPlayer.create(this, getSystemDefultRingtoneUri());
        //mediaPlayer.setLooping(true);
        mediaPlayer.start();

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
        return RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE);
    }
}
