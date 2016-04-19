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
 * xutils��ʹ��
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
		
		//����һ�����һ��FragmentTransaction��ʵ��
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		//���������add()��������Fragment�Ķ���rightFragment 
		transaction.replace(R.id.mainview, new ImageFragment());
		//������������commit()����ʹ��FragmentTransactionʵ���ĸı���Ч
		transaction.commit();     
		
		imageOptions = new ImageOptions.Builder()
		.setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
		.setRadius(DensityUtil.dip2px(5))
		// ���ImageView�Ĵ�С���Ƕ���Ϊwrap_content, ��Ҫcrop.
		.setCrop(true)
		// �����л����ͼƬ��ScaleType
		//.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
		.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
		//���ü��ع����е�ͼƬ
		.setLoadingDrawableId(R.drawable.ic_launcher)
		//���ü���ʧ�ܺ��ͼƬ
		.setFailureDrawableId(R.drawable.ic_launcher)
		//����ʹ�û���
		.setUseMemCache(true)
		//����֧��gif
		.setIgnoreGif(false)
		//������ʾԲ��ͼƬ
//	      .setCircular(false)
		.build();
	}

	@Event(value={R.id.img})
    private void loadImage(View view){
		Toast.makeText(context, "����ͼƬ", 2000).show();
    }
    
    @Event(value={R.id.button1,R.id.button}, type=View.OnClickListener.class) 
    private void onClick(View view){
        //����Ϊprivate 
        switch (view.getId()) {
        case R.id.button:
        	x.image().bind(imageView, "http://pica.nipic.com/2007-11-09/200711912453162_2.jpg", imageOptions);
        	Toast.makeText(context, "����btn", 2000).show();
            break;
        case R.id.button1:
        	x.image().bind(imageView, "http://image.photophoto.cn/m-6/Animal/Amimal%20illustration/0180300271.jpg", imageOptions);
        	Toast.makeText(context, "����btn1", 2000).show();
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
