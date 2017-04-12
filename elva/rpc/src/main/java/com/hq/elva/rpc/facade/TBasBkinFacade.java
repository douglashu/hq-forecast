package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;

public interface TBasBkinFacade {
	/**
	 * 根据银联行号获取银联行号数据
	 * @param lbnkNo
	 * @return
	 */
	public RespEntity<Map<String,String>> getTBasBkin(String lbnkNo);
	
	/**
	 * 根据银行编码，省份，地市，区县获取银联行号数据
	 * @param lbnkCd
	 * @param admProv
	 * @param admCity
	 * @param admRgn
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getTBasBkins(Map<String, String> paramMap);
}
