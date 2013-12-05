package com.ustc.prlib.xunfei;

import org.w3c.dom.Text;

import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.vo.BaseParam;
import com.xiang.xunfei.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * @description : 设置
 * @package com.xiang.xunfei
 * @title:SetActivity.java
 * @author :  
 * @date :2013-12-3 下午2:29:26 
 * @version : v4.0
 */
public class SettingActivity extends Activity implements OnClickListener {
	private LinearLayout ll_defaultOperate, ll_defaultExpress, ll_smsTemplet, ll_aboutUs;
	private TextView tv_defaultOperation, tv_defaultExpress, tv_smsTemplet;
	private SharePreferenceInfo info;
	private Button btn_back;
	private Context context = this;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		info = new SharePreferenceInfo(context);
		ll_defaultOperate = (LinearLayout)findViewById( R.id.set_ll_defaultoperation );
		ll_defaultExpress = (LinearLayout)findViewById( R.id.set_ll_defaultexpress );
		ll_smsTemplet = (LinearLayout)findViewById( R.id.set_ll_smstemplet );
		
		tv_defaultOperation = (TextView)findViewById( R.id.set_tv_defaultoperate );
		tv_defaultExpress = (TextView)findViewById( R.id.set_tv_defaultexpress );
		tv_smsTemplet = (TextView)findViewById( R.id.set_tv_smstemplet );
		
		ll_aboutUs = (LinearLayout)findViewById( R.id.set_ll_aboutus );
		btn_back = (Button)findViewById( R.id.set_btn_back);
		ButtonColorFilter.setButtonFocusChanged( btn_back);
		ll_defaultOperate.setOnClickListener(this);
		ll_defaultExpress.setOnClickListener(this);
		ll_smsTemplet.setOnClickListener(this);
		ll_aboutUs.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch ( v.getId() ) {
		case  R.id.set_ll_defaultoperation :
			//Intent intent = new Intent(SettingActivity.this, OperationChoiceActivity.class);
			//intent.putExtra( BaseParam.ADDTYPE, BaseParam.ADDTYPE_DEFAULT_OPERATE );
			//startActivityForResult(intent, 1);
			startActivity(new Intent(SettingActivity.this, ActionChoiceActivity.class));
			break;
		case  R.id.set_ll_defaultexpress :
			startActivity(new Intent(SettingActivity.this, CompanyListActivity.class));
			break;
		case  R.id.set_ll_smstemplet :
			startActivity(new Intent(SettingActivity.this, SmsTempletActivity.class));
			break;
		case  R.id.set_ll_aboutus :
			startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
			break;
		case  R.id.set_btn_back :
			finish();
			break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		switch( info.getDefaultOperate() ) {
		case BaseParam.OPERATE_SENDSMS :  
			tv_defaultOperation.setText("发送短信");
			break;
		case BaseParam.OPERATE_CALLPHONE :  
			tv_defaultOperation.setText("拨打电话");
			break;
		case BaseParam.OPERATE_ADDCONNECT :  
			tv_defaultOperation.setText("加入通讯录");
			break;
		}
		
		tv_defaultExpress.setText( info.getDefaultCompressContent() );
		tv_smsTemplet.setText( info.getDefaultSmsContent() );
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == 1 ) {
			
		}
	}
}
