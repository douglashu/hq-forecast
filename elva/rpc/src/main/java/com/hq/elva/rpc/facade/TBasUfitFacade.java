package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.Map;

public interface TBasUfitFacade {
	/**
	 * 根据银行卡号获取卡宾信息
	 * @param cardNo
	 * @return
	 */
	public RespEntity<Map<String,String>> getTBasUfit(String cardNo);
}
