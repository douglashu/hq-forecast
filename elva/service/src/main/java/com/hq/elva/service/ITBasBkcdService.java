package com.hq.elva.service;


import com.hq.elva.entity.generate.TBasBkcd;

import java.util.List;

public interface ITBasBkcdService {
	
	/**
	 * 获取银行信息
	 * @return
	 */
	public List<TBasBkcd> getTBasBkcds();
}
