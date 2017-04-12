package com.hq.scrati.common.util;
/**
 * 
 * @Author Zale
 * @Date 2016/11/28 下午8:17
 *
 */
public class HtmlUtil {

	/**
	 * 将字符串中的特殊符号转义成HTML字符
	 * @param str
	 * @return
	 */
	public static String toHtml(String str) {
		if (str==null || "".equals(str)) return str;
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<str.length();i++) {
			char c = str.charAt(i);
			if (c=='<') {
				sb.append("&lt;");
				continue;
			}
			if (c=='>') {
				sb.append("&gt;");
				continue;
			}
			if (c=='&') {
				sb.append("&amp;");
				continue;
			}
			if (c=='"') {
				sb.append("&quot;");
				continue;
			}
			sb.append(c);
		}
		return sb.toString();
	}


}
