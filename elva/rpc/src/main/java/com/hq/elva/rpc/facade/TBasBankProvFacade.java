package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasBankProvFacade {
	/**
	 * 获取银联省份信息
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getBankProv();
}
