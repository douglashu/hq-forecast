package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasBktypeFacade {
	/**
	 * 根据bnk_typ获取银行信息
	 * @param lbnkNo
	 * @return
	 */
	public RespEntity<Map<String,String>> getBasBktype(String lbnkNo);
	
	/**
	 * 获取银行数据
	 */
	public RespEntity<List<Map<String,String>>> getBasBktypes();
}
