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
 * ����Ч������ͼ���̳�relativelayout
 * @author Administrator
 *
 */
public class FavorLayout extends RelativeLayout {

	private int lwidth;		//���Ŀ��
	private int lheight;	//���ĸ߶�
	private int mwidth;		//FavorLayout���
	private int mheight;	//FavorLayout�߶�
	private LayoutParams layoutParams;	//ͼƬ�Ĳ��ֲ���
	private Random random = new Random();	//�����
	private Drawable pink, green, blue;	//����ͼƬ
	private Drawable[] drawables;
	private Interpolator line = new LinearInterpolator();//����
	private Interpolator acc = new AccelerateInterpolator();//����
	private Interpolator dec = new DecelerateInterpolator();//����
	private Interpolator accdec = new AccelerateDecelerateInterpolator();//�ȼ��ٺ����
	private Interpolator[] interpolators;
	
	public FavorLayout(Context context){
		super(context);
		setBackgroundColor(getResources().getColor(R.color.white));//���ñ�����ɫ
		init();
	}
	
	public FavorLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ��ʼ������
	 */
	private void init(){
		
		drawables = new Drawable[3];
		//ȡ����ͼƬ
		pink = getResources().getDrawable(R.drawable.pink);
		green = getResources().getDrawable(R.drawable.green);
		blue = getResources().getDrawable(R.drawable.blue);
		drawables[0] = pink;
		drawables[1] = green;
		drawables[2] = blue;
		
		//��ȡͼƬ���
		lwidth = pink.getIntrinsicWidth();
		lheight = pink.getIntrinsicHeight();
		
		//�ײ�ˮƽ����
		layoutParams = new LayoutParams(lwidth, lheight);
		layoutParams.addRule(CENTER_HORIZONTAL, TRUE);
		layoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
		
		//��ֵ���Ž�����
		interpolators = new Interpolator[4];
		interpolators[0] = line;
		interpolators[1] = acc;
		interpolators[2] = dec;
		interpolators[3] = accdec;
	}
	
	/**
	 * �����ϼ�
	 * @param target
	 * @return
	 */
	private Animator getAnimator(View target){
		AnimatorSet set = getEnterAnimat(target); //���Ŷ���
		ValueAnimator bezierAnimator = getBezier(target); //���������߶���
		AnimatorSet finalset = new AnimatorSet();
		finalset.playSequentially(set);
		finalset.playSequentially(set, bezierAnimator);
		finalset.setInterpolator(interpolators[random.nextInt(4)]); //�������
		finalset.setTarget(target);
		return finalset;
	}
	
	/**
	 * ��ȡ���������߶���
	 * @param target
	 * @return
	 */
	private ValueAnimator getBezier(View target){
		//��ʼ��һ������
		Bezier bezier = new Bezier(getPointF(2), getPointF(1));
		
		//���������ߵ���� �� �յ�
		ValueAnimator animator = ValueAnimator.ofObject(bezier, 
				new PointF((mwidth-lwidth)/2, mheight-lheight), 
				new PointF(random.nextInt(getWidth()),0));
		
		animator.addUpdateListener(new BezierListen(target));
		animator.setTarget(target);
		animator.setDuration(3000);
		return animator;
	}
	
	/**
	 * �����漰������һ������:getPointF(),�������������ȡ;����������
	 * �����ȡֵ�����������,��������ϣ�������Ӿͺ�
	 * @param scale
	 * @return
	 */
	private PointF getPointF(int scale){
		PointF pointF = new PointF();
		
		//��ȥ100 ��Ϊ�˿��� x����Χ,��Ч�� ����~~
		pointF.x = random.nextInt((mwidth - 100));
		
		//��Y���� Ϊ��ȷ���ڶ����� �ڵ�һ����֮��,�Ұ�Y�ֳ����������� ��������Ч����һЩ Ҳ��������������
		pointF.y = random.nextInt((mheight - 100))/scale;
		return pointF;
	}
	
	/**
	 * һ��ʼ�����Ŷ���
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
	 * �����ʾ����
	 */
	public void addfavor(){
		ImageView imageView = new ImageView(getContext());
		imageView.setImageDrawable(drawables[random.nextInt(3)]);
		imageView.setLayoutParams(layoutParams);
		addView(imageView);
		Animator set = getAnimator(imageView);//��ȡ�����ϼ�
		set.addListener(new Aniendlisten(imageView));//���������������Ƴ�����
		set.start();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//��ȡfavorlayoutʵ�ʲ������
		mwidth = getMeasuredWidth();
		mheight = getMeasuredHeight();
	}

	/**
	 * ����������,��㣬�յ㣬;���ĵ�
	 * @author Administrator
	 *
	 */
	public class Bezier implements TypeEvaluator<PointF>{

		private PointF pointF1;//;����������
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
			PointF pointF00 = (PointF)startValue;//���
			PointF pointF11 = (PointF)endValue;//�յ�
			
			//���������߹�ʽ
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
	 * ��������������
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
			removeView(target);//�Ƴ�������ֹ��view����ֻ������
		}
	}
	
	/**
	 * �������¼�����
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
			
			//�����ȡ�����������߼�������ĵ�x yֵ ��ֵ��view ���������ð���������������
			PointF pointF = (PointF)animation.getAnimatedValue();
			target.setX(pointF.x);
			target.setY(pointF.y);
			//˳����һ��alpha����,����alpha����Ҳ�����
			target.setAlpha(1-animation.getAnimatedFraction());
		}

	}
}
