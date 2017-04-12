package com.hq.scrati.framework;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import com.hq.esc.dao.generate.TesbServiceMapper;
import com.hq.esc.entity.generate.TesbService;
import com.hq.esc.entity.generate.TesbServiceExample;
import com.hq.esc.inf.entity.Constants;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.InfException;
import com.hq.scrati.cache.local.LocalCache;
import com.hq.scrati.cache.local.LocalCacheDelayExpire;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.log.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.common.json.JSON;
import com.hq.scrati.framework.adapter.ProtocolAdapter;
import com.hq.scrati.framework.service.MonitorService;


/**
 * Created by Zale on 16/8/10.
 */
@Component("frameworkInvoker")
public class FrameworkInvoker {
	
	public static final Logger logger = Logger.getLogger(FrameworkInvoker.class);

	public static LocalCacheDelayExpire<TesbService> serviceCache = new LocalCacheDelayExpire<TesbService>(5 * 60 * 1000L, 100000L);

	@Resource
	private TesbServiceMapper tesbServiceMapper;
	
	@Resource
	private ProtocolAdapter protocolAdapter;
	
	@Resource(name="JSONRedisCache")
	private RedisCacheDao jsonRedisCache;
	
	@Resource
	private MonitorService monitorService;

	/**
	 * 
	 * @param serviceCode
	 * @param version
	 * @param parameters
	 * @param grayRule
	 * @return
	 */
    public Object invoke(String serviceCode, String version, Map<String,Object> parameters, Object... grayRule) {
    	long curTime = System.currentTimeMillis();
    	boolean maxActiveFlag = false;
    	Object serviceResult = null;
    	//往灰度发布中心获取灰度的版本
    	version = getGrayVersion(serviceCode,version,grayRule);
    	TesbService tesbService = null;
    	//获取服务数据
    	try {
			tesbService = getService(serviceCode,version);
			if(tesbService.getMaxActive()!=null&&tesbService.getMaxActive().longValue()>0){
				Long maxActive = jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf(-1));
				if(maxActive!=null){
					if(maxActive >= tesbService.getMaxActive().longValue()){
						jsonRedisCache.pushSetItem("esb_maxactive", "maxactive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion());
						Long temp = jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("-1"));
						if(temp<0){
							jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("1"));
						}
						String msg = String.format(Constants.EXCEEDING_SLA_QUOTA+" exceeding , service=%s, version=%s", tesbService.getServiceCode(),tesbService.getServiceVersion());
						throw new InfException(Constants.EXCEEDING_SLA_QUOTA,msg," ");
					}else{
						maxActiveFlag = true;
					}
				}
			}
			monitorService.prepareMetricDatum(tesbService.getServiceCode(), version);
			//判断是否需要日志
			StringBuilder params = new StringBuilder();
			for (Entry<String,Object> entry : parameters.entrySet()) {
				params.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
			}
			logger.debug("service :" + serviceCode + " version:" + version + " params:" + params);

