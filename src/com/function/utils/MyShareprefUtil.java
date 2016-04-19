package com.function.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.function.config.Myapplication;

/**
 * SharedPreferences���ݴ�����
 * @author Administrator
 *
 */
public class MyShareprefUtil {

	/**
	 * ��ȡeditor����
	 * @param name
	 * @param key
	 * @param value
	 */
	public static SharedPreferences.Editor getEditor(String name){
		SharedPreferences shared = Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
		return shared.edit();
	}
	/**
	 * �������ݵ�����
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
	 * ��ȡsharedpreferences��Ӧ��ֵ
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
	 * ��ȡsharedpreferences����
	 * @param mcontext
	 * @param name
	 * @param key
	 * @return
	 */
	public static SharedPreferences getShared(String name){
		return Myapplication.getInstance().getSharedPreferences(name, Context.MODE_PRIVATE);
	}
}
