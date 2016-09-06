package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.coeus.network.tools.TatansToast;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by LCM on 2016/9/5. 10:00
 * 写备注页面
 */

@ContentView(R.layout.remarks)
public class RemarksActivity extends BaseActivity {
    //获取视图
    @ViewIoc(R.id.edit_remarks)
    private EditText edit_remarks;
    @ViewIoc(R.id.btn_voice)
    private Button btn_voice;
    @ViewIoc(R.id.btn_determine)
    private Button btn_determine;

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 点击确定按钮
     */
    @OnClick(R.id.btn_determine)
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
    @OnClick(R.id.btn_voice)
    private void onClickVoice() {
        intent = new Intent();
        intent.setClass(this, VioceActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 ) {
            if (data.getStringExtra("label").equals("")) {
                TatansToast.showAndCancel("语音识别失败");
            } else {
                edit_remarks.setText(data.getStringExtra("label"));
            }
        }
    }
}
