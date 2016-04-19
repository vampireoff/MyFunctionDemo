package com.function.utils;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
 * ������ͼ����
 * @author Administrator
 *
 */
public class MyViewUtil {
	
	/**
	 * ��ʾ��ʾ�Ի���
	 * @param context
	 * @param callback
	 * @param resid
	 */
	public static void showAlertDialog(Activity context, AlertCallback callback, int resid){
		new MyAlertDialog(context, callback).showMyAlertDialog(resid);
	}
	
	/**
	 * ��ʾ��ѡ�Ի���
	 * @param context
	 * @param callback
	 * @param arrs
	 * @param boos
	 */
	public static void showMultiDialog(Activity context, AlertMultiCallback callback, String[] arrs, boolean[] boos){
		new MyMultiDialog(context, arrs, boos, callback).showMultiChoiceDialog();
	}
	
	/**
	 * ��ʾʱ��ѡ��Ի���
	 * @param context
	 * @param callback
	 */
	public static void showTimeDialog(Activity context, AlertTimeCallback callback){
		new MyTimeSelectDialog(context, callback).showTimeDialog();
	}
	
	/**
	 * ��ʾ�������ݵ��б�Ի���
	 * @param context
	 * @param callback
	 * @param stringid
	 * @param list
	 */
	public static void showManyListDialog(Activity context, AlertListCallback3 callback, int stringid, List<Map<String, String>> list){
		new MyManyListDialog(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * ��ʾ�������ݵ��б�Ի���List<<String>>
	 * @param context
	 * @param callback
	 * @param list
	 * @param stringid (����)
	 */
	public static void showFewListDialog1(Activity context, AlertListCallback1 callback, List<String> list, int stringid){
		new MyFewListDialog1(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * ��ʾ�������ݵ��б�Ի���List<Map<String, String>>
	 * @param context
	 * @param callback
	 * @param list
	 * @param stringid (����)
	 */
	public static void showFewListDialog2(Activity context, AlertListCallback2 callback, List<Map<String, String>> list, int stringid){
		new MyFewListDialog2(context, callback, list, stringid).showMyListDialog();
	}
	
	/**
	 * ����ѡ��Ի���
	 * @param context
	 * @param callback
	 * @param flag (false����ѡ��true����ѡ)
	 * @param dList (��ѡ��null)
	 */
	public static void showDateDialog(Activity context, AlertDateCallback callback, boolean flag, List<String> dList){
		new MyDateDialog(context, callback).showMyDateDialog(flag, dList);
	}
	
	/**
	 *  �޸������������пؼ�������
	 * @param root
	 * @param path
	 * @param act
	 */
	public static void changeFonts(ViewGroup root,String path, Activity act) {  
		//path������·��
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
	 *  �޸������������пؼ��������С
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
	 *  ���ı�ؼ�λ�ã��޸Ŀؼ���С
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
	 *  �޸Ŀؼ��ĸ�
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
     * ��ȡ�ؼ��ĸ߶ȣ������ȡ�ĸ߶�Ϊ0�������¼���ߴ���ٷ��ظ߶�
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredHeight(View view) {
        calcViewMeasure(view);
        return view.getMeasuredHeight();
    }

    /**
     * ��ȡ�ؼ��Ŀ�ȣ������ȡ�Ŀ��Ϊ0�������¼���ߴ���ٷ��ؿ��
     *
     * @param view
     * @return
     */
    public static int getViewMeasuredWidth(View view) {
        calcViewMeasure(view);
        return view.getMeasuredWidth();
    }

    /**
     * �����ؼ��ĳߴ�
     *
     * @param view
     */
    public static void calcViewMeasure(View view) {

        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        view.measure(width, expandSpec);
    }
}
