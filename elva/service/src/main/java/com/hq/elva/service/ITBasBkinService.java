package com.hq.elva.service;


import com.hq.elva.entity.generate.TBasBkin;

import java.util.List;

public interface ITBasBkinService {
	
	/**
	 * 根据银联行号获取银联行号数据
	 * @param lbnkNo
	 * @return
	 */
	public TBasBkin getTBasBkin(String lbnkNo);
	
	/**
	 * 根据银行编码，省份，地市，区县获取银联行号数据
	 * @param lbnkCd
	 * @param admProv
	 * @param admCity
	 * @param admRgn
	 * @return
	 */
	public List<TBasBkin> getTBasBkins(String lbnkCd, String admProv, String admCity, String admRgn);
}
