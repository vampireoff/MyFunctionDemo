package com.function.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 主界面
 * @author Administrator
 *
 */
public class MainActivity extends BaseActivity {

	private ListView listView;
	private List<String> list = new ArrayList<String>();
	private Context context = MainActivity.this;
	private TextView itemText;
	
	/**
	 * 列表项点击
	 */
	OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int item,
				long arg3) {
			// TODO Auto-generated method stub
			switch (item) {
			case 0:
				startActivity(new Intent(context, AlarmTimerActivity.class));
				break;
			case 1:
				startActivity(new Intent(context, VolleyActivity.class));
				break;
			case 2:
				startActivity(new Intent(context, XutilsImageActivity.class));
				break;
			case 3:
				startActivity(new Intent(context, XutilsHttpActivity.class));
				break;
			case 4:
				startActivity(new Intent(context, XutilsDbActivity.class));
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = getListView(R.id.listview);
		list.add("闹钟定时器");
		list.add("Volley");
		list.add("XutilsImage");
		list.add("XutilsHttp");
		list.add("XutilsDB");
		listView.setAdapter(new BaseAdapter() {
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				convertView = LayoutInflater.from(context).inflate(R.layout.item_main, null);
				itemText = (TextView)convertView.findViewById(R.id.itemtext);
				itemText.setText(list.get(position));
				return convertView;
			}
			
			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return list.size();
			}
		});
		
		listView.setOnItemClickListener(listener);
	}
	
}
