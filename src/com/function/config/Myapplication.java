package com.function.config;

import org.xutils.x;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.function.utils.MyBitmapCache;

import android.app.Application;

/**
 * ȫ��Ӧ�ó�����
 * @author Administrator
 *
 */
public class Myapplication extends Application {

	public static Myapplication myapplication = null;
	public ImageLoader imageLoader = null;
	
	/**
	 * ��ȡȫ��imageloader
	 * @return
	 */
	public ImageLoader getImageloader(RequestQueue requestQueue){
		if (imageLoader == null) {
			synchronized (Myapplication.class) {
				if (imageLoader == null) {
					imageLoader = new ImageLoader(requestQueue, new MyBitmapCache());
				}
			}
		}
		return imageLoader;
	}
	
	/**
	 * ����ģʽ˫������
	 * @return
	 */
	public static Myapplication getInstance(){
		if (myapplication == null) {
			synchronized (Myapplication.class) {
				if (myapplication == null) {
					myapplication = new Myapplication();
				}
			}
		}
		return myapplication;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		myapplication = this;
		
		// ��ʼ��
		x.Ext.init(this);
		// �����Ƿ����debug
		x.Ext.setDebug(true);
	}
}
