package com.hq.elva.service;


import com.hq.elva.entity.generate.TBasBankCity;

import java.util.List;

public interface ITBasBankCityService {

	/**
	 * 根据银联省份信息获取银联地市
	 * @return
	 */
	public List<TBasBankCity> getBankCity(String provCode);
	
	/**
	 * 根据银联地市信息获取银联区县
	 * @return
	 */
	public List<TBasBankCity> getBankCounty(String cityCode);
}
