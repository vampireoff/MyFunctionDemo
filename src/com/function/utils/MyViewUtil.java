package com.function.utils;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.function.activity.MainActivity;
import com.function.activity.R;
import com.function.views.MyAlertDialog;
import com.function.views.MyAlertDialog.AlertCallback;
import com.function.views.MyDateDialog;
import com.function.views.MyDateDialog.AlertDateCallback;
import com.function.views.MyFewListDialog1;
import com.function.views.MyFewListDialog1.AlertListCallback1;
import com.function.views.MyFewListDialog2;
import com.function.views.MyFewListDialog2.AlertListCallback2;
import com.function.views.MyManyListDialog;
import com.function.views.MyManyListDialog.AlertListCallback3;
import com.function.views.MyMultiDialog;
import com.function.views.MyMultiDialog.AlertMultiCallback;
import com.function.views.MyTimeSelectDialog;
import com.function.views.MyTimeSelectDialog.AlertTimeCallback;

/**
 * 界面视图工具
 * @author Administrator
 *
 */
public class MyViewUtil {
	
	/**
	 * 显示notify
	 * @param context
	 */
	public static void showNotify(Context context){
		NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context); 
        
        mBuilder.setContentTitle("SMSservice runing...")//设置通知栏标题  
        .setTicker("runing") //通知首次出现在通知栏，带上升动画效果的  
        .setContentIntent(PendingIntent.getActivity(
        		context, 110, new Intent(context, MainActivity.class), 1))
        .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间  
        .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级  
        .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消    
        .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)  
//        .setDefaults(Notification.DEFAULT_SOUND)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合  
        //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission  
        .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON  
        
        notificationManager.notify(110, mBuilder.build());
	}
	
	/**
	 * 显示提示对话框
	 * @param context
	 * @param callback
	 * @param resid
	 */
	public static void showAlertDialog(Activity context, AlertCallback callback, int resid){
		new MyAlertDialog(context, callback).showMyAlertDialog(resid);
	}
	
	/**
	 * 显示多选对话框
	 * @param context
	 * @param callback
	 * @param arrs
	 * @param boos
	 */
	public static void showMultiDialog(Activity context, AlertMultiCallback callback, String[] arrs, boolean[] boos){
		new MyMultiDialog(context, arrs, boos, callback).showMultiChoiceDialog();
	}
	
	/**
	 * 显示时间选择对话框
	 * @param context
	 * @param callback
	 */
	public static void showTimeDialog(Activity context, AlertTimeCallback callback){
		new MyTimeSelectDialog(context, callback).showTimeDialog();
	}
	
	/**
	 * 显示大量数据的列表对话框
	 * @param context
	 * @param callback
	 * @param stringid
	 * @param list
	 */
	public static void showManyListDialog(Activity context, AlertListCallback3 callback, int stringid, List<Map<String, String>> list){
		new MyManyListDialog(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * 显示少量数据的列表对话框List<<String>>
	 * @param context
	 * @param callback
	 * @param list
	 * @param stringid (标题)
	 */
	public static void showFewListDialog1(Activity context, AlertListCallback1 callback, List<String> list, int stringid){
		new MyFewListDialog1(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * 显示少量数据的列表对话框List<Map<String, String>>
	 * @param context
	 * @param callback
	 * @param list
	 * @param stringid (标题)
	 */
	public static void showFewListDialog2(Activity context, AlertListCallback2 callback, List<Map<String, String>> list, int stringid){
		new MyFewListDialog2(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * 日期选择对话框
	 * @param context
	 * @param callback
	 * @param flag (false：单选，true：多选)
	 * @param dList (单选传null)
	 */
	public static void showDateDialog(Activity context, AlertDateCallback callback, boolean flag, List<String> dList){
		new MyDateDialog(context, callback).showMyDateDialog(flag, dList);
	}
	
	/**
	 *  修改整个界面所有控件的字体
	 * @param root
	 * @param path
	 * @param act
	 */
	public static void changeFonts(ViewGroup root,String path, Activity act) {  
		//path是字体路径
		Typeface tf = Typeface.createFromAsset(act.getAssets(),path);  
		for (int i = 0; i < root.getChildCount(); i++) {  
			View v = root.getChildAt(i); 
			if (v instanceof TextView) {  
				((TextView) v).setTypeface(tf);  
			} else if (v instanceof Button) {  
				((Button) v).setTypeface(tf);  
			} else if (v instanceof EditText) {  
				((EditText) v).setTypeface(tf);  
			} else if (v instanceof ViewGroup) {  
				changeFonts((ViewGroup) v, path,act);  
			} 
		}  
	}
	
	/**
	 *  修改整个界面所有控件的字体大小
	 */
	public static void changeTextSize(ViewGroup root,int size, Activity act) {  
		for (int i = 0; i < root.getChildCount(); i++) {  
			View v = root.getChildAt(i);  
			if (v instanceof TextView) {  
				((TextView) v).setTextSize(size);
			} else if (v instanceof Button) {  
				((Button) v).setTextSize(size);
			} else if (v instanceof EditText) {  
				((EditText) v).setTextSize(size);  
			} else if (v instanceof ViewGroup) {  
				changeTextSize((ViewGroup) v,size,act);  
			}  
		}  
	}
	
	/**
	 *  不改变控件位置，修改控件大小
	 * @param v
	 * @param W
	 * @param H
	 */
	public static void changeWH(View v,int W,int H)
	{
		LayoutParams params = (LayoutParams)v.getLayoutParams();
		params.width = W;
		params.height = H;
		v.setLayoutParams(params);
	}
	
	/**
	 *  修改控件的高
	 * @param v
	 * @param H
	 */
	public static void changeH(View v,int H)
	{
		LayoutParams params = (LayoutParams)v.getLayoutParams();
		params.height = H;
		v.setLayoutParams(params);
	}
	
	 /**
     * 获取控件的高度，如果获取的高度为0，则重新计算尺寸后再返回高度
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * 获取控件的宽度，如果获取的宽度为0，则重新计算尺寸后再返回宽度
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view) {
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * 测量控件的尺寸
     *
     * @param view
     */
    public static void calcViewMeasure(View view) {

        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }
}
