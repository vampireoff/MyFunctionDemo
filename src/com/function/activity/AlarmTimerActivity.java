package com.function.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.function.receiver.AlarmReceiver;

/**
 * 闹钟定时器功能
 * @author Administrator
 *
 */
public class AlarmTimerActivity extends BaseActivity {

	private TextView textView;
	private Button button;
	AlarmManager alarmManager;
	PendingIntent pIntent;
	int count = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alarm);
		textView = getTextView(R.id.alarm_text);
		button = getButton(R.id.alarm_btn);
		
		registerReceiver(receiver, new IntentFilter("refresh"));
		
		//设定一个开始时间
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.HOUR_OF_DAY, 9);
//		calendar.set(Calendar.MINUTE, 45);
//		calendar.set(Calendar.SECOND, 0);
		
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.setAction("isalarm");
		pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
		alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000, pIntent);
		
		button.setOnClickListener(this);
	}
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			textView.setText(count + "");
			count ++;
		}
	};
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		//取消闹钟
		alarmManager.cancel(pIntent);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		alarmManager.cancel(pIntent);
		sendBroadcast(new Intent("stop"));
		unregisterReceiver(receiver);
	}
}
