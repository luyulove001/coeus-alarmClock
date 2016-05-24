package net.tatans.rhea.demo;

import android.content.Intent;
import android.widget.TextView;

import net.tatans.coeus.network.tools.BaseActivity;
import net.tatans.rhea.demo.cache.CacheMainActivity;
import net.tatans.rhea.network.event.OnClick;
import net.tatans.rhea.network.view.ContentView;
import net.tatans.rhea.network.view.ViewIoc;

@ContentView(R.layout.main)
public class MainActivity extends BaseActivity {

    @OnClick(R.id.bt_global)
    public void btnGlobal(){
        TatansStartActivity(GlobalActivity.class);
    }

    @OnClick(R.id.bt_single)
    public void btnSingle(){
        TatansStartActivity(SingleActivity.class);
    }

    @OnClick({R.id.bt_cache,R.id.bt_tiris})
    public void btCache(){
        TatansStartActivity(CacheMainActivity.class);
    }

}
