package com.function.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64工具类
 * @author Administrator
 *
 */
public class MyBase64Util {
	public final static String	ENCODING = "UTF-8";

	/**
	 *  实现base64编码
	 * @param data
	 * @return
	 */
	public static String encode(String data) {
		try {
			byte[] b = Base64.encodeBase64(data.getBytes(ENCODING));
			return new String(b, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * 实现base64编码，按RFC2045标准执行 （加密后每行有换行符）
	 */
	public static String encodeSafe(String data) {
		try {
			byte[] b = Base64.encodeBase64(data.getBytes(ENCODING), true);
			return new String(b, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return data;
		}
	}

	/**
	 * 实现base64解码
	 * @param data
	 * @return
	 */
	public static String decode(String data) {
		try {
			byte[] b = Base64.decodeBase64(data.getBytes(ENCODING));
			return new String(b, ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return data;
		}
	}
}
