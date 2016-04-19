package com.function.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

import com.function.activity.R;
import com.function.config.Myapplication;

/**
 * 动画工具
 * @author Administrator
 *
 */
public class MyAnimationUtil {

	private static Animation hyperspaceJumpAnimation;
	
	/**
	 * 逆时针旋转
	 * @param view
	 */
	public static void startRotate_n(View view){
		//旋转动画
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Myapplication.getInstance().getApplicationContext(), R.anim.rotate2);
		hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
		//使用View显示动画
		view.startAnimation(hyperspaceJumpAnimation);
	}
	
	/**
	 * 顺时针旋转
	 * @param view
	 */
	public static void startRotate_s(View view){
		//旋转动画
		hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				Myapplication.getInstance().getApplicationContext(), R.anim.rotate);
		hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
		//使用View显示动画
		view.startAnimation(hyperspaceJumpAnimation);
	}
	
}
