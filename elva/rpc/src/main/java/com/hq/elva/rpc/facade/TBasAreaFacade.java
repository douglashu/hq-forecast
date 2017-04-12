package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.model.HqRequest;

import java.util.List;
import java.util.Map;

public interface TBasAreaFacade {
	/**
	 * 获取省份信息
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getProv();
	
	/**
	 * 根据省份信息获取地市
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getCity(String provCode);
	/**
	 * 根据城市名称
	 * @return
	 */
	public Map<String,String> getName(HqRequest request);

}
