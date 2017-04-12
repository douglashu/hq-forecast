package com.hq.elva.service;


import com.hq.elva.entity.generate.TBasArea;

import java.util.List;

public interface ITBasAreaService {
	
	/**
	 * 获取省份信息
	 * @return
	 */
	public List<TBasArea> getProv();
	
	/**
	 * 根据省份信息获取地市
	 * @return
	 */
	public List<TBasArea> getCity(String provCode);
}
