package com.hq.elva.service;

import com.hq.elva.entity.generate.TBasBktype;

import java.util.List;

public interface ITBasBktypeService {
	
	/**
	 * 根据bnk_typ获取银行信息
	 * @param lbnkNo
	 * @return
	 */
	public TBasBktype getTBasBktype(String bnkTyp);
	
	/**
	 * @return
	 */
	public List<TBasBktype> getTBasBktypes();
}
