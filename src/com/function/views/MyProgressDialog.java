package com.function.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.function.activity.R;
/**
 * 自定义的progressdialog加载提示框
 * @author Administrator
 *
 */
@SuppressLint("InflateParams")
public class MyProgressDialog {
	private Dialog loadingDialog;
	public static boolean isOpen;
	public static MyProgressDialog progressDialog;
	
	public static MyProgressDialog getInstance(){
		if (progressDialog == null) {
			synchronized (MyProgressDialog.class) {
				if (progressDialog == null) {
					progressDialog = new MyProgressDialog();
				}
			}
		}
		return progressDialog;
	}
	
	/**
	 * 显示自定义的progressDialog
	 * @param context
	 * @param msg
	 * @return
	 */
	public void showDialog(Context context, int textid) {

		if (context == null) return;
		
		if (!isOpen) {
			isOpen = true;
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(R.layout.progress_dialog, null);// 得到加载view
			LinearLayout layout = (LinearLayout) v.findViewById(R.id.progress_linear);// 加载布局
			TextView textView = (TextView) v.findViewById(R.id.progress_text);
			if (textid != 0) {
				textView.setText(textid);
				textView.setVisibility(View.VISIBLE);
			}
			
			loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
			
			loadingDialog.setCanceledOnTouchOutside(false);
			loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.MATCH_PARENT));// 设置布局
			loadingDialog.show();
		}

	}
	
	/**
	 * 关闭加载框
	 */
	public void Dismiss(){
		if (isOpen) {
			loadingDialog.dismiss();
			isOpen = false;
		}
	}
}
