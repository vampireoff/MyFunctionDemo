package com.function.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

/**
 * Imageloader图片显示工具
 * @author Administrator
 *
 */
public class MyImgloadUtil {

	private ImageLoader loader;
	private DisplayImageOptions options;
	
	public MyImgloadUtil(Context context, int img){
		loader = ImageLoader.getInstance();
		loader.init(ImageLoaderConfiguration.createDefault(context));
		
		//图片参数设置
		options = new DisplayImageOptions.Builder()
		.showStubImage(img)
		.showImageForEmptyUri(img)
		.showImageOnFail(img).cacheOnDisc()
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.displayer(new FadeInBitmapDisplayer(300)).build();
	}
	
	/**
	 * 清理缓存
	 */
	public void clearimg(){
		loader.clearDiscCache();
		loader.clearMemoryCache();
	}
	
	/**
	 * 显示图片
	 * @param url
	 * @param view
	 */
	public void show(String url, ImageView view){
		loader.displayImage(url, view, options);
	}
	
}
