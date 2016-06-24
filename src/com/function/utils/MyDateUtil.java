package com.function.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * ���ڹ���
 * @author Administrator
 * 
 * HH��24Сʱ�ƣ�hh��12Сʱ��
 */
@SuppressLint("SimpleDateFormat")
public class MyDateUtil {

	/**
	 * stringתDate
	 * @param string
	 * @return
	 */
	public static Date string2date(String string){
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	/**
	 * Dateתstring(�����ʽ��yyyy-MM-dd HH:mm:ss)
	 * @param string
	 * @return
	 */
	public static String date2string(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * �����ַ�����ʽ��Ϊ����������(���ظ�ʽ��2016-04-20 ����)
	 * @param date
	 * @return
	 */
	public static String time2ymde(String date){
		return new SimpleDateFormat("yyyy-MM-dd E").format(string2date(date));
	}
	
	/**
	 * �����ַ�����ʽ��Ϊ����(���ظ�ʽ������)
	 * @param date
	 * @return
	 */
	public static String time2e(String date){
		return new SimpleDateFormat("E").format(string2date(date));
	}
	
	/**
	 * �����ַ�����ʽ��Ϊ������(���ظ�ʽ��2016-04-20)
	 * @param date
	 * @return
	 */
	public static String time2ymd(String date){
		return new SimpleDateFormat("yyyy-MM-dd").format(string2date(date));
	}
	
	/**
	 * �����ַ�����ʽ��Ϊ����(���ظ�ʽ��2016-04)
	 * @param date
	 * @return
	 */
	public static String time2ym(String date){
		return new SimpleDateFormat("yyyy-MM").format(string2date(date));
	}
	
	/**
	 * �����ַ�����ʽ��Ϊ��(���ظ�ʽ��2016)
	 * @param date
	 * @return
	 */
	public static String time2y(String date){
		return new SimpleDateFormat("yyyy").format(string2date(date));
	}
	
	/**
	 * �����ַ�����ʽ��Ϊ��(���ظ�ʽ��4)
	 * @param date
	 * @return
	 */
	public static String time2m(String date){
		return new SimpleDateFormat("M").format(string2date(date));
	}
	
	/**
	 * ʱ���ַ�����ʽ��Ϊ������ʱ����(���ظ�ʽ��2016-04-20 09:20:00)
	 * @param date
	 * @return
	 */
	public static String time2ymdhms(String date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(string2date(date));
	}
	
	/**
	 * ʱ���ַ�����ʽ��Ϊ����(���ظ�ʽ��20)
	 * @param date
	 * @return
	 */
	public static String time2d(String date){
		return new SimpleDateFormat("d").format(string2date(date));
	}
	
	/** 
	 * ��ʱ���ַ���תΪ����"�����ڶ��֮ǰ"���ַ��� 
	 * @param timeStr   ʱ���ַ�������ʽ(yyyy-MM-dd HH:mm:ss)
	 * @return 
	 */  
	public static String getStandardDate(String timeStr) {  
	  
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
	 * ��ȡ���ĳ��ʱ��ļӼ�Сʱ
	 * @return
	 */
	public static String getAddMinHour(String time, int num){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //����ʱ���ʽ
		
		Calendar calendar = Calendar.getInstance(); //�õ�����
		try {
			calendar.setTime(sdf.parse(time));	//�ѵ�ǰʱ�丳������
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.HOUR_OF_DAY, num);

		return sdf.format(calendar.getTime());
	}
	
	/**
	 * ����ʱ��Сʱ��
	 * @return
	 */
	public static int getTimeDiff(String t1, String t2){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try
		{
			Date d1 = df.parse(t1);
			Date d2 = df.parse(t2);
			long diff = Math.abs(d1.getTime() - d2.getTime());//�����õ��Ĳ�ֵ��΢�뼶��
			
			long days = diff / (1000 * 60 * 60 * 24);
			long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//			long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
			
			return (int)hours;
		}
		catch (Exception e)
		{
			return 404;
		}
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
	
	/**
	 * �Ƚ�����ʱ���С(0:���, -1:��һ��С, 1:��һ����, 404:����)
	 * @param date1
	 * @param date2
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static int compareTime(String date1, String date2){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
