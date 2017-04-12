package com.hq.scrati.common.util.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hq.scrati.common.util.Convert;


public class Validation {

	public static final Pattern REX_CAPITAL_PATTERN = Pattern.compile("[A-Z]");
	public static final Pattern REX_LOWERCASE_PATTERN = Pattern.compile("[a-z]");
	public static final Pattern REX_DIGITS_PATTERN = Pattern.compile("[0-9]");
	public static final Pattern REX_LETTERANDNUMBER_PATTERN = Pattern.compile("[A-Za-z0-9]+");
	
	// Regex expression: REX_INTEGER
	public static final Pattern REX_INTEGER_PATTERN = Pattern.compile("^-?\\d+$");
	
	//Regex expression: REX_NATURAL_NUMBER
	public static final Pattern REX_NATURAL_NUMBER_PATTERN = Pattern.compile("^\\d+$");

	// Regex expression: REX_BOOLEAN
	public static final Pattern REX_BOOLEAN_PATTERN = Pattern.compile("^0|1|False|True|false|true$");

	// Regex expression: REX_NUMERIC
	public static final Pattern REX_NUMERIC_PATTERN = Pattern.compile("^-?\\d+(\\.\\d+)?$");

	// Regex expression: REX_DATE

	public static final Pattern REX_DATE_PATTERN = Pattern.compile("^\\d{2,4}[-\\/]\\d{1,2}[-\\/]\\d{1,4}$");

	public static final Pattern REX_DATE_YYYYMM_PATTERN = Pattern.compile("^[0-9]{6}$");
	
	public static final Pattern REX_DATE_YYYYMMDD_PATTERN = Pattern.compile("^[0-9]{8}$");

	// Regex expression: REX_DATETIME
	public static final Pattern REX_DATETIME_PATTERN = Pattern.compile("^\\d{2,4}[-\\/]\\d{1,2}[-\\/]\\d{1,4}(?:\\d{1,2}:\\d{1,2}:\\d{1,2})?$");

	// Regex expression: REX_IPV4
	public static final Pattern REX_IPV4_PATTERN = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");

