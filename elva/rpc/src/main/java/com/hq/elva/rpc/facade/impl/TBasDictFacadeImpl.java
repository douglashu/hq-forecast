package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasDict;
import com.hq.elva.rpc.facade.TBasDictFacade;
import com.hq.elva.service.ITBasDictService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasDictFacadeImpl implements TBasDictFacade {

	
	public static final Logger logger = Logger.getLogger(TBasDictFacadeImpl.class);

	@Resource
	private ITBasDictService iTBasDictService;
	
	/**
	 * 根据参数名获取参数列表
	 * @param paramName
	 * @return
	 */
	@Override
	public RespEntity<List<Map<String, String>>> getTBasDicts(String paramName) {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasDict> list = iTBasDictService.getTBasDicts(paramName);
			for(int i = 0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("paramCname", list.get(i).getParamCname());
				map.put("paramExp", list.get(i).getParamExp());
				map.put("paramExpDesc", list.get(i).getParamExpDesc());
				map.put("paramType", list.get(i).getParamType());
				map.put("paramValue", list.get(i).getParamValue());
				map.put("paramId", String.valueOf(list.get(i).getParamId()));
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);	
	}

	/**
	 * 根据参数名以及参数值获取参数列表
	 * @param paramName
	 * @param paramValue
	 * @return
	 */
	@Override
	public RespEntity<String> getTBasDict(String paramName, String paramValue) {
		String paramExp = "";
		try{
			paramExp = iTBasDictService.getTBasDict(paramName, paramValue);
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<String>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",paramExp);
		}
		return new RespEntity<String>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",paramExp);	
	}

}
