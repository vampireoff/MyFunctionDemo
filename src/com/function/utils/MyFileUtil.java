package com.function.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.net.Uri;

import com.function.config.Common;
import com.function.config.Myapplication;
/**
 * �ļ���������
 * @author Administrator
 *
 */
public class MyFileUtil {
	
	/**
	 * ·��תUri
	 * @param url
	 * @return
	 */
	public static Uri urltouri(String url){
		return Uri.fromFile(new File(url));
	}
	
	/**
	 * ����url��ȡ�ļ���
	 * @param tag
	 * @param msg
	 */
	public static String getUrlFilename(String url){
		String[] strings = url.split("/");
		return strings[strings.length - 1];
	}
	
	/**
	 * ��txt�ļ�������ݳ���
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String readfromtxt(String url){
		File file = new File(url);
		
		if (!file.exists()) {
			return "";
		}
		
		byte[] b = null;
		
		try {
			FileInputStream inputStream = new FileInputStream(file);
			int len = inputStream.available();
			if (len == 0) return "";
			
			b = new byte[len];
			inputStream.read(b);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		return EncodingUtils.getString(b, "UTF-8");
	}

	/**
	 * �������ݵ�txt�ļ�
	 * @param url (Ŀ¼��)
	 * @param name (�ļ���)
	 * @param text
	 * @param append (true:���ӣ�false:����)
	 */
	public static void savetotext(String url, String name, String text, boolean append){
		createDirectory(url);
		
		File file = new File(url, name);
		if (file.exists() && !append) {
			file.delete();
		}
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file, append);
			if (text != null && !text.equals("")) {
				byte[] b = text.getBytes();
				outputStream.write(b);
				outputStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * ����log��txt�ļ���
	 * @param string
	 */
	public static void savelog(String text){
		createDirectory(Common.logurl);
		
		File file = new File(Common.logurl, "log.txt");
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file, true);
			if (text != null && !text.equals("")) {
				byte[] b = text.getBytes();
				outputStream.write(b);
				outputStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * ����log��txt�ļ����ʱ�䣩
	 * @param string
	 */
	public static void savelogwithtime(String text){
		createDirectory(Common.logurl);
		
		File file = new File(Common.logurl, "log.txt");
		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(file, true);
			if (text != null && !text.equals("")) {
				text += "\n" + MyDateUtil.getTime() + "\n\n";
				byte[] b = text.getBytes();
				outputStream.write(b);
				outputStream.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �����ļ�Ŀ¼
	 * @param url
	 */
	public static void createDirectory (String url){
		File path = new File(url);
		if (!path.exists()) {
			path.mkdirs();
		}
	}
	
	
	/**
	 * ��ȡӦ���⻺��·��
	 * 
	 * @return ·�����ַ���
	 */
	public static String getExterCachePath() {
		return Myapplication.getInstance().getExternalCacheDir().getPath().toString();
	}
	
	/**
	 * ��ȡӦ���ڻ���·��
	 * 
	 * @return ·�����ַ���
	 */
	public static String getCachePath() {
		return Myapplication.getInstance().getCacheDir().getPath().toString();
	}
	
	/**
	 * �ж��ļ��Ƿ����
	 * @param uri
	 * @return
	 */
	public static boolean isFileExist(String uri){
		try {
			File file = new File(uri);
			if (file.exists()) {
				return true;
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return false;
	}
	
	 /**
     * ɾ���ļ�������������ļ�
     * @param path String �ļ���·�� 
     */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
		}
	}
	
	
	/**
	 * ɾ���ļ�
	 * @param dirString
	 */
	public static void deleteFile(String dirString){
		File file = new File(dirString);
		if(file.exists() && file.isFile()){
			file.delete();
        }
	}

	/**
	 * ɾ��Ŀ¼
	 * @param dirString
	 */
	public static void deleteDir(String dirString) {
		File dir = new File(dirString);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;
		
		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete(); 
			else if (file.isDirectory())
				deleteDir(file.getAbsolutePath()); 
		}
		dir.delete();
	}

}
