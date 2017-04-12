package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasBkinMapper;
import com.hq.elva.entity.generate.TBasBkin;
import com.hq.elva.entity.generate.TBasBkinExample;
import com.hq.elva.entity.generate.TBasBkinExample.Criteria;
import com.hq.elva.service.ITBasBkinService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TBasBkinServiceImpl implements ITBasBkinService {


	@Resource
	private TBasBkinMapper tBasBkinMapper;
	
	/**
	 * 根据银联行号获取银联行号数据
	 * @param lbnkNo
	 * @return
	 */
	@Override
	public TBasBkin getTBasBkin(String lbnkNo) {
		TBasBkin tBasBkin = tBasBkinMapper.selectByPrimaryKey(lbnkNo);
		return tBasBkin;
	}

	/**
	 * 根据银行编码，省份，地市，区县获取银联行号数据
	 * @param lbnkCd
	 * @param admProv
	 * @param admCity
	 * @param admRgn
	 * @return
	 */
	@Override
	public List<TBasBkin> getTBasBkins(String lbnkCd, String admProv, String admCity, String admRgn) {
		TBasBkinExample example = new TBasBkinExample();
		Criteria criteria = example.createCriteria();
		//行别代码
		if(lbnkCd!=null&&!"".equals(lbnkCd.trim())){
			criteria.andLbnkCdEqualTo(lbnkCd);
		}
		//归属行政省
		if(admProv!=null&&!"".equals(admProv.trim())){
			criteria.andAdmProvEqualTo(admProv);
		}
		//归属行政地市
		if(admCity!=null&&!"".equals(admCity.trim())){
			criteria.andAdmCityEqualTo(admCity);
		}
		//归属行政区县
		if(admRgn!=null&&!"".equals(admRgn.trim())){
			criteria.andAdmRgnEqualTo(admRgn);
		}
		List<TBasBkin> list = tBasBkinMapper.selectByExample(example);
		return list;
	}
	

	public TBasBkinMapper gettBasBkinMapper() {
		return tBasBkinMapper;
	}

	public void settBasBkinMapper(TBasBkinMapper tBasBkinMapper) {
		this.tBasBkinMapper = tBasBkinMapper;
	}

}
