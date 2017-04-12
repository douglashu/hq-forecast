package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasBkin;
import com.hq.elva.rpc.facade.TBasBkinFacade;
import com.hq.elva.service.ITBasBkinService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasBkinFacadeImpl implements TBasBkinFacade {
	
	public static final Logger logger = Logger.getLogger(TBasBkinFacadeImpl.class);

	@Resource
	private ITBasBkinService iTBasBkinService;

	/**
	 * 根据银联行号获取银联行号数据
	 * @param lbnkNo
	 * @return
	 */
	@Override
	public RespEntity<Map<String, String>> getTBasBkin(String lbnkNo) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			TBasBkin tBasBkin = iTBasBkinService.getTBasBkin(lbnkNo);
			map.put("lbnkCd", tBasBkin.getLbnkCd());
			map.put("lbnkNm", tBasBkin.getLbnkNm());
			map.put("lbnkNo", tBasBkin.getLbnkNo());
			map.put("admCity", tBasBkin.getAdmCity());
			map.put("admProv", tBasBkin.getAdmProv());
			map.put("admRgn", tBasBkin.getAdmRgn());
			map.put("cityCd", tBasBkin.getCityCd());
			map.put("corpOrg", tBasBkin.getCorpOrg());
			map.put("nodId", tBasBkin.getNodId());
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<Map<String, String>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",map);
		}
		return new RespEntity<Map<String, String>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",map);	
	
	}

	/**
	 * 根据银行编码，省份，地市，区县获取银联行号数据
	 * @param lbnkCd
	 * @param admProv
	 * @param admCity
	 * @param admRgn
	 * @return
	 */
	@Override
	public RespEntity<List<Map<String, String>>> getTBasBkins(Map<String, String> paramMap) {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		
		try{
			List<TBasBkin> list = iTBasBkinService.getTBasBkins(paramMap.get("lbnkCd"),paramMap.get("admProv") ,paramMap.get("admCity") ,paramMap.get("admRgn") );
			for(int i = 0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("lbnkCd", list.get(i).getLbnkCd());
				map.put("lbnkNm", list.get(i).getLbnkNm());
				map.put("lbnkNo", list.get(i).getLbnkNo());
				map.put("admCity", list.get(i).getAdmCity());
				map.put("admProv", list.get(i).getAdmProv());
				map.put("admRgn", list.get(i).getAdmRgn());
				map.put("cityCd", list.get(i).getCityCd());
				map.put("corpOrg", list.get(i).getCorpOrg());
				map.put("nodId", list.get(i).getNodId());
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);	
	
	
	}
	
	
	public ITBasBkinService getiTBasBkinService() {
		return iTBasBkinService;
	}


	public void setiTBasBkinService(ITBasBkinService iTBasBkinService) {
		this.iTBasBkinService = iTBasBkinService;
	}

}
