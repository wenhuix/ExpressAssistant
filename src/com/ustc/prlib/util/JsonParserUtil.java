package com.ustc.prlib.util; 

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ustc.prlib.vo.BaseVo;

/**
 * @description : 定义一些通用的解析json数据方法
 * @version : v1.0
 */
public class JsonParserUtil {

	/**
	 * 将单层的json数据转换为对象 { "":"" } 
	 * 该对象必须继承自:BaseVo
	 */
	public static BaseVo parseJson2Vo(InputStream in, Class<? extends BaseVo> classzz) {
		BaseVo vo = null;
		try {
			Gson gson = new Gson();
			InputStreamReader inReader = new InputStreamReader(in);
			vo = gson.fromJson(inReader, classzz);
		} catch ( Exception e) {
			e.printStackTrace();
			System.out.println("parseJson2Vo json解析失败" + e.getMessage());
		}
		return vo;
	}

	public static BaseVo parseJson2Vo(String jsonStr, Class<? extends BaseVo> classzz) {
		BaseVo vo = null;
		try {
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse( jsonStr );
			vo = gson.fromJson(jsonObject, classzz);
		} catch ( Exception e) {
			e.printStackTrace();
			System.out.println("parseJson2Vo json解析失败" + e.getMessage());
		}
		return vo;
	}
	
	/**
	 * 将单层的json数据转换为对象 { "":"" } 
	 * 该对象必须继承自:BaseVo
	 */
	public static JsonArray parseJson2JsonArray(String jsonStr) {
		JsonArray jsonArray = null;
		try {
 			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse( jsonStr );
			jsonArray = (JsonArray) jsonObject.get("item");
		} catch ( Exception e) {
			e.printStackTrace();
			System.out.println("parseJson2JsonArray 解析失败" + e.getMessage() + jsonStr);
		}
		return jsonArray;
	}
	
	/**
	 * 将JsonElement转化为对象
	 * @param element
	 * @param classzz
	 * @return
	 */
	public static BaseVo JsonElement2Obj(JsonElement element, Class<? extends BaseVo> classzz) {
		Gson gson = new Gson();
		return gson.fromJson(element, classzz);
	}
	
	/**
	 * 将数据转换为数组,json格式  (包含item根节点)
	 * {  "item":[
	 *   	{ 	"text":"" } ,
	 *   	{   "text":"" }
	 *		]		
	 *	}
	 * @param jsonStr	json字符串
	 * @param nodeName item节点名字
	 * @param type  new TypeToken<BaseVo>(){}.getType();
	 * @return
	 */
	public static ArrayList<? extends BaseVo> parseJson2List(String jsonStr, Type type, String nodeName){
		ArrayList<? extends BaseVo> arrvo = null;
		try {
			Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			JsonObject jsonObject = (JsonObject) parser.parse( jsonStr );
			JsonArray jsonArray = (JsonArray) jsonObject.get( nodeName );
			arrvo = gson.fromJson(jsonArray, type);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("parseJson2List 解析失败 " + e.getMessage());
		}
		return arrvo;
	}
	
	
	/**
	 * 将数据转换为数组,json格式  (不包含item根节点)
	 *  [ { "text":"" } ,
	 *    { "text":"" }
	 *  ]		
	 * @param jsonStr
	 * @return
	 */
	public static ArrayList<? extends BaseVo> parseJson2ListNoItem(String jsonStr, Type type ) {
		ArrayList<? extends BaseVo> arrvo = null;
		try {
 			Gson gson = new Gson();
			arrvo = gson.fromJson(jsonStr, type);
		} catch ( Exception e ) {
			e.printStackTrace();
			System.out.println("json解析失败"  + jsonStr);
		}
		return arrvo;
	}
	 
}
