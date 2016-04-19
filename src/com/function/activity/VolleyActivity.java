package com.function.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.function.config.Myapplication;

/**
 * volley框架的使用(只适合频繁的小数据请求，不适合大数据请求)
 * @author Administrator
 *
 */
public class VolleyActivity extends BaseActivity {

	private ImageView imageView, imageView2;
	private NetworkImageView networkImageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volley);
		
		networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
		imageView = getImageView(R.id.img);
		imageView2 = getImageView(R.id.img2);
		
		//请求队列
		RequestQueue requestQueue = Volley.newRequestQueue(context);
		
		//请求字符串
		StringRequest stringRequest = new StringRequest(
				"http://192.168.111.13/CZFood/Module/CZFoodService/CZFoodService.asmx/GetVersion?accessKey=jj&Type=android", 
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// TODO Auto-generated method stub
						Log.i("xxxws", response);
					}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				if (error.getMessage() != null) {
					Log.i("xxxerror", error.getMessage());
				}
			}
		});
		
		//请求json
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
				"http://www.baidu.com", null, 
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						
						Log.i("xxxjson", response.toString());
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						if (error.getMessage() != null) {
							Log.i("xxxjsonerror", error.getMessage());
						}
						
					}
				});
		
		//获取图片
//		ImageRequest imageRequest = new ImageRequest(
//				"http://img10.3lian.com/sc6/show/s11/19/20110711104935563.jpg", 
//				new Response.Listener<Bitmap>() {
//
//					@Override
//					public void onResponse(Bitmap response) {
//						// TODO Auto-generated method stub
//						imageView.setImageBitmap(response);
//					}
//				}, 0, 0, Config.RGB_565, 
//				new Response.ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						// TODO Auto-generated method stub
//						imageView.setImageResource(R.drawable.ic_launcher);
//						
//					}
//				});
		
		//imageloader加载图片
//		ImageLoader imageLoader = new ImageLoader(requestQueue, new MyBitmapCache());
		ImageLoader imageLoader = Myapplication.getInstance().getImageloader(requestQueue);
		
		ImageListener imageListener = ImageLoader.getImageListener(
				imageView2, R.drawable.ic_launcher, R.drawable.ic_launcher);
		
		imageLoader.get("http://pic38.nipic.com/20140210/2844191_211830407122_2.jpg",
				imageListener);
		//可以设置图片大小
//		imageLoader.get("http://pic38.nipic.com/20140210/2844191_211830407122_2.jpg",
//				imageListener, 200, 200);
		
		//NetworkImageView显示图片
		networkImageView.setDefaultImageResId(R.drawable.ic_launcher);
		networkImageView.setErrorImageResId(R.drawable.ic_launcher);
		networkImageView.setImageUrl("http://pic1.nipic.com/2008-09-09/200899144734556_2.jpg",
				imageLoader);
		
		requestQueue.add(stringRequest);
		requestQueue.add(jsonObjectRequest);
//		requestQueue.add(imageRequest);
		
	}
	
}
