package com.function.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

/**
 * @author SD卡工具类，处理一切与sd卡相关的操作
 * @see isSDCardExist(), 查看是否存在sd卡。
 * 
 */
public class MySDCardUtil {
	
	
	// SD卡不存在
	public static String SDCARD_IS_UNMOUTED = "没有SD卡";

	/**
	 *  判断sdcard是否存在,true为存在，false为不存在
	 * @return
	 */
	public static boolean isSDCardExist() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	
	
	/**
	 *  判断sdcard的状态，并告知用户
	 * @return
	 */
	public static String checkAndReturnSDCardStatus() {
		String status = Environment.getExternalStorageState();
		if(status!=null){
		//SD已经挂载,可以使用
		if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return "1";
		} else if (status.equals(android.os.Environment.MEDIA_REMOVED)) {
			//SD卡已经已经移除
			return "SD卡已经移除或不存在";

		} else if (status.equals(android.os.Environment.MEDIA_SHARED)) {
			//SD卡正在使用中
			return "SD卡正在使用中";

		} else if (status.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
			//SD卡只能读，不能写
			return "SD卡只能读，不能写";
		} else {
			//SD卡的其它情况
			return "SD卡不能使用或不存在";
		}
		} else {
			//SD卡的其它情况
			return "SD卡不能使用或不存在";
		}
	}

	/**
	 * 获取sd卡的路径(没有sd卡返回内缓存路径)
	 * 
	 * @return 路径的字符串
	 */
	public static String getSDPath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			return Environment.getExternalStorageDirectory().getPath().toString();// 获取sd卡目录
		}else {
			return MyFileUtil.getCachePath();//获取内缓存目录
		}
	}
	
	/**
	 *  获取sdcard路径
	 * @return
	 */
	public static String getSdcardUrl() {
		File sdDir = null;
		if (isSDCardExist()) {
			sdDir = Environment.getExternalStorageDirectory().getAbsoluteFile();// 获取跟目录
			return sdDir.toString();
		} else {
			return SDCARD_IS_UNMOUTED;
		}
	}
	
	/**
	 * 获取SD卡的剩余容量 单位byte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize()
	{
		if (isSDCardExist())
		{
			StatFs stat = new StatFs(getSdcardUrl());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 * 
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	public static long getFreeBytes(String filePath)
	{
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSdcardUrl()))
		{
			filePath = getSdcardUrl();
		} else
		{// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * 获取系统的根目录
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath()
	{
		return Environment.getRootDirectory().getAbsolutePath();
	}

}
