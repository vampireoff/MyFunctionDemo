package com.function.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;

@SuppressLint("SimpleDateFormat")
/**
 * ���ڹ���
 * @author Administrator
 *
 */
public class MyDateUtil {

	/** 
	 * ��ʱ���תΪ����"�����ڶ��֮ǰ"���ַ��� 
	 * @param timeStr   ʱ��� 
	 * @return 
	 */  
	public static String getStandardDate(String timeStr, Context context) {  
	  
		if (timeStr == null || timeStr.equals("")) {
			return "";
		}
	    StringBuffer sb = new StringBuffer();  
	    long t = getStringToDate(timeStr);
	    long time = System.currentTimeMillis() - t;  
	    long mill = (long) Math.ceil(time /1000);//��ǰ  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// ����ǰ  
	  
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// Сʱ  
	  
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// ��ǰ  
	  
	    if (day - 1 > 0) {  
	    	if ((day - 1) == 1) {
	    		sb.append("����");  
			}else if((day - 1) < 4){
				sb.append((day - 1) + "��ǰ");  
			}else {
				sb.append(timeStr.split(" ")[0].replace("/", "-"));
			}
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("����");  
	        } else {  
	            sb.append((hour - 1) + "Сʱǰ");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1" + "Сʱǰ");  
	        } else {  
	            sb.append((minute - 1) + "����ǰ");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1" + "����ǰ");  
	        } else {  
	            sb.append("�ո�");  
	        }  
	    } else {  
	        sb.append("�ո�");  
	    }  
	    return sb.toString();  
	}  
	
	
	 /**
	  * ���ַ���תΪʱ���
	  * */
    @SuppressLint("SimpleDateFormat")
	public static long getStringToDate(String time) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        try{
            date = sdf.parse(time.replace("/", "-"));
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }
	
	/**
	 * ��ȡ��ǰ�����һ��
	 * @return
	 */
	public static String getMonthLast(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance(); 
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		return formatter.format(ca.getTime());
	}
	/**
	 * ��ȡ��Խ���ļӼ�����
	 * @return
	 */
	public static String getAddMinDate_today(int day){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return formatter.format(calendar.getTime());
	}
	/**
	 * ��ȡ��ǰ�µ�һ��
	 * @return
	 */
	public static String getMonthFirst(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//����Ϊ1��,��ǰ���ڼ�Ϊ���µ�һ�� 
		return formatter.format(c.getTime());
	}
	/**
	 * ��ȡ���������(yyyy-mm-dd)
	 * @return
	 */
	public static String getToday(){
		return (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
	}
	
	/**
	 * ��ȡ��ǰ��ʱ��
	 * @return
	 */
	public static String getTime(){
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
	}
	
	/**
	 * ��ȡ�ض����ڵļӼ�����
	 * @param date
	 * @return
	 */
	public static String getAddMinDate_oneday(String date, int i){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //����ʱ���ʽ
		
		Calendar calendar = Calendar.getInstance(); //�õ�����
		try {
			calendar.setTime(sdf.parse(date));	//�ѵ�ǰʱ�丳������
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, i);  //����Ϊǰһ��

		return sdf.format(calendar.getTime());
	}
	
	/**
	 * �Ƚ��������ڴ�С(0:���, -1:��һ��С, 1:��һ����, 404:����)
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static int comparedate(String date1, String date2){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c1.setTime(formatter.parse(date1));
			c2.setTime(formatter.parse(date2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 404;
		}
		
		return c1.compareTo(c2);
	}
}
