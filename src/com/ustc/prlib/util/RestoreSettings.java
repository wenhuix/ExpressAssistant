package com.ustc.prlib.util;

import java.lang.reflect.Type;
import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ustc.prlib.vo.BaseParam;
import com.ustc.prlib.vo.ExpressVo;
import com.ustc.prlib.vo.SmsVo;
import com.xiang.xunfei.R;

public class RestoreSettings {

	private SharePreferenceInfo info;
	private Context context;

	public RestoreSettings(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		info = new SharePreferenceInfo(context);
	}

	public void restoreSettings() {

		restoreSMSTemplate();
		restoreCompany();
		restoreAction();
	}

	private void restoreSMSTemplate() {

		// SMS
		ArrayList<SmsVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.SMS_FILENAME, context);
		if (temp != null) {
			Type type = new TypeToken<ArrayList<SmsVo>>() {
			}.getType();
			listVo = (ArrayList<SmsVo>) JsonParserUtil.parseJson2ListNoItem(
					temp, type);
		}
		if (listVo != null) {
			listVo.clear();
		} else {
			listVo = new ArrayList<SmsVo>();
		}
		String[] smsTemplates = context.getResources().getStringArray(
				R.array.sms_template);
		for (int i = 0; i < smsTemplates.length; i++) {
			SmsVo smsVo = new SmsVo();
			smsVo.setContent(smsTemplates[i]);
			smsVo.setId(i);
			listVo.add(smsVo);
		}
		Gson gson = new Gson();
		String result = gson.toJson(listVo);
		PrivateFileReadSave.save(BaseParam.SMS_FILENAME, result, context);
		info.updateDefaultSmsTemplate(listVo.get(0).getContent());
		info.updateDefaultSmsTemplateId(listVo.get(0).getId());
	}

	private void restoreCompany() {

		// Express Company
		ArrayList<ExpressVo> listVo = null;
		String temp = PrivateFileReadSave.read(BaseParam.EXPRESS_FILENAME,
				context);
		if (temp != null) {
			Type type = new TypeToken<ArrayList<ExpressVo>>() {
			}.getType();
			listVo = (ArrayList<ExpressVo>) JsonParserUtil
					.parseJson2ListNoItem(temp, type);
		}
		if (listVo != null) {
			listVo.clear();
		} else {
			listVo = new ArrayList<ExpressVo>();
		}
		String[] expressCompanys = context.getResources().getStringArray(
				R.array.express_company);
		for (int i = 0; i < expressCompanys.length; i++) {
			ExpressVo expressVo = new ExpressVo();
			expressVo.setContent(expressCompanys[i]);
			expressVo.setId(i);
			listVo.add(expressVo);
		}
		Gson gson = new Gson();
		String result = gson.toJson(listVo);
		PrivateFileReadSave.save(BaseParam.EXPRESS_FILENAME, result, context);
		info.updateDefaultCompany(listVo.get(0).getContent());
		info.updateDefaultCompanyId(listVo.get(0).getId());
	}

	private void restoreAction() {
		info.updateDefaultOperate(BaseParam.OPERATE_SENDSMS);
	}
}
