package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasMccMapper;
import com.hq.elva.entity.generate.TBasMcc;
import com.hq.elva.entity.generate.TBasMccExample;
import com.hq.elva.service.ITBasMccService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class TBasMccServiceImpl implements ITBasMccService {

	@Resource
	private TBasMccMapper tBasMccMapper;
	
	/**
	 * 根据mcc大类获取mcc码，其中大类可以为空
	 * @param typId
	 * @return
	 */
	@Override
	public List<TBasMcc> getTBasMccs(String typId) {
		TBasMccExample example = new TBasMccExample();
		if(typId!=null&&!"".equals(typId)){
			example.createCriteria().andTypIdEqualTo(typId);
		}else{
			example.createCriteria();
		}
		List<TBasMcc> list = tBasMccMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据mccCd获取mcc详情
	 * @param mccCd
	 * @return
	 */
	@Override
	public TBasMcc getTBasMcc(String mccCd) {
		TBasMcc tBasMcc = tBasMccMapper.selectByPrimaryKey(mccCd);
		return tBasMcc;
	}

}
