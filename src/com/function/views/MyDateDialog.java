package com.function.views;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.function.activity.R;
import com.function.utils.MyScreenUtils;
import com.function.views.CalendarView.OnCalItemClickListener;

/**
 * ��������ѡ��Ի���
 * @author Administrator
 *
 */
public class MyDateDialog{

	private List<String> mdateList;
	public Dialog alertDialog;
	public AlertDateCallback alertCallback;
	public Activity context;
	CalendarView calendar;
	TextView calendarCenter;
	View left, right;
	boolean mflag;
	
	public interface AlertDateCallback{
		public void ybutton(List<String> dList);
	}
	
	public MyDateDialog(Activity mActivity, AlertDateCallback callback){
		this.context = mActivity;
		this.alertCallback = callback;
	}
	
	/**
	 * false����ѡ��true����ѡ
	 * @param flag
	 * @param dList
	 */
	public void showMyDateDialog(boolean flag, List<String> dList) {
		// TODO Auto-generated constructor stub
		mdateList = dList;
		mflag = flag;
		View view = LayoutInflater.from(context).inflate(R.layout.datedialog_view, null);
		Button yButton = (Button)view.findViewById(R.id.button_cancle);
		calendar = (CalendarView)view.findViewById(R.id.calendar);
		calendarCenter = (TextView)view.findViewById(R.id.calendarCenter);
		left = (View)view.findViewById(R.id.calendarLeft);
		right = (View)view.findViewById(R.id.calendarRight);
		if (!mflag) {
			yButton.setText("ȡ��");
			yButton.setTextColor(context.getResources().getColor(R.color.light_black));
			yButton.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		calendar.setdatelist(mdateList);
		calendar.setSelectMore(false); //���õ�ѡ ��ѡ 
		//������������
		calendar.setCalendarData(new Date());
		//��ȡ���������� ya[0]Ϊ�꣬ya[1]Ϊ�£���ʽ��ҿ��������������ؼ��иģ�
		String[] ya = calendar.getYearAndmonth().split("-"); 
		calendarCenter.setText(ya[0]+"��"+ya[1]+"��");
		alertDialog = new Dialog(context, R.style.alert_dialog);
		alertDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams params = alertDialog.getWindow()   
                .getAttributes();   
        params.width = MyScreenUtils.getWidth(context);   
        alertDialog.getWindow().setAttributes(params);  
        
        left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//�����һ�� ͬ���������� 
				String leftYearAndmonth = calendar.clickLeftMonth(); 
				String[] ya = leftYearAndmonth.split("-"); 
				calendarCenter.setText(ya[0]+"��"+ya[1]+"��");
			}
		});
        
        right.setOnClickListener(new OnClickListener() {
        	
        	@Override
        	public void onClick(View v) {
        		// TODO Auto-generated method stub
        		//�����һ��
    			String rightYearAndmonth = calendar.clickRightMonth();
    			String[] ye = rightYearAndmonth.split("-"); 
    			calendarCenter.setText(ye[0]+"��"+ye[1]+"��");
        	}
        });
        
      		
      		//���ÿؼ����������Լ����������ÿһ�죨���Ҳ�����ڿؼ��и��������趨��
      		calendar.setOnCalItemClickListener(new OnCalItemClickListener() {
      			@Override
      			public void OnItemClick(Date selectedStartDate,
      					Date selectedEndDate, List<String> dates, boolean dis) {
      				// TODO Auto-generated method stub
      				mdateList = dates;
      				if (!mflag) {
      					alertCallback.ybutton(mdateList);
      					alertDialog.dismiss();
					}
      			}
      		});
      		
        /**
         * ȷ����ť
         */
		yButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mflag) {
					alertCallback.ybutton(mdateList);
				}
				alertDialog.dismiss();
			}
		});
		
	}

}
