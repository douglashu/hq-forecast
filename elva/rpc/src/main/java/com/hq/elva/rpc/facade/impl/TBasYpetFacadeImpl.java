package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasYpet;
import com.hq.elva.rpc.facade.TBasYpetFacade;
import com.hq.elva.service.ITBasYpetService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasYpetFacadeImpl implements TBasYpetFacade{

	public static final Logger logger = Logger.getLogger(TBasYpetFacadeImpl.class);

	@Resource
	private ITBasYpetService iTBasYpetService;
	
	/**
	 * 获取mcc大类列表
	 * @return
	 */
	@Override
	public RespEntity<List<Map<String, String>>> getTBasYpets() {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasYpet> list = iTBasYpetService.getTBasYpets();
			for(int i = 0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("typId", list.get(i).getTypId());
				map.put("baseAmt", String.valueOf(list.get(i).getBaseAmt()));
				map.put("baseRef", String.valueOf(list.get(i).getBaseRef()));
				map.put("specAmt", String.valueOf(list.get(i).getSpecAmt()));
				map.put("specRef", String.valueOf(list.get(i).getSpecRef()));
				map.put("typNm", String.valueOf(list.get(i).getTypNm()));
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);	
	
	}

	/**
	 * 根据大类id获取大类的明细情况
	 * @param typId
	 * @return
	 */
	@Override
	public RespEntity<Map<String, String>> getTBasYpet(String typId) {
		Map<String, String> map = new HashMap<String, String>();
		try{
			TBasYpet tBasYpet = iTBasYpetService.getTBasYpet(typId);
			map.put("typId",tBasYpet.getTypId());
			map.put("baseAmt", String.valueOf(tBasYpet.getBaseAmt()));
			map.put("baseRef", String.valueOf(tBasYpet.getBaseRef()));
			map.put("specAmt", String.valueOf(tBasYpet.getSpecAmt()));
			map.put("specRef", String.valueOf(tBasYpet.getSpecRef()));
			map.put("typNm", String.valueOf(tBasYpet.getTypNm()));
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<Map<String, String>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",map);
		}
		return new RespEntity<Map<String, String>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",map);	
	
	}

}
