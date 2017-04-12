package com.hq.elva.rpc.facade.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.constant.CfgConstant;
import com.hq.elva.entity.generate.TBasConfig;
import com.hq.elva.rpc.facade.TBasConfigFacade;
import com.hq.elva.service.ITBasConfigService;
import com.hq.scrati.common.log.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TBasConfigFacadeImpl implements TBasConfigFacade {
	
	public static final Logger logger = Logger.getLogger(TBasConfigFacadeImpl.class);

	@Resource
	private ITBasConfigService iTBasConfigService;


	@Override
	public RespEntity<List<Map<String, String>>> getTBasConfigByService(String serviceName, String serviceIp) {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			List<TBasConfig> list = iTBasConfigService.getTBasConfigByService(serviceName,serviceIp);
			for(int i = 0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("configExp", list.get(i).getConfigExp());
				map.put("configName", list.get(i).getConfigName());
				map.put("configValue", list.get(i).getConfigValue());
				map.put("dynamic", list.get(i).getDynamic());
				map.put("serviceIp", list.get(i).getServiceIp());
				map.put("serviceName", list.get(i).getServiceName());
				map.put("configId", String.valueOf(list.get(i).getConfigId()));
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);	
	}
	
	@Override
	public RespEntity<Map<String, String>> getTBasConfigByServiceName(String serviceName, String serviceIp) {
		Map<String, String> reMap = new HashMap<String, String>();
		try{
			List<TBasConfig> global_list = iTBasConfigService.getTBasConfigs(null, null, "*", null);
			for(int i = 0;i<global_list.size();i++){
				reMap.put(global_list.get(i).getConfigName(), global_list.get(i).getConfigValue());
			}
			List<TBasConfig> ip_list = iTBasConfigService.getTBasConfigs(null, null, serviceName, "*");
			for(int i = 0;i<ip_list.size();i++){
				reMap.put(ip_list.get(i).getConfigName(), ip_list.get(i).getConfigValue());
			}
			List<TBasConfig> list = iTBasConfigService.getTBasConfigByService(serviceName,serviceIp);
			for(int i = 0;i<list.size();i++){
				reMap.put(list.get(i).getConfigName(), list.get(i).getConfigValue());
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<Map<String, String>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reMap);
		}
		return new RespEntity<Map<String, String>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reMap);	
	}

	@Override
	public RespEntity<List<Map<String, String>>> getTBasConfigs(Map<String, String> paramMap) {
		List<Map<String, String>> reList = new ArrayList<Map<String, String>>();
		try{
			//configId, configName, serviceName, serviceIp
			List<TBasConfig> list = iTBasConfigService.getTBasConfigs(paramMap.get("configId"),paramMap.get("configName")
					,paramMap.get("serviceName") ,paramMap.get("serviceIp") );
			for(int i = 0;i<list.size();i++){
				Map<String, String> map = new HashMap<String, String>();
				map.put("configExp", list.get(i).getConfigExp());
				map.put("configName", list.get(i).getConfigName());
				map.put("configValue", list.get(i).getConfigValue());
				map.put("dynamic", list.get(i).getDynamic());
				map.put("serviceIp", list.get(i).getServiceIp());
				map.put("serviceName", list.get(i).getServiceName());
				map.put("configId", String.valueOf(list.get(i).getConfigId()));
				reList.add(map);
			}
		}catch(Exception e){
			logger.error("",e);
			return new RespEntity<List<Map<String, String>>>(CfgConstant.FAIL_RESP_KEY,"获取系统参数错误"," ",reList);
		}
		return new RespEntity<List<Map<String, String>>>(CfgConstant.SUCCESS_RESP_KEY,"OK"," ",reList);	
	
	
	}

	public ITBasConfigService getiTBasConfigService() {
		return iTBasConfigService;
	}

	public void setiTBasConfigService(ITBasConfigService iTBasConfigService) {
		this.iTBasConfigService = iTBasConfigService;
	}


}
