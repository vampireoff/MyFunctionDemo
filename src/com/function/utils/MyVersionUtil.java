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
 * 版本更新工具
 * @author Administrator
 *
 */
public class MyVersionUtil {

	private Map<String, String> versionMap;
	private File apkFile;
	public Activity activity;
	private int progress = 0;
	private ProgressDialog progressDialog;
	private static final int DOWNLOAD = 1; /* 下载中 */
	private static final int DOWNLOAD_FINISH = 2; /* 下载结束 */
	private boolean cancelUpdate; /* 是否取消更新 */
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
	 * [获取应用程序版本名称信息]
	 * 
	 * @param context
	 * @return 当前应用的版本名称
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
	 * 判断版本号大小
	 * @param no
	 * @param context
	 * @return
	 */
		public static boolean isUpdate(String no, Context context) {
			int num = getAppVersionCode(context);
			if (num != 0) {
				// 版本判断
				if (Integer.parseInt(no) > num) {
					return true;
				}
			}
			return false;
		}
		
		/**
		 * 获取版本号
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
	 * 检查版本，比较版本号
	 */
	public void checkversion(){
		if (isUpdate(versionMap.get("no"), activity)) {
			// 显示提示对话框
			showNoticeDialog();
		} else {
			if (!ism) {
				MyUtils.showToast2(activity, "当前已是最新版本");
			}
		}
    }
    
    /**
	 * 显示版本更新对话框
	 */
	public void showNoticeDialog(){
		View view = LayoutInflater.from(activity).inflate(R.layout.versiontip_view, null);
		Button vyButton = (Button)view.findViewById(R.id.u_button);
		Button vnButton = (Button)view.findViewById(R.id.nu_button);
		TextView versioninfo = (TextView)view.findViewById(R.id.version_info);
		versioninfo.setText("版本号：" + versionMap.get("name") + 
				"\n" + "发布时间：" + versionMap.get("time") + "\n" 
				+ "更新内容：" + "\n" + versionMap.get("content"));
		
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
				//apk存在则直接安装
				installApk();
			}else {
				//显示下载对话框
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
	 * 显示软件下载对话框
	 */
	@SuppressLint("NewApi")
	public void showDownloadDialog() {
		// 构造软件下载对话框
		progressDialog = new ProgressDialog(activity);
		progressDialog.setTitle("下载中...");
		progressDialog.setMax(100);
		// 设置进度条风格STYLE_HORIZONTAL
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.setProgress(0);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

		// 下载文件
		new downloadApkThread().start();
	}

	/**
	 * 下载文件线程
	 */
	public class downloadApkThread extends Thread {
		
		@Override
		public void run() {
			try {
				// 判断SD卡是否存在，并且是否具有读写权限
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					
					URL url = new URL(MyUtils.getIMG_url() + versionMap.get("url"));
					// 创建连接
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.connect();
					// 获取文件大小
					int length = conn.getContentLength();
					// 创建输入流
					InputStream is = conn.getInputStream();

					MyFileUtil.createDirectory(Common.apkurl);
					
					FileOutputStream fos = new FileOutputStream(apkFile);
					int count = 0;
					// 缓存
					byte buf[] = new byte[1024];
					// 写入到文件中
					do {
						int numread = is.read(buf);
						count += numread;
						// 计算进度条位置
						progress = (int) (((float) count / length) * 100);
						// 更新进度
						handler.sendEmptyMessage(DOWNLOAD);
						if (numread <= 0) {
							// 下载完成
							handler.sendEmptyMessage(DOWNLOAD_FINISH);
							break;
						}
						// 写入文件
						fos.write(buf, 0, numread);
					} while (!cancelUpdate);// 点击取消就停止下载.
					fos.close();
					is.close();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 取消下载对话框显示
			if (progressDialog != null && progressDialog.isShowing()) {
				progressDialog.cancel();
			}
		}
	};

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// 正在下载
			case DOWNLOAD:
				// 设置进度条位置
				if (progressDialog != null && progressDialog.isShowing())
					progressDialog.setProgress(progress);
				break;
			case DOWNLOAD_FINISH:
				// 安装文件
				installApk();
				break;
			default:
				break;
			}
		};
	};
	
	/**
	 * 安装APK文件
	 */
	private void installApk() {
		if (!apkFile.exists()) {
			return;
		}
		// 通过Intent安装APK文件
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkFile.toString()),
				"application/vnd.android.package-archive");
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //加上这句，安装过程中app才不会关闭
		activity.startActivity(i);
	}
}
