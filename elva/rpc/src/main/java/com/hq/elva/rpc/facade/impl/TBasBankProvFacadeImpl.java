package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasBankProv;
import com.hq.elva.rpc.facade.TBasBankProvFacade;
import com.hq.elva.service.ITBasBankProvService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasBankProvFacadeImpl implements TBasBankProvFacade {

	
	public static final Logger logger = Logger.getLogger(TBasBankProvFacadeImpl.class);
	
	@Resource
	private ITBasBankProvService iTBasBankProvService;
	
	/**
	 * 获取银联省份信息
	 * @return
	 */
	@Override
	public RespEntity<List<Map<String, String>>> getBankProv() {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasBankProv> list = iTBasBankProvService.getBankProv();
			for(int i=0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("provCd", list.get(i).getProvCd());
				map.put("provNm", list.get(i).getProvNm());
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);
	
	}

}
