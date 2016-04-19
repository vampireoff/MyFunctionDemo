package com.function.config;

import com.function.utils.MySDCardUtil;

/**
 * 常量类
 * @author Administrator
 *
 */
public class Common {

	/**
	 * ip地址
	 */
	public static final String mainurl = "http://wx.landray.com:81";
//	public static String mainurl = "http://192.168.111.13";
	/**
	 * 接口地址
	 */
	public static final String wsurl = "/WQT/Legwork.asmx";
//	public static String wsurl = "/LegworkWS/Legwork.asmx";
	/**
	 * 图片地址
	 */
	public static final String imgurl = "/WQT";
//	public static String imgurl = "/LegworkWS";
	/**
	 * 命名空间
	 */
	public static final String namespace = "http://tempuri.org/";
	/**
	 * 授权码
	 */
	public static final String accesskey = "lwd";
	/**
	 * 默认密码
	 */
	public static final String defpwd = "123456";
	/**
	 * 照片名暂存
	 */
	public static String photoname = "";
	/**
	 * 拍照存放的路径
	 */
	public static final String takephotourl = MySDCardUtil.getSDPath() + "/legwork/capture";
	/**
	 * log存放的路径
	 */
	public static final String logurl = MySDCardUtil.getSDPath() + "/legwork/log";
	/**
	 * 照片存放的路径
	 */
	public static final String photourl = MySDCardUtil.getSDPath() + "/legwork/photos";
	/**
	 * apk存放的路径
	 */
	public static final String apkurl = MySDCardUtil.getSDPath() + "/legwork/apk";
	/**
	 * text文件存放的路径
	 */
	public static final String texturl = MySDCardUtil.getSDPath() + "/legwork/text";
	/**
	 * 组织id存放的text文件名
	 */
	public static final String orgtextname = "org.txt";
	/**
	 * 短信key1
	 */
	public static final String smskey1 = "f5734159d6b8";
	/**
	 * 短信key2
	 */
	public static final String smskey2 = "f1ff277b345b5c64ef888ca71f6511ed";
	
	/**
	 * 图片压缩参数
	 */
	public static int imgoption = 100;
	/**
	 * 允许选择的图片总数
	 */
	public final static int imgnum = 5;
	/**
	 * 开始时间
	 */
	public final static String stime = " 08:00";
	/**
	 * 结束时间
	 */
	public final static String etime = " 18:00";
}