			serviceResult = protocolAdapter.excute(curTime, tesbService, parameters,maxActiveFlag);
			monitorService.resultMetricDatum(tesbService.getServiceCode(), version, true,
					System.currentTimeMillis() - curTime, parameters);
			logger.debug("serviceResult is :" + JSON.json(serviceResult));
			return serviceResult;
		} catch (InfException e) {
			logger.error("Request appear InfException:", e);
			if(tesbService!=null){
				monitorService.resultMetricDatum(tesbService.getServiceCode(), version, false,
						System.currentTimeMillis() - curTime, parameters);
			}
			String[] message = e.getMessage().split("\\|");
			return new RespEntity(message[0], message[1], message[2]," ");
		} catch (Exception e) {
			logger.error("Request Exception:", e);
			if(tesbService!=null){
				monitorService.resultMetricDatum(tesbService.getServiceCode(), version, false,
						System.currentTimeMillis() - curTime, parameters);
			}
			//String[] message = e.getMessage().split("\\|");
			return new RespEntity("9999", e.getMessage()," "," ");
		}

    }

	/**
	 *
	 * @param serviceCode
	 * @param version
	 * @param parameters
	 * @param grayRule
	 * @return
	 */
	public <T> RespEntity<T> invokeConvert(String serviceCode, String version, Map<String,Object> parameters,Type clazz, Object... grayRule) {
		long curTime = System.currentTimeMillis();
		boolean maxActiveFlag = false;
		Object serviceResult = null;
		//往灰度发布中心获取灰度的版本
		version = getGrayVersion(serviceCode,version,grayRule);
		TesbService tesbService = null;
		//获取服务数据
		try {
			tesbService = getService(serviceCode,version);
			if(tesbService.getMaxActive()!=null&&tesbService.getMaxActive().longValue()>0){
				Long maxActive = jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf(-1));
				if(maxActive!=null){
					if(maxActive >= tesbService.getMaxActive().longValue()){
						jsonRedisCache.pushSetItem("esb_maxactive", "maxactive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion());
						Long temp = jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("-1"));
						if(temp<0){
							jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("1"));
						}
						String msg = String.format(Constants.EXCEEDING_SLA_QUOTA+" exceeding , service=%s, version=%s", tesbService.getServiceCode(),tesbService.getServiceVersion());
						throw new InfException(Constants.EXCEEDING_SLA_QUOTA,msg," ");
					}else{
						maxActiveFlag = true;
					}
				}
			}
			monitorService.prepareMetricDatum(tesbService.getServiceCode(), version);
			//判断是否需要日志
			StringBuilder params = new StringBuilder();
			for (Entry<String,Object> entry : parameters.entrySet()) {
				params.append(entry.getKey()).append("=").append(entry.getValue()).append(",");
			}
			logger.debug("service :" + serviceCode + " version:" + version + " params:" + params);

			serviceResult = protocolAdapter.excute(curTime, tesbService, parameters,maxActiveFlag);
			monitorService.resultMetricDatum(tesbService.getServiceCode(), version, true,
					System.currentTimeMillis() - curTime, parameters);
			String json = JSON.json(serviceResult);

			logger.debug("serviceResult is :" + json);
			return com.alibaba.fastjson.JSON.parseObject(json,clazz);
		} catch (InfException e) {
			logger.error("Request appear InfException:", e);
			if(tesbService!=null){
				monitorService.resultMetricDatum(tesbService.getServiceCode(), version, false,
						System.currentTimeMillis() - curTime, parameters);
			}
			String[] message = e.getMessage().split("\\|");
			return new RespEntity(message[0], message[1]);
		} catch (Exception e) {
			logger.error("Request Exception:", e);
			if(tesbService!=null){
				monitorService.resultMetricDatum(tesbService.getServiceCode(), version, false,
						System.currentTimeMillis() - curTime, parameters);
			}
			//String[] message = e.getMessage().split("\\|");
			return new RespEntity("9999", e.getMessage());
		}

	}
    
    /**
     * 根据serviceCode以及version以及对于的grayRule获取灰度发布的版本
     * @param serviceCode
     * @param version
     * @param grayRule
     * @return
     */
    private String getGrayVersion(String serviceCode, String version,Object... grayRule){
    	if(null == grayRule||"".equals(grayRule)||grayRule.length==0){
    		return version;
    	}else{
    		//调用统一分流控制引擎，获取分流版本信息
    	}
    	return version;
    }
    
    /**
     * 根据服务以及版本信息获取服务的具体配置信息
     * @param service
     * @param version
     * @return
     * @throws InfException
     * @throws IllegalAccessException 
     */
	private TesbService getService(String service, String version) throws InfException, IllegalAccessException {
		String serviceKey = service + version;
		TesbService tesbService = serviceCache.get(serviceKey);
		if (null == tesbService) {
			TesbServiceExample example = new TesbServiceExample();
			example.createCriteria().andServiceCodeEqualTo(service).andServiceVersionEqualTo(version)
					.andStatusEqualTo(Constants.STATUS_VALID);
			try {
				List<TesbService> tesbs = tesbServiceMapper.selectByExample(example);
				if (tesbs == null || tesbs.isEmpty()) {
					tesbService = new TesbService();
					tesbService.setServiceCode(LocalCache.EMPTY);
					serviceCache.put(serviceKey, tesbService, 1 * 60 * 1000L);
				} else {
					tesbService = tesbs.get(0);
					serviceCache.put(serviceKey, tesbService);
				}
			} catch (Exception e) {
				serviceCache.get(serviceKey, true);
				serviceCache.put(serviceKey, tesbService);
			}
		}
		if (LocalCache.EMPTY.equals(tesbService.getServiceCode())) {
			String msg = String.format("service not exist, service=%s, version=%s,requestId=%s", service, version," ");
			throw new InfException(Constants.SERVICE_UNAVAILABLE, msg, " ");
		}
		return tesbService;
	}
	

}
