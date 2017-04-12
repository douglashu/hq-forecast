package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasBankProvMapper;
import com.hq.elva.entity.generate.TBasBankProv;
import com.hq.elva.entity.generate.TBasBankProvExample;
import com.hq.elva.service.ITBasBankProvService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TBasBankProvServiceImpl implements ITBasBankProvService {

	@Resource
	private TBasBankProvMapper tBasBankProvMapper;

	/**
	 * 获取银联省份信息
	 * @return
	 */
	@Override
	public List<TBasBankProv> getBankProv() {
		TBasBankProvExample example = new TBasBankProvExample();
		example.createCriteria();
		List<TBasBankProv> list = tBasBankProvMapper.selectByExample(example);
		return list;
	}
	
	
	public TBasBankProvMapper gettBasBankProvMapper() {
		return tBasBankProvMapper;
	}

	public void settBasBankProvMapper(TBasBankProvMapper tBasBankProvMapper) {
		this.tBasBankProvMapper = tBasBankProvMapper;
	}
}
