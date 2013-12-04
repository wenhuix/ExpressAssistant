package com.ustc.prlib.xunfei;

import com.xiang.xunfei.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


/**
 * Æô¶¯Ò³
* @description : TODO
* @package com.xiang.xunfei
* @title:StartActivity.java
* @author :  
* @date :2013-12-3 ÉÏÎç11:05:52 
* @version : v4.0
 */
public class StartActivity extends Activity {
    private static int DELAY_MILLIS = 1000;	//ÑÓ³Ù

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goHome();
            }
        }, DELAY_MILLIS);
	}

	private void goHome() {
        Intent intent = new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
