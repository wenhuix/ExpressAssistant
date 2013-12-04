package com.ustc.prlib.util; 

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.LocalActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.Contacts.People;

/**
 * @description : 公共的常用方法
 * @package cn.apppark.mcd.util
 * @title:PublicUtil.java
 * @author : email:xiangyanhui@unitepower.net
 * @date :2012-6-20 上午10:05:13 
 * @version : v1.0
 */
public class PublicUtil {

	//根据字符串,生成md5
	public static String getMD5Str(String str) {       
		MessageDigest messageDigest = null;       
		try {       
			messageDigest = MessageDigest.getInstance("MD5");       
			messageDigest.reset();       
			messageDigest.update(str.getBytes("UTF-8"));       
		} catch (Exception e) {       
			e.printStackTrace();       
		}       
		byte[] byteArray = messageDigest.digest();       
		StringBuffer md5StrBuff = new StringBuffer();       
		for (int i = 0; i < byteArray.length; i++) {                   
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));       
			}  else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));       
			}
		}       
		//16位加密，从第9位到25位  
		return md5StrBuff.substring(8, 24).toString().toUpperCase();      
	}


	public static boolean checkEmail(String email) {
		Pattern   pattern   =   Pattern.compile( "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$"); 
		Matcher   matcher   =   pattern.matcher(email); 
		return   matcher.matches(); 
	}

	public static boolean chekPhone(String phone) {
		Pattern   pattern   =   Pattern.compile( "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)"); 
		Matcher   matcher   =   pattern.matcher(phone); 
		return   matcher.matches(); 
	}

	public static boolean chekMobilePhone(String phone) {
		if ( phone == null || phone.length()<11 ) {
			return false;
		}
		Pattern   pattern   =   Pattern.compile( "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)"); 
		Matcher   matcher   =   pattern.matcher(phone); 
		return   matcher.matches(); 
	}

	//获取版本VersionCode
	public static String getVersionCode(Context context, String packageName) {
		PackageInfo pinfo = null;
		try {
			pinfo = context.getPackageManager()
					.getPackageInfo(packageName , PackageManager.GET_CONFIGURATIONS);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		if ( pinfo == null) {
			return "";
		}
		return pinfo.versionCode+"";
	}

	/**
	 * 读取asset文件
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static String getStringFromAssets(Context context, String fileName){ 
		try { 
			InputStream inputReader =  context.getResources().getAssets().open(fileName) ; 
			ByteArrayOutputStream bout=new ByteArrayOutputStream();
			int len=-1;
			byte[] buff=new byte[1024];
			while(-1!=(len=inputReader.read(buff))){
				bout.write(buff,0,len);
			}
			String Result="";
			Result=bout.toString();
			return Result;
		} catch (Exception e) { 
			e.printStackTrace(); 
			return null;
		}
	} 

	/**
	 * 解决activityGroup中,activity被destroy后重新进入时,空指针问题
	 * @param id	activity的id
	 * @param activityManager
	 * @return
	 */
	public static  boolean destroyAct(String id, LocalActivityManager activityManager) {
		if( activityManager != null){
			activityManager.destroyActivity(id, false);
			try {
				final Field mActivitiesField = LocalActivityManager.class.getDeclaredField("mActivities");
				if(mActivitiesField != null){
					mActivitiesField.setAccessible(true);
					@SuppressWarnings("unchecked")
					final Map<String, Object> mActivities = (Map<String, Object>)mActivitiesField.get(activityManager);
					if(mActivities != null){
						mActivities.remove(id);
					}
					final Field mActivityArrayField = LocalActivityManager.class.getDeclaredField("mActivityArray");
					if(mActivityArrayField != null){
						mActivityArrayField.setAccessible(true);
						@SuppressWarnings("unchecked")
						final ArrayList<Object> mActivityArray = (ArrayList<Object>)mActivityArrayField.get(activityManager);
						if(mActivityArray != null){
							for(Object record : mActivityArray){
								final Field idField = record.getClass().getDeclaredField("id");
								if(idField != null){
									idField.setAccessible(true);
									final String _id = (String)idField.get(record);
									if(id.equals(_id)){
										mActivityArray.remove(record);
										break;
									}
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}


	private static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			// 递归删除目录中的子目录下
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	//时间格式化, 返回指定时间格式的字符串yyyy-MM-dd HH:mm:ss
	public static String timeFomat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern( format );
		return sdf.format(new Date());
	}

	//获取系统是否具有该权限的申明
	public static boolean getPermisson(Context context, String permissonName) {  
		try {  
			PackageManager pm = context.getPackageManager();  
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
			String pkgName = pi.packageName;  //得到自己的包名   

			PackageInfo pkgInfo = pm.getPackageInfo(pkgName,  PackageManager.GET_PERMISSIONS);//通过包名，返回包信息   
			String sharedPkgList[] = pkgInfo.requestedPermissions;//得到权限列表   

			for (int i = 0; i < sharedPkgList.length; i++) {  
				String permName = sharedPkgList[i];  
				if( permName.equals( permissonName )){
					return true;
				}
			}  
		} catch (NameNotFoundException e) {  
			e.printStackTrace();
		}  
		return false;
	} 


	public static String getShieldString(String str) {
		if ( str != null && PublicUtil.chekMobilePhone( str )) {
			String str1 = str.substring(0,3);
			String str2 = str.substring(8, str.length() );
			return str1+"*****"+str2;
		} else {
			return str;
		}
	}

	//发送短信
	public static  void editSendSms(Context context, String phone, String content) {
		String strDestAddress = phone;
		String strMessage = content;
		String uriString = "smsto:" + strDestAddress;
		Uri uri = Uri.parse(uriString);
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", strMessage);
		context.startActivity(intent);
	}

	//拨打电话
	public static  void callPhone(Context context, String phone) {
		Intent mIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone ));
		context.startActivity(mIntent);
	}

	
	public static  Uri insertContact(Context context, String name, String phone) {
	       ContentValues values = new ContentValues();
	       values.put(People.NAME, name);
	       Uri uri = context.getContentResolver().insert(People.CONTENT_URI, values);
	       Uri numberUri = Uri.withAppendedPath(uri, People.Phones.CONTENT_DIRECTORY);
	       values.clear();
	       values.put(Contacts.Phones.TYPE, People.Phones.TYPE_MOBILE);
	       values.put(People.NUMBER, phone);
	       context.getContentResolver().insert(numberUri, values);
	       return uri;
	}

	public static String getPhone(String str)  {
		Pattern   pattern   =   Pattern.compile( "((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)"); 
		Matcher   matcher   =   pattern.matcher(str); 
		if ( matcher.find() ) {
			return matcher.group();
		}
		return null;
	}



}
