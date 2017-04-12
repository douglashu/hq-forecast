package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasBkcd;
import com.hq.elva.rpc.facade.TBasBkcdFacade;
import com.hq.elva.service.ITBasBkcdService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasBkcdFacadeImpl implements TBasBkcdFacade {
	
	public static final Logger logger = Logger.getLogger(TBasBkcdFacadeImpl.class);

	@Resource
	private ITBasBkcdService iTBasBkcdService;
	
	@Override
	public RespEntity<List<Map<String, String>>> getTBasBkcds() {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasBkcd> list = iTBasBkcdService.getTBasBkcds();
			for(int i=0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("lbnkCd", list.get(i).getLbnkCd());
				map.put("bnkNm", list.get(i).getBnkNm());
				map.put("corpOrg", list.get(i).getCorpOrg());
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);
	
	
	}
	
	public ITBasBkcdService getiTBasBkcdService() {
		return iTBasBkcdService;
	}

	public void setiTBasBkcdService(ITBasBkcdService iTBasBkcdService) {
		this.iTBasBkcdService = iTBasBkcdService;
	}
}