	// Regex expression: REX_IPV4 with *, 192.168.*.*
	public static final Pattern REX_IPV4_ANONYMS_PATTERN = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3}|\\*)\\.(\\d{1,3}|\\*)\\.(\\d{1,3}|\\*)$");

	// Regex expression: REX_EMAIL
	//public static final Pattern REX_EMAIL_PATTERN = Pattern.compile("^[0-9a-zA-Z]+([\\-_\\.][0-9a-zA-Z]+)*\\[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*)+$");
	public static final Pattern REX_EMAIL_PATTERN = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");

	// Regex expression: REX_HTTP
	public static final Pattern REX_HTTP_PATTERN = Pattern.compile("^(REX_HTTP|https):\\/\\/[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*)+(\\:\\d+)?(\\S+)?$");

	// Regex expression: REX_URL
	public static final Pattern REX_URL_PATTERN = Pattern.compile("^[a-zA-Z]+:\\/\\/[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*(\\.[0-9a-zA-Z]+(\\-[0-9a-zA-Z]+)*)+(\\:\\d+)?(\\S+)?$");

	// Regex expression: REX_ID_LIST (e.g. 123,123,123...)
	public static final Pattern REX_ID_LIST_PATTERN = Pattern.compile("^\\d+(?:,\\d+)*$");

	// Regex expression: REX_SAFE_REG_NAME (e.g. block characters
	// '"&lt;&gt;,&amp;\\n\\r\\f\\t)
	public static final Pattern REX_SAFE_REG_NAME_PATTERN = Pattern.compile("^[^'\"<>,&\\n\\r\\f\\t]+$");

	// Regex expression: REX_SAFE_IMG_TYPE (image/gif, image/pjpeg, image/png,
	// image/bmp)
	public static final Pattern REX_SAFE_IMG_TYPE_PATTERN = Pattern.compile("^(?:(?:image/gif)|(?:image/pjpeg)|(?:image/png)|(?:image/bmp))$");

	// Regex expression: REX_SAFE_IMG_EXT (gif|jpeg|jpg|png|bmp)
	public static final Pattern REX_SAFE_IMG_EXT_PATTERN = Pattern.compile("\\.?(gif|jpeg|jpg|png|bmp)$");

	// Regex expression: REX_PHONE_CA (e.g. 123-456-7890 88888)
	public static final Pattern REX_PHONE_CA_PATTERN = Pattern.compile("^\\d{3}\\-\\d{3}\\-\\d{4}( \\d{1,5})?$");

	// Regex expression: REX_POSTALCODE_CA (e.g. M9M 9M9)
	public static final Pattern REX_POSTALCODE_CA_PATTERN = Pattern.compile("^[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]$");

	// Regex expression: REX_PHONE_CN (e.g. 10086,0731-88888888,13800000000)
	public static final Pattern REX_PHONE_CN_PATTERN = Pattern.compile("^(0\\d{2,4}\\-)?[^0]\\d{4,10}(\\-\\d{1,5})?$");

	// Regex expression: REX_POSTALCODE_CA (e.g. 410000)
	public static final Pattern REX_POSTALCODE_CN_PATTERN = Pattern.compile("^\\d{6}$");
	
	// Regex expression: REX_MOBILE (e.g. 13873100000)
	public static final Pattern REX_MOBILE_PHONE_PATTERN = Pattern.compile("^1\\d{10}$");
	//座机
	public static final Pattern REX_FIXED_TELEPHONE_PATTERN = Pattern.compile("^(0[0-9]{2,3}\\-())?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?$");
	
	//车牌号正则表达式
	public static final Pattern REX_LICENSE_PLATE_PATTERN = Pattern.compile("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");
	
	//身份证校验加权因子
	public static final Integer[] ID_NUM_FACTOR = new Integer[] { 7, 9, 10, 5, 8, 4,
			2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	
	//身份证第18位校验码
	public static final String[] ID_NUM_PARITY_BIT = new String[] { "1", "0", "X", "9",
			"8", "7", "6", "5", "4", "3", "2" };
	
	/*
	 * 是否空字符串
	 * 
	 * @param c 文本或其它基本数字类型对象
	 */
	public static boolean isEmpty(Object c) {
		return c == null || c.toString().trim().equals("");
	}
	// 校验
	// @param c 字符串
	// return 异常集合，返回NULL为没有异常
	public static List<Exception> verify(boolean throwException, ValidationItem[] items) throws Exception{
		List<Exception> results = new ArrayList<Exception>();
		for (ValidationItem item : items){
			String msg = null;
			if (!isEmpty(item.getValue())){
				if (item.isRequired()){
					msg = "不能为空！";
				}else if (item.getRule() == ValidationRule.INTEGER){
					if (!isInteger(item.getValue().trim()))
						msg = "不是正确的整数格式！";
					else if (item.getMin() != null && Integer.parseInt(item.getValue()) < Integer.parseInt(item.getMin()))
						msg = "的值不能小于"+item.getMin()+"！";
					else if (item.getMax() != null && Integer.parseInt(item.getValue()) > Integer.parseInt(item.getMax()))
						msg = "的值不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.NUMERIC){
					if (!isNumeric(item.getValue().trim()))
						msg = "不是正确的数字格式！";
					else if (item.getMin() != null && Double.parseDouble(item.getValue()) < Double.parseDouble(item.getMin()))
						msg = "的值不能小于"+item.getMin()+"！";
					else if (item.getMax() != null && Double.parseDouble(item.getValue()) > Double.parseDouble(item.getMax()))
						msg = "的值不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.DATE){
					if (!isDate(item.getValue().trim()))
						msg = "不是正确的日期格式！";
					else if (item.getMin() != null && Convert
							.stringToDate(item.getValue(), "yyyy-MM-dd").before(Convert.stringToDate(item.getMin(), "yyyy-MM-dd")))
						msg = "的值不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && Convert.stringToDate(item.getValue(), "yyyy-MM-dd").after(Convert.stringToDate(item.getMax(), "yyyy-MM-dd")))
						msg = "的值不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.DATETIME){
					if (!isDateTime(item.getValue().trim()))
						msg = "不是正确的时间格式！";
					else if (item.getMin() != null && Convert.stringToDate(item.getValue(), "yyyy-MM-dd hh:mi:ss").before(Convert.stringToDate(item.getMin(), "yyyy-MM-dd hh:mi:ss")))
						msg = "的值不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && Convert.stringToDate(item.getValue(), "yyyy-MM-dd hh:mi:ss").after(Convert.stringToDate(item.getMax(), "yyyy-MM-dd hh:mi:ss")))
						msg = "的值不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.EMAIL){
					if (!isEmail(item.getValue().trim()))
						msg = "不是正确的电子邮箱格式！";
					else if (item.getMin() != null && item.getValue().length() < Integer.parseInt(item.getMin()))
						msg = "的字符长度不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && item.getValue().length() > Integer.parseInt(item.getMax()))
						msg = "的字符长度不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.MOBILE_PHONE){
					if (!isMobilePhone(item.getValue().trim()))
						msg = "不是正确的手机号码格式！";
					else if (item.getMin() != null && item.getValue().length() < Integer.parseInt(item.getMin()))
						msg = "的字符长度不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && item.getValue().length() > Integer.parseInt(item.getMax()))
						msg = "的字符长度不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.PHONE_CN){
					if (!isPhoneCN(item.getValue().trim()))
						msg = "不是正确的电话号码格式！";
					else if (item.getMin() != null && item.getValue().length() < Integer.parseInt(item.getMin()))
						msg = "的字符长度不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && item.getValue().length() > Integer.parseInt(item.getMax()))
						msg = "的字符长度不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.HTTP){
					if (!isPhoneCN(item.getValue().trim()))
						msg = "不是正确的网站址格式！";
					else if (item.getMin() != null && item.getValue().length() < Integer.parseInt(item.getMin()))
						msg = "的字符长度不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && item.getValue().length() > Integer.parseInt(item.getMax()))
						msg = "的字符长度不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.URL){
					if (!isPhoneCN(item.getValue().trim()))
						msg = "不是正确的网页链接格式！";
					else if (item.getMin() != null && item.getValue().length() < Integer.parseInt(item.getMin()))
						msg = "的字符长度不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && item.getValue().length() > Integer.parseInt(item.getMax()))
						msg = "的字符长度不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.SAFE_IMG_EXT){
					if (!isSafeImgExt(item.getValue().trim()))
						msg = "不是正确的图片格式！";
				}else if (item.getRule() == ValidationRule.SAFE_IMG_TYPE){
					if (!isSafeImgType(item.getValue().trim()))
						msg = "不是正确的图片格式！";
				}else if (item.getRule() == ValidationRule.SAFE_REG_NAME){
					if (!isSafeRegName(item.getValue().trim()))
						msg = "不能包含字符：^'\"<>,&\\n\\r\\f\\t";
					else if (item.getMin() != null && item.getValue().length() < Integer.parseInt(item.getMin()))
						msg = "的字符长度不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && item.getValue().length() > Integer.parseInt(item.getMax()))
						msg = "的字符长度不能大于"+item.getMax()+"！";
				}else if (item.getRule() == ValidationRule.BOOLEAN){
					if (!isBoolean(item.getValue().trim()))
						msg = "不是正确的布尔格式！";
				}else if (item.getRule() == ValidationRule.IPV4){
					if (!isBoolean(item.getValue().trim()))
						msg = "不是正确的IP格式！";
				}else if (item.getRule() == ValidationRule.IPV4_ANONYMS){
					if (!isBoolean(item.getValue().trim()))
						msg = "不是正确的IP格式！";
				}else{
					if (item.getMin() != null && item.getValue().length() < Integer.parseInt(item.getMin()))
						msg = "的字符长度不能小于"+item.getMin()+"！";
					else if (item.getMin() != null && item.getValue().length() > Integer.parseInt(item.getMax()))
						msg = "的字符长度不能大于"+item.getMax()+"！";
				}
				if (msg != null){
					Exception exception = new Exception("[" + item.getFieldName() + "]" + msg);
					if (throwException)
						throw exception;
					results.add(exception);
				}
			}
		}
		return results;
	}
	
	public static boolean isMobilePhone(String c) {
		if (isEmpty(c))
			return false;
		return REX_MOBILE_PHONE_PATTERN.matcher(c).matches();
	}
	
	public static boolean isFixedTelephone(String c) {
		if (isEmpty(c))
			return false;
		return REX_FIXED_TELEPHONE_PATTERN.matcher(c).matches();
	}
	
	public static boolean isDateTime(String c) {
		if (isEmpty(c))
			return false;
		return REX_DATETIME_PATTERN.matcher(c).matches();
	}

	public static boolean isInteger(String c) {
		if (isEmpty(c))
			return false;
		return REX_INTEGER_PATTERN.matcher(c).matches();
	}

	/**
	 * isNaturalNumber（自然数：0 + 正整数）
	 * @param c
	 * @return
	 */
	public static boolean isNaturalNumber(Object c) {
		if (isEmpty(c))
			return false;
		return REX_NATURAL_NUMBER_PATTERN.matcher(c.toString()).matches();
	}

	public static boolean isDate(String c) {
		if (isEmpty(c))
			return false;
		return REX_DATE_PATTERN.matcher(c).matches();
	}

	public static boolean isNumeric(String c) {
		if (isEmpty(c))
			return false;
		return REX_NUMERIC_PATTERN.matcher(c).matches();
	}

	public static boolean isEmail(String c) {
		if (isEmpty(c))
			return false;
		return REX_EMAIL_PATTERN.matcher(c).matches();
	}

	public static boolean isHttp(String c) {
		if (isEmpty(c))
			return false;
		return REX_HTTP_PATTERN.matcher(c).matches();
	}

	public static boolean isUrl(String c) {
		if (isEmpty(c))
			return false;
		return REX_URL_PATTERN.matcher(c).matches();
	}

	public static boolean isPhoneCA(String c) {
		if (isEmpty(c))
			return false;
		return REX_PHONE_CA_PATTERN.matcher(c).matches();
	}

	public static boolean isPhoneCN(String c) {
		if (isEmpty(c))
			return false;
		return REX_PHONE_CN_PATTERN.matcher(c).matches();
	}

	public static boolean isPostalcodeCA(String c) {
		if (isEmpty(c))
			return false;
		return REX_POSTALCODE_CA_PATTERN.matcher(c).matches();
	}

	public static boolean isPostalcodeCN(String c) {
		if (isEmpty(c))
			return false;
		return REX_POSTALCODE_CN_PATTERN.matcher(c).matches();
	}

	public static boolean isIDList(String c) {
		if (isEmpty(c))
			return false;
		return REX_ID_LIST_PATTERN.matcher(c).matches();
	}

	public static boolean isBoolean(String c) {
		if (isEmpty(c))
			return false;
		return REX_BOOLEAN_PATTERN.matcher(c).matches();
	}

	public static boolean isSafeRegName(String c) {
		if (isEmpty(c))
			return false;
		return REX_SAFE_REG_NAME_PATTERN.matcher(c).matches();
	}

	public static boolean isSafeImgType(String c) {
		if (isEmpty(c))
			return false;
		return REX_SAFE_IMG_TYPE_PATTERN.matcher(c).matches();
	}

	public static boolean isSafeImgExt(String c) {
		if (isEmpty(c))
			return false;
		return REX_SAFE_IMG_EXT_PATTERN.matcher(c).matches();
	}

	public static boolean isIPv4(String c) {
		if (isEmpty(c))
			return false;
		Matcher match = REX_IPV4_PATTERN.matcher(c);
		if (match.matches()) {
			int ip1 = Integer.parseInt(match.group(1));
			int ip2 = Integer.parseInt(match.group(2));
			int ip3 = Integer.parseInt(match.group(3));
			int ip4 = Integer.parseInt(match.group(4));
			if (ip1 < 0 || ip1 > 255 || ip2 < 0 || ip2 > 255 || ip3 < 0
					|| ip3 > 255 || ip4 < 0 || ip4 > 255)
				return false;
			else
				return true;
		} else {
			return false;
		}
	}

	/**
	 * isIPAnonyms, 192.168.1.*
	 * @param c
	 * @return
	 */
	public static boolean isIPv4Anonyms(String c) {
		if (isEmpty(c))
			return false;
		Matcher match = REX_IPV4_ANONYMS_PATTERN.matcher(c);
		if (match.matches()) {
			int ip1 = Integer.parseInt(match.group(1).replace('*', '1'));
			int ip2 = Integer.parseInt(match.group(2).replace('*', '1'));
			int ip3 = Integer.parseInt(match.group(3).replace('*', '1'));
			int ip4 = Integer.parseInt(match.group(4).replace('*', '1'));
			if (ip1 < 0 || ip1 > 255 || ip2 < 0 || ip2 > 255 || ip3 < 0
					|| ip3 > 255 || ip4 < 0 || ip4 > 255)
				return false;
			else
				return true;
		} else {
			return false;
		}
	}
	
	/**
	 * isSafePassword (Includes at least one small letter and one capital letter and one digit.)
	 * @param c
	 * @return
	 */
	public static boolean isSafePassword(String c) {
		if (isEmpty(c))
			return false;
		if (REX_CAPITAL_PATTERN.matcher(c).matches()
				&& REX_LOWERCASE_PATTERN.matcher(c).matches()
				&& REX_DIGITS_PATTERN.matcher(c).matches())
			return true;
		else
			return false;
	}

	/**
	 * 字母和数字
	 * @param c
	 * @return
	 */
	public static boolean isLetterAndNumber(String c) {
		if (isEmpty(c))
			return false;
		if (REX_LETTERANDNUMBER_PATTERN.matcher(c).matches())
			return true;
		else
			return false;
	}
	
	public static boolean isHexNumber(char c) {
		return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F')
				|| (c >= 'a' && c <= 'f');
	}
	
	/**
	 * 判断是否为“”式的时期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean isDate6(String dateStr) {
		if (isEmpty(dateStr) 
				|| !REX_DATE_YYYYMM_PATTERN.matcher(dateStr).matches()) {
			return false;
		}
		return isValidDateRange(date6Split(dateStr));
	}
	
	/**
	 * 判断是否为“YYYYMMDD”式的时期
	 * 
	 * @param dateStr
	 * @return
	 */
	public static boolean isDate8(String dateStr) {
		if (isEmpty(dateStr) 
				|| !REX_DATE_YYYYMMDD_PATTERN.matcher(dateStr).matches()) {
			return false;
		}
		return isValidDateRange(date8Split(dateStr));
	}
	
	public static boolean isLeapYear(Integer year) {
		return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
	}
	
	public static boolean isInvalidYear(Integer year){
		return year < 1700 || year > 2500;
	}
	
	public static boolean isInvalidMonth(Integer month){
		return month < 1 || month > 12;
	}
	
	public static boolean isInvalidDay(Integer day, Integer month, Integer year){
		Integer[] iaMonthDays = new Integer[] { 31, 28, 31, 30, 31, 30, 31, 31,
				30, 31, 30, 31 };
		if (isLeapYear(year))
			iaMonthDays[1] = 29;
		return day < 1 || day > iaMonthDays[month - 1];
	}
	
	/**
	 * split date 0-YY,1-MM,2-DD
	 * @param dateStr
	 * @return
	 */
	private static Integer[] date6Split(String dateStr){
		final Integer YEAR_BASE = 1900;
		Integer year = null, month = null, day = null;
		
		year = YEAR_BASE + Integer.valueOf(dateStr.substring(0, 2));  
		month = Integer.valueOf(dateStr.substring(2, 4));
		day = Integer.valueOf(dateStr.substring(4, 6));
		return new Integer[]{year, month, day};
	}
	
	/**
	 * split date 0-YYYY,1-MM,2-DD
	 * @param dateStr
	 * @return
	 */
	private static Integer[] date8Split(String dateStr){
		Integer year = null, month = null, day = null;
		
		year = Integer.valueOf(dateStr.substring(0, 4));  
		month = Integer.valueOf(dateStr.substring(4, 6));
		if(dateStr.length() == 8){
			day = Integer.valueOf(dateStr.substring(6, 8));
			return new Integer[]{year, month, day};
		}else{
			return new Integer[]{year, month};
		}
	}
	
	private static boolean isValidDateRange(Integer[] dateSplitResult){
		Integer year = dateSplitResult[0], month = dateSplitResult[1], day = dateSplitResult[2];
		if (isInvalidYear(year))
			return false;
		if (isInvalidMonth(month))
			return false;
		if (isInvalidDay(day, month, year))
			return false;
		return true;
	}
	
	/**
	 * 18位/15位身份证号码校验
	 * @param idNumber
	 * @return
	 */
	public static boolean isIdentityCardNum(String idNumber){
		if (isEmpty(idNumber) 
				|| (idNumber.length() != 18 && idNumber.length() != 15)){
			return false;
		}
		
		// initialize
		if (idNumber.length() == 18) {
			// check date
			String date8 = idNumber.substring(6, 14);
			if (isDate8(date8) == false) {
				return false;
			}
			int totalMulAiWi = 0;
			char charAt;
			// check and set value, calculate the totalmulAiWi
			for (int i = 0; i < 17; i++) {
				charAt = idNumber.charAt(i);
				if(charAt < '0' || charAt > '9'){
					return false;
				}
				totalMulAiWi += Integer.valueOf(String.valueOf(charAt)) * ID_NUM_FACTOR[i];
			}
			
			// calculate the check digit
			String checkDigit =ID_NUM_PARITY_BIT[totalMulAiWi % 11];
			// check last digit
			if (!checkDigit
					.equalsIgnoreCase(String.valueOf(idNumber.charAt(17)))) {
				return false;
			}
		} else {// length is 15
				// check date
			String date6 = idNumber.substring(6, 12);
			if (isDate6(date6) == false) {
				return false;
			}
		}
		return true;
		
	}
	
	/**
	 * 车牌号校验
	 * @param licensePlate
	 * @return
	 */
	public static boolean isLicensePlate(String licensePlate){
		if(isEmpty(licensePlate)){
			return false;
		}
		return REX_LICENSE_PLATE_PATTERN.matcher(licensePlate).matches();
	}

	public static boolean isValidBooleanValue(Integer intValue) {
		if(intValue == null) return false;
		if(intValue < 0 || intValue > 1) return false;
		return true;
	}
}
