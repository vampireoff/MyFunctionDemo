package com.function.utils;

import org.xutils.cache.LruCache;

import android.graphics.Bitmap;

import com.android.volley.toolbox.ImageLoader.ImageCache;

/**
 * volleyÕº∆¨ª∫¥Ê≈‰÷√¿‡
 * @author Administrator
 *
 */
public class MyBitmapCache implements ImageCache{

	private LruCache<String, Bitmap> lruCache;
	
	public MyBitmapCache(){
		int maxsize = 5 * 1024 * 1024;
		lruCache = new LruCache<String, Bitmap>(maxsize){
			@Override
			protected int sizeOf(String key, Bitmap value) {
				// TODO Auto-generated method stub
				return value.getRowBytes() * value.getHeight();
			}
		};
	}
	
	@Override
	public Bitmap getBitmap(String url) {
		// TODO Auto-generated method stub
		return lruCache.get(url);
	}

	@Override
	public void putBitmap(String url, Bitmap bitmap) {
		// TODO Auto-generated method stub
		lruCache.put(url, bitmap);
	}

}
