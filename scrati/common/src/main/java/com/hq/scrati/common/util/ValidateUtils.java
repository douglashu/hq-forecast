package com.hq.scrati.common.util;
 
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 数据判断工具
 * 提供基础的数据判断方法
 *
 * @Author Zale
 * @Date 2016/11/28 下午7:21
 *
 */

public class ValidateUtils {
	//正则表达式：验证手机号
	public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
	//正则表达式：验证邮箱
	public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	//正则表达式：验证URL
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	//正则表达式：验证IP地址
    public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";
    //正则表达式：验证身份证
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
    //正则表达式：验证汉字
    public static final String REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$";
    //正则表达式：数字
    public static final String REGEX_NUMERIC  = "([1-9]+[0-9]*|0)(\\.[\\d]+)?";
    //正则表达式：小数
    public static final String REGEX_DECIMAL  = "([1-9]+[0-9]*|0)(\\.[\\d]+)";
    //正则表达式：数字
    public static final String REGEX_INTEGER  = "[0-9]*";
    
	/**
	 * 判断字符串是否为空
	 * @param str	字符串数据
	 * @return 判断结果
	 */
	public static boolean isStrEmpty(String str){
		return null == str  || 0 == str.trim().length();
	}
	
	
	/**
	 * 判断Collection集合是否为空
	 * @param collection	集合数据
	 * @return 判断结果
	 */
	public static boolean isCollectionEmpty(Collection<?> collection){
		return null == collection  || 0 == collection.size();
	}
	
	/**
	 * 判断Map是否为空
	 * @param map	map数据
	 * @return 判断结果
	 */
	public static boolean isMapEmpty(Map<?,?> map){
		return null == map  || 0 == map.size();
	}
	
	/**
	 * 判断数组是否为空
	 * @param arr	object数组
	 * @return 判断结果
	 */
	public static boolean isArrEmpty(Object [] arr){
		return null == arr  || 0 == arr.length;
	}
	
	/**
	 * 判断byte数组是否为空
	 * @param arr	byte数组
	 * @return 判断结果
	 */
	public static boolean isByteArrEmpty(byte [] arr){
		return null == arr  || 0 == arr.length;
	}
	
	
	/**
	 * 判断是否是身份证
	 * @param idCard 身份证字符串
	 */
	public static boolean isIdCard(String idCard){
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}
	
	/**
	 * 判断是否为手机号
	 * @param  mobile 手机号
	 * @return 验证结果
	 */
	public static boolean isMobile(String mobile) { 
		return Pattern.matches(REGEX_MOBILE, mobile);
	}
	

	/**
	 * 判断是否为电话号码
	 * @param  tel 电话号码
	 * @return 验证结果
	 */
	public static boolean isPhone(String tel) { 
		Pattern p1 = null,p2 = null;
		Matcher m = null;
		boolean b = false;  
		p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");  // 验证带区号的
		p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");         // 验证没有区号的
		if(tel.length() >9)
		{	m = p1.matcher(tel);
 		    b = m.matches();  
		}else{
			m = p2.matcher(tel);
 			b = m.matches(); 
		}  
		return b;
	}
	
	 /**
     * 校验身份证
     * 
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }
	
   /**
    * 校验邮箱
    * 
    * @param email
    * @return 校验通过返回true，否则返回false
    */
   public static boolean isEmail(String email) {
       return Pattern.matches(REGEX_EMAIL, email);
   }
   
   /**
    * 校验URL
    * 
    * @param url
    * @return 校验通过返回true，否则返回false
    */
   public static boolean isUrl(String url) {
       return Pattern.matches(REGEX_URL, url);
   }

   /**
    * 校验IP地址
    * 
    * @param ipAddr
    * @return
    */
   public static boolean isIPAddr(String ipAddr) {
       return Pattern.matches(REGEX_IP_ADDR, ipAddr);
   }
	
	
	/**
     * 判断字符串是否为整数
     * @param numberStr 字符串
     * @return 验证结果
     */
    public static boolean isInteger(String numberStr) {
        return Pattern.matches(REGEX_INTEGER, numberStr);
    }
    
    /**
     * 判断字符串是否为数字
     * @param numberStr 字符串
     * @return 验证结果
     */
    public static boolean isNumeric(String numberStr) {
        return Pattern.matches(REGEX_NUMERIC, numberStr);
    }
    
    /**
     * 判断是否是数字，包含小数
     * @param decimal 字符串
     * @return 验证结果
     */
    public static boolean isDecimal(String decimal){
    	return Pattern.matches(REGEX_DECIMAL, decimal);
	}
	/**
	 * 功能：判断字符串是否为日期格式
	 * @param str
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
    /**
     * 判断是否是汉字
     * @param chinese 文字字符串
     * @return 验证结果
     */
    public static boolean isChinese(String chinese){
    	return Pattern.matches(REGEX_CHINESE, chinese);
    }
}
