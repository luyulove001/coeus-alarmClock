package net.tatans.rhea.demo.cache;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import net.tatans.rhea.demo.R;
import net.tatans.coeus.network.tools.TatansCache;

/**
 * 
 * @ClassName: SaveMediaActivity
 * @Description: 缓存bitmap
 * @Author Yuliang

 * @Date 2013-8-7 下午5:20:37
 * 
 */
public class SaveMediaActivity extends Activity implements Runnable {
    private String mUrl = "http://www.largesound.com/ashborytour/sound/brobob.mp3";
    private static String CACHE_KEY = "brobob";

    private TextView text;
    private TatansCache mCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_save_file);

		initView();

		mCache = TatansCache.get();
	}

	/**
	 * 初始化控�?
	 */
	private void initView() {
        text = (TextView) findViewById(R.id.text);
	}

	/**
	 * 点击save事件
	 * 
	 * @param v
	 */
	public void save(View v) {
        text.setText("Loading...");
        new Thread(this).start();
    }

	/**
	 * 点击read事件
	 * 
	 * @param v
	 */
	public void read(View v) {
        InputStream stream = null;
        try {
            stream = mCache.get1(CACHE_KEY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (stream == null) {
			Toast.makeText(this, "Bitmap cache is null ...", Toast.LENGTH_SHORT)
					.show();
            text.setText("file not found");
			return;
		}
        try {
            text.setText("file size: " + stream.available());
        } catch (IOException e) {
            text.setText("error " + e.getMessage());
        }
    }

	/**
	 * 点击clear事件
	 * 
	 * @param v
	 */
	public void clear(View v) {
		mCache.remove(CACHE_KEY);
	}

    @Override
    public void run() {
        OutputStream ostream = null;
        try {
            ostream = mCache.put(CACHE_KEY);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (ostream == null){
            Toast.makeText(this, "Open stream error!", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        try {
            URL u = new URL(mUrl);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.connect();
            InputStream stream = conn.getInputStream();

            byte[] buff = new byte[1024];
            int counter;

            while ((counter = stream.read(buff)) > 0){
                ostream.write(buff, 0, counter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // cache update
                ostream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text = (TextView) findViewById(R.id.text);
                    text.setText("done...");
                }
            });
        }
    }
}
