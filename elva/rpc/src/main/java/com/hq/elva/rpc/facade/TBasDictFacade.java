package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasDictFacade {
	/**
	 * 根据参数名获取参数列表
	 * @param paramName
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getTBasDicts(String paramName);
	
	/**
	 * 根据参数名以及参数值获取参数列表
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	public RespEntity<String> getTBasDict(String paramName, String paramValue);
}
