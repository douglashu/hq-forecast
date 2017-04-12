package com.hq.scrati.common.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * 数字工具
 * @author Zale
 */
public class NumberUtils extends org.apache.commons.lang.math.NumberUtils{
	
	/**
	 * 随机数，默认10000内
	 * @return
	 */
	public static double random(){
		return random(0,10000);
	}
	
	/**
	 * 随机数
	 * @param min 最小值
	 * @param max 最大值
	 * @return 随机数
	 */
	public static int random(int min,int max){
		Random random = new Random();
		int s = random.nextInt(max)%(max-min+1) + min;
		return s;
	}
	
	/**
	 * 增加整型数字
	 * @param fieldName 域名
	 * @param data 数据
	 * @param num 数值
	 */
	public static void plusIntegerNumber(String fieldName,Object data,Integer num){
		Integer integer=(Integer)ReflectionUtils.getValueFromField(fieldName, data);
		if(null == integer){
			integer=0;
		}
		ReflectionUtils.setValue2Field(fieldName, data, integer+num);
	}
	
	/**
	 * 格式化两位小数
	 * @param deciaml 小数 
	 * @return 格式化后的小数
	 */
	public static double formatWithTwoDecimal(double deciaml){
		return formatDecimal(deciaml, 2);
	}
	
	/**
	 * 格式化小数
	 * @param deciaml 小数
	 * @param place 保留位数
	 * @return 格式化后的小数
	 */
	public static double formatDecimal(double deciaml,int place){
		String result = String.format("%."+place+"f", deciaml);
		return Double.valueOf(result);
	}
	
	/**
	 * 小数相乘
	 * @param num1 小数1
	 * @param num2 小数2
	 * @return 相乘结果
	 */
	public static double multiply(double num1,double num2){
		BigDecimal num1Bg  =   new  BigDecimal(Double.toString(num1));
		BigDecimal num2Bg=new BigDecimal(Double.toString(num2));
		double res=num1Bg.multiply(num2Bg).doubleValue();
		return res;
	}
	
	/**
	 * 小数相减
	 * @param num1 小数1
	 * @param num2 小数2
	 * @return 相减结果
	 */
	public static double subtract(double num1,double num2){
		BigDecimal num1Bg  =   new BigDecimal(Double.toString(num1));
		BigDecimal num2Bg  =   new BigDecimal(Double.toString(num2));
		return num1Bg.subtract(num2Bg).doubleValue();
	}
	
	/**
	 * 小数相减
	 * @param num1 小数1
	 * @param num2 小数2
	 * @return 相减结果
	 */
	public static double add(double num1,double num2){
		BigDecimal num1Bg  =   new BigDecimal(Double.toString(num1));
		BigDecimal num2Bg  =   new BigDecimal(Double.toString(num2));
		return num1Bg.add(num2Bg).doubleValue();
	}
	
	
	/**
	 * 计算整数百分比
	 * @param elements 分子
	 * @param denominator 分母
	 * @param place 保留位数
	 * @return 百分比
	 */
	public static double calcPercentage(double elements,double denominator,int place){
		if(0 == elements || 0 == denominator){
			return 0.0;
		}
		double per=(elements*1.0/denominator)*100;
		return formatDecimal(per, place);
	}
	
	/**
	 * 计算整数百分比,保留2位小数
	 * @param elements 分子
	 * @param denominator 分母
	 * @return 百分比
	 */
	public static double calcPercentage(double elements,double denominator){
		return calcPercentage(elements, denominator, 2);
	}
}

