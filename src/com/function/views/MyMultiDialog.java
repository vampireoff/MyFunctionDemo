package com.function.views;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.WindowManager;

import com.function.activity.R;
import com.function.utils.MyScreenUtils;

/**
 * 自定义多选对话框
 * @author Administrator
 *
 */
public class MyMultiDialog {
	
	private CustomMultiChoiceDialog.Builder multiChoiceDialogBuilder;
	private Context context;
	private String[] arrs;
	private boolean[] boos;
	private AlertMultiCallback callback;
	
	public interface AlertMultiCallback{
		public void click(boolean[] boo);
	}
	
	public MyMultiDialog(Context context, String[] arrs, boolean[] boos, AlertMultiCallback callback){
		this.context = context;
		this.arrs = arrs;
		this.boos = boos;
		this.callback = callback;
	}
	
	public void showMultiChoiceDialog() {
		
		multiChoiceDialogBuilder = new CustomMultiChoiceDialog.Builder(context);
		CustomMultiChoiceDialog multiChoiceDialog = multiChoiceDialogBuilder.setTitle("全选")
				.setMultiChoiceItems(arrs, boos, null, true)
				.setPositiveButton("确定", new PositiveClickListener())
				.setNegativeButton("取消", null).create();
		multiChoiceDialog.setCanceledOnTouchOutside(false);
		multiChoiceDialog.show();
		WindowManager.LayoutParams params = multiChoiceDialog.getWindow()   
				.getAttributes();   
		params.width = MyScreenUtils.getWidth(context) - context.getResources().getDimensionPixelSize(R.dimen.dialogpadding);   
		params.height = MyScreenUtils.getHeight(context) - context.getResources().getDimensionPixelSize(R.dimen.dialogpadding);
		multiChoiceDialog.getWindow().setAttributes(params);  
	}
	
	class PositiveClickListener implements OnClickListener {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			callback.click(multiChoiceDialogBuilder.getCheckedItems());
		}
	}
}
