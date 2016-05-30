package net.tatans.coeus.alarm.activitities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import net.tatans.coeus.alarm.R;
import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

/**
 * Created by cly on 2016/5/30.
 */
@ContentView(R.layout.activity_public_list)
public class CustomWeekActivity extends BaseActivity {
    @ViewIoc(R.id.lyt_confirm)
    LinearLayout lyt_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lyt_confirm.setVisibility(View.VISIBLE);
    }
}
