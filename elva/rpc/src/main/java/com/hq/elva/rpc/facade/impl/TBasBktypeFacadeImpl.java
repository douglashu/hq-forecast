package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasBktype;
import com.hq.elva.rpc.facade.TBasBktypeFacade;
import com.hq.elva.service.ITBasBktypeService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasBktypeFacadeImpl implements TBasBktypeFacade {
	
	public static final Logger logger = Logger.getLogger(TBasBktypeFacadeImpl.class);

	@Resource
	private ITBasBktypeService iTBasBktypeService;

	/**
	 * 根据银联行号获取银联行号数据
	 * @param lbnkNo
	 * @return
	 */
	@Override
	public RespEntity<Map<String, String>> getBasBktype(String bnkTyp) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			TBasBktype tBasBktype = iTBasBktypeService.getTBasBktype(bnkTyp);
			map.put("bankCode", tBasBktype.getBankCode());
			map.put("bnkNm", tBasBktype.getBnkNm());
			map.put("bnkTyp", tBasBktype.getBnkTyp());
			map.put("orgNo", tBasBktype.getOrgNo());
			map.put("bnkId", String.valueOf(tBasBktype.getBnkId()));
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
	public RespEntity<List<Map<String, String>>> getBasBktypes() {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		
		try{
			List<TBasBktype> list = iTBasBktypeService.getTBasBktypes();
			for(int i = 0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("bankCode", list.get(i).getBankCode());
				map.put("bnkNm", list.get(i).getBnkNm());
				map.put("bnkTyp", list.get(i).getBnkTyp());
				map.put("orgNo", list.get(i).getOrgNo());
				map.put("bnkId", String.valueOf(list.get(i).getBnkId()));
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);	
	
	
	}

	public ITBasBktypeService getiTBasBktypeService() {
		return iTBasBktypeService;
	}

	public void setiTBasBktypeService(ITBasBktypeService iTBasBktypeService) {
		this.iTBasBktypeService = iTBasBktypeService;
	}
	

}
