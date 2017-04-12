package com.hq.elva.service;

import com.hq.elva.entity.generate.TBasUfit;

public interface ITBasUfitService {
	
	/**
	 * 根据银行卡号获取卡宾信息
	 * @param cardNo
	 * @return
	 */
	public TBasUfit getTBasUfit(String cardNo);
}
