package com.ustc.prlib.vo;

public class BaseParam {
	public static final int OPERATE_SENDSMS = 1;   //短信发送
	public static final int OPERATE_CALLPHONE = 2;   //电话拨打
	public static final int OPERATE_ADDCONNECT = 3;   //联系人添加
	
	public static final String SMS_FILENAME = "smstemplet.json"; 	//短信模板文件
	public static final String COMPRESS_FILENAME = "express.json";  //快递模板文件
	
	public static final int ADDTYPE_SMS_TEMPLET = 4;   //添加短信模板
	public static final int ADDTYPE_EXPRESS_TEMPLET = 5;  //添加快递模板
	public static final int ADDTYPE_DEFAULT_OPERATE = 6;  //设置默认操作
	
	public static final int ITEM_POSITON_LEFT = 7;
	public static final int ITEM_POSITON_RIGHT = 8;
	 
	public static final String ADDTYPE = "type";
}