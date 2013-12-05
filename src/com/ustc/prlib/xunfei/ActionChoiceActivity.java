package com.ustc.prlib.xunfei;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.vo.BaseParam;
import com.xiang.xunfei.R;

/**
 * @description : 模板添加
 * @package com.xiang.xunfei
 * @title:AddTempletActivity.java
 * @author : email:xiangyanhui@unitepower.net
 * @date :2013-12-3 下午3:46:33 
 * @version : v4.0
 */
public class ActionChoiceActivity extends Activity  implements OnClickListener{
	private Context context = this;
	private Button btn_back; 
	private TextView tv_title;
	private LinearLayout ll_sendSms, ll_call, ll_addConnect;
	private Button btn_sendSms, btn_call, btn_addConnect; 
	private SharePreferenceInfo info;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.action_choice);
		info = new SharePreferenceInfo(context);
		initWidget();
	}

	private void initWidget() {
		btn_back = (Button)findViewById( R.id.addtemplet_btn_back );
		tv_title = (TextView)findViewById( R.id.addtemplet_tv_title );
		ButtonColorFilter.setButtonFocusChanged( btn_back);
		
		ll_sendSms = (LinearLayout)findViewById( R.id.addtemplet_ll_sendsms );
		ll_call = (LinearLayout)findViewById( R.id.addtemplet_ll_call );
		ll_addConnect = (LinearLayout)findViewById( R.id.addtemplet_ll_addconnect );

		btn_sendSms = (Button)findViewById( R.id.addtemplet_btn_sendsms );
		btn_call = (Button)findViewById( R.id.addtemplet_btn_call );
		btn_addConnect = (Button)findViewById( R.id.addtemplet_btn_addconnect );

		ll_sendSms.setOnClickListener(this);
		ll_call.setOnClickListener(this);
		ll_addConnect.setOnClickListener(this);
		btn_back.setOnClickListener(this);

		tv_title.setText("请选择默认操作");
		switch (info.getDefaultOperate()) {
		case BaseParam.OPERATE_SENDSMS:
			btn_sendSms.setBackgroundResource(R.drawable.btn_sel);
			break;
		case BaseParam.OPERATE_CALLPHONE:
			btn_call.setBackgroundResource(R.drawable.btn_sel);
			break;
		case BaseParam.OPERATE_ADDCONNECT:
			btn_addConnect.setBackgroundResource(R.drawable.btn_sel);
			break;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addtemplet_btn_back:
			finish();
			break;

		case R.id.addtemplet_ll_sendsms:
			info.updateDefaultOperate( BaseParam.OPERATE_SENDSMS );
			Toast.makeText(context, "默认操作修改成功", 0).show();
			setResult(1);
			finish();
			break;
		case R.id.addtemplet_ll_call:
			info.updateDefaultOperate( BaseParam.OPERATE_CALLPHONE );
			Toast.makeText(context, "默认操作修改成功", 0).show();
			setResult(1);
			finish();
			break;
		case R.id.addtemplet_ll_addconnect:
			info.updateDefaultOperate( BaseParam.OPERATE_ADDCONNECT );
			Toast.makeText(context, "默认操作修改成功", 0).show();
			setResult(1);
			finish();
			break;
		}
	}
}
