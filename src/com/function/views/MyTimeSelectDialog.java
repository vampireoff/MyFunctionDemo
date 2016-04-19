package com.function.views;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import com.function.views.DateTimePickerDialog.OnDateTimeSetListener;

/**
 * 时间选择对话框
 * @author Administrator
 *
 */
public class MyTimeSelectDialog{

	public Dialog alertDialog;
	public AlertTimeCallback alertCallback;
	public Activity context;
	
	public interface AlertTimeCallback{
		public void clickbtn(String time);
	}
	
	public MyTimeSelectDialog(Activity mActivity, AlertTimeCallback callback){
		this.context = mActivity;
		this.alertCallback = callback;
	}
	
	public void showTimeDialog() {
		// TODO Auto-generated constructor stub
		DateTimePickerDialog dialog = new DateTimePickerDialog(context);
		dialog.setOnDateTimeSetListener(new listener2());
		dialog.show();
	}
	
	public class listener2 implements OnDateTimeSetListener{
		
		@Override
		public void OnDateTimeSet(AlertDialog dialog, String time) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			alertCallback.clickbtn(time);
		}
	};
}
