package com.hq.elva.service.impl;

import com.hq.esc.inf.entity.RespEntity;
import com.hq.elva.dao.generate.TBasConfigMapper;
import com.hq.elva.entity.generate.TBasConfig;
import com.hq.elva.entity.generate.TBasConfigExample;
import com.hq.elva.entity.generate.TBasConfigExample.Criteria;
import com.hq.elva.service.ITBasConfigService;
import com.hq.elva.util.MybatisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class TBasConfigServiceImpl implements ITBasConfigService {

	@Resource
	private TBasConfigMapper tBasConfigMapper;
	/**
	 * 根据服务名+服务IP获取配置参数
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	@Override
	public List<TBasConfig> getTBasConfigByService(String serviceName, String serviceIp) {
		TBasConfigExample example = new TBasConfigExample();
		example.createCriteria().andServiceNameEqualTo(serviceName).andServiceIpEqualTo(serviceIp);
		List<TBasConfig> list = tBasConfigMapper.selectByExample(example);
		return list;
	}

	/**
	 * 根据配置ID、配置名、服务名，服务IP获取配置参数
	 * @param configId
	 * @param configName
	 * @param serviceName
	 * @param serviceIp
	 * @return
	 */
	@Override
	public List<TBasConfig> getTBasConfigs(String configId, String configName, String serviceName, String serviceIp) {
		TBasConfigExample example = new TBasConfigExample();
		Criteria criteria = example.createCriteria();
		if(configId!=null&&!"".equals(configId)){
			criteria.andConfigIdEqualTo(Long.parseLong(configId));
		}
		if(configName!=null&&!"".equals(configName)){
			criteria.andConfigNameEqualTo(configName);
		}
		if(serviceName!=null&&!"".equals(serviceName)){
			criteria.andServiceNameEqualTo(serviceName);
		}
		if(serviceIp!=null&&!"".equals(serviceIp)){
			criteria.andServiceIpEqualTo(serviceIp);
		}
		List<TBasConfig> list = tBasConfigMapper.selectByExample(example);
		return list;
	}

	/**
	 * 新增tBasConfig数据
	 * @param tBasConfig
	 */
	@Override
	public void insert(TBasConfig tBasConfig) {
		tBasConfigMapper.insert(tBasConfig);
	}

	/**
	 * 修改tBasConfig数据
	 * @param tBasConfig
	 */
	@Override
	public void update(TBasConfig tBasConfig) {
		TBasConfigExample example = new TBasConfigExample();
		Criteria criteria = example.createCriteria();
		criteria.andConfigIdEqualTo(tBasConfig.getConfigId());
		tBasConfigMapper.updateByExample(tBasConfig, example);
	}

	/**
	 * 修改tBasConfig数据
	 * @param tBasConfig
	 */
	@Override
	public void delete(String configId) {
		tBasConfigMapper.deleteByPrimaryKey(Long.valueOf(configId));
	}



	@Override
	public RespEntity<Map<String, Object>> getTBasConfigByServicePage(String serviceName, String serviceIp, int pageSize,
			int curPage) {

		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("pageSize", pageSize);
		pageMap.put("curPage", curPage);
		TBasConfigExample example = new TBasConfigExample();
		example.setOrderByClause("config_id");
		example.createCriteria().andServiceNameEqualTo(serviceName).andServiceIpEqualTo(serviceIp);
		int cnt=tBasConfigMapper.countByExample(example);
		List<TBasConfig> list = tBasConfigMapper.selectByExampleWithRowbounds(example, MybatisUtil.getRowBounds(pageMap));

		RespEntity<Map<String, Object>> entity = new RespEntity<Map<String, Object>>("0000"," "," ",MybatisUtil.populate(list, cnt));
		return entity;
	}

	@Override
	public RespEntity<Map<String, Object>> getTBasConfigsPage(String configId, String configName, String serviceName, String serviceIp,
			int pageSize, int curPage) {

		Map<String,Object> pageMap = new HashMap<String,Object>();
		pageMap.put("pageSize", pageSize);
		pageMap.put("curPage", curPage);
		TBasConfigExample example = new TBasConfigExample();
		Criteria criteria = example.createCriteria();
		if(configId!=null&&!"".equals(configId)){
			criteria.andConfigIdEqualTo(Long.parseLong(configId));
		}
		if(configName!=null&&!"".equals(configName)){
			criteria.andConfigNameEqualTo(configName);
		}
		if(serviceName!=null&&!"".equals(serviceName)){
			criteria.andServiceNameEqualTo(serviceName);
		}
		if(serviceIp!=null&&!"".equals(serviceIp)){
			criteria.andServiceIpEqualTo(serviceIp);
		}
		example.setOrderByClause("config_id");;
		int cnt=tBasConfigMapper.countByExample(example);
		List<TBasConfig> list = tBasConfigMapper.selectByExampleWithRowbounds(example, MybatisUtil.getRowBounds(pageMap));
		RespEntity<Map<String, Object>> entity = new RespEntity<Map<String, Object>>("0000"," "," ",MybatisUtil.populate(list, cnt));
		return entity;
	}


	public TBasConfigMapper gettBasConfigMapper() {
		return tBasConfigMapper;
	}

	public void settBasConfigMapper(TBasConfigMapper tBasConfigMapper) {
		this.tBasConfigMapper = tBasConfigMapper;
	}
}

