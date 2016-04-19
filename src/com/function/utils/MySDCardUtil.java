package com.function.utils;

import java.io.File;

import android.os.Environment;
import android.os.StatFs;

/**
 * @author SD�������࣬����һ����sd����صĲ���
 * @see isSDCardExist(), �鿴�Ƿ����sd����
 * 
 */
public class MySDCardUtil {
	
	
	// SD��������
	public static String SDCARD_IS_UNMOUTED = "û��SD��";

	/**
	 *  �ж�sdcard�Ƿ����,trueΪ���ڣ�falseΪ������
	 * @return
	 */
	public static boolean isSDCardExist() {
		return Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	
	
	/**
	 *  �ж�sdcard��״̬������֪�û�
	 * @return
	 */
	public static String checkAndReturnSDCardStatus() {
		String status = Environment.getExternalStorageState();
		if(status!=null){
		//SD�Ѿ�����,����ʹ��
		if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return "1";
		} else if (status.equals(android.os.Environment.MEDIA_REMOVED)) {
			//SD���Ѿ��Ѿ��Ƴ�
			return "SD���Ѿ��Ƴ��򲻴���";

		} else if (status.equals(android.os.Environment.MEDIA_SHARED)) {
			//SD������ʹ����
			return "SD������ʹ����";

		} else if (status.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
			//SD��ֻ�ܶ�������д
			return "SD��ֻ�ܶ�������д";
		} else {
			//SD�����������
			return "SD������ʹ�û򲻴���";
		}
		} else {
			//SD�����������
			return "SD������ʹ�û򲻴���";
		}
	}

	/**
	 * ��ȡsd����·��(û��sd�������ڻ���·��)
	 * 
	 * @return ·�����ַ���
	 */
	public static String getSDPath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����
		if (sdCardExist) {
			return Environment.getExternalStorageDirectory().getPath().toString();// ��ȡsd��Ŀ¼
		}else {
			return MyFileUtil.getCachePath();//��ȡ�ڻ���Ŀ¼
		}
	}
	
	/**
	 *  ��ȡsdcard·��
	 * @return
	 */
	public static String getSdcardUrl() {
		File sdDir = null;
		if (isSDCardExist()) {
			sdDir = Environment.getExternalStorageDirectory().getAbsoluteFile();// ��ȡ��Ŀ¼
			return sdDir.toString();
		} else {
			return SDCARD_IS_UNMOUTED;
		}
	}
	
	/**
	 * ��ȡSD����ʣ������ ��λbyte
	 * 
	 * @return
	 */
	public static long getSDCardAllSize()
	{
		if (isSDCardExist())
		{
			StatFs stat = new StatFs(getSdcardUrl());
			// ��ȡ���е����ݿ������
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// ��ȡ�������ݿ�Ĵ�С��byte��
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * ��ȡָ��·�����ڿռ��ʣ����������ֽ�������λbyte
	 * 
	 * @param filePath
	 * @return �����ֽ� SDCard���ÿռ䣬�ڲ��洢���ÿռ�
	 */
	public static long getFreeBytes(String filePath)
	{
		// �����sd�����µ�·�������ȡsd����������
		if (filePath.startsWith(getSdcardUrl()))
		{
			filePath = getSdcardUrl();
		} else
		{// ������ڲ��洢��·�������ȡ�ڴ�洢�Ŀ�������
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	/**
	 * ��ȡϵͳ�ĸ�Ŀ¼
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath()
	{
		return Environment.getRootDirectory().getAbsolutePath();
	}

}
