package com.function.views;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.function.activity.R;

/**
 * 点赞效果主视图，继承relativelayout
 * @author Administrator
 *
 */
public class FavorLayout extends RelativeLayout {

	private int lwidth;		//爱心宽度
	private int lheight;	//爱心高度
	private int mwidth;		//FavorLayout宽度
	private int mheight;	//FavorLayout高度
	private LayoutParams layoutParams;	//图片的布局参数
	private Random random = new Random();	//随机数
	private Drawable pink, green, blue;	//爱心图片
	private Drawable[] drawables;
	private Interpolator line = new LinearInterpolator();//线性
	private Interpolator acc = new AccelerateInterpolator();//加速
	private Interpolator dec = new DecelerateInterpolator();//减速
	private Interpolator accdec = new AccelerateDecelerateInterpolator();//先加速后减速
	private Interpolator[] interpolators;
	
	public FavorLayout(Context context){
		super(context);
		setBackgroundColor(getResources().getColor(R.color.white));//设置背景颜色
		init();
	}
	
	public FavorLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 初始化变量
	 */
	private void init(){
		
		drawables = new Drawable[3];
		//取爱心图片
		pink = getResources().getDrawable(R.drawable.pink);
		green = getResources().getDrawable(R.drawable.green);
		blue = getResources().getDrawable(R.drawable.blue);
		drawables[0] = pink;
		drawables[1] = green;
		drawables[2] = blue;
		
		//获取图片宽高
		lwidth = pink.getIntrinsicWidth();
		lheight = pink.getIntrinsicHeight();
		
		//底部水平居中
		layoutParams = new LayoutParams(lwidth, lheight);
		layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
		layoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
		
		//插值器放进数组
		interpolators = new Interpolator[4];
		interpolators[0] = line;
		interpolators[1] = acc;
		interpolators[2] = dec;
		interpolators[3] = accdec;
	}
	
	/**
	 * 动画合集
	 * @param target
	 * @return
	 */
	private Animator getAnimator(View target){
		AnimatorSet set = getEnterAnimat(target); //缩放动画
		ValueAnimator bezierAnimator = getBezier(target); //贝塞尔曲线动画
		AnimatorSet finalset = new AnimatorSet();
		finalset.playSequentially(set);
		finalset.playSequentially(set, bezierAnimator);
		finalset.setInterpolator(interpolators[random.nextInt(4)]); //随机变速
		finalset.setTarget(target);
		return finalset;
	}
	
	/**
	 * 获取贝塞尔曲线动画
	 * @param target
	 * @return
	 */
	private ValueAnimator getBezier(View target){
		//初始化一条曲线
		Bezier bezier = new Bezier(getPointF(2), getPointF(1));
		
		//传入了曲线的起点 和 终点
		ValueAnimator animator = ValueAnimator.ofObject(bezier, 
				new PointF((mwidth-lwidth)/2, mheight-lheight), 
				new PointF(random.nextInt(getWidth()),0));
		
		animator.addUpdateListener(new BezierListen(target));
		animator.setTarget(target);
		animator.setDuration(3000);
		return animator;
	}
	
	/**
	 * 这里涉及到另外一个方法:getPointF(),这个是我用来获取途径的两个点
	 * 这里的取值可以随意调整,调整到你希望的样子就好
	 * @param scale
	 * @return
	 */
	private PointF getPointF(int scale){
		PointF pointF = new PointF();
		
		//减去100 是为了控制 x轴活动范围,看效果 随意~~
		pointF.x = random.nextInt((mwidth - 100));
		
		//再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些 也可以用其他方法
		pointF.y = random.nextInt((mheight - 100))/scale;
		return pointF;
	}
	
	/**
	 * 一开始的缩放动画
	 * @param target
	 * @return
	 */
	private AnimatorSet getEnterAnimat(final View target){
		ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
		ObjectAnimator scalex = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
		ObjectAnimator scaley = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
		AnimatorSet enter = new AnimatorSet();
		enter.setDuration(500);
		enter.setInterpolator(new LinearInterpolator());
		enter.playTogether(alpha, scalex, scaley);
		enter.setTarget(target);
		return enter;
	}
	
	/**
	 * 添加显示爱心
	 */
	public void addfavor(){
		ImageView imageView = new ImageView(getContext());
		imageView.setImageDrawable(drawables[random.nextInt(3)]);
		imageView.setLayoutParams(layoutParams);
		addView(imageView);
		Animator set = getAnimator(imageView);//获取动画合集
		set.addListener(new Aniendlisten(imageView));//监听动画结束，移除爱心
		set.start();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//获取favorlayout实际测量宽高
		mwidth = getMeasuredWidth();
		mheight = getMeasuredHeight();
	}

	/**
	 * 贝塞尔曲线,起点，终点，途径的点
	 * @author Administrator
	 *
	 */
	public class Bezier implements TypeEvaluator<PointF>{

		private PointF pointF1;//途径的两个点
		private PointF pointF2;
		
		public Bezier(PointF pointF1, PointF pointF2){
			this.pointF1 = pointF1;
			this.pointF2 = pointF2;
		}
		
		@Override
		public PointF evaluate(float time, PointF startValue, PointF endValue) {
			// TODO Auto-generated method stub
			float timeleft = 1.0f - time;
			PointF pointF = new PointF();
			PointF pointF00 = (PointF)startValue;//起点
			PointF pointF11 = (PointF)endValue;//终点
			
			//贝塞尔曲线公式
			pointF.x = timeleft * timeleft * timeleft * (pointF00.x) 
					+ 3*timeleft*timeleft*time*(pointF1.x) 
					+ 3*timeleft*time*time*(pointF2.x) 
					+ time*time*time*(pointF11.x);
			
			pointF.y = timeleft * timeleft * timeleft * (pointF00.y) 
					+ 3*timeleft*timeleft*time*(pointF1.y) 
					+ 3*timeleft*time*time*(pointF2.y) 
					+ time*time*time*(pointF11.y);
			
			return pointF;
		}

	}
	
	/**
	 * 动画结束监听器
	 * @author Administrator
	 *
	 */
	private class Aniendlisten extends AnimatorListenerAdapter{
		private View target;
		
		public Aniendlisten(View tar){
			this.target = tar;
		}
		
		@Override
		public void onAnimationEnd(Animator animation) {
			// TODO Auto-generated method stub
			super.onAnimationEnd(animation);
			removeView(target);//移除掉，防止子view数量只增不减
		}
	}
	
	/**
	 * 动画更新监听器
	 * @author Administrator
	 *
	 */
	public class BezierListen implements ValueAnimator.AnimatorUpdateListener{

		private View target;
		 
		public BezierListen(View tar){
			this.target = tar;
		}
		
		@Override
		public void onAnimationUpdate(ValueAnimator animation) {
			// TODO Auto-generated method stub
			
			//这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
			PointF pointF = (PointF)animation.getAnimatedValue();
			target.setX(pointF.x);
			target.setY(pointF.y);
			//顺便做一个alpha动画,这样alpha渐变也完成啦
			target.setAlpha(1-animation.getAnimatedFraction());
		}

	}
}
