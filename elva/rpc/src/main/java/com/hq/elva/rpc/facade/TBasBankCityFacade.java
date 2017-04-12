package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasBankCityFacade {
	/**
	 * 根据银联省份信息获取银联地市
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getBankCity(String provCode);
	
	/**
	 * 根据银联地市信息获取银联区县
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getBankCounty(String cityCode);
}
