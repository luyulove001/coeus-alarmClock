package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.alarm.utils.Const;
import net.tatans.coeus.network.tools.TatansActivity;
import net.tatans.coeus.network.tools.TatansPreferences;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.coeus.network.view.ViewInject;

/**
 * Created by SiLiPing on 2016/5/30.
 * 电源键操作设置
 */
public class KeySettingActivity extends TatansActivity implements View.OnClickListener {

    @ViewInject(id = R.id.img_check_null)
    private ImageView img_check_null;
    /**
     * 无
     */
    @ViewInject(id = R.id.llt_null)
    private LinearLayout llt_null;
    /**
     * 无
     */
    @ViewInject(id = R.id.img_check_wait)
    private ImageView img_check_wait;
    /**
     * 稍后
     */
    @ViewInject(id = R.id.llt_wait)
    private LinearLayout llt_wait;
    /**
     * 稍后
     */
    @ViewInject(id = R.id.img_check_close)
    private ImageView img_check_close;
    /**
     * 关闭
     */
    @ViewInject(id = R.id.llt_close)
    private LinearLayout llt_close;

    /**
     * 关闭
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_setting);
        initData();
    }

    private void initData() {
        String keyCode = (String) TatansPreferences.get(Const.KEY_CODE, "0");
        refresh(keyCode, false);
        setTitle(getString(R.string.alarm_key_setting));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llt_null:
                checkNull();
                break;
            case R.id.llt_wait:
                checkWait();
                break;
            case R.id.llt_close:
                checkClose();
                break;
        }
    }

    /**
     * 无
     */
    private void checkNull() {
        TatansPreferences.put(Const.KEY_CODE, "0");
        refresh("0", true);
    }

    /**
     * 稍后
     */
    private void checkWait() {
        TatansPreferences.put(Const.KEY_CODE, "1");
        refresh("1", true);
    }

    /**
     * 关闭
     */
    private void checkClose() {
        TatansPreferences.put(Const.KEY_CODE, "2");
        refresh("2", true);
    }

    /**
     * 更新UI及数据
     */
    public void refresh(String keyCode, boolean isToast) {
        if (keyCode.equals("0")) {
            img_check_null.setBackgroundResource(R.mipmap.icon_multiple_choice);
            img_check_wait.setBackgroundResource(R.color.black);
            img_check_close.setBackgroundResource(R.color.black);
            llt_null.setContentDescription("无。已选中");
            llt_wait.setContentDescription("稍后再响" + Const.NO_STR);
            llt_close.setContentDescription("关闭" + Const.NO_STR);
            if (isToast) TatansToast.showAndCancel("无已选中");
        } else if (keyCode.equals("1")) {
            img_check_null.setBackgroundResource(R.color.black);
            img_check_wait.setBackgroundResource(R.mipmap.icon_multiple_choice);
            img_check_close.setBackgroundResource(R.color.black);
            llt_null.setContentDescription("无" + Const.NO_STR);
            llt_wait.setContentDescription("稍后再响。已选中");
            llt_close.setContentDescription("关闭" + Const.NO_STR);
            if (isToast) TatansToast.showAndCancel("稍后再响已选中");
        } else if (keyCode.equals("2")) {
            img_check_null.setBackgroundResource(R.color.black);
            img_check_wait.setBackgroundResource(R.color.black);
            img_check_close.setBackgroundResource(R.mipmap.icon_multiple_choice);
            llt_null.setContentDescription("无" + Const.NO_STR);
            llt_wait.setContentDescription("稍后再响" + Const.NO_STR);
            llt_close.setContentDescription("关闭。已选中");
            if (isToast) TatansToast.showAndCancel("关闭已选中");
        }
        if (isToast) {
            this.setResult(Activity.RESULT_OK);
            this.finish();
        }
    }
}
