package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.network.tools.TatansActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.coeus.network.view.ViewInject;

/**
 * Created by LCM on 2016/9/5. 10:00
 * 写备注页面
 */

public class RemarksActivity extends TatansActivity {
    //获取视图
    @ViewInject(id = R.id.edit_remarks)
    private EditText edit_remarks;
    @ViewInject(id = R.id.btn_voice, click = "onClick")
    private Button btn_voice;
    @ViewInject(id = R.id.btn_determine, click = "onClick")
    private Button btn_determine;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remarks);
        setTitle("备注");
        btn_voice.setContentDescription("语音输入。");
        btn_determine.setContentDescription("确定。");
        onInputMethod(true);
    }

    /**
     * 点击确定按钮
     */
    private void onClickDetermine() {
        intent = new Intent();
        if (!edit_remarks.getText().toString().equals("") && edit_remarks.getText().toString() != null) {
            intent.putExtra("label", edit_remarks.getText().toString());
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            TatansToast.showAndCancel("请输入要备注的内容");
        }
    }

    /**
     * 点击语音输入
     */
    private void onClickVoice() {
        onInputMethod(false);
        intent = new Intent();
        intent.setClass(this, VioceActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (data.getStringExtra("label").equals("")) {
                TatansToast.showAndCancel("语音识别失败");
            } else {
                edit_remarks.setText(data.getStringExtra("label"));
                TatansToast.showAndCancel(data.getStringExtra("label"));
                setTitle("");
            }
        }
    }


    /**
     * 软键盘的显示和隐藏
     *
     * @param isFlag
     */
    public void onInputMethod(boolean isFlag) {
        InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isFlag) {
            inputmanger.showSoftInput(edit_remarks,
                    InputMethodManager.RESULT_SHOWN);
            inputmanger.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                    InputMethodManager.HIDE_IMPLICIT_ONLY);
        } else {
            View viewa = getWindow().peekDecorView();
            inputmanger.hideSoftInputFromWindow(viewa.getWindowToken(), 0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        onInputMethod(false);//隐藏软键盘
    }
}
