package com.ustc.prlib.xunfei;

import com.ustc.prlib.util.ButtonColorFilter;
import com.xiang.xunfei.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

/**
* @description : 关于我们
* @package com.xiang.xunfei
* @title:AboutUsActivity.java
* @author : email:xiangyanhui@unitepower.net
* @date :2013-12-3 下午2:33:44 
* @version : v4.0
 */
public class AboutUsActivity extends Activity  {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutus);
		
		Button btn = (Button)findViewById( R.id.aboutus_btn_back);
		ButtonColorFilter.setButtonFocusChanged( btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
}
