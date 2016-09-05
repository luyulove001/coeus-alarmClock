package net.tatans.coeus.alarm.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.network.tools.BaseActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.btn_determine)
    private void onClickDetermine() {
        Intent intent = new Intent();
        intent.putExtra("label", edit_remarks.getText().toString());
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
