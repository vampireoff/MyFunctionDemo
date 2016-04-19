package com.function.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.function.activity.R;
import com.function.config.Common;
/**
 * ͼƬ��������
 * @author Administrator
 *
 */
public class MyImageUtil {
	
	private static MyImageUtil myImageUtil;
	
	public static MyImageUtil getInstance() {
		if (myImageUtil == null) {
			synchronized (MyImageUtil.class) {
				if (myImageUtil == null) {
					myImageUtil = new MyImageUtil();
				}
			}
		}
		return myImageUtil;
	}
	
	/**
	 * ����bitmap������
	 * @param bm
	 * @param picName
	 * @return
	 */
	public static boolean saveBitmap(Bitmap bm, String picurl) {
		try {
			File f = new File(picurl); 
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
     * ��ȡͼƬ���ԣ���ת�ĽǶ�
     * @param path ͼƬ����·��
     * @return degree��ת�ĽǶ�
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /**
     * ��תͼƬ
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //��תͼƬ ����
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // �����µ�ͼƬ
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    /**
     * �ж�ͼƬ��ת�ǶȲ�Ϊ0����תͼƬ�Ƕ�Ϊ0
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap ReadangleRotaingImage(String pathName) {
    	Bitmap bitmap = BitmapFactory.decodeFile(pathName);
    	int angle = readPictureDegree(pathName);
    	if (angle != 0) {
    		//��תͼƬ ����
    		Matrix matrix = new Matrix();
    		matrix.postRotate(angle);
    		System.out.println("angle2=" + angle);
    		// �����µ�ͼƬ
    		bitmap = Bitmap.createBitmap(bitmap, 0, 0, 
    				bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
    	return bitmap;
    }

	
	/**
	 * �µ�ѹ��ͼƬ�ķ���
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static Bitmap CpImageSize_new(String path) throws IOException {
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(
				new File(path)));
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(in, null, options);
		in.close();
		int i = 0;
		Bitmap bitmap = null;
		while (true) {
			if ((options.outWidth >> i <= 1000)
					&& (options.outHeight >> i <= 1000)) {
				in = new BufferedInputStream(
						new FileInputStream(new File(path)));
				options.inSampleSize = (int) Math.pow(2.0D, i);
				options.inJustDecodeBounds = false;
				bitmap = BitmapFactory.decodeStream(in, null, options);
				break;
			}
			i += 1;
		}
		return bitmap;
	}
	
	/***
	 * ����ͼƬ������·������ͼƬ������
	 */
	public static boolean getBitMap_real(String imagePath, String locurl)
			throws OutOfMemoryError {
		try {
			URL url = new URL(imagePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			
			if (is == null) {
				return false;
			}
			
			byte[] by = new byte[1024];
			int n;
			FileOutputStream outputStream = new FileOutputStream(locurl);
			
			while ((n = is.read(by)) != -1) {
				outputStream.write(by, 0, n);
			}
			
			outputStream.flush();
			outputStream.close();
			conn.disconnect();
			is.close();
			
			return true;
		} catch (Exception ex) {
			Log.i("gettouxiang", ex.getMessage().toString());
		}
		return false;
	}
	
	/***
	 * ����ͼƬ������·���õ�ͼƬ�� byte����
	 */
	public static byte[] getBitMapbyte(String imagePath)
			throws OutOfMemoryError {
		try {
			URL url = new URL(imagePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream is = conn.getInputStream();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}
			is.close();
			outputStream.close();
			conn.disconnect();
			return outputStream.toByteArray();
		} catch (Exception ex) {
			Log.i("gettouxiang", ex.getMessage().toString());
		}
		return null;
	}
	
	/**
	 * ��byte����תΪbitmap��ѹ��,     **���߳���ִ��**
	 * @param path
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapFromPathWH(String path,int width,int height){
		Bitmap bitmap = null;
		if (path.equals("") || path == null) {
			return null;
		}
		/*
	            �������ǡ����inSampleSize�ǽ��������Ĺؼ�֮һ��BitmapFactory.Options�ṩ����һ����ԱinJustDecodeBounds��
	           ����inJustDecodeBoundsΪtrue��decodeFile��������ռ䣬���ɼ����ԭʼͼƬ�ĳ��ȺͿ�ȣ���opts.width��opts.height��
	           ������������������ͨ��һ�����㷨�����ɵõ�һ��ǡ����inSampleSize��*/
		byte[] data = getBitMapbyte(path);
		
		if (data != null) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeByteArray(data, 0, data.length, opts);
			opts.inSampleSize = 1;
			if(opts.outHeight > height || opts.outWidth > width){
				if(opts.outWidth > opts.outHeight){
					opts.inSampleSize = Math.round((float)opts.outHeight / (float)height);
				}else {
					opts.inSampleSize = Math.round((float)opts.outWidth / (float)width);
				}
			}
			opts.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
			bitmap = getBitmapThumbnail(bitmap, width, height);
		}
		
		if(bitmap != null){
			return compressImage100(bitmap);
		}else {
			return bitmap;
		}
	}
	/**
	 * ����ѹ��
	 * @param bmp
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap getBitmapThumbnail(Bitmap bmp,int width,int height){
		Bitmap bitmap = null;
		if(bmp != null ){
			int bmpWidth = bmp.getWidth();
			int bmpHeight = bmp.getHeight();
			if(width != 0 && height !=0){
				Matrix matrix = new Matrix();
				float scaleWidth = ((float) width / bmpWidth);
				float scaleHeight = ((float) height / bmpHeight);
				matrix.postScale(scaleWidth, scaleHeight);
				bitmap = Bitmap.createBitmap(bmp, 0, 0, bmpWidth, bmpHeight, matrix, true);
			}else{
				bitmap = bmp;
			}
		}
		return bitmap;
	}
	
	/**  �ü�ͼƬ������
	 * @param bitmap      ԭͼ
	 * @return  ���Ž�ȡ���в��ֺ��λͼ��
	 */
	public static Bitmap centerSquareScaleBitmap(Bitmap bitmap){
		if(null == bitmap)
		{
			return  null;
		}
		
		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();
		int edgeLength = widthOrg > heightOrg ? heightOrg : widthOrg;
		
		//ѹ����һ����С������edgeLength��bitmap
		int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
		int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
		int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
		Bitmap scaledBitmap;
		
		try{
			scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
		}
		catch(Exception e){
			return null;
		}
		
		//��ͼ�н�ȡ���м�������β��֡�
		int xTopLeft = (scaledWidth - edgeLength) / 2;
		int yTopLeft = (scaledHeight - edgeLength) / 2;
		
		try{
			result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
			scaledBitmap.recycle();
		}
		catch(Exception e){
			return null;
		}       
		
		return compressImage100(result);
	}
	
	/**
	 *  �ü�ͼƬ������
	 * @param bitmap      ԭͼ
	 * @param edgeLength  ϣ���õ��������β��ֵı߳�
	 * @return  ���Ž�ȡ���в��ֺ��λͼ��
	 */
	public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength){
		if(null == bitmap || edgeLength <= 0)
		{
			return  null;
		}
		
		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();
		
		//ѹ����һ����С������edgeLength��bitmap
		int longerEdge = (int)(edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
		int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
		int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
		Bitmap scaledBitmap;
		
		try{
			scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
		}
		catch(Exception e){
			return null;
		}
		
		//��ͼ�н�ȡ���м�������β��֡�
		int xTopLeft = (scaledWidth - edgeLength) / 2;
		int yTopLeft = (scaledHeight - edgeLength) / 2;
		
		try{
			result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
			scaledBitmap.recycle();
		}
		catch(Exception e){
			return null;
		}       
		
		return compressImage100(result);
	}
	  
	/**
	 * Բ��ͼƬ
	 * @param source
	 * @param radius
	 * @return
	 */
	public static Bitmap roundCorners(final Bitmap bitmap, final float pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/**
	 * ͼƬ����64λ����
	 * @param image
	 * @param kb ��0����ѹ������0��ѹ����ָ����С����λkb��
	 * @return
	 */
	public static String setBitmap2Base64(Bitmap image, int kb) {  
		if (image == null) {
			return "";
		}
		String string = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		int options = 90;  
		if (kb == 0) {
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
		}else {
			
			while ( (baos.toByteArray().length / 1024) > kb) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��         
				baos.reset();//����baos�����baos  
				options -= 10;//ÿ�ζ�����10  
				image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
			}  
		}
		string = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
		return string.replace("+", "%2B"); 
	}  
	
	/**
	 * ��base64ת����bitmap
	 * @param string
	 * @return
	 */
	public static Bitmap getBitmapFromBase64(String string)
	{
		byte[] bitmapArray = null;
		try {
			bitmapArray = Base64.decode(string, Base64.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
	}
	
	/**
	 * ����ѹ����100k����
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage100(Bitmap image) {  
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
		int options = 90;  
		while ( (baos.toByteArray().length / 1024) > 100) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����200kb,���ڼ���ѹ��         
			baos.reset();//����baos�����baos  
			options -= 10;//ÿ�ζ�����10  
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
		}  
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��  
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ  
		return bitmap;  
	}  
	
	/**
	 * ����ѹ��
	 * @param image
	 * @return
	 */
	public static Bitmap getBitMap2(Bitmap image){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();         
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
		int opp = 80;
		while( (baos.toByteArray().length / 1024)>200) {//�ж����ͼƬ����500k,����ѹ������������ͼƬ��BitmapFactory.decodeStream��ʱ���    
			baos.reset();//����baos�����baos  
			opp -= 10;
			image.compress(Bitmap.CompressFormat.JPEG, opp, baos);//����ѹ��50%����ѹ��������ݴ�ŵ�baos��  
		}  
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
		BitmapFactory.Options newOpts = new BitmapFactory.Options();  
		//��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��  
		newOpts.inJustDecodeBounds = true;  
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
		newOpts.inJustDecodeBounds = false;  
//	    int w = newOpts.outWidth;  
//	    int h = newOpts.outHeight;  
//	    float hh = Config.width * 4 / 8;
//		float ww = Config.width / 2;
//		int be = (int) ((w/ww + h/hh) / 2);
//	    if (be < 1)  
//	        be = 1;  
		if((baos.toByteArray().length / 1024)>1000)
			newOpts.inSampleSize = 2;//�������ű���  
		else {
			newOpts.inSampleSize = 1;//�������ű���  
		}
		//���¶���ͼƬ��ע���ʱ�Ѿ���options.inJustDecodeBounds ���false��  
		isBm = new ByteArrayInputStream(baos.toByteArray());  
		bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
		return bitmap;//ѹ���ñ�����С���ٽ�������ѹ��  
		
	}
	
	
	/**
	 * ��ȡͼƬ����ѹ����200k���ڵĲ���
	 * @param image
	 * @return
	 */
	public static int compressOption200(Bitmap image) {  
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��  
		int options = 90;  
		while ( (baos.toByteArray().length / 1024) > 200) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����200kb,���ڼ���ѹ��         
			baos.reset();//����baos�����baos  
			options -= 10;//ÿ�ζ�����10  
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��  
		}  
		return options;  
	}  
	
	
	/**
	 * ����bitmap������
	 * @param imgName  ͼƬ��
	 * @param bmp	bitmap
	 * @param diskPath	����·��
	 */
	public static void MysaveBitmapToDisk(String imgName, Bitmap bmp,
			String diskPath) {
		try {
//			Bitmap bmp = getBitMap(networkPath, false);
			if (bmp != null) {
				File file = new File(diskPath);
				if (!file.exists()) {
					file.mkdirs();
				}
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(diskPath + imgName));
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
				bos.flush();
				bos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ���һ�ű��ص�ͼƬ
	 * 
	 * @param path
	 *            ����ͼƬ·��
	 * @return bitmap ͼƬ
	 */
	public static Bitmap getDiskBitmap(String path) {
		Bitmap bmp = null;
		try {
			File file = new File(path);
			if (file.exists()) {
				bmp = BitmapFactory.decodeFile(path);
			}
		} catch (OutOfMemoryError ex) {
//			logger.info("get the start_page out of the memeory");
		} catch (Exception e) {
//			logger.info("get disk image bitmap error=" + e.getMessage());
		}
		return bmp;
	}
	
	/**
	 * ��ȡ·���е�ͼƬ������ָ������������ͼƬλ�ã�Ȼ�󱣴�Ϊjpg��ʽ
	 * 
	 * @param path
	 *            ͼƬ��·��
	 * @param size
	 *            ����ָ������
	 * @return ͼƬ��·��
	 */
	public String saveBefore22(String path, float size) {
		try {
			int degree = 0;
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inSampleSize = 3;
			Bitmap bitmap = BitmapFactory.decodeFile(path, options);
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Matrix matrix = new Matrix();
			matrix.postRotate(degree);
			matrix.postScale(size, size);
			Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
			bitmap.recycle();
			
//			return saveJPGE_After(newbmp, path);
		} catch (Exception e) {
		}
		return path;
	}
	
	/**
	 * ��ȡuri��ͼƬ������ָ������������ͼƬλ�ã�Ȼ����jpg��ʽ���浽path��
	 * 
	 * @param uri
	 *            ͼƬ������е�uri
	 * @param path
	 *            ͼƬ�ı���·��
	 * @return
	 */
	public String saveBefore33(String uri, String path) {
		float size = 0;
		String imgName = "/albumSendTopic.jpg";
		String newPath = null;
		
		if (checkSize(uri, 1)) {
			size = (float) 0.25;
		} else {
			size = (float) 0.5;
		}
		
		try {
			int degree = 0;
			ExifInterface exifInterface = new ExifInterface(uri);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
			Bitmap bitmap = BitmapFactory.decodeFile(uri);
			
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Matrix matrix = new Matrix();
			matrix.postRotate(degree);
			matrix.postScale(size, size);
			Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
					true);
			bitmap.recycle();
			
			File file = null;
			if (path != null) {
				file = new File(Environment.getExternalStorageDirectory(), path);
				newPath = path + imgName;
			} else {
				file = new File(Environment.getExternalStorageDirectory(),
						"style13/imageCache");
				newPath = Environment.getExternalStorageDirectory().toString()
						+ "/style13/imageCache" + imgName;
			}
			
			if (!file.exists()) {
				file.mkdirs();
			}
			
			File file2 = new File(newPath);
			FileOutputStream out = new FileOutputStream(file2);
			
			if (newbmp.compress(Bitmap.CompressFormat.JPEG, 100, out)) {
				out.flush();
				out.close();
			}
			newbmp.recycle();
			return newPath;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * �ж��ļ��Ƿ����
	 * @param uri
	 * @return
	 */
	public static boolean isHaveFile(String uri){
		File file = new File(uri);
		if (file.exists()) {
			return true;
		}
		return false;
	}
	
	/**
	 * ����bitmapΪPNG
	 * 
	 * @param bitmap
	 * @param path
	 *            �����·��
	 * @return �����ͼƬ��·��
	 */
	public static void savePNG(Bitmap bitmap, String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
				out.flush();
				out.close();
			}
//			bitmap.recycle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * ����bitmapΪjpg(ѹ��200k)
	 * 
	 * @param bitmap
	 * @param path
	 *            �����·��
	 * @return �����ͼƬ��·��
	 */
	public static void saveJPEG(Bitmap bitmap, String path) {
		if (bitmap == null) {
			return;
		}
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
//			if (Common.imgoption == 100) {
//				Common.imgoption = compressOption200(bitmap);
//			}
			if (bitmap.compress(Bitmap.CompressFormat.JPEG, Common.imgoption, out)) {
				out.flush();
				out.close();
			}
//			bitmap.recycle();
//			System.gc();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����ͼƬΪ��Ļ�Ŀ��(С��150 ���Ŵ�150)
	 * 
	 * @param bitmap
	 * @param windowWinth
	 *            ��Ļ���
	 * @return
	 */
	public Bitmap zoomBitmap2(Bitmap bitmap, int windowWinth) {
		try {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			
			Log.i("image handling", "w = " + w + "  h = " + h);
			
			if (w < 150) { // С��150 ���Ŵ�150
				Matrix matrix = new Matrix();
				float scaleWidth = ((float) 150 / w);
				float scaleHeight = ((float) 150 / w);
				matrix.postScale(scaleWidth, scaleHeight);
				Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
						true);
				return newbmp;
			} else { // ����150�����ŵ���Ļ��С
				Matrix matrix = new Matrix();
				float scaleWidth = ((float) windowWinth / w);
				float scaleHeight = ((float) windowWinth / w);
				
				matrix.postScale(scaleWidth, scaleHeight);
				Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
						true);
				return newbmp;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * ����ͼƬΪ��Ļ�Ŀ��(����ͼƬ��С)
	 * 
	 * @param bitmap
	 * @param windowWinth
	 *            ��Ļ���
	 * @return
	 */
	public Bitmap zoomBitmap3(Bitmap bitmap, int windowWinth) {
		try {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			
			Log.i("image handling", "w = " + w + "  h = " + h);
			
			Matrix matrix = new Matrix();
			float scaleWidth = ((float) windowWinth / w);
			float scaleHeight = ((float) windowWinth / w);
			
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
					true);
			return newbmp;
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * ����ͼƬΪָ���ĸ߶�
	 * 
	 * @param bitmap
	 * @param width
	 *            0=������
	 * @param height
	 *            0=������
	 * @return
	 */
	public Bitmap zoomBitmap2Height(Bitmap bitmap, int height) {
		try {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();
			Log.i("image handling", "w = " + w + "  h = " + h + " height = "
					+ height);
			
			Matrix matrix = new Matrix();
			float scaleWidth = ((float) height / h);
			float scaleHeight = ((float) height / h);
			matrix.postScale(scaleWidth, scaleHeight);
			Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix,
					true);
			
			return newbmp;
		} catch (Exception e) {
		}
		return bitmap;
	}
	
	/**
	 * ��֤ͼƬ��С�Ƿ����ָ����С
	 * 
	 * @param path
	 *            ͼƬ��·��
	 * @param size
	 *            ָ����С MB
	 * @return �Ƿ����
	 */
	public boolean checkSize(String path, float size) {
		InputStream is = null;
		try {
			is = new FileInputStream(path);
			float s = is.available();
			Log.i("image size = ", s / 1024 + "kb");
			Log.i("image size = ", s / 1024 / 1024 + "mb");
			
			return s / 1024 / 1024 > size;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
		return false;
	}
	
	/**
	 * ��drawable����ΪԲ��bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public Bitmap getRoundBitmap(Drawable drawable) {
		Bitmap bmp = drawableToBitmap(drawable);
		
		if (bmp != null) {
			int w = bmp.getWidth();
			int pixels = w / 10;
			return toRoundCorner(bmp, pixels);
		} else {
			return null;
		}
	}
	
	/**
	 * drawable to bitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public Bitmap drawableToBitmap(Drawable drawable) {
		if (drawable != null) {
			Bitmap bitmap = Bitmap
					.createBitmap(
							drawable.getIntrinsicWidth(),
							
							drawable.getIntrinsicHeight(),
							
							drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
									
									: Bitmap.Config.RGB_565);
			
			Canvas canvas = new Canvas(bitmap);
			
			// canvas.setBitmap(bitmap);
			
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
					drawable.getIntrinsicHeight());
			
			drawable.draw(canvas);
			
			return bitmap;
		} else {
			return null;
		}
		
	}
	
	/***
	 * ���ݴ����ַ����ͼ����ԴID
	 */
	public int getDrawId(String icon) {
		int i = 0;
		try {
			Field field = R.drawable.class.getField(icon);
			i = field.getInt(new R.drawable());
		} catch (Exception ex) {
		}
		return i;
	}
	
	
	/**
	 * Բ�Ǵ���ͼƬ
	 * 
	 * @param bitmap
	 * @param pixels
	 * @return
	 */
	public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), android.graphics.Bitmap.Config.ARGB_4444);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		final float roundPx = pixels;
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}
	
	/*************
	 * setimagebitmap��Ӧ�ؼ���С
	 * 
	 * @param target
	 * @param TARGET_WIDTH
	 * @param TARGET_HEIGHT
	 * @return
	 */
	public  Bitmap zoomBitmap(Bitmap target, int TARGET_WIDTH,
			int TARGET_HEIGHT) {
		int width = target.getWidth();
		int height = target.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) TARGET_WIDTH) / width;
		float scaleHeight = ((float) TARGET_HEIGHT) / height;
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap result = Bitmap.createBitmap(target, 0, 0, width, height,
				matrix, true);
		return result;
	}
	
	/*********
	 * �Ƚ�ͼƬ��С��������ȱȽϳ�����ô���ճ��������䣬����߶ȱȽϸߣ����ո߶�������
	 */
	public  Bitmap toAutoSize(Bitmap image, float ww, float hh) {
		if (image != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
			newOpts.inJustDecodeBounds = true;
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
//			logger.info("�õ���hh��ֵΪ" + hh + "��ww��ֵΪ" + ww);
			// ���űȡ������ǹ̶��������ţ�ֻ�ø߻��߿�����һ�����ݽ��м��㼴��
			if (w < ww && w >= h) {
				float be = ww / w;
				h = (int) be * h;
				image = zoomBitmap(image, (int) ww, h);
			} else if (w < h && h <= hh) {
				// ����߶ȸߵĻ����ݿ�ȹ̶���С����
				float be = hh / h;
				w = (int) be * w;
				image = zoomBitmap(image, w, (int) hh);
			}
		} else {
//			logger.error("Ҫ������ͼƬΪnull��");
		}
		return image;
	}
	
	/*********
	 * ���ݿ������Ӧ��С
	 */
	public  Bitmap toAutoSizeWithWid(Bitmap image, float ww, float hh) {
		if (image != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			BitmapFactory.Options newOpts = new BitmapFactory.Options();
			// ��ʼ����ͼƬ����ʱ��options.inJustDecodeBounds ���true��
			newOpts.inJustDecodeBounds = true;
			newOpts.inJustDecodeBounds = false;
			int w = newOpts.outWidth;
			int h = newOpts.outHeight;
			if (w < ww) {
				float be = ww / w;
				h = (int) be * w;
				image = zoomBitmap(image, (int) ww, h);
			} else {
				float be = w / ww;
				h = (int) (hh * be);
				image = zoomBitmap(image, (int) ww, h);
			}
		} else {
//			logger.error("Ҫ������ͼƬΪnull��");
		}
		return image;
	}
	
	// ��ͼƬת���ɻ�ɫ��ͼƬ
	public  Bitmap toGrayscale(Bitmap bmpOriginal) {
		int width, height;
		height = bmpOriginal.getHeight();
		width = bmpOriginal.getWidth();
		Bitmap bmpGrayscale = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
		Canvas c = new Canvas(bmpGrayscale);
		Paint paint = new Paint();
		ColorMatrix cm = new ColorMatrix();
		cm.setSaturation(0);
		ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
		paint.setColorFilter(f);
		c.drawBitmap(bmpOriginal, 0, 0, paint);
		return bmpGrayscale;
	}
}