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
 * 日期工具
 * @author Administrator
 *
 */
public class MyDateUtil {

	/** 
	 * 将时间戳转为代表"距现在多久之前"的字符串 
	 * @param timeStr   时间戳 
	 * @return 
	 */  
	public static String getStandardDate(String timeStr, Context context) {  
	  
		if (timeStr == null || timeStr.equals("")) {
			return "";
		}
	    StringBuffer sb = new StringBuffer();  
	    long t = getStringToDate(timeStr);
	    long time = System.currentTimeMillis() - t;  
	    long mill = (long) Math.ceil(time /1000);//秒前  
	  
	    long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前  
	  
	    long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时  
	  
	    long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前  
	  
	    if (day - 1 > 0) {  
	    	if ((day - 1) == 1) {
	    		sb.append("昨天");  
			}else if((day - 1) < 4){
				sb.append((day - 1) + "天前");  
			}else {
				sb.append(timeStr.split(" ")[0].replace("/", "-"));
			}
	    } else if (hour - 1 > 0) {  
	        if (hour >= 24) {  
	            sb.append("昨天");  
	        } else {  
	            sb.append((hour - 1) + "小时前");  
	        }  
	    } else if (minute - 1 > 0) {  
	        if (minute == 60) {  
	            sb.append("1" + "小时前");  
	        } else {  
	            sb.append((minute - 1) + "分钟前");  
	        }  
	    } else if (mill - 1 > 0) {  
	        if (mill == 60) {  
	            sb.append("1" + "分钟前");  
	        } else {  
	            sb.append("刚刚");  
	        }  
	    } else {  
	        sb.append("刚刚");  
	    }  
	    return sb.toString();  
	}  
	
	
	 /**
	  * 将字符串转为时间戳
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
	 * 获取当前月最后一天
	 * @return
	 */
	public static String getMonthLast(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar ca = Calendar.getInstance(); 
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH)); 
		return formatter.format(ca.getTime());
	}
	/**
	 * 获取相对今天的加减日期
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
	 * 获取当前月第一天
	 * @return
	 */
	public static String getMonthFirst(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance(); 
		c.add(Calendar.MONTH, 0);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		return formatter.format(c.getTime());
	}
	/**
	 * 获取今天的日期(yyyy-mm-dd)
	 * @return
	 */
	public static String getToday(){
		return (new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
	}
	
	/**
	 * 获取当前的时间
	 * @return
	 */
	public static String getTime(){
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
	}
	
	/**
	 * 获取特定日期的加减日期
	 * @param date
	 * @return
	 */
	public static String getAddMinDate_oneday(String date, int i){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		try {
			calendar.setTime(sdf.parse(date));	//把当前时间赋给日历
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_MONTH, i);  //设置为前一天

		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 比较两个日期大小(0:相等, -1:第一个小, 1:第一个大, 404:出错)
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
