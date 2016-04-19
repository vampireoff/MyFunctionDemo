package com.function.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.function.config.Common;

/**
 * ͨ�ù�����
 * @author Administrator
 *
 */
public class MyUtils {
	
	/**
	 * �����Ƿ���ǰ̨����
	 * 
	 * @return
	 */
	public static boolean isAppOnForeground(Context context) {
		// Returns a list of application processes that are running on the
		// device
		
		ActivityManager activityManager = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		String packageName = context.getApplicationContext().getPackageName();
		
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null)
			return false;
		
		for (RunningAppProcessInfo appProcess : appProcesses) {
			// The name of the process that this object is associated with.
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * �������
	 * 
	 * @param mEditText�����
	 * @param mContext������
	 */
	public static void openKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * �ر������
	 * 
	 * @param mEditText�����
	 * @param mContext������
	 */
	public static void closeKeybord(EditText mEditText, Context mContext)
	{
		InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

		imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
	}
	
	
	/**
	 * ��ӡ��־
	 * @param tag
	 * @param msg
	 */
	public static void myLog(String tag, String msg){
		Log.i(tag, msg);
	}
	
	
	/**
	 * ��֤�Ƿ����ֻ�����
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobileNumber(String str) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * ��ȡ�ӿڵ�ַ
	 * @return
	 */
	public static String getWS_url(){
		return Common.mainurl + Common.wsurl;
	}
	/**
	 * ��ȡͼƬ��ַ
	 * @return
	 */
	public static String getIMG_url(){
		return Common.mainurl + Common.imgurl;
	}

	/**
	 * ��ʾtoast
	 * @param activity
	 * @param string
	 */
	public static void showToast2(Context activity, String string){
		Toast.makeText(activity, string, Toast.LENGTH_SHORT).show();
	}
	
	public static void showToast(Context activity, int id){
		Toast.makeText(activity, id, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ����绰����
	 * @param activity
	 * @param phone
	 */
	public static void PhoneCall(Context activity, String phone){
		activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + phone)));
	}
    
}
