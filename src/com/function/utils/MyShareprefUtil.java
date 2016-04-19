package com.function.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.function.config.Myapplication;

/**
 * SharedPreferences数据处理工具
 * @author Administrator
 *
 */
public class MyShareprefUtil {

	/**
	 * 获取editor对象
	 * @param name
	 * @param key
	 * @param value
	 */
	public static SharedPreferences.Editor getEditor(String name){
		SharedPreferences shared = Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
		return shared.edit();
	}
	/**
	 * 保存数据到本地
	 * @param name
	 * @param key
	 * @param value
	 */
	public static void saveShared(String name, String key, String value){
		SharedPreferences shared = Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = shared.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 获取sharedpreferences对应的值
	 * @param mcontext
	 * @param name
	 * @param key
	 * @return
	 */
	public static String getSharedstring(String name, String key){
		SharedPreferences shared = Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
		return shared.getString(key, "");
	}
	
	/**
	 * 获取sharedpreferences对象
	 * @param mcontext
	 * @param name
	 * @param key
	 * @return
	 */
	public static SharedPreferences getShared(String name){
		return Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
	}
}
