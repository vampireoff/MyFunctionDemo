package com.function.activity;

import android.os.Bundle;
import android.view.View;

import com.function.views.FavorLayout;

/**
 * 点赞效果界面
 * @author Administrator
 *
 */
public class LoveFlyActivity extends BaseActivity {

	FavorLayout favorLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		favorLayout = new FavorLayout(context);
		setContentView(favorLayout);
		favorLayout.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		super.onClick(v);
		favorLayout.addfavor();
	}
	
}
