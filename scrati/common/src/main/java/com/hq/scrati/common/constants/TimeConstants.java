package com.hq.scrati.common.constants;

/**
 * 时间常量
 * @author zale
 */
public interface TimeConstants {
	//一秒
	public static final long SECOND_MILLI=1000;

	//一分
	public static final long MINUTE_MILLI=SECOND_MILLI*60;
	public static final long MINUTE_SEC=60;

	//一小时
	public static final long HOUR_MILLI=MINUTE_MILLI*60;
	public static final long HOUR_SEC=MINUTE_SEC*60;
	//一天
	public static final long DAY_MILLI=HOUR_MILLI*24;
	public static final long DAY_SEC=HOUR_SEC*24;
	//一周
	public static final long WEEK_MILLI=DAY_MILLI*7;
	public static final long WEEK_SEC=DAY_SEC*7;
	//30天
	public static final long MONTH_MILLI=DAY_MILLI*30;
	public static final long MONTH_SEC=DAY_SEC*30;
	//一年
	public static final long YEAR_MILLI=DAY_MILLI*365;
	public static final long YEAR_SEC=DAY_SEC*365;
}
