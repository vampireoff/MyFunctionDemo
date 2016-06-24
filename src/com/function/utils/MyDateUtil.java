package com.function.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;

/**
 * 日期工具
 * @author Administrator
 * 
 * HH：24小时制，hh：12小时制
 */
@SuppressLint("SimpleDateFormat")
public class MyDateUtil {

	/**
	 * string转Date
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
	 * Date转string(输出格式：yyyy-MM-dd HH:mm:ss)
	 * @param string
	 * @return
	 */
	public static String date2string(Date date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	/**
	 * 日期字符串格式化为年月日星期(返回格式：2016-04-20 周三)
	 * @param date
	 * @return
	 */
	public static String time2ymde(String date){
		return new SimpleDateFormat("yyyy-MM-dd E").format(string2date(date));
	}
	
	/**
	 * 日期字符串格式化为星期(返回格式：周三)
	 * @param date
	 * @return
	 */
	public static String time2e(String date){
		return new SimpleDateFormat("E").format(string2date(date));
	}
	
	/**
	 * 日期字符串格式化为年月日(返回格式：2016-04-20)
	 * @param date
	 * @return
	 */
	public static String time2ymd(String date){
		return new SimpleDateFormat("yyyy-MM-dd").format(string2date(date));
	}
	
	/**
	 * 日期字符串格式化为年月(返回格式：2016-04)
	 * @param date
	 * @return
	 */
	public static String time2ym(String date){
		return new SimpleDateFormat("yyyy-MM").format(string2date(date));
	}
	
	/**
	 * 日期字符串格式化为年(返回格式：2016)
	 * @param date
	 * @return
	 */
	public static String time2y(String date){
		return new SimpleDateFormat("yyyy").format(string2date(date));
	}
	
	/**
	 * 日期字符串格式化为月(返回格式：4)
	 * @param date
	 * @return
	 */
	public static String time2m(String date){
		return new SimpleDateFormat("M").format(string2date(date));
	}
	
	/**
	 * 时间字符串格式化为年月日时分秒(返回格式：2016-04-20 09:20:00)
	 * @param date
	 * @return
	 */
	public static String time2ymdhms(String date){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(string2date(date));
	}
	
	/**
	 * 时间字符串格式化为日期(返回格式：20)
	 * @param date
	 * @return
	 */
	public static String time2d(String date){
		return new SimpleDateFormat("d").format(string2date(date));
	}
	
	/** 
	 * 将时间字符串转为代表"距现在多久之前"的字符串 
	 * @param timeStr   时间字符串，格式(yyyy-MM-dd HH:mm:ss)
	 * @return 
	 */  
	public static String getStandardDate(String timeStr) {  
	  
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
	 * 获取相对某个时间的加减小时
	 * @return
	 */
	public static String getAddMinHour(String time, int num){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //设置时间格式
		
		Calendar calendar = Calendar.getInstance(); //得到日历
		try {
			calendar.setTime(sdf.parse(time));	//把当前时间赋给日历
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		calendar.add(Calendar.HOUR_OF_DAY, num);

		return sdf.format(calendar.getTime());
	}
	
	/**
	 * 计算时间差（小时）
	 * @return
	 */
	public static int getTimeDiff(String t1, String t2){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try
		{
			Date d1 = df.parse(t1);
			Date d2 = df.parse(t2);
			long diff = Math.abs(d1.getTime() - d2.getTime());//这样得到的差值是微秒级别
			
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
	
	/**
	 * 比较两个时间大小(0:相等, -1:第一个小, 1:第一个大, 404:出错)
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
