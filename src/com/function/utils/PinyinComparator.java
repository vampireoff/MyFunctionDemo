package com.function.utils;

import java.util.Comparator;
import java.util.Map;
/**
 * Æ´Òô±È½ÏÆ÷
 * @author Administrator
 *
 */
public class PinyinComparator implements Comparator<Map<String, String>>{
	
	@Override
	public int compare(Map<String, String> m1, Map<String, String> m2) {
		// TODO Auto-generated method stub
		String str1 = MyPingYinUtil.getPingYin(m1.get("name"));
		String str2 = MyPingYinUtil.getPingYin(m2.get("name"));
		return str1.compareTo(str2);
	}

}
