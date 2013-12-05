package com.ustc.prlib.vo;

public class BaseParam {
	public static final int OPERATE_SENDSMS = 1;   //短信发送
	public static final int OPERATE_CALLPHONE = 2;   //电话拨打
	public static final int OPERATE_ADDCONNECT = 3;   //联系人添加
	
	public static final String SMS_FILENAME = "smstemplate.json"; 	//短信模板文件
	public static final String COMPRESS_FILENAME = "expressCompany.json";  //快递公司文件
	
	public static final int OPERATION_ADD_SMS_TEMPLATE = 4;   //添加短信模板
	public static final int OPERATION_ADD_COMPANY_TEMPLATE = 5;  //添加公司模板
	public static final int OPERATION_EDIT_SMS_TEMPLATE	= 6;  //编辑短信模板
	public static final int OPERATION_EDIT_COMPANY_TEMPLATE = 7;  //编辑公司模板
	public static final int OPERATION_DELETE_SMS_TEMPLATE	= 8;  //删除短信模板
	public static final int OPERATION_DELETE_COMPANY_TEMPLATE = 9;  //删除公司模板
	
	public static final int ITEM_POSITON_LEFT = 7;
	public static final int ITEM_POSITON_RIGHT = 8;
	 
	public static final String OPERATION_TYPE = "operationType";
	public static final String CLICK_ITEM_POSION = "clickItemPosion";
	public static final String CLICK_ITEM_CONTENT = "clickItemContent";
}