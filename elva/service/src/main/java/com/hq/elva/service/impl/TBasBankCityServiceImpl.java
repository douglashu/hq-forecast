package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasBankCityMapper;
import com.hq.elva.entity.generate.TBasBankCity;
import com.hq.elva.entity.generate.TBasBankCityExample;
import com.hq.elva.service.ITBasBankCityService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TBasBankCityServiceImpl implements ITBasBankCityService {

	
	@Resource
	private TBasBankCityMapper tBasBankCityMapper;
	
	/**
	 * 根据银联省份信息获取银联地市
	 * @return
	 */
	@Override
	public List<TBasBankCity> getBankCity(String provCode) {
		TBasBankCityExample example = new TBasBankCityExample();
		example.createCriteria().andProvCdEqualTo(provCode);
		List<TBasBankCity> list = tBasBankCityMapper.selectByCityExample(example);
		return list;
	}

	/**
	 * 根据银联地市信息获取银联区县
	 * @return
	 */
	@Override
	public List<TBasBankCity> getBankCounty(String cityCode) {
		TBasBankCityExample example = new TBasBankCityExample();
		example.createCriteria().andBossCityEqualTo(cityCode);
		List<TBasBankCity> list = tBasBankCityMapper.selectByCountyExample(example);
		return list;
	}

	
	public TBasBankCityMapper gettBasBankCityMapper() {
		return tBasBankCityMapper;
	}

	public void settBasBankCityMapper(TBasBankCityMapper tBasBankCityMapper) {
		this.tBasBankCityMapper = tBasBankCityMapper;
	}

}
