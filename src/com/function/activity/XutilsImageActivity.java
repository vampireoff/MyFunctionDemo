package com.function.activity;

import org.xutils.x;
import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * xutils的使用
 * @author Administrator
 *
 */
@SuppressLint("ShowToast")
@ContentView(R.layout.activity_xutil)
public class XutilsImageActivity extends BaseFragActivity {

	private Context context;
	
	@ViewInject(R.id.button)
	private Button button;
	
	@ViewInject(R.id.button1)
	private Button button1;
	
	@ViewInject(R.id.img)
    private ImageView imageView;
	
    ImageOptions imageOptions;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		x.view().inject(this);
		context = this;
		
		//步骤一：添加一个FragmentTransaction的实例
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//步骤二：用add()方法加上Fragment的对象rightFragment 
		transaction.replace(R.id.mainview, new ImageFragment());
		//步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
		transaction.commit();     
		
		imageOptions = new ImageOptions.Builder()
		.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
		.setRadius(DensityUtil.dip2px(5))
		// 如果ImageView的大小不是定义为wrap_content, 不要crop.
		.setCrop(true)
		// 加载中或错误图片的ScaleType
		//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		//设置加载过程中的图片
		.setLoadingDrawableId(R.drawable.ic_launcher)
		//设置加载失败后的图片
		.setFailureDrawableId(R.drawable.ic_launcher)
		//设置使用缓存
		.setUseMemCache(true)
		//设置支持gif
		.setIgnoreGif(false)
		//设置显示圆形图片
//	      .setCircular(false)
		.build();
	}

	@Event(value={R.id.img})
    private void loadImage(View view){
		Toast.makeText(context, "我是图片", 2000).show();
    }
    
    @Event(value={R.id.button1,R.id.button}, type=View.OnClickListener.class) 
    private void onClick(View view){
        //必须为private 
        switch (view.getId()) {
        case R.id.button:
        	x.image().bind(imageView, "http://pica.nipic.com/2007-11-09/200711912453162_2.jpg", imageOptions);
        	Toast.makeText(context, "我是btn", 2000).show();
            break;
        case R.id.button1:
        	x.image().bind(imageView, "http://image.photophoto.cn/m-6/Animal/Amimal%20illustration/0180300271.jpg", imageOptions);
        	Toast.makeText(context, "我是btn1", 2000).show();
            break;

        default:
            break;
        }
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
