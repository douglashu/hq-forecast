package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasYpetFacade {
	
	/**
	 * 获取mcc大类列表
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getTBasYpets();
	
	/**
	 * 根据大类id获取大类的明细情况
	 * @param typId
	 * @return
	 */
	public RespEntity<Map<String,String>> getTBasYpet(String typId);
}
