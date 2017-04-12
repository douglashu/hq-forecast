package com.hq.elva.service;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.entity.generate.TBasConfig;

import java.util.List;
import java.util.Map;

public interface ITBasConfigService {
	
	/**
	 * 根据服务名+服务IP获取配置参数
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	public List<TBasConfig> getTBasConfigByService(String serviceName, String serviceIp);
	
	/**
	 * 根据配置ID、配置名、服务名，服务IP获取配置参数
	 * @param configId
	 * @param configName
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	public List<TBasConfig> getTBasConfigs(String configId, String configName, String serviceName, String serviceIp);
	
	/**
	 * 根据服务名+服务IP获取配置参数
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	public RespEntity<Map<String, Object>> getTBasConfigByServicePage(String serviceName, String serviceIp, int pageSize,
			int curPage);
	
	/**
	 * 根据配置ID、配置名、服务名，服务IP获取配置参数
	 * @param configId
	 * @param configName
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	public RespEntity<Map<String, Object>> getTBasConfigsPage(String configId, String configName, String serviceName,
			String serviceIp, int pageSize, int curPage);
	
	/**
	 * 新增tBasConfig数据
	 * @param tBasConfig
	 */
	public void insert(TBasConfig tBasConfig);
	
	/**
	 * 修改tBasConfig数据
	 * @param tBasConfig
	 */
	public void update(TBasConfig tBasConfig);
	
	/**
	 * 修改tBasConfig数据
	 * @param tBasConfig
	 */
	public void delete(String configId);

}
