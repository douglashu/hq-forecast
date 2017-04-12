package com.hq.elva.service;


import com.hq.elva.entity.generate.TBasMcc;

import java.util.List;

public interface ITBasMccService {
	
	/**
	 * 根据mcc大类获取mcc码，其中大类可以为空
	 * @param typId
	 * @return
	 */
	public List<TBasMcc> getTBasMccs(String typId);
	
	/**
	 * 根据mccCd获取mcc详情
	 * @param mccCd
	 * @return
	 */
	public TBasMcc getTBasMcc(String mccCd);
}
