package com.function.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.function.activity.R;
import com.function.config.Myapplication;

/**
 * ��������
 * @author Administrator
 *
 */
public class MyAnimationUtil {

	private static Animation hyperspaceJumpAnimation;
	
	/**
	 * ��ʱ����ת
	 * @param view
	 */
	public static void startRotate_n(View view){
		//��ת����
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Myapplication.getInstance().getApplicationContext(), R.anim.rotate2);
		hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
		//ʹ��View��ʾ����
		view.startAnimation(hyperspaceJumpAnimation);
	}
	
	/**
	 * ˳ʱ����ת
	 * @param view
	 */
	public static void startRotate_s(View view){
		//��ת����
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Myapplication.getInstance().getApplicationContext(), R.anim.rotate);
		hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
		//ʹ��View��ʾ����
		view.startAnimation(hyperspaceJumpAnimation);
	}
	
}
