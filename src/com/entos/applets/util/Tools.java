package com.entos.applets.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.entos.applets.docManager.bean.WikisBean;

/**
 * Tools
 * 
 * @author Administrator
 * 
 */
public class Tools {
	/**
	 * java解析json 字符串
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map json(String jsonStr) {
		Map map = new HashMap();
		Pattern p = Pattern.compile("[\\w[^\\[\\],{}]]+");
		Matcher m = p.matcher(jsonStr.replaceAll("'|\"", ""));
		String[] _strs = null;
		while (m.find()) {
			_strs = m.group().split(":");
			if (_strs.length == 2)
				map.put(_strs[0], _strs[1].trim());
		}
		System.out.println(map);
		return map;
	}

	public static WikisBean getParamter(String fileParamStr) {
		WikisBean wikiBean = new WikisBean();
		String[] strs = fileParamStr.split(";");
		if (strs.length > 1) {
			wikiBean.setId(strs[0]);
			wikiBean.setVer(strs[1]);
		}
		return wikiBean;
	}

	public static String splitStr(String str) {
		int index = str.lastIndexOf(".");
		if (index != -1) {
			str = str.substring(0, index);
		}
		return str;
	}

//	public static String insertData(int status, String opt) {
//		String resultJsonStr = "{'status':" + status + ",'opt':'" + opt + "'}";
//		System.out.println(resultJsonStr);
//		return resultJsonStr;
//
//	}
	public static String insertData(Object obj, String path) {
		String resultJsonStr = "{'status':" + obj + ",'path':'" + path + "'}";
		return resultJsonStr;

	}
}
