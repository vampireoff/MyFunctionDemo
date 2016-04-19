package com.function.views;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.function.activity.R;
import com.function.utils.MyScreenUtils;

/**
 * 列表对话框(数据少)，列表是List<Map<String, String>>类型
 * @author Administrator
 *
 */
public class MyFewListDialog2{

	private List<Map<String, String>> list;
	public Dialog alertDialog;
	public AlertListCallback2 alertCallback;
	public Activity context;
	private int strid;
	
	public interface AlertListCallback2{
		public void clickitem(int i);
	}
	
	public MyFewListDialog2(Activity mActivity, AlertListCallback2 callback, 
			List<Map<String, String>> list, int stringid){
		this.context = mActivity;
		this.alertCallback = callback;
		this.list = list;
		this.strid = stringid;
	}
	
	public void showMyListDialog() {
		// TODO Auto-generated constructor stub
		View view = LayoutInflater.from(context).inflate(R.layout.deptselect_dialogview, null);
		ListView dialogListView = (ListView)view.findViewById(R.id.dialoglist);
		TextView jtcancle = (TextView)view.findViewById(R.id.jtcanclebtn);
		TextView dtitle = (TextView)view.findViewById(R.id.dtitle);
		
		dtitle.setText(strid);
		alertDialog = new Dialog(context, R.style.alert_dialog);
		alertDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
		WindowManager.LayoutParams params = alertDialog.getWindow().getAttributes();   
        params.width = MyScreenUtils.getWidth(context) - context.getResources().getDimensionPixelSize(R.dimen.dialogpadding);   
        alertDialog.getWindow().setAttributes(params);  
        
        jtcancle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
        dialogListView.setAdapter(new jtadapter(list));
        dialogListView.setOnItemClickListener(new dialogitemclick());
	}

	public class dialogitemclick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			alertCallback.clickitem(position);
			alertDialog.dismiss();
		}
	}
	
	private class jtadapter extends BaseAdapter{

		TextView itemView;
		List<Map<String, String>> alist;
		
		public jtadapter(List<Map<String, String>> alist){
			this.alist = alist;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return alist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = LayoutInflater.from(context).inflate(R.layout.selectview_item, null);
			itemView = (TextView)convertView.findViewById(R.id.select_item);
			itemView.setText(alist.get(position).get("name"));
			return convertView;
		}
		
	}
}
