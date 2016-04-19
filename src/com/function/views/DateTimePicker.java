package com.function.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.function.activity.R;
import com.function.views.NumberPicker.OnValueChangeListener;
/**
 * 日期选择界面
 * @author Administrator
 *
 */
@SuppressLint("NewApi")
public class DateTimePicker extends FrameLayout
{
	private final NumberPicker mHourSpinner;
	private final NumberPicker mMinuteSpinner;
	private Button button;
	private int hour, minute; 
	private OnDateTimeChangedListener mOnDateTimeChangedListener;
	private OnClickListenered clListener;
	
	public DateTimePicker(Context context)
	{
		super(context);
		
		inflate(context, R.layout.datedialog, this);
		//时
		mHourSpinner=(NumberPicker)this.findViewById(R.id.np_hour);
		mHourSpinner.setMinValue(0);
		mHourSpinner.setMaxValue(23);
		mHourSpinner.setValue(0);
		mHourSpinner.setOnValueChangedListener(mOnHourChangedListener);
		//分
		mMinuteSpinner=(NumberPicker)this.findViewById(R.id.np_minute);
		mMinuteSpinner.setMaxValue(59);
		mMinuteSpinner.setMinValue(0);
		mMinuteSpinner.setValue(0);
		mMinuteSpinner.setOnValueChangedListener(mOnMinuteChangedListener);
		//确定按钮
		button = (Button)this.findViewById(R.id.sure_btn);
		button.setOnClickListener(clickListener);
	}
	
	private OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			onButtonClicked(v);
		}
	};
	//数字滚动监听
	private NumberPicker.OnValueChangeListener mOnMinuteChangedListener=new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			minute = mMinuteSpinner.getValue();
			onDateTimeChanged();
		}
	};
	//数字滚动监听
	private NumberPicker.OnValueChangeListener mOnHourChangedListener=new OnValueChangeListener()
	{
		@Override
		public void onValueChange(NumberPicker picker, int oldVal, int newVal)
		{
			hour = mHourSpinner.getValue();
			onDateTimeChanged();
		}
	};
	
	
	//数字滚动监听接口
	public interface OnDateTimeChangedListener 
	{
		void onDateTimeChanged(DateTimePicker view, int hour, int minute);
	}
	
	public void setOnDateTimeChangedListener(OnDateTimeChangedListener callback) 
	{
		mOnDateTimeChangedListener = callback;
	}
	
	private void onDateTimeChanged() 
	{
		if (mOnDateTimeChangedListener != null)
		{
			mOnDateTimeChangedListener.onDateTimeChanged(this, hour, minute);
		}
	}
	public interface OnClickListenered
	{
		void OnButtonClickListener(View v);
	}
	
	public void setOnClickListenered(OnClickListenered callback) 
	{
		clListener = callback;
	}
	
	private void onButtonClicked(View v) 
	{
		if (clListener != null)
		{
			clListener.OnButtonClickListener(v);
		}
	}
}
