package com.function.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.function.activity.R;
import com.function.config.Common;
import com.function.config.Myapplication;

/**
 * �汾���¹���
 * @author Administrator
 *
 */
public class MyVersionUtil {

	private Map<String, String> versionMap;
	private File apkFile;
	public Activity activity;
	private int progress = 0;
	private ProgressDialog progressDialog;
	private static final int DOWNLOAD = 1; /* ������ */
	private static final int DOWNLOAD_FINISH = 2; /* ���ؽ��� */
	private boolean cancelUpdate; /* �Ƿ�ȡ������ */
	private Dialog versionDialog;
	private String apkname;
	private boolean ism;
	
	public MyVersionUtil(Activity activity, Map<String, String> versionMap, boolean ism){
		this.activity = activity;
		this.versionMap = versionMap;
		this.ism = ism;
		
		apkname = versionMap.get("url").substring(versionMap.get("url").lastIndexOf("/") + 1);
		apkFile = new File(Common.apkurl, apkname);
		
		checkversion();
	}
	
	/**
	 * [��ȡӦ�ó���汾������Ϣ]
	 * 
	 * @param context
	 * @return ��ǰӦ�õİ汾����
	 */
	public static String getVersionName(Context context)
	{
		try
		{
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �жϰ汾�Ŵ�С
	 * @param no
	 * @param context
	 * @return
	 */
		public static boolean isUpdate(String no, Context context) {
			int num = getAppVersionCode(context);
			if (num != 0) {
				// �汾�ж�
				if (Integer.parseInt(no) > num) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * ��ȡ�汾��
		 * @param appPackageCode
		 * @return
		 */
		public static int getAppVersionCode(Context context) {
			PackageInfo info = null;
			try {
				info = context.getPackageManager().getPackageInfo(
						Myapplication.getInstance().getPackageName(), 0);
			} catch (NameNotFoundException e) {
				info = null;
				e.printStackTrace();
			}
			if (null == info) {
				return 0;
			}
			return info.versionCode;
		}
	
	/**
	 * ���汾���Ƚϰ汾��
	 */
	public void checkversion(){
		if (isUpdate(versionMap.get("no"), activity)) {
			// ��ʾ��ʾ�Ի���
			showNoticeDialog();
		} else {
			if (!ism) {
				MyUtils.showToast2(activity, "��ǰ�������°汾");
			}
		}
    }
    
    /**
	 * ��ʾ�汾���¶Ի���
	 */
	public void showNoticeDialog(){
		View view = LayoutInflater.from(activity).inflate(R.layout.versiontip_view, null);
		Button vyButton = (Button)view.findViewById(R.id.u_button);
		Button vnButton = (Button)view.findViewById(R.id.nu_button);
		TextView versioninfo = (TextView)view.findViewById(R.id.version_info);
		versioninfo.setText("�汾�ţ�" + versionMap.get("name") + 
				"\n" + "����ʱ�䣺" + versionMap.get("time") + "\n" 
				+ "�������ݣ�" + "\n" + versionMap.get("content"));
		
		versionDialog = new Dialog(activity, R.style.alert_dialog);
		versionDialog.setContentView(view, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
		versionDialog.show();
		versionDialog.setCanceledOnTouchOutside(false);
		WindowManager.LayoutParams params = versionDialog.getWindow()   
                .getAttributes();   
        params.width = MyScreenUtils.getWidth(activity) - activity.getResources().getDimensionPixelSize(R.dimen.versiondialogpadding);   
//        params.height = height - 200;   
        versionDialog.getWindow().setAttributes(params);  
        
		vyButton.setOnClickListener(new yclick());
		
		vnButton.setOnClickListener(new nclick());
	}
	
	public class yclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			versionDialog.dismiss();
			if (apkFile.exists()) {
				//apk������ֱ�Ӱ�װ
				installApk();
			}else {
				//��ʾ���ضԻ���
				showDownloadDialog();
			}
		}
	}
	
	public class nclick implements OnClickListener{
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			versionDialog.dismiss();
		}
	}
	
	/**
	 * ��ʾ������ضԻ���
	 */
	@SuppressLint("NewApi")
	public void showDownloadDialog() {
		// ����������ضԻ���
		progressDialog = new ProgressDialog(activity);
		progressDialog.setTitle("������...");
		progressDialog.setMax(100);
		// ���ý��������STYLE_HORIZONTAL
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgress(0);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		// �����ļ�
		new downloadApkThread().start();
	}

	/**
	 * �����ļ��߳�
	 */
	public class downloadApkThread extends Thread {
		
		@Override
		public void run() {
			try {
				// �ж�SD���Ƿ���ڣ������Ƿ���ж�дȨ��
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					
					URL url = new URL(MyUtils.getIMG_url() + versionMap.get("url"));
					// ��������
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// ��ȡ�ļ���С
					int length = conn.getContentLength();
					// ����������
					InputStream is = conn.getInputStream();

					MyFileUtil.createDirectory(Common.apkurl);
					
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// ����
					byte buf[] = new byte[1024];
					// д�뵽�ļ���
					do {
						int numread = is.read(buf);
						count += numread;
						// ���������λ��
						progress = (int) (((float) count / length) * 100);
						// ���½���
						handler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// �������
							handler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// д���ļ�
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// ���ȡ����ֹͣ����.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// ȡ�����ضԻ�����ʾ
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.cancel();
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// ��������
			case DOWNLOAD:
				// ���ý�����λ��
				if (progressDialog != null && progressDialog.isShowing())
					progressDialog.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// ��װ�ļ�
				installApk();
				break;
			default:
				break;
			}
		};
	};
	
	/**
	 * ��װAPK�ļ�
	 */
	private void installApk() {
		if (!apkFile.exists()) {
			return;
		}
		// ͨ��Intent��װAPK�ļ�
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
				"application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //������䣬��װ������app�Ų���ر�
		activity.startActivity(i);
	}
}
