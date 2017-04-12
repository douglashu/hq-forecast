package com.hq.elva.service;


import com.hq.elva.entity.generate.TBasYpet;

import java.util.List;

public interface ITBasYpetService {
	
	/**
	 * 获取mcc大类列表
	 * @return
	 */
	public List<TBasYpet> getTBasYpets();
	
	/**
	 * 根据大类id获取大类的明细情况
	 * @param typId
	 * @return
	 */
	public TBasYpet getTBasYpet(String typId);
}
