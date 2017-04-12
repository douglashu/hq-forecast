package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasBktypeMapper;
import com.hq.elva.entity.generate.TBasBktype;
import com.hq.elva.entity.generate.TBasBktypeExample;
import com.hq.elva.entity.generate.TBasBktypeExample.Criteria;
import com.hq.elva.service.ITBasBktypeService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TBasBktypeServiceImpl implements ITBasBktypeService {

	@Resource
	private TBasBktypeMapper tBasBktypeMapper;

	@Override
	public TBasBktype getTBasBktype(String bnkTyp) {
		TBasBktypeExample example = new TBasBktypeExample();
		Criteria criteria = example.createCriteria();
		criteria.andBnkTypEqualTo(bnkTyp);
		List<TBasBktype> list= tBasBktypeMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public List<TBasBktype> getTBasBktypes() {
		TBasBktypeExample example = new TBasBktypeExample();
		Criteria criteria = example.createCriteria();
		List<TBasBktype> list= tBasBktypeMapper.selectByExample(example);
		return list;
	}

}