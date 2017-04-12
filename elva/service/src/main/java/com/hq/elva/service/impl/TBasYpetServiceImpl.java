package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasYpetMapper;
import com.hq.elva.entity.generate.TBasYpet;
import com.hq.elva.entity.generate.TBasYpetExample;
import com.hq.elva.service.ITBasYpetService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TBasYpetServiceImpl implements ITBasYpetService {
	
	@Resource
	private TBasYpetMapper tBasYpetMapper;

	
	/**
	 * 获取mcc大类列表
	 * @return
	 */
	@Override
	public List<TBasYpet> getTBasYpets() {
		TBasYpetExample example = new TBasYpetExample();
		example.createCriteria();
		List<TBasYpet> list = tBasYpetMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据大类id获取大类的明细情况
	 * @param typId
	 * @return
	 */
	@Override
	public TBasYpet getTBasYpet(String typId) {
		TBasYpet tBasYpet = tBasYpetMapper.selectByPrimaryKey(typId);
		return tBasYpet;
	}

	public TBasYpetMapper gettBasYpetMapper() {
		return tBasYpetMapper;
	}

	public void settBasYpetMapper(TBasYpetMapper tBasYpetMapper) {
		this.tBasYpetMapper = tBasYpetMapper;
	}

}
