package com.ustc.prlib.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharePreferenceInfo {
	private Context mContext;
	private String iniName = "init_info";

	public SharePreferenceInfo(Context context) {
		super();
		this.mContext = context;
	}

	// 默认操作
	public int getDefaultOperate() {
		SharedPreferences mPreferences = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE);
		return mPreferences.getInt("_DefaultOperate", -1);
	}

	public void updateDefaultOperate(int operate) {
		Editor editor = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE).edit();
		editor.putInt("_DefaultOperate", operate);
		editor.commit();
	}

	// 默认短信模板ID，无返回-1
	public int getDefaultSmsTemplateId() {
		SharedPreferences mPreferences = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE);
		return mPreferences.getInt("_DefaultSmsTemplateId", -1);
	}

	public void updateDefaultSmsTemplateId(int id) {
		Editor editor = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE).edit();
		editor.putInt("_DefaultSmsTemplateId", id);
		editor.commit();
	}

	// 默认短信模板内容
	public String getDefaultSmsTemplate() {
		SharedPreferences mPreferences = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE);
		return mPreferences.getString("_DefaultSmsTemplate", null);
	}

	public void updateDefaultSmsTemplate(String content) {
		Editor editor = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE).edit();
		editor.putString("_DefaultSmsTemplate", content);
		editor.commit();
	}

	// 默认快递模板ID，无返回-1
	public int getDefaultCompanyId() {
		SharedPreferences mPreferences = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE);
		return mPreferences.getInt("_DefaultCompanyId", -1);
	}

	public void updateDefaultCompanyId(int id) {
		Editor editor = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE).edit();
		editor.putInt("_DefaultCompanyId", id);
		editor.commit();
	}

	// 默认快递模板内容
	public String getDefaultCompany() {
		SharedPreferences mPreferences = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE);
		return mPreferences.getString("_DefaultCompany", null);
	}

	public void updateDefaultCompany(String content) {
		Editor editor = mContext.getSharedPreferences(iniName,
				Context.MODE_PRIVATE).edit();
		editor.putString("_DefaultCompany", content);
		editor.commit();
	}

}
