package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasAreaMapper;
import com.hq.elva.entity.generate.TBasArea;
import com.hq.elva.entity.generate.TBasAreaExample;
import com.hq.elva.service.ITBasAreaService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TBasAreaServiceImpl implements ITBasAreaService {
	
	@Resource
	private TBasAreaMapper tBasAreaMapper;

	/**
	 * 获取省份信息
	 * @return
	 */
	@Override
	public List<TBasArea> getProv() {
		TBasAreaExample example = new TBasAreaExample();
		example.createCriteria().andUpAreaCodeEqualTo("000000");
		List<TBasArea> list = tBasAreaMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据省份信息获取地市
	 * @return
	 */
	@Override
	public List<TBasArea> getCity(String provCode) {
		TBasAreaExample example = new TBasAreaExample();
		example.createCriteria().andUpAreaCodeEqualTo(provCode);
		List<TBasArea> list = tBasAreaMapper.selectByExample(example);
		return list;
	}


	public TBasAreaMapper gettBasAreaMapper() {
		return tBasAreaMapper;
	}

	public void settBasAreaMapper(TBasAreaMapper tBasAreaMapper) {
		this.tBasAreaMapper = tBasAreaMapper;
	}
}
