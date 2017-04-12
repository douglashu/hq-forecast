package com.hq.scrati.framework.adapter;

import java.util.Map;

import javax.annotation.Resource;

import com.hq.esc.entity.generate.TesbService;
import com.hq.esc.inf.entity.Constants;
import com.hq.esc.inf.entity.RespEntity;
import com.hq.scrati.common.exception.InfException;
import com.hq.scrati.cache.redis.RedisCacheDao;
import com.hq.scrati.common.log.Logger;
import org.springframework.stereotype.Component;

import com.hq.scrati.framework.service.MonitorService;

@Component("protocolAdapter")
public class ProtocolAdapter {
	
	public static final Logger LOGGER = Logger.getLogger(ProtocolAdapter.class);

	/**
	 * 支持所有的协议接口的实例
	 */
	@Resource
	private Map<String, IAdapter> protocolAdapterMap;
	
	@Resource
	private MonitorService monitorService;
	
	@Resource(name="JSONRedisCache")
	private RedisCacheDao jsonRedisCache;
	
	/**
	 * 根据服务端协议配置，调用服务
	 * @param tesbService
	 * @param parameters
	 * @return
	 * @throws InfException
	 */
	public Object excute(long cutTime,TesbService tesbService, Map<String, Object> parameters,boolean maxActiveFlag) throws InfException{
		String protocol = tesbService.getProtocol();
		IAdapter adapter = protocolAdapterMap.get(protocol+"Adapter");
		Object serviceResult = null;
		try{
			if("2".equals(tesbService.getCircuitBreaker())){
				serviceResult = new RespEntity("0001","熔断调用成功"," ");
			}else{
				serviceResult = adapter.excute(tesbService, parameters);
			}
			if(maxActiveFlag){
				Long temp = jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("-1"));
				LOGGER.info("esb temp is:"+temp);
				if(temp<0){
					jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("1"));
				}
			}
			
		}catch(Exception e){
			LOGGER.error("",e);
			if(maxActiveFlag){
				Long temp = jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("-1"));
				if(temp<0){
					jsonRedisCache.incrBy("esb_maxActive_"+tesbService.getServiceCode()+"_"+tesbService.getServiceVersion(),Long.valueOf("1"));
				}
			}
			throw new InfException(Constants.SERVICE_UNAVAILABLE,"服务器繁忙，请稍后再试!"," ");
		}
		return serviceResult;
	}
}
