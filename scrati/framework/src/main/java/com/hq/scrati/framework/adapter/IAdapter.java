package com.hq.scrati.framework.adapter;

import com.hq.esc.entity.generate.TesbService;

import java.util.Map;


public interface IAdapter {
	
	/**
	 * 调用协议按照指定协议调用后端应用
	 * @param tesbService
	 * @param parameters
	 * @return
	 * @throws Exception 
	 */
	public Object excute(TesbService tesbService,Map<String, Object> parameters) throws Exception;
}
