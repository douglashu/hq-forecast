package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasBkcdFacade {
	/**
	 * 获取银行信息
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getTBasBkcds();
}
