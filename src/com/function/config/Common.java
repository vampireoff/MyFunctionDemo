package com.function.config;

import com.function.utils.MySDCardUtil;

/**
 * ������
 * @author Administrator
 *
 */
public class Common {

	/**
	 * ip��ַ
	 */
	public static final String mainurl = "http://wx.landray.com:81";
//	public static String mainurl = "http://192.168.111.13";
	/**
	 * �ӿڵ�ַ
	 */
	public static final String wsurl = "/WQT/Legwork.asmx";
//	public static String wsurl = "/LegworkWS/Legwork.asmx";
	/**
	 * ͼƬ��ַ
	 */
	public static final String imgurl = "/WQT";
//	public static String imgurl = "/LegworkWS";
	/**
	 * �����ռ�
	 */
	public static final String namespace = "http://tempuri.org/";
	/**
	 * ��Ȩ��
	 */
	public static final String accesskey = "lwd";
	/**
	 * Ĭ������
	 */
	public static final String defpwd = "123456";
	/**
	 * ��Ƭ���ݴ�
	 */
	public static String photoname = "";
	/**
	 * ���մ�ŵ�·��
	 */
	public static final String takephotourl = MySDCardUtil.getSDPath() + "/legwork/capture";
	/**
	 * log��ŵ�·��
	 */
	public static final String logurl = MySDCardUtil.getSDPath() + "/legwork/log";
	/**
	 * ��Ƭ��ŵ�·��
	 */
	public static final String photourl = MySDCardUtil.getSDPath() + "/legwork/photos";
	/**
	 * apk��ŵ�·��
	 */
	public static final String apkurl = MySDCardUtil.getSDPath() + "/legwork/apk";
	/**
	 * text�ļ���ŵ�·��
	 */
	public static final String texturl = MySDCardUtil.getSDPath() + "/legwork/text";
	/**
	 * ��֯id��ŵ�text�ļ���
	 */
	public static final String orgtextname = "org.txt";
	/**
	 * ����key1
	 */
	public static final String smskey1 = "f5734159d6b8";
	/**
	 * ����key2
	 */
	public static final String smskey2 = "f1ff277b345b5c64ef888ca71f6511ed";
	
	/**
	 * ͼƬѹ������
	 */
	public static int imgoption = 100;
	/**
	 * ����ѡ���ͼƬ����
	 */
	public final static int imgnum = 5;
	/**
	 * ��ʼʱ��
	 */
	public final static String stime = " 08:00";
	/**
	 * ����ʱ��
	 */
	public final static String etime = " 18:00";
}
