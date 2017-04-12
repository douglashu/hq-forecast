package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasBkcdMapper;
import com.hq.elva.entity.generate.TBasBkcd;
import com.hq.elva.entity.generate.TBasBkcdExample;
import com.hq.elva.service.ITBasBkcdService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TBasBkcdServiceImpl implements ITBasBkcdService {

	
	@Resource
	private TBasBkcdMapper tBasBkcdMapper;
	/**
	 * 获取银行信息
	 * @return
	 */
	@Override
	public List<TBasBkcd> getTBasBkcds() {
		TBasBkcdExample example = new TBasBkcdExample();
		example.createCriteria();
		List<TBasBkcd> list = tBasBkcdMapper.selectByExample(example);
		return list;
	}
	public TBasBkcdMapper gettBasBkcdMapper() {
		return tBasBkcdMapper;
	}
	public void settBasBkcdMapper(TBasBkcdMapper tBasBkcdMapper) {
		this.tBasBkcdMapper = tBasBkcdMapper;
	}

}
