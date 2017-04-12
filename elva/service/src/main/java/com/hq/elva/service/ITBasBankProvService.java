package com.hq.elva.service;


import com.hq.elva.entity.generate.TBasBankProv;

import java.util.List;

public interface ITBasBankProvService {
	/**
	 * 获取银联省份信息
	 * @return
	 */
	public List<TBasBankProv> getBankProv();
}
