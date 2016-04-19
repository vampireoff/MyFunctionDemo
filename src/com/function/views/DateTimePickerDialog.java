package com.function.views;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.View;

import com.function.views.DateTimePicker.OnClickListenered;
import com.function.views.DateTimePicker.OnDateTimeChangedListener;
/**
 * 时间选择对话框
 * @author Administrator
 *
 */

public class DateTimePickerDialog extends AlertDialog implements OnClickListener
{
	private DateTimePicker mDateTimePicker;
	private OnDateTimeSetListener mOnDateTimeSetListener;
	private int hour, min;
	/**
	 * 
	 * @param context
	 */
	public DateTimePickerDialog(Context context) 
	{
		super(context);
		mDateTimePicker = new DateTimePicker(context);
		setView(mDateTimePicker, 0, 0, 0, 0);
		mDateTimePicker.setOnDateTimeChangedListener(new OnDateTimeChangedListener()
		{
			@Override
			public void onDateTimeChanged(DateTimePicker view, int mhour, int minute)
			{
				hour = mhour;
				min = minute;
			}
		});
		
		mDateTimePicker.setOnClickListenered(new OnClickListenered() {
			
			@Override
			public void OnButtonClickListener(View v) {
				// TODO Auto-generated method stub
				if (mOnDateTimeSetListener != null) 
				{
					mOnDateTimeSetListener.OnDateTimeSet(DateTimePickerDialog.this, 
							(hour < 10 ? ("0" + hour) : ("" + hour)) + ":" + 
							(min < 10 ? ("0" + min) : ("" + min)));
				}
			}
		});
		
	}
	
	public interface OnDateTimeSetListener 
	{
		void OnDateTimeSet(AlertDialog dialog, String time);
	}
	
	public void setOnDateTimeSetListener(OnDateTimeSetListener callBack)
	{
		mOnDateTimeSetListener = callBack;
	}
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	
}
