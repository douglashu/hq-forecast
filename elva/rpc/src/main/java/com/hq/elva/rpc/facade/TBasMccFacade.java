package com.hq.elva.rpc.facade;

import com.hq.elva.entity.generate.TBasAlimcc;
import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasMccFacade {
	
	/**
	 * 根据mcc大类获取mcc码，其中大类可以为空
	 * @param typId
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getTBasMccs(String typId);
	
	/**
	 * 根据mccCd获取mcc详情
	 * @param typId
	 * @return
	 */
	public RespEntity<Map<String,String>> getTBasMcc(String mccCd);

	public List<TBasAlimcc> getAliMcc(String pMccCd);
}
