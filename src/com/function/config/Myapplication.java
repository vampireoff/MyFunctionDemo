package com.function.config;

import org.xutils.x;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.function.utils.MyBitmapCache;

import android.app.Application;

/**
 * 全局应用程序类
 * @author Administrator
 *
 */
public class Myapplication extends Application {

	public static Myapplication myapplication = null;
	public ImageLoader imageLoader = null;
	
	/**
	 * 获取全局imageloader
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
	 * 单例模式双重锁定
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
		
		// 初始化
		x.Ext.init(this);
		// 设置是否输出debug
		x.Ext.setDebug(true);
	}
}
