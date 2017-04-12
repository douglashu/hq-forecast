package com.hq.elva.rpc.facade;

import com.hq.esc.inf.entity.RespEntity;

import java.util.List;
import java.util.Map;


public interface TBasConfigFacade {
	/**
	 * 根据服务名+服务IP获取配置参数
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getTBasConfigByService(String serviceName,String serviceIp);

	/**
	 * 根据服务名+服务IP获取配置参数
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	public RespEntity<Map<String,String>> getTBasConfigByServiceName(String serviceName,String serviceIp);

	/**
	 * 根据配置ID、配置名、服务名，服务IP获取配置参数
	 * @return
	 */
	public RespEntity<List<Map<String,String>>> getTBasConfigs(Map<String,String> paramMap);
}
