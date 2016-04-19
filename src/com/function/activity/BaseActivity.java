package com.function.activity;

import org.xutils.x;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BaseActivity extends Activity implements OnClickListener{

	protected Context context = BaseActivity.this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		x.view().inject(this);
	}
	
	/**
	 * 根据id获取控件
	 * @param id
	 * @return
	 */
	public TextView getTextView(int id){
		return (TextView)findViewById(id);
	}
	
	public ListView getListView(int id){
		return (ListView)findViewById(id);
	}
	
	public Button getButton(int id){
		return (Button)findViewById(id);
	}
	
	public ImageView getImageView(int id){
		return (ImageView)findViewById(id);
	}
	
	public EditText getEditText(int id){
		return (EditText)findViewById(id);
	}
	
	public View getView(int id){
		return (View)findViewById(id);
	}
	
	public CheckBox getCheckbox(int id){
		return (CheckBox)findViewById(id);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
