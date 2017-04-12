package com.hq.scrati.common.util;

import com.hq.scrati.common.util.validation.Validation;
import org.apache.commons.lang.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;

public class Convert {
	/*
	 * convert the IPv4 string to a integer array.
	 * @param c : IPv4 string
	 * @return int[]
	 */
	public static int[] ipv4ToIntArray(String c) {
		if (StringUtils.isEmpty(c))
			return null;
		Matcher match = Validation.REX_IPV4_PATTERN.matcher(c);
		if (match.matches()) {
			int ip1 = Integer.parseInt(match.group(1));
			int ip2 = Integer.parseInt(match.group(2));
			int ip3 = Integer.parseInt(match.group(3));
			int ip4 = Integer.parseInt(match.group(4));
			if (ip1 < 0 || ip1 > 255 || ip2 < 0 || ip2 > 255 || ip3 < 0
					|| ip3 > 255 || ip4 < 0 || ip4 > 255)
				return null;
			else
				return new int[] { ip1, ip2, ip3, ip4 };
		} else {
			return null;
		}
	}
	//字符串转时间
	public static Date stringToDate(String c, String format){
		SimpleDateFormat f = new SimpleDateFormat(format);
		try {
			return f.parse(c);
		} catch (ParseException e) {
			return null;
		}
	}
	//时间转字符串
	public static String dateToString(Date c, String format){
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(c);
	}
	
	public static final int ASCII_ZERO = 48;
	public static final int SEX_INDEX = 16;
	public static final int BIRTHDAY_START_INDEX = 6;
	public static final int BIRTHDAY_END_INDEX = 14;
	
	public static int autoAnalysisGenderCodeByID(String ID){
		if(Validation.isIdentityCardNum(ID)){
			return ID.charAt(SEX_INDEX) - ASCII_ZERO;
		}else{
			return -1;
		}
	}
	
	public static String autoAnalysisBirthdayByID(String ID){
		if(Validation.isIdentityCardNum(ID)){
			return ID.substring(BIRTHDAY_START_INDEX, BIRTHDAY_END_INDEX);
		}else{
			return null;
		}
	}
}
