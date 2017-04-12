package com.hq.louis.service;

import java.util.Map;

public interface MobileMessageService {
	
	public static final String SEND_SUCCESS = "1";
	public static final String SEND_FAILED = "-1";

	/**
	 * 发送短信
	 * 
	 * @param mobile
	 *            手机号
	 * @param content
	 *            短信内容
	 */
	public String sendMobile(String mobile, String content);

}
