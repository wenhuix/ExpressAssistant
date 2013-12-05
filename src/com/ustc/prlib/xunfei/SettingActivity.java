package com.ustc.prlib.xunfei;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.w3c.dom.Text;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ustc.prlib.util.ButtonColorFilter;
import com.ustc.prlib.util.JsonParserUtil;
import com.ustc.prlib.util.PrivateFileReadSave;
import com.ustc.prlib.util.SharePreferenceInfo;
import com.ustc.prlib.vo.BaseParam;
import com.ustc.prlib.vo.ExpressVo;
import com.ustc.prlib.vo.SmsVo;
import com.xiang.xunfei.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * @description : 设置
 * @package com.xiang.xunfei
 * @title:SetActivity.java
 * @author :  
 * @date :2013-12-3 下午2:29:26 
 * @version : v4.0
 */
public class SettingActivity extends Activity implements OnClickListener {
	private LinearLayout ll_defaultOperate, ll_defaultExpress, ll_smsTemplet;
	private LinearLayout ll_restore_setting, ll_aboutUs;
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
		ll_aboutUs = (LinearLayout)findViewById( R.id.set_ll_aboutus );
		ll_restore_setting = (LinearLayout)findViewById( R.id.set_ll_resotre_setting );
		
		tv_defaultOperation = (TextView)findViewById( R.id.set_tv_defaultoperate );
		tv_defaultExpress = (TextView)findViewById( R.id.set_tv_defaultexpress );
		tv_smsTemplet = (TextView)findViewById( R.id.set_tv_smstemplet );
		
		
		btn_back = (Button)findViewById( R.id.set_btn_back);
		ButtonColorFilter.setButtonFocusChanged( btn_back);
		ll_defaultOperate.setOnClickListener(this);
		ll_defaultExpress.setOnClickListener(this);
		ll_smsTemplet.setOnClickListener(this);
		ll_aboutUs.setOnClickListener(this);
		ll_restore_setting.setOnClickListener(this);
		btn_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.set_ll_defaultoperation:
			startActivity(new Intent(SettingActivity.this,
					ActionChoiceActivity.class));
			break;
		case R.id.set_ll_defaultexpress:
			startActivity(new Intent(SettingActivity.this,
					CompanyListActivity.class));
			break;
		case R.id.set_ll_smstemplet:
			startActivity(new Intent(SettingActivity.this,
					SmsTempletActivity.class));
			break;
		case R.id.set_ll_aboutus:
			startActivity(new Intent(SettingActivity.this,
					AboutUsActivity.class));
			break;
		case R.id.set_ll_resotre_setting:
			Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("确定要恢复默认设置吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									restoreSettings();
									finish();
									startActivity(getIntent());
								}
							}).setNeutralButton("取消", null);
			builder.show();
			break;
		case R.id.set_btn_back:
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
		
		tv_defaultExpress.setText( info.getDefaultCompany() );
		tv_smsTemplet.setText( info.getDefaultSmsTemplate() );
	}
	
	private void restoreSettings(){
		
		restoreSMSTemplate();
		restoreCompany();
		restoreAction();
		
		Toast.makeText(context, "恢复默认操作成功！", 0).show();
	}
	
	private void restoreSMSTemplate(){
		
		//SMS
		ArrayList<SmsVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.SMS_FILENAME, context);
		if ( temp != null ) {
			Type type = new TypeToken<ArrayList<SmsVo>>(){}.getType();
			listVo = (ArrayList<SmsVo>) JsonParserUtil.parseJson2ListNoItem(temp, type);
		}
		if (listVo != null) {
			listVo.clear();
		} else {
			listVo = new ArrayList<SmsVo>();
		}
		String[] smsTemplates = getResources().getStringArray(R.array.sms_template);
		for (int i = 0; i<smsTemplates.length; i++) {
			SmsVo smsVo = new SmsVo();
			smsVo.setContent(smsTemplates[i]);
			smsVo.setId(i);
		    listVo.add(smsVo);
		}
		Gson gson = new Gson();
		String result = gson.toJson( listVo );
		PrivateFileReadSave.save(BaseParam.SMS_FILENAME, result, context);
		info.updateDefaultSmsTemplate(listVo.get(0).getContent());
		info.updateDefaultSmsTemplateId(listVo.get(0).getId());
	}
	
	private void restoreCompany(){
		
		//Express Company
		ArrayList<ExpressVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.EXPRESS_FILENAME, context);
		if ( temp != null ) {
			Type type = new TypeToken<ArrayList<ExpressVo>>(){}.getType();
			listVo = (ArrayList<ExpressVo>) JsonParserUtil.parseJson2ListNoItem(temp, type);
		}
		if ( listVo != null ) {
			listVo.clear();
		}else {
			listVo = new ArrayList<ExpressVo>();
		}
		String[] expressCompanys = getResources().getStringArray(R.array.express_company);
		for (int i = 0; i<expressCompanys.length; i++) {
			ExpressVo expressVo = new ExpressVo();
			expressVo.setContent(expressCompanys[i]);
			expressVo.setId(i);
			listVo.add(expressVo);
		}
		Gson gson = new Gson();
		String result = gson.toJson( listVo );
		PrivateFileReadSave.save(BaseParam.EXPRESS_FILENAME, result, context);
		info.updateDefaultCompany(listVo.get(0).getContent());
		info.updateDefaultCompanyId(listVo.get(0).getId());
	}
	
	private void restoreAction(){
		info.updateDefaultOperate(BaseParam.OPERATE_SENDSMS);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if ( resultCode == 1 ) {
			
		}
	}
}
