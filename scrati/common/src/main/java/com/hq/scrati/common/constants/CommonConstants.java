package com.hq.scrati.common.constants;

/**
 * @author Yan
 * @date 2016年12月13日
 * @version V1.0
 */
public interface CommonConstants {

	/**
	 * 分页信息常量
	 * 
	 * @author Yan
	 * @date 2016年12月14日
	 * @version V1.0
	 */
	public static interface Pagination {
		public static final Integer DEFAULT_START_RECORD = 0;
		public static final Integer DEFAULT_MAX_RECORDS = 20;
	}

	/**
	 * 平台类型-支付宝、微信等
	 * 
	 * @author Yan
	 * @date 2016年12月14日
	 * @version V1.0
	 */
	public static interface PlatType {
		// 数据定义范围[a-zA-z0-9]
		public static final String WX_PAY = "0";
		public static final String ALI_PAY = "1";
		public static final String UNION_PAY = "2";
		public static final String TEN_PAY = "3";
		public static final String BAIDU_PAY = "4";
		public static final String JD_PAY = "5";
	}
	
	public static interface Gender{
		public static final String MALE = "0";
		public static final String FEMALE = "1";
	}
}
