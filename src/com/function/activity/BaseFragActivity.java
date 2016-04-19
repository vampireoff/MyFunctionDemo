package com.function.activity;

import org.xutils.x;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseFragActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		x.view().inject(this);
	}
}
