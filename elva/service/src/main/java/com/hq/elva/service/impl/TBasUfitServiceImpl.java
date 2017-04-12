package com.hq.elva.service.impl;

import com.hq.elva.dao.generate.TBasUfitMapper;
import com.hq.elva.entity.generate.TBasUfit;
import com.hq.elva.service.ITBasUfitService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class TBasUfitServiceImpl implements ITBasUfitService {

	@Resource
	private TBasUfitMapper tBasUfitMapper;
	
	/**
	 * 根据银行卡号获取卡宾信息
	 * @param cardNo
	 * @return
	 */
	@Override
	public TBasUfit getTBasUfit(String cardNo) {
		TBasUfit tBasUfit = tBasUfitMapper.selectByCardNo(cardNo);
		return tBasUfit;
	}

	public TBasUfitMapper gettBasUfitMapper() {
		return tBasUfitMapper;
	}

	public void settBasUfitMapper(TBasUfitMapper tBasUfitMapper) {
		this.tBasUfitMapper = tBasUfitMapper;
	}

}
